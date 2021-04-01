import java.util.Random;

public class PlayerTurn implements Runnable {
    private Player player;
    private Game game;

    public PlayerTurn(Player player, Game game) {
        this.player = player;
        this.game = game;
    }

    @Override
    public void run() {
        Random random = new Random(System.nanoTime() * Thread.currentThread().getId());
        do {
            Token token = game.getRandomToken(random);
            if(token == null) {
                break;
            }
            System.out.println("Player " + player.getId() + " drew " + token.toString());
            player.addToken(token);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.err.print(e.getMessage());
            }
        } while (!game.isBoardEmpty());
    }
}
