package com.glistre.glistremod.items.pickaxes;

import java.util.List;

import com.glistre.glistremod.GlistreMod;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemPickaxe;
//import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;


public class SparksPickaxe extends ItemPickaxe {
    
    private String texturePath = ("glistremod:");
    
    public SparksPickaxe(Item ToolMaterial, String textureName)
    {
        super(GlistreMod.Sparks);
        this.setUnlocalizedName(textureName);
 //       this.setTextureName("Glistre_Pickaxe2");
        texturePath += textureName;
    }
 /*   @Override   
    @SideOnly(Side.CLIENT)

        public void registerIcons(IIconRegister iconRegister)
        {
            this.itemIcon = iconRegister.registerIcon(texturePath);
        }  */
    
    
//next method not adding anything since using displayAllRelevantItems in TabRegistry    
/*    @Override
 //   @SideOnly(Side.CLIENT)
    public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List list){

    	ItemStack enchant04 = new ItemStack(ItemRegistry.Glistre_Pickaxe2);
    	enchant03.addEnchantment(Enchantment.efficiency, 4);
    		list.add(enchant04);
    		}*/





}