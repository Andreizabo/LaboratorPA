import dao.Genres;
import dao.MovieGenre;
import dao.Movies;
import model.Genre;
import model.Movie;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection connection = OracleConnector.connectDB();

        tryInserts(connection);

        System.out.print("\nMovies with SF genre:\n");
        MovieGenre.getMovies(connection, 1).forEach(m -> System.out.print(m.toString() + "\n"));
        System.out.print("\nGenres of movie 1:\n");
        MovieGenre.getGenres(connection, 1).forEach(g -> System.out.print(g.toString() + "\n"));

        OracleConnector.disconnectDB();
    }

    private static void tryInserts(Connection connection) throws SQLException {
        Movie movie = new Movie(1, "Interstellar", LocalDate.parse("2014-11-07"), 169, 8.6f);
        Genre genre = new Genre(1, "SF");
        Genre genre2 = new Genre(2, "Mystery");
        Movies.insertMovie(connection, movie);
        Genres.insertGenre(connection, genre);
        Genres.insertGenre(connection, genre2);
        MovieGenre.addGenre(connection, 1, 1);
        MovieGenre.addGenre(connection, 1, 2);
    }
}
