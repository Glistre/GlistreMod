package com.glistre.glistremod.projectiles.blaster;

import java.util.List;

import javax.annotation.Nullable;

import com.glistre.glistremod.GlistreMod;
import com.glistre.glistremod.init.ItemRegistry;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

import com.glistre.glistremod.projectiles.blaster.*;
import com.glistre.glistremod.reference.Reference;

public class Blaster extends ItemBow implements IItemPropertyGetter{

	public final String textureName;
	
	   public Blaster(int par1, String textureName) {
	       super();
	       this.textureName = textureName;
	       this.setUnlocalizedName(textureName);
	       this.setFull3D();
	       this.addPropertyOverride(new ResourceLocation("pull"), new IItemPropertyGetter()
	       {
	            @SideOnly(Side.CLIENT)
	            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
	            {
	                if (entityIn == null)
	                {
	                    return 0.0F;
	                }
	                else
	                {
	                    ItemStack itemstack = entityIn.getActiveItemStack();
	                    return itemstack != null && itemstack.getItem() == Items.BOW ? (float)(stack.getMaxItemUseDuration() - entityIn.getItemInUseCount()) / 20.0F : 0.0F;
	                }
	            }
	        });
	        this.addPropertyOverride(new ResourceLocation("pulling"), new IItemPropertyGetter()
	        {
	            @SideOnly(Side.CLIENT)
	            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
	            {
	                return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F : 0.0F;
	            }
	        });
	    }
	   private ItemStack findAmmo(EntityPlayer player)
	    {
	        if (this.isArrow(player.getHeldItem(EnumHand.OFF_HAND)))
	        {
	            return player.getHeldItem(EnumHand.OFF_HAND);
	        }
	        else if (this.isArrow(player.getHeldItem(EnumHand.MAIN_HAND)))
	        {
	            return player.getHeldItem(EnumHand.MAIN_HAND);
	        }
	        else
	        {
	            for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
	            {
	                ItemStack itemstack = player.inventory.getStackInSlot(i);

	                if (this.isArrow(itemstack))
	                {
	                    return itemstack;
	                }
	            }

	            return null;
	        }
	    }      
	  
	    /**
	     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	     */
	   
	    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
	    {
	        if (entityLiving instanceof EntityPlayer)
	        {
	            EntityPlayer entityplayer = (EntityPlayer)entityLiving;
	            boolean flag = entityplayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
	            ItemStack itemstack = this.findAmmo(entityplayer);

	            int i = this.getMaxItemUseDuration(stack) - timeLeft;
	            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, (EntityPlayer)entityLiving, i, itemstack != null || flag);
	            if (i < 0) return;

	            if (itemstack != null || flag)
	            {
	                if (itemstack == null)
	                {
	                    itemstack = new ItemStack(ItemRegistry.glistre_dust);
	                }

	                float f = getArrowVelocity(i);

	                if ((double)f >= 0.1D)
	                {
	                    boolean flag1 = entityplayer.capabilities.isCreativeMode || (itemstack.getItem() instanceof EntityBlasterBolt ? ((EntityBlasterBolt)itemstack.getItem()).isInfinite(itemstack, stack, entityplayer) : false);

	                    if (!worldIn.isRemote)
	                    {
	                        EntityBlasterBolt itemarrow = (EntityBlasterBolt)((EntityBlasterBolt)(itemstack.getItem() instanceof EntityBlasterBolt ? itemstack.getItem() : ItemRegistry.glistre_dust));
	                        EntityBlasterBolt entityarrow = itemarrow.createArrow(worldIn, itemstack, entityplayer);
	                        entityarrow.setAim(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0F, f * 3.0F, 1.0F);

	                        if (f == 1.0F)
	                        {
	                            entityarrow.setIsCritical(true);
	                        }

	                        int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);

	                        if (j > 0)
	                        {
	                            entityarrow.setDamage(entityarrow.getDamage() + (double)j * 0.5D + 0.5D);
	                        }

	                        int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);

	                        if (k > 0)
	                        {
	                            entityarrow.setKnockbackStrength(k);
	                        }

	                        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0)
	                        {
	                            entityarrow.setFire(100);
	                        }

	                        stack.damageItem(1, entityplayer);

	                        if (flag1)
	                        {
	                            entityarrow.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
	                        }

	                        worldIn.spawnEntityInWorld(entityarrow);
	                    }

