package GUI;

import controller.FarmController;

public class WriteThread extends Thread {
    private FarmController farmController;

    public WriteThread (FarmController farmController) {
        this.farmController = farmController;
    }

    @Override
    public void run() {
        farmController.listenForCommand();
    }
}
