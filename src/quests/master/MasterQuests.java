package quests.master;

import quests.Quest;

/**
 * An Enumerator which holds values for all of the quests in the game and references to the quest
 * objects for easy access.
 * @author Sean Zimmerman
 */
public enum MasterQuests {

    quest001 {
        public Quest getQuest() {
            return AllStoryQuests.quest001;
        }
    },
    quest002 {
        public Quest getQuest() {
            return AllStoryQuests.quest002;
        }
    };

    /*
     * So the compiler will let me run MasterQuests.valueOf(String).getQuest() on one of the quests
     * in main.MapParser.java
     */
    public Quest getQuest() {
        return null;
    }
}
