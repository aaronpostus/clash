package us.aaronpost.clash;

import us.aaronpost.clash.Buildings.Building;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Island implements Serializable {
    List<Building> buildings = new ArrayList<>();
    public Island() {

    }
    public void addBuilding(Building b) {
        buildings.add(b);
    }
    public void addBuilding(ArrayList<Building> b) {
        buildings.addAll(b);
    }
    public List<Building> getBuildings() { return this.buildings; }
}
