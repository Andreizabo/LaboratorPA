import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainFrame extends BorderPane {
    private Stage stage;

    public ConfigPanel configPanel;
    public ControlPanel controlPanel;
    public DrawingPanel canvas;

    public MainFrame(Stage stage) {
        this.stage = stage;
        init();
    }

    private void init() {
        configPanel = new ConfigPanel(this);
        this.setTop(configPanel);
        controlPanel = new ControlPanel(this);
        this.setBottom(controlPanel);
        canvas = new DrawingPanel(this);
        this.setCenter(canvas);
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
