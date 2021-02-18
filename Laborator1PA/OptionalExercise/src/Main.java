import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Consumer;

public class Main {

    public static class myDeque<T> {
        private final ArrayList<T> elements;

        public myDeque(ArrayList<T> elements) {
            this.elements = elements;
        }

        public myDeque() {
            this.elements = new ArrayList<>();
        }

        public void clear() {
            this.elements.clear();
        }

        public boolean isEmpty() {
            return this.elements.isEmpty();
        }

        public boolean pushTop(T t) {
            return this.elements.add(t);
        }

        public void pushBottom(T t) {
            this.elements.add(0, t);
        }

        public T top() {
            if(elements.size() < 1) {
                return null;
            }
            return this.elements.get(elements.size() - 1);
        }

        public T bottom() {
            if(elements.size() < 1) {
                return null;
            }
            return this.elements.get(0);
        }

        public boolean popTop() {
            if(elements.size() < 1) {
                return false;
            }
            this.elements.remove(elements.size() - 1);
            return true;
        }

        public boolean popBottom() {
            if(elements.size() < 1) {
                return false;
            }
            this.elements.remove(0);
            return true;
        }

        public Iterator<T> getIterator() {
            return this.elements.iterator();
        }

        public void forEach(Consumer<?super T> action) {
            this.elements.forEach(action);
        }
    }

    public static boolean printMatrices = true;

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        if(args.length < 1) {
            System.out.print("You must provide an odd integer.\n");
            showTime(startTime);
            return;
        }
        String argOne = args[0];
        int n = 0;
        if(argOne != null) {
            n = Integer.parseInt(argOne);
            if(n % 2 == 0) {
                System.out.print("You must provide an odd integer.\n");
                showTime(startTime);
                return;
            }
        }
        System.out.printf("%d is an odd integer\n", n);

        if(n > 9) {
            printMatrices = false;
        }

        int[][] matrix = new int[n][n];
        for(int i = 0; i < n; ++i) {
            matrix[i][i] = 0;
            for(int j = i + 1; j < n; ++j) {
                matrix[i][j] = (int)(Math.random() * 2);
                matrix[j][i] = matrix[i][j];
            }
        }
        printMatrix(matrix);
        boolean connected = isConnected(matrix);

        if(connected) {
            makeTree(matrix);
        }
        showTime(startTime);
    }

    public static void printMatrix(int[][] matrix) {
        if(!printMatrices) {
            return;
        }
        int n = matrix.length;
        //int numberOfLines = n * 2 + (5 + ((n - 5) / 2)); //Unicode characters are printed as "?"
        int numberOfLines = n * 2 + 3;
        for(int i = 0; i < numberOfLines; ++i) {
            System.out.print("_");
        }
        System.out.print("\n");
        for(int i = 0; i < n; ++i) {
            System.out.print("| ");
            for(int j = 0; j < n; ++j) {
                System.out.printf("%d ", matrix[i][j]);
                /*if(matrix[i][j] == 1) { //Unicode characters are printed as "?"
                    System.out.print("\u25C8 ");
                }
                else {
                    System.out.print("\u25C7 ");
                }*/
            }
            System.out.print("|\n");
        }
        for(int i = 0; i < numberOfLines; ++i) {
            System.out.print("¯");
        }
        System.out.print("\n");
    }

    public static boolean isConnected(int[][] matrix) {
        int n = matrix.length;
        int[] indexed = new int[n];
        int k = 1;
        for(int i = 0; i < n; ++i) {
            indexed[i] = 0;
        }
        dfs(matrix, 0, k, indexed);
        for(int i = 0; i < n; ++i) {
            if(indexed[i] != 1) {
                ++k;
                dfs(matrix, i, k, indexed);
            }
        }
        if(k == 1) {
            System.out.print("The graph is connected.\n");
            return true;
        }
        else {
            System.out.print("The graph is not connected.\n");
            for(int i = 1; i <= k; ++i) {
                System.out.printf("Component %d/%d", i, k);
                boolean first = true;
                for(int j = 0; j < n; ++j) {
                    if(indexed[j] == i) {
                        System.out.printf("%c %d", first ? ':' : ',', j );
                        first = false;
                    }
                }
                System.out.print("\n");
            }
            return false;
        }
    }

    public static void dfs(int[][] matrix, int node, int k, int[] indexed) {
        int n = matrix.length;
        indexed[node] = k;
        for(int i = 0; i < n; ++i) {
            if(i != node && indexed[i] == 0 && matrix[i][node] == 1) {
                dfs(matrix, i, k, indexed);
            }
        }
    }

    public static void makeTree(int[][] matrix) {
        int n = matrix.length;
        int[][] treeMatrix = new int[n][n];

        boolean[] visited = new boolean[matrix.length];
        Arrays.fill(visited, false);

        myDeque<Integer> deque = new myDeque<>();
        deque.pushTop(0);
        visited[0] = true;

        while(!deque.isEmpty()) {
            int node = deque.bottom();
            deque.popBottom();

            for(int i = 0; i < n; ++i) {
                if(!visited[i] && matrix[node][i] == 1) {
                    visited[i] = true;
                    treeMatrix[node][i] = 1;
                    treeMatrix[i][node] = 1;
                    deque.pushTop(i);
                }
            }
        }

        printMatrix(treeMatrix);
    }


    public static void showTime(long startTime) {
        long endTime = System.nanoTime();
        System.out.printf("\nThe program took %d nanoseconds (%f seconds) to run.\n", endTime - startTime, (double)(endTime - startTime) * Math.pow(10, -9));
    }
}
