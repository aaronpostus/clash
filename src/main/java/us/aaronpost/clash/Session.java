package us.aaronpost.clash;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.io.Serializable;
import java.util.UUID;
public class Session implements Serializable {
    private final Island i;
    private String pName;
    private final UUID u;
    private int elixir, gold;
    public Session(Player p) {
        this.pName = p.getName();
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
        return Bukkit.getPlayer(u);
    }
    public Session getSession() {
        return this;

    }
}
