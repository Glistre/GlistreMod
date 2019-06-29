package com.glistre.glistremod.entities.queen;

import java.util.Calendar;

import javax.annotation.Nullable;

import com.glistre.glistremod.GlistreMod;
import com.glistre.glistremod.entities.blacktobie.EntityBlackTobo;
import com.glistre.glistremod.entities.guardian.EntityTobieSkel;
import com.glistre.glistremod.entities.wolf.EntityGlistreWolf;
import com.glistre.glistremod.init.GlistreEntityRegistry;
import com.glistre.glistremod.init.ItemRegistry;
import com.glistre.glistremod.projectiles.blaster.EntityBlasterBolt;
import com.glistre.glistremod.reference.Reference;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
//import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;

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
import net.minecraft.entity.item.EntityXPOrb;

//import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.monster.SkeletonType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
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
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderHell;

public class EntityTobieQueen extends EntitySkeleton implements IRangedAttackMob
{    
	private static final DataParameter<Integer> SKELETON_VARIANT = EntityDataManager.<Integer>createKey(EntitySkeleton.class, DataSerializers.VARINT);
	private static final DataParameter<Boolean> SWINGING_ARMS = EntityDataManager.<Boolean>createKey(EntitySkeleton.class, DataSerializers.BOOLEAN);
    private EntityAIAttackRangedBow aiArrowAttack = new EntityAIAttackRangedBow(this, 1.0D, 20, 15.0F);
    private EntityAIAttackMelee aiAttackOnCollide = new EntityAIAttackMelee(this, 1.2D, true);
    private final BossInfoServer bossInfo = (BossInfoServer)(new BossInfoServer(this.getDisplayName(), BossInfo.Color.PURPLE, BossInfo.Overlay.PROGRESS)).setDarkenSky(true);
    private static final ItemStack defaultHeldItem = new ItemStack(ItemRegistry.sceptre_1, 1);
    public AxisAlignedBB boundingBox;

    
    private int attackTimer;

