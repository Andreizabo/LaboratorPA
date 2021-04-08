import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GraphicsMain extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        BorderPane mainPane = new MainFrame(stage);
        stage.setMinHeight(640);
        stage.setMinWidth(940);

        Scene scene = new Scene(mainPane, 900, 500);

        stage.setTitle("Free Drawer");
        stage.setScene(scene);
        stage.show();
    }
}
