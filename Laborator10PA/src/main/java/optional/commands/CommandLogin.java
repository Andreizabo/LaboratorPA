package optional.commands;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CommandLogin implements Command {
    private String name;
    private Connection conn;

    public CommandLogin(String name, Connection conn) {
        this.name = name;
        this.conn = conn;
    }

    @Override
    public String run() {
        try {
            Statement statement = conn.createStatement();
            ResultSet set = statement.executeQuery("SELECT COUNT(*) FROM NETWORK_USERS WHERE nume = '" + name + "'");
            set.next();
            if(set.getInt("COUNT(*)") != 1) {
                set.close();
                return "not_exist";
            }
            set.close();
            return "succ";
        }
        catch (SQLException e) {
            return "exception";
        }
    }
}
