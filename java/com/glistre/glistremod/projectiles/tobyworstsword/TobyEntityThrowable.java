package com.glistre.glistremod.projectiles.tobyworstsword;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class TobyEntityThrowable extends EntityThrowable
{
    private int xTile = -1;
    private int yTile = -1;
    private int zTile = -1;
    private Block inTile;
    protected boolean inGround;
    public int throwableShake;
    /** The entity that threw this throwable item. */
    private EntityLivingBase thrower;
    private String throwerName;
    private int ticksInGround;
    private int ticksInAir;
    private static final String __OBFID = "CL_00001723";

    public TobyEntityThrowable(World world)
    {
        super(world);
        this.setSize(0.25F, 0.25F);
    }

    protected void entityInit() {}

    /**
     * Checks if the entity is in range to render by using the past in distance and comparing it to its average edge
     * length * 64 * renderDistanceWeight Args: distance
     */
 /*   @SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        double d1 = this.getEntityBoundingBox().getAverageEdgeLength() * 4.0D;
        d1 *= 64.0D;
        return distance < d1 * d1;
    }*/

    public TobyEntityThrowable(World world, EntityLivingBase entity)
    {
        super(world);
        this.thrower = entity;
        this.setSize(0.25F, 0.25F);
        this.setLocationAndAngles(entity.posX, entity.posY + (double)entity.getEyeHeight(), entity.posZ, entity.rotationYaw, entity.rotationPitch);
        this.posX -= (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
        this.posY -= 0.10000000149011612D;
        this.posZ -= (double)(MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.getYOffset();
        float f = 0.4F;
        this.motionX = (double)(-MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * f);
        this.motionZ = (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * f);
        this.motionY = (double)(-MathHelper.sin((this.rotationPitch + this.func_70183_g()) / 180.0F * (float)Math.PI) * f);
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, this.func_70182_d(), 1.0F);
    }

    public TobyEntityThrowable(World worldIn, double x, double y, double z)
    {
        super(worldIn);
        this.ticksInGround = 0;
        this.setSize(0.25F, 0.25F);
        this.setPosition(x, y, z);
        this.getYOffset();
    }

    protected float func_70182_d()
    {
        return 1.5F;
    }

    protected float func_70183_g()
    {
        return 0.0F;
    }

    /**
     * Similar to setArrowHeading, it's point the throwable entity to a x, y, z direction.
     */
    public void setThrowableHeading(double p_70186_1_, double p_70186_3_, double p_70186_5_, float p_70186_7_, float p_70186_8_)
    {
        float f2 = MathHelper.sqrt_double(p_70186_1_ * p_70186_1_ + p_70186_3_ * p_70186_3_ + p_70186_5_ * p_70186_5_);
        p_70186_1_ /= (double)f2;
        p_70186_3_ /= (double)f2;
        p_70186_5_ /= (double)f2;
        p_70186_1_ += this.rand.nextGaussian() * 0.007499999832361937D * (double)p_70186_8_;
        p_70186_3_ += this.rand.nextGaussian() * 0.007499999832361937D * (double)p_70186_8_;
        p_70186_5_ += this.rand.nextGaussian() * 0.007499999832361937D * (double)p_70186_8_;
        p_70186_1_ *= (double)p_70186_7_;
        p_70186_3_ *= (double)p_70186_7_;
        p_70186_5_ *= (double)p_70186_7_;
        this.motionX = p_70186_1_;
        this.motionY = p_70186_3_;
        this.motionZ = p_70186_5_;
        float f3 = MathHelper.sqrt_double(p_70186_1_ * p_70186_1_ + p_70186_5_ * p_70186_5_);
        this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(p_70186_1_, p_70186_5_) * 180.0D / Math.PI);
        this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(p_70186_3_, (double)f3) * 180.0D / Math.PI);
        this.ticksInGround = 0;
    }

    /**
     * Sets the velocity to the args. Args: x, y, z
     */
    @SideOnly(Side.CLIENT)
    public void setVelocity(double p_70016_1_, double p_70016_3_, double p_70016_5_)
    {
        this.motionX = p_70016_1_;
        this.motionY = p_70016_3_;
        this.motionZ = p_70016_5_;

        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
        {
            float f = MathHelper.sqrt_double(p_70016_1_ * p_70016_1_ + p_70016_5_ * p_70016_5_);
            this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(p_70016_1_, p_70016_5_) * 180.0D / Math.PI);
            this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(p_70016_3_, (double)f) * 180.0D / Math.PI);
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        this.lastTickPosX = this.posX;
        this.lastTickPosY = this.posY;
        this.lastTickPosZ = this.posZ;
        super.onUpdate();

        if (this.throwableShake > 0)
        {
            --this.throwableShake;
        }

        if (this.inGround)
        {
            if (this.worldObj.getBlockState(new BlockPos(this.xTile, this.yTile, this.zTile)).getBlock() == this.inTile)
            {
                ++this.ticksInGround;

                if (this.ticksInGround == 1200)
                {
                    this.setDead();
                }

                return;
            }

            this.inGround = false;
            this.motionX *= (double)(this.rand.nextFloat() * 0.2F);
            this.motionY *= (double)(this.rand.nextFloat() * 0.2F);
            this.motionZ *= (double)(this.rand.nextFloat() * 0.2F);
            this.ticksInGround = 0;
            this.ticksInAir = 0;
        }
        else
        {
            ++this.ticksInAir;
        }

        Vec3d vec3 = new Vec3d(this.posX, this.posY, this.posZ);
        Vec3d vec31 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        RayTraceResult raytraceresult = this.worldObj.rayTraceBlocks(vec3, vec31);
        vec3 = new Vec3d(this.posX, this.posY, this.posZ);
        vec31 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

        if (raytraceresult != null)
        {
            vec31 = new Vec3d(raytraceresult.hitVec.xCoord, raytraceresult.hitVec.yCoord, raytraceresult.hitVec.zCoord);
        }

        if (!this.worldObj.isRemote)
        {
            Entity entity = null;
            List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
            double d0 = 0.0D;
            EntityLivingBase entitylivingbase = this.getThrower();

            for (int j = 0; j < list.size(); ++j)
            {
                Entity entity1 = (Entity)list.get(j);

                if (entity1.canBeCollidedWith() && (entity1 != entitylivingbase || this.ticksInAir >= 5))
                {
                    float f = 0.3F;
                    AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand((double)f, (double)f, (double)f);
                    RayTraceResult raytraceresult1 = axisalignedbb.calculateIntercept(vec3, vec31);

                    if (raytraceresult1 != null)
                    {
                        double d1 = vec3.distanceTo(raytraceresult1.hitVec);

                        if (d1 < d0 || d0 == 0.0D)
                        {
                            entity = entity1;
                            d0 = d1;
                        }
                    }
                }
            }

            if (entity != null)
            {
                raytraceresult = new RayTraceResult(entity);
            }
        }

        if (raytraceresult != null)
        {
            if (raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK && this.worldObj.getBlockState(raytraceresult.getBlockPos()).getBlock() == Blocks.PORTAL)
            {
            	  this.setPortal(raytraceresult.getBlockPos());
             //   this.setInPortal();  this changed in 1.8.9
            }
            else
            {
                this.onImpact(raytraceresult);
            }
        }

        this.posX += this.motionX;
        this.posY += this.motionY;
        this.posZ += this.motionZ;
        float f1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

        for (this.rotationPitch = (float)(Math.atan2(this.motionY, (double)f1) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
        {
            ;
        }

        while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
        {
            this.prevRotationPitch += 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw < -180.0F)
        {
            this.prevRotationYaw -= 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
        {
            this.prevRotationYaw += 360.0F;
        }

        this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
        this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
        float f2 = 0.99F;
        float f3 = this.getGravityVelocity();

        if (this.isInWater())
        {
            for (int i = 0; i < 4; ++i)
            {
                float f4 = 0.25F;
                this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * (double)f4, this.posY - this.motionY * (double)f4, this.posZ - this.motionZ * (double)f4, this.motionX, this.motionY, this.motionZ, new int[0]);
            }

            f2 = 0.8F;
        }

        this.motionX *= (double)f2;
        this.motionY *= (double)f2;
        this.motionZ *= (double)f2;
        this.motionY -= (double)f3;
        this.setPosition(this.posX, this.posY, this.posZ);
    }

    /**
     * Gets the amount of gravity to apply to the thrown entity with each tick.
     */
    protected float getGravityVelocity()
    {
        return 0.03F;
    }
    /**
     * Called when this EntityThrowable hits a block or entity.
     */
    protected abstract void onImpact(RayTraceResult mop);

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound tagCompound)
    {
        tagCompound.setShort("xTile", (short)this.xTile);
        tagCompound.setShort("yTile", (short)this.yTile);
        tagCompound.setShort("zTile", (short)this.zTile);
        ResourceLocation resourcelocation = (ResourceLocation)Block.REGISTRY.getNameForObject(this.inTile);
        tagCompound.setString("inTile", resourcelocation == null ? "" : resourcelocation.toString());
        tagCompound.setByte("shake", (byte)this.throwableShake);
        tagCompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));

        if ((this.throwerName == null || this.throwerName.length() == 0) && this.thrower instanceof EntityPlayer)
        {
            this.throwerName = this.thrower.getName();
        }

        tagCompound.setString("ownerName", this.throwerName == null ? "" : this.throwerName);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound tagCompund)
    {
        this.xTile = tagCompund.getShort("xTile");
        this.yTile = tagCompund.getShort("yTile");
        this.zTile = tagCompund.getShort("zTile");

        if (tagCompund.hasKey("inTile", 8))
        {
            this.inTile = Block.getBlockFromName(tagCompund.getString("inTile"));
        }
        else
        {
            this.inTile = Block.getBlockById(tagCompund.getByte("inTile") & 255);
        }

        this.throwableShake = tagCompund.getByte("shake") & 255;
        this.inGround = tagCompund.getByte("inGround") == 1;
        this.throwerName = tagCompund.getString("ownerName");

        if (this.throwerName != null && this.throwerName.length() == 0)
        {
            this.throwerName = null;
        }
    }

    public EntityLivingBase getThrower()
    {
        if (this.thrower == null && this.throwerName != null && this.throwerName.length() > 0)
        {
            this.thrower = this.worldObj.getPlayerEntityByName(this.throwerName);
        }

        return this.thrower;
    }
}