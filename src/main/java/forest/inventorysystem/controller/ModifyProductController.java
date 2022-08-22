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

public class ModifyProductController {
    @FXML
    private TableColumn AssocPartPricePerUnitCol;
    @FXML
    private TableColumn AssocPartInventoryLevelCol;
    @FXML
    private TableColumn AssocPartNameCol;
    @FXML
    private TableColumn AssocPartIDCol;
    @FXML
    private TableView AssociatedPartsTable;
    @FXML
    private TableColumn PartPricePerUnitCol;
    @FXML
    private TableColumn PartInventoryLevelCol;
    @FXML
    private TableColumn PartNameCol;
    @FXML
    private TableColumn PartIDCol;
    @FXML
    private TableView PartsTable;
    @FXML
    private TextField PartSearchField;
    @FXML
    private TextField nameText;
    @FXML
    private TextField minText;
    @FXML
    private TextField maxText;
    @FXML
    private TextField priceText;
    @FXML
    private TextField invText;
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
    private Product selectedProduct;
    private ObservableList<Part> assocParts;

    /**
     * This method accepts a product to initialize on scene load
     * @param product
     */

    // Still needs if statement to switch between InHouse and Outsourced
    public void initialize(Product product) {

        selectedProduct = product;
        idText.setText(Integer.toString(selectedProduct.getId()));
        nameText.setText(selectedProduct.getName());
        invText.setText(Integer.toString(selectedProduct.getStock()));
        priceText.setText(Double.toString(selectedProduct.getPrice()));
        maxText.setText(Integer.toString(selectedProduct.getMax()));
        minText.setText(Integer.toString(selectedProduct.getMin()));

        PartsTable.setItems(Inventory.getAllParts());
        PartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        assocParts  = selectedProduct.getAllAssociatedParts();

        AssociatedPartsTable.setItems(assocParts);
        AssocPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        AssocPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        AssocPartInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AssocPartPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }


    public void onAddButton(ActionEvent actionEvent) {
        Part selectedPart = (Part) PartsTable.getSelectionModel().getSelectedItem();
        assocParts.add(selectedPart);
        AssociatedPartsTable.setItems(assocParts);
    }

    public void onSaveButton(ActionEvent actionEvent) {
        try {
            int id = Integer.parseInt(idText.getText());
            String name = nameText.getText();
            double price = Double.parseDouble(priceText.getText());
            int stock = Integer.parseInt(invText.getText());
            int min = Integer.parseInt(minText.getText());
            int max = Integer.parseInt(maxText.getText());
            int productIndex = Inventory.getAllProducts().indexOf(selectedProduct);

            if( stock > max || stock < min || stock < 0 ) {
                Alert stockError = new Alert(Alert.AlertType.ERROR, "The error is due to one of the following:\nInventory is equal or less than zero.\nInventory is less than Min or greater than Max.\nMin is greater than max.");
                stockError.setTitle("Error: Stock, Min, Max");
                stockError.setHeaderText("Please verify the values in: Inv, Max, and Min.");
                stockError.showAndWait();
            } else {

                Product newProduct = new Product(id, name, price, stock, min, max);
                Inventory.updateProduct(productIndex, newProduct);

                for(Part newAssocPart: assocParts) {
                    newProduct.addAssociatedPart(newAssocPart);
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

    public void onRemovePartsButton(ActionEvent actionEvent) {
        Part selectedPart = (Part) AssociatedPartsTable.getSelectionModel().getSelectedItem();
        if(selectedPart == null) {
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
}
