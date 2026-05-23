package space.zoemcfife.database.presentation.ui.game.crud;

import space.zoemcfife.database.model.Game;
import space.zoemcfife.database.model.Genre;
import space.zoemcfife.database.presentation.ui.DbAccessScreen;
import space.zoemcfife.database.presentation.zoeui.UI;
import space.zoemcfife.database.service.GameService;
import space.zoemcfife.database.service.GenreService;

import java.util.List;

public class FindByGenreScreen extends DbAccessScreen
{

    public FindByGenreScreen(GameService gameService, GenreService genreService)
    {
        super(gameService, genreService);
    }

    /**
     * Starts and displays this screen.
     * Each implementation should handle its own display logic and user interaction.
     */
    @Override
    public void startScreen()
    {
        boolean validGenre = false;
        Genre genre = null;

        while (!validGenre)
        {
            String genreName = UI.getStringInput("Genre Name: ");

            genre = genreService.getGenreByName(genreName);

            if (genre != null)
            {
                validGenre = true;
            }
        }

        List<Game> games = gameService.findByGenre(genre.getGenreId());

        for (Game g : games)
        {
            UI.printCyan(String.format("Game ID: %d", g.getGameId()));
            UI.printBlue(String.format(" - Game Name: %s", g.getName()));
            IO.println();
        }

        UI.waitForEnterKey();
    }
}
