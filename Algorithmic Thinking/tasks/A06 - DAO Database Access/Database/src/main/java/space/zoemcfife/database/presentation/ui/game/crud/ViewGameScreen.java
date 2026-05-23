package space.zoemcfife.database.presentation.ui.game.crud;

import space.zoemcfife.database.model.Game;
import space.zoemcfife.database.model.Genre;
import space.zoemcfife.database.presentation.ui.DbAccessScreen;
import space.zoemcfife.database.presentation.zoeui.UI;
import space.zoemcfife.database.service.GameService;
import space.zoemcfife.database.service.GenreService;

public class ViewGameScreen extends DbAccessScreen
{
    private int gameId = -1;
    private String gameName = null;

    public ViewGameScreen(GameService gameService, GenreService genreService, int id)
    {
        super(gameService, genreService);
        this.gameId = id;
    }

    public ViewGameScreen(GameService gameService, GenreService genreService, String name)
    {
        super(gameService, genreService);
        this.gameName = name;
    }

    /**
     * Starts and displays this screen.
     * Each implementation should handle its own display logic and user interaction.
     */
    @Override
    public void startScreen()
    {
        Game game;

        if (gameId != -1)
            game = gameService.getGame(gameId);
        else
            game = gameService.getGameByName(gameName);

        if (game == null)
        {
            UI.printlnRed("No game found...");
            UI.waitForEnterKey();
            return;
        }

        UI.printlnCyan("=== Game Info ===");
        UI.printGray("ID:           "); UI.printlnWhite(String.valueOf(game.getGameId()));
        UI.printGray("Name:         "); UI.printlnWhite(game.getName());
        UI.printGray("Release Date: "); UI.printlnWhite(String.valueOf(game.getReleaseDate()));
        UI.printGray("Price:        "); UI.printlnGreen("$" + String.format("%.2f", game.getPrice()));
        UI.printGray("Review Score: ");

        if (game.getReviewScore() >= 8)
        {
            UI.printlnGreen(game.getReviewScore() + "/10");
        }
        else if (game.getReviewScore() >= 5)
        {
            UI.printlnYellow(game.getReviewScore() + "/10");
        }
        else
        {
            UI.printlnRed(game.getReviewScore() + "/10");
        }

        UI.printGray("Genres:       ");

        for (Genre g : game.getGenres())
        {
            UI.printGray(g.getName() + ", ");
        }

        IO.println();

        UI.waitForEnterKey();
    }
}
