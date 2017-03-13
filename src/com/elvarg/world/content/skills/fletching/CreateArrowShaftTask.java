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
 * Created by Lander on 8/03/2017.
 */
public class CreateArrowShaftTask extends Task {
    private static final Animation CREATE_SHAFT_ANIMATION = new Animation(1248, Priority.LOW);

    public static final int ARROW_SHAFT = 52;
    public static final int KNIFE = 946;

    private final Player player;

    private final Optional<ArrowshaftData> shaft;

    private int amount;

    public CreateArrowShaftTask(Player player, Optional<ArrowshaftData> shaft, int amount) {
        super(3, player, true);
        this.player = player;
        this.shaft = shaft;
        this.amount = amount;
    }

    public void start(Player player) {
        if (shaft.isPresent()) {
            if (player.getSkillManager().getCurrentLevel(Skill.FLETCHING) >= shaft.get().getRequirement()) {
                player.setCurrentTask(new CreateArrowShaftTask(player, shaft, amount));
                TaskManager.submit(player.getCurrentTask());
            } else {
                DialogueManager.sendStatement(player, "You need a Fletching level of atleast "
                        + shaft.get().getRequirement() + " to fletch this log.");
                player.getPacketSender().sendMessage("You need a Fletching level of atleast "
                        + shaft.get().getRequirement() + " to fletch this log.");
            }
        }
    }

    private void create(Player player) {
        if (shaft.isPresent()) {
            player.getInventory().delete(shaft.get().getLogs());
            player.getInventory().add(ARROW_SHAFT, shaft.get().getAmount());
            player.getSkillManager().addExperience(Skill.FLETCHING, shaft.get().getExperience());
        }
    }

    @Override
    protected void execute() {
        if (shaft.isPresent()) {
            if (player.getInventory()
                    .contains(new Item[]{shaft.get().getLogs(), new Item(KNIFE)})) {
                player.performAnimation(CREATE_SHAFT_ANIMATION);
                player.getPacketSender()
                        .sendMessage("You cut the "
                                + ItemDefinition.forId(shaft.get().getLogs().getId()).getName().toLowerCase()
                                + " into some " + ItemDefinition.forId(ARROW_SHAFT).getName().toLowerCase() + ".");
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
