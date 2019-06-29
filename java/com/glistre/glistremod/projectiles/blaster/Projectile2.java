package com.glistre.glistremod.projectiles.blaster;


import com.glistre.glistremod.GlistreMod;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemSword;
import net.minecraft.creativetab.CreativeTabs;
//import net.minecraft.client.renderer.texture.IIconRegister;

public class Projectile2 extends ItemSword {
    
//	private String texturePath = ("glistremod:");
//	public String textureName;
    

    public Projectile2(Item ToolMaterial, String textureName){
        super(GlistreMod.Silvers);
        this.setUnlocalizedName(textureName);
        
	}



}
