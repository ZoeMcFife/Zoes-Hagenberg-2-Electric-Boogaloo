package space.zoemcfife.database.data_access;

import org.springframework.data.jpa.repository.JpaRepository;
import space.zoemcfife.database.model.Genre;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre,Integer>
{
    Optional<Genre> findByName(String name);
}
