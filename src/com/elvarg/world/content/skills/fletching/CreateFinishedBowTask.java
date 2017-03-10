package com.elvarg.world.content.skills.fletching;

import com.elvarg.cache.impl.definitions.ItemDefinition;
import com.elvarg.engine.task.Task;
import com.elvarg.engine.task.TaskManager;
import com.elvarg.world.entity.impl.player.Player;
import com.elvarg.world.model.Animation;
import com.elvarg.world.model.Item;
import com.elvarg.world.model.Priority;
import com.elvarg.world.model.Skill;
import com.elvarg.world.model.dialogue.DialogueManager;

import java.util.Optional;

/**
 * Created by Lander on 7/03/2017.
 */
public class CreateFinishedBowTask extends Task {

    public static final int BOWSTRING = 1777;

    private final Player player;

    private final Optional<FinishedBowData> bow;

    private int amount;

    public CreateFinishedBowTask(Player player, Optional<FinishedBowData> bow, int amount) {
        super(9, player, true);
        this.player = player;
        this.bow = bow;
        this.amount = amount;
    }

    public void start(Player player) {
        if (bow.isPresent()) {
            if (player.getSkillManager().getCurrentLevel(Skill.FLETCHING) >= bow.get().getRequirement()) {
                player.setCurrentTask(new CreateFinishedBowTask(player,bow, amount));
                TaskManager.submit(player.getCurrentTask());

            } else {
                DialogueManager.sendStatement(player, "You need a Fletching level of atleast "
                        + bow.get().getRequirement() + " to make this bow.");
                player.getPacketSender().sendMessage("You need a Fletching level of atleast "
                        + bow.get().getRequirement() + " to make this bow.");
            }
        }
    }

    private void create(Player player) {
        if (bow.isPresent()) {
            player.getInventory().delete(bow.get().getUnfinishedBow());
            player.getInventory().delete(BOWSTRING, 1);
            player.getInventory().add(bow.get().getFinishedBow());
            player.getSkillManager().addExperience(Skill.FLETCHING, bow.get().getExperience());
        }
    }

    @Override
    protected void execute() {
        if (bow.isPresent()) {
            if (player.getInventory()
                    .contains(new Item[]{new Item(BOWSTRING), bow.get().getUnfinishedBow()})) {
                player.performAnimation(new Animation(ItemDefinition.forId(bow.get().getAnimation()).getId(), Priority.LOW));
                player.getPacketSender()
                        .sendMessage("You string the "
                                + ItemDefinition.forId(bow.get().getUnfinishedBow().getId()).getName().toLowerCase()
                                + " into a " + ItemDefinition.forId(bow.get().getFinishedBow().getId()).getName().toLowerCase() + ".");
                create(player);
                amount--;
                if (amount == 0) {
                    this.stop();
                }
            } else {
                DialogueManager.sendStatement(player, "You have ran out of the ingredients required.");
                player.getPacketSender().sendMessage("You have ran out of the ingredients required.");
                this.stop();
            }
        }
    }
}