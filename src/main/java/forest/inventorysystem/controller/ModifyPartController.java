package forest.inventorysystem.controller;

import forest.inventorysystem.InventorySystem;
import forest.inventorysystem.model.InHouse;
import forest.inventorysystem.model.Outsourced;
import forest.inventorysystem.model.Part;
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
import forest.inventorysystem.model.InHouse;

import java.io.IOException;

public class ModifyPartController {

    @FXML
    private TextField minText;
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

    private Part selectedPart;

    /**
     * This method accepts a person to initialize on scene load
     *
     * @param part
     */

    public void initialize(Part part) {
        selectedPart = part;

        if (selectedPart instanceof InHouse) {
            inHouseButton.fire();
            machineIdText.setText(Integer.toString(((InHouse) selectedPart).getMachineId()));
        } else if (selectedPart instanceof Outsourced) {
            outsourcedButton.fire();
            machineIdText.setText(((Outsourced) selectedPart).getCompanyName());
        }

        idText.setText(Integer.toString(selectedPart.getId()));
        nameText.setText(selectedPart.getName());
        invText.setText(Integer.toString(selectedPart.getStock()));
        priceText.setText(Double.toString(selectedPart.getPrice()));
        maxText.setText(Integer.toString(selectedPart.getMax()));
        minText.setText(Integer.toString(selectedPart.getMin()));
    }


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
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 948, 337);
        stage.setTitle("C482 - Performance Assessment");
        stage.setScene(scene);
        stage.show();
    }
}
