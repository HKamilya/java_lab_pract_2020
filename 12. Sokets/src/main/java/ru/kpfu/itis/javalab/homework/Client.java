package ru.kpfu.itis.javalab.homework;

import com.beust.jcommander.JCommander;

public class Client {



    public static void main(String[] argv) {
        ClientArgs args = new ClientArgs();
        JCommander.newBuilder()
                .addObject(args)
                .build()
                .parse(argv);
        new SocketClient(args.serverIp, args.port);
    }
}
