package sprites;

import quests.QuestHandler;
import quests.trigger.Trigger;

import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;

public class NPC extends Sprite {

	private characters.Character chara;
	private LinkedList<Trigger> questActivationTriggers;
	private LinkedList<Trigger> questTriggers;
	private Trigger lastActivatedTrigger = null;

	public NPC(int x, int y, characters.Character chara, String[] message) {
		super(x, y);
		this.chara = chara;
		this.message = message;
		this.questActivationTriggers = new LinkedList<>();
		this.questTriggers = new LinkedList<>();
		initNPC();
	}

	/**
	 * Constructor which includes Activation Triggers (this NPC will be used to activate quests)
	 */
	public NPC(int x, int y, characters.Character chara,
			   String[] message, LinkedList<Trigger> questActivationTriggers, LinkedList<Trigger> questTriggers) {
		this(x, y, chara, message);
		this.questActivationTriggers = questActivationTriggers;
		this.questTriggers = questTriggers;

		/*
		Npc's are created on map load so they will always retain the same set of triggers as long as you are in the
		map, this will remove any already used triggers from the Npc before the user can interact with it.
		Not very efficient, but it is the best solution I can come up with. Hopefully it isn't too bad.
		 */
		Iterator<Trigger> it = questActivationTriggers.iterator();
		Trigger t = null;
		while(it.hasNext()) {
			t = it.next();
			if(QuestHandler.checkQuestCompletion(t)) {
				it.remove();
			}
		}

		it = questTriggers.iterator();
		while(it.hasNext()) {
			t = it.next();
			if(QuestHandler.checkQuestCompletion(t)) {
				it.remove();
			}
		}
	}

	private void initNPC() {
		isObstacle = true;
		setImage(chara.getImage());
	}

	public characters.Character getNPC() {
		return chara;
	}

	public void setCurrentImage(String imageLoc) {
		setImage(imageLoc);
	}

	/**
	 * Runs the private helper methods to determine if any quest interaction has occurred, if it has,
	 * activate it.
	 */
	public void questInteraction() {
		interactNormalQuestTriggers();
		interactActivatingQuest();
	}

	/**
	 * If any quest activation triggers have been met then activate the quest. If not then do nothing.
	 */
	private void interactActivatingQuest() {
		if(questActivationTriggers.size() != 0) {
			if(lastActivatedTrigger == null) {
				if(QuestHandler.isAcceptable(questActivationTriggers.get(0))) {
					lastActivatedTrigger = questActivationTriggers.get(0);
					questActivationTriggers.remove(0);
					QuestHandler.acceptQuest(lastActivatedTrigger);
				} else {
					//nothing for now (dialog of something)
				}
			} else { //lastActivatedTrigger != null
				if(!QuestHandler.isActive(lastActivatedTrigger)) { //previous quest is done
					if(QuestHandler.isAcceptable(questActivationTriggers.get(0))) { //next quest is ready
						lastActivatedTrigger = questActivationTriggers.get(0);
						questActivationTriggers.remove(0);
						QuestHandler.acceptQuest(lastActivatedTrigger);
					}
					//otherwise, do nothing until next quest is ready
				}
				//otherwise, do nothing until last quest is done
			}
		}
	}

	/**
	 *
	 */
	private void interactNormalQuestTriggers() {
		if(questTriggers.size() != 0) { //ensures there are triggers
			//check if any triggers are attached to an active task or not
			questTriggers.forEach(QuestHandler::checkForTrigger);
			//QuestHandler.checkForTrigger(Trigger t) handles actual completion of the task it is attached to
		}
	}
}
