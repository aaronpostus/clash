package us.aaronpost.clash.Islands.Currencies;

import org.bukkit.ChatColor;

public class Gold extends Currency {
    public Gold() {
        super.setAmount(750);
        super.setMaxCurrency(1000);
        super.setIngameName(ChatColor.YELLOW + "Gold");
    }
}
