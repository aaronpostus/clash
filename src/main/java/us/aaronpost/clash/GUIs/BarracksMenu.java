package us.aaronpost.clash.GUIs;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;
import us.aaronpost.clash.Buildings.Barracks;
import us.aaronpost.clash.Buildings.Building;
import us.aaronpost.clash.Clash;
import us.aaronpost.clash.Troops.BHelper;

import java.util.ArrayList;
import java.util.Arrays;

public class BarracksMenu implements Listener {
    private Plugin p = Clash.getPlugin(Clash.class);
    private Inventory i;
    private Building b;
    public BarracksMenu(Building b) {
        this.b = b;
    }

    public void newInventory(Player player) {
        // Size must be a multiple of 9
        i = p.getServer().createInventory(null, 18, BHelper.INVENTORY_TITLE);
        ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);

        // Online Player Head
        ItemMeta meta = head.getItemMeta();
        meta.setDisplayName(ChatColor.BLUE + player.getName() + "");
        head.setItemMeta(meta);
        // Changes skin of Online Player to player
        SkullMeta onlinePlayer = (SkullMeta) head.getItemMeta();
        onlinePlayer.setOwner(player.getName() + "");
        head.setItemMeta(onlinePlayer);

        // Offline player skull
        ItemStack barbarian = OfflineSkull.getSkull(BHelper.BARBARIAN_URL[0]);
        meta = barbarian.getItemMeta();
        meta.setDisplayName(BHelper.BARBARIAN_TITLE);
        barbarian.setItemMeta(meta);

        // Offline player skull
        ItemStack archer = OfflineSkull.getSkull(BHelper.ARCHER_URL[0]);
        meta = archer.getItemMeta();
        meta.setDisplayName(BHelper.ARCHER_TITLE);
        archer.setItemMeta(meta);

        ItemStack sign = new ItemStack(Material.OAK_SIGN);
        meta = sign.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Click Tutorial:");
        ArrayList<String> lore = new ArrayList<>(Arrays.asList(
                ChatColor.YELLOW + "    Left: +1  ",
                ChatColor.YELLOW + "    Right: +1  ",
                ChatColor.YELLOW + "    Shift Left: +5  ",
                ChatColor.YELLOW + "    Shift Right: -5  ",
                ChatColor.YELLOW + "    Middle: x2  "));
        meta.setLore(lore);
        sign.setItemMeta(meta);

        ItemStack elixir = new ItemStack(Material.MAGENTA_DYE);
        meta = elixir.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Total Cost: ");
        lore = new ArrayList<>(Arrays.asList(ChatColor.LIGHT_PURPLE + "    0 Elixir"));
        meta.setLore(lore);
        elixir.setItemMeta(meta);

        ItemStack disable = new ItemStack(Material.RED_WOOL);
        meta = disable.getItemMeta();
        meta.setDisplayName(BHelper.DISABLED_TITLE);
        disable.setItemMeta(meta);

        for(int e = 2; e < 7; e++) {
            i.setItem(e, disable);
        }
        i.setItem(0, head);
        i.setItem(BHelper.COST_POSITION, elixir);
        i.setItem(12, archer);
        i.setItem(13, barbarian);
        i.setItem(17, sign);

        player.openInventory(i);
    }

    public void openMenu(Player player) {

    }
}