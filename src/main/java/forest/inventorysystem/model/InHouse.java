package forest.inventorysystem.model;


/**
 * @author Forest Burchinal-Haj
 */

/**
 * The InHouse class is a subclass of the Abstract Part class
 */
public class InHouse extends Part {

    // Declare private int machineId
    private int machineId;

    /**
     * InHouse constructor, uses Part class super-constructor
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param machineId
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     *
     * @param machineId the machineId to set
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     *
     * @return the machineId
     */
    public int getMachineId() {
        return machineId;
    }
}
