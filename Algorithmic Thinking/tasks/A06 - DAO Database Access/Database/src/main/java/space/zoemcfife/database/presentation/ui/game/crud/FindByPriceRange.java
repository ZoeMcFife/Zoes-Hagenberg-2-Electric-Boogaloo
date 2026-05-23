package space.zoemcfife.database.presentation.ui.game.crud;

import space.zoemcfife.database.model.Game;
import space.zoemcfife.database.presentation.ui.DbAccessScreen;
import space.zoemcfife.database.presentation.zoeui.UI;
import space.zoemcfife.database.service.GameService;
import space.zoemcfife.database.service.GenreService;

import java.util.List;

public class FindByPriceRange extends DbAccessScreen
{
    public FindByPriceRange(GameService gameService, GenreService genreService)
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
        UI.printlnBlue("Select Min price");

        int min = UI.getIntInput(0, Integer.MAX_VALUE);

        UI.printlnBlue("Select Max price");

        int max = UI.getIntInput(min, Integer.MAX_VALUE);

        List<Game> games = gameService.findByPriceRange(min, max);

        for (Game g : games)
        {
            UI.printCyan(String.format("Game ID: %d", g.getGameId()));
            UI.printBlue(String.format(" - Game Name: %s", g.getName()));
            IO.println();
        }

        UI.waitForEnterKey();
    }
}
