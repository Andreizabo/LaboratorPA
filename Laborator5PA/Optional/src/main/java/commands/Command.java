public interface Command {
    void run(Catalog catalog);
    void runWithoutLogger(Catalog catalog);
}
