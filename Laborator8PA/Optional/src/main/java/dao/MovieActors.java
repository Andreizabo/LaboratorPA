package dao;

import model.Actor;
import model.Movie;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MovieActors {

    public static List<Movie> getMovies(Connection connection, int id) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery("SELECT * FROM MOVIES WHERE ID IN (SELECT ID_MOVIE FROM MOVIE_Actor WHERE ID_ACTOR = " + id + ")");
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

    public static List<Actor> getActors(Connection connection, int id) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery("SELECT * FROM ActorS WHERE ID IN (SELECT ID_ACTOR FROM MOVIE_Actor WHERE ID_MOVIE = " + id + ")");
        List<Actor> Actors = new ArrayList<>();
        while(set.next()) {
            Actors.add(new Actor(
                    set.getInt("id"),
                    set.getString("name"),
                    set.getInt("age")
            ));
        }
        set.close();
        return Actors;
    }

    public static void addActor(Connection connection, int movie_id, int actor_id) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet set = null;
        try {
            set = statement.executeQuery(
                    "INSERT INTO MOVIE_Actor VALUES(" +
                            movie_id + "," +
                            actor_id + ")"
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
