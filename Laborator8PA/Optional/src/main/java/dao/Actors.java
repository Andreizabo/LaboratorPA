package dao;

import model.Actor;
import model.Movie;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Actors {
    public static Actor getActor(Connection connection, String query) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery(query);
        set.next();
        Actor actor = new Actor(
                set.getInt("id"),
                set.getString("name"),
                set.getInt("age")
        );
        set.close();
        return actor;
    }

    public static Actor getActor(Connection connection, int id) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery("SELECT * FROM Actors WHERE ID = " + id);
        set.next();
        Actor actor = new Actor(
                set.getInt("id"),
                set.getString("name"),
                set.getInt("age")
        );
        set.close();
        return actor;
    }

    public static int insertActor(Connection connection, Actor actor) throws SQLException {
        Statement statement = connection.createStatement();
        try {
            Statement exists = connection.createStatement();
            ResultSet setEx = exists.executeQuery("SELECT id FROM ACTORS WHERE NAME LIKE '" + actor.getName() + "'");
            if(setEx.next()) {
                int id = setEx.getInt("id");
                setEx.close();
                exists.close();
                return id;
            }
            setEx.close();
            exists.close();
            if(actor.getId() == -1) {
                Statement getAnId = connection.createStatement();
                ResultSet set =  getAnId.executeQuery("SELECT COUNT(*) FROM Actors");
                set.next();
                actor.setId(set.getInt("COUNT(*)") + 1);
                set.close();
                statement.close();
            }
            ResultSet set = statement.executeQuery(
                    "INSERT INTO Actors VALUES(" +
                            actor.getId() + "," +
                            "'" + actor.getName() + "'," +
                            actor.getAge() + ")"
            );
            set.close();
            statement.close();
        }
        catch (SQLIntegrityConstraintViolationException e) {
            if(Actors.getActor(connection, actor.getId()).getName().equals(actor.getName())) {
                System.err.println("Object is already in table.");
                return actor.getId();
            }
            else {
                throw e;
            }
        }
        return actor.getId();
    }

    public static List<Actor> getAllActors(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery("SELECT * FROM Actors");
        if(set.getFetchSize() < 1) {
            return null;
        }
        List<Actor> actors = new ArrayList<>();

        while (set.next()) {
            actors.add(new Actor(
                    set.getInt("id"),
                    set.getString("name"),
                    set.getInt("age")
            ));
        }
        set.close();
        return actors;
    }
}
