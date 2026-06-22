package meow.ApiClient;

import com.fasterxml.jackson.core.type.TypeReference;
import meow.Dto.CatDto;
import meow.Dto.ImageDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class CatApi
{
    private RestService service;

    public CatApi(RestService service)
    {
        this.service = service;
    }

    public CompletableFuture<List<CatDto>> getAllBreeds()
    {
        return service.get(Config.getBreedEndpointUrl(), new TypeReference<List<CatDto>>() {})
                .thenApply(opt -> opt.orElseGet(List::of));
    }

    public CompletableFuture<Optional<CatDto>> getBreedById(String id)
    {
        return service.get(Config.getBreedEndpointUrl() + "/" + id, new TypeReference<>() {}, 400);
    }

    // without an api key, image endpoint returns an array with one image..., kinda annoying tbh
    public CompletableFuture<List<ImageDto>> getImagesOfBreed(String id)
    {
        return service.get(Config.getImageEndpointUrl() + "/search?breed_ids=" + id, new TypeReference<List<ImageDto>>() {})
                .thenApply(opt -> opt.orElseGet(List::of));
    }

}
