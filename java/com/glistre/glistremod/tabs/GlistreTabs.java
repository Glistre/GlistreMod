package com.glistre.glistremod.tabs;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import java.util.Iterator;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;


public class GlistreTabs extends CreativeTabs
{
    public GlistreTabs(String unlocalizedName) {
		super(unlocalizedName);

	}

	@Override
	public Item getTabIconItem() {
		
		return null;
	}

	@Override
	public void displayAllRelevantItems(List itemList) {
		
		super.displayAllRelevantItems(itemList);
	}

}