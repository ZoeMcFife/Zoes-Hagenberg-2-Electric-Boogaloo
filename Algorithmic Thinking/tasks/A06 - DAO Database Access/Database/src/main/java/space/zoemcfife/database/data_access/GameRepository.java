package space.zoemcfife.database.data_access;

import org.springframework.data.repository.CrudRepository;
import space.zoemcfife.database.model.Game;

import java.util.Optional;

public interface GameRepository extends CrudRepository<Game, Integer>
{
    Optional<Game> findByName(String name);
}
