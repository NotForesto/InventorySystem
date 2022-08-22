package forest.inventorysystem.controller;

import forest.inventorysystem.InventorySystem;
import forest.inventorysystem.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import java.net.NetworkInterface;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

import static forest.inventorysystem.model.Inventory.*;

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

    // Loads the AddPart form when the "Add" button is pressed in the parts section of the MainScreen form.
    public void toAddPart(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InventorySystem.class.getResource("AddPart.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 670, 580);
        stage.setTitle("Add Parts");
        stage.setScene(scene);
        stage.show();
    }

    // Loads the AddProduct form when the "Add" button is pressed in the products section of the MainScreen form.
    public void toAddProduct(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InventorySystem.class.getResource("AddProduct.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 1150, 630);
        stage.setTitle("Add Products");
        stage.setScene(scene);
        stage.show();
    }

    // Loads the PartsModify form when the "Modify" button is pressed in the parts section of the MainScreen form.
    public void toPartsModify(ActionEvent actionEvent) throws IOException {
        Part selectedPart = MainPartsTable.getSelectionModel().getSelectedItem();
        if(selectedPart == null) {
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

    // Loads the ProductModify form when the "Modify" button is pressed in the products section of the MainScreen form.
    public void toProductModify(ActionEvent actionEvent) throws IOException {
        Product selectedProduct = MainProductsTable.getSelectionModel().getSelectedItem();
        if(selectedProduct == null) {
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

    // Deletes selected/highlighted part in MainScreen PartsTable
    // If deletePart() returns false (no part deleted), alert user that no part was deleted.
    public void onPartsDelete(ActionEvent actionEvent) {

        Part selectedPart = MainPartsTable.getSelectionModel().getSelectedItem();
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

            if(result.isEmpty() || result.get() != ButtonType.OK) {
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

    // Deletes selected/highlighted product in MainScreen ProductTable
    // If deleteProduct() returns false (no product deleted), alert user that no product was deleted.
    public void onProductDelete(ActionEvent actionEvent) {
        Product selectedProduct = MainProductsTable.getSelectionModel().getSelectedItem();
        if(selectedProduct == null) {
            Alert noProduct = new Alert(Alert.AlertType.ERROR, "Please select a product that you would like to delete.");
            noProduct.setTitle("Error: No product selected");
            noProduct.setHeaderText("No product selected.");
            noProduct.showAndWait();
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

    // Closes application when the "Exit" button is pressed on the MainScreen form.
    public void onExitButton(ActionEvent actionEvent) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    // Parts Table on main screen search. Calls searchByPartName and searchByPartID methods
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


    // Product Table on main screen search. Calls searchByProductName and searchByProductID methods
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