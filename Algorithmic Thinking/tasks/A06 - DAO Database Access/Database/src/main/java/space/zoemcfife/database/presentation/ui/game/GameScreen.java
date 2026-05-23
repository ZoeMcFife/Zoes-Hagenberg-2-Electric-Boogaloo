package space.zoemcfife.database.presentation.ui.game;

import space.zoemcfife.database.presentation.ui.CrudScreen;
import space.zoemcfife.database.presentation.ui.DbAccessScreen;
import space.zoemcfife.database.presentation.ui.game.crud.*;
import space.zoemcfife.database.presentation.zoeui.Menu;
import space.zoemcfife.database.presentation.zoeui.MenuItem;
import space.zoemcfife.database.presentation.zoeui.Screen;
import space.zoemcfife.database.presentation.zoeui.UI;
import space.zoemcfife.database.service.GameService;
import space.zoemcfife.database.service.GenreService;

public class GameScreen extends DbAccessScreen implements CrudScreen
{
    public GameScreen(GameService gameService, GenreService genreService)
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
        UI.printlnBlue("Games!");

        MenuItem create = new MenuItem("Create Game", this::create);
        MenuItem delete = new MenuItem("Delete Game", this::delete);
        MenuItem update = new MenuItem("Update Game", this::update);
        MenuItem viewByName = new MenuItem("Get Game by Name", this::viewByName);
        MenuItem viewById = new MenuItem("Get Game by Id", this::viewById);
        MenuItem findByGenre = new MenuItem("Find Game by Genre", this::findByGenre);
        MenuItem findByPriceRange = new MenuItem("Find Game by Price", this::findByPriceRange);

        Menu gameMenu = new Menu("Games", create, delete, update, viewByName, viewById,  findByGenre, findByPriceRange);

        gameMenu.startScreen();
    }

    @Override
    public void create()
    {
        UI.clearScreen();
        CreateGameScreen createGameScreen = new CreateGameScreen(gameService, genreService);
        createGameScreen.startScreen();
    }

    @Override
    public void update()
    {
        UI.clearScreen();
        UpdateGameScreen updateGameScreen = new UpdateGameScreen(gameService, genreService);
        updateGameScreen.startScreen();
    }

    @Override
    public void delete()
    {
        UI.clearScreen();
        DeleteGameScreen deleteGameScreen = new DeleteGameScreen(gameService, genreService);
        deleteGameScreen.startScreen();
    }

    @Override
    public void viewById()
    {
        UI.clearScreen();

        UI.printYellow("Game ID: ");
        int id = UI.getIntInput(0, Integer.MAX_VALUE);

        UI.clearScreen();

        ViewGameScreen viewGameScreen = new ViewGameScreen(gameService, genreService, id);
        viewGameScreen.startScreen();
    }

    @Override
    public void viewByName()
    {
        UI.clearScreen();

        UI.printYellow("Game Name: ");
        String name = UI.getStringInput("");

        UI.clearScreen();

        ViewGameScreen viewGameScreen = new ViewGameScreen(gameService, genreService, name);
        viewGameScreen.startScreen();
    }

    public void findByGenre()
    {
        UI.clearScreen();

        FindByGenreScreen findByGenreScreen = new FindByGenreScreen(gameService, genreService);
        findByGenreScreen.startScreen();
    }

    public void findByPriceRange()
    {
        UI.clearScreen();

        FindByPriceRange findByPriceRange = new FindByPriceRange(gameService, genreService);
        findByPriceRange.startScreen();
    }
}
