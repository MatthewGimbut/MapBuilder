package quests;

import sprites.Sprite;

/**
 * quests.LocationTrigger is a subclass of quests.Trigger, it represents an invisible trigger which
 * would be on a map and ran over by a player. Contains a sprites.Sprite object.
 * @author Sean Zimmerman
 */
public class LocationTrigger extends Trigger {

    private Sprite triggerSprite;

    public LocationTrigger(Sprite sprite) {
        this.triggerSprite = sprite;
    }
}
