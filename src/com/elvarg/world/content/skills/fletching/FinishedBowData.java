package com.elvarg.world.content.skills.fletching;

import com.elvarg.world.model.Item;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by Lander on 7/03/2017.
 */
public enum FinishedBowData {

 // NAME(unfinished, finished, requirement, experience)

    LONGBOW(new Item(48), new Item(839), 10, 10, 6684),

    SHORTBOW(new Item(50), new Item(841), 5, 5, 6678),

    OAK_SHORTBOW(new Item(54), new Item(843), 20, 17, 6679),

    OAK_LONGBOW(new Item(56), new Item(845), 25, 25, 6685),

    WILLOW_LONGBOW(new Item(58), new Item(847), 40, 42, 6686),

    WILLOW_SHORTBOW(new Item(60), new Item(849), 35, 33, 6680),

    MAPLE_LONGBOW(new Item(62), new Item(851), 55, 58, 6687),

    MAPLE_SHORTBOW(new Item(64), new Item(853), 50, 50, 6681),

    YEW_LONGBOW(new Item(66), new Item(855), 70, 75, 6688),

    YEW_SHORTBOW(new Item(68), new Item(857), 65, 68, 6682),

    MAGIC_LONGBOW(new Item(70), new Item(859), 85, 92, 6683),

    MAGIC_SHORTBOW(new Item(72), new Item(861), 80, 83, 6689);

    public final static ImmutableSet<FinishedBowData> BOW_VALUES = Sets
            .immutableEnumSet(EnumSet.allOf(FinishedBowData.class));

    public static Optional<FinishedBowData> get(Item item) {
        return BOW_VALUES.stream().filter(Objects::nonNull)
                .filter(potions -> potions.getUnfinishedBow().getId() == item.getId()).findAny();
    }


    private Item finished, unfinished;

    private int requirement;

    private int experience;

    private int animation;

    private FinishedBowData(Item unfinished, Item finished, int requirement, int experience, int animation) {
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

    public Item getFinishedBow() {
        return finished;
    }

    public int getRequirement() {
        return requirement;
    }

    public Item getUnfinishedBow() {
        return unfinished;
    }
}
