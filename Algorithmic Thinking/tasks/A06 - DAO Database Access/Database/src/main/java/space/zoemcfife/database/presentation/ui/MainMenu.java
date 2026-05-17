package space.zoemcfife.database.presentation.ui;

import space.zoemcfife.database.presentation.zoeui.Menu;
import space.zoemcfife.database.presentation.zoeui.MenuItem;
import space.zoemcfife.database.presentation.zoeui.Screen;
import space.zoemcfife.database.service.GameService;
import space.zoemcfife.database.service.GenreService;

public class MainMenu extends Screen
{

    private final GameService gameService;
    private final GenreService genreService;

    public MainMenu(GameService gameService, GenreService genreService)
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
        MenuItem seed = new MenuItem("Seed Database", this::seedDatabase);

        Menu main = new  Menu("Main Menu", seed);

        main.startScreen();
    }

    private void seedDatabase()
    {
        SeedDB seedDB = new SeedDB(gameService, genreService);

        seedDB.startScreen();
    }
}
