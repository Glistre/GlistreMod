package com.glistre.glistremod.events;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityMoveHelper.Action;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.event.world.BlockEvent;
//import sun.security.krb5.Config;
//import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.BlockFluidClassic;

import java.awt.Event;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.glistre.glistremod.GlistreMod;
import com.glistre.glistremod.blocks.GlistreChest;
import com.glistre.glistremod.blocks.RudBlock;
import com.glistre.glistremod.blocks.fluids.ModFluids;
import com.glistre.glistremod.entities.blacktobie.EntityBlackTobo;
import com.glistre.glistremod.entities.king.EntityTobieKing;
import com.glistre.glistremod.entities.queen.EntityTobieQueen;
import com.glistre.glistremod.init.BiomeRegistry;
import com.glistre.glistremod.init.BlockRegistry;
import com.glistre.glistremod.init.DimensionRegistry;
import com.glistre.glistremod.init.ItemRegistry;
import com.glistre.glistremod.lib.ConfigurationGlistre;
import com.glistre.glistremod.lib.Defaults;
import com.glistre.glistremod.projectiles.tobyworstsword.IExtendedReach;
import com.glistre.glistremod.projectiles.tobyworstsword.MessageExtendedReachAttack;
import com.glistre.glistremod.reference.Reference;
import com.glistre.glistremod.tileentity.TileEntityGlistreChest;

