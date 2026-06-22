package meow.ConsoleApp;

import meow.ApiClient.CatApi;
import meow.ApiClient.RestService;
import meow.Dto.CatDto;
import meow.UserInterface.Screen;
import meow.UserInterface.UI;

import java.util.List;

public class DisplayBreedsScreen extends Screen
{
    CatApi api;

    public DisplayBreedsScreen(CatApi api)
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

        UI.printlnRed("Showing all Breeds: ");

        try
        {
            api.getAllBreeds().thenAccept(this::displayBreeds).join();
        }
        catch (Exception e)
        {
            UI.printlnRed("Failed to show all Breeds. API ERROR.");
        }


        UI.waitForEnterKey();
    }

    private void displayBreeds(List<CatDto> breeds)
    {
        UI.printlnGreen("Found " + breeds.size() + " Breeds: ");

        for (CatDto b : breeds)
        {
            UI.printCyan(b.getName());
            UI.printGray(" - ");
            UI.printYellow(b.getId());
            IO.println();
        }
    }
}
