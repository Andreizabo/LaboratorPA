public class ActionCreated implements Action{
    private final MainFrame frame;
    private int index;
    private DrawableObject obj;

    public ActionCreated(MainFrame frame, int index) {
        this.frame = frame;
        this.index = index;
        this.obj = frame.canvas.getShapeAtIndex(index);
    }

    @Override
    public void redo() {
        frame.canvas.addShape(index, obj);
    }

    @Override
    public void undo() {
        this.obj = frame.canvas.getShapeAtIndex(index);
        frame.canvas.deleteShape(index);
    }

    @Override
    public int getIndex() {
        return index;
    }
}
