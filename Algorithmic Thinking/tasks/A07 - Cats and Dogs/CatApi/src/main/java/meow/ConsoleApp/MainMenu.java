package meow.ConsoleApp;

import meow.ApiClient.CatApi;
import meow.UserInterface.Menu.Menu;
import meow.UserInterface.Menu.MenuAction;
import meow.UserInterface.Menu.MenuItem;
import meow.UserInterface.Screen;
import meow.UserInterface.UI;

public class MainMenu extends Screen
{
    CatApi api;

    public MainMenu(CatApi api)
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
        MenuItem displayAllBreeds = new MenuItem("Display all Breeds", this::displayAllBreeds);
        MenuItem searchBreedById =  new MenuItem("Search Breed by ID", this::searchBreedById);
        MenuItem searchImageByBreed = new MenuItem("Search Image by Breed ID", this::searchImageByBreedId);
        MenuItem asyncTest = new MenuItem("Async Test", this::runAsyncTest);
        MenuItem exit = new MenuItem("Exit", this::exit);

        Menu mainMenu = new Menu("Cat Api", displayAllBreeds, searchBreedById, searchImageByBreed, asyncTest, exit);

        UI.printlnRed("Cat API is running!");
        mainMenu.startScreen();

        UI.clearScreen();
    }

    private void displayAllBreeds()
    {
        DisplayBreedsScreen displayBreedsScreen = new DisplayBreedsScreen(api);
        displayBreedsScreen.startScreen();
    }

    private void searchBreedById()
    {
        SearchBreedScreen searchBreedScreen = new SearchBreedScreen(api);
        searchBreedScreen.startScreen();
    }

    private void searchImageByBreedId()
    {
        SearchImageScreen searchImageScreen = new SearchImageScreen(api);
        searchImageScreen.startScreen();
    }

    private void runAsyncTest()
    {
        AsyncTestScreen asyncTestScreen = new AsyncTestScreen(api);
        asyncTestScreen.startScreen();
    }

    private void exit()
    {
        System.exit(0);
    }
}
