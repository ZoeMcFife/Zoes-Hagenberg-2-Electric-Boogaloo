package gay.fox.statemachine.pattern;

import gay.fox.statemachine.game.Npc;

public class IdleState implements NpcState
{

    @Override
    public void onEnter(Npc npc)
    {
        npc.setAnimation("idling");
        npc.setSpeed(0);

        IO.println(npc.getName() + " started idling.");
    }

    @Override
    public void update(Npc npc)
    {
        if (npc.hasPlayer())
        {
            npc.setState(new PatrolState());
        }
    }

    @Override
    public void onExit(Npc npc)
    {

    }

    @Override
    public String getName()
    {
        return "IDLE";
    }
}
