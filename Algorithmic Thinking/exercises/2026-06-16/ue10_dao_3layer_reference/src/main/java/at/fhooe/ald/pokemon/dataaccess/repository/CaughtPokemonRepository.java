package at.fhooe.ald.pokemon.dataaccess.repository;

import at.fhooe.ald.pokemon.model.CaughtPokemon;
import at.fhooe.ald.pokemon.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CaughtPokemonRepository extends JpaRepository<CaughtPokemon, Integer> {
    List<CaughtPokemon> findByTrainer(Trainer trainer);
}
