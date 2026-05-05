package at.fhooe.ald.pokemon.data_access;

import at.fhooe.ald.pokemon.model.Pokemon;

import java.util.List;
import java.util.Optional;

public interface PokemonDAO
{
    void save(Pokemon pokemon);
    Optional<Pokemon> findById(int pokedexNumber);
    List<Pokemon> findAll();
    void update(Pokemon pokemon);
    void delete(int pokedexNumber);
}
