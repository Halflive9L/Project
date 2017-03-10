package com.elvarg.world.content.skills.fletching;

import com.elvarg.world.model.Item;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by Lander on 8/03/2017.
 */
public enum FinishedCrossBowData {

    BRONZE_CROSSBOW(new Item(9454), new Item(9174), 9, 6, 6671);

    public final static ImmutableSet<FinishedCrossBowData> CROSSBOW_VALUES = Sets
            .immutableEnumSet(EnumSet.allOf(FinishedCrossBowData.class));

    public static Optional<FinishedCrossBowData> get(Item item) {
        return CROSSBOW_VALUES.stream().filter(Objects::nonNull)
                .filter(crossbows -> crossbows.getUnfinishedCrossBow().getId() == item.getId()).findAny();
    }


    private Item finished, unfinished;

    private int requirement;

    private int experience;

    private int animation;

    private FinishedCrossBowData(Item unfinished, Item finished, int requirement, int experience, int animation) {
        this.finished = finished;
        this.unfinished = unfinished;
        this.requirement = requirement;
        this.experience = experience;
        this.animation = animation;
    }

    public int getExperience() {
        return experience;
    }

    public int getAnimation() {
        return animation;
    }

    public Item getFinishedCrossBow() {
        return finished;
    }

    public int getRequirement() {
        return requirement;
    }

    public Item getUnfinishedCrossBow() {
        return unfinished;
    }
}
