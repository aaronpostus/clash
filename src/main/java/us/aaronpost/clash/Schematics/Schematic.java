package us.aaronpost.clash.Schematics;

import net.citizensnpcs.npc.ai.speech.Chat;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.xml.sax.helpers.LocatorImpl;
import us.aaronpost.clash.Clash;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Schematic implements Serializable {
    private final String name;
    private final int xLength, yLength, zLength;
    private List<LocationWrapper> blockLocs = new ArrayList<>();
    public Schematic(Block b1, Block b2, String name) {
        this.name = name;

        World world = b1.getWorld();

        // Get coords of each cuboid selection point

        int b1x = (int) Math.ceil(b1.getX());
        int b1y = (int) Math.ceil(b1.getY());
        int b1z = (int) Math.ceil(b1.getZ());

        int b2x = (int) Math.ceil(b2.getX());
        int b2y = (int) Math.ceil(b2.getY());
        int b2z = (int) Math.ceil(b2.getZ());

        // Get extrema coordinates in every direction

        int westExt = Math.min(b1x, b2x);
        int eastExt = Math.max(b1x, b2x);

        int northExt = Math.max(b1z, b2z);
        int southExt = Math.min(b1z, b2z);

        int upExt = Math.max(b1y, b2y);
        int downExt = Math.min(b1y, b2y);

        xLength = Math.abs(eastExt - westExt) + 1;
        yLength = Math.abs(upExt - downExt) + 1;
        zLength = Math.abs(northExt - southExt) + 1;

        // Debug messages

//        Clash.getPlugin().getServer().getPlayer("Aaronn").sendMessage(ChatColor.GRAY + "b1x: " + b1x + " b1y: " + b1y + " b1z: " +b1z);
//        Clash.getPlugin().getServer().getPlayer("Aaronn").sendMessage(ChatColor.GRAY + "b2x: " + b2x + " b2y: " + b2y + " b2z: " +b2z);
//        Clash.getPlugin().getServer().getPlayer("Aaronn").sendMessage(ChatColor.GRAY + "westExt: " + westExt + " eastExt: " + eastExt + " northExt: " +northExt);
//        Clash.getPlugin().getServer().getPlayer("Aaronn").sendMessage(ChatColor.GRAY + "southExt: " + southExt + " upExt: " + upExt + " downExt: " +downExt);
//        Clash.getPlugin().getServer().getPlayer("Aaronn").sendMessage(ChatColor.GRAY + "xLength: " + xLength + " yLength: " + yLength + " zLength: " +zLength);


        // Save locations for every block within the cuboid

//        Location loc = new Location(b1.getWorld(), westExt, downExt, southExt);
//
//        int counter = 0;

        for(int y = downExt; y <= upExt; y++) {
            for (int x = westExt; x <= eastExt; x++) {
                for(int z = southExt; z <= northExt; z++) {
//                    loc.setX(x);
//                    loc.setY(y);
//                    loc.setZ(z);
//                    loc.getBlock().setType(Material.LIME_WOOL);
                    blockLocs.add(new LocationWrapper(new Location(world, x,y,z)));
//                    counter++;
                }
            }
        }
    }

    public List<LocationWrapper> getLocs() {
        return this.blockLocs;
    }
    public void pasteSchematic(Location loc) {
        double initZ = loc.getZ();
        double initX = loc.getX();
        int counter = 0;
        Clash.getPlugin().getServer().getPlayer("Aaronn").sendMessage(ChatColor.GRAY + "Pasting schematic " + "\"" + name + "\".");
        for(int y = 0; y < yLength; y++) {
            loc.setX(initX);
            for (int x = 0; x < xLength; x++) {
                loc.setZ(initZ);
                for(int z = 0; z < zLength; z++) {
                    Block referenceBlock = blockLocs.get(counter).getBlock();
                    Block newBlock = loc.getBlock();
                    newBlock.setType(referenceBlock.getType());
                    newBlock.setBlockData(referenceBlock.getBlockData());
                    loc.setZ(loc.getZ() + 1);
                    counter++;
                }
                loc.setX(loc.getX() + 1);
            }
            loc.setY(loc.getY() + 1);
        }
    }
}
