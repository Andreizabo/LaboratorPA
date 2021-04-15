package dao;

import model.Genre;
import model.Movie;

import java.sql.*;
import java.time.LocalDate;

public class Genres {
    public static Genre getGenre(Connection connection, String query) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery(query);
        set.next();
        return new Genre(
                set.getInt("id"),
                set.getString("name")
        );
    }

    public static Genre getGenre(Connection connection, int id) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery("SELECT * FROM GENRES WHERE ID = " + id);
        set.next();
        return new Genre(
                set.getInt("id"),
                set.getString("name")
        );
    }

    public static void insertGenre(Connection connection, Genre genre) throws SQLException {
        Statement statement = connection.createStatement();
        try {
            ResultSet set = statement.executeQuery(
                    "INSERT INTO GENRES VALUES(" +
                            genre.getId() + "," +
                            "'" + genre.getName() + "')"
            );
        }
        catch (SQLIntegrityConstraintViolationException e) {
            if(Genres.getGenre(connection, genre.getId()).getName().equals(genre.getName())) {
                System.err.println("Object is already in table.");
            }
            else {
                throw e;
            }
        }
    }
}
