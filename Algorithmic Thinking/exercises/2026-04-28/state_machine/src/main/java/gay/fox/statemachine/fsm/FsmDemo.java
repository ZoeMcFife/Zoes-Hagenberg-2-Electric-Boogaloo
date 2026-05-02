package gay.fox.statemachine.fsm;

import java.util.List;
import java.util.Random;

public class FsmDemo
{
    static void main()
    {
        NpcFsm guard = new NpcFsm("Guard");

        IO.println(guard);

        for (int i = 0; i < 20; i++)
        {
            guard.handle(getRandomEvent());
        }
    }

    static String getRandomEvent()
    {
        List<String> events = List.of(new String[]{"spotted", "inRange", "lostSight", "outRange", "escaped", "inRange", "hpLow"});
        Random rand = new Random();

        return events.get(rand.nextInt(events.size()));
    }
}
