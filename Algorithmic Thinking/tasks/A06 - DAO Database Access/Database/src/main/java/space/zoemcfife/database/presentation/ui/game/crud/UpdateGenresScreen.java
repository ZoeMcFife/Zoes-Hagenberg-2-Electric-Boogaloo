package space.zoemcfife.database.presentation.ui.game.crud;

import space.zoemcfife.database.model.Game;
import space.zoemcfife.database.model.Genre;
import space.zoemcfife.database.presentation.ui.DbAccessScreen;
import space.zoemcfife.database.presentation.zoeui.Menu;
import space.zoemcfife.database.presentation.zoeui.MenuItem;
import space.zoemcfife.database.presentation.zoeui.UI;
import space.zoemcfife.database.service.GameService;
import space.zoemcfife.database.service.GenreService;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class UpdateGenresScreen extends DbAccessScreen
{
    private int gameId;
    private boolean exit = false;

    public UpdateGenresScreen(GameService gameService, GenreService genreService, int gameId)
    {
        super(gameService, genreService);
        this.gameId = gameId;
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
            MenuItem delete = new MenuItem("Delete Genres", this::deleteGenres);
            MenuItem addGenre = new MenuItem("Add Genres", this::addGenres);
            MenuItem exit = new MenuItem("exit", this::exit);

            Menu menu = new Menu("Genre Editing", delete, addGenre, exit);
            menu.startScreen();
        }
    }

    private void exit()
    {
        exit = true;
    }

    private void addGenres()
    {
        do
        {
            UI.printlnBlue("Add Genre: ");
            String genreName = UI.getStringInput("> ");

            Genre genre = genreService.getGenreByName(genreName);

            if (genre == null)
            {
                genre = genreService.addGenre(genreName);
            }

            gameService.addGenre(gameId, genre.getGenreId());
        }
        while (UI.getYesNoInput("Add another genre?"));
    }

    private void deleteGenres()
    {
        Game game = gameService.getGame(gameId);

        AtomicBoolean keepDeleting = new AtomicBoolean(true);

        while (keepDeleting.get())
        {
            List<MenuItem> menuItems = new LinkedList<MenuItem>();

            for (Genre genre : game.getGenres())
            {
                MenuItem menuItem = new MenuItem(genre.getName(), () -> gameService.removeGenre(gameId, genre.getGenreId()));
                menuItems.add(menuItem);
            }

            menuItems.add(new MenuItem("exit", () -> keepDeleting.set(false)));

            Menu menu = new Menu("Delete Genre", menuItems);
            menu.startScreen();
        }

    }
}
