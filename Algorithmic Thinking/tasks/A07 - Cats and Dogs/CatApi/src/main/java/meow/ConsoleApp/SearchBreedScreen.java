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
        UI.printlnRed("Search for Breed by ID: ");

        String id = UI.getStringInput("Enter Breed ID: ", 4, 4);

        CatDto cat = api.getBreedById(id).join().orElse(null);

        if (cat != null)
        {
            UI.printlnRed("========== BREED INFO ==========");

            UI.printlnGreen("ID: " + cat.getId());
            UI.printlnBlue("Name: " + cat.getName());

            UI.printlnYellow("Origin: " + cat.getOrigin());
            UI.printlnCyan("Life Span: " + cat.getLifeSpan());

            UI.printlnPurple("Weight: " + cat.getWeight());

            UI.printlnGray("Description:");
            UI.printlnGray(cat.getDescription());

            UI.printlnYellow("\nTemperament:");
            cat.getTemperament().forEach(t ->
                    UI.printlnCyan(" - " + t));

            UI.printlnRed("================================");
        }
        else
        {
            UI.printlnRed("Breed not found!");
        }

        UI.waitForEnterKey();
    }
}
