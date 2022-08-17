package forest.inventorysystem.controller;

import forest.inventorysystem.InventorySystem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyPartController {

    @FXML
    private RadioButton inHouseButton;
    @FXML
    private RadioButton outsourcedButton;
    @FXML
    private Label machineIdLabel;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField machineIdText;
    @FXML
    private TextField maxText;
    @FXML
    private TextField priceText;
    @FXML
    private TextField invText;
    @FXML
    private TextField nameText;
    @FXML
    private TextField idText;



    // When In-House radio button is selected, Machine ID field is available.
    public void onInHouse(ActionEvent actionEvent) {
        machineIdLabel.setText("Machine ID");
    }

    // When Outsourced radio button is selected, Company Name field is available
    public void onOutsourced(ActionEvent actionEvent) {
        machineIdLabel.setText("Company Name");
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
