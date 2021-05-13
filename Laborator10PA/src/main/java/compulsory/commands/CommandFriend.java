package compulsory.commands;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CommandFriend implements Command {
    private String friend1;
    private String friend2;
    private Connection conn;

    public CommandFriend(String friend1, String friend2, Connection conn) {
        this.friend1 = friend1;
        this.friend2 = friend2;
        this.conn = conn;
    }

    @Override
    public String run() {
        try {
            Statement statement = conn.createStatement();
            ResultSet set = statement.executeQuery("SELECT COUNT(*) FROM NETWORK_RELATIONS WHERE nume_1 = '" + friend1 + "' AND nume_2 = '" + friend2 + "'");
            set.next();
            if(set.getInt("COUNT(*)") > 0) {
                set.close();
                return "already";
            }
            set.close();

            statement = conn.createStatement();
            set = statement.executeQuery(
                    "INSERT INTO NETWORK_RELATIONS VALUES(" + "'" + friend1 + "', '" + friend2 + "')"
            );
            set.close();
            statement.close();

            statement = conn.createStatement();
            set = statement.executeQuery(
                    "INSERT INTO NETWORK_RELATIONS VALUES(" + "'" + friend2 + "', '" + friend1 + "')"
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
