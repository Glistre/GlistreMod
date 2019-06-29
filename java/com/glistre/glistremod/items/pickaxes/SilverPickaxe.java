package com.glistre.glistremod.items.pickaxes;

import java.util.List;
import java.util.Random;

import com.glistre.glistremod.GlistreMod;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemPickaxe;
//import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;


public class SilverPickaxe extends ItemPickaxe {
    
    public String textureName; 
    
    public SilverPickaxe(Item ToolMaterial, String textureName)
    {
        super(GlistreMod.Silvers);
        this.setUnlocalizedName(textureName);
 //       this.setTextureName(textureName);
        
    }
 /*   @Override   
    @SideOnly(Side.CLIENT)

        public void registerIcons(IIconRegister iconRegister)
        {
            this.itemIcon = iconRegister.registerIcon(texturePath);
        }  */




    
   


    
    @Override
//    @SideOnly(Side.CLIENT)
    public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List list){

    		
    		}

 


}