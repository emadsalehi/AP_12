package model.request;

public class SaveGameRequest extends Request {
    private String pathToJsonFile;

    public SaveGameRequest(String pathToJsonFile) {
        this.pathToJsonFile = pathToJsonFile;
    }

    public String getPathToJsonFile() {
        return pathToJsonFile;
    }
}
