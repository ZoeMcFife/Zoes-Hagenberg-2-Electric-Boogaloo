package meow.ConsoleApp;

import meow.ApiClient.CatApi;
import meow.Dto.CatDto;
import meow.Dto.ImageDto;
import meow.Image.ImageHelper;
import meow.UserInterface.Screen;
import meow.UserInterface.UI;

import java.util.ArrayList;
import java.util.List;

public class SearchImageScreen extends Screen
{
    CatApi api;

    public SearchImageScreen(CatApi api)
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

        UI.printlnRed("Search for Image by ID: ");

        String id = UI.getStringInput("Enter Breed ID: ", 4, 4);

        List<ImageDto> imgs = new ArrayList<>();

        try
        {
           imgs = api.getImagesOfBreed(id).join();
        }
        catch (Exception e)
        {
            UI.printlnRed("Failed to find image. API ERROR.");
        }

        if (!imgs.isEmpty())
        {
            ImageDto img = imgs.getFirst();

            UI.printlnGreen("Image found! - " + img.getUrl());

            boolean openImage = UI.getYesNoInput("Open Image?");

            if (openImage)
            {
                ImageHelper.openFromUrl(img.getUrl());
            }
        }
        else
        {
            UI.printlnRed("Image not found!");
        }

        UI.waitForEnterKey();
    }
}
