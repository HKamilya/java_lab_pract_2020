package ru.kpfu.itis.javalab.homework;

import com.beust.jcommander.JCommander;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class EchoServerSocket {
    public static LinkedList<Connection> serverList = new LinkedList<>();


    public static void main(String[] argv) throws IOException {
        ServerArgs args = new ServerArgs();
        JCommander.newBuilder()
                .addObject(args)
                .build()
                .parse(argv);
        ServerSocket server = new ServerSocket(args.port);

        System.out.println("Server Started");
        try {
            while (true) {
                Socket socket = server.accept();
                try {
                    serverList.add(new Connection(socket));
                } catch (IOException e) {
                    socket.close();
                }
            }
        } finally {
            server.close();
        }
    }
}
