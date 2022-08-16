package forest.inventorysystem.controller;

import forest.inventorysystem.InventorySystem;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AddProductController {
    public Button addButton;
    public Button saveButton;
    public Button cancelButton;
    public Button removePartsButton;

    public void onAddButton(ActionEvent actionEvent) {
    }

    public void onSaveButton(ActionEvent actionEvent) {
    }

    // When the Cancel button is clicked, user is returned to MainScreen
    public void onCancelButton(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InventorySystem.class.getResource("MainScreen.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 948, 337);
        stage.setTitle("C482 - Performance Assessment");
        stage.setScene(scene);
        stage.show();
    }

    public void onRemovePartsButton(ActionEvent actionEvent) {
    }
}
