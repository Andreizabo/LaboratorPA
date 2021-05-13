package compulsory.commands;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CommandRead implements Command {
    private String name;
    private Connection conn;

    public CommandRead(String name, Connection conn) {
        this.name = name;
        this.conn = conn;
    }

    @Override
    public String run() {
        try {
            Statement statement = conn.createStatement();
            ResultSet set = statement.executeQuery("SELECT NUME_1, MESSAGE FROM NETWORK_MESSAGES WHERE NUME_2 = '" + name + "'");
            StringBuilder messages = new StringBuilder();
            while(set.next()) {
                messages.append(set.getString("NUME_1")).append(" said:\n").append(set.getString("MESSAGE")).append("\n\n");
            }
            set.close();
            return messages.toString();
        }
        catch (SQLException e) {
            return "exception";
        }
    }
}
