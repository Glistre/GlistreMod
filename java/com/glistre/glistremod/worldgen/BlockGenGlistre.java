package com.glistre.glistremod.worldgen;

import java.util.Random;

import com.glistre.glistremod.init.BiomeRegistry;
import com.glistre.glistremod.init.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;


public class BlockGenGlistre implements IWorldGenerator {
	
	private WorldGenerator glistre_nether;
	private WorldGenerator glistre_overworld;
	private WorldGenerator glistre_end;
	
	public void WorldGen() {
		glistre_overworld = new WorldGenMinable(BlockRegistry.silver_ore_1.getDefaultState(), 8);
	}

	@Override
    public void generate(Random random, int chunkX, int chunkZ,  World worldIn, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        switch(worldIn.provider.getDimension())
        {
        //case -1 = Nether
        
            case -1: 
                runGenerator(glistre_nether, worldIn, random, chunkX*16, chunkZ*16, 20, 0, 64);
                break;
        //case 0 = Overworld
            case 0:
            	runGenerator(glistre_overworld, worldIn, random, chunkX*16,chunkZ*16, 100, 0, 64);
            	break;
        //case 1 = End
            case 1:
            	runGenerator(glistre_end, worldIn, random, chunkX*16,chunkZ*16, 20, 0, 64);
            	break;         	
        //default case e.g. Mystcraft ..others
 //           default:
 //           	this.runGenerator(generator, worldIn, random, chunkX, chunkZ, chancesToSpawn, minHeight, maxHeight);
 //           	break;
        }              
    }
	//generateSurface renamed runGenerator 1.10.2
    private void runGenerator(WorldGenerator generator, World worldIn, Random random, int chunkX, int chunkZ, int chancesToSpawn, int minHeight, int maxHeight) {
    
    if (minHeight < 0 || maxHeight > 256 || minHeight > maxHeight)
    	throw new IllegalArgumentException("MInimum or Maximum Height out of bounds");
    	
    	int heightDiff = maxHeight - minHeight + 1;
    	
    	for(int i =0; i< chancesToSpawn; i++){ 
    		Biome b = worldIn.getBiome(new BlockPos(chunkX, 0, chunkZ));   		
            int firstBlockXcoord = chunkX * 16 + random.nextInt(16);
            int Ycoord = minHeight + random.nextInt(heightDiff);
            int firstBlockZcoord = chunkZ *16 + random.nextInt(16);
            //int OreY = random.nextInt(256); 
            //int Ycoord = (worldIn.getHeight() - 40) + 40;   
            BlockPos YcoordPos = new BlockPos(firstBlockXcoord, Ycoord, firstBlockZcoord);
            //BlockPos pos0 = new BlockPos(firstBlockXcoord, OreY, firstBlockZcoord);
         	if (b == BiomeRegistry.biomeFreon || b == Biomes.BIRCH_FOREST || b == Biomes.BIRCH_FOREST_HILLS)
         		
         			generator.generate(worldIn, random, YcoordPos);////TODDO :: ERRORS npe
                    // WorldGenMinable(IBlockState, int, Predicate)
                  //1.10.2 removed  (new WorldGenMinable(BlockRegistry.silver_block_1.getDefaultState(), 12)).generate(worldIn, random, pos0);
                  //this below just tells me if its generating or not
                   System.out.println("Generating Silver Blocks in Freon & Birch Forest Biomes");
         		}
    	
 		for(int i =0; i<10; i++){ 
            Biome b = worldIn.getBiome(new BlockPos(chunkX, 0, chunkZ)); 	
            int firstBlockXcoord = chunkX + random.nextInt(16);
            int firstBlockZcoord = chunkZ + random.nextInt(16);
            int OreY = random.nextInt(256);
            int Ycoord = (worldIn.getHeight() - 40) + 40;   
            BlockPos YcoordPos = new BlockPos(firstBlockXcoord, Ycoord, firstBlockZcoord);         
            BlockPos pos0 = new BlockPos(firstBlockXcoord, OreY, firstBlockZcoord);
          
         	 if (b == Biomes.EXTREME_HILLS || b == Biomes.EXTREME_HILLS_EDGE || b == Biomes.EXTREME_HILLS_WITH_TREES || b == Biomes.STONE_BEACH)
 
                	
//                    int Ycoord = world.getHeightValue(Xcoord, Zcoord);
                    (new WorldGenMinable(BlockRegistry.silver_ore_1.getDefaultState(), 20)).generate(worldIn, random, pos0);
                  //this below just tells me if its generating or not
                //    System.out.println("Generating Silver Ore in ExtremeHills Biome");
                   
    	}
         
    }

    
}




    

   
    

