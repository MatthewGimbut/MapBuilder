package quests;

import characters.EnemyTypes;
import items.Item;
import quests.master.AllOtherQuests;
import quests.master.AllStoryQuests;
import quests.task.GatherTask;
import quests.task.KillTask;
import quests.task.Task;
import quests.trigger.Trigger;

import java.util.LinkedList;
import java.util.List;

/**
 * quests.QuestHandler is the controller for the Quest system. Handles the assignment and
 * completion of quests, completion of individual tasks of the quest, task completion detection,
 * and other vital features to the system.
 * Created by Zim on 9/9/2016.
 */
public class QuestHandler {

    public static List<Quest> activeQuests = new LinkedList<Quest>();
    public static List<Quest> completeQuests = new LinkedList<Quest>();
    public static List<Quest> inActiveQuests = QuestHandler.fillQuests();

    //the quest the user has flagged as priority, always a direct reference to the original obj
    public static Quest priorityQuest = null;

    /**
     * Accept the given quest. Remove it from inActiveQuests, add it to activeQuests, and
     * set isActive to true for the quest.
     * @param q The quest to be accepted
     */
    public static void acceptQuest(Quest q) {
        if(q.isAcceptable()) {
            q.setActive(true);
            inActiveQuests.remove(q);
            activeQuests.add(q);
            System.out.println("Quest Accept Success! *Quest Handler*"); //TODO delete this
            if(priorityQuest == null) {
                setPriorityQuest(q);
            }
            //TODO begin quest popup menu (display name and description of the quest)
        }
        //else nothing
    }

    /**
     * Accept the quest associated with the quest acceptance trigger given. If there is no
     * such quest nothing happens.
     * @param t The quest acceptance trigger
     */
    public static void acceptQuest(Trigger t) {
        inActiveQuests.forEach(q -> {
            if(q.getQuestAcceptanceTrigger() != null && t.equals(q.getQuestAcceptanceTrigger())) {
                acceptQuest(q);
            }
        });
    }

    /**
     * On enemy kill this method is called. If any active quests have a kill task attempt to
     * decrement the task count (if the enemy type is correct). If the task is completed on this
     * kill call complete the task in the quest and check for quest completion.
     * @param e The characters.EnemyTypes of the Enemy killed.
     */
    public static void checkEnemyKilledTask(EnemyTypes e) {
        activeQuests.forEach(q -> {
            Task t = q.getCurrentTask();

            if(t instanceof KillTask) {
                ((KillTask) t).enemyKilled(e);
                if(t.checkTaskComplete()) {
                    q.currentTaskComplete();
                    checkQuestCompletion(q);
                } else {
                    //TODO gui refresh for the task numbers
                }
            }
        });
    }

    /**
     * On item pickup this method is called. If any active quests have a gather task attempt to
     * decrement the task count (if the item type is correct). If the task is completed on this
     * gather call complete the task in the quest and check for quest completion.
     * @param i The Item that was picked up.
     */
    public static void checkItemAcquiredTask(Item i) {
        activeQuests.forEach(q -> {
            Task t = q.getCurrentTask();

            if(t instanceof GatherTask) {
                ((GatherTask) t).itemAcquired(i);
                if(t.checkTaskComplete()) {
                    q.currentTaskComplete();
                    checkQuestCompletion(q);
                } else {
                    //TODO gui refresh for the task numbers
                }
            }
        });
    }

    /**
     * Checks if the activated trigger is associated with any active quests, if it is then
     * complete that task.
     */
    public static void checkForTrigger(Trigger t) {
        for (Quest q: activeQuests) {
            Trigger trigger = q.getCurrentTask().getTrigger();
            if(trigger != null && t.equals(trigger)) {
                System.out.println("Trigger Check Successful! *Quest Handler*"); //TODO delete this
                q.currentTaskComplete();
                checkQuestCompletion(q);
            }
        }
    }

    /**
     * Check the completion of the quest, if it is complete display complete quest gui.
     * If the quest was the priority quest then have the next priority quest be the oldest
     * non completed story quest.
     *
     * This method is only called after completing a current task on a Quest, so if the quest
     * is not complete then refresh the GUI to display the new task.
     * @param q
     */
    private static void checkQuestCompletion(Quest q) {
        if(q.getActive() && q.getComplete()) {
            System.out.println("Quest Complete Success! *Quest Handler*"); //TODO delete this
            //GameStage.gamePane.displayQuestSuccessPane(q); //gives out rewards for quest completion
            q.setActive(false);
            activeQuests.remove(q);
            completeQuests.add(q);
            if(q.getPriority()) {
                priorityQuest = nextActiveStoryQuest();
            }
            q.setPriority(false);
        } else {
            //TODO task complete animation and fade into next task
        }
    }

    /**
     * Check is a quest with the given Acceptance Trigger has been completed already.
     */
    public static boolean checkQuestCompletion(Trigger t) {
        for(Quest q: completeQuests) {
            if(q.getQuestAcceptanceTrigger().equals(t)) {
                return true;
            }
        }
        return false;
    }

    /**
     * A Quest is acceptable if the given trigger is the accept trigger, the quest is
     * not currently active, and all prerequisites have been met.
     * @param t The trigger to test
     * @return If the quest is acceptable (correct trigger & not currently active)
     */
    public static boolean isAcceptable(Trigger t) {
        for(Quest quest: inActiveQuests) {
            if(t.equals(quest.getQuestAcceptanceTrigger()) &&
                    !quest.getActive()) {
                return quest.isAcceptable();
            }
        }
        return false;
    }

    /**
     * Check if the given quest activation trigger is associated with an active quest.
     */
    public static boolean isActive(Trigger t) {
        for(Quest quest: inActiveQuests) {
            if(t.equals(quest.getQuestAcceptanceTrigger())) {
                return quest.getActive();
            }
        }
        return false;
    }

    /**
     * Helper method to get the next active story quest. If a story quest is not currently
     * active then the next inactive story quest will be assigned. If there are no more story
     * quests then the top active quest will be assigned. If no quests are available then
     * null is returned.
     */
    private static Quest nextActiveStoryQuest() {
        Quest next = null;
        for(Quest q: activeQuests) {
            if(q.isStory()) { //if the quest is a story quest set it to the priority quest
                next = q;
                return next;
            }
        }
        //no story quests active, grab the next one from inactive quests
        for(Quest q: inActiveQuests) {
            if(q.isStory()) {
                next = q;
                acceptQuest(q);
                return next;
            }
        }

        if(activeQuests.size() != 0) {
            return activeQuests.get(0); //no story quests at all, grab top quest
        }
        return null; //no active quests at all
    }

    /**
     * Set the Priority Quest (quest to have progress displayed on screen). Only one quest
     * can be a priority quest at a time.
     * @param q The new Priority Quest
     */
    public static void setPriorityQuest(Quest q) {
        if(priorityQuest != null) { //priorityQuest.setPriority(false) would probably also work
            activeQuests.forEach(quest -> { //if there is efficiency problems try it
                quest.setPriority(false);
            });
        }
        q.setPriority(true);
        priorityQuest = q;
        //TODO gui refresh to display the new priority quest (if not done automatically)
    }

    /**
     * Method which generates all of the quests in the game to fill the inActiveQuests List.
     * @return A LinkedList of all the quests in the game
     */
    public static List<Quest> fillQuests() {
        List<Quest> result = new LinkedList<Quest>();
        result.addAll(AllStoryQuests.initialize());
        result.addAll(AllOtherQuests.initialize());
        System.out.println("Fill Quests Successful! *Quest Handler*");

        return result;
    }
}
