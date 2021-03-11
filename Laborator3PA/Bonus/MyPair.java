/**
 * Pair class, it stores two Integers
 */
public class MyPair<T1, T2> {
    private T1 first;
    private T2 second;
    private int meta;

    /**
     * Creates a new integer pair
     * @param first The first element
     * @param second The second element
     */
    public MyPair(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Creates a new integer pair, with a meta argument
     * @param first The first element
     * @param second The second element
     * @param meta Meta data
     */
    public MyPair(T1 first, T2 second, int meta) {
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
    public T1 getFirst() {
        return first;
    }

    /**
     * Modifies the first element
     * @param first The new value
     */
    public void setFirst(T1 first) {
        this.first = first;
    }

    /**
     * Returns the second element
     * @return The second element
     */
    public T2 getSecond() {
        return second;
    }

    /**
     * Modifies the second element
     * @param second The new value
     */
    public void setSecond(T2 second) {
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
