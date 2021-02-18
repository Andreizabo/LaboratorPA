public class Main {

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
        printMatrix(matrix, n);
        boolean connected = isConnected(matrix, n);

        if(connected) {
            makeTree(matrix, n);
        }
        showTime(startTime);
    }

    public static void printMatrix(int[][] matrix, int n) {
        if(!printMatrices) {
            return;
        }
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

    public static boolean isConnected(int[][] matrix, int n) {
        int[] indexed = new int[n];
        int k = 1;
        for(int i = 0; i < n; ++i) {
            indexed[i] = 0;
        }
        dfs(matrix, n, 0, k, indexed);
        for(int i = 0; i < n; ++i) {
            if(indexed[i] != 1) {
                ++k;
                dfs(matrix, n, i, k, indexed);
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

    public static void dfs(int[][] matrix, int n, int node, int k, int[] indexed) {
        indexed[node] = k;
        for(int i = 0; i < n; ++i) {
            if(i != node && indexed[i] == 0 && matrix[i][node] == 1) {
                dfs(matrix, n, i, k, indexed);
            }
        }
    }

    public static void makeTree(int[][] matrix, int n) {
        int[][] treeMatrix = new int[n][n];
        int[] indexed = new int[n];
        for(int i = 0; i < n; ++i) {
            for(int j = 0; j < n; ++j) {
                treeMatrix[i][j] = 0;
            }
            indexed[i] = 0;
        }
        tree(matrix, n, 0, indexed, treeMatrix);
        printMatrix(treeMatrix, n);
    }

    public static void tree(int[][] matrix, int n, int node, int[] indexed, int[][] treeMatrix) {
        indexed[node] = 1;
        for(int i = 0; i < n; ++i) {
            if(i != node && indexed[i] == 0 && matrix[i][node] == 1) {
                treeMatrix[i][node] = 1;
                treeMatrix[node][i] = 1;
                tree(matrix, n, i, indexed, treeMatrix);
            }
        }
    }

    public static void showTime(long startTime) {
        long endTime = System.nanoTime();
        System.out.printf("\nThe program took %d nanoseconds (%f seconds) to run.\n", endTime - startTime, (double)(endTime - startTime) * Math.pow(10, -9));
    }
}
