package compulsory.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread
{
    private Socket socket;

    public ClientThread(Socket socket)
    {
        this.socket = socket;
    }

    public void run()
    {
        try
        {
            while (true)
            {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                String request = in.readLine();

                if (request == null)
                    break;

                PrintWriter out = new PrintWriter(socket.getOutputStream());
                if (request.equals("stop"))
                {
                    out.println("Server stopped");
                    out.flush();
                    System.exit(0);
                } else
                {
                    String response = "Command " + request;
                    out.println(response);
                    out.flush();
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                socket.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}

