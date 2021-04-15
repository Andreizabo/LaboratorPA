import oracle.jdbc.driver.OracleDriver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleConnector {
    private static String user = "javalab";
    private static String pass = "javalab";
    private static String url = "jdbc:oracle:thin:@localhost:1521:XE";
    private static Connection connection;

    private OracleConnector() {}

    public void init(String urlArg, String userArg, String passArg) {
        url = urlArg;
        user = userArg;
        pass = passArg;
    }

    public static Connection connectDB() throws SQLException {
        connection = DriverManager.getConnection(url, user, pass);
        return connection;
    }

    public static void disconnectDB() throws SQLException {
        connection.close();
    }
}
