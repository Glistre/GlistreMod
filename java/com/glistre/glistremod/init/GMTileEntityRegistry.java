package com.glistre.glistremod.init;

//import com.glistre.glistremod.init.BlockRegistry;
import com.glistre.glistremod.reference.Reference;
import com.glistre.glistremod.tileentity.TileEntityGlistreChest;
import com.glistre.glistremod.tileentity.TileEntityGlistreChestGold;

//import net.minecraft.client.resources.model.ModelResourceLocation;
//import net.minecraft.item.Item;
//import net.minecraftforge.client.ForgeHooksClient;
//import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class GMTileEntityRegistry {

	public static void GlistreMod(){
//		initializeTileEntity();
		registerTileEntity();
	}	

//	public static void initializeTileEntity(){
		
//	}
	
	public static void registerTileEntity(){
		GameRegistry.registerTileEntity(TileEntityGlistreChest.class, "glistre_chest");
		GameRegistry.registerTileEntity(TileEntityGlistreChestGold.class, "glistre_chest_gold");

	}
}