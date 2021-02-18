import java.util.*;
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

    public static final int forceNumberOfNodes = 0;

    public static double startingChance = 100; //out of 100
    public static double connectionModifier = 50; //out of 100, 25 is also ok
    public static int numberOfNodes;

    public static void main(String[] args) {
        if(args.length < 1) {
            System.out.print("You must provide an odd integer.\n");
            return;
        }
        String arg_one = args[0];
        if(arg_one != null) {
            numberOfNodes = Integer.parseInt(arg_one);
        }

        if(forceNumberOfNodes > 0) {
            numberOfNodes = forceNumberOfNodes;
        }

        Random random = new Random(System.nanoTime());

        boolean[] connected = new boolean[numberOfNodes];
        Arrays.fill(connected, false);

        ArrayList<Integer>[] sons = new ArrayList[numberOfNodes];

        int root = random.nextInt(numberOfNodes);

        myDeque<Integer> deque = new myDeque<>();
        deque.pushTop(root);
        connected[root] = true;

        while (!deque.isEmpty()) {
            int node = deque.bottom();
            deque.popBottom();
            sons[node] = new ArrayList<>();

            random = new Random(System.nanoTime() * random.nextInt());
            double connectionChance = startingChance;

            for(int i = 0; i < numberOfNodes; ++i) {
                if(!connected[i]) {
                    if(random.nextInt(100) + 1 < connectionChance) {
                        connected[i] = true;
                        sons[node].add(i);
                        deque.pushTop(i);
                        connectionChance -= connectionModifier;
                    }
                }
            }
        }

        printNodes(sons, 0, root);
    }

    public static void printNodes(ArrayList<Integer>[] sons, int depth, int node) {
        for(int j = 0; j < depth; ++j) {
            System.out.print(" ");
        }
        if(sons[node] == null || sons[node].isEmpty()) {
            System.out.printf("-node %d\n", node);
        }
        else {
            System.out.printf("+node %d\n", node);
            sons[node].forEach((n) -> printNodes(sons, depth + 1, n));
        }
    }
}
