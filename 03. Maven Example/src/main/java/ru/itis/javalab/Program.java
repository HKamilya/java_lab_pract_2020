package ru.itis.javalab;

import ru.itis.javalab.images.Downloader;
import com.beust.jcommander.JCommander;

import java.io.IOException;


public class Program {
    public static void main(String[] argv) throws IOException {
        Args args = new Args();

        JCommander.newBuilder()
                .addObject(args)
                .build()
                .parse(argv);

        Downloader downloader = new Downloader();
        downloader.saveImage(args.mode, args.count, args.files, args.folder);

    }
}