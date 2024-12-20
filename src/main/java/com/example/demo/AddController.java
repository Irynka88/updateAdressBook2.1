package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddController {
    @FXML
    private TextField nameField;

    @FXML
    private TextField phoneField;

    private Contact contact;
    @FXML
    private Button saveButton;

    @FXML
    public void saveContact() {
        String name = nameField.getText();
        String phone = phoneField.getText();

        if(!name.isEmpty() && !phone.isEmpty()){
            if (contact != null){
                contact.setName(name);
                contact.setPhone(phone);
            } else{
                contact = new Contact(name, phone);
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Успішно збережено");
            alert.setHeaderText(null);
            alert.setContentText("Запис успішно збережений!");
            alert.showAndWait();

            ((Stage) saveButton.getScene().getWindow()).close();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Помилка");
            alert.setHeaderText(null);
            alert.setContentText("Будь ласка , заповніть всі поля.");
            alert.showAndWait();
        }

    }

    @FXML
    public void cancel(){
        ((Stage) saveButton.getScene().getWindow()).close();
    }
    public Contact getNewContact(){
        return contact;
    }


    public void setContact(Contact contact) {
        this.contact = contact;
        if (contact != null) {
            nameField.setText(contact.getName());
            phoneField.setText(contact.getPhone());
        }
    }


}




