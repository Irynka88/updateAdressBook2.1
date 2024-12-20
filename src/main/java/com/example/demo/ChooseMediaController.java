package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ChooseMediaController {
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private ImageView imageView;
    @FXML
    private VBox vBox;

    @FXML
    public void handleChoiceSelection() {
        String choice = choiceBox.getValue();

        if (choice.equals("Фото")) {
            imageView.setVisible(true);
            imageView.setImage(new Image(getClass().getResource("/images/poltava.jpg").toExternalForm()));
            vBox.getChildren().clear();
        }
    }
}
