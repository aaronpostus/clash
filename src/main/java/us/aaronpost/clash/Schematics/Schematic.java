package us.aaronpost.clash.Schematics;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import us.aaronpost.clash.Clash;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Schematic implements Serializable {
    private final int xLength, yLength, zLength;
    private List<LocationWrapper> blockLocs = new ArrayList<>();
    public Schematic(Block b1, Block b2) {

        World world = b1.getWorld();

        // Get coords of each cuboid selection point

        int b1x = (int) Math.floor(b1.getX());
        int b1y = (int) Math.floor(b1.getY());
        int b1z = (int) Math.floor(b1.getZ());

        int b2x = (int) Math.floor(b2.getX());
        int b2y = (int) Math.floor(b2.getY());
        int b2z = (int) Math.floor(b2.getZ());

        // Get extrema coordinates in every direction

        int westExt = Math.min(b1x, b2x);
        int eastExt = Math.max(b1x, b2x);

        int northExt = Math.min(b1z, b2z);
        int southExt = Math.max(b1z, b2z);

        int upExt = Math.max(b1y, b2y);
        int downExt = Math.min(b1y, b2y);

        xLength = Math.abs(eastExt - westExt);
        yLength = Math.abs(upExt - downExt);
        zLength = Math.abs(northExt - southExt);

        // Save locations for every block within the cuboid

        for(int y = downExt; y <= upExt; y++) {
            for (int x = westExt; x <= eastExt; x++) {
                for(int z = southExt; z <= northExt; z++) {
                    blockLocs.add(new LocationWrapper(new Location(world, x,y,z)));
                }
            }
        }
    }

    public void pasteSchematic(Location loc) {
        Clash.getPlugin().getServer().getPlayer("Aaronn").sendMessage("yLength" + yLength + "xLength" + xLength + "zLength" +zLength);
        for(int y = 0; y < yLength; y++) {
            loc.setY(loc.getY() + y);
            for (int x = 0; x < xLength; x++) {
                loc.setX(loc.getX() + x);
                for(int z = 0; z < zLength; z++) {
                    loc.setZ(loc.getZ() + y);
                    if(blockLocs.size() > y+x+z) {
                        Clash.getPlugin().getServer().getPlayer("Aaronn").sendMessage("Block done");
                        Block referenceBlock = blockLocs.get(y + x + z).getBlock();
                        Block newBlock = loc.getBlock();
                        newBlock.setType(referenceBlock.getType());
                        newBlock.setBlockData(referenceBlock.getBlockData());
                    } else {
                        Clash.getPlugin().getServer().getPlayer("Aaronn").sendMessage("No coords!");
                    }
                }
            }
        }
    }
}
