package com.glistre.glistremod.entities.blacktobie;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import com.glistre.glistremod.GlistreMod;
import com.glistre.glistremod.entities.guardian.EntityTobieSkel;
import com.glistre.glistremod.entities.wolf.EntityGlistreWolf;
import com.glistre.glistremod.init.GlistreEntityRegistry;
import com.glistre.glistremod.init.ItemRegistry;
import com.glistre.glistremod.projectiles.blaster.EntityBlasterBolt;
import com.glistre.glistremod.reference.Reference;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
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
import net.minecraft.entity.item.EntityItem;
//import net.minecraft.entity.item.EntityItem;
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
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderHell;

public class EntityBlackTobo extends EntitySkeleton implements IRangedAttackMob
{
	private static final DataParameter<Integer> SKELETON_VARIANT = EntityDataManager.<Integer>createKey(EntitySkeleton.class, DataSerializers.VARINT);
	private static final DataParameter<Boolean> SWINGING_ARMS = EntityDataManager.<Boolean>createKey(EntitySkeleton.class, DataSerializers.BOOLEAN);
    private EntityAIAttackRangedBow aiArrowAttack = new EntityAIAttackRangedBow(this, 1.0D, 20, 15.0F);
	//returning false fourth parameter will remove that entity   
    private EntityAIAttackMelee aiAttackOnCollide = new EntityAIAttackMelee(this, 1.2D, false);
    private static final ItemStack defaultHeldItem = new ItemStack(ItemRegistry.tobie_gun_1, 1);

    public AxisAlignedBB boundingBox;

    private int attackTimer;
    
    public EntityBlackTobo(World world)
    {
        super(world);
        //trying to make immune to fire damage not working 
 //       this.isImmuneToFire();

        //trying to raise the hit box
        setSize(1.0F, 4.5F);
//        this.boundingBox.setBounds(minX,minY,minZ,maxX,maxY,maxZ);
        this.boundingBox.intersects(1D, 1D, 1D, 1.0D, 1.0D, 1.0D);
       
//        this.getEntityBoundingBox().fromBounds(0D, 0D, 0D, .5D, .5D, .5D);

        this.setSkeletonType(SkeletonType.NORMAL);
        //add attack AI for TobieQueen to player next line
        this.targetTasks.addTask(0, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.tasks.addTask(0, new EntityAISwimming(this));
        //should match same method above for Double and fourth parameter no need for Player set to false above
        this.tasks.addTask(1,  new EntityAIAttackMelee(this, 1.2D, false));
        this.tasks.addTask(1,  new EntityAIAttackMelee(this,  1.0D, true));      
        this.tasks.addTask(3, new EntityAIRestrictSun(this));
        this.tasks.addTask(3, new EntityAIFleeSun(this, 1.0D));
        this.tasks.addTask(0, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(0, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(0, new EntityAIWatchClosest(this, EntityTobieSkel.class, 8.0F));
        
        this.tasks.addTask(4, new EntityAILookIdle(this));
        this.targetTasks.addTask(0, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityTobieSkel.class, 2, true, false, IMob.MOB_SELECTOR));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityGlistreWolf.class, 3, true, false, IMob.MOB_SELECTOR));
      

/*        if (world != null && !world.isRemote)
        {
            this.setCombatTask();
        }*///removed 1.8.9
    }
   
  
   @Override
	public AxisAlignedBB getCollisionBox(Entity par1Entity) {
    	
		return boundingBox;
	}
	
