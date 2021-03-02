/**
 * Pair class, it stores two Integers
 */
public class MyPair {
    private int first, second;
    private int meta;

    /**
     * Creates a new integer pair
     * @param first The first element
     * @param second The second element
     */
    public MyPair(int first, int second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Creates a new integer pair, with a meta argument
     * @param first The first element
     * @param second The second element
     * @param meta Meta data
     */
    public MyPair(int first, int second, int meta) {
        this.first = first;
        this.second = second;
        this.meta = meta;
    }

    /**
     * Creates an empty pair
     */
    public MyPair() {}

    /**
     * Returns the first element
     * @return The first element
     */
    public int getFirst() {
        return first;
    }

    /**
     * Modifies the first element
     * @param first The new value
     */
    public void setFirst(int first) {
        this.first = first;
    }

    /**
     * Returns the second element
     * @return The second element
     */
    public int getSecond() {
        return second;
    }

    /**
     * Modifies the second element
     * @param second The new value
     */
    public void setSecond(int second) {
        this.second = second;
    }

    /**
     * Returns the meta data
     * @return The meta data stored in the pair
     */
    public int getMeta() {
        return meta;
    }

    /**
     * Modifies the meta data
     * @param meta The new meta data
     */
    public void setMeta(int meta) {
        this.meta = meta;
    }
}
