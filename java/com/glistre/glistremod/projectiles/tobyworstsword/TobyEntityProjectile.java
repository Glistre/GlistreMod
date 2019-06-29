package com.glistre.glistremod.projectiles.tobyworstsword;

import com.glistre.glistremod.GlistreMod;
import com.glistre.glistremod.entities.blacktobie.EntityBlackTobo;
import com.glistre.glistremod.entities.king.EntityTobieKing;
import com.glistre.glistremod.entities.queen.EntityTobieQueen;
import com.glistre.glistremod.projectiles.tobyworstsword.TobyEntityThrowable;
import com.glistre.glistremod.reference.Reference;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class TobyEntityProjectile extends TobyEntityThrowable
{
    public TobyEntityProjectile(World world)
    {
        super(world);
    }

    public TobyEntityProjectile(World worldIn, EntityLivingBase entity)
    {
        super(worldIn, entity);
    }

    public TobyEntityProjectile(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }

    /**
     * Called when this EntityThrowable hits a block or entity.
     */
    @Override
    protected void onImpact(RayTraceResult mop)
    {
        if (mop.entityHit != null)
        {
            byte b0 = 3; //increases thrown damage

            if (mop.entityHit instanceof EntityTobieQueen)
            {
                b0 = 42;//increases thrown damage against Tobie Queen about ten throws needed
            }else if (mop.entityHit instanceof EntityTobieKing)
            {
            	b0 = 20;//increases thrown damage against Tobie King about ten throws needed
        	}else  if (mop.entityHit instanceof EntityBlackTobo)
            {
            	b0 = 20;//increases thrown damage against Corrupted Tobie about ten throws needed
        	}
//  DamageSource.causeThrownDamage(Entity projectile, Entity indirectSource)
            mop.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), (float)b0);
        }

        for (int i = 0; i < 8; ++i)
        {
// 1.8 changed this Reference.MOD_ID + "laserblaster" to EnumParticleTypes.TYPE
            this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
        }

        if (!this.worldObj.isRemote)
        {
        	this.explode();
            this.setDead();
        }
    }

    private void explode()
    {
        float f = 2.0F;
        this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, f, true);
    }
}
