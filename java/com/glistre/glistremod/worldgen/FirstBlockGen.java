package com.glistre.glistremod.worldgen;

import java.util.Random;

import com.glistre.glistremod.biome.GlistreBiome;
import com.glistre.glistremod.init.BiomeRegistry;
import com.glistre.glistremod.init.BlockRegistry;

import net.minecraft.block.state.IBlockState;
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

public class FirstBlockGen extends WorldGenerator implements net.minecraftforge.fml.common.IWorldGenerator {

	@Override
    public void generate(Random random, int chunkX, int chunkZ,  World worldIn, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        switch(worldIn.provider.getDimension())
        {
        //case -1 = Nether
            case -1: 
                generateSurface(random, chunkX*16,chunkZ*16, worldIn, chunkGenerator, chunkProvider);
                break;
        //case 0 = Overworld
            case 0:
                generateSurface(random, chunkX*16,chunkZ*16, worldIn, chunkGenerator, chunkProvider);
            	break;
        //case 1 = End
            case 1:
                generateSurface(random, chunkX*16,chunkZ*16, worldIn, chunkGenerator, chunkProvider);
            	break;         	
        //default case e.g. Mystcraft ..others
            default:
                generateSurface(random, chunkX*16,chunkZ*16, worldIn, chunkGenerator, chunkProvider);
            	break;
        }              
    }

 
    private void generateSurface(Random random, int chunkX, int chunkZ, World worldIn, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider ) {

    	for(int i =0; i<20; i++){ 
    		Biome b = worldIn.getBiomeForCoordsBody(new BlockPos(chunkX, 0, chunkZ));   		
            int firstBlockXcoord = chunkX + random.nextInt(16);
            int firstBlockZcoord = chunkZ + random.nextInt(16);
            int OreY = random.nextInt(256); 
            int Ycoord = (worldIn.getHeight() - 40) + 40;   

            
            BlockPos YcoordPos = new BlockPos(firstBlockXcoord, Ycoord, firstBlockZcoord);
            BlockPos pos0 = new BlockPos(firstBlockXcoord, OreY, firstBlockZcoord);
            if (b == BiomeRegistry.biomeFreon || b == BiomeRegistry.biomeGlistre || b == Biomes.TAIGA)
         		
                    // WorldGenMinable(IBlockState, int, Predicate)
                    (new WorldGenMinable(BlockRegistry.silver_ore_1.getDefaultState(), 32)).generate(worldIn, random, pos0);
    	}      
   
//    	if (b.biomeName.equals("Glistering Biome") ||  b.biomeName.equals("Taiga"))
    	for(int i =0; i<10; i++){ 
    		Biome b = worldIn.getBiomeForCoordsBody(new BlockPos(chunkX, 0, chunkZ));   		
            int firstBlockXcoord = chunkX + random.nextInt(16);
            int firstBlockZcoord = chunkZ + random.nextInt(16);
            int OreY = random.nextInt(256); 
            int Ycoord = (worldIn.getHeight() - 40) + 40;   
  
            BlockPos YcoordPos = new BlockPos(firstBlockXcoord, Ycoord, firstBlockZcoord);
            BlockPos pos0 = new BlockPos(firstBlockXcoord, OreY, firstBlockZcoord);
            if (b == BiomeRegistry.biomeFreon || b == BiomeRegistry.biomeGlistre)         		
                    // WorldGenMinable(IBlockState, int, Predicate)
                    (new WorldGenMinable(BlockRegistry.enchanted_block_1.getDefaultState(), 6)).generate(worldIn, random, pos0);
    	} 
    	
//   creates massive spikes everywhere
   	for(int i =0; i<4; i++){ 
    		Biome b = worldIn.getBiomeForCoordsBody(new BlockPos(chunkX, 0, chunkZ));   		
            int firstBlockXcoord = chunkX + random.nextInt(16);
            int firstBlockZcoord = chunkZ + random.nextInt(16);
            int OreY = random.nextInt(60) + 8; 
            
            int Ycoord = (worldIn.getHeight() - 40) + 40;   
            BlockPos YcoordPos = new BlockPos(firstBlockXcoord, Ycoord, firstBlockZcoord);
            BlockPos pos0 = new BlockPos(firstBlockXcoord, OreY, firstBlockZcoord);
            IBlockState state0 = BlockRegistry.liquid_ice.getDefaultState();
            if (b == BiomeRegistry.biomeFreon)  {       		
                    // WorldGenMinable(IBlockState, int, Predicate)
                if(worldIn.getBlockState(pos0).getBlock() == Blocks.SNOW  || worldIn.getBlockState(pos0).getBlock() == Blocks.GRASS || worldIn.getBlockState(pos0).getBlock() == Blocks.SAND)
              	worldIn.setBlockState(pos0, state0);
                new WorldGenFreonSpikes().generate(worldIn, random, pos0);  	
//is generating ?
    //            System.out.println("Generating Packed Ice in Freon Biome");		
            }
    	}

        
    }


	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		
		return false;
	}

}   
    

