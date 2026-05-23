package space.zoemcfife.database.presentation.ui.genre;

import space.zoemcfife.database.presentation.ui.CrudScreen;
import space.zoemcfife.database.presentation.ui.DbAccessScreen;
import space.zoemcfife.database.presentation.ui.game.crud.FindByGenreScreen;
import space.zoemcfife.database.presentation.ui.game.crud.FindByPriceRange;
import space.zoemcfife.database.presentation.ui.game.crud.ViewGameScreen;
import space.zoemcfife.database.presentation.ui.genre.crud.*;
import space.zoemcfife.database.presentation.zoeui.Menu;
import space.zoemcfife.database.presentation.zoeui.MenuItem;
import space.zoemcfife.database.presentation.zoeui.Screen;
import space.zoemcfife.database.presentation.zoeui.UI;
import space.zoemcfife.database.service.GameService;
import space.zoemcfife.database.service.GenreService;

public class GenreScreen extends DbAccessScreen implements CrudScreen
{
    public GenreScreen(GameService gameService, GenreService genreService)
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

        MenuItem create = new MenuItem("Create Genre", this::create);
        MenuItem delete = new MenuItem("Delete Genre", this::delete);
        MenuItem update = new MenuItem("Update Genre", this::update);
        MenuItem viewByName = new MenuItem("Get Genre by Name", this::viewByName);
        MenuItem viewById = new MenuItem("Get Genre by Id", this::viewById);
        MenuItem viewAll = new MenuItem("View All", this::viewAll);


        Menu genreMenu = new Menu("Genre", create, delete, update, viewByName, viewById, viewAll);

        genreMenu.startScreen();
    }

    @Override
    public void create()
    {
        UI.clearScreen();

        CreateGenreScreen createGenreScreen = new CreateGenreScreen(gameService, genreService);
        createGenreScreen.startScreen();
    }

    @Override
    public void update()
    {
        UI.clearScreen();

        UpdateGenreScreen updateGenreScreen = new UpdateGenreScreen(gameService, genreService);
        updateGenreScreen.startScreen();
    }

    @Override
    public void delete()
    {
        UI.clearScreen();
        DeleteGenreScreen deleteGenreScreen = new DeleteGenreScreen(gameService, genreService);
        deleteGenreScreen.startScreen();
    }

    @Override
    public void viewById()
    {
        UI.clearScreen();

        UI.printYellow("Gernre ID: ");
        int id = UI.getIntInput(0, Integer.MAX_VALUE);

        UI.clearScreen();

        ViewGenreScreen viewGenreScreen = new ViewGenreScreen(gameService, genreService, id);
        viewGenreScreen.startScreen();
    }

    @Override
    public void viewByName()
    {
        UI.clearScreen();

        UI.printYellow("Genre Name: ");
        String name = UI.getStringInput("");

        UI.clearScreen();

        ViewGenreScreen viewGenreScreen = new ViewGenreScreen(gameService, genreService, name);
        viewGenreScreen.startScreen();
    }

    public void viewAll()
    {
        UI.clearScreen();

        ViewAllGenres viewAllGenres = new ViewAllGenres(gameService, genreService);
        viewAllGenres.startScreen();
    }
}
