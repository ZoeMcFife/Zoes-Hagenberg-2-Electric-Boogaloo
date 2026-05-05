package at.fhooe.ald.pokemon.presentation;

import at.fhooe.ald.pokemon.model.CaughtPokemon;
import at.fhooe.ald.pokemon.model.Pokemon;
import at.fhooe.ald.pokemon.model.Trainer;
import at.fhooe.ald.pokemon.service.PokemonService;
import at.fhooe.ald.pokemon.service.TrainerService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class ConsoleApp {

    private final PokemonService pokemonService;
    private final TrainerService trainerService;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleApp(PokemonService pokemonService, TrainerService trainerService)
    {
        this.pokemonService = pokemonService;
        this.trainerService = trainerService;
    }

    public void run()
    {
        boolean running = true;

        while (running)
        {
            System.out.println("\n=== Pokémon DB ===");
            System.out.println("1) Import Pokémon from CSV");
            System.out.println("2) List all Pokémon");
            System.out.println("3) Add Trainer");
            System.out.println("4) List all Trainers");
            System.out.println("5) Catch a Pokémon");
            System.out.println("6) Show Trainer roster");
            System.out.println("0) Exit");
            System.out.print("> ");

            switch (scanner.nextLine().trim())
            {
                case "1" -> pokemonService.importFromCsv();
                case "2" ->
                {
                    List<Pokemon> all = pokemonService.listAll();
                    all.forEach(System.out::println);
                    System.out.println(all.size() + " Pokémon total.");
                }
                case "3" ->
                {
                    System.out.print("Trainer name: ");
                    Trainer t = trainerService.addTrainer(scanner.nextLine().trim());
                    System.out.println("Created: " + t);
                }
                case "4" -> trainerService.listAll().forEach(System.out::println);
                case "5" ->
                {
                    System.out.print("Trainer ID: ");
                    int tid = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("Pokédex number: ");
                    int num = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("Nickname: ");
                    String nick = scanner.nextLine().trim();
                    trainerService.catchPokemon(tid, num, nick);
                    System.out.println("Caught!");
                }
                case "6" ->
                {
                    System.out.print("Trainer ID: ");
                    int tid = Integer.parseInt(scanner.nextLine().trim());
                    List<CaughtPokemon> roster = trainerService.getRoster(tid);
                    if (roster.isEmpty()) {
                        System.out.println("No Pokémon caught yet.");
                    } else {
                        roster.forEach(cp -> {
                            String pokeName = pokemonService.findById(cp.getPokemonNumber())
                                    .map(Pokemon::getName).orElse("Unknown");
                            System.out.printf("  [%d] #%03d %-14s (nickname: %s)%n",
                                    cp.getId(), cp.getPokemonNumber(), pokeName, cp.getNickname());
                        });
                    }
                }
                case "0" -> running = false;
                default  -> System.out.println("Unknown option.");
            }
        }
    }
}
