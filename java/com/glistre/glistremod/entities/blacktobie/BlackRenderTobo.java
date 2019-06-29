package com.glistre.glistremod.entities.blacktobie;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.glistre.glistremod.entities.blacktobie.BlackModelTobo;

@SideOnly(Side.CLIENT)
public class BlackRenderTobo extends RenderBiped
{
    private static final ResourceLocation skeletonTextures = new ResourceLocation("glistremod:textures/entities/black_tobo.png");


    public BlackRenderTobo(RenderManager renderManager, BlackModelTobo myModelTobo, float f)
    {
        super(renderManager, new BlackModelTobo(), 0.5F);
    }

	/**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
   protected void preRenderCallback(EntityBlackTobo tobieskel, float p_77041_2_)
    {
	   
	   //skeleton type == 1 wither; 1.2F, 1.2F, 1.2F == normaal size , increase size without remodeling in Techne to 8.0F, 8.0F, 8.0F
        if (tobieskel.getSkeletonType().getId() == 0)
        {
            GL11.glScalef(1.4F, 1.4F, 1.4F);
        }
    }

   @Override // 1.8 changed func_82422_c() transformHeldFull3DItemLayer()
   public void transformHeldFull3DItemLayer()
   {
       //GL11.glTranslatef(0.09375F, 0.1875F, 0.0F);
		GlStateManager.translate(0.09375F, 0.1875F, 0.0F);
   }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityBlackTobo tobieskel)
    {
        return skeletonTextures;
        		//tobieskel.getSkeletonType() == 1 ? witherSkeletonTextures : skeletonTextures;
    }


    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
   protected void preRenderCallback(EntityLivingBase tobieskel, float p_77041_2_)
    {
        this.preRenderCallback((EntityBlackTobo)tobieskel, p_77041_2_);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity tobieskel)
    {
        return this.getEntityTexture((EntityBlackTobo)tobieskel);
    }
}