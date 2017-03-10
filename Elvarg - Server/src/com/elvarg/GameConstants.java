package com.elvarg;

import com.elvarg.cache.impl.CacheConstants;
import com.elvarg.world.model.Item;
import com.elvarg.world.model.Position;
import com.google.common.collect.ImmutableSet;

/**
 * A class containing different attributes which affect the game in different
 * ways.
 * 
 * @author Swiffy
 */
public class GameConstants {

	/**
	 * Is JAGGRAB enabled?
	 */
	public static final boolean JAGGRAB_ENABLED = true;

	/**
	 * The directory of the cache files.
	 */
	public static final String DEFINITIONS_DIRECTORY = CacheConstants.CACHE_BASE_DIR + "definitions/";

	/**
	 * The game engine rate
	 */
	public static final int ENGINE_PROCESSING_CYCLE_RATE = 300;

	/**
	 * The maximum amount of iterations that should occur per queue.
	 */
	public static final int QUEUED_LOOP_THRESHOLD = 50;

	/**
	 * The game version
	 */
	public static final int GAME_VERSION = 1;

	/**
	 * The secure game UID /Unique Identifier/
	 */
	public static final int GAME_UID = 23;

	/**
	 * The default position in game.
	 */
	public static final Position DEFAULT_POSITION = new Position(3091, 3503);

	/**
	 * Should the inventory be refreshed immediately on switching items or
	 * should it be delayed until next game cycle?
	 */
	public static final boolean QUEUE_SWITCHING_REFRESH = true;

	/**
	 * The tab interfaces in game. {Gameframe} [0] = tab Id, [1] = tab interface
	 * Id
	 */
	public static final int TAB_INTERFACES[][] = {

			{ 0, 2423 },

			{ 1, 3917 },

			{ 2, 639 },

			{ 3, 3213 },

			{ 4, 1644 },

			{ 5, 5608 },

			{ 6, -1 },

			{ 7, 37128 },

			{ 8, 5065 },

			{ 9, 5715 },

			{ 10, 2449 },

			{ 11, 42500 },

			{ 12, 147 }

	};

	public static final ImmutableSet<Item> STATER_KIT_INVENTORY = ImmutableSet.of(

			new Item(995, 150000),

			new Item(8013, 10),

			new Item(2552, 1),

			new Item(3853, 1),

			new Item(1095, 1),

			new Item(1129, 1),

			new Item(882, 250),

			new Item(841, 1),

			new Item(1379, 1),

			new Item(558, 400),

			new Item(556, 400),

			new Item(555, 200),

			new Item(557, 200),

			new Item(554, 200),

			new Item(579, 1),

			new Item(577, 1),

			new Item(1011, 1),

			new Item(380, 44),

			new Item(379, 6)

	);
}