import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GlistreEventHandler {

	//mouseover event below for extended reach of item (tobysword)
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEvent(MouseEvent event)
	{ 
	    if (event.getButton() == 0 && event.isButtonstate())
	    {
	        Minecraft mc = Minecraft.getMinecraft();//.func_71410_x()  == getMinecraft
	        EntityPlayer thePlayer = mc.thePlayer;//field_71439_g; ==thePlayer
	        if (thePlayer != null)
	        {
	        	//checks for the item to avoid hackers
	        	//func_71045_bC(); ==getCurrentEquippedItem 1.10 //func_71045_bC(); 1.10 became getHeldItemMainhand() 1.10 
	            ItemStack itemstack = thePlayer.getHeldItemMainhand();
	            IExtendedReach ieri;
	            if (itemstack != null)
	            {
	                if (itemstack.getItem() instanceof IExtendedReach)//func_77973_b()  ==getItem
	                {
	                    ieri = (IExtendedReach) itemstack.getItem();
	                } else
	                {
	                    ieri = null;
	                }
	   
	                if (ieri != null)
	                {
	                    float reach = ieri.getReach();
	                    RayTraceResult mov = getMouseOverExtended(reach); 
	                      
	                    if (mov != null)
	                    {
	                        if (mov.entityHit != null && mov.entityHit.hurtResistantTime == 0)//.field_72308_g  ==entityHit
	                        {
	                            if (mov.entityHit != thePlayer )
	                            {
	                                GlistreMod.network.sendToServer(new MessageExtendedReachAttack(
	                                      mov.entityHit.getEntityId()));//.func_145782_y() ==getEntityId
	                            }
	                        }
	                    }
	                }
	            }
	        }
	    }
	}
	        
	// This is mostly copied from the EntityRenderer#getMouseOver() method
	public static RayTraceResult getMouseOverExtended(float dist)
	{
	    Minecraft mc = FMLClientHandler.instance().getClient();
	    Entity theRenderViewEntity = mc.getRenderViewEntity();
	    AxisAlignedBB theViewBoundingBox = new AxisAlignedBB(
	            theRenderViewEntity.posX-0.5D,
	            theRenderViewEntity.posY-0.0D,
	            theRenderViewEntity.posZ-0.5D,
	            theRenderViewEntity.posX+0.5D,
	            theRenderViewEntity.posY+1.5D,
	            theRenderViewEntity.posZ+0.5D
	            );
	    RayTraceResult returnMOP = null;
	    if (mc.theWorld != null)
	    {
	        double var2 = dist;
	        returnMOP = theRenderViewEntity.rayTrace(var2, 0);
	        double calcdist = var2;
	        //changed Vec3 to Vec3d 1.10 is that right?
	        Vec3d pos = theRenderViewEntity.getPositionEyes(0);
	        var2 = calcdist;
	        if (returnMOP != null)
	        {
	            calcdist = returnMOP.hitVec.distanceTo(pos);
	        }
	         
	        Vec3d lookvec = theRenderViewEntity.getLook(0);
	        Vec3d var8 = pos.addVector(lookvec.xCoord * var2, 
	              lookvec.yCoord * var2, 
	              lookvec.zCoord * var2);
	        Entity pointedEntity = null;
	        float var9 = 1.0F;
	        @SuppressWarnings("unchecked")
	        List<Entity> list = mc.theWorld.getEntitiesWithinAABBExcludingEntity(
	              theRenderViewEntity, 
	              theViewBoundingBox.addCoord(
	                    lookvec.xCoord * var2, 
	                    lookvec.yCoord * var2, 
	                    lookvec.zCoord * var2).expand(var9, var9, var9));
	        double d = calcdist;
	            
	        for (Entity entity : list)
	        {
	            if (entity.canBeCollidedWith())
	            {
	                float bordersize = entity.getCollisionBorderSize();
	                AxisAlignedBB aabb = new AxisAlignedBB(
	                      entity.posX-entity.width/2, 
	                      entity.posY, 
	                      entity.posZ-entity.width/2, 
	                      entity.posX+entity.width/2, 
	                      entity.posY+entity.height, 
	                      entity.posZ+entity.width/2);
	                aabb.expand(bordersize, bordersize, bordersize);
	                RayTraceResult mop0 = aabb.calculateIntercept(pos, var8);
	                    
	                if (aabb.isVecInside(pos))
	                {
	                    if (0.0D < d || d == 0.0D)
	                    {
	                        pointedEntity = entity;
	                        d = 0.0D;
	                    }
	                } else if (mop0 != null)
	                {
	                    double d1 = pos.distanceTo(mop0.hitVec);
	                        
	                    if (d1 < d || d == 0.0D)
	                    {
	                        pointedEntity = entity;
	                        d = d1;
	                    }
	                }
	            }
	        }
	           
	        if (pointedEntity != null && (d < calcdist || returnMOP == null))
	        {
	             returnMOP = new RayTraceResult(pointedEntity);
	        }
	    }
	    return returnMOP;
	}
	@SubscribeEvent
	public void checkUpdate(PlayerEvent.PlayerLoggedInEvent event){
	}

	@SubscribeEvent
	public void throwegg(PlayerInteractEvent event){
		
	}


	@SubscribeEvent
	public void onBlockDropItems(BlockEvent.HarvestDropsEvent event){
		Biome b = BiomeRegistry.biomeGlistre;
		//searches for random 1/10 = ten percent chance of golden apple drop when leaves decay && random.nextInt(9) == 0)  Random random = new Random();
        
		if (event.getState().getBlock() == BlockRegistry.enchanted_block_1) {

			 ItemStack stack = new ItemStack(Items.EXPERIENCE_BOTTLE, 12, 0);
			 stack.setStackDisplayName(TextFormatting.GOLD + "Enjoy the free EXP :D");
			 event.getDrops().add(stack);
		}
		if (event.getState().getBlock() == BlockRegistry.silver_ore_2) {

			 ItemStack stack = new ItemStack(Blocks.DIAMOND_BLOCK, 3, 0);
			 stack.setStackDisplayName(TextFormatting.GOLD + "Enjoy the Diamonds you earned them!! :D");
			 event.getDrops().add(stack);
		}
		if (event.getState().getBlock() == BlockRegistry.silver_ore_3) {

			 ItemStack stack = new ItemStack(Blocks.GOLD_BLOCK, 8, 0);
			 stack.setStackDisplayName(TextFormatting.GOLD + "Enjoy the Gold you deserve it!! :D");
			 event.getDrops().add(stack);
		}
		//&& !event.getHarvester().capabilities.isCreativeMode 
		if ((event.getHarvester() != null && !event.getHarvester().worldObj.isRemote 
				&& b == BiomeRegistry.biomeGlistre && event.getState().getBlock() == Blocks.LEAVES)) {
			
			EntityPlayer player = event.getHarvester();			 
			ItemStack stack = new ItemStack(Items.GOLDEN_APPLE, 1, 0);
			 ArrayList<ItemStack> drps = new ArrayList<ItemStack>();
		    	for (ItemStack is:event.getDrops()) {
		    			//if (is.getItem() == Items.apple )
			 event.setDropChance(0.3F);
			//add the new item //is.stackSize = # , third parameter =1 enchanted
	    				drps.add(new ItemStack(Items.GOLDEN_APPLE, is.stackSize, 0)); 
			 stack.setStackDisplayName(TextFormatting.GOLD + "Golden Apple! :D");
			 player.addChatMessage(
						new TextComponentString(TextFormatting.GOLD + "LOOK FOR A SURPRISE IN THE GLISTERING BIOME:D")); 
		}
    	event.getDrops().clear(); //remove the old
		event.getDrops().addAll(drps);
		}
		
		if ((event.getHarvester() != null && !event.getHarvester().worldObj.isRemote && b == BiomeRegistry.biomeGlistre && event.getState().getBlock() == Blocks.LEAVES)) {
			 EntityPlayer player = event.getHarvester();
			 ItemStack stack1 = new ItemStack(Items.GOLDEN_APPLE, 1, 1);
			 ArrayList<ItemStack> drps1 = new ArrayList<ItemStack>();
		    	for (ItemStack is1:event.getDrops()) {

			 event.setDropChance(0.2F);
	    				drps1.add(new ItemStack(Items.GOLDEN_APPLE, is1.stackSize, 1)); //add the new item //is.stackSize = # , third parameter =1 enchanted
			 stack1.setStackDisplayName(TextFormatting.GOLD + "Enchanted Golden Apple! :D");
			 player.addChatMessage(
						new TextComponentString(TextFormatting.GOLD + "LOOK FOR A SURPRISE IN THE GLISTERING BIOME :D" ));	 
		    	}
		    	event.getDrops().clear(); //remove the old
				event.getDrops().addAll(drps1);
		}
		

	    if ((b == BiomeRegistry.biomeGlistre && (event.getState().getBlock() == Blocks.LEAVES ) )) {	
	    	ArrayList<ItemStack> drps2 = new ArrayList<ItemStack>();
	    	for (ItemStack is2:event.getDrops()) {
	    			if (is2.getItem() == Items.APPLE )	{
	    				event.setDropChance(1.0F);// Change to e.g. 1.0f, if you manipulate the list and want to guarantee it always drops
	    				drps2.add(new ItemStack(Items.GOLDEN_APPLE, is2.stackSize, 0)); //add the new item //is.stackSize = # , third parameter =1 enchanted
	    				is2.setStackDisplayName(TextFormatting.GOLD + "Golden Apple! :D");
	    			}
	    			else {
	    				drps2.add(is2);//add any other items that dropped, but which we're not replacing
	    			}
	    		}
	    	event.getDrops().clear(); //remove the old
			event.getDrops().addAll(drps2);
	    	}
		}

/**
 * LIGHTNING AND THUNDER EVENT IN FREON BIOME	
 */

	@SubscribeEvent
	public void thunderStorm (PlayerTickEvent event){
	Biome b = BiomeRegistry.biomeFreon;
	Random rand = new Random(); 
	int varX=rand.nextInt(25); 
	int varZ=rand.nextInt(25);
	int spawnchance=rand.nextInt(150);

	if (spawnchance == 50){	
		if (!event.player.worldObj.isRemote )
		{ 		
			BlockPos pos0 = new BlockPos(event.player.getPosition().getX(), event.player.getPosition().getY(), event.player.getPosition().getZ());
			//1.10.2 changed getBiomeGenForCoords Body
			if (event.player.worldObj.getBiomeForCoordsBody(pos0) == BiomeRegistry.biomeFreon)
			{
//1.8 code:  if(player.worldObj.getBiomeGenForCoords(new BlockPos(player.posX, player.posY, player.posZ)) == BiomeRegistry.biomeFreon)	
                //worldIn.playSound((EntityPlayer)null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

				event.player.worldObj.playSound((EntityPlayer)null, pos0, SoundEvents.WEATHER_RAIN_ABOVE, SoundCategory.WEATHER, 100F, 1.0F);
				//.playSound(event.player.posX, event.player.posY, event.player.posZ, "ambient.weather.thunder", 100.0F, 1.0F, false);				
				event.player.worldObj.addWeatherEffect(event.player);//???confused 1.10.2
				//.addWeatherEffect(new EntityLightningBolt(event.player.worldObj, event.player.posX + varX, event.player.posY, event.player.posZ + varZ));//1.89.9 version
			 }	
					//tried removing to stop lag to no avail --9== confusion
						if (event.player.isPotionActive(Potion.getPotionById(9)) &&
							//1.8 changed next two lines
							event.player.worldObj.getBiomeForCoordsBody(pos0) == BiomeRegistry.biomeGlistre 
							|| event.player.worldObj.getBiomeForCoordsBody(pos0) == BiomeRegistry.biomeFreon){  
		
							event.player.worldObj.playSound((EntityPlayer)null, pos0, SoundEvents.WEATHER_RAIN_ABOVE, SoundCategory.WEATHER, 100F, 1.0F);
							//event.player.worldObj.playSound(event.player.posX, event.player.posY, event.player.posZ, "ambient.weather.thunder", 100.0F, 1.0F, false);//changed to above 1.10.2
							
							event.player.worldObj.addWeatherEffect(new EntityLightningBolt(event.player.worldObj, event.player.lastTickPosX + varX, event.player.lastTickPosY, event.player.lastTickPosZ + varZ, false));	
					}
		  }
	   }
	}

//why is this in here again???
/*	@SubscribeEvent
	public void onPlayer(PlayerTickEvent event) {
//||  event.player.worldObj.provider.dimensionId == 0 
//		&& event.player.worldObj.getBiomeGenForCoords(event.player.chunkCoordX, event.player.chunkCoordZ).biomeName.equals("Freon Biome"))
		    if ( event.player.worldObj.provider.dimensionId == 8 ) {
			Random rand = new Random();
			int addX = rand.nextInt(25);
			int addZ = rand.nextInt(25);
			if (rand.nextInt(2) == 1)
				addX = -addX;
			if (rand.nextInt(2) == 1)
				addZ = -addZ;
			int lightingSpawnChance = rand.nextInt(50);
			
			if (lightingSpawnChance == 10)
				event.player.worldObj.addWeatherEffect(new EntityLightningBolt(
						event.player.worldObj, event.player.posX + addX,
						event.player.posY, event.player.posZ + addZ));		
		}	
	}*/


/*	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
			System.out.println("Config changed!");
		    if(event.modID.equalsIgnoreCase(Reference.MODID)) {
		       GlistreMod.config.hasChanged();{
//		    	   GlistreMod.config.load();
		    	   ConfigurationGlistre.loadConfig();
					GlistreMod.config.save();
				}
		    }
	}*/

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event){
		//removed .modId
		if(event.equals(Reference.MODID)){
			
		ConfigurationGlistre.syncConfig();
			//resync configs this is where restart would or not be required	
			System.out.println("Config changed!");
		}
		//resync configs this is where restart would or not be required	
			
	}
