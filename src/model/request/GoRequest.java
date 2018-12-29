package model.request;

public class GoRequest extends Request {
    private String vehiclePartName;

    public GoRequest(String vehiclePartName) {
        this.vehiclePartName = vehiclePartName;
    }

    public String getVehiclePartName() {
        return vehiclePartName;
    }
}
