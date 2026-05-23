package space.zoemcfife.database.presentation.ui.genre.crud;

import space.zoemcfife.database.model.Genre;
import space.zoemcfife.database.presentation.ui.DbAccessScreen;
import space.zoemcfife.database.presentation.zoeui.UI;
import space.zoemcfife.database.service.GameService;
import space.zoemcfife.database.service.GenreService;

import java.util.List;

public class ViewAllGenres extends DbAccessScreen
{

    public ViewAllGenres(GameService gameService, GenreService genreService)
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
        List<Genre> genres = genreService.getAllGenres();

        for  (Genre genre : genres)
        {
            UI.printBlue(String.valueOf(genre.getGenreId()));
            UI.printYellow(genre.getName());
            UI.printlnWhite("");
        }

        UI.waitForEnterKey();
    }
}
