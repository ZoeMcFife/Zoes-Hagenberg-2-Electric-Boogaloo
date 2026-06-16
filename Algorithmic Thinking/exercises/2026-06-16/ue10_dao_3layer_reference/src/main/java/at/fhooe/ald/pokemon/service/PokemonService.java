package at.fhooe.ald.pokemon.service;

import at.fhooe.ald.pokemon.CsvLoader;
import at.fhooe.ald.pokemon.dataaccess.PokemonDao;
import at.fhooe.ald.pokemon.model.Pokemon;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PokemonService {

    private final PokemonDao dao;
    private final CsvLoader csvLoader;

    public PokemonService(PokemonDao dao, CsvLoader csvLoader) {
        this.dao = dao;
        this.csvLoader = csvLoader;
    }

    public void importFromCsv() {
        // Idempotent: importing is a seed operation, so calling it twice must not fail.
        // Without this guard a second import re-inserts the same pokedex numbers and the
        // primary-key constraint throws a duplicate-entry error.
        if (!dao.findAll().isEmpty()) {
            System.out.println("Pokémon already imported -- skipping.");
            return;
        }
        List<Pokemon> pokemon = csvLoader.load("/pokemon_small.csv");
        for (Pokemon p : pokemon) {
            dao.save(p);
        }
        System.out.println("Imported " + pokemon.size() + " Pokémon.");
    }

    public List<Pokemon> listAll() {
        return dao.findAll();
    }

    public Optional<Pokemon> findById(int number) {
        return dao.findById(number);
    }
}
