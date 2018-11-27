package model.request;

public class StartRequest extends Request {
    private String workShopName;

    public StartRequest(String workShopName) {
        this.workShopName = workShopName;
    }

    public String getWorkShopName() {
        return workShopName;
    }
}
