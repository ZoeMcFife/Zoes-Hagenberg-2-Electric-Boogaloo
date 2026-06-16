package at.fhooe.ald.pokemon.controller;

import at.fhooe.ald.pokemon.model.Pokemon;
import at.fhooe.ald.pokemon.service.PokemonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pokemon")
public class PokemonController
{
    private PokemonService service;

    public PokemonController(PokemonService service)
    {
        this.service = service;
    }

    @GetMapping
    public List<Pokemon> getAll()
    {
        return service.listAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pokemon> getById(@PathVariable int id)
    {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/import")
    public ResponseEntity<String> importCsv()
    {
        service.importFromCsv();

        return ResponseEntity.ok("Import Complete");
    }
}
