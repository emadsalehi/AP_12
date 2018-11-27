package model.request;

public class AddRequest extends Request {
    private String vehicleTypeName;
    private String itemName;
    private int count;

    public AddRequest(String vehicleTypeName, String itemName, int count) {
        this.vehicleTypeName = vehicleTypeName;
        this.itemName = itemName;
        this.count = count;
    }

    public String getVehicleTypeName() {
        return vehicleTypeName;
    }

    public String getItemName() {
        return itemName;
    }

    public int getCount() {
        return count;
    }
}
