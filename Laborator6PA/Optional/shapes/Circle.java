import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Circle extends DrawableObject {
    private double centerX, centerY;

    public Circle(Color colour, double startX, double startY, double radius, Color strokeColor, int strokeWidth) {
        super(colour, strokeColor, startX - radius / 2, startY - radius / 2, radius, strokeWidth);
        this.centerX = startX;
        this.centerY = startY;
    }

    public Circle(Color colour, double startX, double startY, double radius, double centerX, double centerY) {
        super(colour, startX, startY, radius);
        this.centerX = centerX;
        this.centerY = centerY;
    }

    public double getCenterX() {
        return centerX;
    }

    public void setCenterX(double centerX) {
        this.centerX = centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public void setCenterY(double centerY) {
        this.centerY = centerY;
    }

    @Override
    public void drawObject(GraphicsContext gc) {
        gc.setStroke(this.getStrokeColor());
        gc.setLineWidth(this.getTheStrokeWidth());
        gc.setFill(this.getColour());
        gc.fillOval(this.getStartX(), this.getStartY(), this.getRadius(), this.getRadius());
        gc.strokeOval(this.getStartX(), this.getStartY(), this.getRadius(), this.getRadius());
    }

    @Override
    public boolean contains(double x, double y) {
        return (MathUtils.distancePoints(new Point(x, y), new Point(this.centerX, this.centerY)) <= this.getRadius() / 2 + (double)this.getTheStrokeWidth() / 2);
    }
}
