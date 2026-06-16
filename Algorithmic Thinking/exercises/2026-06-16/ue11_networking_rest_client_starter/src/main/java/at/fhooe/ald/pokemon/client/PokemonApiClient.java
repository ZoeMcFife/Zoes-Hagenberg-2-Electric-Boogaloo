package at.fhooe.ald.pokemon.client;

import at.fhooe.ald.pokemon.client.dto.CatchRequest;
import at.fhooe.ald.pokemon.client.dto.CaughtPokemonDto;
import at.fhooe.ald.pokemon.client.dto.PokemonDto;
import at.fhooe.ald.pokemon.client.dto.TrainerDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.PrivateKey;
import java.util.List;
import java.util.Optional;

public class PokemonApiClient
{
    private final String baseUrl;
    private final HttpClient httpClient;
    private final ObjectMapper mapper;

    public PokemonApiClient(String baseUrl)
    {
        this.baseUrl = baseUrl;
        httpClient = HttpClient.newBuilder().build();

        this.mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public List<PokemonDto> listAll()
    {
        return get(baseUrl + "/api/pokemon", new TypeReference<>() {});
    }

    public Optional<PokemonDto> findById(int pokedexNr)
    {
        return get(baseUrl + "/api/pokemon/" + pokedexNr, new TypeReference<>() {});
    }

    public void importPokemon()
    {
        try
        {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/api/pokemon/import"))
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            checkStatus(response, 200);


        }
        catch (IOException | InterruptedException e)
        {
            throw new RuntimeException("import failed", e);
        }
    }

    public List<TrainerDto> listTrainers()
    {
        return get(baseUrl + "/api/trainer", new TypeReference<>() {});
    }

    public TrainerDto addTrainer(String name)
    {
        try
        {
            String body = mapper.writeValueAsString((new TrainerNameRecord(name)));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/api/trainers"))
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            checkStatus(response, 200);

            return mapper.readValue(response.body(), TrainerDto.class);
        }
        catch (IOException | InterruptedException e)
        {
            throw new RuntimeException("add trainer rerror", e);
        }
    }

    public List<CaughtPokemonDto> getRoster()
    {
        return get(baseUrl + "/api/pokemon/roster", new TypeReference<>() {});
    }

    public void catchPokemon(int trainerId, int pokemonNr, String nickname)
    {
        try
        {
            String body = mapper.writeValueAsString(new CatchRequest(pokemonNr, nickname));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/api/trainers/" + trainerId + "/catch"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            checkStatus(response, 201);
        }
        catch (IOException | InterruptedException e)
        {
            throw new RuntimeException("add trainer rerror", e);
        }
    }

    private record TrainerNameRecord(String name) {}


    private <T> T get(String url, TypeReference<T> type)
    {
        try
        {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            checkStatus(response, 200);

            return mapper.readValue(response.body(), type);

        }
        catch (IOException | InterruptedException e)
        {
            throw new RuntimeException("GET " + url + " failed", e);
        }

    }

    private void checkStatus(HttpResponse<String> response, int expected)
    {
        if (response.statusCode() != expected)
        {
            throw new RuntimeException("Expected " + expected + " status code " + response.statusCode());
        }
    }


}
