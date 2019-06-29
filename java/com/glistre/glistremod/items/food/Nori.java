package com.glistre.glistremod.items.food;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
//import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemFood;

public class Nori extends ItemFood {
	
	  public String textureName;

//	  private String texturePath = ("com.glistre.glistremod.items:" + textureName);
	    
	    public Nori(int healamount, Float saturationModifier, boolean isWolfsFavoriteMeat) 
	    {
	        super(healamount, saturationModifier, isWolfsFavoriteMeat);
	        this.setUnlocalizedName(textureName);
//	        this.setTextureName("nori");
	    }
	    
/*@Override
@SideOnly(Side.CLIENT)
	     public void registerIcons(IIconRegister iconRegister)
	        {
	         this.itemIcon = iconRegister.registerIcon(texturePath);
	        }*/
	        
	   
}
