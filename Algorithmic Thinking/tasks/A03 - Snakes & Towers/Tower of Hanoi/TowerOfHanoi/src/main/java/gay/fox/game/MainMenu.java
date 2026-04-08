package gay.fox.game;

import gay.fox.towerOfHanoi.HanoiSolver;
import gay.fox.userInterface.Screen;
import gay.fox.userInterface.menu.Menu;
import gay.fox.userInterface.menu.MenuAction;
import gay.fox.userInterface.menu.MenuItem;

public class MainMenu extends Screen
{

    @Override
    public void startScreen()
    {
        MenuItem play = new MenuItem("Play Hanoi", this::playHanoi);
        MenuItem solve = new MenuItem("Try the solver!", this::hanoiSolver);


        Menu main = new Menu("Main Menu", play, solve);

        main.startScreen();
    }

    private void playHanoi()
    {
        HanoiGame game = new HanoiGame();
        game.startScreen();
    }

    private void hanoiSolver()
    {
        HanoiSolverScreen screen = new HanoiSolverScreen();
        screen.startScreen();
    }
}
