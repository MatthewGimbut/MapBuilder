package quests.task;

import items.Item;
import quests.trigger.Trigger;

/**
 * quests.quests.task.Task.GatherTask is a subclass of quests.task.Task and is a Task based on collecting a certain
 * number of a specified Item.
 * @author Sean Zimmerman
 */
public class GatherTask extends Task {

    private Item item; //MAKE SURE QUEST ITEMS ARE NON DROPABLE PLEASE FOR THE LOVE OF GOD
    private int targetNum;

    public GatherTask(String description, Trigger trigger, Item item, int targetNum) {
        this.description = description;
        this.trigger = trigger;
        this.item = item;
        this.targetNum = targetNum;
    }

    /**
     * If the item acquired is the same type as the task requires the number left to get it decremented.
     * This method will only be called from quests.QuestHandler.
     * @return The total number of items left to get.
     */
    public int itemAcquired(Item i) {
        if(item.equals(i)) {
            targetNum--;
        }
        return targetNum;
    }

    /**
     * @return If the Task has been completed or not
     */
    public boolean checkTaskComplete() {
        if(targetNum == 0) {
            setComplete(true);
            return true;
        }
        return false;
    }
}
