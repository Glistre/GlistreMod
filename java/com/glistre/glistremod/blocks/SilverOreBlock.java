package com.glistre.glistremod.blocks;

import java.util.Random;

import com.glistre.glistremod.entities.blacktobie.EntityBlackTobo;
import com.glistre.glistremod.entities.king.EntityTobieKing;
import com.glistre.glistremod.entities.queen.EntityTobieQueen;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
//import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;

public class SilverOreBlock extends Block {
	public String blockName;
//    private String texturePath = ("com.glistremod.glistre:");  
    private int thisBlockID;
    
    public SilverOreBlock (Material blockMaterial, int harvestLevel) {
        
        super(Material.ROCK);
 //       this.blockName = blockName;
 //       this.setBlockName(blockName);
        this.setUnlocalizedName(blockName);
//        this.setBlockTextureName(GlistreMod.MODID +":" + blockName);
 //       this.setCreativeTab(CreativeTabs.tabBlock);
       this.setHarvestLevel("pickaxe", 2);
       this.blockSoundType = (SoundType.STONE);
    }
    
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

/*    public void registerBlockIcons(IIconRegister iconRegister)
    {
        this.blockIcon = iconRegister.registerIcon(GlistreMod.MODID + ":" + blockName);
    }*/
    
}

