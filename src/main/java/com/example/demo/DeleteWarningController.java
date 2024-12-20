package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DeleteWarningController {

    @FXML
    private Button confirmButton;

    @FXML
    private Button cancelButton;
    private HelloController mainController;
    private Contact contactDelete;

    public void setContactDelete(HelloController  controller, Contact contact) {
        this.mainController = controller;
        this.contactDelete = contact;
    }

    @FXML
    public void confirmDelete() {
        mainController.deleteContact(contactDelete);
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Успішно видалено");
        alert.setHeaderText(null);
        alert.setContentText("Ви успішно видалили запис.");
        alert.showAndWait();

        closeWindow();
    }

    @FXML
    public void cancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }
}
