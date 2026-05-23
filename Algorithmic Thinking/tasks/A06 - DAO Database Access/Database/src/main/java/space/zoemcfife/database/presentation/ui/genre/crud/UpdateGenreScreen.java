package space.zoemcfife.database.presentation.ui.genre.crud;

import space.zoemcfife.database.presentation.ui.DbAccessScreen;
import space.zoemcfife.database.presentation.zoeui.UI;
import space.zoemcfife.database.service.GameService;
import space.zoemcfife.database.service.GenreService;

public class UpdateGenreScreen extends DbAccessScreen
{
    public UpdateGenreScreen(GameService gameService, GenreService genreService)
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
        UI.printlnYellow("Update Genre!");

        boolean isValid = false;

        while (!isValid)
        {
            String name = UI.getStringInput("Genre Name: ");

            if (genreService.doesGenreExist(name))
            {
                String newName = UI.getStringInput("New Genre Name: ");

                if (genreService.doesGenreExist(newName))
                    UI.printlnRed("Genre already exists!");
                else
                {
                    isValid = true;

                    genreService.updateGenre(genreService.getGenreByName(name).getGenreId(), newName);
                    UI.printlnGreen("Updated genre!");
                }
            }
            else
            {
                UI.printlnRed("Genre doesn't exist!");
            }
        }

        UI.waitForEnterKey();
    }

}

