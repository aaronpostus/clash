package us.aaronpost.clash.File;

import org.bukkit.entity.Player;

public class PlayerFileController {
    private String filePath;
    public PlayerFileController(Player player) {
        this.filePath = "todo player filepath" + player.getUniqueId();
    }
    private static void saveToFile() {

    }
}
