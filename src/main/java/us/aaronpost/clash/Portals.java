package us.aaronpost.clash;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import us.aaronpost.clash.Arenas.Arenas;
import us.aaronpost.clash.Schematics.LocationWrapper;
import us.aaronpost.clash.Troops.BHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Portals implements Listener {
    private Map<Location, String> portalLocs = new HashMap<>();
    public Portals() {
        portalLocs.put(new Location(BHelper.world, -424, 121, 292), "clash");
        portalLocs.put(new Location(BHelper.world, -424, 121, 238), "pvp");
    }
    public String findClosestPortal(Player p) {
        String portal = "noPortalFound";
        Location playerLoc = p.getLocation();
        int x = playerLoc.getBlockX();
        int z = playerLoc.getBlockZ();
        double shortestDistance = 1000000000;
        for(Map.Entry<Location, String> loc : portalLocs.entrySet()) {
            double differenceX = Math.abs(x - loc.getKey().getX());
            double differenceZ = Math.abs(z - loc.getKey().getZ());
            double distance = Math.sqrt(Math.pow(differenceX,2) + Math.pow(differenceZ,2));
            if(distance <= shortestDistance) {
                shortestDistance = distance;
                portal = loc.getValue();
            }
        }
        if(shortestDistance > 10) {
            return "alreadyTeleported";
        }
        return portal;
    }
    @EventHandler
    public void portal(PlayerPortalEvent p) {
        p.setCancelled(true);
    }
    @EventHandler
    public void portalEntrance(EntityPortalEnterEvent e) {
        if(e.getEntityType().equals(EntityType.PLAYER)) {
            Player p = (Player) e.getEntity();
            switch (findClosestPortal(p)) {
                case "clash":
                    if(Arenas.a.findPlayerArena(p) == null) {
                        p.performCommand("island");
                    }
                    break;
                case "pvp":
                    Arenas.a.sendToSpawn(p);
                    break;
                case "alreadyTeleported":
                    break;
            }
        }

    }
    @EventHandler
    public void portalTravel(PlayerPortalEvent e) {
        e.setCancelled(true);
    }
}
