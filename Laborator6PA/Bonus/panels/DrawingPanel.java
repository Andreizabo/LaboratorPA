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

    private Image currentImage = null;

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
                frame.layers.addLayer(drawnShapes.get(drawnShapes.size() - 1));
                ActionRecorder.addAction(new ActionCreated(frame, drawnShapes.size() - 1));
                drawShapes();
            }
            else if(frame.configPanel.getSelectedShape().equals("Polygon")) {
                drawnShapes.add(new Polygon(frame.configPanel.getColor(), mouseEvent.getX(), mouseEvent.getY(), frame.configPanel.getRadius(), frame.configPanel.getEdges(), frame.configPanel.getRotation(), frame.configPanel.getStrokeColor(), frame.configPanel.getStrokeWidth()));
                frame.layers.addLayer(drawnShapes.get(drawnShapes.size() - 1));
                ActionRecorder.addAction(new ActionCreated(frame, drawnShapes.size() - 1));
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
                frame.layers.addLayer(drawnShapes.get(drawnShapes.size() - 1));
                ActionRecorder.addAction(new ActionCreated(frame, drawnShapes.size() - 1));
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
        this.currentImage = image;
        drawShapes();
    }

    public void resetImage() {
        currentImage = null;
        graphicsContext.setStroke(Color.WHITE);
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillRect(0, 0, WIDTH, HEIGHT);
        drawnShapes = new ArrayList<>();
        frame.layers.removeAll();
        ActionRecorder.reset();
    }

    public void resizeShape(int index, int modifier) {
        drawnShapes.get(index).resize(modifier);
        drawShapes();
    }

    public void rotateShape(int index, int degrees) {
        drawnShapes.get(index).rotate(degrees);
        drawShapes();
    }

    public void deleteShape(int index) {
        drawnShapes.remove(index);
        frame.layers.removeLayer(index);
        try {
            ConfigPanel.modifyEraserText();
        }
        catch (NullPointerException ignored) {
            // Not in eraser
        }
        drawShapes();
    }

    public void addShape(int index, DrawableObject obj) {
        if(index == -1) {
            drawnShapes.add(obj);
        }
        else {
            drawnShapes.add(index, obj);
        }
        frame.layers.addLayer(drawnShapes.get(drawnShapes.size() - 1));
        drawShapes();
    }

    public DrawableObject getShapeAtIndex(int index) {
        return drawnShapes.get(index);
    }

    public void moveShape(int index, int x, int y) {
        drawnShapes.get(index).move(x, y);
        drawShapes();
    }

    public void drawShapes() {
        if(currentImage != null) {
            graphicsContext.drawImage(currentImage, 0, 0, WIDTH, HEIGHT);
        }
        else {
            graphicsContext.setStroke(Color.WHITE);
            graphicsContext.setFill(Color.WHITE);
            graphicsContext.fillRect(0, 0, WIDTH, HEIGHT);
        }
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
                ActionRecorder.addAction(new ActionDeleted(frame, i));
                drawnShapes.remove(i);
                frame.layers.removeLayer(i);
                ConfigPanel.modifyEraserText();
                return;
            }
        }
    }
}
