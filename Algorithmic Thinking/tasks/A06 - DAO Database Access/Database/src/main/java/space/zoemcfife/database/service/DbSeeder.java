package space.zoemcfife.database.service;

import space.zoemcfife.database.csv_parser.CsvParser;
import space.zoemcfife.database.csv_parser.CsvResult;
import space.zoemcfife.database.model.Game;
import space.zoemcfife.database.model.Genre;

import java.util.List;

public class DbSeeder
{
    public static void seed(GameService gameService, GenreService genreService)
    {
        List<CsvResult> list = CsvParser.parseCsv("/steam_games.csv");

        for (CsvResult csvResult : list)
        {
            Game game = csvResult.game();

            gameService.addGame(game);

            for (Genre genre : csvResult.genres())
            {
                if (!genreService.doesGenreExist(genre.getName()))
                {
                    genre = genreService.addGenre(genre.getName());
                }
                else
                {
                    genre = genreService.getGenreByName(genre.getName());
                }

                gameService.addGenre(game.getGameId(), genre.getGenreId());
            }
        }
    }
}
