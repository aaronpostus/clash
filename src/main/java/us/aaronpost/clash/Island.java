package us.aaronpost.clash;

import us.aaronpost.clash.Buildings.Building;

import java.util.ArrayList;

public class Island {
    ArrayList<Building> buildings = new ArrayList<>();
    public Island() {

    }
    public void addBuilding(Building b) {
        buildings.add(b);
    }
    public void addBuilding(ArrayList<Building> b) {
        buildings.addAll(b);
    }
    public ArrayList<Building> getBuildings() {
        return this.buildings;
    }
}
