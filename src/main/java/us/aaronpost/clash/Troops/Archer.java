package us.aaronpost.clash.Troops;

public class Archer extends Troop {

    public Archer(int level){
        super();
        super.setType(2);
        super.setLevel(level);
        super.setTitle(BHelper.ARCHER_TITLE);

        // All skin info
        super.setSkinData(BHelper.ARCHER_URL[level - 1], 0);
        super.setSkinData(BHelper.ARCHER_SIGNATURE[level - 1], 1);
        super.setSkinData(BHelper.ARCHER_VALUE[level - 1], 2);
        super.setSkinData(BHelper.ARCHER_USERNAME[level - 1], 3);

        super.createTroop(this);
    }
}
