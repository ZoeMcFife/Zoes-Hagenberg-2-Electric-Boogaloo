package at.fhooe.ald.pokemon.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trainers")
public class Trainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "badge_count")
    private int badgeCount;

    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CaughtPokemon> roster = new ArrayList<>();

    public Trainer() {}

    public Trainer(String name) {
        this.name = name;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getBadgeCount() { return badgeCount; }
    public void setBadgeCount(int badgeCount) { this.badgeCount = badgeCount; }
    public List<CaughtPokemon> getRoster() { return roster; }

    @Override
    public String toString() {
        return String.format("Trainer[%d] %s  (badges: %d)", id, name, badgeCount);
    }
}
