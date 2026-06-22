package meow;

import meow.ApiClient.CatApi;
import meow.ApiClient.RestService;
import meow.Dto.CatDto;
import meow.Dto.ImageDto;
import meow.Image.ImageHelper;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Main
{
    static void main()
    {
        RestService rest = new RestService();
        CatApi catApi = new CatApi(rest);


        catApi.getAllBreeds()
                .thenAccept(breeds ->
                {
                    IO.println(breeds.size());

                    breeds.forEach(IO::println);
                }).join();


        CatDto abob = catApi.getBreedById("agdfgfbob").join().orElse(null);

        IO.println(abob.getName());
        IO.println(abob.getWeight());

        ImageDto img = catApi.getImagesOfBreed("abob").join().getFirst();

        ImageHelper.openFromUrl(img.getUrl());

    }
}
