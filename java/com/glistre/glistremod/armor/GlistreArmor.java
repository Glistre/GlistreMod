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

public class GlistreArmor extends ItemArmor {


    
	//1.8.9 constructor was (String name, ArmorMaterial, int renderIndex, int slot)
	public GlistreArmor (ItemArmor.ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
		//material, renderIndex, equiptmentSlot
		super(materialIn, renderIndexIn, equipmentSlotIn);
		this.setCreativeTab(CreativeTabs.COMBAT);
	}
			
/*			@Override
		    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
	    	
			if (stack.getItem() == ItemRegistry.glistre_helmet_1 || stack.getItem() == ItemRegistry.glistre_chestplate_1 || stack.getItem() == ItemRegistry.glistre_boots_1){
	  
	    		return Reference.MOD_ID + ":" + "textures/armor/glistrearmor_layer_1.png";
	  
	    	}
	    	if (stack.getItem() == ItemRegistry.glistre_leggings_1) {
	 
	    		return Reference.MOD_ID + ":" + "textures/armor/glistrearmor_layer_2.png";

	    	}

	    	else return null;
	    }*/
		    
		    //set the amplifier to -1 to get rid of the swirling particle effects
		    @Override
		    public void onArmorTick(World world, EntityPlayer player, ItemStack armor) {
		    	//16 night vision, moveSpeed 1, waterbreathing 13
		    	if(armor.getItem() == ItemRegistry.glistre_helmet_1) {
			        player.addPotionEffect(new PotionEffect(Potion.getPotionById(16), 1000, -1));
			        player.addPotionEffect(new PotionEffect(Potion.getPotionById(13), 1000, -1));
		    	}
		    	if(armor.getItem() == ItemRegistry.glistre_boots_1) {
			        player.addPotionEffect(new PotionEffect(Potion.getPotionById(1), 1000, -1));
		    	
		    	//		     player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 500, 4));
		    	}		    
		     
		 }
		    
/*		    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		    	if (!stack.isItemEnchanted()){
		    		if(stack.getItem() == ItemRegistry.GlistreChestplate_1 || stack.getItem() == ItemRegistry.GlistreLeggings_1){
		    			stack.addEnchantment(Enchantment.fireProtection, 4);	
		    				
		    		}
		    		if(stack.getItem() == ItemRegistry.GlistreHelmet_1){
		    			stack.addEnchantment(Enchantment.aquaAffinity, 3);
		    			stack.addEnchantment(Enchantment.fireProtection, 4);
		    		}
		    		if (stack.getItem() == ItemRegistry.GlistreBoots_1) {
		    			stack.addEnchantment(Enchantment.fireProtection, 4);
//lure IV
//		    			stack.addEnchantment(Enchantment.field_151369_A, 4);
//luck of the sea III
//		    			stack.addEnchantment(Enchantment.field_151370_z, 3);	
		    		}	    			    	
		    	}
		    }*/

		    
/*@SideOnly(Side.CLIENT)
@Override
    public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List list){

    		ItemStack enchant0 = new ItemStack(this);
    		enchant0.addEnchantment(Enchantment.fireProtection, 4);
    		list.add(enchant0);

    		    		
	}*/
	    		
}