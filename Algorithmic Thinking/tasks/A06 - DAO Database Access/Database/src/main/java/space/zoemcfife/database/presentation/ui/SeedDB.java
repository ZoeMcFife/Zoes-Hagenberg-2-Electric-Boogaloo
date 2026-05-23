package space.zoemcfife.database.presentation.ui;

import space.zoemcfife.database.presentation.zoeui.Screen;
import space.zoemcfife.database.presentation.zoeui.UI;
import space.zoemcfife.database.service.DbSeeder;
import space.zoemcfife.database.service.GameService;
import space.zoemcfife.database.service.GenreService;

public class SeedDB extends DbAccessScreen
{
    public SeedDB(GameService gameService, GenreService genreService)
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
        UI.clearScreen();

        if (UI.getYesNoInput("Seed Database?"))
            DbSeeder.seed(gameService, genreService);
        else
            IO.println("Okay, not seeding!");

        UI.waitForEnterKey();
    }
}
