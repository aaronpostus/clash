package us.aaronpost.clash.Islands.Buildings.BuilderHut;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import us.aaronpost.clash.Islands.Building;
import us.aaronpost.clash.Schematics.LocationWrapper;

import javax.xml.stream.Location;

public class BuilderHut extends Building {
    // These prices are not correct
    public static int cost = 500;
    public static String itemName = ChatColor.RED + "Builder Hut";
    public static ItemStack itemType = new ItemStack(Material.BRICKS);

    public BuilderHut(int x, int z) {
        super(x,z,"builderhut");
        super.setType(3);
        // Level is superficial in this instance, but we will still set it to one.
        super.setLevel(1);
        super.setGridLengths(2,2);
        super.setItemName(itemName);
        super.setItemType(itemType);
    }
}
