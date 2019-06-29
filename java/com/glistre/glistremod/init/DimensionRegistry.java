package com.glistre.glistremod.init;

import com.glistre.glistremod.world.WorldProviderGlistre;
import com.glistre.glistremod.world.WorldProviderTobyKing;
import com.glistre.glistremod.lib.Defaults;

import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class DimensionRegistry {
	
	public static void GlistreMod(){
		registerDimension();
	}
	
//	public static int dimensionId;
//	public static int dimensionId = 9;


	
	
	public static void registerDimension(){
		

		Defaults.DIM_ID.FREON = DimensionManager.getNextFreeDimId();
		//DimensionManager.registerProviderType(Defaults.DIM_ID.FREON, WorldProviderTobyKing.class, false);
		DimensionManager.registerDimension(Defaults.DIM_ID.FREON, DimensionType.OVERWORLD);

		Defaults.DIM_ID.GLISTRE = DimensionManager.getNextFreeDimId();		
//		DimensionManager.registerProviderType(Defaults.DIM_ID.GLISTRE, WorldProviderGlistre.class, true);
//		DimensionManager.registerDimension(Defaults.DIM_ID.GLISTRE, Defaults.DIM_ID.GLISTRE );		
		DimensionManager.registerDimension(Defaults.DIM_ID.GLISTRE, DimensionType.OVERWORLD);



		
	}

}