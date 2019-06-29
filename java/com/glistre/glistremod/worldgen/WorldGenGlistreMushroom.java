package com.glistre.glistremod.worldgen;

import java.util.Random;

import com.glistre.glistremod.blocks.BlockHugeGlistreMushroom;
import com.glistre.glistremod.init.BlockRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHugeMushroom;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenGlistreMushroom extends WorldGenerator
{
    /** The mushroom type. 0 for brown, 1 for red. */
    private Block mushroomType;

    public WorldGenGlistreMushroom(Block p_i46449_1_)
    {
        super(true);
        this.mushroomType = p_i46449_1_;
    }

    public WorldGenGlistreMushroom()
    {
        super(false);
    }

    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        if (this.mushroomType == null)
        {
            this.mushroomType = rand.nextBoolean() ? BlockRegistry.shroom_dots_block : BlockRegistry.glistre_shroom_stripe_block;
        }

        int i = rand.nextInt(3) + 4;
        boolean flag = true;

        if (position.getY() >= 1 && position.getY() + i + 1 < 256)
        {
            for (int j = position.getY(); j <= position.getY() + 1 + i; ++j)
            {
                int k = 3;

                if (j <= position.getY() + 3)
                {
                    k = 0;
                }

                BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

                for (int l = position.getX() - k; l <= position.getX() + k && flag; ++l)
                {
                    for (int i1 = position.getZ() - k; i1 <= position.getZ() + k && flag; ++i1)
                    {
                        if (j >= 0 && j < 256)
                        {
                            IBlockState state = worldIn.getBlockState(blockpos$mutableblockpos.setPos(l, j, i1));

                            if (!state.getBlock().isAir(state, worldIn, blockpos$mutableblockpos) && !state.getBlock().isLeaves(state, worldIn, blockpos$mutableblockpos))
                            {
                                flag = false;
                            }
                        }
                        else
                        {
                            flag = false;
                        }
                    }
                }
            }

            if (!flag)
            {
                return false;
            }
            else
            {
                Block block1 = worldIn.getBlockState(position.down()).getBlock();

                if (block1 != Blocks.DIRT && block1 != Blocks.GRASS && block1 != Blocks.MYCELIUM)
                {
                    return false;
                }
                else
                {
                    int k2 = position.getY() + i;

                    if (this.mushroomType == BlockRegistry.glistre_shroom_stripe_block)
                    {
                        k2 = position.getY() + i - 3;
                    }

                    for (int l2 = k2; l2 <= position.getY() + i; ++l2)
                    {
                        int j3 = 1;

                        if (l2 < position.getY() + i)
                        {
                            ++j3;
                        }

                        if (this.mushroomType == BlockRegistry.shroom_dots_block)
                        {
                            j3 = 3;
                        }

                        int k3 = position.getX() - j3;
                        int l3 = position.getX() + j3;
                        int j1 = position.getZ() - j3;
                        int k1 = position.getZ() + j3;

                        for (int l1 = k3; l1 <= l3; ++l1)
                        {
                            for (int i2 = j1; i2 <= k1; ++i2)
                            {
                                int j2 = 5;

                                if (l1 == k3)
                                {
                                    --j2;
                                }
                                else if (l1 == l3)
                                {
                                    ++j2;
                                }

                                if (i2 == j1)
                                {
                                    j2 -= 3;
                                }
                                else if (i2 == k1)
                                {
                                    j2 += 3;
                                }

                                BlockHugeGlistreMushroom.EnumType blockhugeglistremushroom$enumtype = BlockHugeGlistreMushroom.EnumType.byMetadata(j2);

                                if (this.mushroomType == BlockRegistry.shroom_dots_block || l2 < position.getY() + i)
                                {
                                    if ((l1 == k3 || l1 == l3) && (i2 == j1 || i2 == k1))
                                    {
                                        continue;
                                    }

                                    if (l1 == position.getX() - (j3 - 1) && i2 == j1)
                                    {
                                        blockhugeglistremushroom$enumtype = BlockHugeGlistreMushroom.EnumType.NORTH_WEST;
                                    }

                                    if (l1 == k3 && i2 == position.getZ() - (j3 - 1))
                                    {
                                    	blockhugeglistremushroom$enumtype = BlockHugeGlistreMushroom.EnumType.NORTH_WEST;
                                    }

                                    if (l1 == position.getX() + (j3 - 1) && i2 == j1)
                                    {
                                    	blockhugeglistremushroom$enumtype = BlockHugeGlistreMushroom.EnumType.NORTH_EAST;
                                    }

                                    if (l1 == l3 && i2 == position.getZ() - (j3 - 1))
                                    {
                                    	blockhugeglistremushroom$enumtype = BlockHugeGlistreMushroom.EnumType.NORTH_EAST;
                                    }

                                    if (l1 == position.getX() - (j3 - 1) && i2 == k1)
                                    {
                                    	blockhugeglistremushroom$enumtype = BlockHugeGlistreMushroom.EnumType.SOUTH_WEST;
                                    }

                                    if (l1 == k3 && i2 == position.getZ() + (j3 - 1))
                                    {
                                    	blockhugeglistremushroom$enumtype = BlockHugeGlistreMushroom.EnumType.SOUTH_WEST;
                                    }

                                    if (l1 == position.getX() + (j3 - 1) && i2 == k1)
                                    {
                                    	blockhugeglistremushroom$enumtype = BlockHugeGlistreMushroom.EnumType.SOUTH_EAST;
                                    }

                                    if (l1 == l3 && i2 == position.getZ() + (j3 - 1))
                                    {
                                    	blockhugeglistremushroom$enumtype = BlockHugeGlistreMushroom.EnumType.SOUTH_EAST;
                                    }
                                }

                                if (blockhugeglistremushroom$enumtype == BlockHugeGlistreMushroom.EnumType.CENTER && l2 < position.getY() + i)
                                {
                                	blockhugeglistremushroom$enumtype = BlockHugeGlistreMushroom.EnumType.ALL_INSIDE;
                                }

                                if (position.getY() >= position.getY() + i - 1 || blockhugeglistremushroom$enumtype != BlockHugeGlistreMushroom.EnumType.ALL_INSIDE)
                                {
                                    BlockPos blockpos = new BlockPos(l1, l2, i2);
                                    IBlockState state = worldIn.getBlockState(blockpos);

                                    if (state.getBlock().canBeReplacedByLeaves(state, worldIn, blockpos))
                                    {
                                        this.setBlockAndNotifyAdequately(worldIn, blockpos, this.mushroomType.getDefaultState().withProperty(BlockHugeGlistreMushroom.VARIANT, blockhugeglistremushroom$enumtype));
                                    }
                                }
                            }
                        }
                    }

                    for (int i3 = 0; i3 < i; ++i3)
                    {
                        BlockPos upN = position.up(i3);
                        IBlockState state = worldIn.getBlockState(upN);

                        if (state.getBlock().canBeReplacedByLeaves(state, worldIn, upN))
                        {
                            this.setBlockAndNotifyAdequately(worldIn, position.up(i3), this.mushroomType.getDefaultState().withProperty(BlockHugeGlistreMushroom.VARIANT, BlockHugeGlistreMushroom.EnumType.STEM));
                        }
                    }

                    return true;
                }
            }
        }
        else
        {
            return false;
        }
    }
}