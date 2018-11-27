package model.request;

public class CageRequest extends Request {
    private int x, y;

    public CageRequest(int x, int y) {
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
