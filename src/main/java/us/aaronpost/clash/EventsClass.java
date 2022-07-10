package us.aaronpost.clash;

import net.citizensnpcs.api.event.CitizensEnableEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import us.aaronpost.clash.GUIs.DynamicItem;
import us.aaronpost.clash.GUIs.ItemStackHelper;
import us.aaronpost.clash.Troops.BHelper;

public class EventsClass implements Listener {

    @EventHandler
    public void InventoryClick(InventoryClickEvent i) {
        InventoryView e = i.getView();
        if(e.getTitle().contains("Barracks")) {
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

    @EventHandler
    public void onFallDamage(EntityDamageEvent e) {
        if(e.getEntityType().equals(EntityType.PLAYER)) {
            if(e.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void HungerDeplete(FoodLevelChangeEvent e) {
        // 20 is full, 0 is empty
        e.setCancelled(true);
        e.getEntity().setFoodLevel(20);
    }

    public void CitizensEnable(CitizensEnableEvent e) {
        // Do stuff idk this isn't really necessary
    }

}
