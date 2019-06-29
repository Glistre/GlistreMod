package com.glistre.glistremod.worldgen;

import java.util.Random;

import com.glistre.glistremod.biome.GlistreBiome;
import com.glistre.glistremod.init.BiomeRegistry;
import com.glistre.glistremod.init.BlockRegistry;
import com.google.common.base.Predicate;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class EmeraldGenerator extends WorldGenerator implements net.minecraftforge.fml.common.IWorldGenerator {

    private final IBlockState oreBlock;
    /** The number of blocks to generate. */
    private final int emeraldsPerChunk;
  //  private final Predicate<IBlockState> predicate;// this is for the #isReplaceableOre method

    public EmeraldGenerator()
    {
        this.oreBlock = Blocks.EMERALD_ORE.getDefaultState();
        this.emeraldsPerChunk = 20;
   //     this.predicate = BlockHelper.forBlock(BlockRegistry.silver_ore_1);
    }
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

 
    private void generateSurface(Random rand, int chunkX, int chunkZ, World worldIn, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider ) {

//    	if (b.biomeName.equals("Glistering Biome") ||  b.biomeName.equals("Taiga"))
    	for(int i =0; i<emeraldsPerChunk; i++){ 
    		Biome b = worldIn.getBiomeForCoordsBody(new BlockPos(chunkX, 0, chunkZ));   		
            int firstBlockXcoord = chunkX + rand.nextInt(16);
            int firstBlockZcoord = chunkZ + rand.nextInt(16);
            int OreY = rand.nextInt(256); //was 256 not (28) + 4
            int Ycoord = (worldIn.getHeight() - 40) + 40;   
  
            BlockPos pos0 = new BlockPos(firstBlockXcoord, OreY, firstBlockZcoord);
       //     if (b == BiomeRegistry.biomeFreon || b == BiomeRegistry.biomeGlistre){  
            	//third par is Predicate <IblockState>target //BlockHelper.forBlock will take that specific block and replace
          /*     if (worldIn.getBlockState(pos0).getBlock().isReplaceableOreGen(worldIn, pos0, BlockHelper.forBlock(BlockRegistry.silver_ore_1)))       		
                {
                	//last argument to setBlockState is a bit flag that tells Minecraft whether or not to update the client side / notify neighboring blocks / other things, and you almost always want it set to either 2 (notify client) or 3 (notify client AND notify neighbors, IIRC).
                   this.setBlockAndNotifyAdequately(worldIn, pos0.add(firstBlockXcoord, OreY, firstBlockZcoord), Blocks.emerald_block.getDefaultState());//adds one emerald block in silver ore
 
            	   worldIn.setBlockState(pos0, (IBlockState) this.oreBlock, 2); 
                }
            
            for (int k = 0; k < 100; ++k)
            {
                int secondBlockCoordX = chunkX + rand.nextInt(16);
                int secondBlockCoordZ = chunkZ + rand.nextInt(16);

                int OreY1 = rand.nextInt(64);
                BlockPos emeraldpos = new BlockPos(secondBlockCoordX, OreY1, secondBlockCoordZ);
                new EmeraldGenerator().generate(worldIn, rand, pos0.add(emeraldpos));
            }*/
            
	            if (b == BiomeRegistry.biomeGlistre)         		
	                // WorldGenMinable(IBlockState, int, Predicate)
	                (new WorldGenMinable(Blocks.EMERALD_ORE.getDefaultState(), 20)).generate(worldIn, rand, pos0);
    	}
	                    // WorldGenMinable(IBlockState, int, Predicate)*/
    		for(int i =0; i<emeraldsPerChunk; i++){ 
    		Biome b = worldIn.getBiomeForCoordsBody(new BlockPos(chunkX, 0, chunkZ));   		
            int firstBlockXcoord = chunkX + rand.nextInt(16);
            int firstBlockZcoord = chunkZ + rand.nextInt(16);
            int OreY = rand.nextInt(256); //was 256 not (28) + 4
            int Ycoord = (worldIn.getHeight() - 40) + 40;   
  
            BlockPos pos0 = new BlockPos(firstBlockXcoord, OreY, firstBlockZcoord);
	            if (b == BiomeRegistry.biomeFreon )         		
	                // WorldGenMinable(IBlockState, int, Predicate)
	                (new WorldGenMinable(Blocks.EMERALD_ORE.getDefaultState(), 10)).generate(worldIn, rand, pos0);
	
	                    // WorldGenMinable(IBlockState, int, Predicate)*/
	            
            
    	} 
    	

    }


	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		// TODO Auto-generated method stub
		return false;
	}

}   
    

