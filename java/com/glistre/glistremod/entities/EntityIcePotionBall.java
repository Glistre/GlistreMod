package com.glistre.glistremod.entities;

import java.util.Iterator;
import java.util.List;

import com.glistre.glistremod.GlistreMod;
import com.glistre.glistremod.effects.potions.splash.EntitySplashThrowable;
import com.glistre.glistremod.init.ItemRegistry;
import com.glistre.glistremod.reference.Reference;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;


public class EntityIcePotionBall extends EntitySplashThrowable
{
    /** The damage value of the thrown potion that this EntityPotion represents. */
    private ItemStack potionDamage;
    private static final String __OBFID = "CL_00001727";

    public EntityIcePotionBall(World world)
    {
        super(world);
    }

    public EntityIcePotionBall(World world, EntityLivingBase entity, int p_i1789_3_)
    {
        this(world, entity, new ItemStack(Items.POTIONITEM, 1, p_i1789_3_));
    }

    public EntityIcePotionBall(World world, EntityLivingBase entity, ItemStack itemStack)
    {
        super(world, entity);
        this.potionDamage = itemStack;
    }

    @SideOnly(Side.CLIENT)
    public EntityIcePotionBall(World world, double p_i1791_2_, double p_i1791_4_, double p_i1791_6_, int p_i1791_8_)
    {
        this(world, p_i1791_2_, p_i1791_4_, p_i1791_6_, new ItemStack(Items.POTIONITEM, 1, p_i1791_8_));
    }

    public EntityIcePotionBall(World world, double p_i1792_2_, double p_i1792_4_, double p_i1792_6_, ItemStack itemStack)
    {
        super(world, p_i1792_2_, p_i1792_4_, p_i1792_6_);
        this.potionDamage = itemStack;
    }

    /**
     * Gets the amount of gravity to apply to the thrown entity with each tick.  next three methods copied from EntityThrowable to make fast 
     * as a snowball instead of slow like a potion
     * 
     */
    protected float getGravityVelocity()
    {
        return 0.03F;
    }

    protected float func_70182_d()
    {
        return 1.5F;
    }

    protected float func_70183_g()
    {
        return 0.0F;
    }
    

    public void setPotionDamage(int potionId)
    {
        if (this.potionDamage == null)
        {
            this.potionDamage = new ItemStack(Items.POTIONITEM, 1, 0);
        }

        this.potionDamage.setItemDamage(potionId);
    }

    /**
     * Returns the damage value of the thrown potion that this EntityPotion represents.
     */
    public int getPotionDamage()
    {
        if (this.potionDamage == null)
        {
            this.potionDamage = new ItemStack(Items.POTIONITEM, 1, 0);
        }

        return this.potionDamage.getItemDamage();
    }

    /**
     * Called when this EntityThrowable hits a block or entity.
     */
/*    protected void onImpact(MovingObjectPosition mop)
    {
        if (!this.worldObj.isRemote)
        {
            List list = (Item.potion.getEffects(this.potionDamage);

            
            {
                AxisAlignedBB axisalignedbb = this.boundingBox.expand(4.0D, 2.0D, 4.0D);
                List list1 = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);

                if (list1 != null && !list1.isEmpty())
                {
                    Iterator iterator = list1.iterator();

                    while (iterator.hasNext())
                    {
                        EntityLivingBase entitylivingbase = (EntityLivingBase)iterator.next();
                        double d0 = this.getDistanceSqToEntity(entitylivingbase);

                        if (d0 < 16.0D)
                        {
                            double d1 = 1.0D - Math.sqrt(d0) / 4.0D;

                            if (entitylivingbase == mop.entityHit)
                            {
                                d1 = 1.0D;
                            }

                            Iterator iterator1 = list.iterator();

                            while (iterator1.hasNext())
                            {
                                PotionEffect potioneffect = (PotionEffect)iterator1.next();
                                int i = potioneffect.getPotionID();

                                if (Potion.potionTypes[i].isInstant())
                                {
                                    Potion.potionTypes[i].affectEntity(this.getThrower(), entitylivingbase, potioneffect.getAmplifier(), d1);
                                }
                                else
                                {
                                    int j = (int)(d1 * (double)potioneffect.getDuration() + 0.5D);

                                    if (j > 20)
                                    {
                                        entitylivingbase.addPotionEffect(new PotionEffect(GlistreMod.poisonProtectPotion.id, 3000, 0, false));
                                    }
                                }
                            }
                        }
                    }
                }
            }

            this.worldObj.playAuxSFX(2002, (int)Math.round(this.posX), (int)Math.round(this.posY), (int)Math.round(this.posZ), this.getPotionDamage());
            this.setDead();
        }
    }*/
    
