package space.zoemcfife.database.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "games")
public class Game implements Model
{
    @Id
    private int gameId;

    @Column(name = "name")
    private String name;

    @Column(name = "release_date")
    private Date releaseDate;

    @Column(name = "price")
    private double price;

    @Column(name = "review_score")
    private int reviewScore;

    @ManyToMany
    @JoinTable
            (
                    name = "game_genres",
                    joinColumns = @JoinColumn(name = "game_id"),
                    inverseJoinColumns = @JoinColumn(name = "genre_id")
            )
    private List<Genre> genres;

    public Game(int gameId, String name, Date releaseDate, double price, int reviewScore)
    {
        setGameId(gameId);
        setName(name);
        setReleaseDate(releaseDate);
        setPrice(price);
        setReviewScore(reviewScore);
    }

    public Game()
    {

    }

    public void setGameId(int gameId)
    {
        this.gameId = gameId;
    }

    public int getGameId()
    {
        return gameId;
    }

    public String getName()
    {
        return name;
    }

    public Date getReleaseDate()
    {
        return releaseDate;
    }

    public double getPrice()
    {
        return price;
    }

    public int getReviewScore()
    {
        return reviewScore;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setReleaseDate(Date releaseDate)
    {
        this.releaseDate = releaseDate;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public void setReviewScore(int reviewScore)
    {
        this.reviewScore = reviewScore;
    }

    @Override
    public String toString()
    {
        return "Game{" +
                "gameId=" + gameId +
                ", name='" + name + '\'' +
                ", releaseDate=" + releaseDate +
                ", price=" + price +
                ", reviewScore=" + reviewScore +
                '}';
    }

    @Override
    public boolean isValid()
    {
        if (name == null || releaseDate == null)
        {
            return false;
        }

        if (price < 0)
        {
            return false;
        }

        if (reviewScore < 0 || reviewScore > 10)
        {
            return false;
        }

        return true;
    }
}
