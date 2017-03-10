package com.elvarg.net.packet.impl;

import com.elvarg.cache.impl.definitions.ObjectDefinition;
import com.elvarg.engine.task.TaskManager;
import com.elvarg.engine.task.impl.ForceMovementTask;
import com.elvarg.engine.task.impl.WalkToTask;
import com.elvarg.engine.task.impl.WalkToTask.FinalizedMovementTask;
import com.elvarg.net.packet.Packet;
import com.elvarg.net.packet.PacketConstants;
import com.elvarg.net.packet.PacketListener;
import com.elvarg.world.collision.region.RegionClipping;
import com.elvarg.world.entity.combat.magic.Autocasting;
import com.elvarg.world.entity.impl.object.GameObject;
import com.elvarg.world.entity.impl.player.Player;
import com.elvarg.world.model.Animation;
import com.elvarg.world.model.ForceMovement;
import com.elvarg.world.model.Item;
import com.elvarg.world.model.MagicSpellbook;
import com.elvarg.world.model.Position;
import com.elvarg.world.model.Skill;
import com.elvarg.world.model.container.impl.Inventory;

public class ObjectActionPacketListener implements PacketListener {

	private void itemOnObject(final Player player, Packet packet) {
		int interfaceId = packet.readShort();
		int id = packet.readUnsignedShort();
		int y = packet.readLEShortA();
		int slot = packet.readUnsignedShort();
		int x = packet.readLEShortA();
		int z = player.getPosition().getZ();
		int itemId = packet.readShort();
		if (interfaceId != Inventory.INTERFACE_ID) {
			return;
		}
		final Item interacted = player.getInventory().forSlot(slot);
		if (interacted == null || itemId != interacted.getId() || slot != interacted.getSlot()
				|| !player.getInventory().contains(interacted.getId())) {
			return;
		}
		final Position pos = new Position(x, y, z);
		final GameObject object = new GameObject(id, pos);
		if (!RegionClipping.objectExists(object) || object == null) {
			return;
		}
		int distanceX = (player.getPosition().getX() - pos.getX());
		int distanceY = (player.getPosition().getY() - pos.getY());
		if (distanceX < 0) {
			distanceX = -(distanceX);
		}
		if (distanceY < 0) {
			distanceY = -(distanceY);
		}
		int size = distanceX > distanceY ? ObjectDefinition.forId(id).getSizeX()
				: ObjectDefinition.forId(id).getSizeY();
		if (size <= 0) {
			size = 1;
		}
		object.setSize(size);
		player.setWalkToTask(new WalkToTask(player, pos, object.getSize(), new FinalizedMovementTask() {
			@Override
			public void execute() {
				// execute code here for action
				if (interacted.getId() == 995 && object.getId() == 409) {
					player.getPacketSender().sendMessage("Hello world.");
					return;
				}
				// example ^
				player.getPacketSender().sendMessage("Nothing interesting happens...");
			}
		}));
		System.out.println("Interacted with objectId: " + object.getId() + " with the itemId: " + itemId);
	}

