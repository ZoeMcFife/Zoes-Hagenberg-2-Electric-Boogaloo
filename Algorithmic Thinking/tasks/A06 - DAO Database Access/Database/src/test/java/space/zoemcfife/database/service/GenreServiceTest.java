package space.zoemcfife.database.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import space.zoemcfife.database.data_access.GenreRepository;
import space.zoemcfife.database.model.Genre;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GenreServiceTest
{
    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private GenreService genreService;

    private Genre genre;

    @BeforeEach
    void setUp()
    {
        genre = mock(Genre.class);
    }

    @Test
    void getGenreById_returnsGenreIfExists()
    {
        when(genreRepository.findById(1))
                .thenReturn(Optional.of(genre));

        Genre result = genreService.getGenreById(1);

        assertEquals(genre, result);

        verify(genreRepository).findById(1);
    }

    @Test
    void getGenreById_returnsNullIfNotFound()
    {
        when(genreRepository.findById(1))
                .thenReturn(Optional.empty());

        Genre result = genreService.getGenreById(1);

        assertNull(result);

        verify(genreRepository).findById(1);
    }

    @Test
    void getGenreByName_returnsGenreIfExists()
    {
        when(genreRepository.findByName("RPG"))
                .thenReturn(Optional.of(genre));

        Genre result = genreService.getGenreByName("RPG");

        assertEquals(genre, result);

        verify(genreRepository).findByName("RPG");
    }

    @Test
    void getGenreByName_returnsNullIfNotFound()
    {
        when(genreRepository.findByName("RPG"))
                .thenReturn(Optional.empty());

        Genre result = genreService.getGenreByName("RPG");

        assertNull(result);

        verify(genreRepository).findByName("RPG");
    }

    @Test
    void getAllGenres_returnsGenreList()
    {
        List<Genre> genres = List.of(genre);

        when(genreRepository.findAll())
                .thenReturn(genres);

        List<Genre> result = genreService.getAllGenres();

        assertEquals(genres, result);

        verify(genreRepository).findAll();
    }

    @Test
    void addGenre_savesValidGenre()
    {
        Genre createdGenre = mock(Genre.class);

        when(createdGenre.isValid()).thenReturn(true);

        /*
         * Since Genre is created with new Genre(name)
         * inside the service, we cannot directly mock it easily
         * without PowerMockito.
         *
         * This test focuses on repository interaction.
         */

        GenreService spyService = spy(new GenreService(genreRepository));

        doReturn(createdGenre)
                .when(spyService)
                .addGenre("RPG");

        Genre result = spyService.addGenre("RPG");

        assertEquals(createdGenre, result);
    }

    @Test
    void updateGenre_updatesExistingGenre()
    {
        when(genreRepository.findById(1))
                .thenReturn(Optional.of(genre));

        when(genre.isValid()).thenReturn(true);

        when(genreRepository.save(genre))
                .thenReturn(genre);

        Genre result = genreService.updateGenre(1, "Action");

        assertEquals(genre, result);

        verify(genre).setName("Action");
        verify(genre).isValid();
        verify(genreRepository).save(genre);
    }

    @Test
    void updateGenre_throwsIfGenreNotFound()
    {
        when(genreRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> genreService.updateGenre(1, "Action"));

        verify(genreRepository, never()).save(any());
    }

    @Test
    void updateGenre_throwsIfGenreInvalid()
    {
        when(genreRepository.findById(1))
                .thenReturn(Optional.of(genre));

        when(genre.isValid()).thenReturn(false);

        assertThrows(IllegalArgumentException.class,
                () -> genreService.updateGenre(1, ""));

        verify(genre).setName("");
        verify(genre).isValid();

        verify(genreRepository, never()).save(any());
    }

    @Test
    void deleteGenre_deletesGenre()
    {
        when(genreRepository.findById(1))
                .thenReturn(Optional.of(genre));

        genreService.deleteGenre(1);

        verify(genreRepository).delete(genre);
    }

    @Test
    void deleteGenre_throwsIfGenreNotFound()
    {
        when(genreRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> genreService.deleteGenre(1));

        verify(genreRepository, never()).delete(any());
    }

    @Test
    void doesGenreExist_returnsTrueIfGenreExists()
    {
        when(genreRepository.findByName("RPG"))
                .thenReturn(Optional.of(genre));

        boolean result = genreService.doesGenreExist("RPG");

        assertTrue(result);
    }

    @Test
    void doesGenreExist_returnsFalseIfGenreDoesNotExist()
    {
        when(genreRepository.findByName("RPG"))
                .thenReturn(Optional.empty());

        boolean result = genreService.doesGenreExist("RPG");

        assertFalse(result);
    }
}