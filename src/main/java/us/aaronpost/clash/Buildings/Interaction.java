package us.aaronpost.clash.Buildings;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Fence;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import us.aaronpost.clash.GUIs.BarracksMenu;
import us.aaronpost.clash.PersistentData.Sessions;
import us.aaronpost.clash.Session;

public class Interaction implements Listener {
    @EventHandler
    public void onRightClick(PlayerInteractEvent i) {
        EquipmentSlot slot = i.getHand(); //Get the hand of the event and set it to 'e'.
        if (slot.equals(EquipmentSlot.HAND)) { // checks for main hand to prevent from running twice
            if (i.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (i.getClickedBlock().getType().equals(Material.RED_CONCRETE)) {
                    Session s = Sessions.s.getSession(i.getPlayer());
                    //Implement logic for detecting buildings, not just a block for barracks lol
                    if (!(s.equals(null))) {
                        for (int e = 0; e < s.getIsland().getBuildings().size(); e++) {
                            //Building b = s.getIsland().getBuildings().get(e) Building;
                            if (s.getIsland().getBuildings().get(e).getLoc().equals(i.getClickedBlock().getLocation())) {
                                BarracksMenu menu = new BarracksMenu(s.getIsland().getBuildings().get(e));
                                menu.newInventory(i.getPlayer());
                                break;
                            }
                        }
                    }
                } else if (i.getClickedBlock().getType().equals(Material.CAMPFIRE)) {
                    Session s = Sessions.s.getSession(i.getPlayer());
                    //Implement logic for detecting buildings, not just a block for barracks lol
                    if (!(s.equals(null))) {
                        for (int e = 0; e < s.getIsland().getBuildings().size(); e++) {
                            if (s.getIsland().getBuildings().get(e).getLoc().equals(i.getClickedBlock().getLocation())) {
                                ArmyCamp a = (ArmyCamp) s.getIsland().getBuildings().get(e);
                                i.getPlayer().sendMessage("Campfire " + a.getInt());
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent b) {
        if (b.getItemInHand().getType().equals(Material.RED_CONCRETE) && b.getItemInHand().getItemMeta().getDisplayName().equals("Barracks")) {
            b.getPlayer().sendMessage("Placed barracks.");
            Barracks barracks = new Barracks(1);
            barracks.setLocation(b.getBlock().getLocation());
            Sessions.s.getSession(b.getPlayer()).getIsland().addBuilding(barracks);
        } else if(b.getItemInHand().getType().equals(Material.CAMPFIRE) && b.getItemInHand().getItemMeta().getDisplayName().equals("Army Camp")) {
            b.getPlayer().sendMessage("Placed army camp.");
            ArmyCamp camp = new ArmyCamp(1);
            camp.setLocation(b.getBlock().getLocation());
            Sessions.s.getSession(b.getPlayer()).getIsland().addBuilding(camp);
        } if(b.getItemInHand().getType().equals(Material.OAK_FENCE) && b.getItemInHand().getItemMeta().getDisplayName().equals("Wall")) {
            Location l = b.getBlockPlaced().getLocation();
            l.setY(l.getY() + 1);
            l.getBlock().setType(Material.OAK_FENCE);
            updateFence(b.getBlockPlaced(), Material.OAK_FENCE);
            updateFence(l.getBlock(), Material.OAK_FENCE);
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
