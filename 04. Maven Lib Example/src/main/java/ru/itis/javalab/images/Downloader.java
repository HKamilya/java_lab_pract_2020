package ru.itis.javalab.images;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Downloader {

    public void saveImage(String mode, int count, String imageUrl, String destinationFile) throws IOException, IOException {
        if (mode.equals("one-thread")) {
            count = 1;
        }
        ExecutorService executorService = Executors.newFixedThreadPool(count);
        String[] urls = imageUrl.split(";");
        for (int i = 0; i < urls.length; i++) {
            int finalI = i;
            Runnable task = () -> {
                try (InputStream in = new URL(urls[finalI]).openStream()) {
                    Files.copy(in, Paths.get(destinationFile + "\\" + finalI + ".jpg"));
                    System.out.println(urls[finalI]);
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            };
            executorService.submit(task);

        }

    }
}
