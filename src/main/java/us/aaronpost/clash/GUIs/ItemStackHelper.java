package us.aaronpost.clash.GUIs;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * This class contains helper methods for manipulating items in GUIs.
 * Each method generally requires an Inventory, slot, and ItemStack.
 * @author Aaron Post
 */
public class ItemStackHelper {
    // Increments an item stack while maintaining metadata. Updates GUI
    public static void increment(Inventory i, int slot, ItemStack item) {
        if (item != null) {
            if (item.getAmount() < 64) {
                item.setAmount(item.getAmount() + 1);
                i.setItem(slot, item);
            }
        }
    }
    // Increments an item stack while maintaining metadata. Updates GUI
    public static void incrementByFive(Inventory i, int slot, ItemStack item) {
        if (item != null) {
            if (item.getAmount() == 1) {
                item.setAmount(5);
            } else {
                if (item.getAmount() <= 59) {
                    item.setAmount(item.getAmount() + 5);
                } else {
                    item.setAmount(64);
                }
            }
            i.setItem(slot, item);
        }
    }

    // Decrements an item stack while maintaining metadata. Updates GUI
    public static void decrement(Inventory i, int slot, ItemStack item) {
        if (item != null) {
            if (item.getAmount() > 1) {
                item.setAmount(item.getAmount() - 1);
                i.setItem(slot, item);
            }
        }
    }

    // Decrements an item stack while maintaining metadata. Updates GUI
    public static void decrementByFive(Inventory i, int slot, ItemStack item) {

        if (item != null) {
            if (item.getAmount() == 64) {
                item.setAmount(60);
            } else {
                if (item.getAmount() >= 6) {
                    item.setAmount(item.getAmount() - 5);
                } else {
                    item.setAmount(1);
                }
            }
            i.setItem(slot, item);
        }
    }
    // Doubles an item stack whiloe maintaining metadata. Updates GUI
    public static void multiplyByTwo(Inventory i, int slot, ItemStack item) {
        if (item != null) {
            if (item.getAmount() >= 32) {
                item.setAmount(64);
            } else {
                item.setAmount(item.getAmount() * 2);
            }
            i.setItem(slot, item);
        }
    }
}
