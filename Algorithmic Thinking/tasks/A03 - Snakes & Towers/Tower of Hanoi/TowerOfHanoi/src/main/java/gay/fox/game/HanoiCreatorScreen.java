package gay.fox.game;

import gay.fox.towerOfHanoi.TowerOfHanoi;
import gay.fox.userInterface.Screen;
import gay.fox.userInterface.UI;

public class HanoiCreatorScreen extends Screen
{
    private TowerOfHanoi towerOfHanoi;
    private boolean showSteps;

    @Override
    public void startScreen()
    {
        UI.clearScreen();

        UI.printCyan("Select your hanoi size!");

        int hanoiSize = UI.getIntInput(0, 1000);

        towerOfHanoi = new TowerOfHanoi(hanoiSize);
    }

    public TowerOfHanoi getTowerOfHanoi()
    {
        return towerOfHanoi;
    }

}
