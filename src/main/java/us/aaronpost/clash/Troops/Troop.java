package us.aaronpost.clash.Troops;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.npc.CitizensNPC;
import net.citizensnpcs.npc.CitizensNPCRegistry;
import net.citizensnpcs.trait.SkinTrait;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
public class Troop {
    private int health, level, trainingTime, id;
    // skin[0] - url    skin[1] - signature     skin[2] - data
    private String[] skin = {"", "", "", ""};
    private String title;
    // true when training time has expired
    private boolean trained;
    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getHealth() {
        return health;
    }

    public String getTitle() {
        return title;
    }
    public Troop(){
        trained = false;
    }
    public int getLevel() {

        return this.level;
    }
    public void createTroop(Troop t) {
        NPCRegistry reg = CitizensAPI.getNPCRegistry();
        // Need to add functionality  for non-player entities
        NPC npc = getNPC();
        npc = reg.createNPC(EntityType.PLAYER, this.title);
        SkinTrait skin = new SkinTrait();
        // Need to add functionality for adding equipment
        // npc.getOrAddTrait(Equipment.class).set(Equipment.EquipmentSlot.HAND, new ItemStack(Material.IRON_SWORD, 1));
        npc.getOrAddTrait(SkinTrait.class).setSkinPersistent(this.skin[3], this.skin[1], this.skin[2]);
        this.id = npc.getId();
    }
    public void spawnTroop(Location loc) {
        getNPC().spawn(loc);
    }
    public void teleport(Location loc) {
        //NPC npc = reg.get
    }

    public void setTrainingTime(int trainingTime) {
        this.trainingTime = trainingTime;
    }

    public void setTrained(boolean trained) {
        this.trained = trained;
    }

    public int getTrainingTime() {
        return trainingTime;
    }



    public String getSkin(int index) {
        return skin[index];
    }

    /**
     * Changes one of the three modifiers for the skin
     * @param data
     * @param index 0 - url    1 - signature     2 - data
     */
    public void setSkinData(String data, int index) {
        this.skin[index] = data;
    }

    /**
     * Returns the abs value of the difference betweenm the new health and the old health, and updates health
     * @param health
     * @return |new health - old health|
     * @updates this.health
     */
    public int setHealth(int health) {
        int difference = Math.abs(health - this.health);
        this.health = health;
        return difference;
    }
    public void setLevel(int level) {
        this.level = level;
    }

    public NPC getNPC() {
        NPCRegistry reg = CitizensAPI.getNPCRegistry();
        return reg.getById(id);
    }
}
