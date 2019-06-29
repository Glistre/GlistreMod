package com.glistre.glistremod.events;

import java.util.Random;

import com.glistre.glistremod.GlistreMod;
import com.glistre.glistremod.blocks.RudBlock;
import com.glistre.glistremod.blocks.fluids.ModFluids;
import com.glistre.glistremod.init.BlockRegistry;
import com.glistre.glistremod.init.ItemRegistry;
import com.glistre.glistremod.reference.Reference;

import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootEntryTable;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogDensity;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fluids.BlockFluidBase;


public class GlistreModEventHooks {

	/*	static boolean hasAlreadyOpened = true;
	//GuiModInfo extends GuiScreen
	@SubscribeEvent
	public void onGuiOpened(GuiOpenEvent event){
		if(event.gui instanceof GuiContainer){
			if (!hasAlreadyOpened){
				event.gui = new GuiScreen();
				hasAlreadyOpened = false;
			}
		}
	}*/
/*	@SubscribeEvent
	public void eventHandler(RenderGameOverlayEvent event){
		if (event.isCancelable()|| event.type != ElementType.ALL)
		{
		return;
	}
	}*/
	
	
	@SubscribeEvent
	public void lootLoad(LootTableLoadEvent evt)

	{		
// 
//	if (evt.getName().toString().equals(Reference.MODID + ":glistre_chest_gold") || evt.getName().equals(LootTableList.CHESTS_SPAWN_BONUS_CHEST))
		if (evt.getName().equals(LootTableList.CHESTS_SPAWN_BONUS_CHEST))
		
		//if (e.getName().equals(LootTableList.register(new ResourceLocation(Reference.MODID + "loot_table_name")
	{
	//	LootEntry entry1 = new LootEntryTable(new ResourceLocation(Reference.MODID + "custom_chest_loot"), 90, 60, new LootCondition[0], Reference.MODID + ":ancient_book");
//		evt.getTable().addPool(new LootPool(new LootEntry[]{new LootEntryTable(new ResourceLocation(Reference.MODID + "custom_chest_loot"), 1, 1, new LootCondition[]{}, Reference.MODID + "custom_chest_loot")}, new LootCondition[]{}, new RandomValueRange(1), new RandomValueRange(0), "glistremod:custom_chest_loot"));

		//evt.getTable().addPool(new LootPool);
		//.addPool(new LootPool(new LootEntry[]{entry1}, new LootCondition[0], new RandomValueRange(1), new RandomValueRange(0,1), "glistremod/loot_tables:custom_chest_loot"));

//    public LootEntryItem(Item itemIn, int weightIn, int qualityIn, LootFunction[] functionsIn, LootCondition[] conditionsIn, String entryName)
			final String name = GlistreMod.CUSTOM_CHEST_LOOT.toString();
			

	LootEntry entry1 = new LootEntryTable(GlistreMod.CUSTOM_CHEST_LOOT, 1, 0, new LootCondition[0], name);
//	LootEntry entry2 = new LootEntryItem(ItemRegistry.secret_book, 50, 60, new LootFunction[0], new LootCondition[0], Reference.MODID + ":secret_book");

//	evt.getTable().getPool("main");
	
//	LootPool main = evt.getTable().getPool("main");
//	main.addPool(entry1);
//	main.addEntry(entry2);
	
	LootPool pool = new LootPool(new LootEntry[]{entry1}, new LootCondition[0], new RandomValueRange(1), new RandomValueRange(0,1), name);

	evt.getTable().addPool(pool);
	
/*	LootPool p1 = new LootPool(new LootEntry[]{entry1}, new LootCondition[0], new RandomValueRange(1), new RandomValueRange(0,1), "glistremod/loot_tables:custom_chest_loot");

	evt.getTable().getPool("p1").addEntry(entry1);*/
	
/*	LootPool main = evt.getTable().getPool("main");
	main.removeEntry("minecraft:wooden_axe");
	main.addEntry(new LootEntryItem(ItemRegistry.ancient_book, 10, 10, new LootFunction[0], new LootCondition[0], "glistremod:custom_chest_loot"));
	evt.getTable().removePool("pool3");*/
//addPool(new LootPool(new LootEntry[]{new LootEntryTable(blueprint_table, 1, 1, new LootCondition[]{}, blueprint_table.toString())}, new LootCondition[]{}, new RandomValueRange(1), new RandomValueRange(0), blueprint_table.toString()));
	
	/*	LootEntry entry2 = new LootEntryItem(ItemRegistry.secret_book, 50, 60, new LootFunction[0], new LootCondition[0], Reference.MODID + ":secret_book");

	LootPool pool2 = new LootPool(new LootEntry[]{entry}, new LootCondition[0], new RandomValueRange(1), new RandomValueRange(0,1), "secret_book");

	evt.getTable().addPool(pool2);*/

	}
	
	
	
	/*private LootPool getAdditive(String entryName) {
		   return new LootPool(new LootEntry[] { getAdditiveEntry(entryName, 1) }, new LootCondition[0], new RandomValueRange(1), new RandomValueRange(0, 1), "Additive_pool");
		}
	private LootEntryTable getAdditiveEntry(String name, inwt weight) {
	    return new LootEntryTable(new ResourceLocation(name), weight, 0, new LootCondition[0], "Additive_entry");
	}*/
	
	

	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onEntityUpdate(LivingUpdateEvent event) {
	if (event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer thePlayer = (EntityPlayer)event.getEntityLiving();{

	if (event.getEntityLiving().isPotionActive(ItemRegistry.poison_protect_potion)) {
	if (event.getEntityLiving().getActivePotionEffect(ItemRegistry.poison_protect_potion).getDuration()==0) 
	{
//		event.getEntityLiving().removePotionEffect(ItemRegistry.poison_protect_potion.id); changed this 1.10.2 not sure what to do here?
		event.getEntityLiving().removePotionEffect(ItemRegistry.poison_protect_potion.getPotionFromResourceLocation("glistremod:textures/gui/poison_protect_potion_icon.png"));

	}
/*	if (event.entityLiving.isPotionActive(ItemRegistry.nauseaProtectPotion)) {
		if (event.entityLiving.getActivePotionEffect(ItemRegistry.nauseaProtectPotion).getDuration()==0) 
		{
			event.entityLiving.removePotionEffect(ItemRegistry.nauseaProtectPotion.id);
		}*/
/*	if (event.entityLiving.isPotionActive(ItemRegistry.vomitusPotion)) {
		if (event.entityLiving.getActivePotionEffect(ItemRegistry.vomitusPotion).getDuration()==0) 
		{
			event.entityLiving.removePotionEffect(ItemRegistry.vomitusPotion.id);
		}
	}*/
	else if (event.getEntityLiving().worldObj.rand.nextInt(20) == 0)
	
//	{	event.entityLiving.attackEntityFrom(DamageSource.generic, -4);
	{	event.getEntityLiving().heal(4.0F);


	 return;
	}
	}
	}
	}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void playerSwim(EntityViewRenderEvent.FogColors event)
	{
		EntityPlayer player = (EntityPlayer)event.getEntity();
		//Entity entity = event.entity;
		World world = player.getEntityWorld();
		
		BlockPos pos = player.getPosition();
		IBlockState blockstate = world.getBlockState(pos);
		Block block = blockstate.getBlock();
//		System.out.println("Colored");


		if (block.equals(ModFluids.rud.getBlock()))//changes color of fog
		{		
		//	int red = 80, green = 13, blue = 147;

			int color = ((RudBlock) block).getFluid().getColor();
//			event.red = (color >> 56 & 255) / 255.0F;//not sure how the heck this adjusts color exactly
//			event.green = (color >> 8 & 255) / 255.0F;
//	        event.blue = (color & 255) / 255.0F;
			event.setRed(255F); 
			event.setBlue(0F); 
			event.setGreen(0F);
//			System.out.println("Reddish Colored");
		}
		
/*		else{
			
			event.red = (0F); 
			event.green = (0F); 
			event.blue = (0F);
			System.out.println("Black");
		}*/
	
	}	
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void playerSwim(FogDensity e)
	{
		EntityPlayer player = (EntityPlayer)e.getEntity();
		World w = player.getEntityWorld();
		BlockPos pos = player.getPosition();
		IBlockState bs = w.getBlockState(pos);
		Block b = bs.getBlock();
		if(b.equals(ModFluids.rud.getBlock()))
		{
			//float alpha = 34.0F; 
			e.setDensity(34/100.0F);
//			System.out.println("Fogged");
		
		}else 
	
		 e.setDensity(0.0F);   //turns off fog when not in Rudd

    e.setCanceled(true); // must be canceled to affect the fog density 
	}


}

	

