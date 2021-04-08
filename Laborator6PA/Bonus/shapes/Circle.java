import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Circle extends DrawableObject {
    private double centerX, centerY;

    public Circle(Color colour, double startX, double startY, double radius, Color strokeColor, int strokeWidth) {
        super(colour, strokeColor, startX - radius / 2, startY - radius / 2, radius, 0, strokeWidth);
        this.centerX = startX;
        this.centerY = startY;
    }

    public Circle(Color colour, double startX, double startY, double radius, double centerX, double centerY) {
        super(colour, startX, startY, radius, 0);
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

    @Override
    public String toString() {
        return "Circle ";
    }

    @Override
    public void resize(int modifier) {
        this.setRadius(this.getRadius() + modifier);
        this.setStartX(this.getStartX() - modifier / 2f);
        this.setStartY(this.getStartY() - modifier / 2f);
    }

    @Override
    public void rotate(int degrees) {}

    @Override
    public void move(int x, int y) {
        this.centerX += x;
        this.centerY += y;
        this.setStartX(this.getStartX() + x);
        this.setStartY(this.getStartY() + y);
    }
}
