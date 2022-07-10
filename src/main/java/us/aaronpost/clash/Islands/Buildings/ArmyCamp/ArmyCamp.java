package us.aaronpost.clash.Islands.Buildings.ArmyCamp;

import us.aaronpost.clash.Islands.Building;
public class ArmyCamp extends Building {
    private int armyCamp;
    public ArmyCamp(int level, int x, int z) {
        super(x, z, "fillerschematic");
        this.armyCamp = 3;
        super.setType(2);
        super.setLevel(level);
    }
    public int getInt() {
        return armyCamp;
    }
}
