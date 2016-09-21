package quests.trigger;

/**
 * quests.trigger.Trigger represents an object which has a special interaction when player collision is detected
 * or when player interaction with an NPC holding a trigger occurs.
 *
 * The class itself has no special behavior but is used for general detection purposes by using
 * the default .equals() method to make sure the same trigger is used.
 * @author Sean Zimmerman
 */
public class Trigger {

    private String associatedWith;

    public Trigger(String associatedWith) {
        this.associatedWith = associatedWith;
    }

    public String getAssociatedWith() {
        return associatedWith;
    }

    public void setAssociatedWith(String associatedWith) { this.associatedWith = associatedWith; }

    public void setTrigger(String trigger) {
        this.associatedWith = trigger;
    }

    public boolean equals(Object o) {
        if(!(o instanceof Trigger)) {
            return false;
        }
        return associatedWith.equals(((Trigger) o).getAssociatedWith());
    }
}
