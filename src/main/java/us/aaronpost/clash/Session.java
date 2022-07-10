package us.aaronpost.clash;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import us.aaronpost.clash.Islands.Currencies.Currency;
import us.aaronpost.clash.Islands.Currencies.Gold;
import us.aaronpost.clash.Islands.Island;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
public class Session implements Serializable {
    private final Island i;
    private transient Scoreboard scoreboard;
    private List<Currency> currencies = new ArrayList<>();
    private String pName;
    private final UUID u;

    // Time the player left the server
    private long d;

    public Session(Player p) {
        this.pName = p.getName();
        u = p.getUniqueId();
        i = new Island();
        currencies.add(new Gold());
    }

    public long getD() {
        return d;
    }

    public void initializeScoreboard(Player player) {
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("x", "y", ChatColor.BOLD +
                StringUtils.upperCase(player.getName() + "'s Island"));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        int x = 1;
        for(Currency currency: currencies) {
            Score score = objective.getScore(StringUtils.center(StringUtils.upperCase(currency.getInGameName())
                    + ChatColor.GRAY + ": " +
                    String.format("%,d",currency.getAmount()) + ChatColor.DARK_GRAY + "/" + ChatColor.GRAY+
                    String.format("%,d",currency.getMaxCurrency()), 40));
            score.setScore(x++);
        }
        player.setScoreboard(scoreboard);

    }

    public void setD(long d) {
        this.d = d;
    }

    public Island getIsland() {
        return i;
    }



    public Player getPlayer() {
        return Bukkit.getPlayer(u);
    }
    public Session getSession() {
        return this;

    }
}
