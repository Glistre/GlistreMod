package com.glistre.glistremod.init;

import com.glistre.glistremod.*;
import com.glistre.glistremod.tabs.TabRegistry;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ArmorRegistry {
	
	public static void GlistreMod(){
		init();
		register();
	}

	public static void init(){
		
	}
	
	public static void register(){
		
	}
	
	public static void registerRenders(){
		
	}
	
/*	public static void registerItem(Item item){
		item.setCreativeTab(TabRegistry.glistre_tab_1);
		GameRegistry.register(item);
		Utils.getLogger().info("Registered item: " + item.getUnlocalizedName().substring(5));
	}*/
	
	public static void registerRender(Item item){
	 	ModelLoader.setCustomModelResourceLocation(item, 0 , new ModelResourceLocation(item.getRegistryName(), "inventory"));
		GlistreMod.log.info("Register render for : " + item.getUnlocalizedName().substring(5));

	}

}
