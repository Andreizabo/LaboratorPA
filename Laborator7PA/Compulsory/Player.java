import java.util.ArrayList;
import java.util.List;

public class Player {
    private int id;
    private List<Token> tokens;

    public Player() {
        tokens = new ArrayList<>();
    }

    public void addToken(Token token) {
        tokens.add(token);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void printTokens(String prefix, String suffix) {
        if(tokens.isEmpty()) {
            System.out.print(prefix + "Player " + this.id + " has no tokens" + suffix);
        }
        else {
            System.out.print(prefix + "Player " + this.id + " has the following tokens: ");
            this.tokens.forEach(t -> System.out.print("\n\t" + t.toString()));
            System.out.print(suffix);
        }
    }
}
