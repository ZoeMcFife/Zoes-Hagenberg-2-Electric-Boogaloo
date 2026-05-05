package at.fhooe.ald.pokemon.service;

import at.fhooe.ald.pokemon.CsvLoader;
import at.fhooe.ald.pokemon.data_access.PokemonDAO;
import at.fhooe.ald.pokemon.model.Pokemon;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PokemonService {

    private final PokemonDAO dao;
    private final CsvLoader csvLoader;

    public PokemonService(PokemonDAO dao, CsvLoader csvLoader)
    {
        this.dao = dao;
        this.csvLoader = csvLoader;
    }

    public void importFromCsv()
    {
        List<Pokemon> pokemon = csvLoader.load("/pokemon_small.csv");

        for (Pokemon p : pokemon)
        {
            dao.save(p);
        }

        System.out.println("Imported " + pokemon.size() + " Pokémon.");
    }

    public List<Pokemon> listAll()
    {
        return dao.findAll();
    }

    public Optional<Pokemon> findById(int number)
    {
        return dao.findById(number);
    }
}
 