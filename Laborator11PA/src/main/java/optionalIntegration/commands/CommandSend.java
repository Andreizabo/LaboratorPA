package optionalIntegration.commands;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CommandSend implements Command {
    private String sender;
    private String receiver;
    private String message;

    public CommandSend(String sender, String receiver, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    @Override
    public String run() {
//        try {
//            Statement statement = conn.createStatement();
//            ResultSet set = statement.executeQuery(
//                    "INSERT INTO NETWORK_MESSAGES VALUES(" + "'" + sender + "','" + receiver + "','" + message + "')"
//            );
//            set.close();
//            statement.close();
//            return "succ";
//        }
//        catch (SQLException e) {
//            return "exception";
//        }
        return "a";
    }
}
