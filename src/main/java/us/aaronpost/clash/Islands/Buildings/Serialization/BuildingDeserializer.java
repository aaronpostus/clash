package us.aaronpost.clash.Islands.Buildings.Serialization;

import com.google.gson.*;
import us.aaronpost.clash.Islands.Buildings.ArmyCamp.ArmyCamp;
import us.aaronpost.clash.Islands.Buildings.Barracks.Barracks;
import us.aaronpost.clash.Islands.Building;
import us.aaronpost.clash.Islands.Buildings.BuilderHut.BuilderHut;
import us.aaronpost.clash.Islands.Buildings.GoldMine.GoldMine;
import us.aaronpost.clash.Islands.Buildings.TownHall.TownHall;

import java.lang.reflect.Type;

public class BuildingDeserializer implements JsonDeserializer<Building> {
    @Override
    public Building deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        int myType = json.getAsJsonObject().get("type").getAsInt();
        switch (myType) {
            case 1: return context.deserialize(json, Barracks.class);
            case 2: return context.deserialize(json, ArmyCamp.class);
            case 3: return context.deserialize(json, BuilderHut.class);
            case 4: return context.deserialize(json, GoldMine.class);
            case 5: return context.deserialize(json, TownHall.class);
            default: return null;
        }
    }
}
