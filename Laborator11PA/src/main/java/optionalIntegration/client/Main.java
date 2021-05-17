package optionalIntegration.client;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Client client = new Client();
            client.connect();
            while(true) {
                Scanner in = new Scanner(System.in);
                String string = in.nextLine();

                if(string.trim().toLowerCase().equals("exit")) {
                    break;
                }

                if(string.trim().toLowerCase().equals("logout")) {
                    client.setName("not logged in");
                    System.out.println("Logged out");
                    continue;
                }

                if((!string.trim().toLowerCase().startsWith("register") && !string.trim().toLowerCase().startsWith("login")) && client.getName().equals("not logged in")) {
                    System.err.println("You must be logged in");
                    continue;
                }

                if(!string.trim().toLowerCase().startsWith("register") && !string.trim().toLowerCase().startsWith("login")) {
                    string = string.trim();
                    string += " " + client.getName();
                }

                String response = client.sendMessage(string);

                if(response.startsWith("Logged in as")) {
                    client.setName(response.substring(13));
                }

                for(String line : response.split("/socketNewLine/")) {
                    System.out.println(line);
                }

                if (string.trim().toLowerCase().equals("stop"))
                    break;
            }
            client.disconnect();
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
