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

    private ColorPicker colorPicker;
    private Label colorLabel;
    private Spinner<Integer> radiusPicker;
    private Label radiusLabel;
    private Label edgesLabel;
    private Spinner<Integer> edgePicker;
    private Label shapeLabel;
    private ObservableList<String> shapeList;
    private ComboBox shapes;

    public ConfigPanel(MainFrame frame) {
        this.frame = frame;
        init();
    }

    private void init() {
        shapeLabel = new Label("Shape");
        shapeList = FXCollections.observableArrayList("Circle", "Polygon");
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
            else {
                this.getColumnConstraints().clear();
                this.getChildren().clear();
                this.add(shapeLabel, 0, 0);
                this.add(shapes, 0, 1);
                initPolygon();
            }
        });
        this.add(shapeLabel, 0, 0);
        this.add(shapes, 0, 1);
        initCircle();
    }

    private void initCircle() {
        colorLabel = new Label("Colour picker");
        colorPicker = new ColorPicker(Color.BLUE);
        radiusLabel = new Label("Radius picker");
        radiusPicker = new Spinner<>(1, 250, 10, 1);
        radiusPicker.setEditable(true);

        this.add(colorLabel, 1, 0);
        this.add(colorPicker, 1, 1);
        this.add(radiusLabel, 2, 0);
        this.add(radiusPicker, 2, 1);

        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(33);
        this.getColumnConstraints().addAll(column, column, column);
    }

    private void initPolygon() {
        colorLabel = new Label("Colour picker");
        colorPicker = new ColorPicker(Color.BLUE);
        radiusLabel = new Label("Size picker");
        radiusPicker = new Spinner<>(1, 250, 10, 1);
        radiusPicker.setEditable(true);
        edgesLabel = new Label("Number of edges");
        edgePicker = new Spinner<>(3, 10, 3, 1);
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
}
