package ru.kpfu.itis.javalab;

public class MainForServer {
    public static void main(String[] args) {
        EchoServerSocket serverSocket = new EchoServerSocket();
        serverSocket.start(7777);
    }
}
