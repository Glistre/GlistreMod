package com.glistre.glistremod.worldgen;

import java.util.Random;

import com.glistre.glistremod.blocks.BlockSeaweed;
import com.glistre.glistremod.blocks.SilverOreBlock;
import com.glistre.glistremod.init.BiomeRegistry;
import com.glistre.glistremod.init.BlockRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenClay;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGenSeaweed extends WorldGenClay implements IWorldGenerator
{
    public BlockSeaweed fieldseaweed;
    private int metadata;
    /** The number of blocks to generate. */
    private int numberOfBlocks;
 //   private static final String __OBFID = "CL_00000405";

    public WorldGenSeaweed(int num)
    {
    	super(num);
        this.fieldseaweed = (BlockSeaweed) BlockRegistry.block_seaweed;
        this.numberOfBlocks = num;
//        this.numberOfBlocks = 16;
    }
    
	@Override
    public void generate(Random random, int chunkX, int chunkZ,  World worldIn, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        switch(worldIn.provider.getDimension())
        {
        //case -1 = Nether
            case -1: 
                generate(random, chunkX*16,chunkZ*16, worldIn, chunkGenerator, chunkProvider);
                break;
        //case 0 = Overworld
            case 0:
                generate(random, chunkX*16,chunkZ*16, worldIn, chunkGenerator, chunkProvider);
            	break;
        //case 1 = End
            case 1:
                generate(random, chunkX*16,chunkZ*16, worldIn, chunkGenerator, chunkProvider);
            	break;         	
        //default case e.g. Mystcraft ..others
            default:
                generate(random, chunkX*16,chunkZ*16, worldIn, chunkGenerator, chunkProvider);
            	break;
        }              
    }
	
	   private void generateSurface(Random random, int chunkX, int chunkZ, World worldIn, IChunkProvider chunkGenerator, IChunkProvider chunkProvider ) {
   		//.getBiomeGenForCoords replaced with getBiomeForCoordsBody 1.10.2
		   Biome b = worldIn.getBiomeForCoordsBody(new BlockPos(chunkX, 0, chunkZ));   		
           if (b == BiomeRegistry.biomeGlistre)
           {

//	    	if (b.biomeName.equals("Glistering Biome") ||  b.biomeName.equals("Taiga"))
	    	for(int i =0; i<10; i++){ 
	            int firstBlockXcoord = chunkX + random.nextInt(16);
	            int firstBlockZcoord = chunkZ + random.nextInt(16);
	            int OreY = random.nextInt(256); 
	            int Ycoord = (worldIn.getHeight() - 20) + 20;   
	  
	            BlockPos YcoordPos = new BlockPos(firstBlockXcoord, Ycoord, firstBlockZcoord);
	            BlockPos pos0 = new BlockPos(firstBlockXcoord, OreY, firstBlockZcoord);
	            IBlockState state = worldIn.getBlockState(pos0);//added 1.10.2
		        if (worldIn.getBlockState(pos0).getMaterial() != Material.WATER)
		        {
   //isReplaceableOreGen changed params 1.10.2 changed to isReplaceable not sure if that will work
		        	//BlockHelper renamed to BlockMatcher 1.10.2
	                if (state.getBlock().isReplaceableOreGen(state, worldIn, pos0, BlockMatcher.forBlock(BlockRegistry.block_seaweed)))       		
	                {
	                	//last argument to setBlockState is a bit flag that tells Minecraft whether or not to update the client side / notify neighboring blocks / other things, and you almost always want it set to either 2 (notify client) or 3 (notify client AND notify neighbors, IIRC).
	                    worldIn.setBlockState(pos0, (IBlockState) Blocks.CLAY.getDefaultState(), 2);
	                }
		       }
	    	}
           }
           }

/*		if (IceAndFire.CONFIG.generateSapphireOre) {
			if (BiomeDictionary.isBiomeOfType(world.getBiome(height), Type.SNOWY)) {
				int count = 3 + random.nextInt(6);
				for (int sapphireAmount = 0; sapphireAmount < count; sapphireAmount++) {
					int oreHeight = random.nextInt(28) + 4;
					int xOre = (chunkX * 16) + random.nextInt(16);
					int zOre = (chunkZ * 16) + random.nextInt(16);
					BlockPos pos = new BlockPos(xOre, oreHeight, zOre);
					IBlockState state = world.getBlockState(pos);
					if (state.getBlock().isReplaceableOreGen(state, world, pos, BlockMatcher.forBlock(Blocks.STONE))) {
						world.setBlockState(pos, ModBlocks.sapphireOre.getDefaultState());*/
						
    public boolean generate(World worldIn, Random random, BlockPos blockPos)
    {
        if (worldIn.getBlockState(blockPos).getMaterial() != Material.WATER)
        {
            return false;
        }
        else
        {
            int l = random.nextInt(this.numberOfBlocks - 2) + 2;
            byte b0 = 1;

            for (int i1 = blockPos.getX() - l; i1 <= blockPos.getX() + l; ++i1)
            {
                for (int j1 = blockPos.getZ() - l; j1 <= blockPos.getZ() + l; ++j1)
                {
                    int k1 = i1 - blockPos.getX();
                    int l1 = j1 - blockPos.getZ();

                    if (k1 * k1 + l1 * l1 <= l * l)
                    {
                        for (int i2 = blockPos.getY() - b0; i2 <= blockPos.getY() + b0; ++i2)
                        {
                        	BlockPos blockPos1 = new BlockPos(i1, i2, j1);
                            Block block = worldIn.getBlockState(blockPos1).getBlock();

                            if (block == Blocks.DIRT || block == Blocks.SAND || block == Blocks.GRAVEL || block == Blocks.CLAY || block == BlockRegistry.silver_ore_1)
                            { 
                            	//not sure why the state adds 2 here for 1.8
                                worldIn.setBlockState(blockPos1, this.fieldseaweed.getDefaultState(), 2);
                            }
                        }
                    }
                }
            }

            return true;
        }
    }


}