	@Override
	//#getBoundingBox changed to #getCollisionBoundingBox 1.8.9  //collision defines hitbox //selected defines area around
	public AxisAlignedBB getCollisionBoundingBox() {
		return boundingBox;
	}
	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
 //       this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(100.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.34000000417232513D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(65.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(10.0D);
    }
	@Override
    protected void entityInit()
    {
        super.entityInit();
        //changed 13 to 26 can go up to 31 better to use packets
 //       this.dataWatcher.addObject(13, new Byte((byte)0));
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
    protected void playStepSound(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
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
 /*   public void updateRidden()
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
/*    public void onDeath(DamageSource source)
    {
        super.onDeath(source);
//source.getSourceOfDamage() instanceof EntityBlasterBolt &&
        if ( source.getEntity() instanceof EntityPlayer)
        {
            EntityPlayer entityplayer = (EntityPlayer)source.getEntity();
            double d0 = entityplayer.posX - this.posX;
            double d1 = entityplayer.posZ - this.posZ;

            if (d0 * d0 + d1 * d1 >= 2500.0D)
            {
                entityplayer.triggerAchievement(AchievementList.snipeSkeleton);
            }
        }
    }*/

/*    @Override
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
    	World world = worldObj;
        int j;
        int k;
        
        if (this.getSkeletonType().getId() == 0)
        {    
        	final String KEY = "activated";
            ItemStack toDrop = new ItemStack(ItemRegistry.mighty_sword, 1);
            if(toDrop.getTagCompound() == null)
            {
                toDrop.setTagCompound(new NBTTagCompound());
                toDrop.getTagCompound().setBoolean(KEY, false);

            }
     //       j = this.rand.nextInt(3 + lootingLev) - 1;
    //    	for (k = 0; k < j; ++k)
               
     //         	{
            if (!(worldObj.isRemote)){
            EntityItem dropItem = new EntityItem(worldObj, this.posX, this.posY, this.posZ, toDrop); 

            worldObj.spawnEntityInWorld(dropItem);
            }
 
        	j = this.rand.nextInt(3 + lootingLev) - 1;
              	
            for (k = 0; k < j; ++k)
            {
           	this.entityDropItem(new ItemStack(ItemRegistry.glistre_sword, 1, 1), 1.0F);
            }
        
        	j = this.rand.nextInt(3 + lootingLev) - 1;

        	for (k = 0; k < j; ++k)
        	{
            this.dropItem(ItemRegistry.nausea_protection, 1);
        	}
        	
        	j = this.rand.nextInt(3 + lootingLev);

        	for (k = 0; k < j; ++k)
        	{
        	this.dropItem(ItemRegistry.ancient_book, 1);
        	/* removed in 1.10.2 no need for itemstack anymore
        	ItemRegistry instance = new ItemRegistry();
        	ItemStack ancientBook = instance.getAncientBook();
        	ancientBook.getItem();
        	this.entityDropItem(ancientBook, 1.0F); */
        	}
        
        	j = this.rand.nextInt(3 + lootingLev) - 1;

        	for (k = 0; k < j; ++k)
        	{	
        	this.dropItem(GlistreEntityRegistry.splash_poison_protection, 1);
        	}
  
        	//1.8 way to make 40% chance of drop
        	if(rand.nextInt(100)<40){
        		int p;
                int q;
                
            	p = this.rand.nextInt(3 + lootingLev) + 1;

        	for (q = 0; q < p; ++q)
            // what does float do here?  likelihood of drop?     	       	
               {
            	
            	this.entityDropItem(new ItemStack(GlistreEntityRegistry.tobie_worst_projectile_1, 1, 1), 1.0F);
               }
        	for (q = 0; q < p; ++q)
                // what does float do here?  likelihood of drop?     	       	
                   {
                	
                	this.entityDropItem(new ItemStack(ItemRegistry.chain_helmet_1, 1, 1), 1.0F);
                   }
        	p = this.rand.nextInt(3 + lootingLev);

        	for (q = 0; q < p; ++q)
            	{	
            	this.dropItem(ItemRegistry.custom_bow_1, 1);
            	}
        	}
        }
        
    }

 /*   @Override
    protected void dropRareDrop(int num)
    { 
        
        if (this.getSkeletonType() == 0){
        	int j;
            int k;
            
        	j = this.rand.nextInt(3 + num) + 1;

    	for (k = 0; k < j; ++k)
        // what does float do here?  likelihood of drop?     	       	
           {
        	
        	this.entityDropItem(new ItemStack(GlistreEntityRegistry.tobie_worst_projectile_1, 1, 1), 1.0F);
           }
    	for (k = 0; k < j; ++k)
            // what does float do here?  likelihood of drop?     	       	
               {
            	
            	this.entityDropItem(new ItemStack(ItemRegistry.chain_helmet_1, 1, 1), 1.0F);
               }
         
    	j = this.rand.nextInt(3 + num);

    	for (k = 0; k < j; ++k)
        	{	
        	this.dropItem(ItemRegistry.item_spawn_egg_3, 1);
        	} 
        }  
    }*/

    
    /**
     * Makes entity wear random armor based on difficulty
     */

 /*  protected void addRandomArmor()
    {
 //       super.addRandomArmor();
       this.setCurrentItemOrArmor(0, new ItemStack(ItemRegistry.tobie_gun_1));
        // sets the ability for the RayGun to drop which is different than enderGun don't want RayGun for player
        this.equipmentDropChances[0] = 0.0F;
    }*/
    
   @Override
   //1.8.9 changed func_180482_a to #onInitialSpawn
   public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData entitylivingdata)
    {
	   entitylivingdata = super.onInitialSpawn(difficulty, entitylivingdata);

        if (this.worldObj.provider instanceof WorldProviderHell && this.getRNG().nextInt(5) > 0)
        {
            this.tasks.addTask(2, this.aiAttackOnCollide);
            this.setSkeletonType(SkeletonType.WITHER);
            this.setCurrentItemOrArmor(0, new ItemStack(ItemRegistry.sceptre_1));
            this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
 // sets the ability for the RayGun to drop which is different than blaster dont want RayGun for player
        //    this.equipmentDropChances[0] = 0.0F;
        }
        else
        {
            this.tasks.addTask(0, this.aiArrowAttack);
  //          this.addRandomArmor();  removed or changed 1.8.9
            this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(ItemRegistry.tobie_gun_1));
  //          this.enchantEquipment();//removed 1.8.9
        }

        this.setCanPickUpLoot(this.rand.nextFloat() < 0.55F * difficulty.getClampedAdditionalDifficulty());
 //       this.setCanPickUpLoot(this.rand.nextFloat() < 0.55F * this.worldObj.(this.posX, this.posY, this.posZ));

        if (this.getItemStackFromSlot(EntityEquipmentSlot.HEAD) == null)
        {
            Calendar calendar = this.worldObj.getCurrentDate();

            if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.rand.nextFloat() < 0.25F)
            {
                this.setCurrentItemOrArmor(4, new ItemStack(this.rand.nextFloat() < 0.1F ? Blocks.LIT_PUMPKIN : Blocks.PUMPKIN));
                this.inventoryArmorDropChances[EntityEquipmentSlot.HEAD.getIndex()] = 0.0F;
            }
        }

        return entitylivingdata;
    }
   /**
    * Enchants the entity's armor and held item based on difficulty
    */