  @Override
    protected void onImpact(RayTraceResult mop)
    { 
	  AxisAlignedBB axisalignedbb = this.getEntityBoundingBox().expand(4.0D, 4.0D, 4.0D);
      List<EntityLivingBase> entities = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
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
/*                  Iterator iterator1 = entities.iterator();

                  while (iterator1.hasNext())
                  {
                      PotionEffect potioneffect = (PotionEffect)iterator1.next();
                      int i = potioneffect.getPotionID();

                      if (Potion.potionTypes[i].isInstant())
                      {
                          Potion.potionTypes[i].affectEntity(this.getThrower(), hitEntity, potioneffect.getAmplifier(), durationMultiplier);
                      }
                      else
                      {
                          int j = (int)(durationMultiplier * (double)potioneffect.getDuration() + 0.5D);

                          if (j > 20)
                          {
//                              hitEntity.addPotionEffect(new PotionEffect(i, j, potioneffect.getAmplifier()));
                              hitEntity.addPotionEffect(new PotionEffect(GlistreMod.poisonProtectPotion.id, 3000, 0, false));

                          }*/
                  for (EntityLivingBase entity : entities) {
                	  //2 ==moveSlowdown
                	  entity.addPotionEffect(new PotionEffect(Potion.getPotionById(2),800, 2));
                	  }
/*         if(mop.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY)
                   ((EntityLivingBase) mop.entityHit).addPotionEffect(new PotionEffect(GlistreMod.poisonProtectPotion.id, 3000, 0, false));*/
//         	this.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0.0F);
    	 if (!this.worldObj.isRemote)
    	 {
    		 //2002 blue water potion circles, 2003 purple expanding effect, 2004 fire , 2005 green sparkles
    		 //changed last parameter this.getPotionDamage() to 0 blue swirls, 4 makes green swirls; 888888 makes particles splashing charcoal, 555555 makes orange swirls, purple/pink 777777, 333333 reddust
    		 //6 grey swirls 444444 darkredblood reddust,  2 light blue grey swirls 229911 darkblue swirls
    		
    		 //Update 1,8 not sure about adding the blockpos here
  		 
    		 BlockPos blockpos = new BlockPos((int)Math.round(this.posX), (int)Math.round(this.posY), (int)Math.round(this.posZ));
    		 //playAuxSFX changed to playBroadcastSound 1.10.2 probably big mistake   
    		 this.worldObj.playBroadcastSound(2002, blockpos, 0);
 
    		 
    		 // worked in 1.7.10		 this.worldObj.playAuxSFX(2002, (int)Math.round(this.posX), (int)Math.round(this.posY), (int)Math.round(this.posZ), 0);
    		 this.setDead();
    	 }
         }
        }
      }
      }
      
    
          

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
   public void readEntityFromNBT(NBTTagCompound p_70037_1_)
    {
        super.readEntityFromNBT(p_70037_1_);

        if (p_70037_1_.hasKey("Potion", 10))
        {
            this.potionDamage = ItemStack.loadItemStackFromNBT(p_70037_1_.getCompoundTag("Potion"));
        }
        else
        {
            this.setPotionDamage(p_70037_1_.getInteger("potionValue"));
        }

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