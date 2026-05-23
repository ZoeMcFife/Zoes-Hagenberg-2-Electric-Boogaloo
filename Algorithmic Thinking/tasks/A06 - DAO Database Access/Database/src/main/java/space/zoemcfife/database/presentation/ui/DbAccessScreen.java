package space.zoemcfife.database.presentation.ui;

import space.zoemcfife.database.presentation.zoeui.Screen;
import space.zoemcfife.database.service.GameService;
import space.zoemcfife.database.service.GenreService;

public abstract class DbAccessScreen extends Screen
{
    protected GameService gameService;
    protected GenreService genreService;

    public DbAccessScreen(GameService gameService, GenreService genreService)
    {
        this.gameService = gameService;
        this.genreService = genreService;
    }
}
