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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
        // work in progress
        Part partToModify = MainPartsTable.getSelectionModel().getSelectedItem();

        FXMLLoader fxmlLoader = new FXMLLoader(InventorySystem.class.getResource("ModifyPart.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 670, 580);
        stage.setTitle("Modify Parts");
        stage.setScene(scene);
        stage.show();


    }

    // Loads the ProductModify form when the "Modify" button is pressed in the products section of the MainScreen form.
    public void toProductModify(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InventorySystem.class.getResource("ModifyProduct.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 1150, 630);
        stage.setTitle("Modify Products");
        stage.setScene(scene);
        stage.show();
    }

    public void onPartsDelete(ActionEvent actionEvent) {

    }

    public void onProductDelete(ActionEvent actionEvent) {
    }

    // Closes application when the "Exit" button is pressed on the MainScreen form.
    public void onExitButton(ActionEvent actionEvent) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    // Parts Table on main screen search. Calls searchByPartName and searchByPartID methods
    public void onPartsSearchField(ActionEvent actionEvent) {
        String q = PartSearchField.getText();
        ObservableList<Part> parts = searchByPartName(q);
        if (parts.size() == 0) {
            try {
                int id = Integer.parseInt(q);
                Part part = searchByPartID(id);
                if (part != null) {
                    parts.add(part);
                }
            } catch (NumberFormatException ignored) { }
        }

        MainPartsTable.setItems(parts);
        PartSearchField.setText("");
    }

    // Method to search by PartName. Looks at parts that contain the text, doesn't require full string.
    // toLowerCase() is set on both namedPart and partialName as to not be case-sensitive in search
    private ObservableList<Part> searchByPartName(String partialName) {
        ObservableList<Part> namedParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = Inventory.getAllParts();
        for (Part namedPart : allParts) {
            if (namedPart.getName().toLowerCase().contains(partialName.toLowerCase())) {
                namedParts.add(namedPart);
            }
        }
        return namedParts;
    }

    // Method to search by PartID. Requires exact ID match to find part
    private Part searchByPartID(int id) {
        ObservableList<Part> allParts = Inventory.getAllParts();
        for (Part namedPart : allParts) {
            if (namedPart.getId() == id) {
                return namedPart;
            }
        }
        return null;
    }

    // Product Table on main screen search. Calls searchByProductName and searchByProductID methods
    public void onProductsSearchField(ActionEvent actionEvent) {
        String q = ProductSearchField.getText();
        ObservableList<Product> products = searchByProductName(q);

        if (products.size() == 0) {
            try {
                int id = Integer.parseInt(q);
                Product product = searchByProductID(id);
                if (product != null) {
                    products.add(product);
                }
            } catch (NumberFormatException ignored) { }
        }

        MainProductsTable.setItems(products);
        ProductSearchField.setText("");
    }

    // Method to search by ProductName. Looks at products that contain the text, doesn't require full string.
    // toLowerCase() is set on both namedProduct and partialName as to not be case-sensitive in search
    private ObservableList<Product> searchByProductName(String partialName) {
        ObservableList<Product> namedProducts = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        for (Product namedProduct : allProducts) {
            if (namedProduct.getName().toLowerCase().contains(partialName.toLowerCase())) {
                namedProducts.add(namedProduct);
            }
        }
        return namedProducts;
    }

    // Method to search by ProductID. Requires exact ID match to find product
    private Product searchByProductID(int id) {
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        for (Product namedProduct : allProducts) {
            if (namedProduct.getId() == id) {
                return namedProduct;
            }
        }
        return null;
    }

}