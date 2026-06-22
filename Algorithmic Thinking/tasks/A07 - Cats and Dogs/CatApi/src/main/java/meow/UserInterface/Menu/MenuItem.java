package meow.UserInterface.Menu;

/**
 * Represents a single item in a menu, with a name and an associated action.
 */
public class MenuItem
{
    /** The display name of the menu item */
    private final String name;
    /** The action to perform when the menu item is selected */
    private final MenuAction action;

    public MenuItem(String name, MenuAction action)
    {
        this.name = name;
        this.action = action;
    }

    public String getName()
    {
        return name;
    }

    /**
     * Executes the action associated with this menu item.
     */
    public void executeAction()
    {
        action.execute();
    }
}