    public EntityTobieQueen(World world)
    {
        super(world);
        //trying to make immune to fire damage not working 
    
        this.isImmuneToFire();

        //trying to raise the hit box
        setSize(1.0F, 4.5F);
//      this.boundingBox.setBounds(minX,minY,minZ,maxX,maxY,maxZ);
//        this.boundingBox.fromBounds(1D, 1D, 1D, 1.0D, 1.0D, 1.0D);
        this.boundingBox.intersects(1D, 1D, 1D, 1.0D, 1.0D, 1.0D);

//        this.getEntityBoundingBox().fromBounds(0D, 0D, 0D, .5D, .5D, .5D);
        //add attack AI for TobieQueen to player next line
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1,  new EntityAIAttackMelee(this,1.2D, true));
//        this.tasks.addTask(1,  new EntityAIAttackMelee(this, EntityTobieSkel.class, 1.0D, true));      
        this.tasks.addTask(3, new EntityAIRestrictSun(this));
        this.tasks.addTask(3, new EntityAIFleeSun(this, 1.0D));
        this.tasks.addTask(4, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(0, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(0, new EntityAIWatchClosest(this, EntityTobieSkel.class, 8.0F));
        
        this.tasks.addTask(4, new EntityAILookIdle(this));
        this.targetTasks.addTask(0, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(0, new EntityAINearestAttackableTarget(this, EntityTobieSkel.class, 2, true, false, IMob.MOB_SELECTOR));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityGlistreWolf.class, 3, true, false, IMob.MOB_SELECTOR));
      

        if (world != null && !world.isRemote)
        {
            this.setCombatTask();
        }
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
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(150.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.34000000417232513D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
 //     raise health from 15.0D to 705.0D
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(705.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(10.0D);
    }

    protected void entityInit()
    {
        super.entityInit();
        //changed from 13 to 24 can go up to 31 better use packets
//        this.dataWatcher.addObject(13, new Byte((byte)0));
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
    protected String getLivingSound()
    {
        return "mob.skeleton.say";
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
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
/*  public void onLivingUpdate()
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
/*1.8 UPDATE TOOK OUT PARTICLES FOR NOW
 
    @Override
    public void onLivingUpdate(){
        if (this.getHealth() < this.getMaxHealth() && this.ticksExisted % 20 == 0)
        {
         // this.heal(1.0F);
            this.playSound("mob.blaze.hit", 1.2F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
//1.8 update added blockpos
          BlockPos blockpos = new BlockPos(posX + (rand.nextDouble() - 0.5D) * (double)width, posY + (rand.nextDouble() * (double)height), posZ + (rand.nextDouble() - 0.5D) * (double)width, 1.0D);
    	     worldObj.spawnParticle("portal", posX + (rand.nextDouble() - 0.5D) * (double)width, posY + (rand.nextDouble() * (double)height), posZ + (rand.nextDouble() - 0.5D) * (double)width, 1.0D, 0.0D, 0.0D);

        }
        
    	worldObj.spawnParticle("reddust", posX + (rand.nextDouble() - 0.5D) * (double)width, posY + (rand.nextDouble() * (double)height), posZ + (rand.nextDouble() - 0.5D) * (double)width, 1.0D, 0.0D, 0.0D);
    	super.onLivingUpdate();
    	}*/

    /**
     * Called when the mob's health reaches 0.
     */
 /*   public void onDeath(DamageSource damageSource)
    {
        super.onDeath(damageSource);

        if (damageSource.getSourceOfDamage() instanceof EntityBlasterBolt && damageSource.getEntity() instanceof EntityPlayer)
        {
            EntityPlayer entityplayer = (EntityPlayer)damageSource.getEntity();
            double d0 = entityplayer.posX - this.posX;
            double d1 = entityplayer.posZ - this.posZ;

            if (d0 * d0 + d1 * d1 >= 2500.0D)
            {
            	
                entityplayer.triggerAchievement(AchievementList.snipeSkeleton);
            }
        }
    }*/

    

    protected Item getDropItem()
    {
        return ItemRegistry.ender_gun;
    }

    /**
     * Drop 0-2 items of this living's type. @param par1 - Whether this entity has recently been hit by a player. @param
     * par2 - Level of Looting used to kill this mob.
     */
    protected void dropFewItems(boolean hit, int lootingLev)
    {
        int j;
        int k;

        if (this.getSkeletonType().getId() == 0)
        {
            j = this.rand.nextInt(3 + lootingLev) - 1;

            for (k = 0; k < j; ++k)
            {
                this.dropItem(ItemRegistry.chain_chestplate_1, 1);
            }
        
        	j = this.rand.nextInt(3 + lootingLev) - 1;

        	for (k = 0; k < j; ++k)
        	{
            this.dropItem(ItemRegistry.nausea_protection, 1);
        	}
        	
        	j = this.rand.nextInt(3 + lootingLev) + 1;

        	for (k = 0; k < j; ++k)
        	{
            this.dropItem(ItemRegistry.secret_book, 1);
            /* removed in 1.10.2 no need for itemstack anymore
        	ItemRegistry instance = new ItemRegistry();
        	ItemStack secretBook = instance.getSecretBook();
        	secretBook.getItem();
        	this.entityDropItem(secretBook, 1.0F); */
        	}
        
        	j = this.rand.nextInt(3 + lootingLev) - 1;

        	for (k = 0; k < j; ++k)
        	{	
        	this.dropItem(GlistreEntityRegistry.splash_poison_protection, 1);
        	} 
        	//1.8 way to make 20% chance of drop
        	if(rand.nextInt(100)<20){
        		int p;
                int q;
                
            	p = this.rand.nextInt(3 + lootingLev) + 1;

        	for (q = 0; q < p; ++q)
            // what does float do here?  likelihood of drop?     	       	
               {
            	
            	this.entityDropItem(new ItemStack(ItemRegistry.blaster_gun_1, 1, 1), 1.0F);
               }
        	for (q = 0; q < p; ++q)
                // what does float do here?  likelihood of drop?     	       	
                   {
                	
                	this.entityDropItem(new ItemStack(GlistreEntityRegistry.tobie_worst_projectile_1, 1, 1), 1.0F);
                   }

        	}
        }
    }

/*    @Override
    protected void dropRareDrop(int p_70600_1_)
    { 
        if (this.getSkeletonType() == 1)
        	
        // what does float do here?       	       	
           {
        	this.entityDropItem(new ItemStack(ItemRegistry.blaster_gun_1, 1, 1), 0.0F);
           }
        //make non-wither TobySkeleton guardian drop rare drop
        else 
        {
            this.entityDropItem(new ItemStack(GlistreEntityRegistry.tobie_worst_projectile_1, 1, 1), 0.0F);
        } 
    }*/

    
    /**
     * Makes entity wear random armor based on difficulty
     */
/*   protected void addRandomArmor()
    {
 //       super.addRandomArmor();
       this.setCurrentItemOrArmor(0, new ItemStack(ItemRegistry.sceptre_1));
        // sets the ability for the RayGun to drop which is different than enderGun don't want RayGun for player
        this.equipmentDropChances[0] = 0.0F;
    }*/
    /**
     * Gives armor or weapon for entity based on given DifficultyInstance
     */
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
    {
        super.setEquipmentBasedOnDifficulty(difficulty);
        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(ItemRegistry.sceptre_1));
    }

   public IEntityLivingData func_180482_a(DifficultyInstance difficulty, IEntityLivingData entitylivingdata)
    {
		 //changed .func_180482_a to #onInitialSpawn 1.8.9
	   entitylivingdata = super.onInitialSpawn(difficulty, entitylivingdata);

        if (this.worldObj.provider instanceof WorldProviderHell && this.getRNG().nextInt(5) > 0)
        {
            this.tasks.addTask(2, this.aiAttackOnCollide);
            this.setSkeletonType(SkeletonType.WITHER);
//            this.setCurrentItemOrArmor(0, new ItemStack(ItemRegistry.sceptre_1));
            this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(ItemRegistry.sceptre_1));

            this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
 // sets the ability for the RayGun to drop which is different than blaster dont want RayGun for player
//            this.equipmentDropChances[0] = 0.0F;
            this.inventoryArmorDropChances[EntityEquipmentSlot.HEAD.getIndex()] = 0.0F;

            
        }
        else
        {
            this.tasks.addTask(0, this.aiArrowAttack);
//            this.addRandomArmor();
            this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(ItemRegistry.tobie_gun_1));           
//            this.setCurrentItemOrArmor(0, new ItemStack(ItemRegistry.tobie_gun_1));
            this.enchantEquipment();
        }

        this.setCanPickUpLoot(this.rand.nextFloat() < 0.55F * difficulty.getClampedAdditionalDifficulty());
 //       this.setCanPickUpLoot(this.rand.nextFloat() < 0.55F * this.worldObj.(this.posX, this.posY, this.posZ));

        if (this.getItemStackFromSlot(EntityEquipmentSlot.HEAD) == null)
 //       if (this.getEquipmentInSlot(4) == null)
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
   public void enchantEquipment()
   {
       float f = 1.0F;

       if (this.getHeldItemMainhand() != null && this.rand.nextFloat() < 0.25F * f)
       {
           EnchantmentHelper.addRandomEnchantment(this.rand, this.getHeldItemMainhand(), (int)(5.0F + f * (float)this.rand.nextInt(18)), true);
       }

  //     for (int i = 0; i < 4; ++i)
  //     {
    	   
 //1.8 update not doubt this probably will not work :  change func_130225_q(i) to this getHeldItem   and dleteled for loop        for (int i = 0; i < 4; ++i)
    //       {	   
           ItemStack itemstack = this.getHeldItemMainhand();

           if (itemstack != null && this.rand.nextFloat() < 0.5F * f)
           {
               EnchantmentHelper.addRandomEnchantment(this.rand, itemstack, (int)(5.0F + f * (float)this.rand.nextInt(18)), true);
           }
 //      }
   }


    /**
     * sets this entity's combat AI.
     */
    public void setCombatTask()
    {
        this.tasks.removeTask(this.aiAttackOnCollide);
        this.tasks.removeTask(this.aiArrowAttack);

        ItemStack itemstack = this.getHeldItemMainhand();

        if (itemstack != null && itemstack.getItem() == ItemRegistry.sceptre_1)
        {
            this.tasks.addTask(0, this.aiArrowAttack);
        }
        else
        {
            this.tasks.addTask(1, this.aiAttackOnCollide);
        }
    }

    /**
     * Attack the specified entity using a ranged attack.
     */
    public void attackEntityWithRangedAttack(EntityLivingBase entity, float distance)
    {
        EntityBlasterBolt entityblasterbolt = new EntityBlasterBolt(this.worldObj, this, entity, 1.6F, (float)(14 - this.worldObj.getDifficulty().getDifficultyId() * 4));
    	//48 == power; 49 == punch
        int i = EnchantmentHelper.getMaxEnchantmentLevel(Enchantment.getEnchantmentByID(48), this);
        int j = EnchantmentHelper.getMaxEnchantmentLevel(Enchantment.getEnchantmentByID(49), this);
        entityblasterbolt.setDamage((double)(distance * 2.0F) + this.rand.nextGaussian() * 0.25D + (double)((float)this.worldObj.getDifficulty().getDifficultyId() * 0.11F));

        if (i > 0)
        {
            entityblasterbolt.setDamage(entityblasterbolt.getDamage() + (double)i * 0.5D + 0.5D);
        }

        if (j > 0)
        {
            entityblasterbolt.setKnockbackStrength(j);
        }
        //50==flame
        if (EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(50), this.getHeldItemMainhand()) > 0 || this.getSkeletonType().getId() == 1)
        {
            entityblasterbolt.setFire(100);
        }

//        this.playSound("glistremod:laserblaster", 1.2F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        this.playSound(SoundEvent.REGISTRY.getObject(new ResourceLocation(Reference.MODID, "laser_blaster")), 1.2F, 1.0F );
        
        this.worldObj.spawnEntityInWorld(entityblasterbolt);
        
        //next part shoots fireballs from Tobie Queen
        //calculates the x,y,z DISTANCE between the projectile and the target. getLook() only gives the DIRECTION.

            double d0 = entity.posX - this.posX;
            double d1 = entity.getEntityBoundingBox().minY + (double)(entity.height / 2.0F) - (this.posY + (double)(this.height / 2.0F));
            double d2 = entity.posZ - this.posZ;
            //calculating the accuracy (randomness) of the projectile. (The square root of the total distance divided by half. So, larger distances are more accurately fired.)
 
            float f1 = MathHelper.sqrt_float(distance) * 0.5F;
            //1.8 update not sure about next added blockpos
            BlockPos pos = new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ);
 //           this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1009, blockpos, 0);
            this.worldObj.playEvent((EntityPlayer)null, 1009, pos, 0);
            for (int k = 0; k < 1; ++k)
            {
         //This sets the x,y,z vector of the projectile, it uses the distance numbers calculated above, times a random number. (Because the Blaze fires randomly), 
         //you can take out "this.rand.nextGaussian() * (double)f1" and the projectile will always hit you dead on.    

                EntityLargeFireball entitylargefireball = new EntityLargeFireball(this.worldObj, this, d0 + this.rand.nextGaussian() * (double)f1, d1, d2 + this.rand.nextGaussian() * (double)f1);
        //This is where the projectile starts, the 0.5F is added to half the height to place it near normal size mobs mouth area , but for Queen should be 8.0 chest area, 8.9 mouth but
                // lower if preRenderOffset is higher value 4.0 puts at chest.            
   //mob.ghast.fireball
                entitylargefireball.posY = this.posY + (double)(this.height / 2.0F) + 4.0D;
//                this.playSound("mob.blaze.hit", 1.2F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
                this.playSound(SoundEvents.ENTITY_BLAZE_SHOOT, 1.2F, 1.0F/ (this.getRNG().nextFloat() * 0.4F + 0.8F));

                this.worldObj.spawnEntityInWorld(entitylargefireball);
            }
        }
            
            
    

