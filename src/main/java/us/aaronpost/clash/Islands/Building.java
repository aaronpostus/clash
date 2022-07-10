package us.aaronpost.clash.Islands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import us.aaronpost.clash.Arenas.Arena;
import us.aaronpost.clash.Clash;
import us.aaronpost.clash.Schematics.LocationWrapper;
import us.aaronpost.clash.Schematics.Schematic;
import us.aaronpost.clash.Schematics.Schematics;
import java.io.Serializable;
import java.util.*;

public class Building implements Serializable {
    private int gridLengthX, gridLengthZ, level, cost, type, health, remainingBuildTime, maxLevel, remainingHitPoints, townhallRequired;
    private LocationWrapper loc;
    private boolean isNewBuilding;
    private ItemStack itemType = new ItemStack(Material.CHEST);
    private boolean isTargetable;
    private String itemName = "Todo ItemName";
    // Transient prefix means this will not be serialized
    private transient ArrayList<TimerTask> tasks;
    private transient Schematic schematic;
    private int x, z, relativeX, relativeZ;
    private String schematicName;
    private final UUID buildingID;
    private String world;
    // false when not built
    private boolean built;
    public Building() {
        x = 0;
        z = 0;
        buildingID = UUID.randomUUID();
        isNewBuilding = true;
        isTargetable = true;
    }
    public Building(int x, int z, String schematicName) {
        this.x = x;
        this.z = z;
        this.schematicName = schematicName;
        buildingID = UUID.randomUUID();
        isNewBuilding = true;
    }
    public boolean isNewBuilding() {
        return isNewBuilding;
    }
    public void changeToAssigned() {
        isNewBuilding = false;
    }
    public ItemStack getItemStack() {
        ItemStack stack = itemType.clone();
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(itemName + ChatColor.GRAY + " - Level " + level);
        List<String> lore = new ArrayList<String>();
        if(isNewBuilding) {
            lore.add("new");
        } else {
            lore.add("assigned");
        }
        lore.add(buildingID.toString());
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return stack;
    }
    public void setItemType(ItemStack itemStack) {
        this.itemType = itemStack;
    }
    public ItemStack getItemType() {
        return itemType.clone();
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    public String getItemName() {
        return itemName;
    }

    public void setLocation(Location loc) {
        x = (int) Math.floor(loc.getX());
        z = (int) Math.floor(loc.getZ());
        world = loc.getWorld().getName();
    }
    public void setSchematic(String schematicName) {
        schematicName = schematicName;
        schematic = Schematics.s.getSchematic(schematicName);
    }
    public Schematic getSchematic() {
        return schematic;
    }
//    public Location getLoc() {
//        World w = Bukkit.getWorld(world);
//        return new Location(w, x, y, z);
//    }
    public void paste(Arena a) {
        Location loc = a.getLoc().clone();
        loc.setX(relativeX + loc.getX());
        loc.setZ(relativeZ + loc.getZ());
        Schematics.s.getSchematic(schematicName).pasteSchematic(loc, 0);
        Clash.getPlugin().getServer().getLogger().info("Pasted " + itemName + " at " + loc.toString());
    }

    public void resetToGrass(Arena a) {
        Location loc = a.getLoc().clone();
        loc.setX(relativeX + loc.getX());
        loc.setZ(relativeZ + loc.getZ());
        Schematics.s.getSchematic(schematicName).resetToGrassLand(loc.clone());
        for(Entity entity: loc.getWorld().getNearbyEntities(loc, 7, 7,7)) {
            if(entity.getType().equals(EntityType.DROPPED_ITEM)) {
                entity.remove();
            }
        }
    }

    public void setType(int type) {
        this.type = type;
    }

    public ArrayList<TimerTask> getTasks() {
        return tasks;
    }
    public void addTask(TimerTask t) {
        tasks.add(t);
    }
    public void removeTask(Timer t) {
        tasks.remove(t);
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
    public int getX() {
        return x;
    }
    public int getZ() {
        return z;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setZ(int z) {
        this.z = z;
    }
    public int getGridLengthZ() {
        return gridLengthZ;
    }
    public UUID getUUID() {
        return buildingID;
    }
    public void setGridLengths(int gridLengthX, int gridLengthZ) {
        this.gridLengthX = gridLengthX;
        this.gridLengthZ = gridLengthZ;
    }

    public int getGridLengthX() {
        return gridLengthX;
    }

    public int getRelativeX() {
        return relativeX;
    }

    public void setRelativeX(int relativeX) {
        this.relativeX = relativeX;
    }

    public int getRelativeZ() {
        return relativeZ;
    }

    public void setRelativeZ(int relativeZ) {
        this.relativeZ = relativeZ;
    }
}
