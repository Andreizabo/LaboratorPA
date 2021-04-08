import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Polygon extends DrawableObject {
    private List<Point> points;
    private double[] xs, ys;
    private double[] strokeXs, strokeYs;
    private int sides;
    private boolean freeDrawn;

    public Polygon(Color colour, double startX, double startY, double radius, int sides, double rotation, Color strokeColor, int strokeWidth) {
        super(colour, strokeColor, startX, startY, radius, 0, strokeWidth);
        this.sides = sides;
        this.points = new ArrayList<>();
        double alpha = 2 * Math.PI / sides;
        for (int i = 0; i < sides; i++) {
            double x = this.getStartX() + radius * Math.cos(alpha * i);
            double y = this.getStartY() + radius * Math.sin(alpha * i);
            this.points.add(new Point(x, y));
        }
        generateXY();
        rotate((int) rotation);
        calculateStrokeXY();
        freeDrawn = false;
    }

    public Polygon(Color colour, double startX, double startY, double radius, int sides, double rotation) {
        super(colour, startX, startY, radius, 0);
        this.sides = sides;
        this.points = new ArrayList<>();
        double alpha = 2 * Math.PI / sides;
        for (int i = 0; i < sides; i++) {
            double x = startX + radius * Math.cos(alpha * i);
            double y = startY + radius * Math.sin(alpha * i);
            this.points.add(new Point(x, y));
        }
        generateXY();
        rotate((int) rotation);
        calculateStrokeXY();
        freeDrawn = false;
    }

    public Polygon(Color colour, double startX, double startY, double radius, List<Point> points, Color strokeColor, int strokeWidth) {
        super(colour, strokeColor, startX - strokeWidth, startY - strokeWidth, radius + strokeWidth * 2, 0, strokeWidth);
        this.points = points;
        this.sides = points.size();
        generateXY();
        calculateStrokeXY();
        freeDrawn = true;
    }

    public Polygon(Color colour, double startX, double startY, double radius, List<Point> points) {
        super(colour, startX, startY, radius, 0);
        this.points = points;
        this.sides = points.size();
        generateXY();
        calculateStrokeXY();
        freeDrawn = true;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public int getSides() {
        return sides;
    }

    public void setSides(int sides) {
        this.sides = sides;
    }

    private void generateXY() {
        this.xs = new double[sides];
        this.ys = new double[sides];
        for (int i = 0; i < sides; ++i) {
            xs[i] = points.get(i).getX();
            ys[i] = points.get(i).getY();
        }
    }

    private void calculateStrokeXY() {
        if(getTheStrokeWidth() == 0) {
            strokeXs = xs;
            strokeYs = ys;
        }
        this.strokeXs = new double[sides];
        this.strokeYs = new double[sides];
        for (int i = 0; i < sides; i++) {
            double xCos = (xs[i] - this.getStartX()) / this.getRadius();
            double ySin = (ys[i] - this.getStartY()) / this.getRadius();
            strokeXs[i] = this.getStartX() + (this.getRadius() - this.getTheStrokeWidth()) * xCos;
            strokeYs[i] = this.getStartY() + (this.getRadius() - this.getTheStrokeWidth()) * ySin;
        }
    }

    @Override
    public void resize(int modifier) {
        if(modifier == 0) {
            return;
        }
        double[] newX = new double[sides];
        double[] newY = new double[sides];
        for (int i = 0; i < sides; i++) {
            double xCos = (xs[i] - this.getStartX()) / this.getRadius();
            double ySin = (ys[i] - this.getStartY()) / this.getRadius();
            newX[i] = this.getStartX() + (this.getRadius() + modifier) * xCos;
            newY[i] = this.getStartY() + (this.getRadius() + modifier) * ySin;
            points.set(i, new Point(newX[i], newY[i]));
            xs[i] = newX[i];
            ys[i] = newY[i];
        }
        this.setRadius(this.getRadius() + modifier);
        calculateStrokeXY();
    }

    @Override
    public void move(int x, int y) {
        this.setStartX(this.getStartX() + x);
        this.setStartY(this.getStartY() + y);
        for(int i = 0; i < points.size(); ++i) {
            this.xs[i] = this.xs[i] + x;
            this.ys[i] = this.ys[i] + y;
            this.strokeXs[i] = this.strokeXs[i] + x;
            this.strokeYs[i] = this.strokeYs[i] + y;
            points.set(i, new Point(xs[i], ys[i]));
        }
    }

    @Override
    public void rotate(int degrees) {
        if(degrees == 0 || degrees == 360) {
            return;
        }
        System.err.println(degrees);
        double a = 0, b = 0;
        for(int i = 0; i < sides; ++i) {
            a += xs[i];
            b += ys[i];
        }
        a /= sides;
        b /= sides;

        double[] newX = new double[sides];
        double[] newY = new double[sides];
        for(int i = 0; i < sides; ++i) {
            newX[i] = xs[i] - a;
            newY[i] = ys[i] - b;
        }

        double degSin = Math.sin(Math.toRadians(degrees));
        double degCos = Math.cos(Math.toRadians(degrees));
        for(int i = 0; i < sides; ++i) {
            double oldX = newX[i];
            double oldY = newY[i];
            newX[i] = oldX * degCos - oldY * degSin;
            newY[i] = oldX * degSin + oldY * degCos;
        }

        for(int i = 0; i < sides; ++i) {
            this.xs[i] = (newX[i] + a);
            this.ys[i] = (newY[i] + b);
            this.points.set(i, new Point(this.xs[i], this.ys[i]));
        }
        this.setRotation((this.getRotation() + degrees) % 360);
        calculateStrokeXY();
    }

    public void resetRotation() {
        this.rotate((int) (360 - this.getRotation()));
    }

    @Override
    public void drawObject(GraphicsContext gc) {
        gc.setStroke(this.getStrokeColor());
        gc.setLineWidth(this.getTheStrokeWidth());
        gc.setFill(this.getColour());
        gc.fillPolygon(xs, ys, sides);
        gc.strokePolygon(strokeXs, strokeYs, sides);
    }

    @Override
    public boolean contains(double x, double y) {
        if(x < this.getStartX() - getRadius() || y < this.getStartY() - getRadius() || x > this.getStartX() + getRadius() || y > this.getStartY() + getRadius()) {
            return false;
        }
        if(freeDrawn) {
            return slowContains(x, y);
        }
        Point p = new Point(x, y);
        int indexOfEdgesLeft = -1;
        int indexOfEdgesRight = -1;
        for(int i = 0; i < sides - 1; ++i) {
            if(MathUtils.isPointOnLine(p, points.get(i), points.get(i + 1))) {
                return true;
            }
            if((points.get(i).getY() > p.getY() && points.get(i + 1).getY() < p.getY()) || (points.get(i).getY() < p.getY() && points.get(i + 1).getY() > p.getY())) {
                if(points.get(i).getX() > this.getStartX()) {
                    indexOfEdgesRight = i;
                }
                else {
                    indexOfEdgesLeft = i;
                }
            }
        }
        if((points.get(0).getY() > p.getY() && points.get(sides - 1).getY() < p.getY()) || (points.get(0).getY() < p.getY() && points.get(sides - 1).getY() > p.getY())) {
            if(points.get(0).getX() > this.getStartX()) {
                indexOfEdgesRight = sides - 1;
            }
            else {
                indexOfEdgesLeft = sides - 1;
            }
        }

        if(indexOfEdgesLeft == -1 || indexOfEdgesRight == -1) {
            return false;
        }

        if(x < this.getStartX()) {
            return (!MathUtils.intersect(p, new Point(this.getStartX() + getRadius(), p.getY()), points.get(indexOfEdgesLeft), points.get((indexOfEdgesLeft + 1) % sides)) &&
                    MathUtils.intersect(p, new Point(this.getStartX() + getRadius(), p.getY()), points.get(indexOfEdgesRight), points.get((indexOfEdgesRight + 1) % sides)));
        }
        else {
            return (!MathUtils.intersect(p, new Point(this.getStartX() - getRadius(), p.getY()), points.get(indexOfEdgesRight), points.get((indexOfEdgesRight + 1) % sides)) &&
                    MathUtils.intersect(p, new Point(this.getStartX() - getRadius(), p.getY()), points.get(indexOfEdgesLeft), points.get((indexOfEdgesLeft + 1) % sides)));
        }
    }

    private boolean slowContains(double x, double y) {
        Point point = new Point(x, y);
        Point extreme = new Point((x < this.getStartX() ? DrawingPanel.WIDTH : 0), y);
        int side = 0, count = 0;
        do {
            int next = (side + 1) % sides;
            if(MathUtils.intersect(points.get(side), points.get(next), point, extreme)) {
                if(MathUtils.pointsOrientation(points.get(side), point, points.get(next)) == 0) {
                    return MathUtils.isPointOnLine(point, points.get(side), points.get(next));
                }
                ++count;
            }
            side = next;
        } while (side != 0);

        return count % 2 == 1;
    }

    @Override
    public String toString() {
        return "Polygon ";
    }

}
