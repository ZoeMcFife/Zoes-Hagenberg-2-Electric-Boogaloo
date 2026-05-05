package at.fhooe.ald.pokemon.data_access.jdbc;

import at.fhooe.ald.pokemon.data_access.PokemonDAO;
import at.fhooe.ald.pokemon.model.Pokemon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcPokemonDAO implements PokemonDAO
{
    private final DataSource dataSource;

    public JdbcPokemonDAO(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }

    protected Connection openConnection() throws SQLException
    {
        return dataSource.getConnection();
    }

    @Override
    public void save(Pokemon pokemon)
    {
        String sql = """
                   INSERT INTO Pokemon (pokedex_number, name, type_1, type_2, hp, attack, defense, speed)
                   VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);
                """;

        try(Connection conn = openConnection())
        {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, pokemon.getPokedexNumber());
            ps.setString(2, pokemon.getName());
            ps.setString(3, pokemon.getType1());
            ps.setString(4, pokemon.getType2());
            ps.setInt(5, pokemon.getHp());
            ps.setInt(6, pokemon.getHp());
            ps.setInt(7, pokemon.getAttack());
            ps.setInt(8, pokemon.getDefense());
            ps.setInt(9, pokemon.getSpeed());
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Could not save Pokemon", e);
        }
    }

    @Override
    public Optional<Pokemon> findByPokedexNumber(int pokedexNumber)
    {
        return Optional.empty();
    }

    @Override
    public List<Pokemon> findAll()
    {
        return List.of();
    }

    @Override
    public void update(Pokemon pokemon)
    {

    }

    @Override
    public void delete(int pokedexNumber)
    {

    }
}
