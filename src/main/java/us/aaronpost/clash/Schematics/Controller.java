package us.aaronpost.clash.Schematics;

import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
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
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import us.aaronpost.clash.Clash;

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
    public void onDrop(PlayerDropItemEvent i) {
        ItemStack item = i.getItemDrop().getItemStack();
        if(item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            if(meta.hasDisplayName()) {
                if(meta.getDisplayName().equals(ChatColor.BLUE + "Schematic Wand")) {
                    ArrayList<String> lore = new ArrayList<>(Objects.requireNonNull(meta.getLore()));
                    if(checkForCompleteLore(lore)) {
                        new AnvilGUI.Builder()
                                .onComplete((player, text) -> {                                    //called when the inventory output slot is clicked
                                    int x = getCoordFromString(lore.get(0));
                                    int y = getCoordFromString(lore.get(1));
                                    int z = getCoordFromString(lore.get(2));

                                    Location l = new Location(i.getPlayer().getWorld(), x,y,z);
                                    Block b1 = l.getBlock();

                                    player.sendMessage(ChatColor.GOLD + "Created Schematic \"" + text + "\".");
                                    player.sendMessage(ChatColor.GRAY + "1: " + x + ", " + y + ", " + z);

                                    x = getCoordFromString(lore.get(3));
                                    y = getCoordFromString(lore.get(4));
                                    z = getCoordFromString(lore.get(5));

                                    l = new Location(i.getPlayer().getWorld(), x,y,z);
                                    Block b2 = l.getBlock();

                                    player.sendMessage(ChatColor.GRAY + "2: " + x + ", " + y + ", " + z);

                                    Schematics.s.addSchematic(new Schematic(b1, b2));

                                    return AnvilGUI.Response.close();
                                })
                                .text("?")                              //sets the text the GUI should start with
                                .itemLeft(new ItemStack(Material.NAME_TAG))                      //use a custom item for the first slot
//                                .itemRight(new ItemStack(Material.IRON_SWORD))                     //use a custom item for the second slot
//                                .onLeftInputClick(player -> player.sendMessage("first sword"))     //called when the left input slot is clicked
//                                .onRightInputClick(player -> player.sendMessage("second sword"))   //called when the right input slot is clicked
                                .title("Write schematic name:")                                       //set the title of the GUI (only works in 1.14+)
                                .plugin(Clash.getPlugin())                                          //set the plugin instance
                                .open(i.getPlayer());                                                   //opens the GUI for the player provided
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
