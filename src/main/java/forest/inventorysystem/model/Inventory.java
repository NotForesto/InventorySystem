package forest.inventorysystem.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();


    public static void addPart(Part part) { allParts.add(part); }

    public static void addProduct(Product product) { allProducts.add(product); }

    public static ObservableList<Part> lookupPart(int partId) {

        return null;
    }

    public static ObservableList<Product> lookupProduct(int productId) {

        return null;
    }

    // Work in progress
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> namedParts = FXCollections.observableArrayList();

        for(Part part : allParts) {
            if(part.getName().contains(partName)) {
                namedParts.add(part);
            }
        }
        return namedParts;
    }

    public static ObservableList<Product> lookupProduct(String productName) {

        return null;
    }
    public static void updatePart(int index, Part selectedPart) {

    }

    public static void updateProduct(int index, Product selectedProduct) {

    }

    public static boolean deletePart(Part selectedPart) {
        return true;
    }

    public static boolean deleteProduct(Product selectedProduct) {
        return true;
    }
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

}
