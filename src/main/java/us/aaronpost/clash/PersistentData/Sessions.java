package us.aaronpost.clash.PersistentData;

import org.bukkit.entity.Player;
import us.aaronpost.clash.Session;

import java.util.ArrayList;
import java.util.List;

public class Sessions {
    private List<Session> sessions = new ArrayList<>();
    public static Sessions s = new Sessions();

    public void setSessions(ArrayList<Session> sessions) {
        this.sessions = sessions;
    }

    public Session getSession(Player p) {
        for(Session s: sessions) {
            if(s.getPlayer().equals(p)) {
                return s;
            }
        }
        return null;
    }
    public List<Session> getSessions() {
        return sessions;
    }
}
