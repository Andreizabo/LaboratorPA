package manip;

import oracle.jdbc.driver.OracleDriver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleConnector {
    private static String user = "C##MEDIC";
    private static String pass = "MEDIC";
    private static String url = "jdbc:oracle:thin:@localhost:1521:orcl";
    private static Connection connection;

    private OracleConnector() {}

    public void init(String urlArg, String userArg, String passArg) {
        url = urlArg;
        user = userArg;
        pass = passArg;
    }

    public static Connection connectDB() throws SQLException {
        connection = DriverManager.getConnection(url, user, pass);
        connection.setAutoCommit(false);
        return connection;
    }

    public static void commit() throws SQLException {
        connection.commit();
    }

    public static void disconnectDB() throws SQLException {
        connection.close();
    }
}
