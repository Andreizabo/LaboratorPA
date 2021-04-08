public class ActionDeleted implements Action{
    private final MainFrame frame;
    private int index;
    private DrawableObject obj;

    public ActionDeleted(MainFrame frame, int index) {
        this.frame = frame;
        this.index = index;
        this.obj = frame.canvas.getShapeAtIndex(index);
    }

    @Override
    public void redo() {
        this.obj = frame.canvas.getShapeAtIndex(index);
        frame.canvas.deleteShape(index);
    }

    @Override
    public void undo() {
        frame.canvas.addShape(index, obj);
    }

    @Override
    public int getIndex() {
        return index;
    }
}
