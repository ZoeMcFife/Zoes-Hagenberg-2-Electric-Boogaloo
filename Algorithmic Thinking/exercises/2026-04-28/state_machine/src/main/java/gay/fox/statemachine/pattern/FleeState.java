package gay.fox.statemachine.pattern;

import gay.fox.statemachine.game.Npc;

public class FleeState implements NpcState
{
    private static final double SAFE_DISTANCE = 50;

    @Override
    public void onEnter(Npc npc)
    {
        npc.setAnimation("run");
        npc.setSpeed(4);

        IO.println(npc.getName() + " started fleeing.");
    }

    @Override
    public void update(Npc npc)
    {
        npc.moveAwayFromPlayer();

        if (npc.distanceToPlayer() > SAFE_DISTANCE)
        {
            npc.setState(new PatrolState());
        }
    }

    @Override
    public void onExit(Npc npc)
    {
        npc.setHp(npc.getMaxHp());
    }

    @Override
    public String getName()
    {
        return "FLEE";
    }
}
