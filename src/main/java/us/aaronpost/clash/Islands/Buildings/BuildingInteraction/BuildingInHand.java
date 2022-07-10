package us.aaronpost.clash.Islands.Buildings.BuildingInteraction;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import us.aaronpost.clash.Arenas.Arenas;
import us.aaronpost.clash.Islands.Building;
import us.aaronpost.clash.Islands.Island;
import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

/**
 * BuildingInHand will follow the user's cursor on the island and paste a given building's schematic at that location,
 * and unpaste it as they move their cursor around. A special case MUST be considered for "new buildings" which won't
 * be created until they are placed. Buildings can be placed by left clicking, or "cancelled" by right clicking. Right
 * clicking for new buildings will prevent buying the building. Right clicking for old buildings will restore the building
 * to its original location. No actual data for existing buildings should be changed until it is actually placed back
 * down. This will be more efficient as well as redundant in case a player leaves before they place their building
 * back down. This listener should be deleted if the player leaves.
 */
public class BuildingInHand implements Serializable {
    private boolean isNewBuilding;
    // Cant be saved
    private int x;
    private int z;
    private Building building;

    //Player p = new Player

    /**
     * @requires Player to already be assigned to an arena.
     * @param building
     * @param isNewBuilding
     */
    public BuildingInHand(Building building, boolean isNewBuilding) {
        this.building = building;
        Timer timer = new Timer();
        // Action bar stuff
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //spigot().sendMessage(ChatMessageType.ACTION_BAR);
            }
        }, 10000);
    }
    // Will check if schematic needs an update.
    public boolean checkIfSchematicNeedsUpdate(Location location) {
        // 500 is definitely overkill. This could be lowered to about 100 and probably work perfectly.
        //Location location = player.getTargetBlockExact(500).getLocation();
        int x = location.getBlockX();
        int z = location.getBlockZ();
        if(x != this.x || z != this.z) {
            // Needs to check if building can fit in target location.
            //if(Arenas.a.findPlayerArena(player).fitSquareAtGridLoc(building)) {
                //return true;
            //}
        }
        return false;
    }
    public Building getBuilding() {
        return building;
    }
}
