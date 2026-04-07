package gay.fox.game;

import gay.fox.towerOfHanoi.TowerOfHanoi;
import gay.fox.userInterface.Screen;
import gay.fox.userInterface.UI;

public class HanoiGame extends Screen
{
    private TowerOfHanoi towerOfHanoi;

    @Override
    public void startScreen()
    {
        HanoiCreatorScreen hanoiCreatorScreen = new HanoiCreatorScreen();
        hanoiCreatorScreen.startScreen();

        towerOfHanoi = hanoiCreatorScreen.getTowerOfHanoi();

        UI.clearScreen();

        while (!towerOfHanoi.isSolved())
        {
            towerOfHanoi.printStacks();

            getMove();

            UI.clearScreen();
        }

        UI.clearScreen();
        towerOfHanoi.printStacks();
        UI.printGreen("You did it!");

        UI.waitForEnterKey();
    }

    public void getMove()
    {
        int source = 0;
        int destination = 0;

        boolean moveComplete = false;

        while (!moveComplete)
        {

            UI.printCyan("Select Source Tower: ");
            source = UI.getIntInput(1, 3);

            UI.printCyan("Select Destination Tower: ");
            destination = UI.getIntInput(1, 3);

            try
            {
                towerOfHanoi.move(source, destination);
                moveComplete = true;
            }
            catch (Exception e)
            {
                UI.printlnRed("Cannot move! dik too small");
            }
        }
    }
}
