package us.aaronpost.clash.GUIs;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import us.aaronpost.clash.Clash;
import us.aaronpost.clash.Schematics.Schematic;
import us.aaronpost.clash.Schematics.Schematics;
import us.aaronpost.clash.Troops.BHelper;

import java.util.ArrayList;

public class AdminSchematicMenu implements Listener {
    private Inventory i;
    private ArrayList<Schematic> s;
    private final int pages;
    private int currentPage;
    public AdminSchematicMenu(Player p) {
        i = p.getServer().createInventory(null, 54, ChatColor.GREEN + "+ Schematics +");

        s = (ArrayList<Schematic>) Schematics.s.getSchematics();

        // Offline player skull
        ItemStack leftArrow = OfflineSkull.getSkull(BHelper.LEFT_ARROW_URL);
        ItemMeta meta = leftArrow.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "<==");
        leftArrow.setItemMeta(meta);

        // Offline player skull
        ItemStack rightArrow = OfflineSkull.getSkull(BHelper.RIGHT_ARROW_URL);
        meta = rightArrow.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "==>");
        rightArrow.setItemMeta(meta);

        pages = (int) Math.floor(((double) s.size()) / 45);
        currentPage = 1;

        i.setItem(53, rightArrow);
        i.setItem(45, leftArrow);

        formatPage(1);
        p.openInventory(i);
    }

    @EventHandler
    public void InventoryClick(InventoryClickEvent i) {
        InventoryView e = i.getView();
        if (e.getTitle().contains(ChatColor.GREEN + "+ Schematics +")) {
            i.setCancelled(true);
            if(i.getCurrentItem().getType().equals(Material.PAPER)) {
                for(Schematic schematic: s) {
                    if(schematic.getName().equals(ChatColor.stripColor(i.getCurrentItem().getItemMeta().getDisplayName()))) {
                        schematic.pasteSchematic(i.getWhoClicked().getLocation(), 0);
                        break;
                    }
                }
            } else if (i.getCurrentItem().getType().equals(Material.PLAYER_HEAD)) {
                if(i.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "<==")) {
                    if(currentPage > 1) {
                        currentPage--;
                        clearSlots();
                        formatPage(currentPage);
                    }
                } else if (i.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "==>")) {
                    if(currentPage < pages) {
                        currentPage++;
                        clearSlots();
                        formatPage(currentPage);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if(e.getView().getTitle().equals(ChatColor.GREEN + "+ Schematics +")) {
            HandlerList.unregisterAll(this);
        }
    }

    public void clearSlots() {
        ItemStack air = new ItemStack(Material.AIR);
        for(int e = 0; e < 45; e++) {
            i.setItem(e, air);
        }

    }

    public void formatPage(int page) {

        ItemStack schematic = new ItemStack(Material.PAPER);
        ItemMeta meta = schematic.getItemMeta();

        int startIndex = ((page - 1) * 45);
        int endIndex = (page * 45);
        if(s.size() < endIndex) {
            endIndex = s.size();
        }

        for(int i = startIndex; i < endIndex; i++) {
            meta.setDisplayName(ChatColor.GOLD + s.get(i).getName());
            schematic.setItemMeta(meta);
            this.i.setItem(i, schematic);
        }
    }
}
