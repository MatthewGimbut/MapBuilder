package quests.master;

import items.Item;
import quests.Quest;
import quests.task.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * A class which holds all static references to all story quests.
 * @author Sean Zimmerman
 */
public class AllStoryQuests {

    public static Quest quest001;
    public static Quest quest002;

    public static List<Quest> initialize() {
        List<Quest> result = new ArrayList<Quest>();

        ArrayList<Task> quest001_tasks = new ArrayList<Task>();
        quest001_tasks.add(AllStoryTasks.task001_01);
        quest001_tasks.add(AllStoryTasks.task001_02);

        List<Item> quest001_items = Item.generateRandomItem(2);

        quest001 = new Quest(true, 10, 10, quest001_items,
                new ArrayList<Quest>(), quest001_tasks, "Test Quest 1",
                "This is a placeholder quest.", AllStoryQuestAcceptanceTriggers.trigger001);
        result.add(quest001);

        ArrayList<Task> quest002_tasks = new ArrayList<Task>();
        quest002_tasks.add(AllStoryTasks.task002_01);

        ArrayList<Quest> quest002_prereq = new ArrayList<Quest>();
        quest002_prereq.add(quest001);

        quest002 = new Quest(true, 15, 15, new ArrayList<Item>(),
                quest002_prereq, quest002_tasks, "Test Quest 2",
                "This is another placeholder quest.", AllStoryQuestAcceptanceTriggers.trigger002);
        result.add(quest002);

        return result;
    }
}
