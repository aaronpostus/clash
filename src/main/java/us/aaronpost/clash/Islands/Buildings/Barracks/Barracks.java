package us.aaronpost.clash.Islands.Buildings.Barracks;

import us.aaronpost.clash.Islands.Building;
import us.aaronpost.clash.Troops.BHelper;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.TimerTask;

public class Barracks extends Building {
    public static String[] SCHEMATICS = { "barrackslvl1", "fillerschematic", "fillerschematic", "fillerschematic", "fillerschematic", "fillerschematic", "fillerschematic", "fillerschematic", "fillerschematic", "fillerschematic", "fillerschematic", "fillerschematic", "fillerschematic", "fillerschematic", "fillerschematic"};
    public static int[] COST = { 100, 500, 2500, 5000, 10000, 75000, 200000, 600000, 900000, 1200000, 1800000, 2500000, 4000000, 5000000, 6000000};
    //public static

    public Barracks(int level, int x, int z) {
        super(x, z, SCHEMATICS[level - 1]);
        super.setType(1);
        super.setLevel(level);
    }
    public void test() {
        super.addTask(new TimerTask() {
            @Override
            public void run() {

            }
        });
    }
    //public Troop train(String ) {}
}
