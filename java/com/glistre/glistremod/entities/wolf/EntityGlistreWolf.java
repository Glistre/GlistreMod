package com.glistre.glistremod.entities.wolf;

import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIBeg;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityGlistreWolf extends EntityWolf
{
    private static final DataParameter<Float> DATA_HEALTH_ID = EntityDataManager.<Float>createKey(EntityWolf.class, DataSerializers.FLOAT);
    private static final DataParameter<Boolean> BEGGING = EntityDataManager.<Boolean>createKey(EntityWolf.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> COLLAR_COLOR = EntityDataManager.<Integer>createKey(EntityWolf.class, DataSerializers.VARINT);
    /** Float used to smooth the rotation of the wolf head */
    private float headRotationCourse;
    private float headRotationCourseOld;
    /** true is the wolf is wet else false */
    private boolean isWet;
    /** True if the wolf is shaking else False */
    private boolean isShaking;
    /** This time increases while wolf is shaking and emitting water particles. */
    private float timeWolfIsShaking;
    private float prevTimeWolfIsShaking;
    private EntityAIAttackMelee aiAttackOnCollide = new EntityAIAttackMelee(this, 1.2D, false);

 //   private static final String __OBFID = "CL_00001654";


    public EntityGlistreWolf(World worldIn)
    {
        super(worldIn);
        this.setSize(0.6F, 0.8F);
      //func_179690_a changed to setAvoidsWater 1.8.9
 //       ((PathNavigateGround)this.getNavigator()).setAvoidsWater(true);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, this.aiSit);////////THROWS ERROR???
        this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0D, true));
        this.tasks.addTask(5, new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
        this.tasks.addTask(6, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(8, new EntityAIBeg(this, 8.0F));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(9, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.targetTasks.addTask(4, new EntityAITargetNonTamed(this, EntityAnimal.class, false, new Predicate()
        {
        	@Override
            public boolean apply(Object target)
            {
                return target instanceof EntitySheep || target instanceof EntityRabbit;
            }
        }));
        this.targetTasks.addTask(5, new EntityAINearestAttackableTarget(this, EntitySkeleton.class, false));
        this.setTamed(false);
    }
    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30000001192092896D);

        if (this.isTamed())
        {
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        }
        else
        {
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
        }

//        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
    }

    /**
     * Sets the active target the Task system uses for tracking
     */
    @Override
    public void setAttackTarget(EntityLivingBase target)
    {
        super.setAttackTarget(target);

        if (target == null)
        {
            this.setAngry(false);
        }
        else if (!this.isTamed())
        {
            this.setAngry(true);
        }
    }
    @Override
    protected void updateAITasks()
    {
  //  	changed 18 to 27 can go to 31 better to use packets
        this.dataManager.set(DATA_HEALTH_ID, Float.valueOf(this.getHealth()));
    }
    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(DATA_HEALTH_ID, Float.valueOf(this.getHealth()));
        this.dataManager.register(BEGGING, Boolean.valueOf(false));
        this.dataManager.register(COLLAR_COLOR, Integer.valueOf(EnumDyeColor.RED.getDyeDamage()));
     }
    @Override
    protected void playStepSound(BlockPos pos, Block block)
    {
        this.playSound(SoundEvents.ENTITY_WOLF_STEP, 0.15F, 1.0F);
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound tagCompound)
    {
        super.writeEntityToNBT(tagCompound);
        tagCompound.setBoolean("Angry", this.isAngry());
        tagCompound.setByte("CollarColor", (byte)this.getCollarColor().getDyeDamage());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound tagCompund)
    {
        super.readEntityFromNBT(tagCompund);
        this.setAngry(tagCompund.getBoolean("Angry"));

        if (tagCompund.hasKey("CollarColor", 99))
        {
            this.setCollarColor(EnumDyeColor.byDyeDamage(tagCompund.getByte("CollarColor")));
        }
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    @Override
    protected SoundEvent getAmbientSound()
    {
        return this.isAngry() ? SoundEvents.ENTITY_WOLF_GROWL : (this.rand.nextInt(3) == 0 ? (this.isTamed() && ((Float)this.dataManager.get(DATA_HEALTH_ID)).floatValue() < 10.0F ? SoundEvents.ENTITY_WOLF_WHINE : SoundEvents.ENTITY_WOLF_PANT) : SoundEvents.ENTITY_WOLF_AMBIENT);
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    @Override
    protected SoundEvent getHurtSound()
    {
        return this.getHurtSound();
    }

    /**
     * Returns the sound this mob makes on death.
     */
    @Override
    protected SoundEvent getDeathSound()
    {
        return this.getDeathSound();

    }

    /**
     * Returns the volume for the sounds this mob makes.
     */
    @Override
    protected float getSoundVolume()
    {
        return 0.4F;
    }
    @Override
    protected Item getDropItem()
    {
        return Item.getItemById(-1);
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (!this.worldObj.isRemote && this.isWet && !this.isShaking && !this.hasPath() && this.onGround)
        {
            this.isShaking = true;
            this.timeWolfIsShaking = 0.0F;
            this.prevTimeWolfIsShaking = 0.0F;
            this.worldObj.setEntityState(this, (byte)8);
        }

        if (!this.worldObj.isRemote && this.getAttackTarget() == null && this.isAngry())
        {
            this.setAngry(false);
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void onUpdate()
    {
        super.onUpdate();
        this.headRotationCourseOld = this.headRotationCourse;

        //func_70922_bv changed to #isBegging
        if (this.isBegging())
        {
            this.headRotationCourse += (1.0F - this.headRotationCourse) * 0.4F;
        }
        else
        {
            this.headRotationCourse += (0.0F - this.headRotationCourse) * 0.4F;
        }

        if (this.isWet())
        {
            this.isWet = true;
            this.isShaking = false;
            this.timeWolfIsShaking = 0.0F;
            this.prevTimeWolfIsShaking = 0.0F;
        }
        else if ((this.isWet || this.isShaking) && this.isShaking)
        {
            if (this.timeWolfIsShaking == 0.0F)
            {
                this.playSound(SoundEvents.ENTITY_WOLF_SHAKE, this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
            }

            this.prevTimeWolfIsShaking = this.timeWolfIsShaking;
            this.timeWolfIsShaking += 0.05F;

            if (this.prevTimeWolfIsShaking >= 2.0F)
            {
                this.isWet = false;
                this.isShaking = false;
                this.prevTimeWolfIsShaking = 0.0F;
                this.timeWolfIsShaking = 0.0F;
            }

            if (this.timeWolfIsShaking > 0.4F)
            {
                float f = (float)this.getEntityBoundingBox().minY;
                int i = (int)(MathHelper.sin((this.timeWolfIsShaking - 0.4F) * (float)Math.PI) * 7.0F);

                for (int j = 0; j < i; ++j)
                {
                    float f1 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
                    float f2 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
                    this.worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, this.posX + (double)f1, (double)(f + 0.8F), this.posZ + (double)f2, this.motionX, this.motionY, this.motionZ, new int[0]);
                }
            }
        }
    }

    /**
     * True if the wolf is wet
     */
    @SideOnly(Side.CLIENT)
    @Override
    public boolean isWolfWet()
    {
        return this.isWet;
    }

    /**
     * Used when calculating the amount of shading to apply while the wolf is wet.
     */
    @SideOnly(Side.CLIENT)
    @Override
    public float getShadingWhileWet(float p_70915_1_)
    {
        return 0.75F + (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * p_70915_1_) / 2.0F * 0.25F;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public float getShakeAngle(float p_70923_1_, float p_70923_2_)
    {
        float f2 = (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * p_70923_1_ + p_70923_2_) / 1.8F;

        if (f2 < 0.0F)
        {
            f2 = 0.0F;
        }
        else if (f2 > 1.0F)
        {
            f2 = 1.0F;
        }

        return MathHelper.sin(f2 * (float)Math.PI) * MathHelper.sin(f2 * (float)Math.PI * 11.0F) * 0.15F * (float)Math.PI;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public float getInterestedAngle(float p_70917_1_)
    {
        return (this.headRotationCourseOld + (this.headRotationCourse - this.headRotationCourseOld) * p_70917_1_) * 0.15F * (float)Math.PI;
    }
    @Override
    public float getEyeHeight()
    {
        return this.height * 0.8F;
    }

    /**
     * The speed it takes to move the entityliving's rotationPitch through the faceEntity method. This is only currently
     * use in wolves.
     */
    @Override
    public int getVerticalFaceSpeed()
    {
        return this.isSitting() ? 31 : super.getVerticalFaceSpeed();
    }

    /**
     * Called when the entity is attacked.
     */
    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (this.isEntityInvulnerable(source))
        {
            return false;
        }
        else
        {
            Entity entity = source.getEntity();
            this.aiSit.setSitting(false);

            if (entity != null && !(entity instanceof EntityPlayer) && !(entity instanceof EntityArrow))
            {
                amount = (amount + 1.0F) / 2.0F;
            }

            return super.attackEntityFrom(source, amount);
        }
    }
    @Override
    public boolean attackEntityAsMob(Entity entityIn)
    {
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)((int)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()));

        if (flag)
        {
        	//func_174815_a changed to #applyEnchantments(entityLivingBaseIn, entityIn) 1.8.9
            this.applyEnchantments(this, entityIn);
        }

        return flag;
    }
    @Override
    public void setTamed(boolean tamed)
    {
        super.setTamed(tamed);

        if (tamed)
        {
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        }
        else
        {
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
        }

        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand, @Nullable ItemStack stack)
    {
        ItemStack itemstack = player.inventory.getCurrentItem();

        if (this.isTamed())
        {
            if (itemstack != null)
            {
                if (itemstack.getItem() instanceof ItemFood)
                {
                    ItemFood itemfood = (ItemFood)itemstack.getItem();

                    if (itemfood.isWolfsFavoriteMeat() && this.dataManager.get(DATA_HEALTH_ID).floatValue() < 20.0F)
                    {
                        if (!player.capabilities.isCreativeMode)
                        {
                            --itemstack.stackSize;
                        }

                        this.heal((float)itemfood.getHealAmount(itemstack));

                        if (itemstack.stackSize <= 0)
                        {
                            player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
                        }

                        return true;
                    }
                }
                else if (itemstack.getItem() == Items.DYE)
                {
                    EnumDyeColor enumdyecolor = EnumDyeColor.byDyeDamage(itemstack.getMetadata());

                    if (enumdyecolor != this.getCollarColor())
                    {
                        this.setCollarColor(enumdyecolor);

                        if (!player.capabilities.isCreativeMode && --itemstack.stackSize <= 0)
                        {
                            player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
                        }

                        return true;
                    }
                }
            }

            if (this.isOwner(player) && !this.worldObj.isRemote && !this.isBreedingItem(itemstack))
            {
                this.aiSit.setSitting(!this.isSitting());
                this.isJumping = false;
                this.navigator.clearPathEntity();
                this.setAttackTarget((EntityLivingBase)null);
            }
        }
        else if (itemstack != null && itemstack.getItem() == Items.BONE && !this.isAngry())
        {
            if (!player.capabilities.isCreativeMode)
            {
                --itemstack.stackSize;
            }

            if (itemstack.stackSize <= 0)
            {
                player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
            }

            if (!this.worldObj.isRemote)
            {
                if (this.rand.nextInt(3) == 0)
                {
                    this.setTamed(true);
                    this.navigator.clearPathEntity();
                    this.setAttackTarget((EntityLivingBase)null);
                    this.aiSit.setSitting(true);
                    this.setHealth(20.0F);
                    this.setOwnerId(player.getUniqueID());
                    this.playTameEffect(true);
                    this.worldObj.setEntityState(this, (byte)7);
                }
                else
                {
                    this.playTameEffect(false);
                    this.worldObj.setEntityState(this, (byte)6);
                }
            }

            return true;
        }

        return super.processInteract(player, hand, stack);
    }

    @SideOnly(Side.CLIENT)
    //handleHealthUpdate changed to #handleStatusUpdate 1.8.9
    public void handleStatusUpdate(byte id)
    {
        if (id == 8)
        {
            this.isShaking = true;
            this.timeWolfIsShaking = 0.0F;
            this.prevTimeWolfIsShaking = 0.0F;
        }
        else
        {
            super.handleStatusUpdate(id);
        }
    }

    @SideOnly(Side.CLIENT)
    public float getTailRotation()
    {
        return this.isAngry() ? 1.5393804F : (this.isTamed() ? (0.55F - (this.getMaxHealth() - ((Float)this.dataManager.get(DATA_HEALTH_ID)).floatValue()) * 0.02F) * (float)Math.PI : ((float)Math.PI / 5F));
    }

    /**
     * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
     * the animal type)
     */
    public boolean isBreedingItem(ItemStack stack)
    {
        return stack == null ? false : (!(stack.getItem() instanceof ItemFood) ? false : ((ItemFood)stack.getItem()).isWolfsFavoriteMeat());
    }

    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    @Override
    public int getMaxSpawnedInChunk()
    {
        return 8;
    }

    /**
     * Determines whether this wolf is angry or not.
     */
    @Override
    public boolean isAngry()
    {
        return (((Byte)this.dataManager.get(TAMED)).byteValue() & 2) != 0;
    }

    /**
     * Sets whether this wolf is angry or not.
     */
    @Override
    public void setAngry(boolean angry)
    {
        byte b0 = ((Byte)this.dataManager.get(TAMED)).byteValue();

        if (angry)
        {
            this.dataManager.set(TAMED, Byte.valueOf((byte)(b0 | 2)));
        }
        else
        {
            this.dataManager.set(TAMED, Byte.valueOf((byte)(b0 & -3)));
        }
    }
    @Override
    public EnumDyeColor getCollarColor()
    {
        return EnumDyeColor.byDyeDamage(((Integer)this.dataManager.get(COLLAR_COLOR)).intValue() & 15);
    }
    @Override
    public void setCollarColor(EnumDyeColor collarcolor)
    {
        this.dataManager.set(COLLAR_COLOR, Integer.valueOf(collarcolor.getDyeDamage()));
    }
    @Override
    public EntityWolf createChild(EntityAgeable ageable)
    {
        EntityWolf glistrewolf = new EntityGlistreWolf(this.worldObj);
        UUID uuid = this.getOwnerId();

        if (uuid != null)
        {
            glistrewolf.setOwnerId(uuid);
            glistrewolf.setTamed(true);
        }

        return glistrewolf;
    }
@Override
//func_70918_i changed to setBegging
    public void setBegging(boolean beg)
    {
    this.dataManager.set(BEGGING, Boolean.valueOf(beg));
    }

    /**
     * Returns true if the mob is currently able to mate with the specified mob.
     */
	@Override
    public boolean canMateWith(EntityAnimal otherAnimal)
    {
        if (otherAnimal == this)
        {
            return false;
        }
        else if (!this.isTamed())
        {
            return false;
        }
        else if (!(otherAnimal instanceof EntityWolf))
        {
            return false;
        }
        else
        {
            EntityWolf entitywolf = (EntityWolf)otherAnimal;
            return !entitywolf.isTamed() ? false : (entitywolf.isSitting() ? false : this.isInLove() && entitywolf.isInLove());
        }
    }
	@Override
	//func_70922_bv() changed to #isBegging 1.8.9  
    public boolean isBegging()
    {
        return ((Boolean)this.dataManager.get(BEGGING)).booleanValue();
    }

    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    protected boolean canDespawn()
    {
        return !this.isTamed() && this.ticksExisted > 2400;
    }
    @Override
    //changed func_142018_a to #shouldAttackEntity 1.8.9; #getOwnerEntity changed to #getOwner
    public boolean shouldAttackEntity(EntityLivingBase targetEntity, EntityLivingBase owner)
    {
        if (!(targetEntity instanceof EntityCreeper) && !(targetEntity instanceof EntityGhast))
        {
            if (targetEntity instanceof EntityGlistreWolf)
            {
                EntityWolf entitywolf = (EntityGlistreWolf)targetEntity;

                if (entitywolf.isTamed() && entitywolf.getOwner() == owner)
                {
                    return false;
                }
            }

            return targetEntity instanceof EntityPlayer && owner instanceof EntityPlayer && !((EntityPlayer)owner).canAttackPlayer((EntityPlayer)targetEntity) ? false : !(targetEntity instanceof EntityHorse) || !((EntityHorse)targetEntity).isTame();
        }
        else
        {
            return false;
        }
    }
	@Override
    public boolean canBeLeashedTo(EntityPlayer player)
    {
        return !this.isAngry() && super.canBeLeashedTo(player);
    }
}
