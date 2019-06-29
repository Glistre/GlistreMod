package com.glistre.glistremod.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticlePortal;
import net.minecraft.world.World;

public class EntityPortalFreonFX extends ParticlePortal{

    /** Reference to the World object. */
    public World worldObj;
    public double posX;
    /** Entity position Y */
    public double posY;
    /** Entity position Z */
    public double posZ;
    /** Entity motion X */
    /** How wide this entity is considered to be */
    public float width;
    /** How high this entity is considered to be */
    public float height;
	public EntityPortalFreonFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn,
            double xSpeedIn, double ySpeedIn, double zSpeedIn) 
    {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
        setParticleTextureIndex(82); // see Jabelar list happy villager four-pointed stars 82, portalparticle shapes 0-7, 7 rainbow
        particleScale = 2.0F;
        setRBGColorF(1.000f, 0.843f, 0.000f); //red, green, blue? this is mostly green
    }

	
/*	@Override
    protected void ParticlePortal(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn)
    {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
        this.motionX = xSpeedIn;
        this.motionY = ySpeedIn;
        this.motionZ = zSpeedIn;
        this.posX = xCoordIn;
        this.posY = yCoordIn;
        this.posZ = zCoordIn;
        this.portalPosX = this.posX;
        this.portalPosY = this.posY;
        this.portalPosZ = this.posZ;
        float f = this.rand.nextFloat() * 0.6F + 0.4F;
        this.particleScale = this.rand.nextFloat() * 0.2F + 0.5F;
        this.portalParticleScale = this.particleScale;
        this.particleRed = f * 0.9F;
        this.particleGreen = f * 0.3F;
        this.particleBlue = f;
        this.particleMaxAge = (int)(Math.random() * 10.0D) + 40;
        this.setParticleTextureIndex((int)(Math.random() * 8.0D));
    }*/
	
	

}
