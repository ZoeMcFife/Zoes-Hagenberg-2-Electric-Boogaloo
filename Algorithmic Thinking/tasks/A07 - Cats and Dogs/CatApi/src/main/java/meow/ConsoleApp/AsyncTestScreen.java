package meow.ConsoleApp;

import meow.ApiClient.CatApi;
import meow.Dto.CatDto;
import meow.UserInterface.Screen;
import meow.UserInterface.UI;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;

public class AsyncTestScreen extends Screen
{
    // A handful of breed ids to fetch - feel free to add more from your list
    private static final String[] BREED_IDS = {
            "abys", "beng", "mcoo", "ragd", "siam", "sphy", "pers", "sava"
    };

    // TheCatAPI's free/no-key tier throttles hard if you slam it with
    // a burst of simultaneous requests. Capping how many are "in flight"
    // at once avoids tripping their 429 rate limiter.
    private static final int MAX_CONCURRENT_REQUESTS = 3;

    private final CatApi api;

    public AsyncTestScreen(CatApi api)
    {
        this.api = api;
    }

    /**
     * Starts and displays this screen.
     * Each implementation should handle its own display logic and user interaction.
     */
    @Override
    public void startScreen()
    {
        UI.clearScreen();
        UI.printlnPurple("=== Async vs Non-Async API Showcase ===");
        UI.printBlankSeparatorLine();
        UI.printlnGray("Fetching " + BREED_IDS.length + " breeds by id, first one-at-a-time (blocking),");
        UI.printlnGray("then all at once (firing every request before waiting on any of them).");
        UI.printBlankSeparatorLine();
        UI.waitForEnterKey();

        runSequential();
        UI.printBlankSeparatorLine();
        UI.waitForEnterKey();

        runConcurrent();
        UI.printBlankSeparatorLine();
        UI.waitForEnterKey();
    }

    /**
     * NON-ASYNC STYLE.
     * Even though getBreedById() returns a CompletableFuture, we call .join()
     * on it immediately - so the next request can't even be sent until the
     * current one fully completes. Total time ≈ sum of every request's time.
     */
    private void runSequential()
    {
        UI.printlnYellow("--- Sequential (blocking) requests ---");
        UI.printSeparatorLine();

        long start = System.currentTimeMillis();
        int found = 0;
        int errors = 0;

        for (String id : BREED_IDS)
        {
            long reqStart = System.currentTimeMillis();

            try
            {
                Optional<CatDto> breed = api.getBreedById(id).join();

                long reqTime = System.currentTimeMillis() - reqStart;

                if (breed.isPresent())
                {
                    found++;
                    UI.printlnGreen("[" + id + "] found  (" + reqTime + "ms)");
                }
                else
                {
                    UI.printlnRed("[" + id + "] not found (" + reqTime + "ms)");
                }
            }
            catch (CompletionException | CancellationException e)
            {
                errors++;
                UI.printlnRed("[" + id + "] an API error occurred: " + rootMessage(e));
            }
        }

        long totalTime = System.currentTimeMillis() - start;

        UI.printSeparatorLine();
        UI.printlnCyan("Sequential total: " + totalTime + "ms — " + found + "/" + BREED_IDS.length
                + " found, " + errors + " error(s).");
    }

    /**
     * ASYNC STYLE.
     * We fire off requests in small batches WITHOUT waiting on each one
     * individually - within a batch every request is truly concurrent.
     * Batching (rather than firing all 8 at once) keeps us under
     * TheCatAPI's rate limit and avoids 429 Too Many Requests errors.
     */
    private void runConcurrent()
    {
        UI.printlnYellow("--- Concurrent (async) requests ---");
        UI.printSeparatorLine();

        long start = System.currentTimeMillis();

        Semaphore inFlight = new Semaphore(MAX_CONCURRENT_REQUESTS);
        List<CompletableFuture<Void>> tasks = new ArrayList<>();
        int[] found = {0};
        int[] errors = {0};

        for (String id : BREED_IDS)
        {
            CompletableFuture<Void> task = CompletableFuture.runAsync(() ->
            {
                try
                {
                    inFlight.acquireUninterruptibly();

                    try
                    {
                        Optional<CatDto> breed = api.getBreedById(id).join();

                        if (breed.isPresent())
                        {
                            synchronized (found)
                            {
                                found[0]++;
                            }
                            UI.printlnGreen("[" + id + "] found");
                        }
                        else
                        {
                            UI.printlnRed("[" + id + "] not found");
                        }
                    }
                    finally
                    {
                        inFlight.release();
                    }
                }
                catch (CompletionException | CancellationException e)
                {
                    synchronized (errors)
                    {
                        errors[0]++;
                    }
                    UI.printlnRed("[" + id + "] an API error occurred: " + rootMessage(e));
                }
            });

            tasks.add(task);
        }

        try
        {
            CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0])).join();
        }
        catch (CompletionException e)
        {
            UI.printlnRed("An API error occurred while waiting on the batch: " + rootMessage(e));
        }

        long totalTime = System.currentTimeMillis() - start;

        UI.printSeparatorLine();
        UI.printlnCyan("Concurrent total: " + totalTime + "ms — " + found[0] + "/" + BREED_IDS.length
                + " found, " + errors[0] + " error(s).");
    }

    /**
     * CompletableFuture wraps thrown exceptions in a CompletionException.
     * This digs out the actual underlying cause so the printed message
     * is useful instead of just "CompletionException".
     */
    private String rootMessage(Throwable t)
    {
        Throwable cause = t.getCause() != null ? t.getCause() : t;
        return cause.getMessage() != null ? cause.getMessage() : cause.toString();
    }
}