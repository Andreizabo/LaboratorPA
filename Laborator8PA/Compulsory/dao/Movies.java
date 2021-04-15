package dao;

import model.Movie;

import java.sql.*;
import java.time.LocalDate;

public class Movies {
    public static Movie getMovie(Connection connection, String query) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery(query);
        set.next();
        return new Movie(
                set.getInt("id"),
                set.getString("title"),
                LocalDate.parse(set.getString("release_date").split(" ")[0]),
                set.getInt("duration"),
                set.getFloat("score")
        );
    }

    public static Movie getMovie(Connection connection, int id) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery("SELECT * FROM MOVIES WHERE ID = " + id);
        set.next();
        return new Movie(
                set.getInt("id"),
                set.getString("title"),
                LocalDate.parse(set.getString("release_date").split(" ")[0]),
                set.getInt("duration"),
                set.getFloat("score")
        );
    }

    public static void insertMovie(Connection connection, Movie movie) throws SQLException {
        Statement statement = connection.createStatement();
        try {
            ResultSet set = statement.executeQuery(
                    "INSERT INTO MOVIES VALUES(" +
                            movie.getId() + "," +
                            "'" + movie.getTitle() + "'," +
                            "TO_DATE('" + movie.getReleaseDate().toString() + "','YYYY-MM-DD')," +
                            movie.getDuration() + "," +
                            movie.getScore() + ")"
            );
        }
        catch (SQLIntegrityConstraintViolationException e) {
            if(Movies.getMovie(connection, movie.getId()).getTitle().equals(movie.getTitle())) {
                System.err.println("Movie is already in table.");
            }
            else {
                throw e;
            }
        }
    }
}
