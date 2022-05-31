package us.aaronpost.clash.Buildings;

import org.bukkit.entity.ArmorStand;

import java.io.Serializable;

public class ArmyCamp extends Building {
    private int armyCamp;
    public ArmyCamp(int level) {
        this.armyCamp = 3;
        super.setType(2);
        super.setLevel(level);
    }
    public int getInt() {
        return armyCamp;
    }
}
