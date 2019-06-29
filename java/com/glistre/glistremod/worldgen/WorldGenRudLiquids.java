package com.glistre.glistremod.worldgen;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenLiquids;

public class WorldGenRudLiquids extends WorldGenLiquids
{
    private Block block;
 //   private static final String __OBFID = "CL_00000434";

    public WorldGenRudLiquids(Block block)
    {
    	super(block);
        this.block = block;
        
    }

    public boolean generate(World worldIn, Random rand, BlockPos blockpos)
    {
        if (worldIn.getBlockState(blockpos.up()).getBlock() != Blocks.STONE)
        {
            return false;
        }
        else if (worldIn.getBlockState(blockpos.down()).getBlock() != Blocks.STONE)
        {
            return false;
        }
        else if (worldIn.getBlockState(blockpos).getMaterial() != Material.AIR && worldIn.getBlockState(blockpos).getBlock() != Blocks.STONE)
        {
            return false;
        }
        else
        {
            int i = 0;

            if (worldIn.getBlockState(blockpos.west()).getBlock() == Blocks.STONE)
            {
                ++i;
            }

            if (worldIn.getBlockState(blockpos.east()).getBlock() == Blocks.STONE)
            {
                ++i;
            }

            if (worldIn.getBlockState(blockpos.north()).getBlock() == Blocks.STONE)
            {
                ++i;
            }

            if (worldIn.getBlockState(blockpos.south()).getBlock() == Blocks.STONE)
            {
                ++i;
            }

            int j = 0;

            if (worldIn.isAirBlock(blockpos.west()))
            {
                ++j;
            }

            if (worldIn.isAirBlock(blockpos.east()))
            {
                ++j;
            }

            if (worldIn.isAirBlock(blockpos.north()))
            {
                ++j;
            }

            if (worldIn.isAirBlock(blockpos.south()))
            {
                ++j;
            }

            if (i == 3 && j == 1)
            {
            	//forceBlockUpdateTick(this.block, blockpos, rand) changed 1.10.2 to immediateBlockTick
            	IBlockState iblockstate1 = this.block.getDefaultState();
                worldIn.setBlockState(blockpos, this.block.getDefaultState(), 2);
                worldIn.immediateBlockTick(blockpos, iblockstate1, rand);
            }

            return true;
        }
    }
}