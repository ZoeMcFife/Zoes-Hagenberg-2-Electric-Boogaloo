package space.zoemcfife.database.presentation.ui.genre.crud;

import space.zoemcfife.database.model.Genre;
import space.zoemcfife.database.presentation.ui.DbAccessScreen;
import space.zoemcfife.database.presentation.zoeui.UI;
import space.zoemcfife.database.service.GameService;
import space.zoemcfife.database.service.GenreService;

public class ViewGenreScreen extends DbAccessScreen
{
    private int genreId = -1;
    private String genreName;

    public ViewGenreScreen(GameService gameService, GenreService genreService, int genreId)
    {
        super(gameService, genreService);
        this.genreId = genreId;
    }

    public ViewGenreScreen(GameService gameService, GenreService genreService, String genreName)
    {
        super(gameService, genreService);
        this.genreName = genreName;
    }

    /**
     * Starts and displays this screen.
     * Each implementation should handle its own display logic and user interaction.
     */
    @Override
    public void startScreen()
    {
        Genre genre;

        if (genreId == -1)
        {
            genre = genreService.getGenreByName(genreName);

            if (genre == null)
            {
                UI.printlnRed("Genre Not Found");
                UI.waitForEnterKey();
                return;
            }

            genreId = genre.getGenreId();
        }
        else
        {
            genre = genreService.getGenreById(genreId);

            if (genre == null)
            {
                UI.printlnRed("Genre Not Found");
                UI.waitForEnterKey();
                return;
            }
        }

        UI.printlnBlue("Genre: ");
        UI.printlnYellow(String.valueOf(genre.getGenreId()));
        UI.printlnWhite(genre.getName());

        UI.waitForEnterKey();
    }
}
