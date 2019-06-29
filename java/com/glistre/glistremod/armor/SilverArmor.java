package com.glistre.glistremod.armor;

import java.util.List;

import com.glistre.glistremod.GlistreMod;
import com.glistre.glistremod.init.ItemRegistry;
import com.glistre.glistremod.reference.Reference;

//import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class SilverArmor extends ItemArmor {


    
	//1.8.9 constructor was (String name, ArmorMaterial, int renderIndex, int slot)
	public SilverArmor (ItemArmor.ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
		//material, renderIndex, equiptmentSlot
		super(materialIn, renderIndexIn, equipmentSlotIn);
		this.setCreativeTab(CreativeTabs.COMBAT);	

	}
/*			@Override
		    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
	    	
			if (stack.getItem() == ItemRegistry.silver_helmet_1 || stack.getItem() == ItemRegistry.silver_chestplate_1 || stack.getItem() == ItemRegistry.silver_boots_1){
	  
	    		return Reference.MOD_ID + ":" + "textures/armor/silverarmor_layer_1.png";
	  
	    	}
	    	if (stack.getItem() == ItemRegistry.silver_leggings_1) {
	 
	    		return Reference.MOD_ID + ":" + "textures/armor/silverarmor_layer_2.png";

	    	}

	    	else return null;
	    }*/
		    
/*		    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		    	if (!stack.isItemEnchanted()){
		    		if(stack.getItem() == ItemRegistry.SilverHelmet_1 || stack.getItem() == ItemRegistry.SilverChestplate_1 || stack.getItem() == ItemRegistry.SilverLeggings_1){
		    			stack.addEnchantment(Enchantment.protection, 4);	
		    				
		    		}	
		    		if (stack.getItem() == ItemRegistry.SilverBoots_1) {
		    			stack.addEnchantment(Enchantment.protection, 4);
		    			stack.addEnchantment(Enchantment.featherFalling, 4);
	    		
		    		}
		    			    	
		    	}
		    }*/
		    
		    //setting amplifier to -1 eliminates the particle effect swirl
		    @Override
		    public void onArmorTick(World world, EntityPlayer player, ItemStack armor) {
		     player.addPotionEffect(new PotionEffect(Potion.getPotionById(16), 500, -1));
		    }
		    
	/*	    @SideOnly(Side.CLIENT)
		    @Override
		        public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List list){

		        		ItemStack enchant0 = new ItemStack(this);
		        		enchant0.addEnchantment(Enchantment.fortune, 7);
		        		list.add(enchant0);
		        		    		
		    	}*/

		    /** Adds Enchantment glow to item */
		    /*@SideOnly(Side.CLIENT)
@Override
		    public boolean hasEffect (ItemStack par1ItemStack){

		    	return true;
		    }*/
}

