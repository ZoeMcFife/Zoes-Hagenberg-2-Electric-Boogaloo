package gay.fox.statemachine.fsm;

public class GraphDemo
{
    static void main()
    {
        String[] events = { "spotted", "inRange", "lostSight", "outRange", "escaped", "inRange", "hpLow" };

        System.out.printf("%-10s", "STATE");

        for (String event : events)
        {
            System.out.printf("%-12s", event);
        }

        IO.println();

        IO.println("-".repeat(10 + 12 * events.length));

        for (NpcState state : NpcState.values())
        {
            System.out.printf("%-10s", state.toString());

            for (String event : events)
            {
                NpcState next = state.transition(event);

                System.out.printf("%-12s", next == state ? "." : next.toString());
            }

            IO.println();
        }
    }
}
