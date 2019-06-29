package com.glistre.glistremod.worldgen;

import java.util.Random;

import com.glistre.glistremod.blocks.fluids.ModFluids;
import com.glistre.glistremod.init.BiomeRegistry;
import com.glistre.glistremod.init.BlockRegistry;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;


public class FluidGen implements net.minecraftforge.fml.common.IWorldGenerator{

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

	    	for(int i =0; i<10; i++){ 
	      		Biome b = worldIn.getBiomeForCoordsBody(new BlockPos(chunkX, 0, chunkZ));   		
	            int firstBlockXcoord = chunkX + random.nextInt(16);
	            int firstBlockZcoord = chunkZ + random.nextInt(16);
	            int OreY = random.nextInt(92) + 8; 
	            
	      //      int Ycoord = (worldIn.getHeight() - 40) + 40;   
	        //    BlockPos YcoordPos = new BlockPos(firstBlockXcoord, Ycoord, firstBlockZcoord);
	            BlockPos pos0 = new BlockPos(firstBlockXcoord, OreY, firstBlockZcoord);
	            IBlockState state0 = ModFluids.rud_block.getDefaultState();
	            if (b == BiomeRegistry.biomeFreon)  {       		
	                    // WorldGenMinable(IBlockState, int, Predicate)
	                if(worldIn.getBlockState(pos0).getBlock() == Blocks.WATER)  
	                		//|| worldIn.getBlockState(pos0).getBlock() == Blocks.grass || worldIn.getBlockState(pos0).getBlock() == Blocks.sand)
	              	worldIn.setBlockState(pos0, state0);
	                new WorldGenRudLiquids(ModFluids.rud_block).generate(worldIn, random, pos0);  	
	        		//is generating ?
		          //    System.out.println("Generating Rud Liquid in Freon Biome");		
	            }	
	    	
		    	for(int i1 =0; i1<20; i1++){ 
		      		Biome b1 = worldIn.getBiomeForCoordsBody(new BlockPos(chunkX, 0, chunkZ));   		
		            int firstBlockXcoord2 = chunkX + random.nextInt(16) + 8;
		            int firstBlockZcoord2 = chunkZ + random.nextInt(16) + 8;
		            int OreY1 = random.nextInt(60) + 8; 
		            
		         //   int Ycoord2 = (worldIn.getHeight() - 40) + 40;   
		            //1.8 just changed OrY1 was YCoord2
		      //      BlockPos YcoordPos2 = new BlockPos(firstBlockXcoord2, OreY1, firstBlockZcoord2);
		            BlockPos pos1 = new BlockPos(firstBlockXcoord2, OreY1, firstBlockZcoord2);
		            IBlockState state1 = ModFluids.rud_block.getDefaultState();
		            if (b1 == BiomeRegistry.biomeFreon)  {       		
		                    // WorldGenMinable(IBlockState, int, Predicate)
		                if(worldIn.getBlockState(pos1).getBlock() == Blocks.WATER)  
		                		//|| worldIn.getBlockState(pos0).getBlock() == Blocks.grass || worldIn.getBlockState(pos0).getBlock() == Blocks.sand)
		              	worldIn.setBlockState(pos1, state1);
		                new WorldGenRudLakes(ModFluids.rud_block).generate(worldIn, random, pos1);  	
		//is generating ?
		    //          System.out.println("Generating Rud Lakes in Freon Biome");		
		            }

	    	}
		
	    }
	}

}
