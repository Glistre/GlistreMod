package com.glistre.glistremod.proxies;

import java.awt.Color;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticlePortal;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import com.glistre.glistremod.GlistreMod;
import com.glistre.glistremod.blocks.fluids.ModFluids;
import com.glistre.glistremod.effects.potions.splash.EntitySplashProjectile;
import com.glistre.glistremod.effects.potions.splash.RenderSplashPotion;
import com.glistre.glistremod.entities.*;
import com.glistre.glistremod.entities.blacktobie.BlackModelTobo;
import com.glistre.glistremod.entities.blacktobie.BlackRenderTobo;
import com.glistre.glistremod.entities.blacktobie.EntityBlackTobo;
import com.glistre.glistremod.entities.guardian.EntityTobieSkel;
import com.glistre.glistremod.entities.guardian.TobieModelGuardian;
import com.glistre.glistremod.entities.guardian.TobieSkelRender;
import com.glistre.glistremod.entities.king.EntityTobieKing;
import com.glistre.glistremod.entities.king.TobieKingRender;
import com.glistre.glistremod.entities.king.TobieModelKing;
import com.glistre.glistremod.entities.queen.EntityTobieQueen;
import com.glistre.glistremod.entities.queen.TobieModelQueen;
import com.glistre.glistremod.entities.queen.TobieQueenRender;
import com.glistre.glistremod.entities.wolf.BlackModelWolf;
import com.glistre.glistremod.entities.wolf.BlackRenderWolf;
import com.glistre.glistremod.entities.wolf.EntityBlackWolf;
import com.glistre.glistremod.entities.wolf.EntityGlistreWolf;
import com.glistre.glistremod.entities.wolf.GlistreModelWolf;
import com.glistre.glistremod.entities.wolf.GlistreRenderWolf;
import com.glistre.glistremod.events.EntityPortalFreonFX;
import com.glistre.glistremod.init.BlockRegistry;
import com.glistre.glistremod.init.GMTileEntityRegistry;
import com.glistre.glistremod.init.GlistreEntityRegistry;
import com.glistre.glistremod.init.ItemRegistry;
import com.glistre.glistremod.init.LootTableRegistry;
import com.glistre.glistremod.init.Recipes;
//import com.glistre.glistremod.items.bow.BusterBowRenderer;
import com.glistre.glistremod.projectiles.blaster.EntityBlasterBolt;
import com.glistre.glistremod.projectiles.blaster.EntityEnderBoltFireball;
import com.glistre.glistremod.projectiles.blaster.EntitySceptreBolt;
import com.glistre.glistremod.projectiles.blaster.RendreBlast;
import com.glistre.glistremod.projectiles.blaster.RendreBlast2;
import com.glistre.glistremod.projectiles.blaster.RendreBlast3;
import com.glistre.glistremod.projectiles.blaster.RendreGlistreFactory;
import com.glistre.glistremod.projectiles.tobyworstsword.MessageExtendedReachAttack;
import com.glistre.glistremod.projectiles.tobyworstsword.TobyEntityProjectile;
import com.glistre.glistremod.projectiles.tobyworstsword.TobyRenderProjectile;
import com.glistre.glistremod.reference.Reference;
import com.glistre.glistremod.render.GlistreChestGoldInventoryRenderer;
import com.glistre.glistremod.render.GlistreChestInventoryRenderer;
import com.glistre.glistremod.render.GlistreChestRenderer;
import com.glistre.glistremod.render.GlistreGoldChestRenderer;
import com.glistre.glistremod.tabs.TabRegistry;
import com.glistre.glistremod.tileentity.TileEntityGlistreChest;
import com.glistre.glistremod.tileentity.TileEntityGlistreChestGold;
import com.glistre.glistremod.util.GlistreModelManager;

import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;



public class ClientProxy extends CommonProxy {

	@Override
	public void preInit() {
		
		registerSimpleNetworking();
		GlistreModelManager.INSTANCE.registerAllModels();
		ItemRegistry.registerRenders();
		BlockRegistry.registerRenders();
		
//		GlistreEntityRegistry.initializeEntity();
//		GlistreEntityRegistry.register();
		GlistreEntityRegistry.registerRenders();
		
		RenderingRegistry.registerEntityRenderingHandler(EntityBlasterBolt.class, new IRenderFactory<EntityBlasterBolt>(){

		@Override
		public Render<? super EntityBlasterBolt> createRenderFor(RenderManager manager){
			
			return new RendreBlast(manager);

		}});
	 	ModelLoader.setCustomModelResourceLocation(GlistreEntityRegistry.blaster_bolt_1, 0 , new ModelResourceLocation(GlistreEntityRegistry.blaster_bolt_1.getRegistryName(), "inventory"));

	}
	
