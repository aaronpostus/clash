package us.aaronpost.clash;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.api.trait.trait.Equipment;
import net.citizensnpcs.trait.SkinTrait;
import net.citizensnpcs.util.PlayerAnimation;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import us.aaronpost.clash.Buildings.Interaction;
import us.aaronpost.clash.PersistentData.Serializer;
import us.aaronpost.clash.PersistentData.Sessions;
import us.aaronpost.clash.Schematics.Controller;
import us.aaronpost.clash.Schematics.Schematics;
import us.aaronpost.clash.Troops.BHelper;
import us.aaronpost.clash.Troops.Barbarian;
import us.aaronpost.clash.Troops.BarracksQueue;
import us.aaronpost.clash.Troops.Troop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public final class Clash extends JavaPlugin {
    private static Clash plugin;
    private static Serializer s;


    public static Clash getPlugin() {
        return plugin;
    }
    @Override
    public void onEnable() {
        plugin = this;
        getServer().getPluginManager().registerEvents(new EventsClass(), this);
        s = new Serializer();
        getServer().getPluginManager().registerEvents(s, this);
        getServer().getPluginManager().registerEvents(new Interaction(), this);
        getServer().getPluginManager().registerEvents(new Controller(), this);

        Player[] list = new Player[getServer().getOnlinePlayers().size()];
        getServer().getOnlinePlayers().toArray(list);

        for(Player p: list) {
            try {
                Session session = s.deserializeSession(p);
                if(session != null) {
                    session.setD(System.currentTimeMillis());
                    Clash.getPlugin().getLogger().info(p.getName() + "'s session has been loaded!");
                    Sessions.s.getSessions().add(session);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        getLogger().info("Successfully enabled!");
    }

    @Override
    public void onDisable() {
        Player[] list = new Player[Bukkit.getOnlinePlayers().size()];
        Bukkit.getOnlinePlayers().toArray(list);
        for(Player p: list) {
            try {
                Session session = Sessions.s.getSession(p);
                if(session != null) {
                    s.serializeSession(p, session);
                }
                Clash.getPlugin().getLogger().info(p.getName() + "'s session has been saved!");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        getLogger().info("Successfully disabled.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, @NotNull String label, String[] args) {
        Player player = (Player) sender;
        if (label.equals("troop")) {
            if(args.length < 1) {
                NPCRegistry reg = CitizensAPI.getNPCRegistry();
                NPC npc = reg.createNPC(EntityType.PLAYER, BHelper.BARBARIAN_TITLE);
                Location loc = player.getLocation();
                SkinTrait skin = new SkinTrait();
                npc.getOrAddTrait(Equipment.class).set(Equipment.EquipmentSlot.HAND, new ItemStack(Material.IRON_SWORD, 1));

                npc.getOrAddTrait(SkinTrait.class).setSkinPersistent("skin20d13f5d",
                        "IKYQYgpJz7qL+6rg2+U+L1fByImCVvRABaLFo6iwgj9HAxXeIwTt0ESIcwYft9zQEd1xmtlpRpe7A5Unk+gM1jLzJqpz/bYbDBZYnL4062cAOPyO4VAZjyakVcPRaVCdenoSEfTHlOWoczSS+JD4zSQZP24FrgYK5N/+BKRbs5cKvrGtUoyAZAIyXFj8khoMaZ4Ydw1x3BwDJAc/gvrlbmTiZNrZDEXUER1YcNE2wfvWHUjPbghru95RN+6WVnUWyKn+lmVvs/PaK7qkr3GfiueK+9sVLQg+xH34oE0MGAljmKjMj+/TLqODf6Qy+IUeN1Xx4mfywuMHsFE/K1O5dC1S5q/7JmJHhgIHpsUzU2hk9PXJRyCW2Hw1MJDI8inSvjR3r2Fz7Jt71Mgx94u1xH2ur3W8cF36SWDa5LNYfsoV47m+GAWBLvxXwJeycJag/PuzmEticzGlmDMeIqC1oRMaUksFUKy68MqFoet20HVTlfwIRcIk5sqon17IX+pJUJtmcXGeN3WLvS5rN2q2Wz0QMU+7z6nAkrKfh/F8HYe8ulkrrjyD5htnPHZDQY3MxSq6RhRcLO+nkoZhOqkjB8rB3L7fcHPfjxWJiCISwFwWdqvQpIpor+92oFeBwE8sruDN/l0fsQeC0RZSmAtNlsh8oxJtyBtG9KHTKt4/cXI=",
                        "ewogICJ0aW1lc3RhbXAiIDogMTY1MjQ5MDc3MTk1OSwKICAicHJvZmlsZUlkIiA6ICIzOTVkZTJlYjVjNjU0ZmRkOWQ2NDAwY2JhNmNmNjFhNyIsCiAgInByb2ZpbGVOYW1lIiA6ICJzcGFyZXN0ZXZlIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2QzMjk4NTIwMDAxMGFhYTA0NjVmNjY5MmZhYzYxOWY1YmFmZTY4YjYyMjcwZjI0M2YxZmI2YTMzMzNjYzQwZmUiCiAgICB9CiAgfQp9");
                npc.spawn(loc);

                return true;
            } else {
                if(args[0].equals("move")) {
                    NPCRegistry reg = CitizensAPI.getNPCRegistry();
                    NPC npc = reg.getById(Integer.parseInt(args[1]));
                    Location loc = player.getLocation();
                    npc.faceLocation(loc);
                    npc.getNavigator().setTarget(loc);
                    return true;
                } else if(args[0].equals("hurt")) {
                    NPCRegistry reg = CitizensAPI.getNPCRegistry();
                    NPC npc = reg.getById(Integer.parseInt(args[1]));
                    //npc.getEntity().playEffect(EntityEffect.HURT);d
                    PlayerAnimation.SIT.play((Player) npc.getEntity());
                    ((Player) sender).spawnParticle(Particle.SMOKE_NORMAL, npc.getEntity().getLocation(), 5);
                    ((Player) sender).spawnParticle(Particle.EXPLOSION_NORMAL, npc.getEntity().getLocation(), 5);
                    new BukkitRunnable() {
                        public void run() { PlayerAnimation.STOP_SITTING.play((Player) npc.getEntity()); }
                    }.runTaskLater(getServer().getPluginManager().getPlugin("Clash"), 3);
                    new BukkitRunnable() {
                        public void run() {
                            PlayerAnimation.SLEEP.play(((Player) npc.getEntity())); }
                    }.runTaskLater(getServer().getPluginManager().getPlugin("Clash"), 7);
                    new BukkitRunnable() {
                        public void run() { PlayerAnimation.STOP_SLEEPING.play(((Player) npc.getEntity())); }
                    }.runTaskLater(getServer().getPluginManager().getPlugin("Clash"), 80);
                    return true;
                } else if(args[0].equals("attack")) {
                    NPCRegistry reg = CitizensAPI.getNPCRegistry();
                    NPC npc = reg.getById(Integer.parseInt(args[1]));
                    NPC aStand = reg.createNPC(EntityType.ARMOR_STAND, "");
                    Location loc = npc.getEntity().getLocation();
                    loc.setX(loc.getX() + 3);
                    aStand.spawn(loc);
                    npc.getNavigator().setTarget(aStand.getEntity(), true);
                    npc.faceLocation(aStand.getEntity().getLocation());
                    //npc.getEntity().playEffect(EntityEffect.);
                    return true;
                } else if(args[0].equals("create")) {
                    Troop t = new Barbarian(1);
//                    switch(args[1]) {
//                        case "barbarian":
//                            t = new Barbarian(1);
//                            break;
//                    }
                    ArrayList<Troop> list = new ArrayList<>();
                    list.add(t);
                    BarracksQueue q = new BarracksQueue(list, new Date());
                    q.getQueue().get(0).spawnTroop(player.getLocation());
                    return true;
                }
                else if(args[0].equals("debugtools")) {

                    ItemStack stack = new ItemStack(Material.RED_CONCRETE);
                    ItemMeta meta = stack.getItemMeta();
                    meta.setDisplayName("Barracks");
                    stack.setItemMeta(meta);
                    player.getInventory().addItem(stack);

                    stack = new ItemStack(Material.CAMPFIRE);
                    meta = stack.getItemMeta();
                    meta.setDisplayName("Army Camp");
                    stack.setItemMeta(meta);
                    player.getInventory().addItem(stack);

                    stack = new ItemStack(Material.OAK_FENCE);
                    meta = stack.getItemMeta();
                    meta.setDisplayName("Wall");
                    stack.setItemMeta(meta);
                    player.getInventory().addItem(stack);

                    stack = new ItemStack(Material.BLAZE_ROD);
                    meta = stack.getItemMeta();
                    meta.setDisplayName(ChatColor.BLUE + "Schematic Wand");
                    ArrayList<String> lore = new ArrayList<>(Arrays.asList(
                            ChatColor.YELLOW + "x: ",
                            ChatColor.YELLOW + "y: ",
                            ChatColor.YELLOW + "z: ",
                            ChatColor.YELLOW + "x: ",
                            ChatColor.YELLOW + "y: ",
                            ChatColor.YELLOW + "z: ")
                    );
                    meta.setLore(lore);
                    stack.setItemMeta(meta);
                    player.getInventory().addItem(stack);

                    return true;
                } else if(args[0].equals("paste")) {
                    if(Schematics.s.getSchematics().size() != 0) {
                        Schematics.s.getSchematics().get(Integer.parseInt(args[1])).pasteSchematic(player.getLocation());
                    } else {
                        player.sendMessage("There are no schematics made.");
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
