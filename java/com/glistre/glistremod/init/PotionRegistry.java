package com.glistre.glistremod.init;

import com.glistre.glistremod.GlistreMod;
import com.glistre.glistremod.effects.potions.GlistremodPotionTypes;
import com.glistre.glistremod.effects.potions.NauseaProtectEffect;
import com.glistre.glistremod.effects.potions.PoisonProtectEffect;
import com.glistre.glistremod.effects.potions.VomitusEffect;
import com.glistre.glistremod.reference.Reference;

import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

/**
 * Registers this mod's {@link Potion}s.
 *
 * @author Glistre credit to Choonster
 */
@SuppressWarnings("WeakerAccess")
@ObjectHolder(Reference.MODID)
public class PotionRegistry {

	public static void GlistreMod(){
		init();
		register();
	}

	private static void register() {
		// TODO Auto-generated method stub
		
	}

	private static void init() {
		// TODO Auto-generated method stub
		
	}

	public static final PoisonProtectEffect POISON_PROTECT_POTION = new PoisonProtectEffect(false, 2, 2, 2, "poison_protect_potion");
	public static final	NauseaProtectEffect NAUSEA_PROTECT_POTION = new NauseaProtectEffect(false, 2, 2, 2, "nausea_protect_potion");
	public static final	VomitusEffect VOMITUS_POTION = new VomitusEffect(false, 2, 2, 2, "vomitus_potion");

	@Mod.EventBusSubscriber
	public static class RegistrationHandler {
		/**
		 * Register this mod's {@link Potion}s.
		 *
		 * @param event The event
		 */
		@SubscribeEvent
		public static void registerPotions(final RegistryEvent.Register<Potion> event) {
			event.getRegistry().registerAll(
					POISON_PROTECT_POTION,
					NAUSEA_PROTECT_POTION,
					VOMITUS_POTION
			);
		}
	}

}