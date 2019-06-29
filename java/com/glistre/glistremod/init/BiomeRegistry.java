package com.glistre.glistremod.init;

import com.glistre.glistremod.biome.FreonBiome;
import com.glistre.glistremod.biome.GlistreBiome;
import com.glistre.glistremod.lib.Defaults;

import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.common.BiomeDictionary.Type;



public class BiomeRegistry {

	public static void GlistreMod(){
		initializeBiome();
		registerBiome();
	}
  
	public static Biome biomeGlistre;
	public static Biome biomeFreon;
//	public static int biomeGlistreID = 155;
//	public static int biomeFreonID = 154;
	
	public static void initializeBiome(){
	
// 1.7.10 valid biome IDs use 0-39, 129-167, neg one unallocated		
      biomeGlistre = new GlistreBiome(new BiomeProperties("Glistering Biome").setSnowEnabled().setTemperature(1.0F).setRainfall(.75F).setBaseHeight(.45F).setHeightVariation(.75F));
      Biome.registerBiome(Defaults.DIM_ID.GLISTRE, "Glistering Biome", biomeGlistre);
      biomeFreon = new FreonBiome(new BiomeProperties("Freon Biome").setWaterColor(0x9CFF00).setSnowEnabled().setTemperature(0.25F).setRainfall(1.25F).setBaseHeight(.75F).setHeightVariation(1.5F));
      Biome.registerBiome(Defaults.DIM_ID.FREON, "Freon Biome", biomeFreon);


    }	

	public static void registerBiome(){
		BiomeDictionary.registerBiomeType(biomeGlistre, Type.FOREST);
		BiomeDictionary.registerBiomeType(biomeFreon, Type.COLD);
//change the next line to make biome more frequent, 50 is 50% probability of spawning, change to 10 later
		//		BiomeManager.addSpawnBiome(biomeGlistre);
		BiomeEntry glistreBiomeEntry = new BiomeEntry(biomeGlistre, 30);
		BiomeEntry freonBiomeEntry = new BiomeEntry(biomeFreon, 30);
//need to try with .COOL it did not seem to work
		BiomeManager.addBiome(BiomeType.WARM, glistreBiomeEntry);
		BiomeManager.addBiome(BiomeType.WARM, freonBiomeEntry);
	}

}