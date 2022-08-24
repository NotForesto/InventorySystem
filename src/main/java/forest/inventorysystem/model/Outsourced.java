package forest.inventorysystem.model;

/**
 * @author Forest Burchinal-Haj
 */

/**
 * The Outsourced class is a subclass of the Abstract Part class
 */
public class Outsourced extends Part {

    // Declare private int machineId
    private String companyName;

    /**
     * InHouse constructor, uses Part class super-constructor
     *
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param companyName
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * @param companyName the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

}