package ApiClient;

import com.github.tomakehurst.wiremock.WireMockServer;
import meow.ApiClient.CatApi;
import meow.ApiClient.Config;
import meow.ApiClient.RestService;
import meow.Dto.CatDto;
import meow.Dto.ImageDto;
import meow.Dto.WeightDto;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CatApiTest
{
    private static WireMockServer wireMock;

    private CatApi api;

    @BeforeAll
    static void setupServer()
    {
        wireMock = new WireMockServer(0);
        wireMock.start();
    }

    @AfterAll
    static void stopServer()
    {
        wireMock.stop();
    }

    @BeforeEach
    void setup()
    {
        wireMock.resetAll();

        RestService service = new RestService();

        Config.setBaseUrl("http://localhost:" + wireMock.port());

        api = new CatApi(service);
    }

    @Test
    public void testGetAllBreeds()
    {
        wireMock.stubFor(get(urlEqualTo("/breeds"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("breeds.json")));

        List<CatDto> result = api.getAllBreeds().join();

        assertEquals(67, result.size());
    }

    public static CatDto expectedAbob()
    {
        CatDto cat = new CatDto();
        cat.setId("abob");
        cat.setName("American Bobtail");
        cat.setDescription("American Bobtails are loving and incredibly intelligent cats possessing a distinctive wild appearance. They are extremely interactive cats that bond with their human family with great devotion.");
        cat.setOrigin("United States");
        cat.setLifeSpan("11 - 15");

        cat.setTemperament("Intelligent, Interactive, Lively, Playful, Sensitive");

        WeightDto weight = new WeightDto();
        weight.setImperial("7 - 16");
        weight.setMetric("3 - 7");

        cat.setWeight(weight);

        return cat;
    }

    @Test
    public void testGetBreedById()
    {
        wireMock.stubFor(get(urlEqualTo("/breeds/abob"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("abob.json")));

        CatDto cat = api.getBreedById("abob").join().orElse(null);

        assertNotNull(cat);

        assertEquals("abob", cat.getId());
        assertEquals("American Bobtail", cat.getName());
        assertEquals("American Bobtails are loving and incredibly intelligent cats possessing a distinctive wild appearance. They are extremely interactive cats that bond with their human family with great devotion.", cat.getDescription());
        assertEquals("United States", cat.getOrigin());
        assertEquals("11 - 15", cat.getLifeSpan());
        assertEquals(List.of("Intelligent", "Interactive", "Lively", "Playful", "Sensitive"), cat.getTemperament());

        assertNotNull(cat.getWeight());
        assertEquals("7 - 16", cat.getWeight().getImperial());
        assertEquals("3 - 7", cat.getWeight().getMetric());
    }

    @Test
    public void testGetImagesOfBreed()
    {
        wireMock.stubFor(get(urlEqualTo("/images/search?breed_ids=munc"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("search_munc.json")));

        List<ImageDto> images = api.getImagesOfBreed("munc").join();

        assertNotNull(images);
        assertEquals(1, images.size());

        ImageDto image = images.getFirst();
        assertEquals("mpcBi2Utm", image.getId());
        assertEquals("https://cdn2.thecatapi.com/images/mpcBi2Utm.jpg", image.getUrl());
    }
}
