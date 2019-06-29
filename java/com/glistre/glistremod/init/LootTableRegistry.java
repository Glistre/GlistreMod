package com.glistre.glistremod.init;

import com.glistre.glistremod.reference.Reference;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.LootTable;
import java.util.HashSet;
import java.util.Set;

/*import static glistre.glistremod.init.LootTableRegistry.RegistrationHandler.create;

/*public static final ResourceLocation Custom_Chest_Loot = register("custom_chest_loot");


private static ResourceLocation register(String id) {
		
       return LootTableList.register(new ResourceLocation(Reference.MODID, id));
       
       
}*/


/**
 * Registers this mod's {@link LootTable}s.
 *
 * credit to Choonster
 */
/*public class LootTableRegistry {
	public static final ResourceLocation CUSTOM_CHEST_LOOT = create("custom_chest_loot");


	/**
	 * Register this mod's {@link LootTable}s.
	 */
/*	public static void registerLootTables() {
		RegistrationHandler.LOOT_TABLES.forEach(LootTableList::register);
	}

	public static class RegistrationHandler {

		/**
		 * Stores the IDs of this mod's {@link LootTable}s.
		 */
/*		private static final Set<ResourceLocation> LOOT_TABLES = new HashSet<>();

		/**
		 * Create a {@link LootTable} ID.
		 *
		 * @param id The ID of the LootTable without the testmod3: prefix
		 * @return The ID of the LootTable
		 */
/*		protected static ResourceLocation create(final String id) {
			final ResourceLocation lootTable = new ResourceLocation(Reference.MODID, id);
			RegistrationHandler.LOOT_TABLES.add(lootTable);
			return lootTable;
		}
	}
}*/