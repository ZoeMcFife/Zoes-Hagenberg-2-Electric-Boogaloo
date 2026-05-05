package at.fhooe.ald.pokemon.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name  = "trainers")
public class Trainer
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int trainerId;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(name = "badge_count")
    private int badgeCount;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "trainer",  orphanRemoval = true)
    private List<CaughtPokemon> roster;

    public Trainer()
    {
    }

    public Trainer(String name)
    {
        this.name = name;
    }

    public int getTrainerId()
    {
        return trainerId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getBadgeCount()
    {
        return badgeCount;
    }

    public void setBadgeCount(int badgeCount)
    {
        this.badgeCount = badgeCount;
    }

    public List<CaughtPokemon> getRoster()
    {
        return roster;
    }

    public void setRoster(List<CaughtPokemon> roster)
    {
        this.roster = roster;
    }

    @Override
    public String toString()
    {
        return "Trainer{" +
                "trainerId=" + trainerId +
                ", name='" + name + '\'' +
                ", badgeCount=" + badgeCount +
                ", roster=" + roster +
                '}';
    }
}
