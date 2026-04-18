package gay.fox;

import java.util.Random;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main
{
    static void main()
    {
        Random rand = new Random(12);

        for (int i = 0; i < 10; i++)
        {
            IO.println(rand.nextInt(i + 1));
        }

    }
}
