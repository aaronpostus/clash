package us.aaronpost.clash.PersistentData;

import com.google.gson.*;
import us.aaronpost.clash.Buildings.ArmyCamp;
import us.aaronpost.clash.Buildings.Barracks;
import us.aaronpost.clash.Buildings.Building;

import java.lang.reflect.Type;

public class BuildingDeserializer implements JsonDeserializer<Building> {
    @Override
    public Building deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        int myType = json.getAsJsonObject().get("type").getAsInt();
        switch (myType) {
            case 1: return context.deserialize(json, Barracks.class);
            case 2: return context.deserialize(json, ArmyCamp.class);
            default: return null;
        }
    }
}
