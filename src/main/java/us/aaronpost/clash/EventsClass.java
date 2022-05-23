package us.aaronpost.clash;

import com.google.gson.Gson;
import net.citizensnpcs.api.event.CitizensEnableEvent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Campfire;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import us.aaronpost.clash.Buildings.ArmyCamp;
import us.aaronpost.clash.Buildings.Barracks;
import us.aaronpost.clash.GUIs.BarracksMenu;
import us.aaronpost.clash.GUIs.DynamicItem;
import us.aaronpost.clash.GUIs.ItemStackHelper;
import us.aaronpost.clash.Troops.BHelper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

public class EventsClass implements Listener {
    private ArrayList<ClashSession> sessions = new ArrayList<>();
    @EventHandler
    public void InventoryClick(InventoryClickEvent i) {
        InventoryView e = i.getView();
        if(e.getTitle().equals(BHelper.INVENTORY_TITLE)) {
            if(i.getClickedInventory().equals(e.getTopInventory())) {
                Player p = (Player) i.getWhoClicked();
                String name = i.getCurrentItem().getItemMeta().getDisplayName();
                if(name.equals(BHelper.BARBARIAN_TITLE) || name.equals(BHelper.ARCHER_TITLE)) {
                    switch(i.getClick().name()) {
                        case "LEFT":
                            ItemStackHelper.increment(i.getClickedInventory(), i.getSlot(), i.getCurrentItem());
                            break;
                        case "RIGHT":
                            ItemStackHelper.decrement(i.getClickedInventory(), i.getSlot(), i.getCurrentItem());
                            break;
                        case "SHIFT_LEFT":
                            ItemStackHelper.incrementByFive(i.getClickedInventory(), i.getSlot(), i.getCurrentItem());
                            break;
                        case "SHIFT_RIGHT":
                            ItemStackHelper.decrementByFive(i.getClickedInventory(), i.getSlot(), i.getCurrentItem());
                            break;
                        case "MIDDLE":
                            ItemStackHelper.multiplyByTwo(i.getClickedInventory(), i.getSlot(), i.getCurrentItem());
                            break;
                    }
                }
                if(name.equals(BHelper.ENABLED_TITLE) || name.equals(BHelper.DISABLED_TITLE)) {
                    ItemStack enable = new ItemStack(Material.LIME_WOOL);
                    ItemMeta meta = enable.getItemMeta();
                    meta.setDisplayName(BHelper.ENABLED_TITLE);
                    enable.setItemMeta(meta);

                    ItemStack disable = new ItemStack(Material.RED_WOOL);
                    meta = disable.getItemMeta();
                    meta.setDisplayName(BHelper.DISABLED_TITLE);
                    disable.setItemMeta(meta);

                    ItemStack[] options = {disable, enable};
                    DynamicItem button;
                    if(name.equals(BHelper.ENABLED_TITLE)) {
                        button = new DynamicItem(options, 1);
                    } else {
                        button = new DynamicItem(options, 0);
                    }
                    ItemStack updatedButton;
                    if(i.getClick().isLeftClick()) {
                        updatedButton = button.cycleForwards();
                    } else {
                        updatedButton = button.cycleBackwards();
                    }
                    i.getClickedInventory().setItem(i.getSlot(), updatedButton);

                }
                BHelper.updateElixirIcon(i.getClickedInventory(), BHelper.calculateElixirForInventory(i.getClickedInventory(), p));
            }
            // There are loopholes to this. This must be fixed.
            i.setCancelled(true);
        }
    }
    @EventHandler
    public void InventoryClose(InventoryCloseEvent e) {
        if(e.getView().getTitle().equals(BHelper.INVENTORY_TITLE)) {
            Player p = (Player) e.getPlayer();
            p.sendMessage(ChatColor.LIGHT_PURPLE + "Total Cost: " + BHelper.calculateElixirForInventory(e.getInventory(), p) + "");
        }
    }

    public void CitizensEnable(CitizensEnableEvent e) {
        // Do stuff idk this isn't really necessary
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent p) {
        // Load their files up!m, .
        sessions.add(new ClashSession(p.getPlayer()));
    }

    public void setSessions(ArrayList<ClashSession> sessions) {
        this.sessions = sessions;
    }
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent p) {
        ClashSession c = getSession(p.getPlayer());
        try {
            saveSession(p.getPlayer(), c);
        } catch(IOException e) {
            Clash.getPlugin().getLogger().info("IOException encountered.");
        }
        sessions.remove(c);
        Clash.getPlugin().getLogger().info("The island of " + p.getPlayer() + "has been saved.");
    }
    public void saveSession(Player p, ClashSession c) throws IOException{
        Gson gson = new Gson();
        File file = new File(Clash.getPlugin().getDataFolder().getAbsolutePath() + "/" + p.getPlayer().getUniqueId() +".json");
        file.getParentFile().mkdir();
        file.createNewFile();
        Writer writer = new FileWriter(file, false);
        gson.toJson(c, writer);
        writer.flush();
        writer.close();
    }
    public ClashSession getSession(Player p) {
        for(ClashSession s: sessions) {
            if(s.getPlayer().equals(p)) {
                return s;
            }
        }
        return null;
    }
    @EventHandler
    public void onRightClick(PlayerInteractEvent i) {
        EquipmentSlot slot = i.getHand(); //Get the hand of the event and set it to 'e'.
        if (slot.equals(EquipmentSlot.HAND)) { // checks for main hand to prevent from running twice
            if (i.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (i.getClickedBlock().getType().equals(Material.RED_CONCRETE)) {
                    ClashSession s = getSession(i.getPlayer());
                    //Implement logic for detecting buildings, not just a block for barracks lol
                    if (!(s.equals(null))) {
                        for (int e = 0; e < s.getIsland().getBuildings().size(); e++) {
                            if (s.getIsland().getBuildings().get(e).getLoc().equals(i.getClickedBlock().getLocation())) {
                                BarracksMenu menu = new BarracksMenu((Barracks) s.getIsland().getBuildings().get(e));
                                menu.newInventory(i.getPlayer());
                                break;
                            }
                        }
                    }
                } else if (i.getClickedBlock().getType().equals(Material.CAMPFIRE)) {
                    ClashSession s = getSession(i.getPlayer());
                    //Implement logic for detecting buildings, not just a block for barracks lol
                    if (!(s.equals(null))) {
                        for (int e = 0; e < s.getIsland().getBuildings().size(); e++) {
                            if (s.getIsland().getBuildings().get(e).getLoc().equals(i.getClickedBlock().getLocation())) {
                                i.getPlayer().sendMessage("Campfire");
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent b) {
        if (b.getItemInHand().getType().equals(Material.RED_CONCRETE) && b.getItemInHand().getItemMeta().getDisplayName().equals("Barracks")) {
            b.getPlayer().sendMessage("Placed barracks.");
            Barracks barracks = new Barracks();
            barracks.setLocation(b.getBlock().getLocation());
            getSession(b.getPlayer()).getIsland().addBuilding(barracks);
        } else if(b.getItemInHand().getType().equals(Material.CAMPFIRE) && b.getItemInHand().getItemMeta().getDisplayName().equals("Army Camp")) {
            b.getPlayer().sendMessage("Placed army camp.");
            ArmyCamp camp = new ArmyCamp();
            camp.setLocation(b.getBlock().getLocation());
            getSession(b.getPlayer()).getIsland().addBuilding(camp);
        }
    }
}
