package us.aaronpost.clash.Islands.Buildings.BuildingInteraction;

import net.citizensnpcs.npc.ai.speech.Chat;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Fence;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import us.aaronpost.clash.Arenas.Arena;
import us.aaronpost.clash.Arenas.Arenas;
import us.aaronpost.clash.Clash;
import us.aaronpost.clash.Islands.Building;
import us.aaronpost.clash.Islands.GUIS.IslandMenu;
import us.aaronpost.clash.Islands.Island;
import us.aaronpost.clash.PersistentData.Sessions;
import us.aaronpost.clash.Session;
import us.aaronpost.clash.Troops.BHelper;

import java.util.List;

public class Interaction implements Listener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        if(Arenas.a.findPlayerArena(e.getPlayer()) != null) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void inventoryItemClick(InventoryClickEvent e) {
        if(Arenas.a.findPlayerArena((Player) e.getWhoClicked()) != null) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemClick(PlayerInteractEvent i) {
        Arena playerArena = Arenas.a.findPlayerArena(i.getPlayer());
        if(playerArena != null) {
            i.setCancelled(true);
            EquipmentSlot slot = i.getHand(); //Get the hand of the event and set it to 'e'.
            Player player = i.getPlayer();
            if (slot.equals(EquipmentSlot.HAND)) { // checks for main hand to prevent from running twice
                ItemStack item = i.getPlayer().getInventory().getItemInMainHand();
                if (item.hasItemMeta()) {
                    ItemMeta meta = item.getItemMeta();
                    if (meta.hasDisplayName()) {
                        String displayName = meta.getDisplayName();
                        if (displayName.equals(ChatColor.GOLD + "Shop")) {
                            Clash.getPlugin().getServer().getPluginManager().registerEvents(
                                    new IslandMenu(i.getPlayer()), Clash.getPlugin());
                        }
                        else if (displayName.equals(ChatColor.LIGHT_PURPLE + "Return to Spawn")) {
                            i.setCancelled(true);
                            if (i.getItem() != null) {
                                player.getInventory().remove(i.getItem());
                            }
                            player.performCommand("island");
                        } else if(displayName.equals(ChatColor.RED + "Pick Building Up")) {
                            Building building = playerArena.findBuildingAtLocation(
                                    player.getTargetBlockExact(500).getLocation());
                            // This handles finding if there is a building, but also not interacting with the building
                            // because "building" will be null if it is in your hand already!
                            if(building != null) {
                                Island island = playerArena.getIsland();
                                BuildingInHand buildingInHand = island.getBuildingInHand();
                                if(buildingInHand == null) {
                                    BuildingInHand newBuildingInHand = new BuildingInHand(building, false);
                                    island.getBuildings().remove(building);
                                    island.putBuildingInHand(newBuildingInHand);
                                    building.resetToGrass(playerArena);
                                    player.getInventory().addItem(building.getItemStack());
                                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                                            TextComponent.fromLegacyText(ChatColor.GRAY + "Picked up "
                                                    + building.getItemName()));
                                } else {
                                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                                            TextComponent.fromLegacyText(ChatColor.GRAY + " You can only have " +
                                                    ChatColor.RED + "one " + ChatColor.GRAY +
                                                    "building picked up at a time."));
                                }
                            }
                        } else if(displayName.equals(ChatColor.AQUA + "Open Building Menu")) {
                            Building building = playerArena.findBuildingAtLocation(player.getTargetBlockExact
                                    (500).getLocation());
                            if(building != null) {
                                player.sendMessage(ChatColor.GRAY +  "todo menu for " + building.getItemName());
                            }
                        } else if (meta.hasLore()) {
                                List<String> lore = meta.getLore();
                                // Probably don't need this check
                                if (lore.size() > 1) {
                                    Session playerSession = Sessions.s.getSession(player);
                                    if (playerSession != null) {
                                        BuildingInHand buildingInHand = playerSession.getIsland().getBuildingInHand();
                                        if(buildingInHand != null) {
                                            String uuid = ChatColor.stripColor(lore.get(1));
                                            if(buildingInHand.getBuilding().getUUID().toString().equals(uuid)) {
                                                if (playerArena.isValidGridLocation(i.getPlayer().getTargetBlockExact(500).getLocation())) {
                                                    Island island = playerSession.getIsland();
                                                    Location targetBlockLoc = i.getPlayer().getTargetBlockExact(500).getLocation();
                                                    Arena arena = Arenas.a.findPlayerArena(i.getPlayer());
                                                    Building building = buildingInHand.getBuilding();
                                                    boolean fit3x3atTargetBlock = arena.fitSquareAtGridLoc(targetBlockLoc, building.getGridLengthX(), building.getUUID());
                                                    if (fit3x3atTargetBlock) {
                                                        island.deleteAllUUID(building.getUUID());
                                                        player.getInventory().remove(building.getItemStack());
                                                        building.setLocation(targetBlockLoc);
                                                        int x = (int) arena.getRelativeX(targetBlockLoc);
                                                        int z = (int) arena.getRelativeZ(targetBlockLoc);
                                                        building.setRelativeX(x);
                                                        building.setRelativeZ(z);
                                                        building.paste(playerArena);
                                                        island.addBuildingUUIDsToGrid(building);
                                                        if(building.isNewBuilding()) {
                                                            building.changeToAssigned();
                                                        }
                                                        island.addBuilding(building);
                                                        island.removeBuildingInHand();

                                                    } else {
                                                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                                                                TextComponent.fromLegacyText(ChatColor.GRAY +
                                                                        " Sorry, you "+ ChatColor.RED +"cannot "+
                                                                        ChatColor.GRAY + "place that there."));
                                                    }
                                                } else {
                                                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                                                            TextComponent.fromLegacyText(ChatColor.GRAY +
                                                                    " Sorry, you "+ ChatColor.RED +"cannot "+
                                                                    ChatColor.GRAY + "place that there."));
                                                }
                                            }
                                        }
                                    }
                                }

                        }
                    }
                } else {
                    Building building = playerArena.findBuildingAtLocation(player.getTargetBlockExact(500).getLocation());
                    if(building != null) {
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                                TextComponent.fromLegacyText(ChatColor.GRAY +  "Clicked on a " + building.getItemName()));
                    }
                }
            }
        }
    }

    //WILL ONLY CONNECT TO WALLS OF THE SAME TYPE! THE SECOND ARGUMENT NEEDS TO BE UPDATED TO A LIST OF ALL WALL MATERIALS
    public void updateFence(Block b, Material M) {
        Fence f = ((Fence) b.getBlockData());
        Location l = b.getLocation();
        int x = b.getX();
        int z = b.getZ();
        l.setZ(z-1);
        if(!l.getBlock().getType().equals(M)) {
            f.setFace(BlockFace.NORTH,false);
        }
        l.setZ(z+1);
        if(!l.getBlock().getType().equals(M)) {
            f.setFace(BlockFace.SOUTH,false);
        }
        l.setZ(z);
        l.setX(x-1);
        if(!l.getBlock().getType().equals(M)) {
            f.setFace(BlockFace.WEST,false);
        }
        l.setX(x+1);
        if(!l.getBlock().getType().equals(M)) {
            f.setFace(BlockFace.EAST,false);
        }
        l.setX(x);
        b.setBlockData(f);
    }
}
