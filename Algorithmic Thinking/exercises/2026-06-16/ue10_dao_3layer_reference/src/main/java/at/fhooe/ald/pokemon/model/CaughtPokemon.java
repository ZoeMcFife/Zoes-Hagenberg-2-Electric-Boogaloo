package at.fhooe.ald.pokemon.model;

import jakarta.persistence.*;

@Entity
@Table(name = "caught_pokemon")
public class CaughtPokemon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "trainer_id", nullable = false)
    private Trainer trainer;

    @Column(name = "pokemon_number", nullable = false)
    private int pokemonNumber;

    private String nickname;

    public CaughtPokemon() {}

    public CaughtPokemon(Trainer trainer, int pokemonNumber, String nickname) {
        this.trainer = trainer;
        this.pokemonNumber = pokemonNumber;
        this.nickname = nickname;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Trainer getTrainer() { return trainer; }
    public void setTrainer(Trainer trainer) { this.trainer = trainer; }
    public int getPokemonNumber() { return pokemonNumber; }
    public void setPokemonNumber(int pokemonNumber) { this.pokemonNumber = pokemonNumber; }
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    @Override
    public String toString() {
        return String.format("CaughtPokemon[%d] #%03d '%s'", id, pokemonNumber, nickname);
    }
}
