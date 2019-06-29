package com.glistre.glistremod.entities.guardian;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;

import com.glistre.glistremod.GlistreMod;
import com.glistre.glistremod.entities.blacktobie.EntityBlackTobo;
import com.glistre.glistremod.entities.king.EntityTobieKing;
import com.glistre.glistremod.entities.queen.EntityTobieQueen;
import com.glistre.glistremod.init.GlistreEntityRegistry;
import com.glistre.glistremod.init.ItemRegistry;
import com.glistre.glistremod.projectiles.blaster.EntityBlasterBolt;
import com.glistre.glistremod.reference.Reference;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackRangedBow;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityIronGolem;
//import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.monster.SkeletonType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderHell;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityTobieSkel extends EntitySkeleton implements IRangedAttackMob
{
    private static final DataParameter<Integer> SKELETON_VARIANT = EntityDataManager.<Integer>createKey(EntitySkeleton.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> SWINGING_ARMS = EntityDataManager.<Boolean>createKey(EntitySkeleton.class, DataSerializers.BOOLEAN);
    private EntityAIAttackRangedBow aiArrowAttack = new EntityAIAttackRangedBow(this, 1.0D, 20, 15.0F);
    //EntityZombie.class was below
    private EntityAIAttackMelee aiAttackOnCollide = new EntityAIAttackMelee(this, 1.2D, true);
    private static final ItemStack defaultHeldItem = new ItemStack(ItemRegistry.tobie_gun_1, 1);

    public AxisAlignedBB boundingBox;

    private int attackTimer;

    public EntityTobieSkel(World worldIn)
    {
        super(worldIn);
//      this.boundingBox.setBounds(minX,minY,minZ,maxX,maxY,maxZ);
//        this.boundingBox.fromBounds(1D, 1D, 1D, 1.0D, 1.0D, 1.0D);
        this.boundingBox.intersects(1D, 1D, 1D, 1.0D, 1.0D, 1.0D);
//      this.getEntityBoundingBox().fromBounds(0D, 0D, 0D, .5D, .5D, .5D);
        //add attack AI from entity to player next line
//        this.targetTasks.addTask(0, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
        this.tasks.addTask(0, new EntityAISwimming(this));
        //EntityZombie.class was in the next parameter removed 1.10.2
        this.tasks.addTask(1,  new EntityAIAttackMelee(this, 1.0D, true));
        this.tasks.addTask(3, new EntityAIRestrictSun(this));
//        this.tasks.addTask(3, new EntityAIFleeSun(this, 1.0D));
        this.tasks.addTask(3, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(0, new EntityAIWatchClosest(this, EntityZombie.class, 8.0F));
 
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityWitch.class, 3, true, false, IMob.MOB_SELECTOR));
        
        this.tasks.addTask(3, new EntityAILookIdle(this));
        this.targetTasks.addTask(0, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityZombie.class, 2, true, false, IMob.MOB_SELECTOR));
        this.targetTasks.addTask(0, new EntityAINearestAttackableTarget(this, EntityTobieKing.class, 2, true, false, IMob.MOB_SELECTOR));
        this.targetTasks.addTask(0, new EntityAINearestAttackableTarget(this, EntityTobieQueen.class, 2, true, false, IMob.MOB_SELECTOR));
        this.targetTasks.addTask(0, new EntityAINearestAttackableTarget(this, EntityBlackTobo.class, 2, true, false, IMob.MOB_SELECTOR));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntitySpider.class, 3, true, false, IMob.MOB_SELECTOR));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityIronGolem.class, 3, true, false, IMob.MOB_SELECTOR));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityCaveSpider.class, 4, true, false, IMob.MOB_SELECTOR));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityEnderman.class, 4, true, false, IMob.MOB_SELECTOR));


