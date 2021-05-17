package lab11opt.commands;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CommandLogin implements Command {
    private String name;

    public CommandLogin(String name) {
        this.name = name;
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
