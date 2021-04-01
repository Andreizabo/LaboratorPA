import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private int lastID;
    private int numberOfTokens;
    private List<Token> tokensOnBoard;
    private List<Player> players;
    private boolean available;

    public Game(int tokenNo) {
        this.lastID = 0;
        this.numberOfTokens = tokenNo;
        this.tokensOnBoard = new ArrayList<>(numberOfTokens);
        this.players = new ArrayList<>();
        generateTokens();
        this.available = true;
    }

    private void generateTokens() {
        Random random = new Random(System.nanoTime());
        for(int i = 0; i < numberOfTokens - 1; ++i) {
            for(int j = i + 1; j < numberOfTokens; ++j) {
                if(random.nextBoolean()) {
                    tokensOnBoard.add(new Token(i, j));
                }
                if(random.nextBoolean()) {
                    tokensOnBoard.add(new Token(j, i));
                }
            }
        }
    }

    public void addPlayer(Player p) {
        p.setId(getID());
        players.add(p);
    }

    /**
     * Each player will draw a token once every 1 second, if there is 1 available, using the getRandomToken function
     */
    public void startGame() {
        List<PlayerTurn> turns = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();
        players.forEach(p -> turns.add(new PlayerTurn(p, this)));
        turns.forEach(t -> threads.add(new Thread(t)));
        threads.forEach(Thread::start);
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        });
        printPlayers("\n", "\n");
    }

    public synchronized Token getRandomToken(Random random) {
        if(tokensOnBoard.isEmpty()) {
            return null;
        }
        int index = random.nextInt(tokensOnBoard.size());
        Token token = tokensOnBoard.get(index);
        tokensOnBoard.remove(token);
        return token;
    }

    public boolean isBoardEmpty() {
        return tokensOnBoard.isEmpty();
    }

    public int getID() {
        return lastID++;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void printPlayers(String prefix, String suffix) {
        System.out.print(prefix);
        players.forEach(p -> p.printTokens("\n", "\n"));
        System.out.print(suffix);
    }
}
