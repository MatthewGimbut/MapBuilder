package quests.task;

import quests.trigger.Trigger;

/**
 * quests.quests.task.Task.InteractTask is a subclass of quests.task.Task and is a Task based on interacting with an NPC
 * who shares the same Trigger. An Interact Task is always "complete" since its only requirement
 * is interacting with an NPC who holds the same trigger. (And it will only check completion if
 * the NPC you are interacting with has the same trigger).
 * @author Sean Zimmerman
 */
public class InteractTask extends Task {

    public InteractTask(String description, Trigger trigger) {
        this.setComplete(true);
        this.description = description;
        this.trigger = trigger;
    }

    public boolean checkTaskComplete() {
        return this.isComplete();
    }
}
