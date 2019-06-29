package com.glistre.glistremod.projectiles.tobyworstsword;

import com.glistre.glistremod.GlistreMod;
import com.glistre.glistremod.projectiles.tobyworstsword.TobyEntitySword;
import com.glistre.glistremod.tabs.TabRegistry;

//import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class TobyProjectile extends TobyEntitySword {
    
//    private String texturePath = "glistremod:";
    
    public TobyProjectile(Item ToolMaterial)
    {
        super(Item.ToolMaterial.IRON);
//        this.setUnlocalizedName(textureName);
//        texturePath += textureName;
    }



}