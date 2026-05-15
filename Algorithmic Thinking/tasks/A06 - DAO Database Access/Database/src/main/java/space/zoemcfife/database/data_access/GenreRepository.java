package space.zoemcfife.database.data_access;

import org.springframework.data.jpa.repository.JpaRepository;
import space.zoemcfife.database.model.Genre;

public interface GenreRepository extends JpaRepository<Genre,Integer>
{

}
