import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Player {
    private int id;
    private List<Token> tokens;
    private boolean AI;
    private String name;

    public Player() {
        tokens = new ArrayList<>();
        AI = true;
        name = "bot";
    }

    public Player(String name) {
        tokens = new ArrayList<>();
        AI = false;
        this.name = name;
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

    public String getName() { return this.name; }

    public boolean isAI() {
        return AI;
    }

    public void printTokens(String prefix, String suffix) {
        if(tokens.isEmpty()) {
            System.out.print(prefix + "Player " + this.id + " (" + this.name + ") has no tokens" + suffix);
        }
        else {
            System.out.print(prefix + "Player " + this.id + " (" + this.name + ") has the following tokens: ");
            this.tokens.forEach(t -> System.out.print("\n\t" + t.toString()));
            System.out.print(suffix);
        }
    }

    public void calculateScore() {
        int score = getScore();
        System.out.print("Player " + this.id + " (" + this.name + ") has " + score + " points\n");
    }

    private int getScore() {
        if(tokens.size() == 0) {
            return 0;
        }
        int cycles = 0;
        int n = getMax();
        boolean[][] matrix = new boolean[n][n];
        String[] visited = new String[n];
        MyStack<Integer> stack;

        Arrays.fill(visited, "IGNORE");
        for(Token t : tokens) {
            visited[t.getX()] = "NOT_VISITED";
            visited[t.getY()] = "NOT_VISITED";
        }
        for(int i = 0; i < n; ++i) {
            Arrays.fill(matrix[i], false);
        }
        for(Token t : tokens) {
            matrix[t.getX()][t.getY()] = true;
        }

        boolean countLength = true;

        for(int i = 0; i < n; ++i) {
            if(visited[i].equals("NOT_VISITED")) {
                stack = new MyStack<>(new ArrayList<>());
                stack.push(i);
                visited[i] = "IN_STACK";
                if(countLength) {
                    cycles = Math.max(cycles, dfs(matrix, n, stack, visited, countLength));
                }
                else {
                    cycles += dfs(matrix, n, stack, visited, countLength);
                }
            }
        }

        return cycles;
    }

    private int dfs(boolean[][] matrix, int n, MyStack<Integer> stack, String[] visited, boolean countLength) {
        int score = 0;
        int node = stack.top();
        for(int i = 0; i < n; ++i) {
            if(matrix[node][i]) {
                if (visited[i].equals("IN_STACK")) {
                    if (countLength) {
                        score = Math.max(cycleLength(stack.clone(), i), score);
                    } else {
                        score += 1;
                    }
                } else if (visited[i].equals("NOT_VISITED")) {
                    stack.push(i);
                    visited[i] = "IN_STACK";
                    if(countLength) {
                        score = Math.max(score, dfs(matrix, n, stack, visited, countLength));
                    }
                    else {
                        score += dfs(matrix, n, stack, visited, countLength);
                    }
                }
            }
        }
        visited[node] = "DONE";
        stack.pop();

        return score;
    }

    private int cycleLength(MyStack<Integer> stack, int node) {
        int length = 1;
        int current = stack.top();
        stack.pop();

        while (current != node) {
            current = stack.top();
            stack.pop();
            ++length;
        }

        return length;
    }

    private int getMax() {
        int mx = -1;
        for(Token t : tokens) {
            if(t.getX() > mx) {
                mx = t.getX();
            }
            if(t.getY() > mx) {
                mx = t.getY();
            }
        }
        return mx + 1;
    }
}
