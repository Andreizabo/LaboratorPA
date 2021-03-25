import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DrawingPanel extends FlowPane {
    private final MainFrame frame;
    public final static int WIDTH = 512, HEIGHT = 512;

    public Canvas imageCanvas;
    public GraphicsContext graphicsContext;

    public DrawingPanel(MainFrame frame) {
        this.frame = frame;
        init();
    }

    private void init() {
        imageCanvas = new Canvas(WIDTH, HEIGHT);
        graphicsContext = imageCanvas.getGraphicsContext2D();

        graphicsContext.setLineWidth(2);
        graphicsContext.setStroke(Color.WHITE);
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillRect(0, 0, WIDTH, HEIGHT);

        imageCanvas.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if(frame.configPanel.getSelectedShape().equals("Circle")) {
                drawCircle(mouseEvent.getX(), mouseEvent.getY());
            }
            else {
                drawPolygon(mouseEvent.getX(), mouseEvent.getY());
            }
        });

        this.getChildren().add(imageCanvas);
    }

    public Image getImage() {
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        return imageCanvas.snapshot(params, null);
    }

    public void setImage(Image image) {
        graphicsContext.drawImage(image, 0, 0, WIDTH, HEIGHT);
    }

    public void resetImage() {
        graphicsContext.setStroke(Color.WHITE);
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillRect(0, 0, WIDTH, HEIGHT);
    }

    private void drawCircle(double x, double y) {
        graphicsContext.setStroke(Color.BLACK);
        graphicsContext.setFill(frame.configPanel.getColor());
        graphicsContext.fillOval(x - frame.configPanel.getRadius() / 2f, y - frame.configPanel.getRadius() / 2f, frame.configPanel.getRadius(), frame.configPanel.getRadius());
    }

    private void drawPolygon(double x, double y) {
        int edges = frame.configPanel.getEdges();
        RegularPolygon polygon = new RegularPolygon((int)x, (int)y, frame.configPanel.getRadius(), edges);
        polygon.rotate(frame.configPanel.getRotation());
        double[] xs = new double[edges];
        double[] ys = new double[edges];
        for(int i = 0; i < edges; ++i) {
            xs[i] = polygon.xpoints[i];
            ys[i] = polygon.ypoints[i];
        }

        graphicsContext.setStroke(Color.BLACK);
        graphicsContext.setFill(frame.configPanel.getColor());
        graphicsContext.fillPolygon(xs, ys, edges);
    }
}
