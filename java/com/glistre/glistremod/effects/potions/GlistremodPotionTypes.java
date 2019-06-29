package com.glistre.glistremod.effects.potions;

import com.glistre.glistremod.GlistreMod;
import com.glistre.glistremod.init.ItemRegistry;
import com.glistre.glistremod.init.PotionRegistry;
import com.glistre.glistremod.reference.Reference;
import com.google.common.collect.ImmutableList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistryNamespacedDefaultedByKey;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Registers this mod's {@link PotionType}s.
 *
 *credit belongs to Choonster for his help on this
 */
@SuppressWarnings("WeakerAccess")
@ObjectHolder(Reference.MODID)
public class GlistremodPotionTypes {

	public static final PotionType POISON_PROTECT;
	public static final PotionType NAUSEA_PROTECT;
	public static final PotionType VOMITUS;
	public static final PotionType LONG_POISON_PROTECT;
	public static final PotionType LONG_NAUSEA_PROTECT;
	public static final PotionType LONG_VOMITUS;
	public static final PotionType STRONG_POISON_PROTECT;
	public static final PotionType STRONG_NAUSEA_PROTECT;
	public static final PotionType STRONG_VOMITUS;

	static {
		final String LONG_PREFIX = "long_";
		final String STRONG_PREFIX = "strong_";

		final int HELPFUL_DURATION_STANDARD = 3600;
		final int HELPFUL_DURATION_LONG = 9600;
		final int HELPFUL_DURATION_STRONG = 1800;

		final int HARMFUL_DURATION_STANDARD = 1800;
		final int HARMFUL_DURATION_LONG = 4800;
		final int HARMFUL_DURATION_STRONG = 900;

//		TEST = createPotionType(new PotionEffect(ItemRegistry.poison_protect_potion, HELPFUL_DURATION_STANDARD));
		POISON_PROTECT = createPotionType(new PotionEffect(PotionRegistry.POISON_PROTECT_POTION, HELPFUL_DURATION_STANDARD));
		LONG_POISON_PROTECT = createPotionType(new PotionEffect(PotionRegistry.POISON_PROTECT_POTION, HELPFUL_DURATION_LONG), LONG_PREFIX);
		STRONG_POISON_PROTECT = createPotionType(new PotionEffect(PotionRegistry.POISON_PROTECT_POTION, HELPFUL_DURATION_STRONG, 1), STRONG_PREFIX);
		NAUSEA_PROTECT = createPotionType(new PotionEffect(PotionRegistry.NAUSEA_PROTECT_POTION, HELPFUL_DURATION_STANDARD));
		LONG_NAUSEA_PROTECT = createPotionType(new PotionEffect(PotionRegistry.NAUSEA_PROTECT_POTION, HELPFUL_DURATION_LONG), LONG_PREFIX);
		STRONG_NAUSEA_PROTECT = createPotionType(new PotionEffect(PotionRegistry.NAUSEA_PROTECT_POTION, HELPFUL_DURATION_STRONG, 1), STRONG_PREFIX);
		VOMITUS = createPotionType(new PotionEffect(PotionRegistry.VOMITUS_POTION, HELPFUL_DURATION_STANDARD));
		LONG_VOMITUS = createPotionType(new PotionEffect(PotionRegistry.VOMITUS_POTION, HELPFUL_DURATION_LONG), LONG_PREFIX);
		STRONG_VOMITUS = createPotionType(new PotionEffect(PotionRegistry.VOMITUS_POTION, HELPFUL_DURATION_STRONG, 1), STRONG_PREFIX);

	}
	/**
	 * Create a {@link PotionType} from the specified {@link PotionEffect}.
	 * <p>
	 * Uses the {@link Potion}'s registry name as the {@link PotionType}'s registry name and name.
	 *
	 * @param potionEffect The PotionEffect
	 * @return The PotionType
	 */
	private static PotionType createPotionType(final PotionEffect potionEffect) {
		return createPotionType(potionEffect, null);
	}
	/**
	 * Create a {@link PotionType} from the specified {@link PotionEffect}
	 * <p>
	 * Uses the {@link Potion}'s registry name as the {@link PotionType}'s registry name (with an optional prefix) and name (with no prefix).
	 *
	 * @param potionEffect The PotionEffect
	 * @param namePrefix   The name prefix, if any
	 * @return The PotionType
	 */
	private static PotionType createPotionType(final PotionEffect potionEffect, @Nullable final String namePrefix) {
		final ResourceLocation potionName = potionEffect.getPotion().getRegistryName();

		final ResourceLocation potionTypeName;
		if (namePrefix != null) {
			potionTypeName = new ResourceLocation(potionName.getResourceDomain(), namePrefix + potionName.getResourcePath());
		} else {
			potionTypeName = potionName;
		}

		return new PotionType(potionName.toString(), potionEffect).setRegistryName(potionTypeName);
	}
	
	
	@Mod.EventBusSubscriber
	public static class RegistrationHandler {
		/**
		 * Register this mod's {@link PotionType}s.
		 *
		 * @param event The event
		 */
		@SubscribeEvent
		public static void registerPotionTypes(final RegistryEvent.Register<PotionType> event) {
			event.getRegistry().registerAll(
					POISON_PROTECT,
					NAUSEA_PROTECT,
					VOMITUS,
					LONG_POISON_PROTECT,
					LONG_NAUSEA_PROTECT,
					LONG_VOMITUS,
					STRONG_POISON_PROTECT,
					STRONG_NAUSEA_PROTECT,
					STRONG_VOMITUS
			);
		}
	}
}