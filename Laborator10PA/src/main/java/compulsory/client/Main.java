package compulsory.client;

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

                if(string.toLowerCase().equals("exit")) {
                    break;
                }

                System.out.println(client.sendMessage(string));

                if (string.toLowerCase().equals("stop"))
                    break;
            }
            client.disconnect();
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
