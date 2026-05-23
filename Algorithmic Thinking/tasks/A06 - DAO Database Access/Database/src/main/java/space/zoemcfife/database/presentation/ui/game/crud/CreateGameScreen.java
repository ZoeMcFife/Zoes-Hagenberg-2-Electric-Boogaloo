package space.zoemcfife.database.presentation.ui.game.crud;

import space.zoemcfife.database.model.Game;
import space.zoemcfife.database.model.Genre;
import space.zoemcfife.database.presentation.ui.DbAccessScreen;
import space.zoemcfife.database.presentation.zoeui.UI;
import space.zoemcfife.database.service.GameService;
import space.zoemcfife.database.service.GenreService;

import java.sql.Date;

public class CreateGameScreen extends DbAccessScreen
{

    public CreateGameScreen(GameService gameService, GenreService genreService)
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
        UI.printlnYellow("Create Game!");

        boolean isIdValid = false;
        int id = -1;

        UI.printlnBlue("Enter ID:");
        while (!isIdValid)
        {
            id = UI.getIntInput(0, Integer.MAX_VALUE);

            isIdValid = gameService.getGame(id) == null;

            if (!isIdValid)
            {
                UI.printlnYellow("Invalid ID!");
            }
        }

        UI.printlnBlue("Enter Name:");
        String name = UI.getStringInput("> ");

        // i dont wanna deal with parsing dates rn
        Date releaseDate = new Date(System.currentTimeMillis());

        UI.printlnBlue("Enter Price:");
        double price = UI.getDoubleInput(0, Double.MAX_VALUE);

        UI.printlnBlue("Enter Review Score:");
        int score = UI.getIntInput(0, 10);

        try
        {
            gameService.addGame(id, name, releaseDate, price, score);

            do
            {
                UI.printlnBlue("Add Genre: ");
                String genreName = UI.getStringInput("> ");

                Genre genre = genreService.getGenreByName(genreName);

                if (genre == null)
                {
                    genre = genreService.addGenre(genreName);
                }

                gameService.addGenre(id, genre.getGenreId());
            }
            while (UI.getYesNoInput("Add another genre?"));
        }
        catch (Exception e)
        {
            UI.printlnYellow(e.getMessage());
        }

        UI.waitForEnterKey();
    }
}
