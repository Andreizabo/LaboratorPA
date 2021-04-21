package dao;

import model.Genre;
import model.Movie;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MovieGenre {

    public static List<Movie> getMovies(Connection connection, int id) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery("SELECT * FROM MOVIES WHERE ID IN (SELECT ID_MOVIE FROM MOVIE_GENRE WHERE ID_GENRE = " + id + ")");
        List<Movie> movies = new ArrayList<>();
        while(set.next()) {
            movies.add(new Movie(
                set.getInt("id"),
                set.getString("title"),
                LocalDate.parse(set.getString("release_date").split(" ")[0]),
                set.getInt("duration"),
                set.getFloat("score"),
                set.getInt("id_director")
            ));
        }
        set.close();
        return movies;
    }

    public static List<Genre> getGenres(Connection connection, int id) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery("SELECT * FROM GENRES WHERE ID IN (SELECT ID_GENRE FROM MOVIE_GENRE WHERE ID_MOVIE = " + id + ")");
        List<Genre> genres = new ArrayList<>();
        while(set.next()) {
            genres.add(new Genre(
                    set.getInt("id"),
                    set.getString("name")
            ));
        }
        set.close();
        return genres;
    }

    public static void addGenre(Connection connection, int movie_id, int genre_id) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet set = null;
        try {
            set = statement.executeQuery(
                    "INSERT INTO MOVIE_GENRE VALUES(" +
                            movie_id + "," +
                            genre_id + ")"
            );
            set.close();
            statement.close();
        }
        catch (SQLIntegrityConstraintViolationException e) {
            System.err.println("Pair already exists.");
            if(set != null) {
                set.close();
            }
            statement.close();
        }
    }
}
