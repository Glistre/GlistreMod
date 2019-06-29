package com.glistre.glistremod.items.swords;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Stack;
 

import com.glistre.glistremod.GlistreMod;
import com.glistre.glistremod.entities.EntityIcePotionBall;
import com.glistre.glistremod.entities.wolf.EntityGlistreWolf;
import com.glistre.glistremod.init.GlistreEntityRegistry;
import com.glistre.glistremod.init.ItemRegistry;
import com.glistre.glistremod.reference.Reference;

//import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class GlistreSword extends ItemSword {
    
 //   private String texturePath = "com.glistre.glistremod:";
    
    public GlistreSword(Item ToolMaterial, String textureName)
    {
        super(GlistreMod.Glistres);
        this.setUnlocalizedName(textureName);
//        this.setTextureName("glistre_sword");
//        this.setCreativeTab(CreativeTabs.tabCombat);
        
        
    }

 @Override   
 public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
{
	stack.damageItem(1, attacker);
	target.setFire(2);
	return true;
}

 /*  @Override
    public void getSubItems(Item item, CreativeTabs tab, List subItems) {
	   ItemStack egg = new ItemStack(Items.spawn_egg, 1);
	   ItemMonsterPlacer.getEntityName(egg);
 
//You might have to add NBT Data to the stack...
        subItems.add(egg);
    }*/


//next method not adding anything since using displayAllRelevantItems
//@SideOnly(Side.CLIENT)
/*@Override
public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List list){
		
		ItemStack enchant = new ItemStack(ItemRegistry.Glistre_Sword);
		enchant.addEnchantment(Enchantment.sharpness, 5);
		list.add(enchant);
		}*/

@Override
public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
{

        
//added this to add Poison Protection 
		playerIn.addPotionEffect(new PotionEffect(ItemRegistry.poison_protect_potion, 3000, 0, false, true));
//take away just damage if you add .damageitem subtracks 500 durability so thre uses of potion effect only, does not effect the non-potion durability decrement rate	
//		itemStack.stackSize -=7; that's for reducing stacks not damage, damageItem makes durability decrease when potion used
		itemStackIn.damageItem(494,  playerIn);
		//adds particle effects to Item
		/*for(int countparticles = 0; countparticles <= 80; ++countparticles)
		{
			par2World.spawnParticle("mobSpell", par3EntityPlayer.posX + (rand.nextDouble() - 0.5D) * (double)par3EntityPlayer.width, par3EntityPlayer.posY + rand.nextDouble() * (double)par3EntityPlayer.height - (double)par3EntityPlayer.yOffset, par3EntityPlayer.posZ + (rand.nextDouble() - 0.5D) * (double)par3EntityPlayer.width, 0, 0, 0);

		}*/
		Random rand = new Random();
		
			for(int countparticles = 0; countparticles <= 80; ++countparticles)
			{
				//changed yOffest to getYoffset 1.8
				worldIn.spawnParticle(EnumParticleTypes.REDSTONE, playerIn.posX + (rand.nextDouble() - 0.5D) * (double)playerIn.width, playerIn.posY + rand.nextDouble() * (double)playerIn.height - (double)playerIn.getYOffset(), playerIn.posZ + (rand.nextDouble() - 0.5D) * (double)playerIn.width, 255, 215, 0, new int[0]);
			}

			  return new ActionResult(EnumActionResult.SUCCESS, itemStackIn); 
		
}

/*public static void main (String args[]){
		GlistreSword enchant = new GlistreSword();
				enchant.getSubItems(Item p_150895_1_, Creative Tabs p_150895_2_, List list);
;}*/


//super(CreativeTabs.tabMISC)
/*	list.add(new ItemStack(par1, 1, 0));

}
//list.add(new ItemStack(par1, 1, 0));}*/
       	  //	ItemStack string = new ItemStack(Items.string);
//       string.addEnchantment(Enchantment.silkTouch, 1);
	


//        list.add(par1, 1);   	
//    list.add(new ItemStack(par1, 1, 0));
//    for(int i = 0; i < 15; ++i){
//                       list.add(new ItemStack(itemID, 1, i));}

/** Adds Lightning Power to your Sword */

   /** public boolean hitEntity(ItemStack item, EntityLivingBase target, EntityLivingBase player)
 //   {
 //     target.worldObj.addWeatherEffect(new EntityLightningBolt(target.worldObj, target.posX, target.posY, target.posZ));
 //       return true;
//    }*/

@Override
//@SideOnly(Side.CLIENT)
/** Makes your Item Enchanted when it is crafted */
    public void onCreated(ItemStack item, World world, EntityPlayer player) 
    {
		//20==fireaspect
        item.addEnchantment(Enchantment.getEnchantmentByID(20), 3);
        // Replace the "." after second "Enchantment" to see options
        // The number is the Enchantment Level
    }
}

