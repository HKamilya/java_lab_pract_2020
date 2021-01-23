package ru.kpfu.itis.javalab;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class ClientArgs {

    @Parameter(names = {"--server-port"})
    public int port;

    @Parameter(names = {"--server-ip"})
    public String serverIp;

}
