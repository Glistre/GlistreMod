package com.glistre.glistremod.worldgen;

import com.glistre.glistremod.init.ItemRegistry;
import com.glistre.glistremod.lib.ConfigurationGlistre;

import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class WorldGen {
	
	public static void initWorldGen(){
		if(!ConfigurationGlistre.enableWorldGeneration){
//	       	GameRegistry.registerWorldGenerator(new MyBlockGen(), 10);
//	       	GameRegistry.registerWorldGenerator(new BlockGenGlistre(), 8);
//	       	GameRegistry.registerWorldGenerator(new BlockGenSavanna(), 8);
			;
		}
		
		else {
		
			//generator, weight
	       	GameRegistry.registerWorldGenerator(new FirstBlockGen(), 18);
	       	GameRegistry.registerWorldGenerator(new BlockGenGlistre(), 8);
	       	GameRegistry.registerWorldGenerator(new BlockGenSavanna(), 8);
	       	GameRegistry.registerWorldGenerator(new BlockGenMesaGold(), 10);
	       	GameRegistry.registerWorldGenerator(new EmeraldGenerator(), 6);
	       	GameRegistry.registerWorldGenerator(new TobyKingTowerGen(), 18);
	       	GameRegistry.registerWorldGenerator(new CabinChestGen(), 30);
	       	//turned off FluidGen created too much lag and blood color really creeps me out
//	       	GameRegistry.registerWorldGenerator(new FluidGen(), -1);

//	       	GameRegistry.registerWorldGenerator((IWorldGenerator) new WorldGenFreonSpikes(), 1);
	       	
		}
		
	}
}
