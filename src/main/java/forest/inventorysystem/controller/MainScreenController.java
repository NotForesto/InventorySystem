package forest.inventorysystem.controller;

import forest.inventorysystem.InventorySystem;
import forest.inventorysystem.model.Inventory;
import forest.inventorysystem.model.Part;
import forest.inventorysystem.model.Product;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static forest.inventorysystem.model.Inventory.lookupPart;
import static forest.inventorysystem.model.Inventory.lookupProduct;

/**
 * @author Forest Burchinal-Haj
 */

/**
 * The MainScreenController class provides control logic for the main screen of the Inventory System application.
 */
public class MainScreenController implements Initializable {
    @FXML
    private TextField PartSearchField;
    @FXML
    private TextField ProductSearchField;
    @FXML
    private Button exitButton;
    @FXML
    private TableView<Part> MainPartsTable;
    @FXML
    private TableColumn<Part, Integer> PartIDCol;
    @FXML
    private TableColumn<Part, String> PartNameCol;
    @FXML
    private TableColumn<Part, Integer> PartInventoryLevelCol;
    @FXML
    private TableColumn<Part, Double> PartPricePerUnitCol;
    @FXML
    private TableColumn<Product, Integer> ProductIDCol;
    @FXML
    private TableColumn<Product, String> ProductNameCol;
    @FXML
    private TableColumn<Product, Integer> ProductInventoryLevelCol;
    @FXML
    private TableColumn<Product, Double> ProductPricePerUnitCol;
    @FXML
    private TableView<Product> MainProductsTable;

