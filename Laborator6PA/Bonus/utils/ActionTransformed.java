public class ActionTransformed implements Action {
    private final MainFrame frame;
    private double startX, startY, startRadius, startRotation, endX, endY, endRadius, endRotation, difX, difY, difRadius, difRotation;
    private int index;
    private boolean finished;

    public ActionTransformed(double startX, double startY, double startRadius, double startRotation, int index, MainFrame frame) {
        this.startX = startX;
        this.startY = startY;
        this.startRadius = startRadius;
        this.startRotation = startRotation;
        this.index = index;
        this.frame = frame;
        this.finished = false;
    }

    public void endAction(double endX, double endY, double endRadius, double endRotation) {
        this.endX = endX;
        this.endY = endY;
        this.endRadius = endRadius;
        this.endRotation = endRotation;
        this.difRadius = this.startRadius - this.endRadius;
        this.difRotation = this.startRotation - this.endRotation;
        this.difX = this.startX - this.endX;
        this.difY = this.startY - this.endY;
        this.finished = true;
    }

    @Override
    public void redo() {
        if(this.difRadius == 0) {
            if (this.difX != 0) {
                frame.canvas.moveShape(this.index, -(int) this.difX, 0);
            }
            if (this.difY != 0) {
                frame.canvas.moveShape(this.index, 0, -(int) this.difY);
            }
        }
        else {
            frame.canvas.resizeShape(this.index, -(int)this.difRadius);
        }
        if(this.difRotation != 0) {
            frame.canvas.rotateShape(this.index, -(int)this.difRotation);
        }
    }

    @Override
    public void undo() {
        if(this.difRadius == 0) {
            if (this.difX != 0) {
                frame.canvas.moveShape(this.index, (int) this.difX, 0);
            }
            if (this.difY != 0) {
                frame.canvas.moveShape(this.index, 0, (int) this.difY);
            }
        }
        else {
            frame.canvas.resizeShape(this.index, (int)this.difRadius);
        }
        if(this.difRotation != 0) {
            frame.canvas.rotateShape(this.index, (int)this.difRotation);
        }
    }

    @Override
    public int getIndex() {
        return index;
    }

    public boolean isFinished() {
        return finished;
    }
}
