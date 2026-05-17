package space.zoemcfife.database.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class GameGenreId implements Serializable
{
    @Column(name = "game_id")
    private Integer gameId;

    @Column(name = "genre_id")
    private Integer genreId;

    public GameGenreId() {}

    public GameGenreId(Integer gameId, Integer genreId)
    {
        this.gameId = gameId;
        this.genreId = genreId;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof GameGenreId that)) return false;
        return Objects.equals(gameId, that.gameId) &&
                Objects.equals(genreId, that.genreId);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(gameId, genreId);
    }
}
