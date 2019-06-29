package com.glistre.glistremod.biome;

import net.minecraft.world.WorldType;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerBiome;
import net.minecraft.world.gen.layer.GenLayerBiomeEdge;
import net.minecraft.world.gen.layer.GenLayerZoom;

public class WorldTypeGlistre extends WorldType{

	public WorldTypeGlistre(int par1, String name) {
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
//	public GenLayer getBiomeLayer(long worldSeed, GenLayer parentLayer)
    {
        GenLayer ret = new GlistreGenLayerBiome(200L, parentLayer, this);
        ret = GenLayerZoom.magnify(1000L, ret, 2);
        ret = new GenLayerBiomeEdge(1000L, ret);
        return ret;
    }

  /*    public BiomeGenBase[] getBiomesForWorldType() {
  return biomesForWorldType;
}

public void addNewBiome(BiomeGenBase biome)
{
  Set<BiomeGenBase> newBiomesForWorld = Sets.newLinkedHashSet(Arrays.asList(biomesForWorldType));
  newBiomesForWorld.add(biome);
 biomesForWorldType = newBiomesForWorld.toArray(new BiomeGenBase[0]);
}

public void removeBiome(BiomeGenBase biome)
{
  Set<BiomeGenBase> newBiomesForWorld = Sets.newLinkedHashSet(Arrays.asList(biomesForWorldType));
  newBiomesForWorld.remove(biome);
  biomesForWorldType = newBiomesForWorld.toArray(new BiomeGenBase[0]);
}
*/
}