	@Override
	public void init(){
		this.registerRenders();
		TabRegistry.registerRenders();

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
	
    @Override
    public EntityPlayer getPlayerEntityFromContext(MessageContext ctx) 
    {
        // Note that if you simply return 'Minecraft.getMinecraft().thePlayer',
        // your packets will not work because you will be getting a client
        // player even when you are on the server! Sounds absurd, but it's true.

        // Solution is to double-check side before returning the player:
  //  	return (ctx.side.isClient() ? Minecraft.getMinecraft().thePlayer : ctx.getServerHandler().playerEntity);

       return (ctx.side.isClient() ? Minecraft.getMinecraft().thePlayer : super.getPlayerEntityFromContext(ctx));
    }
   
		
	@Override
	public void registerRenders(){
	//     RenderingRegistry.registerEntityRenderingHandler(TobyEntityProjectile.class, RendreGlistreFactory.FACTORY);
	     RenderingRegistry.registerEntityRenderingHandler(TobyEntityProjectile.class, new TobyRenderProjectile(Minecraft.getMinecraft().getRenderManager(), GlistreEntityRegistry.tobie_worst_projectile_1, Minecraft.getMinecraft().getRenderItem()));
	     RenderingRegistry.registerEntityRenderingHandler(EntitySplashProjectile.class, new RenderSplashPotion(Minecraft.getMinecraft().getRenderManager(), GlistreEntityRegistry.splash_poison_protection, Minecraft.getMinecraft().getRenderItem()));
		
//removed from 1.8		MinecraftForgeClient.registerItemRenderer(ItemRegistry.custom_bow_1, new BusterBowRenderer());
//		GlistreEntityRegistry.registerEntity();

	    RenderingRegistry.registerEntityRenderingHandler(EntityGlistreWolf.class, new GlistreRenderWolf(Minecraft.getMinecraft().getRenderManager(), new GlistreModelWolf(), 0.3F));
	    RenderingRegistry.registerEntityRenderingHandler(EntityBlackWolf.class, new BlackRenderWolf(Minecraft.getMinecraft().getRenderManager(), new BlackModelWolf(), 0.3F));
	    RenderingRegistry.registerEntityRenderingHandler(EntityBlackTobo.class, new BlackRenderTobo(Minecraft.getMinecraft().getRenderManager(), new BlackModelTobo(), 0.7F));
	    RenderingRegistry.registerEntityRenderingHandler(EntityTobieSkel.class, new TobieSkelRender(Minecraft.getMinecraft().getRenderManager(), new TobieModelGuardian(), 0.5F));
	    RenderingRegistry.registerEntityRenderingHandler(EntityTobieQueen.class, new TobieQueenRender(Minecraft.getMinecraft().getRenderManager(), new TobieModelQueen(), 0.5F));
	    RenderingRegistry.registerEntityRenderingHandler(EntityTobieKing.class, new TobieKingRender(Minecraft.getMinecraft().getRenderManager(), new TobieModelKing(), 0.5F));

//	    RenderingRegistry.registerEntityRenderingHandler(EntityBlasterBolt.class, new RendreBlast(Minecraft.getMinecraft().getRenderManager(), GlistreEntityRegistry.blaster_bolt_1, Minecraft.getMinecraft().getRenderItem()));
        RenderingRegistry.registerEntityRenderingHandler(EntityEnderBoltFireball.class, new RendreBlast2(Minecraft.getMinecraft().getRenderManager(), GlistreEntityRegistry.ender_bolt_1, Minecraft.getMinecraft().getRenderItem()));
	    RenderingRegistry.registerEntityRenderingHandler(EntitySceptreBolt.class, new RendreBlast3(Minecraft.getMinecraft().getRenderManager(), GlistreEntityRegistry.sceptre_bolt_1, Minecraft.getMinecraft().getRenderItem()));
	    
//      RenderingRegistry.registerEntityRenderingHandler(EntitySplashProjectile.class, new RenderSplashPotion(Minecraft.getMinecraft().getRenderManager(), GlistreEntityRegistry.splash_poison_protection, Minecraft.getMinecraft().getRenderItem()));
//      RenderingRegistry.registerEntityRenderingHandler(EntitySplashProjectile.class, new RenderSplashPotion(GlistreEntityRegistry.splash_poison_protection));


	    
	    GlistreChestRenderer gcr = new GlistreChestRenderer(Minecraft.getMinecraft().getRenderManager());
	    ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGlistreChest.class, gcr);	    
	    GlistreGoldChestRenderer gcrg = new GlistreGoldChestRenderer(Minecraft.getMinecraft().getRenderManager());
	    ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGlistreChestGold.class, gcrg);	
	    TileEntityItemStackRenderer.instance = new GlistreChestInventoryRenderer();
	    TileEntityItemStackRenderer.instance = new GlistreChestGoldInventoryRenderer();


	}
	
	//added/instantiated public World worldObj etc for all the non-visible variables from the Entity class 
	//because Particle was EntityFX used to extend Entity where they were visible(public) but they are protected (only accessible from same package) 
	//in the Particle class

	public void addParticleEffect(EntityPortalFreonFX particle) {
		 double motionX = particle.worldObj.rand.nextGaussian() * 0.02D;
		    double motionY = particle.worldObj.rand.nextGaussian() * 0.02D;
		    double motionZ = particle.worldObj.rand.nextGaussian() * 0.02D;
		    ParticlePortal particleMysterious = new EntityPortalFreonFX(
		          particle.worldObj, 
		          particle.posX + particle.worldObj.rand.nextFloat() * particle.width 
		                * 2.0F - particle.width, 
		          particle.posY + 0.5D + particle.worldObj.rand.nextFloat() 
		                * particle.height, 
		          particle.posZ + particle.worldObj.rand.nextFloat() * particle.width 
		                * 2.0F - particle.width, 
		          motionX, 
		          motionY, 
		          motionZ);
		Minecraft.getMinecraft().effectRenderer.addEffect(particle);
	}

}