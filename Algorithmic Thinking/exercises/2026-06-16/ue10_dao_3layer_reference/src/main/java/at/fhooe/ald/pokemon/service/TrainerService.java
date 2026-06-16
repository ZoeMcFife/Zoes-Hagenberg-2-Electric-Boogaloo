package at.fhooe.ald.pokemon.service;

import at.fhooe.ald.pokemon.dataaccess.PokemonDao;
import at.fhooe.ald.pokemon.model.CaughtPokemon;
import at.fhooe.ald.pokemon.model.Trainer;
import at.fhooe.ald.pokemon.dataaccess.repository.CaughtPokemonRepository;
import at.fhooe.ald.pokemon.dataaccess.repository.TrainerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TrainerService {

    private final TrainerRepository trainerRepository;
    private final CaughtPokemonRepository caughtPokemonRepository;
    private final PokemonDao pokemonDao;

    public TrainerService(TrainerRepository trainerRepository, CaughtPokemonRepository caughtPokemonRepository, PokemonDao pokemonDao) {
        this.trainerRepository = trainerRepository;
        this.caughtPokemonRepository = caughtPokemonRepository;
        this.pokemonDao = pokemonDao;
    }

    @Transactional
    public Trainer addTrainer(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Trainer name must not be blank");
        // Idempotent: the trainer name is unique, so a repeated demo run must reuse the
        // existing trainer instead of violating the unique constraint. Get-or-create by name.
        return trainerRepository.findByName(name)
                .orElseGet(() -> trainerRepository.save(new Trainer(name)));
    }

    public List<Trainer> listAll() {
        return trainerRepository.findAll();
    }

    @Transactional
    public void catchPokemon(int trainerId, int pokemonNumber, String nickname) {
        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new IllegalArgumentException("Trainer not found: " + trainerId));
        pokemonDao.findById(pokemonNumber)
                .orElseThrow(() -> new IllegalArgumentException("Pokémon not found: " + pokemonNumber));
        caughtPokemonRepository.save(new CaughtPokemon(trainer, pokemonNumber, nickname));
    }

    public List<CaughtPokemon> getRoster(int trainerId) {
        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new IllegalArgumentException("Trainer not found: " + trainerId));
        return caughtPokemonRepository.findByTrainer(trainer);
    }
}
