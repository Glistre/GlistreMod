package com.glistre.glistremod.blocks;

import java.util.Iterator;
import java.util.Random;

import com.glistre.glistremod.init.BlockRegistry;
import com.glistre.glistremod.worldgen.WorldGenGlistreMushroom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.IGrowable;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
//import net.minecraft.world.gen.feature.WorldGenBigMushroom;


public class BlockGlistreMushroom extends BlockBush implements IGrowable
{
//	public static final PropertyInteger VARIANT = PropertyInteger.create("variant", 0, 12);
    public static final PropertyBool NORTHWEST = PropertyBool.create("northwest");
    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool NORTHEAST = PropertyBool.create("northeast");
    public static final PropertyBool WEST = PropertyBool.create("west");
    public static final PropertyBool CENTER = PropertyBool.create("center");
    public static final PropertyBool SOUTHWEST = PropertyBool.create("southwest");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool SOUTHEAST = PropertyBool.create("southeast");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool STEM = PropertyBool.create("stem");
    public static final PropertyBool ALL_STEM = PropertyBool.create("all_stem");
    public static final PropertyBool ALL_OUTSIDE = PropertyBool.create("all_outside");
    public static final PropertyBool ALL_INSIDE = PropertyBool.create("all_inside");

    protected static final AxisAlignedBB GLISTRE_SHROOM_AABB = new AxisAlignedBB(0.30000001192092896D, 0.0D, 0.30000001192092896D, 0.699999988079071D, 0.4000000059604645D, 0.699999988079071D);

    
    public BlockGlistreMushroom()
    {
//        float f = 0.2F;
//        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f); replaced in 1.10.2 w/ override of getBoundingBox below
        this.setTickRandomly(true);
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
  //      return MUSHROOM_AABB;
    	  return GLISTRE_SHROOM_AABB;
    	
    }

    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (rand.nextInt(25) == 0)
        {
            int i = 5;
            boolean flag = true;
            Iterator iterator = BlockPos.getAllInBoxMutable(pos.add(-4, -1, -4), pos.add(4, 1, 4)).iterator();

            while (iterator.hasNext())
            {
                BlockPos blockpos1 = (BlockPos)iterator.next();

                if (worldIn.getBlockState(blockpos1).getBlock() == this)
                {
                    --i;

                    if (i <= 0)
                    {
                        return;
                    }
                }
            }

            BlockPos blockpos2 = pos.add(rand.nextInt(3) - 1, rand.nextInt(2) - rand.nextInt(2), rand.nextInt(3) - 1);

            for (int j = 0; j < 4; ++j)
            {
                if (worldIn.isAirBlock(blockpos2) && this.canBlockStay(worldIn, blockpos2, this.getDefaultState()))
                {
                    pos = blockpos2;
                }

                blockpos2 = pos.add(rand.nextInt(3) - 1, rand.nextInt(2) - rand.nextInt(2), rand.nextInt(3) - 1);
            }

            if (worldIn.isAirBlock(blockpos2) && this.canBlockStay(worldIn, blockpos2, this.getDefaultState()))
            {
                worldIn.setBlockState(blockpos2, this.getDefaultState(), 2);
            }
        }
    }

    @Override
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos) 
    { 
        return false; 
    }
    
 //removed # isOpaqueCube and #getCollisionBoundingBoxfor 1.10.2 does it still work?
 /*    @Override
    public boolean isOpaqueCube() {
        return true;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
        float f = 0.125F;
        return new AxisAlignedBB((double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), (double)(pos.getX() + 1), (double)((float)(pos.getY() + 1) - f), (double)(pos.getZ() + 1));

    }
  */  
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return super.canPlaceBlockAt(worldIn, pos) && this.canBlockStay(worldIn, pos, this.getDefaultState());
    }

    /**
     * Return true if the block can sustain a Bush
     */
    //1.10.2 changed canPlaceBlockOn to  canSustainBush
    protected boolean canSustainBush(IBlockState state)
    {
        return state.isFullBlock();
    }

    //1.10.2 added parameter iblockstate1 below
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state)
    {
        if (pos.getY() >= 0 && pos.getY() < 256)
        {
            IBlockState iblockstate1 = worldIn.getBlockState(pos.down());
            return iblockstate1.getBlock() == Blocks.MYCELIUM ? true : (iblockstate1.getBlock() == Blocks.DIRT && iblockstate1.getValue(BlockDirt.VARIANT) == BlockDirt.DirtType.PODZOL ? true : worldIn.getLight(pos) < 13 && iblockstate1.getBlock().canSustainPlant(iblockstate1, worldIn, pos.down(), net.minecraft.util.EnumFacing.UP, this));
        }
        else
        {
            return false;
        }
    }


    public boolean generateBigMushroom(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        worldIn.setBlockToAir(pos);
        WorldGenGlistreMushroom worldgenbigmushroom = null;

        //brown mushroom
        if (this == BlockRegistry.glistre_shroom_stripe_block)
        {
            worldgenbigmushroom = new WorldGenGlistreMushroom(BlockRegistry.shroom_dots_block);
        }
        //red mushroom
        else if (this == BlockRegistry.shroom_dots_block)
        {
            worldgenbigmushroom = new WorldGenGlistreMushroom(BlockRegistry.glistre_shroom_stripe_block);
        }

        if (worldgenbigmushroom != null && worldgenbigmushroom.generate(worldIn, rand, pos))
        {
            return true;
        }
        else
        {
            worldIn.setBlockState(pos, state, 3);
            return false;
        }
    }

    /**
     * Whether this IGrowable can grow
     */
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
    {
        return true;
    }

    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
        return (double)rand.nextFloat() < 0.4D;
    }

    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
        this.generateBigMushroom(worldIn, pos, state, rand);
    }


}