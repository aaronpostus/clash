package us.aaronpost.clash.Islands.Buildings.GoldMine;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import us.aaronpost.clash.Islands.Building;
public class GoldMine extends Building {

    public static String[] SCHEMATICS = { "goldmine1", "fillerschematic", "fillerschematic", "fillerschematic", "fillerschematic", "fillerschematic", "fillerschematic", "fillerschematic", "fillerschematic", "fillerschematic", "fillerschematic", "fillerschematic", "fillerschematic", "fillerschematic", "fillerschematic"};
    // These prices are not correct
    public static int[] COST = { 100, 500, 2500, 5000, 10000, 75000, 200000, 600000, 900000, 1200000, 1800000, 2500000, 4000000, 5000000, 6000000};
    public static String itemName = ChatColor.YELLOW + "Gold Mine";
    public static ItemStack itemType = new ItemStack(Material.YELLOW_CONCRETE);

    public GoldMine(int level, int x, int z) {
        super(x, z, SCHEMATICS[level - 1]);
        super.setGridLengths(3,3);
        super.setType(4);
        super.setLevel(level);
        super.setItemType(itemType);
        super.setItemName(itemName);
    }

}
