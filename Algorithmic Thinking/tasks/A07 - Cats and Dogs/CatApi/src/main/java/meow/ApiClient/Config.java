package meow.ApiClient;

public class Config
{
    public static String BASE_URL = "https://api.thecatapi.com/v1";

    public static String getBreedEndpointUrl()
    {
        return BASE_URL + "/breeds";
    }

    public static String getImageEndpointUrl()
    {
        return BASE_URL + "/images";
    }
}
