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
public enum ArrowshaftData {



    LOGS(new Item(1511), 1, 5, 15),

    OAK_LOGS(new Item(1521), 15, 10, 30),

    WILLOW_LOGS(new Item(1519), 30, 15, 45),

    MAPLE_LOGS(new Item(1517), 45, 20, 60),

    YEW_LOGS(new Item(1515), 60, 25, 75),

    MAGIC_LOGS(new Item(1513), 75, 30, 90);

    public final static ImmutableSet<ArrowshaftData> LOG_VALUES = Sets
            .immutableEnumSet(EnumSet.allOf(ArrowshaftData.class));

    public static Optional<ArrowshaftData> get(Item item) {
        return LOG_VALUES.stream().filter(Objects::nonNull)
                .filter(logs -> logs.getLogs().getId() == item.getId()).findAny();
    }

    private Item logs;

    private int requirement;

    private int experience;

    private int amount;

    private ArrowshaftData(Item logs, int requirement, int experience, int amount) {
        this.logs = logs;
        this.requirement = requirement;
        this.experience = experience;
        this.amount = amount;
    }

    public int getExperience() {
        return experience;
    }

    public Item getLogs() {
        return logs;
    }

    public int getRequirement() {
        return requirement;
    }

    public int getAmount() {
        return amount;
    }

}
