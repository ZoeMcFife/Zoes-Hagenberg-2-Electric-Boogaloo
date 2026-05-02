package gay.fox.statemachine.fsm;

public enum NpcState
{
    PATROL,
    CHASE,
    ATTACK,
    FLEE;

    // this is fucking cursed? ew? no? i fucking hate this?
    public NpcState transition(String event)
    {
        return switch (this)
        {
            case PATROL -> switch (event)
            {
                case "spotted" -> CHASE;
                default -> PATROL;
            };

            case CHASE -> switch (event)
            {
                case "inRange" -> ATTACK;
                case "lostSight" -> PATROL;
                default -> CHASE;
            };

            case ATTACK -> switch (event)
            {
                case "outRange" -> CHASE;
                case "hpLow" -> FLEE;
                default -> ATTACK;
            };

            case FLEE -> switch (event)
            {
                case "escaped" -> PATROL;
                default -> FLEE;
            };
        };
    }

    public String describe()
    {
        return "State " + this.name();
    }
}
