package us.aaronpost.clash.PersistentData;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import us.aaronpost.clash.Buildings.ArmyCamp;
import us.aaronpost.clash.Buildings.Barracks;
import us.aaronpost.clash.Buildings.Building;

import java.lang.reflect.Type;

public class BuildingSerializer implements JsonSerializer<Building> {

    public JsonElement serialize(Building src, Type typeOfSrc, JsonSerializationContext context) {
        switch (src.getType()) {
            case 1: return context.serialize((Barracks) src);
            case 2: return context.serialize((ArmyCamp) src);
            default: return null;
        }
    }
}
