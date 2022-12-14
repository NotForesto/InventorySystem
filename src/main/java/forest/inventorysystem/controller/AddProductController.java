package forest.inventorysystem.controller;

import forest.inventorysystem.InventorySystem;
import forest.inventorysystem.model.Inventory;
import forest.inventorysystem.model.Part;
import forest.inventorysystem.model.Product;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

import static forest.inventorysystem.InventorySystem.productIdIncrement;
import static forest.inventorysystem.model.Inventory.lookupPart;

/**
 * @author Forest Burchinal-Haj
 */

/**
 * The AddProductController class provides control logic for the AddProduct screen of the Inventory System application.
 */
public class AddProductController {
    @FXML
    private TableColumn AssocPartPricePerUnitCol;
    @FXML
    private TableColumn AssocPartInventoryLevelCol;
    @FXML
    private TableColumn AssocPartNameCol;
    @FXML
    private TableColumn AssocPartIDCol;
    @FXML
    private TableColumn PartPricePerUnitCol;
    @FXML
    private TableColumn PartInventoryLevelCol;
    @FXML
    private TableColumn PartNameCol;
    @FXML
    private TableColumn PartIDCol;
    @FXML
    private TableView AssociatedPartsTable;
    @FXML
    private TableView PartsTable;
    @FXML
    private TextField PartSearchField;
    @FXML
    private TextField minText;
    @FXML
    private TextField maxText;
    @FXML
    private TextField priceText;
    @FXML
    private TextField invText;
    @FXML
    private TextField nameText;
    @FXML
    private Button addButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button removePartsButton;
    @FXML
    private TextField idText;
    private ObservableList<Part> assocParts;

    /**
     * The initialize() method is called after constructor and @FXML.
     * Populates PartsTable and AssociatedPartsTable.
     * Initializes assocParts ObservableList as well.
     */
    public void initialize() {

        PartsTable.setItems(Inventory.getAllParts());
        PartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        assocParts = AssociatedPartsTable.getItems();

        AssocPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        AssocPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        AssocPartInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AssocPartPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * The onAddButton() method adds part highlighted in PartsTable to assocParts Observable list.
     * AssociatedPartsTable is then reloaded so new assocPart is shown in table.
     * Will error alert if no part is selected
     * @param actionEvent refers to the AddButton being pressed.
     */
    public void onAddButton(ActionEvent actionEvent) {
        Part selectedPart = (Part) PartsTable.getSelectionModel().getSelectedItem();
        if(selectedPart == null) {
            Alert noPart = new Alert(Alert.AlertType.ERROR, "Please select a part that you would like to associate to the product.");
            noPart.setTitle("Error: No part selected");
            noPart.setHeaderText("No part selected.");
            noPart.showAndWait();
        } else {
            assocParts.add(selectedPart);
            AssociatedPartsTable.setItems(assocParts);
        }

    }

    /**
     * The onSaveButton() method attempts to create new product with supplied inputs.
     *
     * @param actionEvent refers to the SaveButton being pressed.
     */
    public void onSaveButton(ActionEvent actionEvent) {
        try {
            int id = productIdIncrement();
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

                Product newProduct = new Product(id, name, price, stock, min, max);
                Inventory.addProduct(newProduct);

                for (Part newAssocPart : assocParts) {
                    newProduct.addAssociatedPart(newAssocPart);
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

    /**
     * The onRemovePartsButton() method removes highlighted associated part.
     *
     * @param actionEvent refers to the pressing of the RemoveAssociatedPart button.
     */
    public void onRemovePartsButton(ActionEvent actionEvent) {
        Part selectedPart = (Part) AssociatedPartsTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            Alert noPart = new Alert(Alert.AlertType.ERROR, "Please select a part that you would like to delete.");
            noPart.setTitle("Error: No part selected");
            noPart.setHeaderText("No part selected.");
            noPart.showAndWait();
        } else {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the selected part?");
            confirmation.setTitle("Confirm Deletion");
            confirmation.setHeaderText("Delete \"" + selectedPart.getName() + "\"?");
            Optional<ButtonType> result = confirmation.showAndWait();

            if (result.isEmpty() || result.get() != ButtonType.OK) {
                Alert notDeleted = new Alert(Alert.AlertType.INFORMATION, "The selected part has not been deleted.");
                notDeleted.setTitle("Cancelled");
                notDeleted.setHeaderText("Part \"" + selectedPart.getName() + "\" not deleted.");
                notDeleted.showAndWait();
            } else {
                if (assocParts.remove(selectedPart)) {
                    AssociatedPartsTable.setItems(assocParts);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Part not deleted");
                    alert.setTitle("Error: Part not deleted!");
                    alert.setHeaderText("");
                    alert.show();
                }
            }
        }
    }

    /**
     * The onPartSearchField() method searches for parts using both partialName and exact ID matching
     *
     * @param actionEvent refers to the search bar being activated (via enter key or on screen button).
     */
    public void onPartSearchField(ActionEvent actionEvent) {
        String q = PartSearchField.getText();
        ObservableList<Part> parts = lookupPart(q);
        if (parts.size() == 0) {
            try {
                int partID = Integer.parseInt(q);
                Part part = lookupPart(partID);
                if (part != null) {
                    parts.add(part);
                }
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Your query:\n" + PartSearchField.getText() + "\nDid not return any parts.");
                alert.setTitle("Error: Part not found!");
                alert.setHeaderText("");
                alert.show();
            }
        }
        PartsTable.setItems(parts);
        PartSearchField.setText("");
    }
}
