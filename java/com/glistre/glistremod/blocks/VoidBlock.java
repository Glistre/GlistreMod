package com.glistre.glistremod.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class VoidBlock extends Block {

    private String texturePath = "com.glistre.glistremod:";  
    private int thisBlockID;
	public String blockName;
    public VoidBlock (Material blockMaterial, int harvestLevel) {
        
        super(Material.ROCK);
        this.setUnlocalizedName(blockName);
        this.setHarvestLevel("pickaxe", 3);
        this.setSoundType(SoundType.METAL);

    }
    
 /*   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state)
    {
//        float f = .01F;
  //      return new AxisAlignedBB((double)((float)pos.getX() + f), (double)pos.getY(), (double)((float)pos.getZ() + f), (double)((float)(pos.getX() + 1) - f), (double)((float)(pos.getY() + 1) - f), (double)((float)(pos.getZ() + 1) - f));
        float f = 0.125F;
        return new AxisAlignedBB((double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), (double)(pos.getX() + 1), (double)((float)(pos.getY() + 1) - f), (double)(pos.getZ() + 1));

    }
    
    @Override
 	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
 	   
// 	   super.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);

 	   	((EntityPlayer)entityIn).attackEntityFrom(DamageSource.generic, 10.0F);
    }*/

    public int idDropped(int par1, Random par2Random, int par3)
    {
        return thisBlockID;
    }
    
    public int quantityDropped(Random random)
    {
        return 1;
    }


    
}

