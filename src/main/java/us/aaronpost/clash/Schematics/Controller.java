package us.aaronpost.clash.Schematics;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Controller implements Listener {
    @EventHandler
    public void onClick(PlayerInteractEvent i) {
        if(i.hasItem()) {
            ItemStack item = i.getItem();
            if (item.hasItemMeta()) {
                ItemMeta meta = item.getItemMeta();
                if (meta.getDisplayName().equals(ChatColor.BLUE + "Schematic Wand")) {
                    if (i.hasBlock()) {
                        EquipmentSlot slot = i.getHand(); //Get the hand of the event and set it to 'e'.
                        if (slot.equals(EquipmentSlot.HAND)) { // checks for main hand to prevent from running twice

                            Location l = i.getClickedBlock().getLocation();
                            ArrayList<String> lore = new ArrayList<>(meta.getLore());

                            int x = (int) Math.floor(l.getX());
                            int y = (int) Math.floor(l.getY());
                            int z = (int) Math.floor(l.getZ());

                            if (i.getAction() == Action.LEFT_CLICK_BLOCK) {
                                i.getPlayer().sendMessage(ChatColor.GOLD + "Position 1: " + ChatColor.GRAY + x + ", " + y + ", " + z);
                                lore.set(0, ChatColor.YELLOW + "x: " + x);
                                lore.set(1, ChatColor.YELLOW + "y: " + y);
                                lore.set(2, ChatColor.YELLOW + "z: " + z);
                                i.setCancelled(true);
                            } else if (i.getAction() == Action.RIGHT_CLICK_BLOCK) {
                                lore.set(3, ChatColor.YELLOW + "x: " + x);
                                lore.set(4, ChatColor.YELLOW + "y: " + y);
                                lore.set(5, ChatColor.YELLOW + "z: " + z);
                                i.getPlayer().sendMessage(ChatColor.GOLD + "Position 2: " + ChatColor.GRAY + x + ", " + y + ", " + z);
                                i.setCancelled(true);
                            }
                            meta.setLore(lore);
                            item.setItemMeta(meta);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void InventoryClick(InventoryClickEvent e) {
        ItemStack itemStack = e.getCurrentItem();
        Inventory i = e.getClickedInventory();
        if (i.getType().equals(InventoryType.ANVIL)) {
            if (e.getSlot() == 2) {
                e.getWhoClicked().sendMessage("clicked");
                if (e.getView().getTitle().equals("Write schematic name")) {
                    e.getWhoClicked().sendMessage("2");
                    Player p = (Player) e.getWhoClicked();
                    ItemStack item = p.getInventory().getItemInMainHand();
                    if (item.hasItemMeta()) {
                        e.getWhoClicked().sendMessage("3");
                        ItemMeta meta = item.getItemMeta();
                        if (meta.hasDisplayName()) {
                            e.getWhoClicked().sendMessage("4");
                            if (meta.getDisplayName().equals(ChatColor.BLUE + "Schematic Wand")) {
                                e.getWhoClicked().sendMessage("5");
                                ArrayList<String> lore = new ArrayList<>(Objects.requireNonNull(meta.getLore()));
                                // this line below is what breaks it idk why it doesnt work
                                p.sendMessage(ChatColor.GOLD + "Created Schematic \"" + e.getView().getTopInventory().getItem(2).getItemMeta().getDisplayName() + "\".");
                                p.sendMessage(ChatColor.GRAY + "1: " + getCoordFromString(lore.get(0))
                                        + ", " + getCoordFromString(lore.get(1)) + ", " + getCoordFromString(lore.get(2))
                                );
                                p.sendMessage(ChatColor.GRAY + "2: " + getCoordFromString(lore.get(3))
                                        + ", " + getCoordFromString(lore.get(4)) + ", " + getCoordFromString(lore.get(5))
                                );
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent i) {
        ItemStack item = i.getItemDrop().getItemStack();
        if(item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            if(meta.hasDisplayName()) {
                if(meta.getDisplayName().equals(ChatColor.BLUE + "Schematic Wand")) {
                    ArrayList<String> lore = new ArrayList<>(Objects.requireNonNull(meta.getLore()));
                    if(checkForCompleteLore(lore)) {
//                        i.getPlayer().sendMessage(ChatColor.GOLD + "Both blocks are selected.");
//                        i.getPlayer().sendMessage(ChatColor.GRAY + "1: " + getCoordFromString(lore.get(0))
//                                + ", " + getCoordFromString(lore.get(1)) + ", " + getCoordFromString(lore.get(2))
//                        );
//                        i.getPlayer().sendMessage(ChatColor.GRAY + "2: " + getCoordFromString(lore.get(3))
//                                + ", " + getCoordFromString(lore.get(4)) + ", " + getCoordFromString(lore.get(5))
//                        );

                        Inventory inven = i.getPlayer().getServer().createInventory(i.getPlayer(), InventoryType.ANVIL, "Write schematic name");
                        ItemStack rod = new ItemStack(Material.NAME_TAG);
                        meta = rod.getItemMeta();
                        meta.setDisplayName("?");
                        meta.setLore(Arrays.asList(ChatColor.GOLD + "Click to create!"));
                        rod.setItemMeta(meta);

                        inven.setItem(0, rod);
                        i.getPlayer().openInventory(inven);
                    } else {
                        i.getPlayer().sendMessage(ChatColor.GOLD + "Lore is incomplete.");
                    }
                    i.setCancelled(true);
                }
            }
        }

    }
    public boolean checkForCompleteLore(ArrayList<String> lore) {
        for(int i = 0; i < 6; i++) {
            if(ChatColor.stripColor(lore.get(i)).length() < 4) {
                return false;
            }
        }
        return true;
    }
    public int getCoordFromString(String str) {
        return Integer.parseInt(ChatColor.stripColor(str.substring(5)));
    }
}