	                    worldIn.playSound((EntityPlayer)null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

	                    if (!flag1)
	                    {
	                        --itemstack.stackSize;

	                        if (itemstack.stackSize == 0)
	                        {
	                            entityplayer.inventory.deleteStack(itemstack);
	                        }
	                    }

	                    entityplayer.addStat(StatList.getObjectUseStats(this));
	                }
	            }
	        }
	    }
	// change normal 20.0F to 1.0F to make instant charge 5.0F = 5 ticks or 1/4 second	        	
//       float f = (float)j / 1.0F;

/*	   public void onPlayerStoppedUsing(ItemStack itemStack, World world, EntityPlayer player, int usesRemaining)
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
// change normal 20.0F to 1.0F to make instant charge 5.0F = 5 ticks or 1/4 second	        	
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

	            EntityBlasterBolt par1EntityBlasterBolt = new EntityBlasterBolt(world, player, f * 2.0F);

	            if (f == 1.0F)
	            {
	                par1EntityBlasterBolt.setIsCritical(true);
	            }

	            int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemStack);

	            if (k > 0)
	            {
	                par1EntityBlasterBolt.setDamage(par1EntityBlasterBolt.getDamage() + (double)k * 0.5D + 0.5D);
	            }

	            int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, itemStack);

	            if (l > 0)
	            {
	                par1EntityBlasterBolt.setKnockbackStrength(l);
	            }

	            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, itemStack) > 0)
	            {
	                par1EntityBlasterBolt.setFire(100);
	            }

	            itemStack.damageItem(1, player);

	            if (flag)
	            {
	                par1EntityBlasterBolt.canBePickedUp = 2;
	            }
	            else
	            {
	                player.inventory.consumeInventoryItem(ItemRegistry.glistre_dust);
	            }

/*	            if (!p_77615_2_.isRemote)
	            {
	                p_77615_2_.spawnEntityInWorld(par1EntityBlasterBolt);
	            }*/
//took out the if ...isRemote to get blasterBolt to render
	            //   if (!p_77615_2_.isRemote)
	          //  {
//	            	world.spawnEntityInWorld(par1EntityBlasterBolt);
	         //   }
//	        }
//	    }

	    /**
	     * returns the action that specifies what animation to play when the items is being used
	     */
	    public EnumAction getItemUseAction(ItemStack p_77661_1_)
	    {
	        return EnumAction.BOW;
	    }
	   
	    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
	    {
	        boolean flag = this.findAmmo(playerIn) != null;

	        ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemStackIn, worldIn, playerIn, hand, flag);
	        if (ret != null) return ret;

	        if (!playerIn.capabilities.isCreativeMode && !flag)
	        {
	            return !flag ? new ActionResult(EnumActionResult.FAIL, itemStackIn) : new ActionResult(EnumActionResult.PASS, itemStackIn);
	        }
	        else
	        {
	            playerIn.setActiveHand(hand);
	            return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
	        }
	    }
	    
	    private ItemStack findAmmo(EntityPlayer player)
	    {
	        if (this.isArrow(player.getHeldItem(EnumHand.OFF_HAND)))
	        {
	            return player.getHeldItem(EnumHand.OFF_HAND);
	        }
	        else if (this.isArrow(player.getHeldItem(EnumHand.MAIN_HAND)))
	        {
	            return player.getHeldItem(EnumHand.MAIN_HAND);
	        }
	        else
	        {
	            for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
	            {
	                ItemStack itemstack = player.inventory.getStackInSlot(i);

	                if (this.isArrow(itemstack))
	                {
	                    return itemstack;
	                }
	            }

	            return null;
	        }
	    }

	    protected boolean isArrow(@Nullable ItemStack stack)
	    {
	        return stack != null && stack.getItem() instanceof EntityBlasterBolt;
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
	        
	        world.playSoundAtEntity(player, "glistremod:epm_flash", 1.0F, 2.0F);

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

	    @SideOnly(Side.CLIENT)
	    public boolean hasEffect(ItemStack par1ItemStack)
	    {
		    return true;
	    }
		@Override
		public float apply(ItemStack stack, World worldIn, EntityLivingBase entityIn) {
			
			return 0;
		} 
 

	} 


