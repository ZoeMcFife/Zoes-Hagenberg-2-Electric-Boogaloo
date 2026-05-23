package space.zoemcfife.database.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import space.zoemcfife.database.data_access.GameGenreRepository;
import space.zoemcfife.database.data_access.GameRepository;
import space.zoemcfife.database.data_access.GenreRepository;
import space.zoemcfife.database.model.Game;
import space.zoemcfife.database.model.GameGenre;
import space.zoemcfife.database.model.GameGenreId;
import space.zoemcfife.database.model.Genre;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class GameService
{
    private final GameRepository gameRepository;
    private final GenreRepository genreRepository;
    private final GameGenreRepository gameGenreRepository;

    public GameService(GameRepository gameRepository, GenreRepository genreRepository, GameGenreRepository gameGenreRepository)
    {
        this.gameRepository = gameRepository;
        this.genreRepository = genreRepository;
        this.gameGenreRepository = gameGenreRepository;
    }

    @Transactional
    public Game getGame(int id)
    {
        Optional<Game> game = gameRepository.findById(id);

        return game.orElse(null);
    }

    @Transactional
    public Game getGameByName(String name)
    {
        Optional<Game> game = gameRepository.findByName(name);

        return game.orElse(null);
    }

    @Transactional
    public List<Game> getAllGames()
    {
        return (List<Game>) gameRepository.findAll();
    }

    @Transactional
    public Game addGame(Game game)
    {
        if (!game.isValid())
        {
            throw new IllegalArgumentException("Game is invalid");
        }

        return gameRepository.save(game);
    }

    @Transactional
    public Game addGame(int gameId, String name, Date releaseDate, double price, int reviewScore)
    {
        Game game = new Game(gameId, name, releaseDate, price, reviewScore);

        if (!game.isValid())
        {
            throw new IllegalArgumentException("Game is invalid");
        }

        return gameRepository.save(game);
    }

    @Transactional
    public Game updateGame(int gameId, Game game)
    {
        Optional<Game> newGame = gameRepository.findById(gameId);

        if (newGame.isEmpty())
        {
            throw new IllegalArgumentException("Game doens't exist");
        }

        if (!game.isValid())
        {
            throw new IllegalArgumentException("Game is invalid");
        }

        newGame.get().setName(game.getName());
        newGame.get().setReleaseDate(game.getReleaseDate());
        newGame.get().setPrice(game.getPrice());
        newGame.get().setReviewScore(game.getReviewScore());

        return gameRepository.save(newGame.get());
    }

    @Transactional
    public void deleteGame(int gameId)
    {
        Optional<Game> game = gameRepository.findById(gameId);

        if (game.isEmpty())
        {
            throw new IllegalArgumentException("Game doens't exist");
        }

        gameRepository.delete(game.get());
    }

    @Transactional
    public GameGenre addGenre(int gameId, int genreId)
    {
        Optional<Game> game = gameRepository.findById(gameId);

        if (game.isEmpty())
        {
            throw new IllegalArgumentException("Game doens't exist");
        }

        Optional<Genre> genre = genreRepository.findById(genreId);

        if (genre.isEmpty())
        {
            throw new IllegalArgumentException("Genre doens't exist");
        }

        GameGenre gameGenre = new GameGenre(game.get(), genre.get());


        return gameGenreRepository.save(gameGenre);
    }

    @Transactional
    public void removeGenre(int gameId, int genreId)
    {
        Optional<Game> game = gameRepository.findById(gameId);

        if (game.isEmpty())
        {
            throw new IllegalArgumentException("Game doens't exist");
        }

        GameGenreId gameGenreId = new GameGenreId(gameId, genreId);

        gameGenreRepository.deleteById(gameGenreId);
    }
}
