package space.zoemcfife.database.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import space.zoemcfife.database.data_access.GameRepository;
import space.zoemcfife.database.model.Game;

import java.sql.Date;

@Service
public class GameService
{
    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository)
    {
        this.gameRepository = gameRepository;
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

}
