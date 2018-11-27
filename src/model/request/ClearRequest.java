package model.request;


public class ClearRequest extends Request {
    private String vehicleTypeName;

    public ClearRequest(String vehicleTypeName) {
        this.vehicleTypeName = vehicleTypeName;
    }

    public String getVehicleTypeName() {
        return vehicleTypeName;
    }
}
