package us.aaronpost.clash.Islands.Buildings.Barracks;

import us.aaronpost.clash.Troops.Troop;

import java.util.ArrayList;
import java.util.Date;

public class BarracksQueue {
    public ArrayList<Troop> queue;
    public Date date;

    /**
     * Create a new BarracksQueue
     * @param queue - the set of troops you would like to train. FIFO
     * @param date - this is the starting time that this class will reference when performing time calculations
     */
    public BarracksQueue(ArrayList<Troop> queue, Date date) {
        this.queue = queue;
        this.date = date;
    }

    public ArrayList<Troop> getQueue() {
        return queue;
    }
    public void setQueue(ArrayList<Troop> queue) {
        this.queue = queue;
    }
    public void addToQueue(Troop troop) {
        queue.add(troop);
    }
    public void addToQueue(ArrayList<Troop> t) {
        queue.addAll(t);
    }
    public Troop dequeue() {
        return queue.remove(0);
    }
}
