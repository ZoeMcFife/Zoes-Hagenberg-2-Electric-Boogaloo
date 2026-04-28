package gay.fox.statemachine.pattern;

import gay.fox.statemachine.game.Npc;

public class PatrolState implements NpcState
{
    private int maximumPatrolSteps = 4;

    @Override
    public void onEnter(Npc npc)
    {
        npc.setAnimation("walk");
        npc.setSpeed(1.5);

        IO.println(npc.getName() + " started patrolling.");
    }

    @Override
    public void update(Npc npc)
    {
        npc.moveAlongWaypoints();

        if (npc.distanceToPlayer() <= npc.getDetectionRange())
        {
            npc.setState(new ChaseState());
        }

        maximumPatrolSteps--;

        if (maximumPatrolSteps <= 0)
        {
            npc.setState(new IdleState());
            npc.setPlayer(null);
        }
    }

    @Override
    public void onExit(Npc npc)
    {

    }

    @Override
    public String getName()
    {
        return "PATROL";
    }
}
