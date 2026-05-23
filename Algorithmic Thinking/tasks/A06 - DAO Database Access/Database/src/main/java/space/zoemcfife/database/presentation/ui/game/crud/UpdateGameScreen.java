package space.zoemcfife.database.presentation.ui.game.crud;

import space.zoemcfife.database.model.Game;
import space.zoemcfife.database.presentation.ui.DbAccessScreen;
import space.zoemcfife.database.presentation.zoeui.UI;
import space.zoemcfife.database.service.GameService;
import space.zoemcfife.database.service.GenreService;

import java.sql.Date;

public class UpdateGameScreen extends DbAccessScreen
{
    public UpdateGameScreen(GameService gameService, GenreService genreService) {
        super(gameService, genreService);
    }

    /**
     * Starts and displays this screen.
     * Each implementation should handle its own display logic and user interaction.
     */
    @Override
    public void startScreen()
    {
        UI.printlnYellow("uPDATE Game!");

        boolean isIdValid = false;
        int id = -1;

        while (!isIdValid)
        {
            UI.printlnBlue("Enter ID:");
            id = UI.getIntInput(0, Integer.MAX_VALUE);

            isIdValid = gameService.getGame(id) != null;

            if (!isIdValid)
            {
                UI.printlnYellow("Invalid ID!");
            }
            else
            {

                ViewGameScreen viewGameScreen = new ViewGameScreen(gameService, genreService, id);
                viewGameScreen.startScreen();

                isIdValid = UI.getYesNoInput("Update this game?");
            }

        }

        Game game = gameService.getGame(id);

        if (UI.getYesNoInput("Update name?:"))
        {
            UI.printlnBlue("Enter Name:");
            String name = UI.getStringInput("> ");
            game.setName(name);
        }

        if (UI.getYesNoInput("Update price?:"))
        {
            UI.printlnBlue("Enter Price:");
            double price = UI.getDoubleInput(0, Double.MAX_VALUE);
            game.setPrice(price);
        }

        if (UI.getYesNoInput("Update score?:"))
        {
            int score = UI.getIntInput(0, 10);
            game.setReviewScore(score);
        }

        if (UI.getYesNoInput("Update Genres?:"))
        {
            UpdateGenresScreen updateGenresScreen = new UpdateGenresScreen(gameService, genreService, id);
            updateGenresScreen.startScreen();
        }

        try
        {
            gameService.updateGame(game.getGameId(), game);
        }
        catch (Exception e)
        {
            UI.printlnYellow(e.getMessage());
        }

        UI.waitForEnterKey();
    }
}
