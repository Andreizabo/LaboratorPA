import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class ConfigPanel extends GridPane {
    private final MainFrame frame;

    //For circles and polygons
    private ColorPicker colorPicker;
    private Label colorLabel;
    private Spinner<Integer> radiusPicker;
    private Label radiusLabel;
    private ColorPicker strokeColorPicker;
    private Label strokeColorLabel;
    private Spinner<Integer> strokePicker;
    private Label strokeLabel;
    //For polygons and free draw
    private Label edgesLabel;
    private Spinner<Integer> edgePicker;
    //For polygons
    private Label rotatorLabel;
    private Spinner<Integer> rotator;
    //For eraser
    private static Label shapeNo;
    //For all
    private Label shapeLabel;
    private ObservableList<String> shapeList;
    private ComboBox shapes;

    public ConfigPanel(MainFrame frame) {
        this.frame = frame;
        init();
    }

    private void init() {
        shapeLabel = new Label("Tool");
        shapeList = FXCollections.observableArrayList("Circle", "Polygon", "Free Draw", "Eraser");
        shapes = new ComboBox<>(shapeList);
        shapes.setValue("Circle");
        shapes.setOnAction((a) -> {
            if(shapes.getValue().equals("Circle")) {
                this.getColumnConstraints().clear();
                this.getChildren().clear();
                this.add(shapeLabel, 0, 0);
                this.add(shapes, 0, 1);
                initCircle();
            }
            else if(shapes.getValue().equals("Polygon")) {
                this.getColumnConstraints().clear();
                this.getChildren().clear();
                this.add(shapeLabel, 0, 0);
                this.add(shapes, 0, 1);
                initPolygon();
            }
            else if(shapes.getValue().equals("Free Draw")) {
                this.getColumnConstraints().clear();
                this.getChildren().clear();
                this.add(shapeLabel, 0, 0);
                this.add(shapes, 0, 1);
                initFreeDraw();
            }
            else if(shapes.getValue().equals("Eraser")) {
                this.getColumnConstraints().clear();
                this.getChildren().clear();
                this.add(shapeLabel, 0, 0);
                this.add(shapes, 0, 1);
                initEraser();
            }
        });
        this.add(shapeLabel, 0, 0);
        this.add(shapes, 0, 1);
        initCircle();
    }

    private void initCircle() {
        colorLabel = new Label("Fill colour");
        colorPicker = new ColorPicker(Color.BLUE);
        radiusLabel = new Label("Diameter");
        radiusPicker = new Spinner<>(1, 250, 50, 1);
        radiusPicker.setEditable(true);
        strokeColorLabel = new Label("Stroke colour");
        strokeColorPicker = new ColorPicker(Color.BLACK);
        strokeLabel = new Label("Stroke width");
        strokePicker = new Spinner<>(1, 50, 2, 1);
        strokePicker.setEditable(true);

        this.add(colorLabel, 1, 0);
        this.add(colorPicker, 1, 1);
        this.add(radiusLabel, 2, 0);
        this.add(radiusPicker, 2, 1);
        this.add(strokeColorLabel, 3, 0);
        this.add(strokeColorPicker, 3, 1);
        this.add(strokeLabel, 4, 0);
        this.add(strokePicker, 4, 1);

        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(20);
        this.getColumnConstraints().addAll(column, column, column, column, column);
    }

    private void initPolygon() {
        colorLabel = new Label("Fill colour");
        colorPicker = new ColorPicker(Color.BLUE);
        radiusLabel = new Label("Size");
        radiusPicker = new Spinner<>(1, 250, 100, 1);
        radiusPicker.setEditable(true);
        edgesLabel = new Label("Number of edges");
        edgePicker = new Spinner<>(3, 10, 3, 1);
        edgePicker.setEditable(true);
        rotatorLabel = new Label("Rotation");
        rotator = new Spinner<>(0, 360, 0, 15);
        rotator.setEditable(true);
        strokeColorLabel = new Label("Stroke colour");
        strokeColorPicker = new ColorPicker(Color.BLACK);
        strokeLabel = new Label("Stroke width");
        strokePicker = new Spinner<>(1, 50, 2, 1);
        strokePicker.setEditable(true);

        this.add(colorLabel, 1, 0);
        this.add(colorPicker, 1, 1);
        this.add(radiusLabel, 2, 0);
        this.add(radiusPicker, 2, 1);
        this.add(edgesLabel, 3, 0);
        this.add(edgePicker, 3, 1);
        this.add(rotatorLabel, 4, 0);
        this.add(rotator, 4, 1);
        this.add(strokeColorLabel, 5, 0);
        this.add(strokeColorPicker, 5, 1);
        this.add(strokeLabel, 6, 0);
        this.add(strokePicker, 6, 1);

        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(15);
        this.getColumnConstraints().addAll(column, column, column, column, column, column, column);
    }

    private void initFreeDraw() {
        colorLabel = new Label("Colour picker");
        colorPicker = new ColorPicker(Color.BLUE);
        radiusLabel = new Label("Size picker");
        radiusPicker = new Spinner<>(1, 250, 10, 1);
        radiusPicker.setEditable(true);
        edgesLabel = new Label("Max edges");
        edgePicker = new Spinner<>(3, 500, 100, 1);
        edgePicker.setEditable(true);

        this.add(colorLabel, 1, 0);
        this.add(colorPicker, 1, 1);
        this.add(radiusLabel, 2, 0);
        this.add(radiusPicker, 2, 1);
        this.add(edgesLabel, 3, 0);
        this.add(edgePicker, 3, 1);

        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(25);
        this.getColumnConstraints().addAll(column, column, column, column);
    }

    private void initEraser() {
        shapeNo = new Label();
        modifyEraserText();
        this.add(shapeNo, 1, 1);

        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(50);
        this.getColumnConstraints().addAll(column, column);
    }

    public static void modifyEraserText() {
        int numberOfShapes = DrawingPanel.getNumberOfDrawnShapes();
        if(numberOfShapes == 1) {
            shapeNo.setText(DrawingPanel.getNumberOfDrawnShapes() + " shape is currently drawn on the canvas");
        }
        else {
            shapeNo.setText(DrawingPanel.getNumberOfDrawnShapes() + " shapes are currently drawn on the canvas");
        }
    }

    public Color getColor() {
        return colorPicker.getValue();
    }

    public Integer getRadius() {
        return radiusPicker.getValue();
    }

    public Integer getEdges() {
        return edgePicker.getValue();
    }

    public String getSelectedShape() {
        return (String) shapes.getValue();
    }

    public Integer getRotation() {
        return rotator.getValue();
    }

    public Color getStrokeColor() {
        return strokeColorPicker.getValue();
    }

    public Integer getStrokeWidth() {
        return strokePicker.getValue();
    }
}
