package space.zoemcfife.database.data_access;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import space.zoemcfife.database.model.Game;
import space.zoemcfife.database.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GameRepository extends CrudRepository<Game, Integer>
{
    Optional<Game> findByName(String name);
    List<Game> findByPriceBetween(double minPrice, double maxPrice);

    @Query("SELECT g FROM Game g JOIN GameGenre gg ON g.gameId = gg.game.gameId WHERE gg.genre.genreId = :genreId")
    List<Game> findByGenreId(@Param("genreId") int genreId);
}