    /**
     * Return this skeleton's type.
     */
    /*   public int getSkeletonType()
    {
        return this.dataWatcher.getWatchableObjectByte(13);
    }*/
    public SkeletonType getSkeletonType()
    {
        return SkeletonType.getByOrdinal(((Integer)this.dataManager.get(SKELETON_VARIANT)).intValue());
    }
    /**
     * Set this skeleton's type.
     */

    public void setSkeletonType(SkeletonType skeletontype)
    {
        this.dataManager.set(SKELETON_VARIANT, Integer.valueOf(skeletontype.getId()));
        this.isImmuneToFire = skeletontype == SkeletonType.WITHER;
        this.updateSize(skeletontype);
    }
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

    public void readEntityFromNBT(NBTTagCompound tagCompound)//this loads the mob to disc
    {
        super.readEntityFromNBT(tagCompound);
        
  //      this.onGround = tagCompound.getByte("onGround") == 1;

        if (tagCompound.hasKey("SkeletonType", 99))
        {
            // byte b0 = tag.getByte("SkeletonType");
         	int i = tagCompound.getByte("SkeletonType");
            this.setSkeletonType(SkeletonType.getByOrdinal(i));
        }

        this.setCombatTask();
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */

    public void writeEntityToNBT(NBTTagCompound tagCompound)//this saves the mob to disc
    {
        super.writeEntityToNBT(tagCompound);
        tagCompound.setByte("SkeletonType", (byte)this.getSkeletonType().getId());
 //       tagCompound.setByte("onGround", (byte)(this.onGround ? 1 : 0));
    }

    /**
     * Sets the held item, or an armor slot. Slot 0 is held item. Slot 1-4 is armor. Params: Item, slot
     */
 /*   public void setCurrentItemOrArmor(int slot, ItemStack itemstack)
    {
        super.setCurrentItemOrArmor(slot, itemstack);

        if (!this.worldObj.isRemote && slot == 0)
        {
            this.setCombatTask();
        }
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
     * Returns the Y Offset of this entity.
     */
    public double getYOffset()
    {
        return super.getYOffset() - 0.5D;
    }
    
    @Override
    public ItemStack getHeldItemMainhand()
    {
        return defaultHeldItem;
    }
    //added next two methods 1.10.2
    /**
     * Add the given player to the list of players tracking this entity. For instance, a player may track a boss in
     * order to view its associated boss bar.
     */
    public void addTrackingPlayer(EntityPlayerMP player)
    {
        super.addTrackingPlayer(player);
        this.bossInfo.addPlayer(player);
    }

    /**
     * Removes the given player from the list of players tracking this entity. See {@link Entity#addTrackingPlayer} for
     * more information on tracking.
     */
    public void removeTrackingPlayer(EntityPlayerMP player)
    {
        super.removeTrackingPlayer(player);
        this.bossInfo.removePlayer(player);
    }

}