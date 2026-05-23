package space.zoemcfife.database.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceTest
{
    @Mock
    private GameRepository gameRepository;

    @Mock
    private GenreRepository genreRepository;

    @Mock
    private GameGenreRepository gameGenreRepository;

    @InjectMocks
    private GameService gameService;

    private Game game;
    private Genre genre;

    @BeforeEach
    void setUp()
    {
        game = mock(Game.class);
        genre = mock(Genre.class);
    }

    @Test
    void getGame_returnsGameIfExists()
    {
        when(gameRepository.findById(1)).thenReturn(Optional.of(game));

        Game result = gameService.getGame(1);

        assertEquals(game, result);
        verify(gameRepository).findById(1);
    }

    @Test
    void getGame_returnsNullIfNotFound()
    {
        when(gameRepository.findById(1)).thenReturn(Optional.empty());

        Game result = gameService.getGame(1);

        assertNull(result);
        verify(gameRepository).findById(1);
    }

    @Test
    void addGame_savesValidGame()
    {
        when(game.isValid()).thenReturn(true);
        when(gameRepository.save(game)).thenReturn(game);

        Game result = gameService.addGame(game);

        assertEquals(game, result);

        verify(game).isValid();
        verify(gameRepository).save(game);
    }

    @Test
    void addGame_throwsIfGameInvalid()
    {
        when(game.isValid()).thenReturn(false);

        assertThrows(IllegalArgumentException.class,
                () -> gameService.addGame(game));

        verify(game).isValid();
        verify(gameRepository, never()).save(any());
    }

    @Test
    void updateGame_updatesExistingGame()
    {
        Game existingGame = mock(Game.class);

        when(gameRepository.findById(1))
                .thenReturn(Optional.of(existingGame));

        when(game.isValid()).thenReturn(true);

        when(game.getName()).thenReturn("Halo");
        when(game.getReleaseDate()).thenReturn(Date.valueOf("2020-01-01"));
        when(game.getPrice()).thenReturn(59.99);
        when(game.getReviewScore()).thenReturn(90);

        when(gameRepository.save(existingGame)).thenReturn(existingGame);

        Game result = gameService.updateGame(1, game);

        assertEquals(existingGame, result);

        verify(existingGame).setName("Halo");
        verify(existingGame).setReleaseDate(Date.valueOf("2020-01-01"));
        verify(existingGame).setPrice(59.99);
        verify(existingGame).setReviewScore(90);

        verify(gameRepository).save(existingGame);
    }

    @Test
    void updateGame_throwsIfGameDoesNotExist()
    {
        when(gameRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> gameService.updateGame(1, game));

        verify(gameRepository, never()).save(any());
    }

    @Test
    void deleteGame_deletesExistingGame()
    {
        when(gameRepository.findById(1))
                .thenReturn(Optional.of(game));

        gameService.deleteGame(1);

        verify(gameRepository).delete(game);
    }

    @Test
    void deleteGame_throwsIfGameDoesNotExist()
    {
        when(gameRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> gameService.deleteGame(1));

        verify(gameRepository, never()).delete(any());
    }

    @Test
    void addGenre_savesGameGenre()
    {
        when(gameRepository.findById(1))
                .thenReturn(Optional.of(game));

        when(genreRepository.findById(2))
                .thenReturn(Optional.of(genre));

        GameGenre savedGameGenre = mock(GameGenre.class);

        when(gameGenreRepository.save(any(GameGenre.class)))
                .thenReturn(savedGameGenre);

        GameGenre result = gameService.addGenre(1, 2);

        assertEquals(savedGameGenre, result);

        verify(gameRepository).findById(1);
        verify(genreRepository).findById(2);
        verify(gameGenreRepository).save(any(GameGenre.class));
    }

    @Test
    void removeGenre_deletesGameGenre()
    {
        when(gameRepository.findById(1))
                .thenReturn(Optional.of(game));

        gameService.removeGenre(1, 2);

        verify(gameGenreRepository)
                .deleteById(any(GameGenreId.class));
    }

    @Test
    void findByGenre_returnsGames()
    {
        List<Game> games = List.of(game);

        when(gameRepository.findByGenreId(2))
                .thenReturn(games);

        List<Game> result = gameService.findByGenre(2);

        assertEquals(games, result);

        verify(gameRepository).findByGenreId(2);
    }

    @Test
    void findByPriceRange_returnsGames()
    {
        List<Game> games = List.of(game);

        when(gameRepository.findByPriceBetween(10, 50))
                .thenReturn(games);

        List<Game> result = gameService.findByPriceRange(10, 50);

        assertEquals(games, result);

        verify(gameRepository)
                .findByPriceBetween(10, 50);
    }

    @Test
    void findByPriceRange_throwsIfInvalidRange()
    {
        assertThrows(IllegalArgumentException.class, () -> gameService.findByPriceRange(50, 10));

        verify(gameRepository, never())
                .findByPriceBetween(anyDouble(), anyDouble());
    }
}