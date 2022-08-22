package forest.inventorysystem.controller;

import forest.inventorysystem.InventorySystem;
import forest.inventorysystem.model.InHouse;
import forest.inventorysystem.model.Inventory;
import forest.inventorysystem.model.Outsourced;
import forest.inventorysystem.model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

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


    // Not working, need to figure out
    public void onSaveButton(ActionEvent actionEvent) throws IOException {
        try {
            int id = Integer.parseInt(idText.getText());
            String name = nameText.getText();
            double price = Double.parseDouble(priceText.getText());
            int stock = Integer.parseInt(invText.getText());
            int min = Integer.parseInt(minText.getText());
            int max = Integer.parseInt(maxText.getText());
            int partIndex = Inventory.getAllParts().indexOf(selectedPart);

            if (stock > max || stock < min || stock < 0) {
                Alert stockError = new Alert(Alert.AlertType.ERROR, "The error is due to one of the following:\nInventory is equal or less than zero.\nInventory is less than Min or greater than Max.\nMin is greater than max.");
                stockError.setTitle("Error: Stock, Min, Max");
                stockError.setHeaderText("Please verify the values in: Inv, Max, and Min.");
                stockError.showAndWait();
            } else {
                if (inHouseButton.isSelected()) {
                    int machineId = Integer.parseInt(machineIdText.getText());
                    InHouse newPart = new InHouse(id, name, price, stock, min, max, machineId);
                    Inventory.updatePart(partIndex, newPart);

                } else if (outsourcedButton.isSelected()) {
                    String companyName = machineIdText.getText();
                    Outsourced newPart = new Outsourced(id, name, price, stock, min, max, companyName);
                    Inventory.updatePart(partIndex, newPart);
                }
                FXMLLoader fxmlLoader = new FXMLLoader(InventorySystem.class.getResource("MainScreen.fxml"));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(fxmlLoader.load(), 948, 337);
                stage.setTitle("C482 - Performance Assessment");
                stage.setScene(scene);
                stage.show();
            }
        } catch (Exception e) {
            Alert exception = new Alert(Alert.AlertType.ERROR, e.toString());
            exception.setTitle("Error");
            exception.setHeaderText("Please verify values in form.\nPlease refer to the exception message below for more information.");
            exception.showAndWait();
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
