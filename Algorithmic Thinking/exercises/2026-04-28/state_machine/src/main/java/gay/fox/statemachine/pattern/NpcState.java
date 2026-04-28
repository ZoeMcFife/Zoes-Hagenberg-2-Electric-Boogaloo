package gay.fox.statemachine.pattern;

import gay.fox.statemachine.game.Npc;

public interface NpcState
{
    void onEnter(Npc npc);
    void update(Npc npc);
    void onExit(Npc npc);

    String getName();
}
