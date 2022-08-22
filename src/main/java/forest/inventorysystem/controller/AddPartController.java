package forest.inventorysystem.controller;

import forest.inventorysystem.InventorySystem;
import forest.inventorysystem.model.InHouse;
import forest.inventorysystem.model.Inventory;
import forest.inventorysystem.model.Outsourced;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

import static forest.inventorysystem.InventorySystem.partIdIncrement;

public class AddPartController {

    @FXML
    private TextField invText;
    @FXML
    private TextField priceText;
    @FXML
    private TextField maxText;
    @FXML
    private TextField machineIdText;
    @FXML
    private TextField minText;
    @FXML
    private TextField nameText;
    @FXML
    private TextField idText;
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


    // When In-House radio button is selected, Machine ID field is available.
    public void onInHouse(ActionEvent actionEvent) {
        machineIdLabel.setText("Machine ID");
    }

    // When Outsourced radio button is selected, Company Name field is available
    public void onOutsourced(ActionEvent actionEvent) {
        machineIdLabel.setText("Company Name");
    }


    public void onSaveButton(ActionEvent actionEvent) throws IOException {
        try {
            int id = partIdIncrement();
            String name = nameText.getText();
            double price = Double.parseDouble(priceText.getText());
            int stock = Integer.parseInt(invText.getText());
            int min = Integer.parseInt(minText.getText());
            int max = Integer.parseInt(maxText.getText());

            if( stock > max || stock < min || stock < 0 ) {
                Alert stockError = new Alert(Alert.AlertType.ERROR, "The error is due to one of the following:\nInventory is equal or less than zero.\nInventory is less than Min or greater than Max.\nMin is greater than max.");
                stockError.setTitle("Error: Stock, Min, Max");
                stockError.setHeaderText("Please verify the values in: Inv, Max, and Min.");
                stockError.showAndWait();
            } else {
                if(inHouseButton.isSelected()) {
                    // create new inHouse object
                    int machineId = Integer.parseInt(machineIdText.getText());
                    Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineId));

                } else if(outsourcedButton.isSelected()) {
                    // create new outsourced object
                    String companyName = machineIdText.getText();
                    Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));
                }
                FXMLLoader fxmlLoader = new FXMLLoader(InventorySystem.class.getResource("MainScreen.fxml"));
                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(fxmlLoader.load(), 948, 337);
                stage.setTitle("C482 - Performance Assessment");
                stage.setScene(scene);
                stage.show();
            }
        }
        catch (Exception e) {
            Alert exception = new Alert(Alert.AlertType.ERROR,  e.toString());
            exception.setTitle("Error");
            exception.setHeaderText("Please verify values in form.\nPlease refer to the exception message below for more information.");
            exception.showAndWait();
        }
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
