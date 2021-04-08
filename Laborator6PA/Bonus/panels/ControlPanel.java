import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;

public class ControlPanel extends GridPane {
    private final MainFrame frame;

    private Button save;
    private Button load;
    private Button reset;
    private Button exit;

    public ControlPanel(MainFrame frame) {
        this.frame = frame;
        init();
    }

    private void init() {
        save = new Button("Save");
        save.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files (*.png, *.jpeg, *.gif, *.bmp)", "*.png", "*.jpeg", "*.gif", "*.bmp");
            fileChooser.getExtensionFilters().add(extFilter);

            File file = fileChooser.showSaveDialog(frame.getStage());
            if(file != null) {
                String extension = file.getName().substring(1 + file.getName().lastIndexOf(".")).toLowerCase();
                try {
                    ImageIO.write(SwingFXUtils.fromFXImage(frame.canvas.getImage(), null), extension, file);
                } catch (IOException e) {
                    System.err.print("Could not save file, IO exception");
                    return;
                }
            }
        });

        load = new Button("Load");
        load.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files (*.png, *.jpeg, *.gif, *.bmp)", "*.png", "*.jpeg", "*.gif", "*.bmp");
            fileChooser.getExtensionFilters().add(extFilter);

            File file = fileChooser.showOpenDialog(frame.getStage());
            if(file != null) {
                try {
                    frame.canvas.setImage(new Image(file.toURI().toURL().toExternalForm()));
                } catch (MalformedURLException e) {
                    System.err.print("Could not save file, MalformedURLException exception");
                    return;
                }
            }
        });

        reset = new Button("Reset");
        reset.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> frame.canvas.resetImage());

        exit = new Button("Exit");
        exit.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> System.exit(1));

        this.add(save, 0, 0);
        this.add(load, 1, 0);
        this.add(reset, 2, 0);
        this.add(exit, 3, 0);

        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(25);
        this.getColumnConstraints().addAll(column, column, column, column);
    }
}
