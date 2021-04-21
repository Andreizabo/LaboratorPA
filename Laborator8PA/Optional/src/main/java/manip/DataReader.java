package manip;

import dao.*;
import model.Actor;
import model.Director;
import model.Genre;
import model.Movie;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataReader {
    private static String path = "src/main/java/data/data.csv";
    private static int maxNumber = 100;

    private DataReader() {}

    public static void init(String pathArg, int maxArg) {
        path = pathArg;
        maxNumber = maxArg;
    }

    public static void readFile(Connection connection) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(path));
            int counter = 0;
            String line = reader.readLine();
            line = reader.readLine();
            while(line != null && counter <= maxNumber) {
                //System.out.println(line + "\n\n");
                String[] data = line.replace("0,\"", "0,").
                        replace("1,\"", "1,").
                        replace("2,\"", "2,").
                        replace("3,\"", "3,").
                        replace("4,\"", "4,").
                        replace("5,\"", "5,").
                        replace("6,\"", "6,").
                        replace("7,\"", "7,").
                        replace("8,\"", "8,").
                        replace("9,\"", "9,").
                        replace("\",0", ",0").
                        replace("\",1", ",1").
                        replace("\",2", ",2").
                        replace("\",3", ",3").
                        replace("\",4", ",4").
                        replace("\",5", ",5").
                        replace("\",6", ",6").
                        replace("\",7", ",7").
                        replace("\",8", ",8").
                        replace("\",9", ",9").
                        replaceAll("(\")*?\\1", "").
                        replace("0,0", "00"). //TODO Use a regex to identify (\").+[0-9],[0-9].+\\1
                        replace(",...", "").
                        replace(",..", "").
                        replace(", ", "`").
                        replace("'", " ").
                        split(",");

                //ADD GENRES 5
                List<Integer> genreIds = new ArrayList<Integer>();
                Arrays.stream(data[5].split("`")).forEach((genre) -> {
                    try {
                        genreIds.add(Genres.insertGenre(connection, new Genre(-1, genre)));
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                });
                //ADD DIRECTOR 9

                int dirId = Directors.insertDirector(connection, new Director(-1, data[9], 0));

                //ADD ACTORS 12
                List<Integer> actorIds = new ArrayList<Integer>();
                Arrays.stream(data[12].replace("\"", "").split("`")).forEach((act) -> {
                    try {
                        actorIds.add(Actors.insertActor(connection, new Actor(-1, act, 0)));
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                });

                //ADD MOVIE
                int movId = Movies.insertMovie(connection, new Movie(-1,
                            data[1],
                            data[4].length() > 4 ? LocalDate.parse(data[4]) : LocalDate.parse(data[4] + "-01-01"),
                            Integer.parseInt(data[6]),
                            Float.parseFloat(data[14]),
                            dirId
                        ));

                //ADD MOVIE GENRES

                genreIds.forEach((id) -> {
                    try {
                        MovieGenre.addGenre(connection, movId, id);
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                });

                //ADD MOVIE ACTORS

                actorIds.forEach((act) -> {
                    try {
                        MovieActors.addActor(connection, movId, act);
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                });

                line = reader.readLine();
                ++counter;
            }
            reader.close();
            OracleConnector.commit();
        }
        catch (IOException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
