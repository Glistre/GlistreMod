package com.glistre.glistremod.world;

import net.minecraft.init.Biomes;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderHell;
import net.minecraftforge.common.DimensionManager;

import java.util.Arrays;

import com.glistre.glistremod.chunkprovider.GlistreChunkProvider;
import com.glistre.glistremod.init.BiomeRegistry;
import com.glistre.glistremod.lib.Defaults;

//import com.glistre.glistremod.world.GlistreWorldChunkManager;

public class WorldProviderGlistre extends WorldProvider{

//	public World worldObj;
//	private float rainfall;
	
private String generatorSettings;

@Override
//	public void registerWorldChunkManager(){changed 1.10.2 to createBiomeProvider
/**
 * creates a new world chunk manager for WorldProvider
 */
public void createBiomeProvider(){

//		this.worldChunkMgr = new GlistreWorldChunkManager(this.worldObj);
//		this.worldChunkMgr = new WorldProviderHell(BiomeRegistry.biomeGlistre, 1.0F);  changed to next line 1.10.2
		this.biomeProvider = new BiomeProviderSingle(BiomeRegistry.biomeGlistre);
//		this.isHellWorld = false;
//		this.worldChunkMgr.getRainfall(1.0F,  x, z, width, length);
//		this.worldChunkMgr = new WorldChunkManagerHell(BiomeRegistry.biomeFreon, .25F);
//		worldObj.provider.dimensionId = Defaults.DIM_ID.GLISTRE;
//		this.hasNoSky = false;
//		this.dimensionId = DimensionRegistry.dimensionId;
		this.setDimension(Defaults.DIM_ID.GLISTRE);
//		this.rainfall = 1.0F;
//		this.hasNoSky = false;
		

	}

@Override
// third parameter was this.worldObj.getWorldInfo().isMapFeaturesEnabled()
public IChunkGenerator createChunkGenerator(){
	//return new GlistreChunkProvider(this.worldObj, this.getSeed(), true, this.worldObj.getWorldInfo().getGeneratorOptions()); changed 1.10.2
	return new GlistreChunkProvider(this.worldObj, this.worldObj.getWorldInfo().isMapFeaturesEnabled(), this.worldObj.getSeed(), "Glistre");
  //  return (IChunkGenerator)(this.terrainType == WorldType.FLAT ? new ChunkProviderFlat(this.worldObj, this.worldObj.getSeed(), this.worldObj.getWorldInfo().isMapFeaturesEnabled(), this.generatorSettings) : (this.terrainType == WorldType.DEBUG_WORLD ? new ChunkProviderDebug(this.worldObj) : (this.terrainType == WorldType.CUSTOMIZED ? new ChunkProviderOverworld(this.worldObj, this.worldObj.getSeed(), this.worldObj.getWorldInfo().isMapFeaturesEnabled(), this.generatorSettings) : new ChunkProviderOverworld(this.worldObj, this.worldObj.getSeed(), this.worldObj.getWorldInfo().isMapFeaturesEnabled(), this.generatorSettings))));

}

	
/*@Override
public void resetRainAndThunder(){
	worldObj.getWorldInfo().setRainTime(0);
	worldObj.getWorldInfo().setRaining(true);
	worldObj.getWorldInfo().setThunderTime(0);
	worldObj.getWorldInfo().setThundering(false);
}*/
/*
@Override
	public boolean getHasNoSky {
	return false;
}*/
/*	@Override
	public static WorldProvider getProviderForDimension(int id)
	{
		return DimensionManager.createProviderFor(Defaults.DIM_ID.GLISTRE);
	}*/
	
	@Override
		public boolean isSurfaceWorld() {
			return true;
	}
	
	@Override	
		public boolean canRespawnHere() { 
			return true; 
		}
/*
@Override	
	public boolean canDoRainSnowIce(Chunk chunk) {
		return true;
	}
	
	public IChunkProvider createChunkGeneration(){
		return null;
	}
@Override    
	public boolean canSnowAt(int x, int y, int z, boolean checkLight)
    {
        return worldObj.canSnowAtBody(x, y, z, false);
    }
@Override
    public boolean canDoLightning(Chunk chunk)
    {
        return true;
    }
@Override 
public void resetRainAndThunder()
{
    worldObj.getWorldInfo().setRainTime(0);
    worldObj.getWorldInfo().setRaining(true);
    worldObj.getWorldInfo().setThunderTime(0);
    worldObj.getWorldInfo().setThundering(true);
}*/
	@Override
    public String getWelcomeMessage() {
    
	if (this instanceof WorldProviderGlistre){
		return "Entering the Glistering Biome!";
	}
	else {
		return null;
		
    }
	}
/*	@Override
	public String getDimensionName() {
		
		return "Glistre";
	}
	@Override
	public String getInternalNameSuffix() {
		
		return "Glistre";
	}*/

	@Override
	public DimensionType getDimensionType() {
		
		return DimensionType.OVERWORLD;

	}

}
