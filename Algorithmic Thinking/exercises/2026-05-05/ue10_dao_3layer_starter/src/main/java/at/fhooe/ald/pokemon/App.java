package at.fhooe.ald.pokemon;

import at.fhooe.ald.pokemon.presentation.ConsoleApp;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class App {

    public static void main(String[] args)
    {
        SpringApplication.run(App.class, args);
    }

    @Bean
    @ConditionalOnProperty(name = "app.console.enabled", havingValue = "true", matchIfMissing = true)
    CommandLineRunner runConsole(ConsoleApp consoleApp)
    {
        return args -> consoleApp.run();
    }
}