/*   @Override
   public void enchantEquipment()
   {
       float f = 1.0F;

       if (this.getHeldItem() != null && this.rand.nextFloat() < 0.25F * f)
       {
           EnchantmentHelper.addRandomEnchantment(this.rand, this.getHeldItem(), (int)(5.0F + f * (float)this.rand.nextInt(18)));
       }

  //     for (int i = 0; i < 4; ++i)
  //     {
    	   
 //1.8 update not doubt this probably will not work :  change func_130225_q(i) to this getHeldItem   and dleteled for loop        for (int i = 0; i < 4; ++i)
    //       {	   
           ItemStack itemstack = this.getHeldItem();

           if (itemstack != null && this.rand.nextFloat() < 0.5F * f)
           {
               EnchantmentHelper.addRandomEnchantment(this.rand, itemstack, (int)(5.0F + f * (float)this.rand.nextInt(18)));
           }
 //      }
   }*/


    /**
     * sets this entity's combat AI.
     */
/*   @Override
    public void setCombatTask()
    {
        this.tasks.removeTask(this.aiAttackOnCollide);
        this.tasks.removeTask(this.aiArrowAttack);
        ItemStack itemstack = this.getHeldItem();

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
 /*       EntityBlasterBolt entityblasterbolt = new EntityBlasterBolt(this.worldObj, this, target, 1.6F, (float)(14 - this.worldObj.getDifficulty().getDifficultyId() * 4));
        int i = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, this.getHeldItem());
        int j = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, this.getHeldItem());
        entityblasterbolt.setDamage((double)(distance * 2.0F) + this.rand.nextGaussian() * 0.25D + (double)((float)this.worldObj.getDifficulty().getDifficultyId() * 0.11F));

        if (i > 0)
        {
            entityblasterbolt.setDamage(entityblasterbolt.getDamage() + (double)i * 0.5D + 0.5D);
        }

        if (j > 0)
        {
            entityblasterbolt.setKnockbackStrength(j);
        }

        if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, this.getHeldItem()) > 0 || this.getSkeletonType() == 1)
        {
            entityblasterbolt.setFire(100);
        }*/
    	EntityBlasterBolt entityblasterbolt = new EntityBlasterBolt(this.worldObj, this, target, 500F, (float)(100));
        double d0 = target.posX - this.posX;
        double d1 = target.getEntityBoundingBox().minY + (double)(target.height / 2.0F) - (this.posY + (double)(this.height / 2.0F));

 //       double d1 = target.posY + (double)target.getEyeHeight() - 1.100000023841858D - entityblasterbolt.posY;
        double d2 = target.posZ - this.posZ;
        float f1 = MathHelper.sqrt_double(d0 * d0 + d2 * d2) * 0.2F;
        entityblasterbolt.setThrowableHeading(d0, d1 + (double)f1, d2, 0.6F, 12.0F);

        this.playSound(SoundEvent.REGISTRY.getObject(new ResourceLocation(Reference.MODID, "laser_blaster")), 1.2F, 1.0F );
        this.worldObj.spawnEntityInWorld(entityblasterbolt);
        

        }

