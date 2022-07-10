package us.aaronpost.clash.PersistentData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import us.aaronpost.clash.Islands.Building;
import us.aaronpost.clash.Clash;
import us.aaronpost.clash.Islands.Buildings.Serialization.BuildingDeserializer;
import us.aaronpost.clash.Schematics.Schematic;
import us.aaronpost.clash.Schematics.Schematics;
import us.aaronpost.clash.Session;
import us.aaronpost.clash.Troops.Troop;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Serializer implements Listener {

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent p) {
        Session c = Sessions.s.getSession(p.getPlayer());
        if(c!=null) {
            c.setD(System.currentTimeMillis());
            try {
                serializeSession(p.getPlayer(), c);
                Clash.getPlugin().getLogger().info(p.getPlayer().getName() + "'s session has been saved!");
            } catch (IOException e) {
                e.printStackTrace();
            }
            Sessions.s.getSessions().remove(c);
        }
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent p) {
        // Load their files up!m, .
        Player player = p.getPlayer();
        File file = new File(Clash.getPlugin().getDataFolder().getAbsolutePath() + "/Sessions/" + player.getUniqueId() + ".json");
        // Player already has data, deserialize
        if(file.exists()) {
            try {
                Sessions.s.getSessions().add(deserializeSession(player));
                Clash.getPlugin().getLogger().info(player.getName() + "'s session has been loaded!");
            } catch (IOException e) {
                // This shouldn't happen because we already have a check for it.
                e.printStackTrace();
            }
        }
        // Player does NOT already have data, so create.
        else {
            Clash.getPlugin().getLogger().info(player.getName() + " is new! Their session data has been instantiated.");
            Sessions.s.getSessions().add(new Session(player));

        }
    }
    public void serializeSchematics() throws IOException {
        List<Schematic> schematics = Schematics.s.getSchematics();
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson g = builder.create();
        File file;
        for(int i = 0; i < schematics.size(); i++) {
            file = new File(Clash.getPlugin().getDataFolder().getAbsolutePath() + "/Schematics/" + schematics.get(i).getName() + ".json");
            file.getParentFile().mkdir();
            file.createNewFile();
            Writer w = new FileWriter(file, false);
            g.toJson(schematics.get(i), w);
            w.flush();
            w.close();
        }

    }

    public List<Schematic> deserializeSchematics() throws IOException {
        File[] schematicsFilePath = new File(Clash.getPlugin().getDataFolder().getAbsolutePath() + "/Schematics/").listFiles();
        List<Schematic> schematics = new ArrayList<>();
        int numberOfLoadedSchematics = 0;
        for(File file: schematicsFilePath) {
            if (file.exists()) {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                Reader reader = new FileReader(file);
                Schematic s = gson.fromJson(reader, Schematic.class);
                schematics.add(s);
                reader.close();
                numberOfLoadedSchematics++;
            }
            else {
                Clash.getPlugin().getLogger().info("Something is horribly wrong with the file system. Fix this.");
                return null;
            }
        }
        Clash.getPlugin().getLogger().info("Loaded " + numberOfLoadedSchematics +" schematics.");
        return schematics;
    }
    public void serializeSession(Player p, Session c) throws IOException {
        Session s = Sessions.s.getSession(p.getPlayer());
        GsonBuilder builder = new GsonBuilder();
//        builder.registerTypeAdapter(Building.class, new BuildingSerializer());
//        builder.registerTypeAdapter(Troop.class, new TroopSerializer());
        builder.setPrettyPrinting();
        Gson g = builder.create();
        File file = new File(Clash.getPlugin().getDataFolder().getAbsolutePath() + "/Sessions/" + p.getUniqueId() + ".json");
        file.getParentFile().mkdir();
        file.createNewFile();
        Writer w = new FileWriter(file, false);
        g.toJson(s, w);
        w.flush();
        w.close();
    }
    public Session deserializeSession(Player p) throws IOException {
        File file = new File(Clash.getPlugin().getDataFolder().getAbsolutePath() + "/Sessions/" + p.getUniqueId() + ".json");
        if (file.exists()){
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Building.class, new BuildingDeserializer());
            builder.registerTypeAdapter(Troop.class, new TroopDeserializer());
            builder.setPrettyPrinting();
            Gson gson = builder.create();
            Reader reader = new FileReader(file);
            Session s = gson.fromJson(reader, Session.class);
            reader.close();
            return s;
        }
        return null;
    }
}
