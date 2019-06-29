package com.glistre.glistremod.items;

import com.glistre.glistremod.GlistreMod;
import com.glistre.glistremod.reference.Reference;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.item.Item;
import net.minecraft.creativetab.CreativeTabs;
//import net.minecraft.client.renderer.texture.IIconRegister;

public class GlistreDust extends Item {
    
 


    public GlistreDust(int Item, String textureName)
    {
        super();
        this.setUnlocalizedName(textureName);
//        this.setTextureName("Glistre_Dust");
        this.setCreativeTab(CreativeTabs.MATERIALS);

    }


}