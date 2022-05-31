package us.aaronpost.clash.Buildings;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import us.aaronpost.clash.Clash;

import java.io.Serial;
import java.io.Serializable;


public class Building implements Serializable {
    private int level, cost, buildtime, type;
    private int x, y, z;
    private String world;
    // false when not built
    private boolean built;
    public void setLocation(Location loc) {
        x = (int) Math.floor(loc.getX());
        y = (int) Math.floor(loc.getY());
        z = (int) Math.floor(loc.getZ());
        world = loc.getWorld().getName();
        if(world == null) {
            Clash.getPlugin().getLogger().info("World of a building is null! This is bad!");
        }
    }
    public Location getLoc() {
        World w = Bukkit.getWorld(world);
        return new Location(w, x, y, z);
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getLevel() {
        return this.level;
    }
    public void setLevel(int level) {
        this.level = level;
    }

    public int getType() {
        return type;
    }
}
