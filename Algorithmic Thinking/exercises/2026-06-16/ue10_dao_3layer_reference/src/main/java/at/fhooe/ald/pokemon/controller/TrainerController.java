package at.fhooe.ald.pokemon.controller;


import at.fhooe.ald.pokemon.controller.dto.CatchRequest;
import at.fhooe.ald.pokemon.controller.dto.TrainerRequest;
import at.fhooe.ald.pokemon.model.CaughtPokemon;
import at.fhooe.ald.pokemon.model.Trainer;
import at.fhooe.ald.pokemon.service.TrainerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trainer")
public class TrainerController
{
    private TrainerService service;

    public TrainerController(TrainerService service)
    {
        this.service = service;
    }

    @GetMapping
    public List<Trainer> getAll()
    {
        return service.listAll();
    }

    @PostMapping
    public ResponseEntity<Trainer> create(@RequestBody TrainerRequest trainer)
    {
        Trainer t = service.addTrainer(trainer.getName());

        return ResponseEntity.status(HttpStatus.CREATED).body(t);
    }

    @GetMapping("{id}/roster")
    public List<CaughtPokemon> getRoster(@PathVariable int id)
    {
        return service.getRoster(id);
    }

    @PostMapping("/{id}/catch")
    public ResponseEntity<Void> catchPokemon(@PathVariable int id, @RequestBody CatchRequest catched)
    {
        service.catchPokemon(id, catched.getPokemonNumber(), catched.getNickname());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
