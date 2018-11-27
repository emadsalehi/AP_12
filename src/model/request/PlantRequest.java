package model.request;

public class PlantRequest extends Request {
    private int x, y;

    public PlantRequest(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
