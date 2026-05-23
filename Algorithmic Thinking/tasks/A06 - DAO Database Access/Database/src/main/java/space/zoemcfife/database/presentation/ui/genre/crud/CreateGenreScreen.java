package space.zoemcfife.database.presentation.ui.genre.crud;

import space.zoemcfife.database.presentation.ui.DbAccessScreen;
import space.zoemcfife.database.presentation.zoeui.Screen;
import space.zoemcfife.database.presentation.zoeui.UI;
import space.zoemcfife.database.service.GameService;
import space.zoemcfife.database.service.GenreService;

public class CreateGenreScreen extends DbAccessScreen
{
    public CreateGenreScreen(GameService gameService, GenreService genreService)
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
        UI.printlnYellow("Create Genre!");

        boolean isValid = false;

        while (!isValid)
        {
            String name = UI.getStringInput("Genre Name: ");

            if (genreService.doesGenreExist(name))
            {
                UI.printlnRed("Genre already exists!");
            }
            else
            {
                genreService.addGenre(name);
                isValid = true;
                UI.printlnGreen("Genre added!");
            }
        }

        UI.waitForEnterKey();
    }
}
