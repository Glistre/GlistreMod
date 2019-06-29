package com.glistre.glistremod.items.swords;

import java.util.List;
import java.util.Random;

import com.glistre.glistremod.GlistreMod;
import com.glistre.glistremod.entities.blacktobie.EntityBlackTobo;
import com.glistre.glistremod.init.ItemRegistry;
import com.glistre.glistremod.reference.Reference;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
//import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;


public class SilverSword extends ItemSword {
    
    private String texturePath = "com.glistre.glistremod:";
    
    public SilverSword(Item ToolMaterial, String textureName)
    {
        super(GlistreMod.Silvers);
        this.setUnlocalizedName(textureName);
 //       this.setTextureName("SilverSword_1");
    }

/*@Override   
@SideOnly(Side.CLIENT)

    public void registerIcons(IIconRegister iconRegister)
    {
        this.itemIcon = iconRegister.registerIcon(Reference.MOD_ID +":" + "SilverSword_1");
    }*/
@Override
//@SideOnly(Side.CLIENT)
public void getSubItems(Item itemStack, CreativeTabs tab, List list){
		//bane of arthropods == 18
		ItemStack enchant01 = new ItemStack(ItemRegistry.silver_sword_1);
		enchant01.addEnchantment(Enchantment.getEnchantmentByID(18), 8);
		list.add(enchant01);
		}

@Override
//@SideOnly(Side.CLIENT)
/** Makes your Item Enchanted when it is crafted */
    public void onCreated(ItemStack item, World world, EntityPlayer player) 
    {
		//knockback == 19
        item.addEnchantment(Enchantment.getEnchantmentByID(19), 8);
        // Replace the "." after second "Enchantment" to see options
        // The number is the Enchantment Level
    }

@Override
public boolean hitEntity(ItemStack itemstack, EntityLivingBase target, EntityLivingBase attacker){

if (target instanceof EntityBlackTobo)
{
	target.setFire(7); //sets fire to Corrupted Black Tobie 7 seconds
	itemstack.damageItem(1, attacker);
//	target.attackEntityFrom(DamageSource.causeMobDamage(targetentity), (100));
//	target.attackEntityFrom(DamageSource.fall, 100);
}
else if (target instanceof EntityLivingBase){
	target.setFire(2); //sets fire to Corrupted Black Tobie 7 seconds
	itemstack.damageItem(1, attacker);
}
	return true;
}

//Makes silver sword disappear if you try to use it after EXACT specified time using the right click potion effect 
/*public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10)
    {
        if(player.getCurrentEquippedItem() != null)
        {

        	ItemStack currentItem = player.getCurrentEquippedItem();

        	player.addPotionEffect((new PotionEffect(GlistreMod.poisonProtectPotion.id, 10, 1, false))); //about 2:30 seconds sword will disappear when used

        	if (currentItem.stackSize == 1)
        	{
      		player.inventory.decrStackSize(player.inventory.currentItem, 1);

        	}
        	else 
        	{
        		ItemStack item=player.inventory.getStackInSlot(player.inventory.currentItem);
    		
        		player.inventory.decrStackSize(player.inventory.currentItem, 1);
    		
        		player.inventory.setInventorySlotContents(player.inventory.currentItem,item.copy());
        	}
        }
        return true;
    }*/
    
//@Override
/** Makes your Item Enchanted on startup */

/*public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
if (!stack.isItemEnchanted()){
	if(this == GlistreMod.MySword_1){
	
		stack.addEnchantment(Enchantment.smite, 8);
	}
}
	}*/

/*public boolean hitEntity(ItemStack stack, EntityLivingBase living, EntityLivingBase living2)
{
	stack.damageItem(1, living2);
	living.setFire(7);
	return true;
}*/

@Override
public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
{
        
//added this to add Poison Protection 
		playerIn.addPotionEffect(new PotionEffect(ItemRegistry.poison_protect_potion, 3000, 0, false, true));
//take away just damage if you add .damageitem subtracks 500 durability so thre uses of potion effect only, does not effect the non-potion durability decrement rate	
//		itemStack.stackSize -=7; that's for reducing stacks not damage, damageItem makes durability decrease when potion used
		itemStackIn.damageItem(498,  playerIn);


			  return new ActionResult(EnumActionResult.SUCCESS, itemStackIn); 
		
}
}