package forest.inventorysystem.controller;

import forest.inventorysystem.InventorySystem;
import forest.inventorysystem.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

    public void onSaveButton(ActionEvent actionEvent) throws IOException {

        if (Integer.parseInt(minText.getText()) <= Integer.parseInt(maxText.getText())) {

            if (inHouseButton.isSelected()) {
                try {
                    selectedPart.setName(nameText.getText());
                    selectedPart.setStock(Integer.parseInt(invText.getText()));
                    selectedPart.setPrice(Double.parseDouble(priceText.getText()));
                    selectedPart.setMax(Integer.parseInt(maxText.getText()));
                    selectedPart.setMin(Integer.parseInt(minText.getText()));
                    ((InHouse) selectedPart).setMachineId(Integer.parseInt(machineIdText.getText()));
                } catch (Exception e) {
                    Alert exceptionAlert = new Alert(Alert.AlertType.ERROR, e.toString());
                    exceptionAlert.setTitle("Error");
                    exceptionAlert.setHeaderText("Please verify values in form.\nPlease refer to the following exception:");
                    exceptionAlert.showAndWait();
                }
            } else {
                try {
                    selectedPart.setStock(Integer.parseInt(invText.getText()));
                    selectedPart.setPrice(Double.parseDouble(priceText.getText()));
                    selectedPart.setMax(Integer.parseInt(maxText.getText()));
                    selectedPart.setMin(Integer.parseInt(minText.getText()));
                    ((Outsourced) selectedPart).setCompanyName(machineIdText.getText());
                    selectedPart.setName(nameText.getText());

                } catch (Exception e) {
                    Alert exceptionAlert = new Alert(Alert.AlertType.ERROR, "Error. Please check correct values in form:\n" + e.toString());
                    exceptionAlert.setTitle("Error");
                    exceptionAlert.setHeaderText("");
                    exceptionAlert.showAndWait();
                }

            }
            FXMLLoader fxmlLoader = new FXMLLoader(InventorySystem.class.getResource("MainScreen.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load(), 948, 337);
            stage.setTitle("C482 - Performance Assessment");
            stage.setScene(scene);
            stage.show();
        } else {
            Alert minMaxAlert = new Alert(Alert.AlertType.ERROR, "Minimum value MUST be less than or equal to Maximum value.");
            minMaxAlert.setTitle("Error: Min is larger than Max");
            minMaxAlert.setHeaderText("");
            minMaxAlert.showAndWait();
        }
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
