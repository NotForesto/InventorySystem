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

/** The InventorySystem class creates teh Inventory Management application and then adds test data.*/
public class InventorySystem extends Application {

    public static int partIdCount=0;

    /**
     * The partIDIncrement() method increments the part ID counter by 1 and then returns the partID.
     * @return the current partIdCount
     */
    public static int partIdIncrement() {
        return ++partIdCount;
    }

    public static int productIdCount=999;

    /**
     * The productIDIncrement() method increments the product ID counter by 1 and then returns the productID.
     * @return the current productIdCount
     */
    public static int productIdIncrement() {
        return ++productIdCount;
    }

    /**
     * The start() method initializes MainScreen.fxml and sets scene GUI size and title.
     * @param stage The stage that MainScreen.fxml initializes to
     * @throws IOException So compiler knows that Input/Output might throw an exception, in this case the "MainScreen.fxml" file
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InventorySystem.class.getResource("MainScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 948, 337);
        stage.setTitle("C482 - Performance Assessment");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main() method creates test data and launches the application.
     * @param args Contains the supplied command-line arguments as an array of String objects.
     */
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