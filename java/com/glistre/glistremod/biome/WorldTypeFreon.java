package com.glistre.glistremod.biome;

import net.minecraft.world.WorldType;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerBiome;
import net.minecraft.world.gen.layer.GenLayerBiomeEdge;
import net.minecraft.world.gen.layer.GenLayerZoom;

public class WorldTypeFreon extends WorldType{

	public WorldTypeFreon(int par1, String name) {
		super(name);
		
	}
    /**
     * Creates the GenLayerBiome used for generating the world
     *
     * @param worldSeed The world seed
     * @param parentLayer The parent layer to feed into any layer you return
     * @return A GenLayer that will return ints representing the Biomes to be generated, see GenLayerBiome
     */
	
  @Override
  public GenLayer getBiomeLayer(long worldSeed, GenLayer parentLayer, String chunkProviderSettingsJson)
 
  //1.8 adds String chunkProviderSettingsJson parameter
  //public GenLayer getBiomeLayer(long worldSeed, GenLayer parentLayer, )
    {
        GenLayer ret = new GlistreGenLayerBiome(200L, parentLayer, this);
        ret = GenLayerZoom.magnify(1000L, ret, 2);
        ret = new GenLayerBiomeEdge(1000L, ret);
        return ret;
    }
  
  


}
