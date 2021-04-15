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
            game.waitForTurn(player);
            Token token;
            if(player.isAI()) {
                token = game.getRandomToken(random);
            }
            else {
                token = game.drawTokenPlayer();
            }
            if (token == null) {
                break;
            }
            System.out.println("Player " + player.getId() + " (" + player.getName() + ") drew " + token.toString());
            player.addToken(token);
            game.endTurn();
        } while (!game.isBoardEmpty());
    }
}
