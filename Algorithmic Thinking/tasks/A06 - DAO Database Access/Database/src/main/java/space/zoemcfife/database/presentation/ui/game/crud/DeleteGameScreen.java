package space.zoemcfife.database.presentation.ui.game.crud;

import space.zoemcfife.database.model.Game;
import space.zoemcfife.database.presentation.ui.DbAccessScreen;
import space.zoemcfife.database.presentation.zoeui.UI;
import space.zoemcfife.database.service.GameService;
import space.zoemcfife.database.service.GenreService;

public class DeleteGameScreen extends DbAccessScreen
{

    public DeleteGameScreen(GameService gameService, GenreService genreService)
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
        UI.printlnBlue("Select Game ID: ");
        int id = UI.getIntInput(0, Integer.MAX_VALUE);

        Game game = gameService.getGame(id);

        if (game == null)
        {
            UI.printlnRed("Game not found");
            UI.waitForEnterKey();
            return;
        }

        gameService.deleteGame(id);
        UI.printlnGreen("Game deleted!!!!");

        UI.waitForEnterKey();
    }
}
