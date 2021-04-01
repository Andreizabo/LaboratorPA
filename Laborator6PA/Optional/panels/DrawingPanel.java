import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;


public class DrawingPanel extends FlowPane {
    private final MainFrame frame;
    public final static int WIDTH = 800, HEIGHT = 500;

    public Canvas imageCanvas;
    public GraphicsContext graphicsContext;

    private static ArrayList<DrawableObject> drawnShapes;

    public DrawingPanel(MainFrame frame) {
        this.frame = frame;
        init();
    }

    private void init() {
        drawnShapes = new ArrayList<>();
        imageCanvas = new Canvas(WIDTH, HEIGHT);
        graphicsContext = imageCanvas.getGraphicsContext2D();

        graphicsContext.setLineWidth(2);
        graphicsContext.setStroke(Color.WHITE);
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillRect(0, 0, WIDTH, HEIGHT);

        imageCanvas.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if(frame.configPanel.getSelectedShape().equals("Circle")) {
                drawnShapes.add(new Circle(frame.configPanel.getColor(), mouseEvent.getX(), mouseEvent.getY(), frame.configPanel.getRadius(), frame.configPanel.getStrokeColor(), frame.configPanel.getStrokeWidth()));
                drawShapes();
            }
            else if(frame.configPanel.getSelectedShape().equals("Polygon")) {
                drawnShapes.add(new Polygon(frame.configPanel.getColor(), mouseEvent.getX(), mouseEvent.getY(), frame.configPanel.getRadius(), frame.configPanel.getEdges(), frame.configPanel.getRotation(), frame.configPanel.getStrokeColor(), frame.configPanel.getStrokeWidth()));
                drawShapes();
            }
            else if(frame.configPanel.getSelectedShape().equals("Eraser")) {
                checkForShapes(mouseEvent.getX(), mouseEvent.getY());
                drawShapes();
            }
        });
        imageCanvas.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            if(frame.configPanel.getSelectedShape().equals("Free Draw")) {
                drawnShapes.add(new FreeDrawShape(frame.configPanel.getColor(), mouseEvent.getX(), mouseEvent.getY(), frame.configPanel.getRadius(), frame.configPanel.getEdges()));
                drawShapes();
            }
        });
        imageCanvas.addEventFilter(MouseEvent.MOUSE_DRAGGED, mouseEvent -> {
            if(frame.configPanel.getSelectedShape().equals("Free Draw")) {
                if(!((FreeDrawShape)drawnShapes.get(drawnShapes.size() - 1)).isFinished()) {
                    ((FreeDrawShape)drawnShapes.get(drawnShapes.size() - 1)).addPoint(mouseEvent.getX(), mouseEvent.getY());
                }
                drawShapes();
            }
        });
        imageCanvas.addEventFilter(MouseEvent.MOUSE_RELEASED, mouseEvent -> {
            if(frame.configPanel.getSelectedShape().equals("Free Draw")) {
                ((FreeDrawShape)drawnShapes.get(drawnShapes.size() - 1)).setFinished(true);
                DrawableObject obj = ((FreeDrawShape)drawnShapes.get(drawnShapes.size() - 1)).checkShape();
                if(obj != null) {
                    drawnShapes.remove(drawnShapes.size() - 1);
                    drawnShapes.add(obj);
                }
                drawShapes();
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
        drawnShapes = new ArrayList<>();
    }

    public void drawShapes() {
        graphicsContext.setStroke(Color.WHITE);
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillRect(0, 0, WIDTH, HEIGHT);
        drawnShapes.forEach(d -> d.drawObject(graphicsContext));
    }

    public static int getNumberOfDrawnShapes() {
        return drawnShapes.size();
    }

    /**
     * Deletes the newest shape which contains the mouse coordinates
     * @param x The x coordinate of the mouse
     * @param y The y coordinate of the mouse
     */
    private void checkForShapes(double x, double y) {
        System.err.print(x + " " + y + "\n");
        for(int i = drawnShapes.size() - 1; i >= 0; --i) {
            if(drawnShapes.get(i).contains(x, y)) {
                drawnShapes.remove(i);
                ConfigPanel.modifyEraserText();
                return;
            }
        }
    }
}
