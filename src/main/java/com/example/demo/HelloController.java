package com.example.demo;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;

import java.io.IOException;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnEdit;
    @FXML
    private TextField searchField;
    @FXML
    private TableView<Contact> tableView;
    @FXML
    private Label contactCountLabel;

    private ObservableList<Contact> contacts = FXCollections.observableArrayList();

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    public void updateContactCount(){
        contactCountLabel.setText("Кількість записів : " + contacts.size());
    }

    @FXML
    public void initialize() {
        contacts.add(new Contact("Аліна Лин", "0684578294"));
        contacts.add(new Contact("Вікторія Нестерчук", "0687571598"));
        contacts.add(new Contact("Юлія Лизан", "0687268494"));

        TableColumn<Contact, String> nameColumn = new TableColumn<>("ПІП");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        TableColumn<Contact, String> phoneColumn = new TableColumn<>("Телефон");
        phoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhone()));

        tableView.getColumns().addAll(nameColumn, phoneColumn);
        tableView.setItems(contacts);

        tableView.getStylesheets().add(getClass().getResource("/com/example/demo/viewEditor.css").toExternalForm());


    }

    public void updateContact() {
        contactCountLabel.setText("Кількість записів: " + contacts.size());

    }

    @FXML
    public void showDialog(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add-dialog.fxml"));
            Parent parent = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Редагування");

            AddController addController = fxmlLoader.getController();
            addController.setContact(null);

            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(btnAdd.getScene().getWindow());
            stage.setMinHeight(300);
            stage.setMinWidth(400);
            stage.setResizable(false);
            stage.showAndWait();
            Contact contact = addController.getNewContact();
            if (contact != null){
                contacts.add(contact);
                tableView.setItems(contacts);
                updateContact();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showDeleteWarning(ActionEvent event) {
        Contact selectedContact = tableView.getSelectionModel().getSelectedItem();
        if (selectedContact != null ) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/delete-warning.fxml"));
                Parent parent = fxmlLoader.load();

                DeleteWarningController controller = fxmlLoader.getController();
                controller.setContactDelete(this, selectedContact);

                Stage stage = new Stage();
                stage.setTitle("Попередження");

                Scene scene = new Scene(parent);
                stage.setScene(scene);

                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(btnDelete.getScene().getWindow());

                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Виберіть контакт для видалення ");
            alert.showAndWait();

        }

    }
    @FXML
    public void searchContacts(){
        String query = searchField.getText().toLowerCase();
        ObservableList<Contact> filterContacts = FXCollections.observableArrayList();

        for (Contact contact : contacts){
            if (contact.getName().toLowerCase().contains(query) ||
                    contact.getPhone().toLowerCase().contains(query)){
                filterContacts.add(contact);
            }
        }
        tableView.setItems(filterContacts);
    }

    @FXML
    public void editContact() {
        Contact selectedContact = tableView.getSelectionModel().getSelectedItem();
        if (selectedContact != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add-dialog.fxml"));
                Parent parent = fxmlLoader.load();

                AddController controller = fxmlLoader.getController();
                controller.setContact(selectedContact);

                Stage stage = new Stage();
                stage.setTitle("Редагування");


                Scene scene = new Scene(parent);
                stage.setScene(scene);
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(btnEdit.getScene().getWindow());

                stage.showAndWait();
                Contact updateContact = controller.getNewContact();
                if (updateContact != null){
                    selectedContact.setName(updateContact.getName());
                    selectedContact.setPhone(updateContact.getPhone());
                    tableView.refresh();
                    updateContactCount();

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Попередження");
            alert.setHeaderText(null);
            alert.setContentText("Будь ласка, виберіть контакт для редагування.");
            alert.showAndWait();
        }
    }

    public void deleteContact(Contact contact){
        contacts.remove(contact);
        tableView.setItems(contacts);
        updateContactCount();
    }

}
