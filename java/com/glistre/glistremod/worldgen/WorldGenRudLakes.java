package com.glistre.glistremod.worldgen;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenRudLakes extends WorldGenLakes
{
    private Block block;


    public WorldGenRudLakes(Block block)
    {
    	super(block);
        this.block = block;
    }

    public boolean generate(World worldIn, Random rand, BlockPos blockpos)
    {
        for (blockpos = blockpos.add(-8, 0, -8); blockpos.getY() > 5 && worldIn.isAirBlock(blockpos); blockpos = blockpos.down())
        {
            ;
        }

        if (blockpos.getY() <= 4)
        {
            return false;
        }
        else
        {
            blockpos = blockpos.down(4);
            boolean[] aboolean = new boolean[2048];
            int i = rand.nextInt(4) + 4;
            int j;

            for (j = 0; j < i; ++j)
            {
                double d0 = rand.nextDouble() * 6.0D + 3.0D;
                double d1 = rand.nextDouble() * 4.0D + 2.0D;
                double d2 = rand.nextDouble() * 6.0D + 3.0D;
                double d3 = rand.nextDouble() * (16.0D - d0 - 2.0D) + 1.0D + d0 / 2.0D;
                double d4 = rand.nextDouble() * (8.0D - d1 - 4.0D) + 2.0D + d1 / 2.0D;
                double d5 = rand.nextDouble() * (16.0D - d2 - 2.0D) + 1.0D + d2 / 2.0D;

                for (int l = 1; l < 15; ++l)
                {
                    for (int i1 = 1; i1 < 15; ++i1)
                    {
                        for (int j1 = 1; j1 < 7; ++j1)
                        {
                            double d6 = ((double)l - d3) / (d0 / 2.0D);
                            double d7 = ((double)j1 - d4) / (d1 / 2.0D);
                            double d8 = ((double)i1 - d5) / (d2 / 2.0D);
                            double d9 = d6 * d6 + d7 * d7 + d8 * d8;

                            if (d9 < 1.0D)
                            {
                                aboolean[(l * 16 + i1) * 8 + j1] = true;
                            }
                        }
                    }
                }
            }

            int k;
            int k1;
            boolean flag;

            for (j = 0; j < 16; ++j)
            {
                for (k1 = 0; k1 < 16; ++k1)
                {
                    for (k = 0; k < 8; ++k)
                    {
                        flag = !aboolean[(j * 16 + k1) * 8 + k] && (j < 15 && aboolean[((j + 1) * 16 + k1) * 8 + k] || j > 0 && aboolean[((j - 1) * 16 + k1) * 8 + k] || k1 < 15 && aboolean[(j * 16 + k1 + 1) * 8 + k] || k1 > 0 && aboolean[(j * 16 + (k1 - 1)) * 8 + k] || k < 7 && aboolean[(j * 16 + k1) * 8 + k + 1] || k > 0 && aboolean[(j * 16 + k1) * 8 + (k - 1)]);

                        if (flag)
                        {
                        	//1.10.2 removed getBlock() before getMaterial()
                            Material material = worldIn.getBlockState(blockpos.add(j, k, k1)).getMaterial();

                            if (k >= 4 && material.isLiquid())
                            {
                                return false;
                            }

                            if (k < 4 && !material.isSolid() && worldIn.getBlockState(blockpos.add(j, k, k1)).getBlock() != this.block)
                            {
                                return false;
                            }
                        }
                    }
                }
            }

            for (j = 0; j < 16; ++j)
            {
                for (k1 = 0; k1 < 16; ++k1)
                {
                    for (k = 0; k < 8; ++k)
                    {
                        if (aboolean[(j * 16 + k1) * 8 + k])
                        {
                            worldIn.setBlockState(blockpos.add(j, k, k1), k >= 4 ? Blocks.AIR.getDefaultState() : this.block.getDefaultState(), 2);
                        }
                    }
                }
            }

            for (j = 0; j < 16; ++j)
            {
                for (k1 = 0; k1 < 16; ++k1)
                {
                    for (k = 4; k < 8; ++k)
                    {
                        if (aboolean[(j * 16 + k1) * 8 + k])
                        {
                            BlockPos blockpos1 = blockpos.add(j, k - 1, k1);

                            if (worldIn.getBlockState(blockpos1).getBlock() == Blocks.DIRT && worldIn.getLightFor(EnumSkyBlock.SKY, blockpos.add(j, k, k1)) > 0)
                            {
                            	//changed getBiomeGenForCoords 1.10.2 to getBiome
                                Biome biome = worldIn.getBiome(blockpos1);

                                if (biome.topBlock.getBlock() == Blocks.MYCELIUM)
                                {
                                    worldIn.setBlockState(blockpos1, Blocks.MYCELIUM.getDefaultState(), 2);
                                }
                                else
                                {
                                    worldIn.setBlockState(blockpos1, Blocks.GRASS.getDefaultState(), 2);
                                }
                            }
                        }
                    }
                }
            }
            //added getDefaultState 1.10.2
            if (this.block.getDefaultState().getMaterial() == Material.LAVA)
            {
                for (j = 0; j < 16; ++j)
                {
                    for (k1 = 0; k1 < 16; ++k1)
                    {
                        for (k = 0; k < 8; ++k)
                        {
                            flag = !aboolean[(j * 16 + k1) * 8 + k] && (j < 15 && aboolean[((j + 1) * 16 + k1) * 8 + k] || j > 0 && aboolean[((j - 1) * 16 + k1) * 8 + k] || k1 < 15 && aboolean[(j * 16 + k1 + 1) * 8 + k] || k1 > 0 && aboolean[(j * 16 + (k1 - 1)) * 8 + k] || k < 7 && aboolean[(j * 16 + k1) * 8 + k + 1] || k > 0 && aboolean[(j * 16 + k1) * 8 + (k - 1)]);

                            if (flag && (k < 4 || rand.nextInt(2) != 0) && worldIn.getBlockState(blockpos.add(j, k, k1)).getMaterial().isSolid())
                            {
                                worldIn.setBlockState(blockpos.add(j, k, k1), Blocks.STONE.getDefaultState(), 2);
                            }
                        }
                    }
                }
            }

            if (this.block.getDefaultState().getMaterial() == Material.WATER)
            {
                for (j = 0; j < 16; ++j)
                {
                    for (k1 = 0; k1 < 16; ++k1)
                    {
                        byte b0 = 4;
                        
                        //func_175675_v changed to #canBlockFreezeWater 1.8.9

                        if (worldIn.canBlockFreezeWater(blockpos.add(j, b0, k1)))
                        {
                            worldIn.setBlockState(blockpos.add(j, b0, k1), Blocks.ICE.getDefaultState(), 2);
                        }
                    }
                }
            }

            return true;
        }
    }
}