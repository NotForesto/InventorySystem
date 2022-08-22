package forest.inventorysystem.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();


    public static void addPart(Part part) { allParts.add(part); }

    public static void addProduct(Product product) { allProducts.add(product); }

    // Method to search by PartID. Requires exact ID match to find part
    public static Part lookupPart(int partID) {
        ObservableList<Part> allParts = Inventory.getAllParts();
        for (Part namedPart : allParts) {
            if (namedPart.getId() == partID) {
                return namedPart;
            }
        }
        return null;
    }

    // Method to search by ProductID. Requires exact ID match to find product
    public static Product lookupProduct(int productId) {
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        for (Product namedProduct : allProducts) {
            if (namedProduct.getId() == productId) {
                return namedProduct;
            }
        }
        return null;
    }

    // Method to search by PartName. Looks at parts that contain the text, doesn't require full string.
    // toLowerCase() is set on both namedPart and partialName as to not be case-sensitive in search
    public static ObservableList<Part> lookupPart(String partialName) {
        ObservableList<Part> namedParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = Inventory.getAllParts();
        for (Part namedPart : allParts) {
            if (namedPart.getName().toLowerCase().contains(partialName.toLowerCase())) {
                namedParts.add(namedPart);
            }
        }
        return namedParts;
    }
    // Method to search by ProductName. Looks at products that contain the text, doesn't require full string.
    // toLowerCase() is set on both namedProduct and partialName as to not be case-sensitive in search
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> namedProducts = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        for (Product namedProduct : allProducts) {
            if (namedProduct.getName().toLowerCase().contains(productName.toLowerCase())) {
                namedProducts.add(namedProduct);
            }
        }
        return namedProducts;
    }

    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    public static boolean deletePart(Part selectedPart) {
        if(allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        } else {
            return false;
        }
    }

    public static boolean deleteProduct(Product selectedProduct) {
        if(allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        } else {
            return false;
        }
    }

    public static int getPartIndex (Part part){
        return allParts.indexOf(part);
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

}
