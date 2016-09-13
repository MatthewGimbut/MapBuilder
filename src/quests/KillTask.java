package quests;

import characters.EnemyTypes;

/**
 * quests.KillTask is a subclass of quests.Task and is a Task based on killing a certain
 * type of enemy a designated number of times.
 * @author Sean Zimmerman
 */
public class KillTask extends Task {

    private EnemyTypes targetEnemy;
    private int numToKill;

    public KillTask(String description, EnemyTypes target, int numToKill) {
        this.description = description;
        this.targetEnemy = target;
        this.numToKill = numToKill;
    }

    /**
     * If the enemy that was killed is the same type as the task requires the task number
     * decremeents. This method will only be called from quests.QuestHandler.
     * @return The number of enemies left to kill after completion of the method (sucessful kill or not)
     */
    public int enemyKilled(EnemyTypes e) {
        if(targetEnemy.equals(e)) {
            numToKill--;
        }
        return numToKill;
    }

    /**
     * @return If the Task has been completed or not
     */
    public boolean checkTaskComplete() {
        if(numToKill == 0) {
            this.setComplete(true);
            return true;
        }
        return false;
    }

    public String toString() { return super.toString() + "\nProgress: "
            + numToKill + " remaining."; }

}
