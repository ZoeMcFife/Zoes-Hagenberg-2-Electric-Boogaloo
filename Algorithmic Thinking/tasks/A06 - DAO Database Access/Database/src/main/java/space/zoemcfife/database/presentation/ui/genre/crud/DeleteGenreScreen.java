package space.zoemcfife.database.presentation.ui.genre.crud;

import space.zoemcfife.database.presentation.ui.DbAccessScreen;
import space.zoemcfife.database.presentation.zoeui.UI;
import space.zoemcfife.database.service.GameService;
import space.zoemcfife.database.service.GenreService;

public class DeleteGenreScreen extends DbAccessScreen
{
    public DeleteGenreScreen(GameService gameService, GenreService genreService)
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
        UI.printlnYellow("Delete Genre!");

        boolean isValid = false;

        while (!isValid)
        {
            String name = UI.getStringInput("Genre Name: ");

            if (genreService.doesGenreExist(name))
            {
                UI.printlnGreen("Genre remoed!");

                genreService.deleteGenre(genreService.getGenreByName(name).getGenreId());

                isValid = true;
            }
            else
            {
                UI.printlnRed("Genre doesn't exists");
            }
        }

        UI.waitForEnterKey();
    }
}
