import java.awt.*;

public class RegularPolygon extends Polygon {
    private int sides;

    public RegularPolygon(int x0, int y0, int radius, int sides) {
        this.sides = sides;
        double alpha = 2 * Math.PI / sides;
        for (int i = 0; i < sides; i++) {
            double x = x0 + radius * Math.cos(alpha * i);
            double y = y0 + radius * Math.sin(alpha * i);
            this.addPoint((int) x, (int) y);
        }
    }

    public void rotate(int degrees) {
        if(degrees == 0 || degrees == 360) {
            return;
        }
        double a = 0, b = 0;
        for(int i = 0; i < sides; ++i) {
            a += xpoints[i];
            b += ypoints[i];
        }
        a /= sides;
        b /= sides;

        double[] newX = new double[sides];
        double[] newY = new double[sides];
        for(int i = 0; i < sides; ++i) {
            newX[i] = xpoints[i] - a;
            newY[i] = ypoints[i] - b;
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
            this.xpoints[i] = (int)(newX[i] + a);
            this.ypoints[i] = (int)(newY[i] + b);
        }
    }
}