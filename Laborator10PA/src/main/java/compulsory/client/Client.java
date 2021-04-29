package compulsory.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private String address = "127.0.0.1";
    private int port = 8081;
    private Socket socket;

    public Client(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public Client() {}

    public void connect() throws IOException {
        try {
            socket = new Socket(address, port);
            System.out.println("Connected");
        }
        catch (UnknownHostException e) {
            System.err.println(e.getMessage());
        }
    }

    public String sendMessage(String message) throws IOException {
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        out.println(message);

        return in.readLine();
    }

    public void disconnect() throws IOException {
        socket.close();
    }
}
