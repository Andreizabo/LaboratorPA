package dao;

import model.Actor;
import model.Director;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Directors {
    public static Director getDirector(Connection connection, String query) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery(query);
        set.next();
        Director director = new Director(
                set.getInt("id"),
                set.getString("name"),
                set.getInt("age")
        );
        set.close();
        return director;
    }

    public static Director getDirector(Connection connection, int id) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery("SELECT * FROM Directors WHERE ID = " + id);
        set.next();
        Director director = new Director(
                set.getInt("id"),
                set.getString("name"),
                set.getInt("age")
        );
        set.close();
        return director;
    }

    public static int insertDirector(Connection connection, Director director) throws SQLException {
        Statement statement = connection.createStatement();
        try {
            Statement exists = connection.createStatement();
            ResultSet setEx = exists.executeQuery("SELECT id FROM DIRECTORS WHERE NAME LIKE '" + director.getName() + "'");
            if(setEx.next()) {
                int id = setEx.getInt("id");
                setEx.close();
                exists.close();
                return id;
            }
            setEx.close();
            exists.close();
            if(director.getId() == -1) {
                Statement getAnId = connection.createStatement();
                ResultSet set =  getAnId.executeQuery("SELECT COUNT(*) FROM Directors");
                set.next();
                director.setId(set.getInt("COUNT(*)") + 1);
                set.close();
                statement.close();
            }
            ResultSet set = statement.executeQuery(
                    "INSERT INTO Directors VALUES(" +
                            director.getId() + "," +
                            "'" + director.getName() + "'," +
                            director.getAge() + ")"
            );
            set.close();
            statement.close();
        }
        catch (SQLIntegrityConstraintViolationException e) {
            if(Directors.getDirector(connection, director.getId()).getName().equals(director.getName())) {
                System.err.println("Object is already in table.");
                return director.getId();
            }
            else {
                throw e;
            }
        }
        return director.getId();
    }

    public static List<Director> getAllDirectors(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery("SELECT * FROM Directors");
        if(set.getFetchSize() < 1) {
            return null;
        }
        List<Director> directors = new ArrayList<>();

        while (set.next()) {
            directors.add(new Director(
                    set.getInt("id"),
                    set.getString("name"),
                    set.getInt("age")
            ));
        }
        set.close();
        return directors;
    }
}
