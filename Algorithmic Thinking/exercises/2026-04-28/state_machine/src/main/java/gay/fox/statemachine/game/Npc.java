package gay.fox.statemachine.game;

import gay.fox.statemachine.pattern.NpcState;
import gay.fox.statemachine.pattern.PatrolState;

import java.util.List;

/**
 * NPC context class for the State Pattern FSM (Part 2).
 *
 * The NPC owns its current state object and delegates all behavior
 * to it via update(). State transitions happen by calling setState(),
 * which automatically invokes onExit() on the departing state and
 * onEnter() on the arriving state — callers never invoke those hooks directly.
 *
 * Sensor methods (distanceToPlayer, getHp, ...) are read by state classes
 * to decide when to transition.  Actuator methods (moveTowardPlayer, attackPlayer, ...)
 * are called by state classes to produce behavior.
 */
public class Npc {

    // ── immutable configuration ───────────────────────────────────────────────
    private final String name;
    private final double detectionRange;   // PATROL → CHASE trigger distance
    private final double attackRange;      // CHASE  → ATTACK trigger distance
    private final int    maxHp;
    private final int    attackDamage;

    // ── mutable game state ────────────────────────────────────────────────────
    private double x;
    private double y;
    private int    hp;
    private double speed;
    private String animation;

    // ── FSM ───────────────────────────────────────────────────────────────────
    private NpcState currentState;

    // ── world references ──────────────────────────────────────────────────────
    private Player            player;       // null = no player in world
    private final List<double[]> waypoints; // patrol path [[x,y], ...]
    private int waypointIndex;

    // ── constructor ───────────────────────────────────────────────────────────

    /**
     * Creates an NPC that begins in the PATROL state.
     *
     * @param name            display name (e.g. "Guard", "Troll")
     * @param startX          initial X position
     * @param startY          initial Y position
     * @param maxHp           maximum hit points
     * @param detectionRange  distance at which the NPC spots the player (PATROL → CHASE)
     * @param attackRange     distance at which the NPC can melee-attack (CHASE → ATTACK)
     * @param attackDamage    damage dealt per attack tick
     * @param waypoints       ordered patrol points [[x0,y0],[x1,y1],...]; must be non-empty
     */
    public Npc(String name, double startX, double startY,
               int maxHp, double detectionRange, double attackRange,
               int attackDamage, List<double[]> waypoints) {
        this.name           = name;
        this.x              = startX;
        this.y              = startY;
        this.maxHp          = maxHp;
        this.hp             = maxHp;
        this.detectionRange = detectionRange;
        this.attackRange    = attackRange;
        this.attackDamage   = attackDamage;
        this.waypoints      = List.copyOf(waypoints);
        this.waypointIndex  = 0;

        // Start in PATROL — call onEnter directly (not via setState) to avoid
        // a spurious onExit call on a null previous state.
        this.currentState = new PatrolState();
        this.currentState.onEnter(this);
    }

    // ── FSM core ──────────────────────────────────────────────────────────────

    /**
     * Advances the FSM by one game tick.
     * Call this once per frame (or once per test step) to drive NPC behaviour.
     */
    public void update() {
        currentState.update(this);
    }

    /**
     * Transitions to a new state.
     * Calls onExit() on the current state, then onEnter() on the new state.
     * Invoked exclusively by state classes from inside their update() method.
     */
    public void setState(NpcState newState) {
        currentState.onExit(this);
        currentState = newState;
        currentState.onEnter(this);
    }

    // ── sensor methods (read by state classes) ────────────────────────────────

    /**
     * Euclidean distance to the player.
     * Returns Double.MAX_VALUE when no player is registered, so patrol/chase
     * guards never fire without an explicit setPlayer() call.
     */
    public double distanceToPlayer() {
        if (player == null) return Double.MAX_VALUE;
        double dx = player.getX() - x;
        double dy = player.getY() - y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public boolean hasPlayer()
    {
        return player != null;
    }

    public double getHp()             { return hp;             }
    public int getMaxHp()          { return maxHp;          }
    public double getDetectionRange() { return detectionRange; }
    public double getAttackRange()    { return attackRange;    }

    // ── actuator methods (called by state classes) ────────────────────────────

    /** Moves one step along the patrol waypoint list (cycles when it reaches the end). */
    public void moveAlongWaypoints() {
        if (waypoints.isEmpty()) return;
        double[] target = waypoints.get(waypointIndex);
        System.out.println(name + " moves toward waypoint " + waypointIndex
                + " (" + target[0] + "," + target[1] + ")");
        waypointIndex = (waypointIndex + 1) % waypoints.size();

        x = target[0];
        y = target[1];
    }

    /** Moves the NPC one step toward the player (Euclidean, speed units). */
    public void moveTowardPlayer() {
        if (player == null) return;
        System.out.println(name + " moves toward " + player.getName());
        double dist = distanceToPlayer();
        if (dist > 0) {
            x += speed * (player.getX() - x) / dist;
            y += speed * (player.getY() - y) / dist;
        }
    }

    /** Moves the NPC one step away from the player (Euclidean, speed units). */
    public void moveAwayFromPlayer() {
        if (player == null) return;
        System.out.println(name + " flees away from " + player.getName());
        double dist = distanceToPlayer();
        if (dist > 0) {
            x -= speed * (player.getX() - x) / dist;
            y -= speed * (player.getY() - y) / dist;
        }
    }

    /** Deals attackDamage to the player and logs the hit. */
    public void attackPlayer() {
        if (player == null) return;
        player.takeDamage(attackDamage);
        System.out.println(name + " attacks " + player.getName()
                + " for " + attackDamage + " dmg ("
                + player.getHp() + "/" + player.getMaxHp() + " hp left)");
    }

    /** Sets the current animation label (used by states in onEnter). */
    public void setAnimation(String animation) {
        this.animation = animation;
        System.out.println(name + " [animation: " + animation + "]");
    }

    /** Sets the movement speed (used by states in onEnter). */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    // ── world wiring ──────────────────────────────────────────────────────────

    /** Registers the player in the NPC's world. Pass null to remove. */
    public void setPlayer(Player player) {
        this.player = player;
    }

    // ── getters for testing ───────────────────────────────────────────────────

    public String   getName()            { return name;                  }
    public NpcState getCurrentState()    { return currentState;          }
    public String   getCurrentStateName(){ return currentState.getName(); }
    public String   getAnimation()       { return animation;             }
    public double   getSpeed()           { return speed;                 }
    public double   getX()               { return x;                     }
    public double   getY()               { return y;                     }

    /**
     * Directly sets the NPC's HP — test seam for injecting low-health scenarios.
     * Value is clamped to [0, maxHp].
     */
    public void setHp(int hp) {
        this.hp = Math.max(0, Math.min(maxHp, hp));
    }

    @Override
    public String toString() {
        return name + " [state=" + currentState.getName()
                + ", hp=" + hp + "/" + maxHp
                + ", pos=(" + String.format("%.1f", x) + "," + String.format("%.1f", y) + ")"
                + ", anim=" + animation + "]";
    }
}