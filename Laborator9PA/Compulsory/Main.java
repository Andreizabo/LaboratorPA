import entities.Genre;
import manager.Manager;
import repositories.GenreRepository;
import repositories.MovieRepository;

public class Main {
    public static void main(String[] args) {
        Manager.init();
        GenreRepository.createGenre(new Genre(100L, "test"));
        Manager.end();
    }
}
