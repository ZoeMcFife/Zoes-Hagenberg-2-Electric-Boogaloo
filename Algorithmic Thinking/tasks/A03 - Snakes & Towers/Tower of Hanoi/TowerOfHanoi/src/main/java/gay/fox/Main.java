package gay.fox;


import gay.fox.game.MainMenu;
import gay.fox.towerOfHanoi.HanoiSolver;
import gay.fox.towerOfHanoi.PegStack;
import gay.fox.towerOfHanoi.TowerOfHanoi;
import gay.fox.userInterface.UI;

public class Main
{
    static void main()
    {
        while (true)
        {
            MainMenu menu = new MainMenu();
            menu.startScreen();
            UI.clearScreen();
        }


    }
}
