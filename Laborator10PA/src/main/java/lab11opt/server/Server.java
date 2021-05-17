package lab11opt.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class Server
{
    public static final int PORT = 8081;

    public Server() throws IOException, SQLException {
        ServerSocket serverSocket = null;

        try
        {
            serverSocket = new ServerSocket(PORT);
            while (true)
            {
                System.out.println("Waiting for a client");
                Socket socket = serverSocket.accept();
                new ClientThread(socket).start();
            }
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        finally
        {
            if (serverSocket != null)
                serverSocket.close();
        }
    }
}
