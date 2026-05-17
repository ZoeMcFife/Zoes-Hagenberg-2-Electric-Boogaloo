package space.zoemcfife.database.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import space.zoemcfife.database.data_access.GameRepository;
import space.zoemcfife.database.data_access.GenreRepository;
import space.zoemcfife.database.model.Genre;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService
{
    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository)
    {
        this.genreRepository = genreRepository;
    }

    @Transactional
    public Genre getGenreById(int id)
    {
        Optional<Genre> genre = genreRepository.findById(id);

        return genre.orElse(null);
    }

    @Transactional
    public Genre getGenreByName(String name)
    {
        Optional<Genre> genre = genreRepository.findByName(name);

        return genre.orElse(null);
    }

    @Transactional
    public List<Genre> getAllGenres()
    {
        return genreRepository.findAll();
    }

    @Transactional
    public Genre addGenre(String name)
    {
        Genre genre = new Genre(name);

        if (!genre.isValid())
        {
            throw new IllegalArgumentException("Genre is invalid");
        }

        return genreRepository.save(genre);
    }

    @Transactional
    public Genre updateGenre(int genreId, String name)
    {
        Optional<Genre> genre = genreRepository.findById(genreId);

        if (genre.isEmpty())
        {
            throw new IllegalArgumentException("Genre not found");
        }


        genre.get().setName(name);

        if (!genre.get().isValid())
        {
            throw new IllegalArgumentException("Genre is invalid");
        }

        return genreRepository.save(genre.get());
    }

    @Transactional
    public void deleteGenre(int genreId)
    {
        Optional<Genre> genre = genreRepository.findById(genreId);

        if (genre.isEmpty())
        {
            throw new IllegalArgumentException("Genre not found");
        }

        genreRepository.delete(genre.get());
    }



}


