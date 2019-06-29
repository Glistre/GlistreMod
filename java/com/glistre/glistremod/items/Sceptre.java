package com.glistre.glistremod.items;

import javax.annotation.Nullable;

import com.glistre.glistremod.init.GlistreEntityRegistry;
import com.glistre.glistremod.init.ItemRegistry;
import com.glistre.glistremod.projectiles.blaster.EntitySceptreBolt;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class Sceptre extends ItemBow{

	private final String name = "sceptre_1";

public Sceptre(){
	
    this.bFull3D = true;
}

public String getName()
{
	return name;
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
        return stack != null && stack.getItem() instanceof ItemArrow;
    }

    /**
     * Called when the player stops using an Item (stops holding the right mouse button).
     */
	@Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
    {
		ResourceLocation location = new ResourceLocation("glistremod", "ender_blaster");
		SoundEvent event = new SoundEvent(location); //useed util
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
                    boolean flag1 = entityplayer.capabilities.isCreativeMode || (itemstack.getItem() instanceof ItemArrow ? ((ItemArrow)itemstack.getItem()).isInfinite(itemstack, stack, entityplayer) : false);

                    if (!worldIn.isRemote)
                    {
                        ItemArrow itemarrow = (ItemArrow)((ItemArrow)(itemstack.getItem() instanceof ItemArrow ? itemstack.getItem() : GlistreEntityRegistry.sceptre_bolt_1));
                       // EntitySceptreBolt entitysceptrebolt = new EntitySceptreBolt(worldIn, entityplayer, f * 2.0F);
                        EntitySceptreBolt entitysceptrebolt = (EntitySceptreBolt) itemarrow.createArrow(worldIn, itemstack, entityplayer);
                        entitysceptrebolt.setAim(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0F, f * 3.0F, 1.0F);

                        if (f == 1.0F)
                        {
                            entitysceptrebolt.setIsCritical(true);
                        }

                        /*       int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);

                        if (j > 0)
                        {
                            entitysceptrebolt.setDamage(entitysceptrebolt.getDamage() + (double)j * 0.5D + 0.5D);
                        }

                        int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);

                        if (k > 0)
                        {
                            entitysceptrebolt.setKnockbackStrength(k);
                        }

                        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0)
                        {
                            entitysceptrebolt.setFire(100);
                        }

                        stack.damageItem(1, entityplayer);*/

                        if (flag1)
                        {
                	      //  flag1 = entityplayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(51), itemstack) > 0;

                            entitysceptrebolt.pickupStatus = EntitySceptreBolt.PickupStatus.CREATIVE_ONLY;
                        }

                        worldIn.spawnEntityInWorld(entitysceptrebolt);
                    }
                    //created SoundEvent with custom ResourceLocation above -- parameter is event below
                    worldIn.playSound((EntityPlayer)null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, event, SoundCategory.NEUTRAL, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

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


}