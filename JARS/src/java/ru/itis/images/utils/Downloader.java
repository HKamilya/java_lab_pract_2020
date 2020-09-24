package ru.itis.images.utils;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Downloader {

    public void saveImage(String mode, String count, String imageUrl, String destinationFile) throws IOException, IOException {
        if (mode.equals("one-thread")) {
            count = "1";
        }
        ExecutorService executorService = Executors.newFixedThreadPool(Integer.parseInt(count));
        String[] urls = imageUrl.split(";");
        for (int i = 0; i < urls.length; i++) {
            System.out.println(urls[i]);
            int finalI = i;
            Runnable task = () -> {
                try {
                    URL url = new URL(urls[finalI]);
                    BufferedImage img = ImageIO.read(url);
                    File file = new File(destinationFile + "\\dest" + finalI + ".jpg");
                    ImageIO.write(img, "jpg", file);
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }

            };
            executorService.submit(task);

        }

    }
}
