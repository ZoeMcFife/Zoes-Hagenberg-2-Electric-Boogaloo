package space.zoemcfife.database;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import space.zoemcfife.database.csv_parser.CsvParser;
import space.zoemcfife.database.csv_parser.CsvResult;
import space.zoemcfife.database.presentation.ConsoleUI;
import space.zoemcfife.database.service.GameService;

import java.util.List;

@SpringBootApplication
public class DatabaseApplication
{


    public static void main(String[] args)
    {
        SpringApplication.run(DatabaseApplication.class, args);

    }

    @Bean
    @ConditionalOnProperty(name = "app.console.enabled", havingValue = "true", matchIfMissing = true)
    CommandLineRunner runConsole(ConsoleUI consoleUI)
    {
        return args -> consoleUI.run();
    }

}
