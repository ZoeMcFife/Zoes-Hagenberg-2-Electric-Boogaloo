package space.zoemcfife.database.presentation.ui;

import space.zoemcfife.database.presentation.ui.game.GameScreen;
import space.zoemcfife.database.presentation.ui.genre.GenreScreen;
import space.zoemcfife.database.presentation.zoeui.Menu;
import space.zoemcfife.database.presentation.zoeui.MenuItem;
import space.zoemcfife.database.presentation.zoeui.Screen;
import space.zoemcfife.database.presentation.zoeui.UI;
import space.zoemcfife.database.service.GameService;
import space.zoemcfife.database.service.GenreService;

public class MainMenu extends DbAccessScreen
{
    private boolean exit = false;

    public MainMenu(GameService gameService, GenreService genreService)
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
        while (!exit)
        {
            MenuItem seed = new MenuItem("Seed Database", this::seedDatabase);
            MenuItem games = new MenuItem("Access Games", this::openGames);
            MenuItem genres = new MenuItem("Access Genres", this::openGenres);
            MenuItem exit = new MenuItem("Exit", this::exit);

            Menu main = new  Menu("Main Menu", seed, games, genres, exit);

            UI.printlnYellow("Welcoem to Database! :(");

            main.startScreen();
        }
    }

    private void seedDatabase()
    {
        SeedDB seedDB = new SeedDB(gameService, genreService);
        seedDB.startScreen();
    }

    private void openGames()
    {
        GameScreen gameScreen = new GameScreen(gameService, genreService);
        gameScreen.startScreen();
    }

    private void openGenres()
    {
        GenreScreen genreScreen = new GenreScreen(gameService, genreService);
        genreScreen.startScreen();
    }

    private void exit()
    {
        exit = true;
    }
}
