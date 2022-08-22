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

/**
 * @author Forest Burchinal-Haj
 */

/**
 * The AddPartController class provides control logic for the AddPart screen of the Inventory System application.
 */
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


    /**
     * The onInHouse() method changes the machineIdLabel to "Machine ID" when the InHouse button is selected.
     *
     * @param actionEvent refers to the selection of the InHouse radio button
     */
    public void onInHouse(ActionEvent actionEvent) {
        machineIdLabel.setText("Machine ID");
    }

    /**
     * The onOutsourced() method changes the machineIdLabel to "Company Name" when the Outsourced button is selected.
     *
     * @param actionEvent refers to the selection of the Outsourced radio button
     */
    // When Outsourced radio button is selected, Company Name field is available
    public void onOutsourced(ActionEvent actionEvent) {
        machineIdLabel.setText("Company Name");
    }

    /**
     * The onSaveButton() method attempts to create a new part with supplied inputs.
     *
     * @param actionEvent refers to the SaveButton being pressed.
     * @throws IOException so compiler knows that Input/Output might throw an exception, in this case the values the user entered into the text boxes
     */
    public void onSaveButton(ActionEvent actionEvent) throws IOException {
        try {
            int id = partIdIncrement();
            String name = nameText.getText();
            double price = Double.parseDouble(priceText.getText());
            int stock = Integer.parseInt(invText.getText());
            int min = Integer.parseInt(minText.getText());
            int max = Integer.parseInt(maxText.getText());

            if (min > max) {
                Alert stockError = new Alert(Alert.AlertType.ERROR, "Min must be less than Max. Please change Min or Max value.");
                stockError.setTitle("Error: Min/Max");
                stockError.setHeaderText("Min is larger than max value.");
                stockError.showAndWait();
            } else if (stock < min || stock > max) {
                Alert stockError = new Alert(Alert.AlertType.ERROR, "Inv must be greater than Min, but less than Max. Please change stock to be greater than Min but less than Max.");
                stockError.setTitle("Error: Inv");
                stockError.setHeaderText("Inv is not between Min and Max.");
                stockError.showAndWait();
            } else {
                if (inHouseButton.isSelected()) {
                    // create new inHouse object
                    int machineId = Integer.parseInt(machineIdText.getText());
                    Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineId));

                } else if (outsourcedButton.isSelected()) {
                    // create new outsourced object
                    String companyName = machineIdText.getText();
                    Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));
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

    /**
     * The onCancelButton() method returns the user to the MainScreen
     *
     * @param actionEvent refers to the pressing of the CancelButton
     * @throws IOException so compiler knows that Input/Output might throw an exception, in this case the "MainScreen.fxml" file.
     */
    public void onCancelButton(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InventorySystem.class.getResource("MainScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 948, 337);
        stage.setTitle("C482 - Performance Assessment");
        stage.setScene(scene);
        stage.show();
    }
}
