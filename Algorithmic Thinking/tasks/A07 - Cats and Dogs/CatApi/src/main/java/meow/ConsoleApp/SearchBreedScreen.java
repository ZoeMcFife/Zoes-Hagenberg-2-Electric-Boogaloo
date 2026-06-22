package meow.ConsoleApp;

import meow.ApiClient.CatApi;
import meow.Dto.CatDto;
import meow.UserInterface.Screen;
import meow.UserInterface.UI;

public class SearchBreedScreen extends Screen
{
    CatApi api;

    public SearchBreedScreen(CatApi api)
    {
        this.api = api;
    }


    /**
     * Starts and displays this screen.
     * Each implementation should handle its own display logic and user interaction.
     */
    @Override
    public void startScreen()
    {
        UI.clearScreen();

        UI.printlnRed("Search for Breed by ID: ");

        String id = UI.getStringInput("Enter Breed ID: ", 4, 4);

        CatDto cat = null;

        try
        {
            cat = api.getBreedById(id).join().orElse(null);
        }
        catch (Exception e)
        {
            UI.printlnRed("Failed to find breed. API ERROR.");
        }

        if (cat != null)
        {
            UI.printlnRed("╔══════════════════════════════╗");
            UI.printlnRed("║        BREED PROFILE         ║");
            UI.printlnRed("╚══════════════════════════════╝");

            UI.printlnGreen("ID        : " + cat.getId());
            UI.printlnBlue("Name      : " + cat.getName());
            UI.printlnYellow("Origin    : " + cat.getOrigin());
            UI.printlnCyan("Life span : " + cat.getLifeSpan());

            UI.printlnPurple("Weight    : " + cat.getWeight());

            IO.println("\nDescription:");
            IO.println(cat.getDescription());

            UI.printlnYellow("\nTemperament:");
            for (String t : cat.getTemperament())
            {
                UI.printlnCyan(" • " + t);
            }

            UI.printlnRed("══════════════════════════════");
        }
        else
        {
            UI.printlnRed("Breed not found!");
        }

        UI.waitForEnterKey();
    }
}
