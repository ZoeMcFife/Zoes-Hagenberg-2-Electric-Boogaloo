package gay.fox.statemachine.pattern;

import gay.fox.statemachine.game.Npc;

public class AttackState implements NpcState
{
    @Override
    public void onEnter(Npc npc)
    {
        npc.setAnimation("attack");
        npc.setSpeed(0);

        IO.println(npc.getName() + " started attacking.");
    }

    @Override
    public void update(Npc npc)
    {
        npc.attackPlayer();

        if (npc.getHp() < npc.getMaxHp() * 0.2)
        {
            npc.setState(new FleeState());
        }

        if (npc.distanceToPlayer() > npc.getAttackRange() * 1.5)
        {
            npc.setState(new ChaseState());
        }
    }

    @Override
    public void onExit(Npc npc)
    {

    }

    @Override
    public String getName()
    {
        return "ATTACK";
    }
}