/*    @Override
    public void onLivingUpdate()
    {
      if (this.getHealth() < this.getMaxHealth() && this.ticksExisted % 400 == 0)
      {
       // this.heal(1.0F);
          this.playSound("mob.blaze.hit", 1.2F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
       
  	     worldObj.spawnParticle(EnumParticleTypes.PORTAL, posX + (rand.nextDouble() - 0.5D) * (double)width, posY + (rand.nextDouble() * (double)height), posZ + (rand.nextDouble() - 0.5D) * (double)width, 1.0D, 0.0D, 0.0D);

      }
      else if (this.getHealth() == 0 ){
          this.playSound("glistremod:sceptre_1", 1.2F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
          Minecraft.getMinecraft().thePlayer.addChatMessage(
					new ChatComponentText(EnumChatFormatting.DARK_RED + "You have AWAKENED TOBIE KING!"));
          worldObj.spawnParticle(EnumParticleTypes.PORTAL, posX + (rand.nextDouble() - 0.5D) * (double)width, posY + (rand.nextDouble() * (double)height), posZ + (rand.nextDouble() - 0.5D) * (double)width, 1.0D, 0.0D, 0.0D);
      }
      
      super.onLivingUpdate();
    }*/
   
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

				if(entityplayer.getHeldItemMainhand() !=null &&  entityplayer.getHeldItemMainhand().getItem() == ItemRegistry.mighty_sword){

					entityplayer.addChatComponentMessage(
                    		new TextComponentString(TextFormatting.DARK_GREEN + "Corrupt Tobie in Range location " 
                    + TextFormatting.DARK_RED + " X: " + (int)Math.round(this.posX) 
                    + TextFormatting.GOLD + " | Y: " + (int)Math.round(this.posY) 
                    + TextFormatting.DARK_AQUA +" | Z: " + (int)Math.round(this.posZ)));
			}
			
            }}
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
    public void setSkeletonType(SkeletonType type)
    {
//        this.dataWatcher.updateObject(13, Byte.valueOf((byte)type));
        this.dataManager.set(SKELETON_VARIANT, Integer.valueOf(type.getId()));
        this.isImmuneToFire = type == SkeletonType.WITHER;

    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound tag)//this loads the mob to disc
    {
        super.readEntityFromNBT(tag);

        if (tag.hasKey("SkeletonType", 99))
        {
           // byte b0 = tag.getByte("SkeletonType");
        	int i = tag.getByte("SkeletonType");
            this.setSkeletonType(SkeletonType.getByOrdinal(i));
//            tickCounter = tag.getInteger("tickCounter");
//    		counterEnabled = tag.getBoolean("counterEnabled");
        }

        this.setCombatTask();
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound tag)//this saves the mob to disc
    {
        super.writeEntityToNBT(tag);
        tag.setByte("SkeletonType", (byte)this.getSkeletonType().getId());
//		tag.setInteger("tickCounter", tickCounter);
//		tag.setBoolean("counterEnabled", counterEnabled);
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
     * Sets the held item, or an armor slot. Slot 0 is held item. Slot 1-4 is armor. Params: Item, slot
     */
    public void setCurrentItemOrArmor(int slot, ItemStack itemstack)
    {
    //    super.setCurrentItemOrArmor(slot, itemstack);

 /*       if (!this.worldObj.isRemote && slot == 0)
        {
            this.setCombatTask();
        }*/ //removed 1.8.9
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
    
/*    @Override
	public void setPosition(double par1, double par2, double par3) {
		AxisAlignedBB b = this.getEntityBoundingBox();
		double boxSX = b.maxX - b.minX;
		double boxSY = b.maxY - b.minY;
		double boxSZ = b.maxZ - b.minZ;
		this.getEntityBoundingBox().fromBounds(posX - boxSX/2D, posY, posZ - boxSZ/2D, posX + boxSX/2D, posY + boxSY, posZ + boxSZ/2D);
	}*/
}