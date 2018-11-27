package model.request;

public class LoadCustomRequest extends Request {
    private String pathToCustomDirectory;

    public LoadCustomRequest(String pathToCustomDirectory) {
        this.pathToCustomDirectory = pathToCustomDirectory;
    }

    public String getPathToCustomDirectory() {
        return pathToCustomDirectory;
    }
}
