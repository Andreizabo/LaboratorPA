package dao;

import model.Movie;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Movies {
    public static Movie getMovie(Connection connection, String query) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery(query);
        set.next();
        Movie movie = new Movie(
                set.getInt("id"),
                set.getString("title"),
                LocalDate.parse(set.getString("release_date").split(" ")[0]),
                set.getInt("duration"),
                set.getFloat("score"),
                set.getInt("id_director")
        );
        set.close();
        return movie;
    }

    public static Movie getMovie(Connection connection, int id) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery("SELECT * FROM MOVIES WHERE ID = " + id);
        if(set.getFetchSize() < 1) {
            return null;
        }
        set.next();
        Movie movie = new Movie(
                set.getInt("id"),
                set.getString("title"),
                LocalDate.parse(set.getString("release_date").split(" ")[0]),
                set.getInt("duration"),
                set.getFloat("score"),
                set.getInt("id_director")
        );
        set.close();
        return movie;
    }

    public static List<Movie> getAllMovies(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery("SELECT * FROM MOVIES");
        if(set.getFetchSize() < 1) {
            return null;
        }
        List<Movie> movies = new ArrayList<>();

        while (set.next()) {
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

    public static int insertMovie(Connection connection, Movie movie) throws SQLException {
        Statement statement = connection.createStatement();
        try {
            Statement exists = connection.createStatement();
            ResultSet setEx = exists.executeQuery("SELECT id FROM MOVIES WHERE TITLE LIKE '" + movie.getTitle() + "'");
            if(setEx.next()) {
                int id = setEx.getInt("id");
                setEx.close();
                exists.close();
                return id;
            }
            setEx.close();
            exists.close();
            if(movie.getId() == -1) {
                Statement getAnId = connection.createStatement();
                ResultSet set =  getAnId.executeQuery("SELECT COUNT(*) FROM Movies");
                set.next();
                movie.setId(set.getInt("COUNT(*)") + 1);
                set.close();
                statement.close();
            }
            ResultSet set = statement.executeQuery(
                    "INSERT INTO MOVIES VALUES(" +
                            movie.getId() + "," +
                            "'" + movie.getTitle() + "'," +
                            "TO_DATE('" + movie.getReleaseDate().toString() + "','YYYY-MM-DD')," +
                            movie.getDuration() + "," +
                            movie.getScore() + "," +
                            movie.getDirectorId() + ")"
            );
            set.close();
            statement.close();
        }
        catch (SQLIntegrityConstraintViolationException e) {
            Movie mv = Movies.getMovie(connection, movie.getId());
            if(mv != null && mv.getTitle().equals(movie.getTitle())) {
                System.err.println("Movie is already in table.");
                return movie.getId();
            }
            else {
                throw e;
            }
        }
        return movie.getId();
    }
}
