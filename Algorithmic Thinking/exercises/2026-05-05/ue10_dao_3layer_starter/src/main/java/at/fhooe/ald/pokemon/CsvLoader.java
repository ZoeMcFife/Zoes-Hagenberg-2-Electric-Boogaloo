package at.fhooe.ald.pokemon;

import at.fhooe.ald.pokemon.model.Pokemon;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvLoader {

    public List<Pokemon> load(String resourcePath) {
        List<Pokemon> result = new ArrayList<>();
        try (InputStream in = CsvLoader.class.getResourceAsStream(resourcePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            reader.readLine(); // skip header line
            String line;
            while ((line = reader.readLine()) != null) {
                String[] col = line.split(",", -1);
                int number   = Integer.parseInt(col[0].trim());
                String name  = col[1].trim();
                String type1 = col[2].trim();
                String type2 = col[3].trim().isEmpty() ? null : col[3].trim();
                int hp       = Integer.parseInt(col[4].trim());
                int attack   = Integer.parseInt(col[5].trim());
                int defense  = Integer.parseInt(col[6].trim());
                int speed    = Integer.parseInt(col[7].trim());
                result.add(new Pokemon(number, name, type1, type2, hp, attack, defense, speed));
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load CSV: " + resourcePath, e);
        }
        return result;
    }
}
