package forest.inventorysystem.controller;

import forest.inventorysystem.InventorySystem;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

import java.io.IOException;

public class ModifyPartController {

    public RadioButton inHouseButton;
    public RadioButton outsourcedButton;
    public Label machineID;
    public Button saveButton;
    public Button cancelButton;

    // When In-House radio button is selected, Machine ID field is available.
    public void onInHouse(ActionEvent actionEvent) {
        machineID.setText("Machine ID");
    }

    // When Outsourced radio button is selected, Company Name field is available
    public void onOutsourced(ActionEvent actionEvent) {
        machineID.setText("Company Name");
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
}
