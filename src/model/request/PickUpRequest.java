package model.request;

public class PickUpRequest extends Request {
    private int x, y;

    public PickUpRequest(int x, int y) {
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
