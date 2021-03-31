import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;

public class FreeDrawShape extends DrawableObject {

    private static final double ERROR_MARGIN = 5;

    private ArrayList<Point> points;
    private boolean finished;

    public FreeDrawShape(Color colour, double startX, double startY, double radius) {
        super(colour, startX, startY, radius);
        points = new ArrayList<>();
        points.add(new Point(startX, startY));
        finished = false;
    }

    public void addPoint(double x, double y) {
        for(Point p : points) {
            if(x == p.getX() && y == p.getY()) {
                return;
            }
        }
        points.add(new Point(x, y));
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    /**
     * Checks if what was drawn is a pre-defined shape
     * Can identify lines, circles and triangles. (And sometimes pentagons)
     * @return A pre-defined shape, or null if none could be matched
     */
    public DrawableObject checkShape() {
        DrawableObject obj = isCircle();
        if(obj != null) {
            return obj;
        }
        else {
            obj = isLine();
            if(obj != null) {
                return obj;
            }
            else {
                obj = isPolygon();
                if(obj != null) {
                    return obj;
                }
                else {
                    return null;
                }
            }
        }
    }

    private Circle isCircle() {
        Point center = new Point(0, 0);

        for(Point p : points) {
            center.setX(center.getX() + p.getX());
            center.setY(center.getY() + p.getY());
        }

        center.setX(center.getX() / points.size());
        center.setY(center.getY() / points.size());

        double maxDiff = 0;
        double minDiff = DrawingPanel.WIDTH;

        for(Point p : points) {
            double diff = MathUtils.distancePoints(p, center);
            if(diff > maxDiff) {
                maxDiff = diff;
            }
            if(diff < minDiff) {
                minDiff = diff;
            }
        }
        System.err.print(maxDiff - minDiff + "\n");

        if(maxDiff - minDiff < ERROR_MARGIN * (minDiff + maxDiff) / 12) {
            return new Circle(Color.TRANSPARENT, center.getX(), center.getY(), minDiff + maxDiff, this.getColour(), (int)this.getRadius());
        }
        else {
            return null;
        }
    }

    private Line isLine() {
        Point start = points.get(0);
        Point finish = points.get(points.size() - 1);

        for(int i = 1; i < points.size() - 1; ++i) {
            if(!MathUtils.isPointOnLineCustom(points.get(i), start, finish, ERROR_MARGIN)) {
                return null;
            }
        }

        return new Line(this.getColour(), start.getX(), start.getY(), this.getRadius(), start, finish);
    }

    private Polygon isPolygon() {
        Point center = new Point(0, 0);

        for(Point p : points) {
            center.setX(center.getX() + p.getX());
            center.setY(center.getY() + p.getY());
        }

        center.setX(center.getX() / points.size());
        center.setY(center.getY() / points.size());

        Point furthestPoint = new Point(0, 0);
        int furthestPointIndex = 0;
        double distance = 0;

        for(Point p : points) {
            if(MathUtils.distancePoints(p, center) > distance) {
                distance = MathUtils.distancePoints(p, center);
                furthestPoint = p;
            }
        }

        furthestPointIndex = points.indexOf(furthestPoint);

        int sides = 3;
        double lowestDifference = DrawingPanel.WIDTH;
        int lowestDifferenceSides = 0;
        int lowestDifferenceRotation = 0;
        while (sides < 10) {
            for(int rotation = 0; rotation < 360; rotation += 5) {
                int step = (this.points.size() - 1) / sides;
                double difference = 0;
                double alpha = 2 * Math.PI / sides;

                for (int i = 0; i < sides; ++i) {
                    double x = center.getX() + distance * Math.cos(alpha * i + Math.toRadians(rotation));
                    double y = center.getY() + distance * Math.sin(alpha * i + Math.toRadians(rotation));
                    difference += MathUtils.distancePoints(new Point(x, y), points.get((furthestPointIndex + step * i) % sides));
                }
                difference /= sides;
                if(sides < 5) {
                    System.err.println(difference + " diff " + sides);
                }
                if (difference < lowestDifference) {
                    lowestDifference = difference;
                    lowestDifferenceSides = sides;
                    lowestDifferenceRotation = rotation;
                }
            }
            ++sides;
        }

        if(lowestDifference < ERROR_MARGIN * 20) {
            return new Polygon(Color.TRANSPARENT, center.getX(), center.getY(), distance, lowestDifferenceSides, lowestDifferenceRotation, this.getColour(), (int) this.getRadius());
        }

        return null;
    }

    @Override
    public void drawObject(GraphicsContext gc) {
        points.forEach(p -> {
            gc.setFill(this.getColour());
            gc.fillOval(p.getX() - this.getRadius() / 2, p.getY() - this.getRadius() / 2, this.getRadius(), this.getRadius());
        });
    }

    @Override
    public boolean contains(double x, double y) {
        for(Point p : points) {
            if(MathUtils.distancePoints(new Point(x, y), new Point(p.getX(), p.getY())) <= this.getRadius() / 2) {
                return true;
            }
        }
        return false;
    }
}
