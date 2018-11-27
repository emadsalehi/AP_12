package model.request;

public class LoadGameRequest extends Request {
    private String pathToJsonFile;

    public LoadGameRequest(String pathToJsonFile) {
        this.pathToJsonFile = pathToJsonFile;
    }

    public String getPathToJsonFile() {
        return pathToJsonFile;
    }
}
