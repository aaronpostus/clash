package us.aaronpost.clash.Buildings;

import org.bukkit.entity.ArmorStand;

import java.io.Serializable;

public class ArmyCamp extends Building {
    private int armyCamp;
    public ArmyCamp() {
        this.armyCamp = 3;
        super.setType(2);
    }
    public int getInt() {
        return armyCamp;
    }
}
