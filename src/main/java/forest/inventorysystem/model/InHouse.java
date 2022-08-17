package forest.inventorysystem.model;

public class InHouse extends Part {

    // Declare private int machineId
    private int machineId;

    // InHouse constructor
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    // machineId setter allows machineID to be set without accessing the private int directly
    public void setMachineId(int machineID) {
        this.machineId = machineID;
    }

    // machineId setter allows machineID to be accessed without accessing the private int directly
    public int getMachineId() {
        return machineId;
    }


}
