package space.zoemcfife.database;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import space.zoemcfife.database.csv_parser.CsvParser;
import space.zoemcfife.database.csv_parser.CsvResult;

import java.util.List;

@SpringBootApplication
public class DatabaseApplication {

    public static void main(String[] args)
    {
        SpringApplication.run(DatabaseApplication.class, args);

        List<CsvResult> list = CsvParser.parseCsv("/steam_games.csv");
    }

}
