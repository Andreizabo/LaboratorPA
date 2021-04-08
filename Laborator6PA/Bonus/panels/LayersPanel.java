import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.GridPane;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class LayersPanel extends GridPane {
    private final MainFrame frame;
    private Label layersLabel;
    private ListView<String>  layers;
    private ObservableList<String> layersList;

    public LayersPanel(MainFrame frame) {
        this.frame = frame;
        init();
    }

    private void init() {
        layersLabel = new Label("Layers");
        layersList = FXCollections.observableArrayList();
        layers = new ListView<>();

        layers.setPrefWidth(100);
        layers.setPrefHeight(500);

        this.add(layers, 0, 0);
        this.add(layersLabel, 0, 1);

        setKeys();
    }

    public void addLayer(DrawableObject obj) {
        layersList.add(obj.toString() + "(" + layersList.size() + ")");
        layers.setItems(layersList);
    }

    public void removeLayer(int index) {
        layersList.remove(index);
        layers.setItems(layersList);
    }

    public void removeAll() {
        layersList = FXCollections.observableArrayList();
        layers.setItems(layersList);
    }

    private void setKeys() {
        frame.setOnKeyPressed(e -> {
            if (new KeyCodeCombination(KeyCode.Z, KeyCodeCombination.CONTROL_DOWN).match(e)) {
                ActionRecorder.undoAction();
            }
            else if (new KeyCodeCombination(KeyCode.Y, KeyCodeCombination.CONTROL_DOWN).match(e)) {
                ActionRecorder.redoAction();
            }
            else if(layers.getSelectionModel().getSelectedIndex() > -1) {
                if(e.getCode() == KeyCode.DELETE || e.getCode() == KeyCode.BACK_SPACE) {
                    frame.canvas.deleteShape(layers.getSelectionModel().getSelectedIndex());
                    ActionRecorder.addAction(new ActionDeleted(frame, layers.getSelectionModel().getSelectedIndex()));
                }

                DrawableObject obj = frame.canvas.getShapeAtIndex(layers.getSelectionModel().getSelectedIndex());

                if( new KeyCodeCombination(KeyCode.ADD, KeyCodeCombination.CONTROL_DOWN).match(e) ||
                        e.getCode() == KeyCode.ADD ||
                        new KeyCodeCombination(KeyCode.SUBTRACT, KeyCodeCombination.CONTROL_DOWN).match(e) ||
                        e.getCode() == KeyCode.SUBTRACT ||
                        new KeyCodeCombination(KeyCode.MULTIPLY, KeyCodeCombination.CONTROL_DOWN).match(e) ||
                        e.getCode() == KeyCode.MULTIPLY ||
                        new KeyCodeCombination(KeyCode.DIVIDE, KeyCodeCombination.CONTROL_DOWN).match(e) ||
                        e.getCode() == KeyCode.DIVIDE ||
                        new KeyCodeCombination(KeyCode.NUMPAD4, KeyCodeCombination.CONTROL_DOWN).match(e) ||
                        e.getCode() == KeyCode.NUMPAD4 ||
                        new KeyCodeCombination(KeyCode.NUMPAD6, KeyCodeCombination.CONTROL_DOWN).match(e) ||
                        e.getCode() == KeyCode.NUMPAD6 ||
                        new KeyCodeCombination(KeyCode.NUMPAD8, KeyCodeCombination.CONTROL_DOWN).match(e) ||
                        e.getCode() == KeyCode.NUMPAD8 ||
                        new KeyCodeCombination(KeyCode.NUMPAD2, KeyCodeCombination.CONTROL_DOWN).match(e) ||
                        e.getCode() == KeyCode.NUMPAD2) {
                    if(ActionRecorder.isLastActionFinished()) {
                        ActionRecorder.addAction(new ActionTransformed(obj.getStartX(), obj.getStartY(), obj.getRadius(), obj.getRotation(), layers.getSelectionModel().getSelectedIndex(), frame));
                    }
                }

                if (new KeyCodeCombination(KeyCode.ADD, KeyCodeCombination.CONTROL_DOWN).match(e)) {
                    frame.canvas.resizeShape(layers.getSelectionModel().getSelectedIndex(), 5);
                } else if (e.getCode() == KeyCode.ADD) {
                    frame.canvas.resizeShape(layers.getSelectionModel().getSelectedIndex(), 1);
                }

                if (new KeyCodeCombination(KeyCode.SUBTRACT, KeyCodeCombination.CONTROL_DOWN).match(e)) {
                    frame.canvas.resizeShape(layers.getSelectionModel().getSelectedIndex(), -5);
                } else if (e.getCode() == KeyCode.SUBTRACT) {
                    frame.canvas.resizeShape(layers.getSelectionModel().getSelectedIndex(), -1);
                }

                if (new KeyCodeCombination(KeyCode.MULTIPLY, KeyCodeCombination.CONTROL_DOWN).match(e)) {
                    frame.canvas.rotateShape(layers.getSelectionModel().getSelectedIndex(), 10);
                } else if (e.getCode() == KeyCode.MULTIPLY) {
                    frame.canvas.rotateShape(layers.getSelectionModel().getSelectedIndex(), 1);
                }

                if (new KeyCodeCombination(KeyCode.DIVIDE, KeyCodeCombination.CONTROL_DOWN).match(e)) {
                    frame.canvas.rotateShape(layers.getSelectionModel().getSelectedIndex(), -10);
                }
                if (e.getCode() == KeyCode.DIVIDE) {
                    frame.canvas.rotateShape(layers.getSelectionModel().getSelectedIndex(), -1);
                }

                if (new KeyCodeCombination(KeyCode.NUMPAD4, KeyCodeCombination.CONTROL_DOWN).match(e)) {
                    frame.canvas.moveShape(layers.getSelectionModel().getSelectedIndex(), -5, 0);
                } else if (e.getCode() == KeyCode.NUMPAD4) {
                    frame.canvas.moveShape(layers.getSelectionModel().getSelectedIndex(), -1, 0);
                }
                if (new KeyCodeCombination(KeyCode.NUMPAD6, KeyCodeCombination.CONTROL_DOWN).match(e)) {
                    frame.canvas.moveShape(layers.getSelectionModel().getSelectedIndex(), 5, 0);
                } else if (e.getCode() == KeyCode.NUMPAD6) {
                    frame.canvas.moveShape(layers.getSelectionModel().getSelectedIndex(), 1, 0);
                }
                if (new KeyCodeCombination(KeyCode.NUMPAD8, KeyCodeCombination.CONTROL_DOWN).match(e)) {
                    frame.canvas.moveShape(layers.getSelectionModel().getSelectedIndex(), 0, -5);
                } else if (e.getCode() == KeyCode.NUMPAD8) {
                    frame.canvas.moveShape(layers.getSelectionModel().getSelectedIndex(), 0, -1);
                }
                if (new KeyCodeCombination(KeyCode.NUMPAD2, KeyCodeCombination.CONTROL_DOWN).match(e)) {
                    frame.canvas.moveShape(layers.getSelectionModel().getSelectedIndex(), 0, 5);
                } else if (e.getCode() == KeyCode.NUMPAD2) {
                    frame.canvas.moveShape(layers.getSelectionModel().getSelectedIndex(), 0, 1);
                }
            }
        });

        frame.setOnKeyReleased(e -> {
            if(layers.getSelectionModel().getSelectedIndex() > -1) {
                if (new KeyCodeCombination(KeyCode.ADD, KeyCodeCombination.CONTROL_DOWN).match(e) ||
                        e.getCode() == KeyCode.ADD ||
                        new KeyCodeCombination(KeyCode.SUBTRACT, KeyCodeCombination.CONTROL_DOWN).match(e) ||
                        e.getCode() == KeyCode.SUBTRACT ||
                        new KeyCodeCombination(KeyCode.MULTIPLY, KeyCodeCombination.CONTROL_DOWN).match(e) ||
                        e.getCode() == KeyCode.MULTIPLY ||
                        new KeyCodeCombination(KeyCode.DIVIDE, KeyCodeCombination.CONTROL_DOWN).match(e) ||
                        e.getCode() == KeyCode.DIVIDE ||
                        new KeyCodeCombination(KeyCode.NUMPAD4, KeyCodeCombination.CONTROL_DOWN).match(e) ||
                        e.getCode() == KeyCode.NUMPAD4 ||
                        new KeyCodeCombination(KeyCode.NUMPAD6, KeyCodeCombination.CONTROL_DOWN).match(e) ||
                        e.getCode() == KeyCode.NUMPAD6 ||
                        new KeyCodeCombination(KeyCode.NUMPAD8, KeyCodeCombination.CONTROL_DOWN).match(e) ||
                        e.getCode() == KeyCode.NUMPAD8 ||
                        new KeyCodeCombination(KeyCode.NUMPAD2, KeyCodeCombination.CONTROL_DOWN).match(e) ||
                        e.getCode() == KeyCode.NUMPAD2) {
                    DrawableObject obj = frame.canvas.getShapeAtIndex(layers.getSelectionModel().getSelectedIndex());
                    ((ActionTransformed) ActionRecorder.getLastAction()).endAction(obj.getStartX(), obj.getStartY(), obj.getRadius(), obj.getRotation());
                }
            }
        });
    }
}
