package us.aaronpost.clash.Troops;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class BHelper {
    public static String INVENTORY_TITLE = ChatColor.RED + "Barracks";
    public static String ENABLED_TITLE = ChatColor.GREEN + "Training..";
    public static String DISABLED_TITLE = ChatColor.RED + "Not training.. (Click)";

    public static String[] BARBARIAN_URL = { "http://textures.minecraft.net/texture/d32985200010aaa0465f6692fac619f5bafe68b62270f243f1fb6a3333cc40fe" };
    public static String[] BARBARIAN_SIGNATURE = { "IKYQYgpJz7qL+6rg2+U+L1fByImCVvRABaLFo6iwgj9HAxXeIwTt0ESIcwYft9zQEd1xmtlpRpe7A5Unk+gM1jLzJqpz/bYbDBZYnL4062cAOPyO4VAZjyakVcPRaVCdenoSEfTHlOWoczSS+JD4zSQZP24FrgYK5N/+BKRbs5cKvrGtUoyAZAIyXFj8khoMaZ4Ydw1x3BwDJAc/gvrlbmTiZNrZDEXUER1YcNE2wfvWHUjPbghru95RN+6WVnUWyKn+lmVvs/PaK7qkr3GfiueK+9sVLQg+xH34oE0MGAljmKjMj+/TLqODf6Qy+IUeN1Xx4mfywuMHsFE/K1O5dC1S5q/7JmJHhgIHpsUzU2hk9PXJRyCW2Hw1MJDI8inSvjR3r2Fz7Jt71Mgx94u1xH2ur3W8cF36SWDa5LNYfsoV47m+GAWBLvxXwJeycJag/PuzmEticzGlmDMeIqC1oRMaUksFUKy68MqFoet20HVTlfwIRcIk5sqon17IX+pJUJtmcXGeN3WLvS5rN2q2Wz0QMU+7z6nAkrKfh/F8HYe8ulkrrjyD5htnPHZDQY3MxSq6RhRcLO+nkoZhOqkjB8rB3L7fcHPfjxWJiCISwFwWdqvQpIpor+92oFeBwE8sruDN/l0fsQeC0RZSmAtNlsh8oxJtyBtG9KHTKt4/cXI=",
            ""};
    public static String[] BARBARIAN_VALUE = { "ewogICJ0aW1lc3RhbXAiIDogMTY1MjQ5MDc3MTk1OSwKICAicHJvZmlsZUlkIiA6ICIzOTVkZTJlYjVjNjU0ZmRkOWQ2NDAwY2JhNmNmNjFhNyIsCiAgInByb2ZpbGVOYW1lIiA6ICJzcGFyZXN0ZXZlIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2QzMjk4NTIwMDAxMGFhYTA0NjVmNjY5MmZhYzYxOWY1YmFmZTY4YjYyMjcwZjI0M2YxZmI2YTMzMzNjYzQwZmUiCiAgICB9CiAgfQp9",
            "" };
    public static String[] BARBARIAN_USERNAME = { "skin20d13f5d" };
    public static String BARBARIAN_TITLE = ChatColor.YELLOW + "Barbarian";
    public static int BARBARIAN_POSITION = 13;
    public static int[] BARBARIAN_COST = {15, 30, 60, 100, 150, 200, 250, 300, 350, 400};
    public static String[] ARCHER_URL = { "http://textures.minecraft.net/texture/e95016226b3d7dfcc165c6c7bb60a5a2c619f52e880868faac4315e31153c248" };
    public static String ARCHER_TITLE = ChatColor.LIGHT_PURPLE + "Archer";
    public static int[] ARCHER_COST = {30, 60, 120, 200, 300, 400, 500, 600, 700, 800};
    public static int ARCHER_POSITION = 12;
    public static int COST_POSITION = 8;

    // Needs to be adjusted to check a player's level of their barracks
    public static int calculateElixirForInventory(Inventory i, Player p) {
        int elixir = 0;
        if(i.getItem(BARBARIAN_POSITION - 9).getItemMeta().getDisplayName().equals(ENABLED_TITLE)) {
            elixir += (i.getItem(BARBARIAN_POSITION).getAmount() * BARBARIAN_COST[0]);
        }
        if(i.getItem(ARCHER_POSITION - 9).getItemMeta().getDisplayName().equals(ENABLED_TITLE)) {
            elixir += (i.getItem(ARCHER_POSITION).getAmount() * ARCHER_COST[0]);
        }
        return elixir;
    }
    public static void updateElixirIcon(Inventory i, int elixir) {
        ItemMeta meta = i.getItem(COST_POSITION).getItemMeta();
        ArrayList<String> lore = new ArrayList<>(Arrays.asList(ChatColor.LIGHT_PURPLE + "    " + elixir +" Elixir"));
        meta.setLore(lore);
        i.getItem(COST_POSITION).setItemMeta(meta);
    }
}
