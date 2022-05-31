package us.aaronpost.clash.Buildings;

import java.io.Serializable;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class Barracks extends Building {
    /**
     * This probably doesn't go here lol.
     * @param d1
     * @param d2
     * @return difference between two dates in seconds.
     */
    public Barracks(int level) {
        super.setType(1);
        super.setLevel(level);
    }
    public long getDifferenceBetweenDates(Date d1, Date d2) {
        return ChronoUnit.SECONDS.between(d1.toInstant(), d2.toInstant());
    }
    //public Troop train(String ) {}
}
