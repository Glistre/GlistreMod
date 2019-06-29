package com.glistre.glistremod.entities.king;

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

import com.glistre.glistremod.entities.king.TobieModelKing;

@SideOnly(Side.CLIENT)
public class TobieKingRender extends RenderBiped
{
    private static final ResourceLocation tobieKingTextures = new ResourceLocation("glistremod:textures/entities/tobie_king.png");
//    private static final ResourceLocation witherSkeletonTextures = new ResourceLocation("glistremod:textures/entities/TobieKingElizabeth.png");
//    private static final String __OBFID = "CL_00001023";

    public TobieKingRender(RenderManager renderManager, TobieModelKing tobieModelKing, float f)
    {
        super(renderManager, new TobieModelKing(), 0.5F);
    }

	/**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
   protected void preRenderCallback(EntityTobieKing tobieKing, float partTicks)
    {
	   //skeleton type == 1 wither; 1.2F, 1.2F, 1.2F == normal size , increase size without remodeling in Techne to 8.0F, 8.0F, 8.0F
       if (tobieKing.getSkeletonType().getId() == 0)//maybe should be (SkeletonType.NORMAL not sure but WITHER is 1
       {
            GL11.glScalef(2.4F, 2.4F, 2.4F);
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
    protected ResourceLocation getEntityTexture(EntityTobieKing tobieKing)
    {
        return tobieKingTextures;
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */


    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
   protected void preRenderCallback(EntityLivingBase tobieKing, float partTicks)
    {
        this.preRenderCallback((EntityTobieKing)tobieKing, partTicks);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity tobieKing)
    {
        return this.getEntityTexture((EntityTobieKing)tobieKing);
    }
}