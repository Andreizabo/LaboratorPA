import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private int lastID;
    private int numberOfTokens;
    private List<Token> tokensOnBoard;
    private List<Player> players;
    private boolean available;
    private int currentPlayer;
    private boolean lastTurn;
    private Timer timer;

    public Game(int tokenNo, boolean all) {
        this.lastID = 0;
        this.numberOfTokens = tokenNo;
        this.tokensOnBoard = new ArrayList<>(numberOfTokens);
        this.players = new ArrayList<>();
        generateTokens(all);
        this.available = true;
        this.lastTurn = false;
    }

    private void generateTokens(boolean all) {
        Random random = new Random(System.nanoTime());
        for(int i = 0; i < numberOfTokens - 1; ++i) {
            for(int j = i + 1; j < numberOfTokens; ++j) {
                if(!all) {
                    if (random.nextBoolean()) {
                        tokensOnBoard.add(new Token(i, j));
                    }
                    if (random.nextBoolean()) {
                        tokensOnBoard.add(new Token(j, i));
                    }
                }
                else {
                    tokensOnBoard.add(new Token(i, j));
                    tokensOnBoard.add(new Token(j, i));
                }
            }
        }
    }

    public void addPlayer(Player p) {
        p.setId(getID());
        players.add(p);
    }

    public void startGame() {
        currentPlayer = 0;
        timer = new Timer(this, 300);
        Thread timerThread = new Thread(timer);
        timerThread.setDaemon(true);
        timerThread.start();

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
        timer.getTime();
    }

    public Token getRandomToken(Random random) {
        if(tokensOnBoard.isEmpty()) {
            return null;
        }
        int index = random.nextInt(tokensOnBoard.size());
        Token token = tokensOnBoard.get(index);
        tokensOnBoard.remove(token);
        if(lastTurn) {
            tokensOnBoard.clear();
        }
        //timer.getTime();
        return token;
    }

    public Token drawTokenPlayer() {
        if(tokensOnBoard.isEmpty()) {
            return null;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nThe following tokens are available:\n");
        tokensOnBoard.forEach(System.out::print);
        String chosen = "";
        while (true) {
            System.out.print("\nChoose a token: (must be in (x, y) format)\n");
            chosen = scanner.nextLine();
            Token token = isTokenOnBoard(chosen);
            if(token != null) {
                tokensOnBoard.remove(token);
                if(lastTurn) {
                    tokensOnBoard.clear();
                }
                timer.getTime();
                return token;
            }
        }
    }

    private Token isTokenOnBoard(String string) {
        if(string.length() < 5) {
            return null;
        }
        string = string.replace("(", "");
        string = string.replace(")", "");
        String[] splatted = string.split(", ");

        if(splatted.length != 2) {
            return null;
        }

        int x = Integer.parseInt(splatted[0]);
        int y = Integer.parseInt(splatted[1]);

        for(Token t : tokensOnBoard) {
            if(t.getX() == x && t.getY() == y) {
                return t;
            }
        }
        return null;
    }

    public synchronized void waitForTurn(Player player) {
        while(!isBoardEmpty() && currentPlayer != player.getId()) {
            try {
                wait();
            }
            catch (java.lang.InterruptedException e) {
                System.err.print(e.getMessage());
            }
        }
    }

    public synchronized void endTurn() {
        currentPlayer = (currentPlayer + 1) % players.size();
        notifyAll();
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
        players.forEach(Player::calculateScore);
        System.out.print(suffix);
    }

    public void forceEnd() {
        this.lastTurn = true;
        System.out.print("\n\nTime's up! This will be the last turn!\n\n");
    }
}
