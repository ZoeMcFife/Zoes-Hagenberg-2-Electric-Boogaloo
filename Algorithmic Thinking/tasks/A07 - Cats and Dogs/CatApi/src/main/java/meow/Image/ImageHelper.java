package meow.Image;

import meow.ApiClient.RestService;
import org.apache.commons.io.FilenameUtils;

import java.awt.*;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;

public class ImageHelper
{
    public static void openFromUrl(String path)
    {
        RestService restService = new RestService();

        byte[] imageData = restService.getImageByteArray(path).join().orElse(null);

        if (imageData == null)
        {
            IO.println("No image found!");
            return;
        }

        try
        {
            File temp = File.createTempFile("image_", "." + getFileExtensionFromPath(path));

            temp.deleteOnExit();

            Files.write(temp.toPath(), imageData);

            Desktop.getDesktop().open(temp);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private static String getFileExtensionFromPath(String path)
    {
        return FilenameUtils.getExtension(path);
    }
}
