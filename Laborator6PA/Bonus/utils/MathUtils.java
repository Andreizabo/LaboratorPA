public class MathUtils {
    private static final double ERROR_MARGIN = 0.1;

    /**
     * Checks if point P is on the line between point X and point Y
     * @param p the concerned point
     * @param x the starting point of the line
     * @param y the ending point of the line
     * @return whether the point is on the line
     */
    public static boolean isPointOnLine(Point p, Point x, Point y) {
        //return (p.getX() <= Math.max(x.getX(), y.getX()) && p.getX() >= Math.min(x.getX(), y.getX()) && p.getY() <= Math.max(x.getY(), y.getY()) && p.getY() >= Math.min(x.getY(), y.getY()));
        return (distancePoints(p, x) + distancePoints(p, y) - distancePoints(x, y) < ERROR_MARGIN);
    }

    /**
     * Checks if point P is on the line between point X and point Y, with a custom error margin
     * @param p the concerned point
     * @param x the starting point of the line
     * @param y the ending point of the line
     * @param error theh error margin
     * @return whether the point is on the line
     */
    public static boolean isPointOnLineCustom(Point p, Point x, Point y, double error) {
        return (distancePoints(p, x) + distancePoints(p, y) - distancePoints(x, y) < error);
    }

    /**
     * Calculates the distance between two points
     * @param x The first point
     * @param y The second point
     * @return The distance between the two points
     */
    public static double distancePoints(Point x, Point y) {
        return Math.sqrt(Math.pow(x.getX() - y.getX(), 2) + Math.pow(x.getY() - y.getY(), 2));
    }

    /**
     * Returns the orientation of three points
     * @param x The first point
     * @param y The second point
     * @param z The third point
     * @return The orientation of the three points
     */
    public static int pointsOrientation(Point x, Point y, Point z) {
        int value = ((int)y.getY() - (int)x.getY()) * ((int)z.getX() - (int)y.getX()) - ((int)y.getX() - (int)x.getX()) * ((int)z.getY() - (int)y.getY());

        if(value == 0) {
            return 0; // Collinear
        }
        else {
            return value > 0 ? 1 : 2; // Clockwise or Counterclockwise
        }
    }

    /**
     * Checks if two lines intersect
     * @param p1 Point 1 of line 1
     * @param p2 Point 2 of line 1
     * @param p3 Point 1 of line 2
     * @param p4 Point 2 of line 2
     * @return Whether or not the lines intersect
     */
    public static boolean intersect(Point p1, Point p2, Point p3, Point p4) {
        int orientation1 = pointsOrientation(p1, p2, p3);
        int orientation2 = pointsOrientation(p1, p2, p4);
        int orientation3 = pointsOrientation(p3, p4, p1);
        int orientation4 = pointsOrientation(p3, p4, p2);

        if(orientation1 != orientation2 && orientation3 != orientation4) {
            return true;
        }

        if(orientation1 == 0 && isPointOnLine(p1, p2, p3)) {
            return true;
        }
        if(orientation2 == 0 && isPointOnLine(p1, p2, p4)) {
            return true;
        }
        if(orientation3 == 0 && isPointOnLine(p3, p4, p1)) {
            return true;
        }
        if(orientation4 == 0 && isPointOnLine(p4, p4, p2)) {
            return true;
        }

        return false;
    }
}
