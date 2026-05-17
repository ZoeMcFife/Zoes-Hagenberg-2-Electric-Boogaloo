package space.zoemcfife.database.presentation;

import org.springframework.stereotype.Component;
import space.zoemcfife.database.presentation.ui.MainMenu;
import space.zoemcfife.database.presentation.zoeui.UI;
import space.zoemcfife.database.service.GameService;
import space.zoemcfife.database.service.GenreService;

@Component
public class ConsoleUI
{
    private final GameService gameService;
    private final GenreService genreService;

    public ConsoleUI(GameService gameService, GenreService genreService)
    {
        this.gameService = gameService;
        this.genreService = genreService;
    }

    public void run()
    {
        while (true)
        {
            MainMenu menu = new MainMenu(gameService, genreService);

            menu.startScreen();
            UI.clearScreen();
        }
    }
}
