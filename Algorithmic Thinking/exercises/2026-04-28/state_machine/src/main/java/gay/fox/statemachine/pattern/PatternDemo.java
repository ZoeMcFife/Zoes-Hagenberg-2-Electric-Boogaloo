package gay.fox.statemachine.pattern;

import gay.fox.statemachine.game.Npc;
import gay.fox.statemachine.game.Player;

import java.util.List;

public class PatternDemo
{
    static void main()
    {
        Npc arsonCat =  new Npc(
                "Arson Cat",
                0,
                0,
                100,
                15,
                3,
                36,
                List.of(
                        new double[] {5,0}, new double[] {10, 5}, new double[] {10,10}, new double[] {5,10}, new double[] {10,10}, new double[] {5,5}, new double[] {0,0}
                )
        );

        Player mina =  new Player("Mina", 40, 0, 200);

        arsonCat.setPlayer(mina);

        IO.println("--- Cat patrols far away from player ---");

        arsonCat.update();

        IO.println(arsonCat);

        arsonCat.update();

        IO.println(arsonCat);

        IO.println("--- suddenly, a mina appears ---");

        mina.moveTo(12, 0);
        IO.println(mina);

        IO.println(arsonCat);
        arsonCat.update();
        IO.println(arsonCat);

        IO.println("--- arson cat beats the shit out of mina ---");

        arsonCat.update();
        IO.println(arsonCat);
        arsonCat.update();
        IO.println(arsonCat);
        arsonCat.update();
        IO.println(arsonCat);
        arsonCat.update();
        IO.println(arsonCat);
        arsonCat.update();
        IO.println(arsonCat);
        arsonCat.update();
        IO.println(arsonCat);
        arsonCat.update();
        IO.println(arsonCat);

        IO.println("--- mina uses bite ---");
        arsonCat.setHp(5);

        arsonCat.update();
        IO.println(arsonCat);
        arsonCat.update();
        IO.println(arsonCat);
        arsonCat.update();
        IO.println(arsonCat);
        arsonCat.update();
        IO.println(arsonCat);
        arsonCat.update();
        IO.println(arsonCat);
        arsonCat.update();
        IO.println(arsonCat);
        arsonCat.update();
        IO.println(arsonCat);
        arsonCat.update();
        IO.println(arsonCat);
    }
}
