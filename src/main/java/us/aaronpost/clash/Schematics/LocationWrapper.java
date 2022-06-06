package us.aaronpost.clash.Schematics;


import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import us.aaronpost.clash.Clash;

import java.time.LocalDate;

public class LocationWrapper {
    private transient Location loc;
    private transient World w;
    private double x, y, z;
    private String world;
    public LocationWrapper(Location loc) {
        this.loc = loc;
        this.w = loc.getWorld();
        x = loc.getX();
        y = loc.getY();
        z = loc.getZ();
        world = loc.getWorld().getName();
    }
    public Location getLoc() {
        loc = new Location(w, x, y, z);
        return loc;
    }
    public Block getBlock() {
        return  getLoc().getBlock();
    }
}
