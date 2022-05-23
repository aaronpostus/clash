package us.aaronpost.clash.Buildings;

import org.bukkit.Location;

public class Building {
    private int cost, buildtime;
    private Location loc;
    // false when not built
    private boolean built;
    public void setLocation(Location loc) {
        this.loc = loc;
    }
    public Location getLoc() {
        return this.loc;
    }
}
