package model.request;

public class RunRequest extends Request {
    private String mapName;

    public RunRequest(String mapName) {
        this.mapName = mapName;
    }

    public String getMapName() {
        return mapName;
    }
}
