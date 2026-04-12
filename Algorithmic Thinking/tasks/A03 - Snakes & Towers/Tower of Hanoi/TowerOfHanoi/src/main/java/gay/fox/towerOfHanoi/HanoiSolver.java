package gay.fox.towerOfHanoi;

public class HanoiSolver
{
    private final TowerOfHanoi towerOfHanoi;
    private int moves;
    private final boolean printSteps;

    public HanoiSolver(int size, boolean printSteps)
    {
        this.towerOfHanoi = new TowerOfHanoi(size);
        this.printSteps = printSteps;
    }

    public HanoiSolver(TowerOfHanoi towerOfHanoi, boolean printSteps)
    {
        this.towerOfHanoi = towerOfHanoi;
        this.printSteps = printSteps;
    }

    public void solve()
    {
        moves = 0;

        towerOfHanoi.printStacks();

        solveTower(towerOfHanoi.getSize(), 1, 3, 2);

        towerOfHanoi.printStacks();

        IO.println("Moves: " + moves);
    }

    private void solveTower(int n, int from, int to, int aux)
    {
        if (n == 1)
        {
            towerOfHanoi.move(from, to);

            if (printSteps)
            {
                towerOfHanoi.printStacks();
            }

            moves++;

            return;
        }

        solveTower(n - 1, from, aux, to);

        towerOfHanoi.move(from, to);

        if (printSteps)
        {
            towerOfHanoi.printStacks();
        }

        moves++;

        solveTower(n - 1, aux, to, from);
    }
}