/*}	
		if (b == BiomeRegistry.biomeGlistre 
				&& (event.pickedUp.getEntityItem().equals(new ItemStack(ItemRegistry.MightySword).hasTagCompound() &&
						event.pickedUp.getEntityItem().equals(new ItemStack(ItemRegistry.MightySword).getTagCompound().getBoolean("dropped") == true*/

	@SubscribeEvent
	public void onPickup(PlayerEvent.ItemPickupEvent event){

		World world = event.player.worldObj;

		final String KEY = "activated"; // make sure not to repeat literals, store it in a variable like this
		ItemStack playerPickedUp = event.pickedUp.getEntityItem();
		if(playerPickedUp.getItem() == ItemRegistry.mighty_sword && playerPickedUp.getTagCompound() != null 
				&& playerPickedUp.getTagCompound().hasKey(KEY) && !playerPickedUp.getTagCompound().getBoolean(KEY)){
			
		EntityPlayer player = (EntityPlayer) event.player;
		
		if  (player != null && player.worldObj.provider.getDimension() == 0 || player.worldObj.provider.getDimension() == Defaults.DIM_ID.GLISTRE || player.dimension != -1 || player.dimension != 1 && !event.player.worldObj.isRemote 
				&& event.pickedUp.getEntityItem().isItemEqual(new ItemStack(ItemRegistry.mighty_sword))){
		
			if (!(event.player.worldObj.isRemote) && !(event.player.worldObj.getWorldInfo().isRaining() 
				&& event.pickedUp.getEntityItem().equals(new ItemStack(ItemRegistry.mighty_sword)))){
    
				//WorldServer worldserver = MinecraftServer.getServer().worldServers[0];//worked in 1.8.9
				//World world = World.getMinecraftServer();  //this is client side world
				
				WorldServer worldServer = FMLCommonHandler.instance().getMinecraftServerInstance().getServer().worldServerForDimension(Defaults.DIM_ID.FREON);			
				WorldInfo worldinfo = worldServer.getWorldInfo();
				//first parameter is temp .2F and rain looks like snow, second parameter is rainfall 0F none .5F is normal)	
				//new Biome.BiomeProperties("Desert");
					BiomeProperties b = new Biome.BiomeProperties("Glistering Biome");
					BiomeProperties b1 = new Biome.BiomeProperties("Desert");
					BiomeProperties b2 = new Biome.BiomeProperties("Savanna");
					//Biome b2 = Biomes.SAVANNA; 1.8.9 version					//.setTemperatureRainfall(0.0F, 1.0F);
					BiomeProperties b3 = new Biome.BiomeProperties("Savanna Plateau");
					//Biome b3 = Biomes.SAVANNA_PLATEAU;
					BiomeProperties b4 = new Biome.BiomeProperties("Mesa");
					//Biome b4 = Biomes.MESA;
					b.setTemperature(0.0F);
					b1.setTemperature(0.0F);
					b2.setTemperature(0.0F);
					b3.setTemperature(0.0F);
					b4.setTemperature(0.0F);
					b.setRainfall(1.0F);
					b1.setRainfall(1.0F);
					b2.setRainfall(1.0F);
					b3.setRainfall(1.0F);
					b4.setRainfall(1.0F);
					event.player.worldObj.getRainStrength(500.0F);
					event.player.worldObj.setRainStrength(500.0F);
					event.player.worldObj.getWorldInfo().setRainTime(0);
					event.player.worldObj.getWorldInfo().setThunderTime(0);
					b.setSnowEnabled();//.setEnableSnow();	in 1.8.9
					b1.setSnowEnabled();
					b2.setSnowEnabled();
					b3.setSnowEnabled();
					b4.setSnowEnabled();
					
					//debug
					System.out.println("rain=" + player.worldObj.getWorldInfo().isRaining());
					
					worldinfo.setRaining(true);
					worldinfo.setThundering(true);
					event.player.worldObj.getWorldInfo().setRaining(true);
					//debug
					System.out.println("rain=" + player.worldObj.getWorldInfo().isRaining());
					
					BlockPos pos = player.getPosition();
					event.player.worldObj.playSound((EntityPlayer)null, pos, SoundEvents.WEATHER_RAIN_ABOVE, SoundCategory.WEATHER, 100F, 1.0F);
					event.player.worldObj.addWeatherEffect(new EntityLightningBolt(event.player.worldObj, event.player.posX , event.player.posY, event.player.posZ, false));

// blindness == 15; confusion/nausea == 9
					player.addPotionEffect(new PotionEffect(Potion.getPotionById(15), 2500, 0));					
					PotionEffect vomitus = (new PotionEffect(Potion.getPotionById(9), 3500, 4));
					vomitus.getCurativeItems().clear();				
					player.addPotionEffect(vomitus); 
					//player.addPotionEffect(new PotionEffect(ItemRegistry.vomitusPotion.id, 100, 1000, true));
	
						}
						
						event.player.addChatMessage(
								new TextComponentString(TextFormatting.DARK_RED + "DESTRUCTION OF THE BIOMES HAVE BEGUN!!"));	
						event.player.addChatMessage(
								new TextComponentString(TextFormatting.DARK_AQUA+ "YOU MUST KILL THE TOBIE KING TO SAVE THIS WORLD!!"));	
						event.player.addChatMessage(
								new TextComponentString(TextFormatting.DARK_AQUA +  "BEGIN FREEZING WORLD!!"));
//						ICommandManager server = MinecraftServer.getServer().getCommandManager();
//						server.executeCommand(player, "/op" + player);
						
//						server.executeCommand(player, "/weather rain 40000");
//						server.executeCommand(player, "/deop" + player);
						event.player.worldObj.setWorldTime(12000);
						
						playerPickedUp.getTagCompound().removeTag(KEY);
							
						}
		
		}
		if (event.pickedUp.getEntityItem().isItemEqual(new ItemStack(BlockRegistry.silver_ore_1))){

			event.player.addStat(GlistreMod.blockAchievement_1, 1);	

			EntityPlayer player = (EntityPlayer) event.player;
			player.addExperience(3);
		
			event.player.addChatMessage(new TextComponentString("You just harvested Silver Ore!"));
		}
		if (event.pickedUp.getEntityItem().isItemEqual(new ItemStack(Items.GOLDEN_APPLE, 1, 1))){
		EntityPlayer player = (EntityPlayer)event.player;
		 player.addChatMessage(
					new TextComponentString(TextFormatting.GREEN + "YOU FOUND IT >>>> A GOD APPLE!!! :D"));
		}
	}	

	@SubscribeEvent
	public void onCrafting(PlayerEvent.ItemSmeltedEvent event){
		 if(event.smelting.getItem() == ItemRegistry.mighty_ice_sword){
			 
			 EntityPlayer player = (EntityPlayer)event.player;
			
			 if((!event.player.worldObj.isRemote) && (event.player.worldObj.getWorldInfo().isRaining())){
					 event.player.worldObj.getWorldInfo().setRaining(false);
					 event.player.worldObj.getWorldInfo().setThundering(false);
					 event.player.worldObj.setWorldTime(2000);
					
					 //1.10.2 change MinecraftServer.getServer() to FML ...
					 	ICommandManager command = FMLCommonHandler.instance().getMinecraftServerInstance().getCommandManager();
					 	command.executeCommand(player, "/op" + player);
						command.executeCommand(player, "/weather clear");
						command.executeCommand(player, "/deop" + player);
						
			 }
		 }
		//	 event.player.triggerAchievement(achievementCuredIceSword);)
			 
	}
		 
	@SubscribeEvent
	public void onPlayerJoin(PlayerLoggedInEvent event)
	{
		if (!event.player.worldObj.isRemote)
		{
			event.player.inventory.addItemStackToInventory(new ItemStack(ItemRegistry.secret_book));
			event.player.inventory.addItemStackToInventory(new ItemStack(ItemRegistry.ancient_book));

			event.player.addChatMessage(new TextComponentString(TextFormatting.GOLD + "Have fun with GlistreMod!"));
			event.player.addChatMessage(
					new TextComponentString(TextFormatting.GOLD + "You have a Recipe Book!"));
		}
	}
	
	//Player right click on a block
	@SubscribeEvent
	public void blockRightClickEvent(PlayerInteractEvent.RightClickBlock event) {
	// public static void BlockRightClickEvent(PlayerInteractEvent.RightClickBlock event) { ///old 1.8.9
		    // client side
		    if (event.getWorld().isRemote) {
		        if (event.getHand().equals(EnumHand.MAIN_HAND)) {
		        }
		        else {
		        }
		    }
		    // server side
		    else {
		        if (event.getHand().equals(EnumHand.MAIN_HAND)) {
		        }
		        else {
		        }
		    }
		
			if(!event.getEntityPlayer().isSneaking()) {
				//Player is not sneaking
								
			//	TileEntity tileentity = event.world.getTileEntity(event.pos);
				Block block = event.getWorld().getBlockState(event.getPos()).getBlock();
				
				if (!event.getWorld().isRemote && block.equals(BlockRegistry.glistre_chest)) {
					World world = event.getEntity().worldObj;
			        	//spawn corrupted Tobies 
			        	EntityBlackTobo entity = new EntityBlackTobo(world);
			        	EntityBlackTobo entity1 = new EntityBlackTobo(world);
			        	EntityBlackTobo entity2 = new EntityBlackTobo(world);

			        	EntityPlayer player = event.getEntityPlayer();
			        	entity.copyLocationAndAnglesFrom(player);
			    		entity.setPosition(event.getEntityPlayer().posX - 3, event.getEntityPlayer().posY + 3, event.getEntityPlayer().posZ - 3);
			    		entity.setCurrentItemOrArmor(0, new ItemStack(ItemRegistry.tobie_gun_1));
			        	entity1.copyLocationAndAnglesFrom(player);
			    		entity1.setPosition(event.getEntityPlayer().posX - 5, event.getEntityPlayer().posY + 3, event.getEntityPlayer().posZ - 5);
			    		entity1.setCurrentItemOrArmor(0, new ItemStack(ItemRegistry.tobie_gun_1));
			        	entity2.copyLocationAndAnglesFrom(player);
			    		entity2.setPosition(event.getEntityPlayer().posX - 7, event.getEntityPlayer().posY + 3, event.getEntityPlayer().posZ - 7);    		
			    		entity2.setCurrentItemOrArmor(0, new ItemStack(ItemRegistry.tobie_gun_1));

			    		
			    		
			    		//entity.setLocationAndAngles(i, j, k, yaw, pitch)
			    		//entity.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, 0.0F, 0.0F);

			    		//entity.setLocationAndAngles(event.x + 2, event.y + 1, event.z + 11, 0.0F , 0.0F);		    		
			    		
			    			 world.spawnEntityInWorld(entity);
			    			 world.spawnEntityInWorld(entity1);
			    			 world.spawnEntityInWorld(entity2);

			    		
//			    		Minecraft.getMinecraft().thePlayer.addChatMessage(
//			    	    		new TextComponentString(TextFormatting.DARK_GREEN + "Chest spawned Toby King at location X: " + entity.posX + "|Y: " + entity.posY + TextFormatting.DARK_GREEN + "|Z: " + entity.posZ));
			    		//DEBUG this below just tells me if its generating or not 
			         //   System.out.println("Generating Toby King in Freon Biome");
		//			FMLClientHandler.instance().displayGuiScreen(event.getEntityPlayer(), new GuiChest(event.getEntityPlayer().inventory, (IInventory) tileentity));
			
			            //makes chest not open when right clicked
			            //	event.setCanceled(true);
					
				}
			}		
	}
	@SubscribeEvent
	public void onEntitySpawnsTobieKing(LivingSpawnEvent event) {
		if (event.getEntity() instanceof EntityTobieKing)  {
        	NBTTagCompound nbt = event.getEntity().getEntityData();
        //	NBTTagCompound nbt = new NBTTagCompound();
        	nbt.setBoolean("spawned", true);
			if(nbt.hasKey("spawned")){
				
		List<EntityPlayer> playerList = event.getEntity().worldObj.getEntitiesWithinAABB(EntityPlayer.class, event.getEntity().getEntityBoundingBox().expand(35.0D, 15.0D, 15.0D));
		Iterator<EntityPlayer> i1 = playerList.iterator();
		EntityPlayer player;

              while (i1.hasNext())
              {
            	     player = (EntityPlayer)i1.next();	

		        			player.addChatComponentMessage(
                    		new TextComponentString(TextFormatting.DARK_GREEN + "Toby King in Range, location" 
                    + TextFormatting.DARK_RED + " X: " + (int)Math.round(event.getEntity().posX) 
                    + TextFormatting.GOLD + " | Y: " + (int)Math.round(event.getEntity().posY) 
                    + TextFormatting.DARK_AQUA +" | Z: " + (int)Math.round(event.getEntity().posZ)));
				}
              }
              }
		}
	
	@SubscribeEvent
	public void onEntityTobieQueenDeath(LivingDeathEvent event){
		if (!event.getEntityLiving().worldObj.isRemote ){
		if(!(event.getEntityLiving() instanceof EntityTobieQueen)){
			return;
		}
		//TobieQueenElizabeth
		EntityTobieQueen queen = (EntityTobieQueen) event.getEntityLiving();
		if (event.getEntityLiving() instanceof EntityTobieQueen){
		//	event.getEntityLiving().worldObj.setWorldTime(2000); 
			World world = event.getEntityLiving().worldObj;
		
			DamageSource source = event.getSource();
//			EntityTobieQueen queen = (EntityTobieQueen) event.getEntityLiving();		
			if (source.getEntity() instanceof EntityPlayer) {
				EntityPlayer sourcePlayer = (EntityPlayer) source.getEntity();
				sourcePlayer.addStat(GlistreMod.mobKillAchievement_1, 1);	
				//System.out.println("Tobie Queen Elizabeth "+entityLiving.getName()+" died. Entity on "+(event.getEntity().worldObj.isRemote ? "client" : "server") + " world.");

				sourcePlayer.addChatMessage(
						new TextComponentString(TextFormatting.GOLD + "YOU KILLED THE EVIL TOBIE QUEEN ELIZABETH!!!!"));
				sourcePlayer.addPotionEffect(new PotionEffect(Potion.getPotionById(6), 2500, 0));//6==instantHealth
				sourcePlayer.addPotionEffect(new PotionEffect(ItemRegistry.poison_protect_potion, 3000, 0, false, false));			
				sourcePlayer.addPotionEffect(new PotionEffect(ItemRegistry.nausea_protect_potion, 3000, 0, false, false));			
				sourcePlayer.getActivePotionEffects();
				sourcePlayer.removePotionEffect(Potion.getPotionById(9));//confusion==9			
				sourcePlayer.addExperience(80000);
				
				sourcePlayer.addChatMessage(
						new TextComponentString(TextFormatting.DARK_GREEN + "CURE THE MIGHTY ICE SWORD!!"));
				sourcePlayer.addChatMessage(
						new TextComponentString(TextFormatting.DARK_GREEN + "HINT: USE THE SECRET RECIPE!"));
				}
			    
			}
		}
			
	}	
