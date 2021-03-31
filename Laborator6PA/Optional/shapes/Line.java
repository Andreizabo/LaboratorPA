import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Line extends DrawableObject {
    private Point x, y;

    public Line(Color colour, double startX, double startY, double radius, Point x, Point y) {
        super(colour, startX, startY, radius);
        this.x = x;
        this.y = y;
    }

    @Override
    public void drawObject(GraphicsContext gc) {
        gc.setStroke(this.getColour());
        gc.setLineWidth(this.getRadius());
        gc.strokeLine(x.getX(), x.getY(), y.getX(), y.getY());
    }

    @Override
    public boolean contains(double x, double y) {
        return MathUtils.isPointOnLine(new Point(x, y), this.x, this.y);
    }
}
