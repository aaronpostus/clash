package us.aaronpost.clash.GUIs;

import org.bukkit.inventory.ItemStack;

public class DynamicItem {
    private ItemStack[] states;
    private int currentState;

    /**
     *
     * @param states all item stacks in order
     * @param startingState the index of the starting state
     */
    public DynamicItem(ItemStack[] states, int startingState){
        this.states = states;
        currentState = startingState;
    }

    public ItemStack cycleForwards() {
        currentState++;
        if(currentState >= states.length) {
            currentState = 0;
        }
        return states[currentState];
    }
    public ItemStack cycleBackwards() {
        currentState--;
        if(currentState < 0) {
            currentState = states.length - 1;
        }
        return states[currentState];
    }
}
