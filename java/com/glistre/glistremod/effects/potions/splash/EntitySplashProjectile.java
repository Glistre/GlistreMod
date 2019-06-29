package com.glistre.glistremod.effects.potions.splash;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import com.glistre.glistremod.GlistreMod;
import com.glistre.glistremod.init.GlistreEntityRegistry;
import com.glistre.glistremod.init.ItemRegistry;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntitySplashProjectile extends EntitySplashThrowable
{
    /** The damage value of the thrown potion that this EntityPotion represents. */
    private ItemStack potionDamage;
//    private static final String __OBFID = "CL_00001727";

    public EntitySplashProjectile(World world)
    {
        super(world);
    }

    public EntitySplashProjectile(World world, EntityLivingBase entity, int p_i1789_3_)
    {
        this(world, entity, new ItemStack(GlistreEntityRegistry.splash_poison_protection, 1, p_i1789_3_));
    }

    public EntitySplashProjectile(World world, EntityLivingBase entity, ItemStack itemStack)
    {
        super(world, entity);
        this.potionDamage = itemStack;
    }

    @SideOnly(Side.CLIENT)
    public EntitySplashProjectile(World world, double x, double y, double z, int p_i1791_8_)
    {
        this(world, x, y, z, new ItemStack(GlistreEntityRegistry.splash_poison_protection, 1, p_i1791_8_));
    }

    public EntitySplashProjectile(World world, double x, double y, double z, ItemStack itemStack)
    {
        super(world, x, y, z);
        this.potionDamage = itemStack;
    }

    /**
     * Gets the amount of gravity to apply to the thrown entity with each tick.
     */
    protected float getGravityVelocity()
    {
        return 0.05F;
    }

    protected float func_70182_d()
    {
        return 0.5F;
    }

    protected float func_70183_g()
    {
        return -20.0F;
    }
/*    
@Override
    public void setPotionDamage(int potionId)
    {
        if (this.potionDamage == null)
        {
            this.potionDamage = new ItemStack(GlistreEntityRegistry.splash_poison_protection, 1, 0);
        }

        this.potionDamage.setItemDamage(potionId);
    }
@Override

    /**
     * Returns the damage value of the thrown potion that this EntityPotion represents.
     */
/*    public int getPotionDamage()
    {
        if (this.potionDamage == null)
        {
            this.potionDamage = new ItemStack(GlistreEntityRegistry.splash_poison_protection, 1, 0);
        }

        return this.potionDamage.getItemDamage();
    }*/

    /**
     * Called when this EntityThrowable hits a block or entity.
     */
/*   @Override
    protected void onImpact(MovingObjectPosition m)
    {
    if(m.typeOfHit == EnumMovingObjectType.ENTITY)
    m.entityHit.addPotionEffect(entity.addPotionEffect(new PotionEffect(ItemRegistry.poison_protect_potion.id, 3000, 0, false, false));
);

    	 if (!this.worldObj.isRemote)
    	 {
    		 this.setDead();
    	 }
    }*/

  @Override
    protected void onImpact(RayTraceResult mop)
    { 
	  AxisAlignedBB axisalignedbb = this.getEntityBoundingBox().expand(4.0D, 4.0D, 4.0D);
      List<EntityLivingBase> entities = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
      
      if(mop.entityHit != null ) {
      if (entities != null && !entities.isEmpty())
      {
          Iterator iterator = entities.iterator();

          while (iterator.hasNext())
          {
              EntityLivingBase hitEntity = (EntityLivingBase)iterator.next();
              double distanceToHit = this.getDistanceSqToEntity(hitEntity);

              if (distanceToHit < 16.0D)
              {
                  double durationMultiplier = 1.0D - Math.sqrt(distanceToHit) / 4.0D;

                  if (hitEntity == mop.entityHit)
                  {
                      durationMultiplier = 1.0D;
                  }

                  for (EntityLivingBase entity : entities) {
                	  //duration /20= one second, amplifier: increases color intensity, ambient: makes particles almost completely transparent, particles displayed or not)
                	  entity.addPotionEffect(new PotionEffect(ItemRegistry.poison_protect_potion, 3000, 0, false, true));
                	  }

    	 if (!this.worldObj.isRemote)
    	 {
    		 //2002 blue water potion circles, 2003 purple expanding effect, 2004 fire , 2005 green sparkles
    		 //changed last parameter this.getPotionDamage() to 4 makes green swirls; 888888 makes particles splashing charcoal, 555555 makes orange swirls, purple/pink 777777, 333333 reddust
    		 //6 grey swirls 444444 darkredblood reddust,  2 light blue grey swirls 229911 darkblue swirls
    		 //1.8 update added BlockPos next line
    		 BlockPos pos = new BlockPos((int)Math.round(this.posX), (int)Math.round(this.posY), (int)Math.round(this.posZ));
    		 //this.worldObj.playAuxSFX(2002, pos, 4);  changed in 1.10.2 to next line
    		 //changed PotionType.getID() to 4
    		 this.worldObj.playEvent(2002, new BlockPos(this), 4);
    		 
    		 this.setDead();
    	 }
         }
         }
        }
      	}
      }
    

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
   public void readEntityFromNBT(NBTTagCompound tagCompound)
    {
        super.readEntityFromNBT(tagCompound);

        if (tagCompound.hasKey("Potion", 10))
        {
            this.potionDamage = ItemStack.loadItemStackFromNBT(tagCompound.getCompoundTag("Potion"));
        }
//        else
/*        {
            this.setPotionDamage(tagCompound.getInteger("potionValue"));
        }*/

        if (this.potionDamage == null)
        {
            this.setDead();
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound p_70014_1_)
    {
        super.writeEntityToNBT(p_70014_1_);

        if (this.potionDamage != null)
        {
            p_70014_1_.setTag("Potion", this.potionDamage.writeToNBT(new NBTTagCompound()));
        }
    }


}