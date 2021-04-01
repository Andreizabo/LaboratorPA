public class Main {
    public static void main(String[] args) {
        Game game = new Game(10);
        game.addPlayer(new Player());
        game.addPlayer(new Player());
        game.addPlayer(new Player());
        game.addPlayer(new Player());
        game.addPlayer(new Player());
        game.startGame();
    }
}