    /**
     * The initialize() method is called after constructor and @FXML.
     * Populates MainPartsTable and MainProductsTable.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Grabs all parts in inventory and maps them to correct columns
        MainPartsTable.setItems(Inventory.getAllParts());
        PartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Grabs all products in inventory and maps them to correct columns
        MainProductsTable.setItems(Inventory.getAllProducts());
        ProductIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        ProductNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ProductInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ProductPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * The toAddPart() method loads the AddPart screen.
     *
     * @param actionEvent refers to the AddPart button being pressed.
     * @throws IOException so compiler knows that Input/Output might throw an exception, in this case the "AddPart.fxml" file.
     */
    public void toAddPart(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InventorySystem.class.getResource("AddPart.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 670, 580);
        stage.setTitle("Add Parts");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The toAddProduct() method loads the AddPart screen.
     *
     * @param actionEvent refers to the AddProduct button being pressed.
     * @throws IOException so compiler knows that Input/Output might throw an exception, in this case the "AddProduct.fxml" file.
     */
    public void toAddProduct(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InventorySystem.class.getResource("AddProduct.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 1150, 630);
        stage.setTitle("Add Products");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The toPartsModify() method loads the ModifyParts screen.
     * Stores highlighted/selected part into a Part object so that parts data can be passed to the ModifyPart screen.
     *
     * @param actionEvent refers to the ModifyPart button being pressed.
     * @throws IOException so compiler knows that Input/Output might throw an exception, in this case the "ModifyPart.fxml" file.
     */
    public void toPartsModify(ActionEvent actionEvent) throws IOException {
        Part selectedPart = MainPartsTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            Alert noPart = new Alert(Alert.AlertType.ERROR, "Please select a part that you would like to modify.");
            noPart.setTitle("Error: No part selected");
            noPart.setHeaderText("No part selected.");
            noPart.showAndWait();
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(InventorySystem.class.getResource("ModifyPart.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load(), 670, 580);
            ModifyPartController controller = fxmlLoader.getController();
            controller.initialize(selectedPart);
            stage.setTitle("Modify Parts");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * The toProductModify() method loads the ModifyProducts screen.
     * Stores highlighted/selected part into a Part object so that parts data can be passed to the ModifyProducts screen.
     *
     * @param actionEvent refers to the ModifyProduct button being pressed.
     * @throws IOException so compiler knows that Input/Output might throw an exception, in this case the "ModifyProduct.fxml" file.
     */
    public void toProductModify(ActionEvent actionEvent) throws IOException {
        Product selectedProduct = MainProductsTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            Alert noProduct = new Alert(Alert.AlertType.ERROR, "Please select a product that you would like to modify.");
            noProduct.setTitle("Error: No product selected");
            noProduct.setHeaderText("No product selected.");
            noProduct.showAndWait();
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(InventorySystem.class.getResource("ModifyProduct.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load(), 1150, 630);
            ModifyProductController controller = fxmlLoader.getController();
            controller.initialize(selectedProduct);
            stage.setTitle("Modify Products");
            stage.setScene(scene);
            stage.show();
        }
    }


    /**
     * The onPartDelete() method deletes highlighted/selected part.
     * Throws confirmation window to verify user wants to delete part.
     * Throws alert if part is unable to be deleted.
     *
     * @param actionEvent refers to the PartDelete button being pressed.
     */
    public void onPartsDelete(ActionEvent actionEvent) {

        Part selectedPart = MainPartsTable.getSelectionModel().getSelectedItem();
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
                if (Inventory.deletePart(selectedPart)) {
                    MainPartsTable.setItems(Inventory.getAllParts());
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
     * The onProductDelete() method deletes highlighted/selected product.
     * Throws confirmation window to verify user wants to delete product.
     * Throws alert if product is unable to be deleted.
     *
     * @param actionEvent refers to the ProductDelete button being pressed.
     */
    public void onProductDelete(ActionEvent actionEvent) {
        Product selectedProduct = MainProductsTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            Alert noProduct = new Alert(Alert.AlertType.ERROR, "Please select a product that you would like to delete.");
            noProduct.setTitle("Error: No product selected");
            noProduct.setHeaderText("No product selected.");
            noProduct.showAndWait();
        } else {
            if (selectedProduct.getAllAssociatedParts().size() > 0) {
                Alert hasAssocPartsError = new Alert(Alert.AlertType.ERROR);
                hasAssocPartsError.setTitle("Error: Product has associated parts");
                hasAssocPartsError.setContentText("""
                        Please first remove the associated parts. This can be completed by:
                                                
                        1.) Selecting \"Modify\" button in the Products Table.
                        2.) Selecting the associated parts in the bottom table.
                        3.) Selecting the \"Remove Associated Parts\" button.""");
                hasAssocPartsError.setHeaderText("Product: " + selectedProduct.getName() + " has associated parts and cannot be deleted.");
                hasAssocPartsError.showAndWait();
            } else {
                Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the selected product?");
                confirmation.setTitle("Confirm Deletion");
                confirmation.setHeaderText("Delete \"" + selectedProduct.getName() + "\"?");
                Optional<ButtonType> result = confirmation.showAndWait();

                if (result.isEmpty() || result.get() != ButtonType.OK) {
                    Alert notDeleted = new Alert(Alert.AlertType.INFORMATION, "The selected product has not been deleted.");
                    notDeleted.setTitle("Cancelled");
                    notDeleted.setHeaderText("Product \"" + selectedProduct.getName() + "\" not deleted.");
                    notDeleted.showAndWait();
                } else {
                    if (Inventory.deleteProduct(selectedProduct)) {
                        MainProductsTable.setItems(Inventory.getAllProducts());
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Product not deleted");
                        alert.setTitle("Error: Product not deleted!");
                        alert.setHeaderText("");
                        alert.show();
                    }
                }
            }
        }
    }

    /**
     * The onExitButton() method closes the application when the ExitButton is pressed.
     *
     * @param actionEvent refers to the ExitButton being pressed.
     */
    public void onExitButton(ActionEvent actionEvent) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    /**
     * The onPartSearchField() method searches for parts using both partialName and exact ID matching
     *
     * @param actionEvent refers to the search bar being activated (via enter key or on screen button).
     */
    public void onPartsSearchField(ActionEvent actionEvent) {
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
        MainPartsTable.setItems(parts);
        PartSearchField.setText("");
    }

    /**
     * The onProductsSearchField() method searches for products using both partialName and exact ID matching
     *
     * @param actionEvent refers to the search bar being activated (via enter key or on screen button).
     */
    public void onProductsSearchField(ActionEvent actionEvent) {
        String q = ProductSearchField.getText();
        ObservableList<Product> products = lookupProduct(q);

        if (products.size() == 0) {
            try {
                int id = Integer.parseInt(q);
                Product product = lookupProduct(id);
                if (product != null) {
                    products.add(product);
                }
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Your query:\n" + ProductSearchField.getText() + "\nDid not return any products.");
                alert.setTitle("ERROR: Product not found");
                alert.setHeaderText("");
                alert.show();
            }
        }

        MainProductsTable.setItems(products);
        ProductSearchField.setText("");
    }
}