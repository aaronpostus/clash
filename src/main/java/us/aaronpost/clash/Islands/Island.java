package us.aaronpost.clash.Islands;

import org.bukkit.Location;
import us.aaronpost.clash.Arenas.Arena;
import us.aaronpost.clash.Arenas.Arenas;
import us.aaronpost.clash.Islands.Buildings.BuildingInteraction.BuildingInHand;
import us.aaronpost.clash.Islands.Buildings.TownHall.TownHall;
import us.aaronpost.clash.PersistentData.Sessions;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Island implements Serializable {

    private UUID islandGrid[][] = new UUID[44][44];
    private transient Location arenaLoc = null;
    private List<Building> buildings = new ArrayList<>();
    private BuildingInHand buildingInHand = null;

    public Island() {
        TownHall townHall = new TownHall(1, 20,20);
        townHall.setRelativeX(20);
        townHall.setRelativeZ(20);
        buildings.add(townHall);
        addBuildingUUIDsToGrid(townHall);
    }

    public List<Building> getBuildings() {
        return this.buildings;
    }
    public BuildingInHand getBuildingInHand() { return this.buildingInHand; }
    public void removeBuildingInHand() {
        buildingInHand = null;
    }

    public Building buildingFromUUID(UUID u) {
        String uString = u.toString();
        for(Building building : buildings) {
            if(building.getUUID().toString().equals(uString)) {
                return building;
            }
        }
        if(buildingInHand.getBuilding().getUUID().toString().equals(uString)) {
            return buildingInHand.getBuilding();
        }
        return null;
    }

    public void setArenaLoc(Location loc) {
        arenaLoc = loc.clone();
    }
    public Location getArenaLoc() {
        return arenaLoc;
    }
    public void setGridPosUUID(int x, int z, UUID u) {
        islandGrid[x][z] = u;
    }

    public void deleteBuildingUUIDs(Building building) {
        String buildingUUID = building.getUUID().toString();
        for(int i = 0; i < 44; i++) {
            for(int j = 0; j < 44; j++) {
                if(islandGrid[i][j].toString().equals(buildingUUID)) {
                    islandGrid[i][j] = null;
                }
            }
        }
    }
    public void addBuildingUUIDsToGrid(Building building) {
        int x = building.getRelativeX();
        int z = building.getRelativeZ();
        for(int i = 0; i < building.getGridLengthX(); i++) {
            for(int j = 0; j < building.getGridLengthZ(); j++) {
                setGridPosUUID(x+i, z+j, building.getUUID());
            }
        }
    }
    public void deleteAllUUID(UUID u) {
        String uuid = u.toString();
        for(int i = 0; i < islandGrid.length; i++) {
            for(int j = 0; j < islandGrid[i].length; j++) {
                if(islandGrid[i][j] != null) {
                    if (islandGrid[i][j].toString().equals(uuid)) {
                        islandGrid[i][j] = null;
                    }
                }
            }
        }
    }
    public UUID getGridPosUUID(int x, int z) {
        return islandGrid[x][z];
    }
    public Building getBuildingFromUUID(UUID u) {
        for(Building b : buildings) {
            if(b.getUUID().equals(u)) {
                return b;
            }
        }
        return null;
    }

    public void addBuilding(Building b) {
        buildings.add(b);
    }
    public void addBuilding(List<Building> t) {
        buildings.addAll(t);
    }

    private BuildingInHand putNewBuildingInHand(Building building) {
        BuildingInHand buildingInHand = new BuildingInHand(building,
                true);
        putBuildingInHand(buildingInHand);
        return buildingInHand;
    }

    public void putBuildingInHand(BuildingInHand b) { buildingInHand = b; }

}
