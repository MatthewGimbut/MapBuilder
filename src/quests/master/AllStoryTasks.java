package quests.master;

import quests.task.InteractTask;
import quests.task.Task;

/**
 * A class which holds all static references to all tasks associated with story quests.
 * Task naming format is quest number followed by _task number (###_##).
 * @author Sean Zimmerman
 */
public class AllStoryTasks {

    public static Task task001_01 = new InteractTask("filler", AllStoryTriggers.trigger001_01);
    public static Task task001_02 = new InteractTask("filler2", AllStoryTriggers.trigger001_02);

    public static Task task002_01 = new InteractTask("filler3", AllStoryTriggers.trigger002_01);
}
