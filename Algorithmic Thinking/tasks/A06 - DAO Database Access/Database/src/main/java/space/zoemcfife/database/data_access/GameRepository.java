package space.zoemcfife.database.data_access;

import org.springframework.data.repository.CrudRepository;
import space.zoemcfife.database.model.Game;

public interface GameRepository extends CrudRepository<Game, Integer>
{

}
