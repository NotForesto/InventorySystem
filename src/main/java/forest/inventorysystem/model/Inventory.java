package forest.inventorysystem.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Forest Burchinal-Haj
 */

/**
 * The Inventory class holds all parts and products.
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();


    /**
     * Adds part to inventory
     *
     * @param part the part that is to be added to inventory
     */
    public static void addPart(Part part) {
        allParts.add(part);
    }

    /**
     * Adds product to inventory
     *
     * @param product the product that is to be added to inventory
     */
    public static void addProduct(Product product) {
        allProducts.add(product);
    }

    /**
     * Searches for Part by its Id.
     * Loops over allParts until ones Id matches the supplied partID.
     *
     * @param partiD the ID to be searched
     * @return the Part object
     */
    public static Part lookupPart(int partiD) {
        ObservableList<Part> allParts = Inventory.getAllParts();
        for (Part namedPart : allParts) {
            if (namedPart.getId() == partiD) {
                return namedPart;
            }
        }
        return null;
    }

    /**
     * Searches for Product by its Id.
     * Loops over allProducts until ones Id matches the supplied productID.
     *
     * @param productId the ID to be searched
     * @return the Product object
     */
    public static Product lookupProduct(int productId) {
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        for (Product namedProduct : allProducts) {
            if (namedProduct.getId() == productId) {
                return namedProduct;
            }
        }
        return null;
    }

    /**
     * Searcher for Part by it's name. Allows for partial name search.
     * Uses toLowerCase() method to lowercase both part names in list and partialName in the query to make search not case-sensitive.
     *
     * @param partialName the query to be searched
     * @return the Part object
     */
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

    /**
     * Searcher for Product by its name. Allows for partial name search.
     * Uses toLowerCase() method to lowercase both product names in list and partialName in the query to make search not case-sensitive.
     *
     * @param partialName the query to be searched
     * @return the Part object
     */
    public static ObservableList<Product> lookupProduct(String partialName) {
        ObservableList<Product> namedProducts = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        for (Product namedProduct : allProducts) {
            if (namedProduct.getName().toLowerCase().contains(partialName.toLowerCase())) {
                namedProducts.add(namedProduct);
            }
        }
        return namedProducts;
    }

    /**
     * Updates part at given index to given parts parameters
     *
     * @param index        part is located at in ObservableList
     * @param selectedPart part to be updated to
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * Updates product at given index to given parts parameters
     *
     * @param index           product is located at in ObservableList
     * @param selectedProduct product to be updated to
     */
    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    /**
     * Deletes given part from ObservableList
     *
     * @param selectedPart part to be deleted
     * @return true if part is successfully deleted, false otherwise
     */
    public static boolean deletePart(Part selectedPart) {
        if (allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Deletes given product from ObservableList
     *
     * @param selectedProduct part to be deleted
     * @return true if product is successfully deleted, false otherwise
     */
    public static boolean deleteProduct(Product selectedProduct) {
        if (allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return returns allParts in inventory
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * @return returns allProducts in inventory
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

}
