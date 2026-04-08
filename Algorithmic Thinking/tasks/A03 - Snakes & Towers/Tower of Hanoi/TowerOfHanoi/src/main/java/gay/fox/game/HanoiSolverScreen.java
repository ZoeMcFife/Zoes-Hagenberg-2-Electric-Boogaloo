package gay.fox.game;

import gay.fox.towerOfHanoi.HanoiSolver;
import gay.fox.towerOfHanoi.TowerOfHanoi;
import gay.fox.userInterface.Screen;
import gay.fox.userInterface.UI;

public class HanoiSolverScreen extends Screen
{
    private TowerOfHanoi towerOfHanoi;

    @Override
    public void startScreen()
    {
        HanoiCreatorScreen hanoiCreatorScreen = new HanoiCreatorScreen();
        hanoiCreatorScreen.startScreen();

        towerOfHanoi = hanoiCreatorScreen.getTowerOfHanoi();

        boolean showSteps = UI.getYesNoInput("Show Steps?");

        HanoiSolver solver = new HanoiSolver(towerOfHanoi, showSteps);

        solver.solve();

        UI.waitForEnterKey();
    }
}
