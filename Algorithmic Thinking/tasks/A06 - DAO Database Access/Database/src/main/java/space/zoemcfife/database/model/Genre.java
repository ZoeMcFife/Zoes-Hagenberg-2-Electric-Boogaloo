package space.zoemcfife.database.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "genres")
public class Genre implements Model
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int genreId;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "genres")
    private List<Game> games;

    public Genre(String name)
    {
        setName(name);
    }

    public Genre()
    {

    }

    public int getGenreId()
    {
        return genreId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "Genre{" +
                "genreId=" + genreId +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean isValid()
    {
        return (name != null);
    }
}
