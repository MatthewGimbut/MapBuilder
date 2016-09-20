package quests;

import items.Item;
import quests.task.Task;
import quests.trigger.Trigger;

import java.util.List;

/**
 * quests.Quest represents a Quest. A Quest contains a list of tasks that the player must
 * complete to finish the quest.
 * @author Sean Zimmerman
 */
public class Quest {

    private boolean isAcceptable = false;
    private boolean isActive = false;
    private boolean isComplete = false; //a quest must be complete and active to be turned in
    //only one quest can be priority at a time, the priority quest is displayed on screen
    private boolean isPriority = false;
    private boolean isStory;

    private int currentTaskIndex = 0;
    private int exp;
    private int money;

    private List<Item> reward;
    private List<Quest> prerequisites;
    private List<Task> allTasks;
    private List<Task> tasks;

    private String questName;
    private String description;

    private Task currentTask;

    private Trigger questAcceptanceTrigger; //a quest MUST have an acceptance trigger

    public Quest(boolean isStory, int exp, int money, List<Item> reward,
                 List<Quest> prerequisites, List<Task> tasks,
                 String questName, String description, Trigger questAcceptanceTrigger) {
        this.isStory = isStory;
        this.exp = exp;
        this.money = money;
        this.reward = reward;
        this.prerequisites = prerequisites;
        this.tasks = tasks;
        this.questName = questName;
        this.description = description;
        this.questAcceptanceTrigger = questAcceptanceTrigger;
        this.allTasks = tasks;

        currentTask = tasks.get(0); //the current task defaults to the first in the list
    }

    public void currentTaskComplete() {
        currentTask.setComplete(true);
        if(currentTaskIndex == tasks.size() - 1) { //the last task has been completed
            this.setComplete(true);
        } else { //move onto the next task
            currentTaskIndex++;
            currentTask = tasks.get(currentTaskIndex);
            //TODO REMOVE THE QUEST TRIGGER FROM THE MAP TEXT FILE (this will be difficult probably)
            //probably actually don't need to worry about the above one
            //TODO some sort of display for the next task of the quest
        }
    }

    /**
     * If a prerequisite quest has not been completed then this quest is not able
     * to be accepted.
     */
    public void updateAcceptable() {
        boolean acceptable = true;

        List<Quest> completed = QuestHandler.completeQuests;
        for(Quest q: prerequisites) {
            if(!completed.contains(q)) {
                acceptable = false;
                break;
            }
        }

        isAcceptable = acceptable;
    }

    public String getQuestName() {
        return this.questName;
    }

    public String getDescription() {
        return this.description;
    }

    public List<Item> getReward() {
        return this.reward;
    }

    public List<Quest> getPrerequisites() { return this.prerequisites; }

    public int getExpReward() {
        return this.exp;
    }

    public int getMoneyReward() {
        return this.money;
    }

    public Task getCurrentTask() {
        return currentTask;
    }

    public List<Task> getAllTasks() { return this.allTasks; }

    public boolean isAcceptable() {
        updateAcceptable();
        return this.isAcceptable;
    }

    public boolean getActive() { return this.isActive; }

    public boolean getComplete() { return this.isComplete; }

    public boolean getPriority() { return this.isPriority; }

    public boolean isStory() { return this.isStory; }

    public Trigger getQuestAcceptanceTrigger() { return this.questAcceptanceTrigger; }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    public void setComplete(boolean complete) {
        this.isComplete = complete;
    }

    public void setPriority(boolean priority) {
        this.isPriority = priority;
    }

}