/*        if (worldIn != null && !worldIn.isRemote)
        {
            this.setCombatTask();
        }*///removed 1.8.9
    }
    
    @Override
 	public AxisAlignedBB getCollisionBox(Entity par1Entity) {
     	
 		return boundingBox;
 	}
 	
 	@Override
 	//changed #getBoundingBox to #getCollisionBoundingBox 1.8.9
 	public AxisAlignedBB getCollisionBoundingBox() {
 		return boundingBox;
 	}
    
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        
 //       this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(100.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.34000000417232513D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(12.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(35.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(10.0D);
    }

    protected void entityInit()
    {
        super.entityInit();
        //changed 13 to 23
        //   this.dataWatcher.addObject(13, new Byte((byte)0));
        this.dataManager.register(SKELETON_VARIANT, Integer.valueOf(SkeletonType.NORMAL.getId()));
        this.dataManager.register(SWINGING_ARMS, Boolean.valueOf(false));
    }

    /**
     * Returns true if the newer Entity AI code should be run
     */
    public boolean isAIEnabled()
    {
        return true;
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected SoundEvent getAmbientSound()
    {
//        return "mob.skeleton.say";
        return this.getSkeletonType().getAmbientSound();
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    //1.10.2 no longer a String
    protected SoundEvent getHurtSound()
    {
        return this.getSkeletonType().getHurtSound();
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected SoundEvent getDeathSound()
    {
        return this.getSkeletonType().getDeathSound();
    }
//func_145780_a  changed to playStepSound
    protected void playStepSound(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block blockIn)
    {
    	SoundEvent soundevent = this.getSkeletonType().getStepSound();
        this.playSound(soundevent, 0.15F, 1.0F);     
    }
    
    @SideOnly(Side.CLIENT)
    public int getAttackTimer()
    {
        return this.attackTimer;
    }
    
    @Override
    public boolean attackEntityAsMob(Entity targetEntity)
    {
      if (super.attackEntityAsMob(targetEntity))
      {
                 /*  if (this.getSkeletonType() == 1 && targetEntity instanceof EntityLivingBase)
                   {
                       ((EntityLivingBase)targetEntity).addPotionEffect(new PotionEffect(Potion.wither.id, 200));
                   }*/
       this.attackTimer = 10;
       this.worldObj.setEntityState(this, (byte)4);
       boolean flag = targetEntity.attackEntityFrom(DamageSource.causeMobDamage(this), (float)(7 + this.rand.nextInt(15)));

       if (flag)
       {
          targetEntity.motionY += 0.4000000059604645D;
       }

       this.playSound(SoundEvents.ENTITY_IRONGOLEM_ATTACK, 1.0F, 1.0F);
                   return flag;
               }
               else
               {
                   return false;
               }
      }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
 /*   public void onLivingUpdate()
    {
        if (this.worldObj.isDaytime() && !this.worldObj.isRemote)
        {
            float f = this.getBrightness(1.0F);

            if (f > 0.5F && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)))
            {
                boolean flag = true;
                ItemStack itemstack = this.getEquipmentInSlot(4);

                if (itemstack != null)
                {
                    if (itemstack.isItemStackDamageable())
                    {
                        itemstack.setItemDamage(itemstack.getItemDamageForDisplay() + this.rand.nextInt(2));

                        if (itemstack.getItemDamageForDisplay() >= itemstack.getMaxDamage())
                        {
                            this.renderBrokenItemStack(itemstack);
                            this.setCurrentItemOrArmor(4, (ItemStack)null);
                        }
                    }

                    flag = false;
                }

                if (flag)
                {
                    this.setFire(8);
                }
            }
        }

        if (this.worldObj.isRemote && this.getSkeletonType() == 1)
        {
            this.setSize(0.72F, 2.34F);
        }

        super.onLivingUpdate();
    }*/

    /**
     * Handles updating while being ridden by an entity
     */
/*    @Override
    public void updateRidden()
    {
        super.updateRidden();

        if (this.ridingEntity instanceof EntityCreature)
        {
            EntityCreature entitycreature = (EntityCreature)this.ridingEntity;
            this.renderYawOffset = entitycreature.renderYawOffset;
        }
    }*/
    

    /**
     * Called when the mob's health reaches 0.
     */
 /*   @Override
    public void onDeath(DamageSource source)
    {
        super.onDeath(source);

        if (source.getSourceOfDamage() instanceof EntityBlasterBolt && source.getEntity() instanceof EntityPlayer)
        {
            EntityPlayer entityplayer = (EntityPlayer)source.getEntity();
            double d0 = entityplayer.posX - this.posX;
            double d1 = entityplayer.posZ - this.posZ;

            if (d0 * d0 + d1 * d1 >= 2500.0D)//what is this doing?
            {
                entityplayer.triggerAchievement(AchievementList.snipeSkeleton);
            }
        }
    }*/
    
 /*   @Override
    protected Item getDropItem()
    {
        return ItemRegistry.ender_gun;
    }*/

    /**
     * Drop 0-2 items of this living's type. @param par1 - Whether this entity has recently been hit by a player. @param
     * par2 - Level of Looting used to kill this mob.
     */
    @Override
    protected void dropFewItems(boolean hasPlayerHit, int lootingLev)
    {
        int j;
        int k;

        if (this.getSkeletonType().getId() == 1)
        {
            j = this.rand.nextInt(3 + lootingLev) - 1;

            for (k = 0; k < j; ++k)
            {
                this.dropItem(ItemRegistry.sceptre_1, 1);
            }
        }
        else
        {
        j = this.rand.nextInt(3 + lootingLev);

        for (k = 0; k < j; ++k)
        	{
            this.dropItem(GlistreEntityRegistry.ender_bolt_1, 1);
        	}
        }

        j = this.rand.nextInt(3 + lootingLev);

        for (k = 0; k < j; ++k)
        	{
            this.dropItem(ItemRegistry.glistre_dust, 1);
        	}
        
        j = this.rand.nextInt(3 + lootingLev);

        for (k = 0; k < j; ++k)
        	{
            this.dropItem(ItemRegistry.glistre_ingot, 1);
        	}  
    	//1.8 way to make 20% chance of drop
    	if(rand.nextInt(100)<40){
    		int p;
            int q;
            
        	p = this.rand.nextInt(3 + lootingLev) + 1;
        // what does float do here?  //was entityDropItem now dropItem
        	for (q = 0; q < p; ++q)
                // what does float do here?  likelihood of drop?     	       	
                   
        	{
        		this.dropItem(ItemRegistry.ender_gun, 1);
        	}
        	//make non-wither TobySkeleton guardian drop rare drop
        	for (q = 0; q < p; ++q)
                // what does float do here?  likelihood of drop?     	       	
                   
        	{
        		this.dropItem(GlistreEntityRegistry.tobie_worst_projectile_1, 1);
        		this.dropItem(ItemRegistry.sceptre_1, 1);
     		}
    	}
    }

 
    /**
     * Makes entity wear random armor based on difficulty
     */
/*    @Override
    protected void addRandomArmor()
    {
 //       super.addRandomArmor();
       this.setCurrentItemOrArmor(0, new ItemStack(ItemRegistry.tobie_gun_1));
        // sets the ability for the RayGun to drop which is different than enderGun don't want RayGun for player
        this.equipmentDropChances[0] = 0.0F;
    }*/

   @Override
   public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData entitylivingdata)
    {
	 //changed .func_180482_a to #onInitialSpawn 1.8.9
	   entitylivingdata = super.onInitialSpawn(difficulty, entitylivingdata);

        if (this.worldObj.provider instanceof WorldProviderHell && this.getRNG().nextInt(5) > 0)
        {
            this.tasks.addTask(2, this.aiAttackOnCollide);
            this.setSkeletonType(SkeletonType.WITHER);
 //           this.setCurrentItemOrArmor(0, new ItemStack(ItemRegistry.sceptre_1));
            this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(ItemRegistry.sceptre_1));
            
            this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
 // sets the ability for the RayGun to drop which is different than blaster don't want RayGun for player
       //     this.equipmentDropChances[0] = 0.0F;
        }
        else
        {
            this.tasks.addTask(0, this.aiArrowAttack);
 //           this.addRandomArmor();  //removed or changed 1.8.9
 //           this.setCurrentItemOrArmor(0, new ItemStack(ItemRegistry.tobie_gun_1));
            this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(ItemRegistry.tobie_gun_1));

  //          this.enchantEquipment(); //removed 1.8.9
        }

        this.setCanPickUpLoot(this.rand.nextFloat() < 0.55F * difficulty.getClampedAdditionalDifficulty());
 //       this.setCanPickUpLoot(this.rand.nextFloat() < 0.55F * this.worldObj.(this.posX, this.posY, this.posZ));

 //       if (this.getEquipmentInSlot(4) == null)
        if (this.getItemStackFromSlot(EntityEquipmentSlot.HEAD) == null)

        {
            Calendar calendar = this.worldObj.getCurrentDate();

            if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.rand.nextFloat() < 0.25F)
            {
                this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(this.rand.nextFloat() < 0.1F ? Blocks.LIT_PUMPKIN : Blocks.PUMPKIN));
//                this.equipmentDropChances[4] = 0.0F;
                this.inventoryArmorDropChances[EntityEquipmentSlot.HEAD.getIndex()] = 0.0F;

            }
        }

        return entitylivingdata;
    }
   /**
    * Enchants the entity's armor and held item based on difficulty
    */
 /*  @Override
   public void enchantEquipment()
   {
       float f = 1.0F;

       if (this.getHeldItemMainhand() != null && this.rand.nextFloat() < 0.25F * f)
       {
           EnchantmentHelper.addRandomEnchantment(this.rand, this.getHeldItemMainhand(), (int)(5.0F + f * (float)this.rand.nextInt(18)));
       }

  //     for (int i = 0; i < 4; ++i)
  //     {
    	   
 //1.8 update not doubt this probably will not work :  change func_130225_q(i) to this getHeldItemMainhand   and deleted for loop        for (int i = 0; i < 4; ++i)
    //       {	   
           ItemStack itemstack = this.getHeldItemMainhand();

           if (itemstack != null && this.rand.nextFloat() < 0.5F * f)
           {
               EnchantmentHelper.addRandomEnchantment(this.rand, itemstack, (int)(5.0F + f * (float)this.rand.nextInt(18)));
           }
 //      }
   }*/

    /**
     * sets this entity's combat AI.
     */
   /*
   @Override
    public void setCombatTask()
    {
        this.tasks.removeTask(this.aiAttackOnCollide);
 //       this.tasks.removeTask(this.aiArrowAttack);
        ItemStack itemstack = this.getHeldItemMainhand();

        if (itemstack != null && itemstack.getItem() == ItemRegistry.tobie_gun_1)
        {
            this.tasks.addTask(0, this.aiArrowAttack);
        }
        else
        {
            this.tasks.addTask(1, this.aiAttackOnCollide);
        }
    }*/

    /**
     * Attack the specified entity using a ranged attack.
     */
    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distance)
    {
/*        EntityBlasterBolt entityblasterbolt = new EntityBlasterBolt(this.worldObj, this, target, 1.6F, (float)(14 - this.worldObj.getDifficulty().getDifficultyId() * 4));
//        EntityBlasterBolt entityblasterbolt = new EntityBlasterBolt(this.worldObj, this, target, 500F, (float)(100));

        int i = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, this.getHeldItemMainhand());
        int j = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, this.getHeldItemMainhand());
        entityblasterbolt.setDamage((double)(distance * 2.0F) + this.rand.nextGaussian() * 0.25D + (double)((float)this.worldObj.getDifficulty().getDifficultyId() * 0.11F));

        if (i > 0)
        {
            entityblasterbolt.setDamage(entityblasterbolt.getDamage() + (double)i * 0.5D + 0.5D);
        }

        if (j > 0)
        {
            entityblasterbolt.setKnockbackStrength(j);
        }

        if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, this.getHeldItemMainhand()) > 0 || this.getSkeletonType() == 1)
        {
            entityblasterbolt.setFire(100);
        }*/
    	EntityBlasterBolt entityblasterbolt = new EntityBlasterBolt(this.worldObj, this, target, 500F, (float)(100));
        double d0 = target.posX - this.posX;
        double d1 = target.posY + (double)target.getEyeHeight() - 1.100000023841858D - entityblasterbolt.posY;
        double d2 = target.posZ - this.posZ;
        float f1 = MathHelper.sqrt_double(d0 * d0 + d2 * d2) * 0.2F;
        entityblasterbolt.setThrowableHeading(d0, d1 + (double)f1, d2, 0.6F, 12.0F);

      //glistremod:laser_blaster"
        this.playSound(SoundEvent.REGISTRY.getObject(new ResourceLocation(Reference.MODID, "laser_blaster")), 1.2F, 1.0F );
         this.worldObj.spawnEntityInWorld(entityblasterbolt);
        

        }
    
    @Override
    public void onUpdate(){
    	super.onUpdate();
    	if (!(worldObj.isRemote && this.ticksExisted < 400)){
   
		List<EntityPlayer> playerList = worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().expand(45.0D, 35.0D, 45.0D));
		Iterator<EntityPlayer> i1 = playerList.iterator();
		EntityPlayer entityplayer;

              while (i1.hasNext())
              {
            	     entityplayer = (EntityPlayer)i1.next();		
			double inRange = this.getDistanceSqToEntity(entityplayer);
			
			if ( inRange < 1600.0D & inRange > 1580.0D)

				if(entityplayer.getHeldItemMainhand() !=null &&  entityplayer.getHeldItemMainhand().getItem() == ItemRegistry.glistre_sword){

		        			entityplayer.addChatComponentMessage(
                    		new TextComponentString(TextFormatting.DARK_GREEN + "Tobie Guardian near Player location " 
                    + TextFormatting.DARK_RED + " X: " + (int)Math.round(this.posX) 
                    + TextFormatting.GOLD + " | Y: " + (int)Math.round(this.posY) 
                    + TextFormatting.DARK_AQUA +" | Z: " + (int)Math.round(this.posZ)));
			}
			
            }}
    	}

    /**
     * Return this skeleton's type.
     */
    public SkeletonType getSkeletonType()
    {
        return SkeletonType.getByOrdinal(((Integer)this.dataManager.get(SKELETON_VARIANT)).intValue());
    }

    /**
     * Set this skeleton's type.
     */
    private void updateSize(SkeletonType skeletontype)
    {
        if (skeletontype == SkeletonType.WITHER)
        {
            this.setSize(0.7F, 2.4F);
        }
        else
        {
            this.setSize(0.6F, 1.99F);
        }
    }
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);

        if (compound.hasKey("SkeletonType", 99))
        {
            // byte b0 = tag.getByte("SkeletonType");
         	int i = compound.getByte("SkeletonType");
            this.setSkeletonType(SkeletonType.getByOrdinal(i));
        }

