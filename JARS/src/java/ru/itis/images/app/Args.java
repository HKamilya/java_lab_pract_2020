package ru.itis.images.app;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import java.util.ArrayList;
import java.util.List;

@Parameters(separators = "=")
public class Args {

    @Parameter(names = {"--mode"})
    public String mode;

    @Parameter(names = {"--count"})
    public String count;

    @Parameter(names = {"--files"})
    public String files;


    @Parameter(names = {"--folder"})
    public String folder;
}
