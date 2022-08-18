package forest.inventorysystem;

import forest.inventorysystem.model.InHouse;
import forest.inventorysystem.model.Inventory;
import forest.inventorysystem.model.Outsourced;
import forest.inventorysystem.model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class InventorySystem extends Application {
    // Variable that will be used to increment IDs for all parts and products
    public static int partIdCount=0;
    public static int partIdIncrement() {
        return ++partIdCount;
    }

    public static int productIdCount=999;
    public static int productIdIncrement() {
        return ++productIdCount;
    }


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InventorySystem.class.getResource("MainScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 948, 337);
        stage.setTitle("C482 - Performance Assessment");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {


        // Create test parts
        InHouse testPart = new InHouse(partIdIncrement(), "TestPart1", 9.99, 1, 1, 10, 12);
        InHouse testPart2 = new InHouse(partIdIncrement(), "TestPart2", 19.99, 56, 1, 1000, 123);
        Outsourced outPart1 = new Outsourced(partIdIncrement(), "OutPart", 59.99, 100, 1, 100000, "TestCompany");
        // Create test products
        Product testProduct = new Product(productIdIncrement(), "TestProduct", 199.99, 10, 1, 100);
        Product testProduct2 = new Product(productIdIncrement(), "TestProduct2", 199.99, 10, 1, 100);
        Product newProduct1 = new Product(productIdIncrement(), "newProduct1", 19.99, 10, 10, 100);

        // Associate test parts with test products
        testProduct.addAssociatedPart(testPart);
        testProduct.addAssociatedPart(testPart2);
        testProduct2.addAssociatedPart(outPart1);

        // Add test parts and test products to inventory
        Inventory.addPart(testPart);
        Inventory.addPart(testPart2);
        Inventory.addPart(outPart1);
        Inventory.addProduct(testProduct);
        Inventory.addProduct(testProduct2);
        Inventory.addProduct(newProduct1);

        // Launches GUI
        launch();
    }

}