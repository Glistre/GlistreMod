package com.glistre.glistremod.world;

import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.DimensionManager;

import com.glistre.glistremod.init.BiomeRegistry;
import com.glistre.glistremod.init.DimensionRegistry;
import com.glistre.glistremod.lib.Defaults;
//import com.glistre.glistremod.world.GlistreWorldChunkManager;

public class WorldProviderTobyKing extends WorldProvider{

//	private GlistreWorldChunkManager worldChunkMgr;
//	this.worldChunkMgr = new GlistreWorldChunkManager(this.worldObj);
//	private WorldType terrainType = new WorldTypeFreon("Freon Biome");
    /**
     * associate an existing world with a World provider
     */
/*	@Override
    public final void registerWorld(World worldIn)
    {
        this.worldObj = worldIn;
        this.terrainType = worldIn.getWorldInfo().getTerrainType();
        this.generatorSettings = worldIn.getWorldInfo().getGeneratorOptions();
        this.registerWorldChunkManager();
        this.generateLightBrightnessTable();
    }*/

//	public void registerWorldChunkManager(){  // changed 1.10.2 to createBiomeProvider
		/**
		 * creates a new world chunk manager for WorldProvider
		 */
		public void createBiomeProvider(){
		this.biomeProvider = new BiomeProviderSingle(BiomeRegistry.biomeFreon);
//		this.worldChunkMgr = new GlistreWorldChunkManager(this.worldObj);

		//		worldObj.provider. = Defaults.DIM_ID.FREON;
//		this.dimensionId = DimensionRegistry.dimensionId;
//		this.dimensionId = 8;
		this.setDimension(Defaults.DIM_ID.FREON);
	}
	
/*	@Override
	public static WorldProvider getProviderForDimension(int id)
	{
		return DimensionManager.createProviderFor(Defaults.DIM_ID.FREON);
	}*/
	
	@Override
	public boolean canRespawnHere() { 
		return false; 
	}
	
/*	@Override
	public IChunkProvider createChunkGeneration(){
		
		return null;
//		return  new GlistreWorldChunkManager(worldObj, worldObj.getSeed(), true);
	}*/
	
/*	@Override
	public String getDimensionName() {
		
		return "Freon";
	}

	@Override
	public String getInternalNameSuffix() {
		
		return "Freon";
	}*/
	@Override
	 public String getWelcomeMessage()
	    {
		
		return "Entering Ice Dimension";
	 
	    }

	@Override
	public DimensionType getDimensionType() {
		
		return DimensionType.OVERWORLD;
	}

}
