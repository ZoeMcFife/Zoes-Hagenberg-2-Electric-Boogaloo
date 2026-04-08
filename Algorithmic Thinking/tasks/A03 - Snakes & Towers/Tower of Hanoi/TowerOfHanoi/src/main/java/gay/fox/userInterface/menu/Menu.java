package gay.fox.userInterface.menu;

import gay.fox.userInterface.Screen;
import gay.fox.userInterface.UI;

public class Menu extends Screen
{
    private final MenuItem[] menuItems;
    private final String menuTitle;

    public Menu(String menuTitle, MenuItem... menuItems)
    {
        this.menuTitle = menuTitle;
        this.menuItems = menuItems;
    }

    /**
     * Displays the menu and prompts the user for a selection.
     */
    @Override
    public void startScreen()
    {
        displayMenu();
        menuSelectionPrompt();
    }

    /**
     * Displays the menu and prompts the user for a selection.
     */
    private void displayMenu()
    {
        IO.println(menuTitle);
        UI.printSeparatorLine();
        for (int i = 0; i < menuItems.length; i++)
        {
            System.out.printf("%d. %s%n", i + 1, menuItems[i].getName());
        }
    }

    private void menuSelectionPrompt()
    {
        IO.print("Select 1 - " + menuItems.length + ": ");

        int input = UI.getIntInput(1, menuItems.length) - 1;

        menuItems[input].executeAction();
    }

    public MenuItem[] getMenuItems()
    {
        return menuItems;
    }
}