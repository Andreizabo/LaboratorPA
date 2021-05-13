package optional.commands;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CommandSend implements Command {
    private String sender;
    private String receiver;
    private String message;
    private Connection conn;

    public CommandSend(String sender, String receiver, String message, Connection conn) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.conn = conn;
    }

    @Override
    public String run() {
        try {
            Statement statement = conn.createStatement();
            ResultSet set = statement.executeQuery(
                    "INSERT INTO NETWORK_MESSAGES VALUES(" + "'" + sender + "','" + receiver + "','" + message + "')"
            );
            set.close();
            statement.close();
            return "succ";
        }
        catch (SQLException e) {
            return "exception";
        }
    }
}
