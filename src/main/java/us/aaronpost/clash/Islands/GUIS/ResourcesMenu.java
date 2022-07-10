package us.aaronpost.clash.Islands.GUIS;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import us.aaronpost.clash.Islands.Building;
import us.aaronpost.clash.Islands.Buildings.BuilderHut.BuilderHut;
import us.aaronpost.clash.Islands.Buildings.BuildingInteraction.BuildingInHand;
import us.aaronpost.clash.Islands.Buildings.GoldMine.GoldMine;
import us.aaronpost.clash.Islands.Island;
import us.aaronpost.clash.PersistentData.Sessions;
import us.aaronpost.clash.Troops.BHelper;

import java.util.*;

public class ResourcesMenu implements Listener {
    private final Inventory categorySelect;
    private final Player p;

    public ResourcesMenu(Player p) {
        this.p = p;
        categorySelect = p.getServer().createInventory(null, 27, ChatColor.GOLD + "+ Resources +");
        ItemStack stack = new ItemStack(Material.YELLOW_CONCRETE);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(ChatColor.YELLOW + "Gold Mine");
        stack.setItemMeta(meta);
        categorySelect.setItem(10, stack);

        stack = new ItemStack(Material.MAGENTA_CONCRETE);
        meta = stack.getItemMeta();
        meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Elixir Collector");
        stack.setItemMeta(meta);
        categorySelect.setItem(12, stack);

        stack = new ItemStack(Material.GRAY_CONCRETE);
        meta = stack.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Dark Elixir Drill");
        stack.setItemMeta(meta);
        categorySelect.setItem(14, stack);

        stack = new ItemStack(Material.BRICKS);
        meta = stack.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Builder Hut");
        stack.setItemMeta(meta);
        categorySelect.setItem(16, stack);

        p.openInventory(categorySelect);
    }

    @EventHandler
    public void onInvenClose(InventoryCloseEvent e) {
        if(e.getPlayer().equals(p)) {
            HandlerList.unregisterAll(this);
        }
    }

    @EventHandler
    public void onItemClick(InventoryClickEvent e) {
        if(e.getWhoClicked().equals(p)) {
            e.setCancelled(true);
            if(e.getCurrentItem() != null) {
                // Kills the listener for this class, closes inventory
                HandlerList.unregisterAll(this);
                e.getWhoClicked().closeInventory();
                Island island = Sessions.s.getSession(p).getIsland();
                if(island.getBuildingInHand() == null) {
                    ItemStack stack = new ItemStack(Material.AIR);
                    switch (e.getCurrentItem().getType()) {
                        case YELLOW_CONCRETE: {
                            // Creates a new building and puts it in the "hand" in the background
                            BuildingInHand buildingInHand = new BuildingInHand(new GoldMine(1, 700, 700), true);
                            island.putBuildingInHand(buildingInHand);
                            // Gets building ItemStack with formatted lore, and gives it to player
                            Building building = buildingInHand.getBuilding();
                            stack = building.getItemStack();
                            break;
                        }
                        case MAGENTA_CONCRETE:
                            e.getWhoClicked().sendMessage(BHelper.prefix + " Elixir Collector");
                            HandlerList.unregisterAll(this);
                            e.getWhoClicked().closeInventory();
                            break;
                        case GRAY_CONCRETE:
                            e.getWhoClicked().sendMessage(BHelper.prefix + " Dark Elixir Drill");
                            HandlerList.unregisterAll(this);
                            e.getWhoClicked().closeInventory();
                            break;
                        case BRICKS: {
                            // Creates a new building and puts it in the "hand" in the background
                            BuildingInHand buildingInHand = new BuildingInHand(new BuilderHut(700, 700), true);
                            island.putBuildingInHand(buildingInHand);
                            // Gets building ItemStack with formatted lore, and gives it to player
                            Building building = buildingInHand.getBuilding();
                            stack = building.getItemStack();
                            break;
                        }
                    }
                    e.getWhoClicked().getInventory().addItem(stack);
                } else {
                    p.sendMessage(BHelper.prefix + ChatColor.GRAY + " You may only have " + ChatColor.RED + "one" + ChatColor.GRAY + " building picked up at a time.");

                }
            }
        }
    }
}
