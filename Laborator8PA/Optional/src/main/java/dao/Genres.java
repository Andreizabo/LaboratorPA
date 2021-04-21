package dao;

import model.Director;
import model.Genre;
import model.Movie;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Genres {
    public static Genre getGenre(Connection connection, String query) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery(query);
        set.next();
        Genre genre = new Genre(
                set.getInt("id"),
                set.getString("name")
        );
        set.close();
        return genre;
    }

    public static Genre getGenre(Connection connection, int id) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery("SELECT * FROM GENRES WHERE ID = " + id);
        set.next();
        Genre genre = new Genre(
                set.getInt("id"),
                set.getString("name")
        );
        set.close();
        return genre;
    }

    public static int insertGenre(Connection connection, Genre genre) throws SQLException {
        Statement statement = connection.createStatement();
        try {
            Statement exists = connection.createStatement();
            ResultSet setEx = exists.executeQuery("SELECT id FROM GENRES WHERE NAME LIKE '" + genre.getName() + "'");
            if(setEx.next()) {
                int id = setEx.getInt("id");
                setEx.close();
                exists.close();
                return id;
            }
            setEx.close();
            exists.close();
            if(genre.getId() == -1) {
                Statement getAnId = connection.createStatement();
                ResultSet set =  getAnId.executeQuery("SELECT COUNT(*) FROM Genres");
                set.next();
                genre.setId(set.getInt("COUNT(*)") + 1);
                set.close();
                statement.close();
            }
            ResultSet set = statement.executeQuery(
                    "INSERT INTO GENRES VALUES(" +
                            genre.getId() + "," +
                            "'" + genre.getName() + "')"
            );
            set.close();
            statement.close();
        }
        catch (SQLIntegrityConstraintViolationException e) {
            if(Genres.getGenre(connection, genre.getId()).getName().equals(genre.getName())) {
                System.err.println("Object is already in table.");
                return genre.getId();
            }
            else {
                throw e;
            }
        }

        return genre.getId();
    }

    public static List<Genre> getAllGenres(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery("SELECT * FROM Genres");
        if(set.getFetchSize() < 1) {
            return null;
        }
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
}
