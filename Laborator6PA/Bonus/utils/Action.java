public interface Action {
    void redo();
    void undo();
    int getIndex();
}
