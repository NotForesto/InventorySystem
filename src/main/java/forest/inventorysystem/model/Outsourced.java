package forest.inventorysystem.model;

import forest.inventorysystem.InventorySystem;

public class Outsourced extends Part {

    // Declare private int machineId
    private String companyName;

    // InHouse constructor
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    // machineId setter allows machineID to be set without accessing the private int directly
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    // machineId setter allows machineID to be accessed without accessing the private int directly
    public String getCompanyName() {
        return companyName;
    }


}