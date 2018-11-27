package model.request;

public class PrintRequest extends Request {
    private String partToPrintName;

    public PrintRequest(String partToPrintName) {
        this.partToPrintName = partToPrintName;
    }

    public String getPartToPrintName() {
        return partToPrintName;
    }
}
