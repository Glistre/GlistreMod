package com.glistre.glistremod.proxies;

import com.glistre.glistremod.GlistreMod;
import com.glistre.glistremod.events.EntityPortalFreonFX;
import com.glistre.glistremod.init.ItemRegistry;
import com.glistre.glistremod.init.Recipes;
import com.glistre.glistremod.projectiles.blaster.EntityBlasterBolt;
import com.glistre.glistremod.projectiles.tobyworstsword.MessageExtendedReachAttack;
import com.glistre.glistremod.reference.Reference;
import com.glistre.glistremod.tileentity.TileEntityGlistreChest;

//import cpw.mods.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticlePortal;
//import net.minecraft.client.particle.ParticlePortal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;


public class CommonProxy {
	
	public void preInit() {
		
		registerSimpleNetworking();
		
		int modEntityID = 1;
	      //ResourceLocation registryName, Class<? extends Entity> entityClass, String entityName, int id, Object mod, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates
//        EntityRegistry.registerModEntity(new ResourceLocation(Parachute.modid, parachuteName), EntityParachute.class, parachuteName, entityID, Parachute.instance, 80, 3, true);
		EntityRegistry.registerModEntity(EntityBlasterBolt.class, "blaster_bolt_1" , modEntityID++, GlistreMod.instance, 64, 1, true);       

	}

	public void init() {

	}
	
	public void postInit() {
		
	}
	
	public void registerRenders(){
		
	}
//changed constructor from ParticlePortal and added public World worldObj to EntityPortalFreonFX class

	//1.10.2 changed constructor back to ParticlePortal
	public void addParticleEffect(ParticlePortal particle) {
		
		
	}
	
    /*
	 * Thanks to jabelar copied from his tutorial
	 */
	/**
	 * Registers the simple networking channel and messages for both sides
	 */
	protected void registerSimpleNetworking() 
	{
		// DEBUG
		System.out.println("registering simple networking");
		GlistreMod.network = NetworkRegistry.INSTANCE.newSimpleChannel(GlistreMod.NETWORK_CHANNEL_NAME);

		int packetId = 0;
		// register messages from client to server
        GlistreMod.network.registerMessage(MessageExtendedReachAttack.Handler.class, MessageExtendedReachAttack.class, packetId++, Side.SERVER);
	}

	public EntityPlayer getPlayerEntityFromContext(MessageContext ctx) {
		
		//return ctx.side.isClient() ? Minecraft.getMinecraft().thePlayer : ctx.getServerHandler().playerEntity;
		return ctx.getServerHandler().playerEntity;
	/*	if (ctx.side.isServer()){
			return ctx.getServerHandler().playerEntity;
		}
		return null;*/
	}

}	


