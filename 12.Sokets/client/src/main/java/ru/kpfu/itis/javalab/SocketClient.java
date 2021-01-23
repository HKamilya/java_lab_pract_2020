package ru.kpfu.itis.javalab;

import java.io.*;
import java.net.Socket;

public class SocketClient {
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private BufferedReader input;
    private String name;


    public SocketClient(String addr, int port) {
        try {
            this.socket = new Socket(addr, port);
        } catch (IOException e) {
            System.err.println("Socket failed :(");
        }
        try {
            input = new BufferedReader(new InputStreamReader(System.in));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.insertName();
            new SocketClient.ReadMsg().start();
            new SocketClient.WriteMsg().start();
        } catch (IOException e) {
            SocketClient.this.downService();
        }
    }

    private void insertName() {
        System.out.print("Enter your name: ");
        try {
            name = input.readLine();
        } catch (IOException ignored) {
        }

    }

    private void downService() {
        try {
            if (!socket.isClosed()) {
                socket.close();
                in.close();
                out.close();
            }
        } catch (IOException ignored) {
        }
    }

    private class ReadMsg extends Thread {
        @Override
        public void run() {

            String str;
            try {
                while (true) {
                    str = in.readLine();
                    if (str.equals("stop")) {
                        SocketClient.this.downService();
                        break;
                    }
                    System.out.println(str);
                }
            } catch (IOException e) {
                SocketClient.this.downService();
            }
        }
    }

    public class WriteMsg extends Thread {

        @Override
        public void run() {
            while (true) {
                String userWord;
                try {
                    userWord = input.readLine();
                    if (userWord.equals("stop")) {
                        out.write("stop" + "\n");
                        SocketClient.this.downService();
                        break;
                    } else {
                        out.write(name + ": " + userWord + "\n");
                    }
                    out.flush();
                } catch (IOException e) {
                    SocketClient.this.downService();

                }
            }
        }
    }
}
