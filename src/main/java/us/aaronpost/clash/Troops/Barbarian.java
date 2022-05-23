package us.aaronpost.clash.Troops;

public class Barbarian extends Troop {

    public Barbarian(int level){
        super();
        super.setLevel(level);
        super.setTitle(BHelper.BARBARIAN_TITLE);

        // All skin info
        super.setSkinData(BHelper.BARBARIAN_URL[level - 1], 0);
        super.setSkinData(BHelper.BARBARIAN_SIGNATURE[level - 1], 1);
        super.setSkinData(BHelper.BARBARIAN_VALUE[level - 1], 2);
        super.setSkinData(BHelper.BARBARIAN_USERNAME[level - 1], 3);

        super.createTroop(this);
    }
}
