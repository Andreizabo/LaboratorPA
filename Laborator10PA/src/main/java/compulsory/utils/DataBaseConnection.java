package compulsory.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
    private static DataBaseConnection instance;

    private static final String url ="jdbc:oracle:thin:@localhost:1521:orcl";
    private static final String user="C##MEDIC";
    private static final String password="MEDIC";

    private Connection connection;

    private DataBaseConnection() throws SQLException {
        connection = DriverManager.getConnection(url, user, password);
    }

    public static DataBaseConnection getDBInstance() throws SQLException {
        if(instance == null)
            instance = new DataBaseConnection();
        return instance;
    }

    public Connection getConnection(){
        return connection;
    }
}
