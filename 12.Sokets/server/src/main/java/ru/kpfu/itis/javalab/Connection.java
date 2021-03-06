package ru.kpfu.itis.javalab;

import java.io.*;
import java.net.Socket;

public class Connection extends Thread {
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;


    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        start();
    }

    @Override
    public void run() {
        String word;
        try {
            word = in.readLine();
            try {
                out.write(word + "\n");
                out.flush();
            } catch (IOException ignored) {
            }
            try {
                while (true) {
                    word = in.readLine();
                    System.out.println("Echo: " + word);
                    for (Connection connection : EchoServerSocket.serverList) {
                        if (connection != this) {
                            connection.send(word);
                        }
                    }
                }
            } catch (NullPointerException ignored) {
            }


        } catch (IOException e) {
            this.downService();
        }
    }

    private void send(String msg) {
        try {
            out.write(msg + "\n");
            out.flush();
        } catch (IOException ignored) {
        }

    }

    private void downService() {
        try {
            if (!socket.isClosed()) {
                socket.close();
                in.close();
                out.close();
                for (Connection con : EchoServerSocket.serverList) {
                    if (con.equals(this)) {
                        con.interrupt();
                    }
                    EchoServerSocket.serverList.remove(this);
                }
            }
        } catch (IOException ignored) {
        }
    }
}
