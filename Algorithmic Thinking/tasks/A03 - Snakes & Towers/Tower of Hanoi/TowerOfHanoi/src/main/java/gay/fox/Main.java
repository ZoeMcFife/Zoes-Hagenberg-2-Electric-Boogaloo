package gay.fox;


import gay.fox.towerOfHanoi.PegStack;
import gay.fox.towerOfHanoi.TowerOfHanoi;

public class Main
{
    static void main()
    {
        TowerOfHanoi towerOfHanoi = new TowerOfHanoi(5);

        towerOfHanoi.printStacks();

        towerOfHanoi.move(1, 2);
        towerOfHanoi.printStacks();
        towerOfHanoi.move(1,3);
        towerOfHanoi.move(2,3);
        towerOfHanoi.printStacks();

    }
}
