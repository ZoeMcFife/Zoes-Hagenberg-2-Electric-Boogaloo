package gay.fox.statemachine.fsm;

public class NpcFsm
{
    private NpcState state;
    private final String name;

    public NpcFsm(String name)
    {
        this.name = name;
        this.state = NpcState.PATROL;
    }

    public void handle(String event)
    {
        NpcState nextState = state.transition(event);

        if (state == nextState)
            return;

        IO.println("NPC transitioned from " + state + " to " + nextState);

        state = nextState;
    }

    public NpcState getState()
    {
        return state;
    }

    @Override
    public String toString()
    {
        return name + "(" + state.describe() + ")";
    }
}
