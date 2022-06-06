package us.aaronpost.clash.Schematics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Schematics implements Serializable {
    private List<Schematic> schematics = new ArrayList<>();
    public static Schematics s = new Schematics();

    public void addSchematic(Schematic s) {
        schematics.add(s);
    }
    public List<Schematic> getSchematics() {
        return schematics;
    }
}
