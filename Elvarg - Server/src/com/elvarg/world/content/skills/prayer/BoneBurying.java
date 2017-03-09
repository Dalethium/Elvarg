package com.elvarg.world.content.skills.prayer;

import java.util.HashMap;
import java.util.Map;

import com.elvarg.cache.impl.definitions.ItemDefinition;
import com.elvarg.world.entity.impl.player.Player;
import com.elvarg.world.model.Animation;
import com.elvarg.world.model.Item;
import com.elvarg.world.model.Priority;
import com.elvarg.world.model.Skill;

public enum BoneBurying {

	BAT_BONES(new Item(530), 5.3),

	BIG_BONES(new Item(532), 15),

	JOGRE_BONES(new Item(3125), 15),

	ZOGRE_BONES(new Item(4812), 12.5),

	BONES(new Item(526), 4.5),

	WYVERN_BONES(new Item(6812), 50),

	DRAGON_BONES(new Item(536), 72),

	FAYRG(new Item(4830), 84),

	RAURG_BONES(new Item(4832), 96),

	DAGANNOTH(new Item(6729), 125),

	WOLF_BONES(new Item(2859), 4.5),

	BURNST_BONES(new Item(528), 4.5),

	MONKEY_BONES(new Item(3183), 5),

	MONKEY_BONES2(new Item(3179), 5),

	SHAIKAHAN_BONES(new Item(3123), 25),

	BABY_DRAGON_BONES(new Item(534), 30),

	OURG_BONES(new Item(4834), 140);

	private final Item item;

	private final double experience;

	private static Map<Integer, BoneBurying> bone = new HashMap<Integer, BoneBurying>();

	static {
		for (BoneBurying b : BoneBurying.values()) {
			bone.put(b.getBone().getId(), b);
		}
	}

	public static BoneBurying forId(int id) {
		return bone.get(id);
	}

	private BoneBurying(final Item item, final double experience) {
		this.item = item;
		this.experience = experience;
	}

	public Item getBone() {
		return item;
	}

	public double getExperience() {
		return experience;
	}

	private static final Animation BURY = new Animation(827, Priority.LOW);

	public static void bury(final Player player, final BoneBurying bone, Item item) {
		if (bone == null) {
			return;
		}
		player.performAnimation(BURY);
		player.getSkillManager().addExperience(Skill.PRAYER, (int) bone.getExperience());
		player.getPacketSender()
				.sendMessage("You bury the " + ItemDefinition.forId(item.getId()).getName().toLowerCase() + ".");
		player.getInventory().delete(item, item.getSlot());

	}
}