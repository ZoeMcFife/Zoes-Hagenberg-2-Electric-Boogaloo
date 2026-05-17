package space.zoemcfife.database.presentation.ui;

import space.zoemcfife.database.presentation.zoeui.Screen;
import space.zoemcfife.database.presentation.zoeui.UI;
import space.zoemcfife.database.service.DbSeeder;
import space.zoemcfife.database.service.GameService;
import space.zoemcfife.database.service.GenreService;

public class SeedDB extends Screen
{
    private final GameService gameService;
    private final GenreService genreService;

    public SeedDB(GameService gameService, GenreService genreService)
    {
        this.gameService = gameService;
        this.genreService = genreService;
    }

    /**
     * Starts and displays this screen.
     * Each implementation should handle its own display logic and user interaction.
     */
    @Override
    public void startScreen()
    {
        UI.clearScreen();

        DbSeeder.seed(gameService, genreService);

        UI.waitForEnterKey();
    }
}
