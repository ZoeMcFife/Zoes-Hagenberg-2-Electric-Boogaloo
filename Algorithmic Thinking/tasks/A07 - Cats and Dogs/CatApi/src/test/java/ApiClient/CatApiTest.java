package ApiClient;

import com.github.tomakehurst.wiremock.WireMockServer;
import meow.ApiClient.CatApi;
import meow.ApiClient.Config;
import meow.ApiClient.RestService;
import meow.Dto.CatDto;
import meow.Dto.WeightDto;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    public void testGetBreedById()
    {
        wireMock.stubFor(get(urlEqualTo("/breeds/abob"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("abob.json")));

        CatDto cat = api.getBreedById("abob").join().orElse(null);

        CatDto abob = new CatDto();
        abob.setId("abob");
        abob.setName("American Bobtail");
        abob.setDescription("American Bobtails are loving and incredibly intelligent cats possessing a distinctive wild appearance. They are extremely interactive cats that bond with their human family with great devotion.");
        abob.setOrigin("United States");
        abob.setLifeSpan("11 - 15");
        abob.setTemperament("Intelligent, Interactive, Lively, Playful, Sensitive");
        WeightDto weightDto = new WeightDto();
        weightDto.setImperial("7 - 16");
        weightDto.setMetric("3 - 7");
        abob.setWeight(weightDto);

        IO.println(abob.getLifeSpan());
        IO.println(cat.getLifeSpan());

        assertEquals(abob, cat);
    }
}
