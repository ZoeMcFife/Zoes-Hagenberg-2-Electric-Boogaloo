package gay.fox.statemachine.game;

/**
 * A simple game entity representing the player character.
 *
 * Acts as the target for NPC sensors (position, HP) and
 * receives damage from NPC attacks. No FSM logic here.
 */
public class Player
{

    private final String name;
    private double x;
    private double y;
    private int    hp;
    private final int maxHp;

    /**
     * Creates a player at the given position with full HP.
     *
     * @param name   display name (e.g. "Hero")
     * @param x      initial X position
     * @param y      initial Y position
     * @param maxHp  maximum hit points (also the starting HP)
     */
    public Player(String name, double x, double y, int maxHp)
    {
        this.name  = name;
        this.x     = x;
        this.y     = y;
        this.maxHp = maxHp;
        this.hp    = maxHp;
    }

    // ── movement ──────────────────────────────────────────────────────────────

    /** Teleports the player to the given coordinates (used in tests and demos). */
    public void moveTo(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    // ── combat ────────────────────────────────────────────────────────────────

    /** Reduces HP by the given amount; HP cannot go below 0. */
    public void takeDamage(int amount)
    {
        hp = Math.max(0, hp - amount);
    }

    /** Returns true while the player has HP greater than 0. */
    public boolean isAlive() {
        return hp > 0;
    }

    // ── getters ───────────────────────────────────────────────────────────────

    public String getName()  { return name;  }
    public double getX()     { return x;     }
    public double getY()     { return y;     }
    public int    getHp()    { return hp;    }
    public int    getMaxHp() { return maxHp; }

    @Override
    public String toString() {
        return name + " [hp=" + hp + "/" + maxHp
                + ", pos=(" + (int) x + "," + (int) y + ")]";
    }
}