//        this.setCombatTask();//removed 1.8.9
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setByte("SkeletonType", (byte)this.getSkeletonType().getId());
    }

    /**
     * Sets the held item, or an armor slot. Slot 0 is held item. Slot 1-4 is armor. Params: Item, slot
     */
 /*   public void setCurrentItemOrArmor(int slot, ItemStack itemstack)
    {
    //    super.setCurrentItemOrArmor(slot, itemstack);

  //      if (!this.worldObj.isRemote && slot == 0)
  //      {
  //          this.setCombatTask();//removed 1.8.9
  //      }
    }*/
    public void setItemStackToSlot(EntityEquipmentSlot slotIn, @Nullable ItemStack stack)
    {
        super.setItemStackToSlot(slotIn, stack);

        if (!this.worldObj.isRemote && slotIn == EntityEquipmentSlot.MAINHAND)
        {
            this.setCombatTask();
        }
    }
    /**
     * Gives armor or weapon for entity based on given DifficultyInstance
     */
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
    {
        super.setEquipmentBasedOnDifficulty(difficulty);
        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(ItemRegistry.sceptre_1));
    }
    /**
     * Returns the Y Offset of this entity.
     */
    @Override
    public double getYOffset()
    {
        return super.getYOffset() - 0.5D;
    }

    //  needs to be false or by the time the Player gets to location the Tobie will be gone    
    @Override
    public boolean canDespawn(){
		return false; 	
    }
    @Override
    public ItemStack getHeldItemMainhand()
    {
        return defaultHeldItem;
    }
    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    @Override
    public int getMaxSpawnedInChunk()
    {
        return 2;
    }
}