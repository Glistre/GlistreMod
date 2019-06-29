package com.glistre.glistremod.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.World;

public class SolidOreBlock extends Block {

    private String texturePath = "com.glistre.glistremod:";  
    private int thisBlockID;
	public String blockName;
    public SolidOreBlock (Material blockMaterial, int harvestLevel) {
        
        super(Material.IRON);
//        this.setCreativeTab(CreativeTabs.tabBlock);
//        this.setBlockName(blockName);
        this.setUnlocalizedName(blockName);
 //       texturePath += textureName;
        this.setHarvestLevel("pickaxe", 3);
        this.blockSoundType = SoundType.METAL;
        this.setSoundType(SoundType.METAL);
    }
    
    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
/*    public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_)
    {
        return p_149742_1_.getBlock(p_149742_2_, p_149742_3_, p_149742_4_).isReplaceable(p_149742_1_, p_149742_2_, p_149742_3_, p_149742_4_);
    }*/
    
    @Override
    public Block setSoundType(SoundType sound){
    	return this;
    }

    public int idDropped(int par1, Random par2Random, int par3)
    {
        return thisBlockID;
    }
    
    public int quantityDropped(Random random)
    {
        return 1;
    }
    
}