	private void firstClick(final Player player, Packet packet) {
		int x = packet.readLEShortA();
		int id = packet.readUnsignedShort();
		int y = packet.readUnsignedShortA();
		final Position position = new Position(x, y, player.getPosition().getZ());
		final GameObject gameObject = new GameObject(id, position);
		if (id > 0 && id != 6 && !RegionClipping.objectExists(gameObject)) {
			return;
		}
		int distanceX = (player.getPosition().getX() - position.getX());
		int distanceY = (player.getPosition().getY() - position.getY());
		if (distanceX < 0) {
			distanceX = -(distanceX);
		}
		if (distanceY < 0) {
			distanceY = -(distanceY);
		}
		int size = distanceX > distanceY ? ObjectDefinition.forId(id).getSizeX()
				: ObjectDefinition.forId(id).getSizeY();
		if (size <= 0) {
			size = 1;
		}
		gameObject.setSize(size);
		player.setWalkToTask(new WalkToTask(player, position, gameObject.getSize(), new FinalizedMovementTask() {
			@Override
			public void execute() {
				switch (id) {
				case WILDERNESS_DITCH:
					player.getMovementQueue().reset();
					if (player.getForceMovement() == null) {
						final Position crossDitch = new Position(0, player.getPosition().getY() < 3522 ? 3 : -3);
						TaskManager
								.submit(new ForceMovementTask(player, 3, new ForceMovement(player.getPosition().copy(),
										crossDitch, 0, 70, crossDitch.getY() == 3 ? 0 : 2, 6132)));
					}
					break;

				case LUNAR_ALTAR:
				case ANCIENT_ALTAR:
					MagicSpellbook toChange = MagicSpellbook.ANCIENT;
					if (id == LUNAR_ALTAR) {
						toChange = MagicSpellbook.LUNAR;
					}
					if (player.getSpellbook() == toChange) {
						player.setSpellbook(MagicSpellbook.NORMAL);
					} else {
						player.setSpellbook(toChange);
					}
					Autocasting.setAutocast(player, null);
					player.getPacketSender().sendMessage("You have changed your magic spellbook.").sendTabInterface(6,
							player.getSpellbook().getInterfaceId());
					break;

				case PRAYER_ALTAR:
					if (player.getSkillManager().getCurrentLevel(Skill.PRAYER) < player.getSkillManager()
							.getMaxLevel(Skill.PRAYER)) {
						player.performAnimation(new Animation(645));
						player.getPacketSender().sendMessage("You recharge your Prayer points.");
						player.getSkillManager().setCurrentLevel(Skill.PRAYER,
								player.getSkillManager().getMaxLevel(Skill.PRAYER), true);
					} else {
						player.getPacketSender()
								.sendMessage("You don't need to recharge your prayer points right now.");
					}
					break;

				}
				player.setPositionToFace(gameObject.getPosition());
			}
		}));
	}

	private void secondClick(final Player player, Packet packet) {
		int id = packet.readLEShortA();
		int y = packet.readLEShort();
		int x = packet.readUnsignedShortA();
		final Position position = new Position(x, y, player.getPosition().getZ());
		final GameObject gameObject = new GameObject(id, position);
		if (id > 0 && id != 6 && !RegionClipping.objectExists(gameObject)) {
			return;
		}
		player.setPositionToFace(gameObject.getPosition());
		int distanceX = (player.getPosition().getX() - position.getX());
		int distanceY = (player.getPosition().getY() - position.getY());
		if (distanceX < 0) {
			distanceX = -(distanceX);
		}
		if (distanceY < 0) {
			distanceY = -(distanceY);
		}
		int size = distanceX > distanceY ? distanceX : distanceY;
		gameObject.setSize(size);
		player.setWalkToTask(new WalkToTask(player, position, gameObject.getSize(), new FinalizedMovementTask() {
			@Override
			public void execute() {
				switch (id) {
				case EDGEVILLE_BANK:
					player.getBank(player.getCurrentBankTab()).open();
					break;
				}
				player.setPositionToFace(gameObject.getPosition());
			}
		}));
	}

	private void thirdClick(Player player, Packet packet) {

	}

	private void fourthClick(Player player, Packet packet) {

	}

	private void fifthClick(final Player player, Packet packet) {

	}

	@Override
	public void handleMessage(Player player, Packet packet) {
		if (player.busy()) {
			return;
		}
		switch (packet.getOpcode()) {
		case PacketConstants.OBJECT_FIRST_CLICK_OPCODE:
			firstClick(player, packet);
			break;
		case PacketConstants.OBJECT_SECOND_CLICK_OPCODE:
			secondClick(player, packet);
			break;
		case PacketConstants.OBJECT_THIRD_CLICK_OPCODE:
			thirdClick(player, packet);
			break;
		case PacketConstants.OBJECT_FOURTH_CLICK_OPCODE:
			fourthClick(player, packet);
			break;
		case PacketConstants.OBJECT_FIFTH_CLICK_OPCODE:
			fifthClick(player, packet);
			break;
		case PacketConstants.ITEM_ON_OBJECT:
			itemOnObject(player, packet);
			break;
		}
	}

	private static final int ANCIENT_ALTAR = 6552;
	private static final int LUNAR_ALTAR = 14911;
	private static final int PRAYER_ALTAR = 409;
	private static final int EDGEVILLE_BANK = 6943;
	private static final int WILDERNESS_DITCH = 23271;
}
