package us.aaronpost.clash.Islands.Buildings.TownHall;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import us.aaronpost.clash.Islands.Building;
public class TownHall extends Building {

    public static String[] SCHEMATICS = { "townhall1", "fillerschematic", "fillerschematic", "fillerschematic",
            "fillerschematic", "fillerschematic", "fillerschematic", "fillerschematic", "fillerschematic",
            "fillerschematic", "fillerschematic", "fillerschematic", "fillerschematic", "fillerschematic",
            "fillerschematic"};
    // These prices are not correct
    public static int[] COST = { 0, 1000, 4000, 5000, 25000, 150000, 750000, 1000000, 2000000, 3000000,
            4000000, 5500000, 8500000, 11500000, 16000000};
    public static String itemName = ChatColor.GOLD + "Town Hall";
    public static ItemStack itemType = new ItemStack(Material.CRAFTING_TABLE);

    public TownHall(int level, int x, int z) {
        super(x, z, SCHEMATICS[level - 1]);
        super.setType(5);
        super.setLevel(level);
        super.setItemType(itemType);
        super.setItemName(itemName);
        super.setGridLengths(4,4);

        // Players will only have ONE townhall and it will already be placed (no need to worry about the special case for new buildings)
        super.changeToAssigned();
    }
}
