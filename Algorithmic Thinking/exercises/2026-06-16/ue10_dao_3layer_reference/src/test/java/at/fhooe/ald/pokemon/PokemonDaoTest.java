package at.fhooe.ald.pokemon;

import at.fhooe.ald.pokemon.dataaccess.jdbc.JdbcPokemonDao;
import at.fhooe.ald.pokemon.model.Pokemon;
import at.fhooe.ald.pokemon.dataaccess.PokemonDao;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PokemonDaoTest {

    // H2 in MySQL-compatibility mode for JdbcPokemonDao tests
    private static final String H2_URL  = "jdbc:h2:mem:testdb;MODE=MySQL;DB_CLOSE_DELAY=-1";
    private static final String H2_USER = "sa";
    private static final String H2_PASS = "";

    private PokemonDao pokemonDao;

    @BeforeAll
    static void createSchema() throws Exception {
        try (Connection conn = DriverManager.getConnection(H2_URL, H2_USER, H2_PASS);
             Statement st = conn.createStatement()) {
            st.execute("""
                    CREATE TABLE IF NOT EXISTS pokemon (
                        pokedex_number INT PRIMARY KEY,
                        name     VARCHAR(100) NOT NULL,
                        type1    VARCHAR(50)  NOT NULL,
                        type2    VARCHAR(50),
                        hp       INT          NOT NULL,
                        attack   INT          NOT NULL,
                        defense  INT          NOT NULL,
                        speed    INT          NOT NULL
                    )""");
        }
    }

    @BeforeEach
    void setUp() throws Exception {
        pokemonDao = new JdbcPokemonDaoTestable(H2_URL, H2_USER, H2_PASS);
        try (Connection conn = DriverManager.getConnection(H2_URL, H2_USER, H2_PASS);
             Statement st = conn.createStatement()) {
            st.execute("DELETE FROM pokemon");
        }
    }

    @Test
    void save_and_findById() {
        Pokemon p = new Pokemon(25, "Pikachu", "electric", null, 35, 55, 40, 90);
        pokemonDao.save(p);
        Optional<Pokemon> found = pokemonDao.findById(25);
        assertTrue(found.isPresent());
        assertEquals("Pikachu", found.get().getName());
        assertEquals("electric", found.get().getType1());
        assertNull(found.get().getType2());
    }

    @Test
    void findAll_returnsAllSaved() {
        pokemonDao.save(new Pokemon(1, "Bulbasaur", "grass", "poison", 45, 49, 49, 45));
        pokemonDao.save(new Pokemon(4, "Charmander", "fire", null, 39, 52, 43, 65));
        List<Pokemon> all = pokemonDao.findAll();
        assertEquals(2, all.size());
        assertEquals(1, all.get(0).getPokedexNumber());
    }

    @Test
    void update_changesStats() {
        pokemonDao.save(new Pokemon(7, "Squirtle", "water", null, 44, 48, 65, 43));
        Pokemon changed = new Pokemon(7, "Squirtle", "water", null, 44, 48, 65, 99);
        pokemonDao.update(changed);
        assertEquals(99, pokemonDao.findById(7).get().getSpeed());
    }

    @Test
    void delete_removesRow() {
        pokemonDao.save(new Pokemon(94, "Gengar", "ghost", "poison", 60, 65, 60, 110));
        pokemonDao.delete(94);
        assertTrue(pokemonDao.findById(94).isEmpty());
    }

    @Test
    void findById_notFound_returnsEmpty() {
        assertTrue(pokemonDao.findById(999).isEmpty());
    }

    // -----------------------------------------------------------------------
    // Testable subclass that provides a dedicated H2 connection
    // -----------------------------------------------------------------------

    static class JdbcPokemonDaoTestable extends JdbcPokemonDao {
        private final String url;
        private final String user;
        private final String pass;

        JdbcPokemonDaoTestable(String url, String user, String pass) {
            this.url  = url;
            this.user = user;
            this.pass = pass;
        }

        @Override
        protected Connection openConnection() throws SQLException {
            return DriverManager.getConnection(url, user, pass);
        }
    }
}
