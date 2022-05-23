package us.aaronpost.clash;

import org.bukkit.entity.Player;
import us.aaronpost.clash.Buildings.Building;

import java.util.ArrayList;
import java.util.UUID;

public class ClashSession {
    private Island i;
    private Player p;
    private UUID u;
    private int elixir, gold;
    public ClashSession(Player p) {
        this.p = p;
        u = p.getUniqueId();
        i = new Island();
    }
    public Island getIsland() {
        return i;
    }

    public int getElixir() {
        return elixir;
    }

    public void setElixir(int elixir) {
        this.elixir = elixir;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public Player getPlayer() {
        return p;
    }
    public ClashSession getSession() {
        return this;

    }
}
