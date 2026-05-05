package at.fhooe.ald.pokemon.data_access.jdbc;

import at.fhooe.ald.pokemon.data_access.PokemonDAO;
import at.fhooe.ald.pokemon.model.Pokemon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        String sql =
                """
                   INSERT INTO Pokemon (pokedex_number, name, type_1, type_2, hp, attack, defense, speed)
                   VALUES (?, ?, ?, ?, ?, ?, ?, ?);
                """;

        try(Connection conn = openConnection())
        {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, pokemon.getPokedexNumber());
            ps.setString(2, pokemon.getName());
            ps.setString(3, pokemon.getType1());
            ps.setString(4, pokemon.getType2());
            ps.setInt(5, pokemon.getHp());
            ps.setInt(6, pokemon.getAttack());
            ps.setInt(7, pokemon.getDefense());
            ps.setInt(8, pokemon.getSpeed());
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
        String sql =
                """
                    SELECT * FROM pokemon
                    WHERE pokedex_number = ?;
                """;

        try (Connection conn = openConnection())
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, pokedexNumber);

            try (ResultSet rs = ps.executeQuery())
            {
                return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
            }
            catch (SQLException e)
            {
                throw new SQLException("Could not find Pokemon", e);
            }

        }
        catch (SQLException e)
        {
            throw new RuntimeException("Could not find Pokemon", e);
        }
    }

    @Override
    public List<Pokemon> findAll()
    {
        String sql =
                """
                    SELECT * FROM pokemon
                    ORDER BY pokedex_number;
                """;
        List<Pokemon> pokemons = new ArrayList<>();

        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();)
        {
            while (rs.next())
            {
                pokemons.add(mapRow(rs));
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Could not find Pokemon", e);
        }

        return pokemons;
    }

    @Override
    public void update(Pokemon pokemon)
    {
        String sql =
                """
                   UPDATE Pokemon
                   SET name = ?, type_1 = ?, type_2 = ?, hp = ?, attack = ?,  defense = ?, speed = ?
                   WHERE pokedex_number = ?;
                """;

        try(Connection conn = openConnection())
        {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, pokemon.getName());
            ps.setString(2, pokemon.getType1());
            ps.setString(3, pokemon.getType2());
            ps.setInt(4, pokemon.getHp());
            ps.setInt(5, pokemon.getAttack());
            ps.setInt(6, pokemon.getDefense());
            ps.setInt(7, pokemon.getSpeed());
            ps.setInt(8, pokemon.getPokedexNumber());

            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Could not save Pokemon", e);
        }
    }

    @Override
    public void delete(int pokedexNumber)
    {
        String sql =
                """
                   DELETE FROM pokemon
                   WHERE pokedex_number = ?;
                """;

        try(Connection conn = openConnection())
        {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, pokedexNumber);

            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Could not delete Pokemon", e);
        }
    }


    private Pokemon mapRow(ResultSet rs) throws SQLException
    {
        return new Pokemon
                (
                    rs.getInt("pokedex_number"),
                    rs.getString("name"),
                    rs.getString("type_1"),
                    rs.getString("type_2"),
                    rs.getInt("hp"),
                    rs.getInt("attack"),
                    rs.getInt("defense"),
                    rs.getInt("speed")
                );
    }
}
