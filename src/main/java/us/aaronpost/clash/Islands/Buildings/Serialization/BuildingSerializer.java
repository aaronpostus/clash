package us.aaronpost.clash.Islands.Buildings.Serialization;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import us.aaronpost.clash.Islands.Buildings.ArmyCamp.ArmyCamp;
import us.aaronpost.clash.Islands.Buildings.Barracks.Barracks;
import us.aaronpost.clash.Islands.Building;
import us.aaronpost.clash.Islands.Buildings.BuilderHut.BuilderHut;
import us.aaronpost.clash.Islands.Buildings.GoldMine.GoldMine;
import us.aaronpost.clash.Islands.Buildings.TownHall.TownHall;

import java.lang.reflect.Type;

public class BuildingSerializer implements JsonSerializer<Building> {

    public JsonElement serialize(Building src, Type typeOfSrc, JsonSerializationContext context) {
        switch (src.getType()) {
            case 1: return context.serialize((Barracks) src);
            case 2: return context.serialize((ArmyCamp) src);
            case 3: return context.serialize((BuilderHut) src);
            case 4: return context.serialize((GoldMine) src);
            case 5: return context.serialize((TownHall) src);
            default: return null;
        }
    }
}
