package at.fhooe.ald.pokemon.service;

import at.fhooe.ald.pokemon.data_access.PokemonDAO;
import at.fhooe.ald.pokemon.model.CaughtPokemon;
import at.fhooe.ald.pokemon.model.Trainer;
import at.fhooe.ald.pokemon.data_access.repository.CaughtPokemonRepository;
import at.fhooe.ald.pokemon.data_access.repository.TrainerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TrainerService
{
    private final TrainerRepository trainerRepository;
    private final CaughtPokemonRepository caughtPokemonRepository;
    private final PokemonDAO pokemonDao;

    public TrainerService(TrainerRepository trainerRepository, CaughtPokemonRepository caughtPokemonRepository, PokemonDAO pokemonDao)
    {
        this.trainerRepository = trainerRepository;
        this.caughtPokemonRepository = caughtPokemonRepository;
        this.pokemonDao = pokemonDao;
    }

    @Transactional
    public Trainer addTrainer(String name)
    {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Trainer name must not be blank");
        Trainer t = new Trainer(name);
        return trainerRepository.save(t);
    }

    public List<Trainer> listAll()
    {
        return trainerRepository.findAll();
    }

    @Transactional
    public void catchPokemon(int trainerId, int pokemonNumber, String nickname)
    {
        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new IllegalArgumentException("Trainer not found: " + trainerId));
        pokemonDao.findById(pokemonNumber)
                .orElseThrow(() -> new IllegalArgumentException("Pokémon not found: " + pokemonNumber));
        caughtPokemonRepository.save(new CaughtPokemon(trainer, pokemonNumber, nickname));
    }

    public List<CaughtPokemon> getRoster(int trainerId)
    {
        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new IllegalArgumentException("Trainer not found: " + trainerId));
        return caughtPokemonRepository.findByTrainer(trainer);
    }
}
