package com.glistre.glistremod.lib;

import com.glistre.glistremod.GlistreMod;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.common.config.Configuration;

public class ConfigurationGlistre  {
	
	public static boolean secretRecipe;

	//ore attempt
	public static boolean enableWorldGeneration;
		
	//ID attempt
	public static int biomeGlistreID;
	public static int biomeFreonID;
	public static int dimensionId;

//  public static int myConfigInteger = 32;
//  public static String myConfigString = "Thank you for playing Glistre Mod!";
//  public static boolean myConfigBool = false;
	
	public static final boolean SECRETRECIPE_DEFAULT = false;
	public static final String SECRETRECIPE_NAME = "Enables the cheap recipes found in Recipe Book";
	public static final boolean WORLDSETTINGS_DEFAULT = true;
	public static final String WORLD_SETTINGS = "Allows custom ore to generate in the world.";
	
	public static final String CATEGORY_BIOME = "Change the ID of the Biomes";
	public static final String CATEGORY_DIMENSION = "Change the ID of the Dimension";
		
	
	public static void syncConfig(){
		FMLCommonHandler.instance().bus().register(GlistreMod.instance);
		  
		// recipes
		final String RECIPES = GlistreMod.config.CATEGORY_GENERAL + GlistreMod.config.CATEGORY_SPLITTER + ("Recipes");
		GlistreMod.config.addCustomCategoryComment(RECIPES, "Enable or disable recipes");
		secretRecipe = GlistreMod.config.get(RECIPES, SECRETRECIPE_NAME, SECRETRECIPE_DEFAULT).getBoolean(SECRETRECIPE_DEFAULT);
		
		// world settings
		final String WORLDGEN = GlistreMod.config.CATEGORY_GENERAL + GlistreMod.config.CATEGORY_SPLITTER + ("World Settings");
		GlistreMod.config.addCustomCategoryComment(WORLDGEN, "Enable or disable world settings");
		enableWorldGeneration = GlistreMod.config.get(WORLDGEN, WORLD_SETTINGS, WORLDSETTINGS_DEFAULT).getBoolean(WORLDSETTINGS_DEFAULT);
		
		final String CATEGORY_BIOME = GlistreMod.config.CATEGORY_GENERAL + GlistreMod.config.CATEGORY_SPLITTER + ("Biomes");
		GlistreMod.config.addCustomCategoryComment(CATEGORY_BIOME, "ID of the Biomes");
		biomeGlistreID = GlistreMod.config.get(CATEGORY_BIOME, ("Glistering Biome ID"), 155).getInt();  
		biomeFreonID = GlistreMod.config.get(CATEGORY_BIOME, ("Freon Biome ID"), 154).getInt();  

		final String CATEGORY_DIMENSION = GlistreMod.config.CATEGORY_GENERAL + GlistreMod.config.CATEGORY_SPLITTER + "Dimensions";
		GlistreMod.config.addCustomCategoryComment(CATEGORY_DIMENSION, "ID of the Dimension");
		dimensionId = GlistreMod.config.get(CATEGORY_DIMENSION, ("Freon Dimension ID"), Defaults.DIM_ID.FREON).getInt();  
		dimensionId = GlistreMod.config.get(CATEGORY_DIMENSION, ("Glistre Dimension ID"), Defaults.DIM_ID.GLISTRE).getInt();  


//		biomeGlistreID = GlistreMod.config.getInt("Glistering Biome ID", Configuration.CATEGORY_GENERAL, biomeGlistreID, 155, Integer.MAX_VALUE, "An Integer!");

//	    myConfigString = GlistreMod.config.getString("My Config String", Configuration.CATEGORY_GENERAL, myConfigString, "A String!");
//	    myConfigBool = config.getBoolean("My Config Bool", Configuration.CATEGORY_GENERAL, myConfigBool, "A Boolean!");


		if(GlistreMod.config.hasChanged()){
//			GlistreMod.config.get(RECIPES, SECRETRECIPE_NAME, SECRETRECIPE_CHANGED).getBoolean(SECRETRECIPE_CHANGED);
//			ConfigurationGlistre.loadConfig();
			
			GlistreMod.config.save();
		}
	}
}
	

