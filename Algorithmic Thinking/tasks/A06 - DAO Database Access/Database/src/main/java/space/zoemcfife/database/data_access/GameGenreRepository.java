package space.zoemcfife.database.data_access;

import org.springframework.data.repository.CrudRepository;
import space.zoemcfife.database.model.Game;
import space.zoemcfife.database.model.GameGenre;
import space.zoemcfife.database.model.GameGenreId;

import java.util.List;

public interface GameGenreRepository extends CrudRepository<GameGenre, GameGenreId>
{
    List<GameGenre> findById_GameId(Integer gameId);
    List<GameGenre> findById_GenreId(Integer genreId);
    void deleteById_GameId(Integer gameId);
}
