package meow.ApiClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class RestService
{
    private final HttpClient httpClient;
    private final ObjectMapper mapper;

    public RestService()
    {
        httpClient = HttpClient.newBuilder().build();

        mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public <T> CompletableFuture<Optional<T>> get(String url, TypeReference<T> type)
    {
        return get(url, type, 404);
    }


    public <T> CompletableFuture<Optional<T>> get(String url, TypeReference<T> type, int extraEmptyOnFailureCode)
    {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response ->
                {
                    if (response.statusCode() == 404)
                        return Optional.empty();

                    if (response.statusCode() == extraEmptyOnFailureCode)
                        return Optional.empty();

                    if (response.statusCode() != 200)
                        throw new RuntimeException("GET " + url + " failed!");

                    try
                    {
                        return Optional.of(mapper.readValue(response.body(), type));
                    }
                    catch (JsonProcessingException e)
                    {
                        throw new RuntimeException(e);
                    }
                });
    }

    public CompletableFuture<Optional<byte[]>> getImageByteArray(String url)
    {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofByteArray())
                        .thenApply(response ->
                                {
                                    if (response.statusCode() == 404)
                                        return Optional.empty();

                                    if (response.statusCode() != 200)
                                        throw new RuntimeException("GET " + url + " failed!");

                                    return Optional.of(response.body());
                                }
                        );
    }
}