/*	@SubscribeEvent
	public void onEntityBlackToboDeath(LivingDeathEvent event){
		if (!(event.getEntityLiving().worldObj.isRemote) &&
		!(event.getEntityLiving() instanceof EntityBlackTobo)){
			return;
		}
		World world = event.getEntityLiving().worldObj;
		DamageSource source = event.getSource();
		
		if (!(world.isRemote) && (event.getEntityLiving() instanceof EntityBlackTobo && !(world.getWorldInfo().isRaining()))){
			EntityPlayer sourcePlayer = (EntityPlayer) source.getEntity();
		//Corrupted Tobie
		EntityBlackTobo blackTobo = (EntityBlackTobo)event.getEntityLiving();
		//WorldInfo worldinfo = worldserver.getWorldInfo();
		
		//b.setTemperatureRainfall(0.0F, 1.0F);
		world.setRainStrength(500.0F);
		world.getWorldInfo().setRainTime(0);
		world.getWorldInfo().setRaining(true);
		}
		}*/
		
		
		@SubscribeEvent
		public void onEntityTobieKingDeath(LivingDeathEvent event){
			if (!event.getEntityLiving().worldObj.isRemote ){
			if(!(event.getEntityLiving() instanceof EntityTobieKing)){
				return;
			}
	
			//Tobie King
			EntityTobieKing king = (EntityTobieKing)event.getEntityLiving();
			
					World world = event.getEntity().worldObj;
					//spawn Tobie Queen Elizabeth on place of Tobie King
					EntityTobieQueen queen = new EntityTobieQueen(world); 
					queen.copyLocationAndAnglesFrom(king);
					world.spawnEntityInWorld(queen);		
			//		System.out.println("Tobie King "+entityLiving.getName()+" died. Entity on "+(event.getEntity().worldObj.isRemote ? "client" : "server") + " world.");
		    		Minecraft.getMinecraft().thePlayer.addChatMessage(
    	    		new TextComponentString(TextFormatting.DARK_RED + "YOU HAVE AWAKENED TOBY QUEEN ELIZABETH!!!!!"));

					Random rand = new Random();
					for(int countparticles = 0; countparticles <= 80; ++countparticles)
					{
						//not sure about 1.8 update substituted BlockPos
						
						world.spawnParticle(EnumParticleTypes.REDSTONE, king.posX + (rand.nextDouble() - 0.5D) * (double)king.width, king.posY + rand.nextDouble() * (double)king.height - (double)king.getYOffset(), king.posZ + (rand.nextDouble() - 0.5D) * (double)king.width, 255, 215, 0);
					}		
			}
		}
		@SubscribeEvent
		@SideOnly(Side.CLIENT)
		public void onLightningHitMob (LivingAttackEvent event){
			

			if (!event.getEntityLiving().worldObj.isRemote ){	
			if (event.getEntityLiving() instanceof EntityBlackTobo || event.getEntityLiving() instanceof EntityTobieKing || event.getEntityLiving() instanceof EntityTobieQueen) {
				World world = event.getEntity().worldObj;
				
				//cancels lightning damage to Bad Tobies
				
				if (event.getSource().getSourceOfDamage() instanceof EntityLightningBolt){
					event.setCanceled(true);
				}
				else if (event.getSource().isFireDamage()){
					 event.setCanceled(true);
				}
				}
			}

		}
		
		@SubscribeEvent
		@SideOnly(Side.CLIENT)
		public void onLightningHurt (LivingHurtEvent event){
			
			if (!event.getEntityLiving().worldObj.isRemote ){	
			if (event.getEntityLiving() instanceof EntityBlackTobo || event.getEntityLiving() instanceof EntityTobieKing || event.getEntityLiving() instanceof EntityTobieQueen) {
				//World world = event.getEntity().worldObj;
	//reduce burning from fire sword damage to Bad Tobies
				if (event.getSource().equals(DamageSource.onFire)){
				event.setAmount(0.1F);
				}
				if (event.getSource().equals(DamageSource.lightningBolt)){
					event.setAmount(0.0F);
					}
				
				//removes fire damage from player wearing emerald lightning helmet
				else if (event.getEntityLiving() instanceof EntityPlayer) {
					
					//ItemStack helmet = event.getEntityLiving().getCurrentArmor(3);  worked in 1.8.9
					ItemStack helmet = event.getEntityLiving().getItemStackFromSlot(EntityEquipmentSlot.HEAD);
					if(helmet.isItemEqual(new ItemStack(ItemRegistry.emerald_helmet_1))){
						if (event.getSource().equals(DamageSource.lightningBolt)){
							event.setAmount(0.0F);	
					}
				}
				}
			}
			}

			
		}	
/*		@SubscribeEvent
		@SideOnly(Side.CLIENT)
		public void onFogColors(FogColors event) {
			RudBlock block = RudBlock.atEyeLevel(event.getEntity());
			if (block != null) {
				int color = block.getFluid().getColor();
				event.red = (color >> 16 & 255) / 255.0F;
				event.green = (color >> 8 & 255) / 255.0F;
		        event.blue = (color & 255) / 255.0F;
			}
		}*/
	}


	
