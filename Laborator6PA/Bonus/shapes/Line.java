import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Line extends DrawableObject {
    private Point x, y;

    public Line(Color colour, Color strokeColor, double startX, double startY, Point x, Point y, int lineWidth) {
        super(colour, strokeColor, startX, startY, MathUtils.distancePoints(x, y), 0, lineWidth);
        if(x.getX() > y.getX()) {
            this.x = y;
            this.y = x;
            this.setStartX(y.getX());
            this.setStartY(y.getY());
        }
        else {
            this.x = x;
            this.y = y;
        }
    }

    private Point lineMiddle() {
        return new Point((x.getX() + y.getX()) / 2, (x.getY() + y.getY()) / 2);
    }

    @Override
    public void drawObject(GraphicsContext gc) {
        gc.setStroke(this.getStrokeColor());
        gc.setLineWidth(this.getTheStrokeWidth());
        gc.strokeLine(x.getX(), x.getY(), y.getX(), y.getY());
    }

    @Override
    public boolean contains(double x, double y) {
        return (x > Math.min(this.x.getX(), this.y.getX()) - getRadius() / 4) && (x < Math.max(this.x.getX(), this.y.getX()) + getRadius() / 4)
                && (y > Math.min(this.x.getY(), this.y.getY()) - getRadius() / 4) && (y < Math.max(this.x.getY(), this.y.getY()) + getRadius() / 4);
    }

    @Override
    public String toString() {
        return "Line ";
    }

    @Override
    public void resize(int modifier) {
        double dMod = modifier / 2f;
        Point middle = this.lineMiddle();
        double phi = 90;
        if(x.getY() < y.getY()) {
            phi -= Math.toDegrees(Math.atan(Math.abs(x.getX() - middle.getX()) / Math.abs(middle.getY() - x.getY())));
        }
        else {
            phi += Math.toDegrees(Math.atan(Math.abs(y.getX() - middle.getX()) / Math.abs(middle.getY() - y.getY()))) + 180;
        }
        double cosPhi = Math.cos(Math.toRadians(phi));
        double sinPhi = Math.sin(Math.toRadians(phi));

        x = new Point(x.getX() + (-dMod) * cosPhi, x.getY() + (-dMod) * sinPhi);
        y = new Point(y.getX() + dMod * cosPhi, y.getY() + dMod * sinPhi);

        this.setRadius(MathUtils.distancePoints(x, y));
    }

    @Override
    public void rotate(int degrees) {
        this.setRotation((this.getRotation() + degrees) % 360);
        Point middle = this.lineMiddle();
        double xxMod = this.x.getX() - middle.getX();
        double xyMod = this.x.getY() - middle.getY();
        double yxMod = this.y.getX() - middle.getX();
        double yyMod = this.y.getY() - middle.getY();

        double phi = degrees;
        double cosPhi = Math.cos(Math.toRadians(phi));
        double sinPhi = Math.sin(Math.toRadians(phi));

        this.x = new Point(xxMod * cosPhi - xyMod * sinPhi + middle.getX(), xxMod * sinPhi + xyMod * cosPhi + middle.getY());
        this.y = new Point(yxMod * cosPhi - yyMod * sinPhi + middle.getX(), yxMod * sinPhi + yyMod * cosPhi + middle.getY());

        if(this.x.getX() > this.y.getX()) {
            Point aux = this.x;
            this.x = this.y;
            this.y = aux;
            this.setStartX(y.getX());
            this.setStartY(y.getY());
        }
    }

    @Override
    public void move(int x, int y) {
        this.setStartX(this.getStartX() + x);
        this.setStartY(this.getStartY() + y);
        this.x = new Point(this.x.getX() + x, this.x.getY() + y);
        this.y = new Point(this.y.getX() + x, this.y.getY() + y);
    }
}
