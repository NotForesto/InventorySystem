package forest.inventorysystem.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A class that represents product objects
 */
public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Product constructor
     *
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @param id the id to be set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @param name the name to be set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param price the price to be set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @param stock the stock to be set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @param min the min to be set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @param max the max to be set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * Adds provided Part to the associatedParts ObservableList
     *
     * @param part part to be added to ObservableList
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * Delete provided Part from associatedParts ObservableList
     *
     * @param selectedAssociatedPart the Part to be deleted from ObservableList
     * @return true if part is successfully deleted, otherwise false
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        associatedParts.remove(selectedAssociatedPart);
        return true;
    }

    /**
     * @return all Parts in associatedParts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
