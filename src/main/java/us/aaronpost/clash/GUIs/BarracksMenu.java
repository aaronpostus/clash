package us.aaronpost.clash.GUIs;

import net.citizensnpcs.npc.ai.speech.Chat;
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
    private Barracks b;
    public BarracksMenu(Building b) {

        this.b = (Barracks) b;
    }

    public void newInventory(Player player) {
        // Size must be a multiple of 9
        int specialChar = BHelper.INVENTORY_TITLE.indexOf("`");
        String title = BHelper.INVENTORY_TITLE.substring(0, specialChar) + b.getLevel() +
            BHelper.INVENTORY_TITLE.substring(specialChar + 1);
        i = p.getServer().createInventory(null, 54, title);
        ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);

//        // Online Player Head
        ItemMeta meta = head.getItemMeta();
//        meta.setDisplayName(ChatColor.BLUE + player.getName() + "");
//        head.setItemMeta(meta);
//        // Changes skin of Online Player to player
//        SkullMeta onlinePlayer = (SkullMeta) head.getItemMeta();
//        onlinePlayer.setOwner(player.getName() + "");
//        head.setItemMeta(onlinePlayer);

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

        // Offline player skull
        ItemStack leftArrow = OfflineSkull.getSkull(BHelper.LEFT_ARROW_URL);
        meta = leftArrow.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "<==");
        leftArrow.setItemMeta(meta);

        // Offline player skull
        ItemStack rightArrow = OfflineSkull.getSkull(BHelper.RIGHT_ARROW_URL);
        meta = rightArrow.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "==>");
        rightArrow.setItemMeta(meta);

        ItemStack sign = new ItemStack(Material.OAK_SIGN);
        meta = sign.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Todo sign");
        ArrayList<String> lore = new ArrayList<>(Arrays.asList(
                ChatColor.YELLOW + "    Fill this  ",
                ChatColor.YELLOW + "    lore out.  "));
        meta.setLore(lore);
        sign.setItemMeta(meta);

        ItemStack elixir = new ItemStack(Material.MAGENTA_DYE);
        meta = elixir.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Total Cost: ");
        lore = new ArrayList<>(Arrays.asList(ChatColor.LIGHT_PURPLE + "    0 Elixir"));
        meta.setLore(lore);
        elixir.setItemMeta(meta);

        ItemStack pane = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        meta = pane.getItemMeta();
        meta.setDisplayName(ChatColor.BLACK + " ");
        pane.setItemMeta(meta);

        ItemStack gem = new ItemStack(Material.EMERALD);
        meta = gem.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "Gem");
        gem.setItemMeta(meta);

        ItemStack upgrade = new ItemStack(Material.ARROW);
        meta = upgrade.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "Upgrade");
        upgrade.setItemMeta(meta);

        for(int e = 0; e < 10; e++) {
            i.setItem(e, pane);
        }
        for(int e = 17; e < 28; e += 2) {
            i.setItem(e, pane);
        }
        i.setItem(35, pane);
        i.setItem(36, pane);
        for(int e = 44; e < 54; e ++) {
            i.setItem(e, pane);
        }
        //i.setItem(BHelper.COST_POSITION, elixir);
        i.setItem(29, archer);
        i.setItem(28, barbarian);
        i.setItem(18, leftArrow);
        i.setItem(26, rightArrow);
        i.setItem(20, sign);
        i.setItem(22, gem);
        i.setItem(24, upgrade);

        player.openInventory(i);
    }

    public void openMenu(Player player) {

    }
}