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

/**
 * @author Forest Burchinal-Haj
 */

/**
 * The ModifyPartController class provides control logic for the ModifyPart screen of the Inventory System application.
 */
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
     * RUNTIME ERROR: I was having a lot of difficulty getting InHouse or Outsourced objects cast to their correct type.
     * After a lot of testing I realized that you could cast them separately in their own if/else if statements.
     * After separating the two I was able to cast them to their correct types (InHouse for InHouse Parts and Outsourced for Outsourced Parts).
     *
     * The initialize() method is called after constructor and @FXML.
     *
     * @param part object is passed from the selected Part in the main screen
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
    public void onOutsourced(ActionEvent actionEvent) {
        machineIdLabel.setText("Company Name");
    }

    /**
     * The onSaveButton() method attempts to modify part with supplied inputs.
     *
     * @param actionEvent refers to the SaveButton being pressed.
     * @throws IOException so compiler knows that Input/Output might throw an exception, in this case the values the user entered into the text boxes
     */
    public void onSaveButton(ActionEvent actionEvent) throws IOException {
        try {
            int id = Integer.parseInt(idText.getText());
            String name = nameText.getText();
            double price = Double.parseDouble(priceText.getText());
            int stock = Integer.parseInt(invText.getText());
            int min = Integer.parseInt(minText.getText());
            int max = Integer.parseInt(maxText.getText());
            int partIndex = Inventory.getAllParts().indexOf(selectedPart);

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
