package model.request;

public class GoRequest extends Request {
    private String vehivlePartName;

    public GoRequest(String vehivlePartName) {
        this.vehivlePartName = vehivlePartName;
    }

    public String getVehivlePartName() {
        return vehivlePartName;
    }
}
