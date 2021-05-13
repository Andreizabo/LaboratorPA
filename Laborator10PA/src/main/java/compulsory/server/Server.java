package compulsory.server;

import compulsory.commands.Command;
import compulsory.commands.CommandLogin;
import compulsory.commands.CommandRegister;
import compulsory.utils.DataBaseConnection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class Server
{
    public static final int PORT = 8081;
    private DataBaseConnection connection;

    public Server() throws IOException, SQLException {
        connection = DataBaseConnection.getDBInstance();

        ServerSocket serverSocket = null;

        try
        {
            serverSocket = new ServerSocket(PORT);
            while (true)
            {
                System.out.println("Waiting for a client");
                Socket socket = serverSocket.accept();
                new ClientThread(socket, connection).start();
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
