package space.zoemcfife.database.csv_parser;

import space.zoemcfife.database.model.Game;
import space.zoemcfife.database.model.Genre;

import java.util.List;

public record CsvResult(Game game, List<Genre> genres)
{
}
