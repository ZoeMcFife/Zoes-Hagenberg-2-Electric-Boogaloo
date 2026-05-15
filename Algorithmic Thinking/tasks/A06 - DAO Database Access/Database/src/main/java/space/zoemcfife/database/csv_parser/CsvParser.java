package space.zoemcfife.database.csv_parser;

import space.zoemcfife.database.model.Game;
import space.zoemcfife.database.model.Genre;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvParser
{
    public static List<CsvResult> parseCsv(String resourcePath)
    {
        List<CsvResult> results = new ArrayList<>();

        try (InputStream in = CsvParser.class.getResourceAsStream(resourcePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in)))
        {
            reader.readLine(); // skip header line
            String line;
            while ((line = reader.readLine()) != null)
            {
                String[] col = line.split(",(?![^\\[]*])", -1);

                List<String> cols = Arrays.stream(col).toList();

                // 0 - steanappid
                // 1 - game name
                // 5 - genre list
                // 10 - release date
                // 15 - review_score
                // 20 - price initial

                int gameId = Integer.parseInt(col[0].trim());
                String name = col[1].trim();

                Date date = null;
                String dateRaw = col[10].trim();

                try
                {
                    if (!dateRaw.equalsIgnoreCase("Not Released") && !dateRaw.isEmpty())
                    {
                        date = Date.valueOf(dateRaw.split(" ")[0]);
                    }
                }
                catch (RuntimeException e)
                {
                    date = null;
                }

                IO.println(col[20].trim());

                double price;

                try
                {
                    price = Double.parseDouble(col[20].trim());
                }
                catch (RuntimeException e)
                {
                    price = 0;
                }

                int review_score;

                try
                {
                    review_score = (int) Double.parseDouble(col[15].trim());
                }
                catch (RuntimeException e)
                {
                    review_score = 0;
                }

                List<Genre> genres = new ArrayList<>();

                String genreRaw = col[5].replaceAll("[\\[\\]\"']", "").trim();

                for (String s : genreRaw.trim().split(","))
                {
                    genres.add(new Genre(s.trim()));
                }

                Game game = new Game(gameId, name, date, price, review_score);

                if (game.isValid())
                {
                    CsvResult result = new CsvResult(game, genres);
                    results.add(result);
                }
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException("Failed to load CSV: " + resourcePath, e);
        }

        return results;
    }
}
