package quests.master;

import quests.Quest;

import java.util.ArrayList;
import java.util.List;

/**
 * A class which holds all static references to all other quests.
 * Other quests begin indexing at 500.
 * @author Sean Zimmerman
 */
public class AllOtherQuests {

    public static Quest quest500;

    public static List<Quest> initialize() {
        List<Quest> result = new ArrayList<Quest>();

        return result;
    }
}
