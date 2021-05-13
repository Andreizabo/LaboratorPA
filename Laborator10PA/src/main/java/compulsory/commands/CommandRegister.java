package compulsory.commands;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CommandRegister implements Command {

    private String name;
    private Connection conn;

    public CommandRegister(String name, Connection conn) {
        this.name = name;
        this.conn = conn;
    }

    @Override
    public String run() {
        try {
            Statement statement = conn.createStatement();
            ResultSet set = statement.executeQuery(
                    "INSERT INTO NETWORK_USERS VALUES(" + "'" + name + "')"
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
