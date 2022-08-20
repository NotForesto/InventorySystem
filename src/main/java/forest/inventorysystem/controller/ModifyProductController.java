package forest.inventorysystem.controller;

import forest.inventorysystem.InventorySystem;
import forest.inventorysystem.model.Part;
import forest.inventorysystem.model.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ModifyProductController {
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

    }


    public void onAddButton(ActionEvent actionEvent) {

    }

    public void onSaveButton(ActionEvent actionEvent) {

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
    }
}
