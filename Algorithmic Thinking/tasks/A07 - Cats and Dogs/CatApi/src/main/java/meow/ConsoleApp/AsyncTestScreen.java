package meow.ConsoleApp;

import meow.ApiClient.CatApi;
import meow.Dto.CatDto;
import meow.UserInterface.Screen;
import meow.UserInterface.UI;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class AsyncTestScreen extends Screen
{
    // A handful of breed ids to fetch - feel free to add more from your list
    private static final String[] BREED_IDS = {
            "abys", "beng", "mcoo", "ragd", "siam", "sphy", "pers", "sava"
    };

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

        for (String id : BREED_IDS)
        {
            long reqStart = System.currentTimeMillis();

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

        long totalTime = System.currentTimeMillis() - start;

        UI.printSeparatorLine();
        UI.printlnCyan("Sequential total: " + totalTime + "ms — " + found + "/" + BREED_IDS.length + " found.");
    }

    /**
     * ASYNC STYLE.
     * We fire off every request first WITHOUT waiting, stashing the futures.
     * Only once all requests are in flight do we wait for them to finish.
     * Total time ≈ the slowest single request, not the sum of all of them.
     */
    private void runConcurrent()
    {
        UI.printlnYellow("--- Concurrent (async) requests ---");
        UI.printSeparatorLine();

        long start = System.currentTimeMillis();

        List<CompletableFuture<Optional<CatDto>>> futures = new ArrayList<>();

        for (String id : BREED_IDS)
        {
            futures.add(api.getBreedById(id)); // fired, not joined yet
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        long totalTime = System.currentTimeMillis() - start;
        int found = 0;

        for (int i = 0; i < BREED_IDS.length; i++)
        {
            Optional<CatDto> breed = futures.get(i).join(); // already done, instant

            if (breed.isPresent())
            {
                found++;
                UI.printlnGreen("[" + BREED_IDS[i] + "] found");
            }
            else
            {
                UI.printlnRed("[" + BREED_IDS[i] + "] not found");
            }
        }

        UI.printSeparatorLine();
        UI.printlnCyan("Concurrent total: " + totalTime + "ms — " + found + "/" + BREED_IDS.length + " found.");
    }
}