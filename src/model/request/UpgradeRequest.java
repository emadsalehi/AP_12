package model.request;

import model.EggPowderPlant;

public class UpgradeRequest extends Request {
    private String partTOUpgradeName;

    public UpgradeRequest(String partTOUpgradeName) {
        this.partTOUpgradeName = partTOUpgradeName;
    }

    public String getPartTOUpgradeName() {
        return partTOUpgradeName;
    }
}
