package quests.master;

import quests.trigger.Trigger;

/**
 * A class which holds all static references to all triggers associated with story quests.
 * Trigger naming format is quest number followed by _task number (###_##).
 * @author Sean Zimmerman
 */
public class AllStoryTriggers {

    public static Trigger trigger001_01 = new Trigger("quest001_01");
    public static Trigger trigger001_02 = new Trigger("quest001_02");

    public static Trigger trigger002_01 = new Trigger("quest002_01");
}
