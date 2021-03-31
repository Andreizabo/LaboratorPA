import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public abstract class DrawableObject {
    private Color colour;
    private Color strokeColor;
    private double startX, startY;
    private double radius;
    private int strokeWidth;

    public DrawableObject(Color colour, Color strokeColor, double startX, double startY, double radius, int strokeWidth) {
        this.colour = colour;
        this.startX = startX;
        this.startY = startY;
        this.radius = radius;
        this.strokeWidth = strokeWidth;
        this.strokeColor = strokeColor;
    }

    public DrawableObject(Color colour, double startX, double startY, double radius) {
        this.colour = colour;
        this.startX = startX;
        this.startY = startY;
        this.radius = radius;
        this.strokeWidth = 0;
        this.strokeColor = colour;
    }

    public Color getColour() {
        return colour;
    }

    public void setColour(Color colour) {
        this.colour = colour;
    }

    public double getStartX() {
        return startX;
    }

    public void setStartX(double startX) {
        this.startX = startX;
    }

    public double getStartY() {
        return startY;
    }

    public void setStartY(double startY) {
        this.startY = startY;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public Color getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(Color strokeColor) {
        this.strokeColor = strokeColor;
    }

    public int getTheStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public abstract void drawObject(GraphicsContext gc);

    public abstract boolean contains(double x, double y);
}
