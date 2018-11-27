package model.request;

import model.Storage;

public class TurnRequest extends Request {
    private int numberOfTurns;

    public TurnRequest(int numberOfTurns) {
        this.numberOfTurns = numberOfTurns;
    }

    public int getNumberOfTurns() {
        return numberOfTurns;
    }
}
