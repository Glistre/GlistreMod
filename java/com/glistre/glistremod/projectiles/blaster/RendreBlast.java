

package com.glistre.glistremod.projectiles.blaster;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraftforge.fml.relauncher.SideOnly;

import com.glistre.glistremod.GlistreMod;
import com.glistre.glistremod.projectiles.blaster.EntityBlasterBolt;
import com.glistre.glistremod.reference.Reference;

@SideOnly(Side.CLIENT)
public class RendreBlast extends Render<EntityBlasterBolt>
{

//, Item blaster_bolt_1, RenderItem renderItem
    public RendreBlast(RenderManager renderManager) {
		//super(Minecraft.getMinecraft().getRenderManager());
		super(renderManager);
	
	}

	private static final ResourceLocation blastTextures = new ResourceLocation(Reference.MODID, "textures/entities/blaster_bolt_1.png");

	
	
	public void doRender(EntityBlasterBolt par1EntityBlasterBolt, double x, double y, double z, float entityYaw, Float partialTicks)
    {
//        this.loadTexture("/GlistreMod/blast.png");
    	this.bindEntityTexture(par1EntityBlasterBolt);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);//added from arrow might make it white
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y, (float)z);
        GlStateManager.rotate(par1EntityBlasterBolt.prevRotationYaw + (par1EntityBlasterBolt.rotationYaw - par1EntityBlasterBolt.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(par1EntityBlasterBolt.prevRotationPitch + (par1EntityBlasterBolt.rotationPitch - par1EntityBlasterBolt.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
        Tessellator tessellator = Tessellator.getInstance();
        
        //1.8 update added WorldRenderer and change var10 to worldrenderer
        //WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        //1.10.2 changed Worldrenderer line above to VertexBuffer below
        VertexBuffer vertexbuffer = tessellator.getBuffer();
        int i = 0;
        float var12 = 0.0F;
        float var13 = 0.5F;
        float var14 = (float)(0 + i * 10) / 32.0F;
        float var15 = (float)(5 + i * 10) / 32.0F;
        float var16 = 0.0F;
        float var17 = 0.15625F;
        float var18 = (float)(5 + i * 10) / 32.0F;
        float var19 = (float)(10 + i * 10) / 32.0F;
        float var20 = 0.05625F;
     // GL11.glEnable(GL12.GL_RESCALE_NORMAL); changed to below
        GlStateManager.enableRescaleNormal();


        float var21 = (float)par1EntityBlasterBolt.arrowShake - partialTicks;

        if (var21 > 0.0F)
        {
            float var22 = -MathHelper.sin(var21 * 3.0F) * var21;
       //     GL11.glRotatef(var22, 0.0F, 0.0F, 1.0F); //changed to below
          GlStateManager.rotate(var22, 0.0F, 0.0F, 1.0F);

        }

        GlStateManager.rotate(45.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(var20, var20, var20);
        GlStateManager.translate(-4.0F, 0.0F, 0.0F);
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
        	GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
            GL11.glNormal3f(0.0F, 0.0F, var20);
            vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
            vertexbuffer.pos(-8.0D, -2.0D, 0.0D).tex((double)var12, (double)var14);
            vertexbuffer.pos(8.0D, -2.0D, 0.0D).tex((double)var13, (double)var14);
            vertexbuffer.pos(8.0D, 2.0D, 0.0D).tex((double)var13, (double)var15);
            vertexbuffer.pos(-8.0D, 2.0D, 0.0D).tex((double)var12, (double)var15);
            tessellator.draw();
        }

        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        super.doRender(par1EntityBlasterBolt, x, y, z, entityYaw, partialTicks);

    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
/*	@Override
    public void doRender(Entity par1EntityBlasterBolt, double par2, double par4, double par6, float entityYaw, float partialTicks)
    {
//       this.doRender((EntityBlasterBolt)par1Entity, par2, par4, par6, par8, par9);
          this.doRender((EntityBlasterBolt)par1EntityBlasterBolt, par2, par4, par6, entityYaw, partialTicks);
    }*/

 /*   @Override
    protected boolean bindEntityTexture(Entity entity)
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
    }*/

	@Override
	public ResourceLocation getEntityTexture(EntityBlasterBolt entity) {

		return blastTextures;
	}


}
