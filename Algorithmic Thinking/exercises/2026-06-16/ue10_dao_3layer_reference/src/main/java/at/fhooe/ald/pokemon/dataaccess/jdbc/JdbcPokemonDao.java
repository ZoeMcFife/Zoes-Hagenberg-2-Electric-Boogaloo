package at.fhooe.ald.pokemon.dataaccess.jdbc;

import at.fhooe.ald.pokemon.dataaccess.PokemonDao;
import at.fhooe.ald.pokemon.model.Pokemon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcPokemonDao implements PokemonDao {

    @Autowired
    private DataSource dataSource;

    protected Connection openConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public void save(Pokemon p) {
        String sql = """
                INSERT INTO pokemon (pokedex_number, name, type1, type2, hp, attack, defense, speed)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;
        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, p.getPokedexNumber());
            ps.setString(2, p.getName());
            ps.setString(3, p.getType1());
            ps.setString(4, p.getType2());
            ps.setInt(5, p.getHp());
            ps.setInt(6, p.getAttack());
            ps.setInt(7, p.getDefense());
            ps.setInt(8, p.getSpeed());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("save() failed", e);
        }
    }

    @Override
    public Optional<Pokemon> findById(int pokedexNumber) {
        String sql = "SELECT * FROM pokemon WHERE pokedex_number = ?";
        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pokedexNumber);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("findById() failed", e);
        }
    }

    @Override
    public List<Pokemon> findAll() {
        String sql = "SELECT * FROM pokemon ORDER BY pokedex_number";
        List<Pokemon> result = new ArrayList<>();
        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("findAll() failed", e);
        }
        return result;
    }

    @Override
    public void update(Pokemon p) {
        String sql = """
                UPDATE pokemon
                SET name=?, type1=?, type2=?, hp=?, attack=?, defense=?, speed=?
                WHERE pokedex_number=?
                """;
        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getName());
            ps.setString(2, p.getType1());
            ps.setString(3, p.getType2());
            ps.setInt(4, p.getHp());
            ps.setInt(5, p.getAttack());
            ps.setInt(6, p.getDefense());
            ps.setInt(7, p.getSpeed());
            ps.setInt(8, p.getPokedexNumber());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("update() failed", e);
        }
    }

    @Override
    public void delete(int pokedexNumber) {
        String sql = "DELETE FROM pokemon WHERE pokedex_number = ?";
        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pokedexNumber);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("delete() failed", e);
        }
    }

    private Pokemon mapRow(ResultSet rs) throws SQLException {
        return new Pokemon(
                rs.getInt("pokedex_number"),
                rs.getString("name"),
                rs.getString("type1"),
                rs.getString("type2"),
                rs.getInt("hp"),
                rs.getInt("attack"),
                rs.getInt("defense"),
                rs.getInt("speed")
        );
    }
}
