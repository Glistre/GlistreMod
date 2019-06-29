package com.glistre.glistremod.projectiles.blaster;

import java.util.List;

import com.glistre.glistremod.GlistreMod;
import com.glistre.glistremod.init.ItemRegistry;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
//import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
// import net.minecraft.entity.projectile.EntityArrow;

import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

import com.glistre.glistremod.projectiles.blaster.*;
import com.glistre.glistremod.projectiles.blaster.EntityEnderBoltFireball;
import com.glistre.glistremod.reference.Reference;

public class EnderGun extends Item{
//	private String texturePath = (Reference.MOD_ID + ":" + "enderman_gun_1");
	   public EnderGun(int par1) {
	       super();
	       this.setFull3D();
	       
	   }
	   @Override
	    /**
	     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	     */
	   public void onPlayerStoppedUsing(ItemStack itemStack, World world, EntityPlayer player, int usesRemaining)
	    {
	        int j = this.getMaxItemUseDuration(itemStack) - usesRemaining;

	        ArrowLooseEvent event = new ArrowLooseEvent(player, itemStack, j);
	        MinecraftForge.EVENT_BUS.post(event);
	        if (event.isCanceled())
	        {
	            return;
	        }
	        j = event.charge;

	        boolean flag = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, itemStack) > 0;

	        if (flag || player.inventory.hasItem(ItemRegistry.glistre_dust))
	        {
// change normal j / 20.0F to j/ 1.0F to make instant charge 5.0F = 5 ticks or 1/4 second	        	
	            float f = (float)j / 1.0F;
	            f = (f * f + f * 2.0F) / 3.0F;

	            if ((double)f < 0.1D)
	            {
	                return;
	            }

	            if (f > 1.0F)
	            {
	                f = 1.0F;
	            }

	            EntityEnderBoltFireball par1EntityEnderBoltFireball = new EntityEnderBoltFireball(world, player, f * 2.0F);

	            if (f == 1.0F)
	            {
	                par1EntityEnderBoltFireball.setIsCritical(true);
	            }

	            int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemStack);

	            if (k > 0)
	            {
	                par1EntityEnderBoltFireball.setDamage(par1EntityEnderBoltFireball.getDamage() + (double)k * 0.5D + 0.5D);
	            }

	            int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, itemStack);

	            if (l > 0)
	            {
	                par1EntityEnderBoltFireball.setKnockbackStrength(l);
	            }

	            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, itemStack) > 0)
	            {
	                par1EntityEnderBoltFireball.setFire(100);
	            }

	            itemStack.damageItem(1, player);
	            //this is the sound releasing from endergun but first line of same sound in EntityEnderBoltFireball will not sound without it, 
	            //the first float is volume 1.0F is usual depends on sound file
	            world.playSoundAtEntity(player, "glistremod:laser_blaster", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

	            if (flag)
	            {
	                par1EntityEnderBoltFireball.canBePickedUp = 2;
	            }
	            else
	            {
	                player.inventory.consumeInventoryItem(ItemRegistry.glistre_dust);
	            }
	          //took out the if ...isRemote to get blasterBolt to render
//	            if (!p_77615_2_.isRemote)
//	            {
	                world.spawnEntityInWorld(par1EntityEnderBoltFireball);
//	            }
	        }
	    }

	    /**
	     * returns the action that specifies what animation to play when the items is being used
	     */
	    public EnumAction getItemUseAction(ItemStack p_77661_1_)
	    {
	        return EnumAction.BOW;
	    }
	   
	    /**
	     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer, if you're in Survivor mode you 
	     * have to have an arrow in inventory
	     */
	    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
	    {
	    	ArrowNockEvent event = new ArrowNockEvent(player, itemStack);
	        MinecraftForge.EVENT_BUS.post(event);
	        if (event.isCanceled())
	        {
	            return event.result;
	        }

	        if (player.capabilities.isCreativeMode || player.inventory.hasItem(ItemRegistry.glistre_dust))
	        {
	            player.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
	        }
	        
	        //adds sound effect on ArrowKnock
	        
	        world.playSoundAtEntity(player, "glistremod:sceptre_1", 1.0F, 2.0F);

	        return itemStack;
	        
	    }
	
	        
	    public void onUsingItemTick(ItemStack stack, EntityPlayer player, int count)
	    {
	       this.setDamage(stack, 99999 - count);
	    }	       
	    /**
	     * How long it takes to use or consume an item
	     */
	    public int getMaxItemUseDuration(ItemStack par1ItemStack)
	    {
	        return 72000;
	    }

	    @Override
	    //adds appearance of enchantment
	    @SideOnly(Side.CLIENT)
	    public boolean hasEffect(ItemStack par1ItemStack)
	    {
		    return true;
	    } 


/*	   @Override   
	   @SideOnly(Side.CLIENT)

	       public void registerIcons(IIconRegister iconRegister)
	       {
	           this.itemIcon = iconRegister.registerIcon(texturePath);
	       }	*/   

	} 


