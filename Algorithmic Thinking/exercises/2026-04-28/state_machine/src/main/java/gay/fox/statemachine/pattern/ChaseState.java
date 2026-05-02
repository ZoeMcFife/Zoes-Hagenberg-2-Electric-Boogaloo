package gay.fox.statemachine.pattern;

import gay.fox.statemachine.game.Npc;

public class ChaseState implements NpcState
{
    @Override
    public void onEnter(Npc npc)
    {
        npc.setAnimation("run");
        npc.setSpeed(3.5);

        IO.println(npc.getName() + " started chasing the player.");
    }

    @Override
    public void update(Npc npc)
    {
        npc.moveTowardPlayer();

        if (npc.distanceToPlayer() <= npc.getAttackRange())
        {
            npc.setState(new AttackState());
        }
        else if (npc.distanceToPlayer() > npc.getDetectionRange())
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
        return "CHASE";
    }
}
