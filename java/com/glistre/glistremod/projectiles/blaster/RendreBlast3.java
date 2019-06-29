package com.glistre.glistremod.projectiles.blaster;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraftforge.fml.relauncher.SideOnly;

import com.glistre.glistremod.GlistreMod;
import com.glistre.glistremod.projectiles.blaster.EntitySceptreBolt;

@SideOnly(Side.CLIENT)
public class RendreBlast3 extends Render<EntitySceptreBolt>
{


    public RendreBlast3(RenderManager renderManager, Item sceptrebolt_1, RenderItem renderItem) {
		super(renderManager);
	
	}


	private static final ResourceLocation blastTextures = new ResourceLocation("glistremod:textures/entities/blaster_bolt_1.png");

	public void doRender(EntitySceptreBolt par1EntitySceptreBolt, double par2, double par4, double par6, float entityYaw, float partialTicks)
    {
//        this.loadTexture("/GlistreMod/blast.png");
    	this.bindEntityTexture(par1EntitySceptreBolt);
    	GL11.glPushMatrix();
        GL11.glTranslatef((float)par2, (float)par4, (float)par6);
        GL11.glRotatef(par1EntitySceptreBolt.prevRotationYaw + (par1EntitySceptreBolt.rotationYaw - par1EntitySceptreBolt.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(par1EntitySceptreBolt.prevRotationPitch + (par1EntitySceptreBolt.rotationPitch - par1EntitySceptreBolt.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
        Tessellator tessellator = Tessellator.getInstance();
        //1.8 update added WorldRenderer and change var10 to worldrenderer
       // WorldRenderer worldrenderer = var10.getWorldRenderer();
        //1.10.2 changed Worldrenderer line above to VertexBuffer below
        VertexBuffer vertexbuffer = tessellator.getBuffer();
        byte var11 = 0;
        float var12 = 0.0F;
        float var13 = 0.5F;
        float var14 = (float)(0 + var11 * 10) / 32.0F;
        float var15 = (float)(5 + var11 * 10) / 32.0F;
        float var16 = 0.0F;
        float var17 = 0.15625F;
        float var18 = (float)(5 + var11 * 10) / 32.0F;
        float var19 = (float)(10 + var11 * 10) / 32.0F;
        float var20 = 0.05625F;
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        float var21 = (float)par1EntitySceptreBolt.arrowShake - partialTicks;

       if (var21 > 0.0F)
        {
            float var22 = -MathHelper.sin(var21 * 3.0F) * var21;
            GL11.glRotatef(var22, 0.0F, 0.0F, 1.0F);
        }

        GL11.glRotatef(45.0F, 1.0F, 0.0F, 0.0F);
        GL11.glScalef(var20, var20, var20);
        GL11.glTranslatef(-4.0F, 0.0F, 0.0F);
        GL11.glNormal3f(var20, 0.0F, 0.0F);
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexbuffer.pos(-7.0D, -2.0D, -2.0D).tex((double)var16, (double)var18);
        vertexbuffer.pos(-7.0D, -2.0D, 2.0D).tex((double)var17, (double)var18);
        vertexbuffer.pos(-7.0D, -2.0D, 2.0D).tex((double)var17, (double)var19);
        vertexbuffer.pos(-7.0D, 2.0D, -2.0D).tex((double)var16, (double)var19);
        tessellator.draw();
        GL11.glNormal3f(-var20, 0.0F, 0.0F);
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexbuffer.pos(-7.0D, 2.0D, -2.0D).tex((double)var16, (double)var18);
        vertexbuffer.pos(-7.0D, 2.0D, 2.0D).tex((double)var17, (double)var18);
        vertexbuffer.pos(-7.0D, -2.0D, 2.0D).tex((double)var17, (double)var19);
        vertexbuffer.pos(-7.0D, -2.0D, -2.0D).tex((double)var16, (double)var19);
        tessellator.draw();

        for (int var23 = 0; var23 < 4; ++var23)
        {
            GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
            GL11.glNormal3f(0.0F, 0.0F, var20);
            vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
            vertexbuffer.pos(-8.0D, -2.0D, 0.0D).tex((double)var12, (double)var14);
            vertexbuffer.pos(8.0D, -2.0D, 0.0D).tex((double)var13, (double)var14);
            vertexbuffer.pos(8.0D, 2.0D, 0.0D).tex((double)var13, (double)var15);
            vertexbuffer.pos(-8.0D, 2.0D, 0.0D).tex((double)var12, (double)var15);
            tessellator.draw();
        }

        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
        super.doRender(par1EntitySceptreBolt, par2, par4, par6, entityYaw, partialTicks);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
/*    public void doRender(EntitySceptreBolt par1EntitySceptreBolt, double par2, double par4, double par6, float par8, float par9)
    {
//        this.doRender((EntitySceptreBolt)par1Entity, par2, par4, par6, par8, par9);
          this.doRender((EntitySceptreBolt)par1EntitySceptreBolt, par2, par4, par6, par8, par9);
    }*/
    @Override
    protected boolean bindEntityTexture(EntitySceptreBolt entity)
    {
        ResourceLocation resourcelocation = this.getEntityTexture(entity);

        if (resourcelocation == null)
        {
            return false;
        }
        else
        {
            this.bindTexture(resourcelocation);
            return true;
        }
    }
    @Override
    public void bindTexture(ResourceLocation location)
    {
        this.renderManager.renderEngine.bindTexture(location);
    }


    @Override
    protected ResourceLocation getEntityTexture(EntitySceptreBolt entity) {
    	return blastTextures;
    }




}
