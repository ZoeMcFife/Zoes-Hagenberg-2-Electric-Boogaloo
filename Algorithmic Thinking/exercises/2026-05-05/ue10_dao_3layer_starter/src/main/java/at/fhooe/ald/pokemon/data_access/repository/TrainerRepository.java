package at.fhooe.ald.pokemon.data_access.repository;

import at.fhooe.ald.pokemon.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerRepository extends JpaRepository<Trainer, Integer>
{

}
