import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        Game game = new Game(100, true);
        IntStream.range(0, 10).forEach(i -> game.addPlayer(new Player()));
        //game.addPlayer(new Player("Andrei"));
        game.startGame();
    }
}
