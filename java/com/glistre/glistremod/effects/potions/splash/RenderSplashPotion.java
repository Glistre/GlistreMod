package com.glistre.glistremod.effects.potions.splash;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionHelper;
//import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.glistre.glistremod.init.GlistreEntityRegistry;

@SideOnly(Side.CLIENT)
public class RenderSplashPotion extends RenderSnowball<Entity>
{
//    private Item field_94151_a;
	protected final Item someitem;	
//	private int field_94150_f;
    private final RenderItem field_177083_e;//was somethingElse
    
    private static final ResourceLocation splashPotionTextures = new ResourceLocation("glistremod" + ":" + "/textures/items/splash_poison_protection.png");
    
/*    public RenderSplashPotion(RenderManager renderManager)
    {
        super(renderManager);
    }*/

    public RenderSplashPotion(RenderManager renderManager, Item item, RenderItem renderItem) {
    	super (renderManager, item, renderItem);
//   	 this.field_94151_a = item;
   	 this.someitem = item;
//        this.field_94150_f = par2;
   	 this.field_177083_e = renderItem;
    }
    @Override 
    //changed func_177082_d construction from entity to EntityPotion in 1.8.9
    //changed 1.10.2 func... to getStackToRender
    public ItemStack getStackToRender(Entity entityIn)
	{
		return new ItemStack(this.someitem, 1, 0);
	}
	/**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
   @SideOnly(Side.CLIENT)
   @Override
   public void doRender(Entity entityIn, double x, double y, double z, float par8, float partialTicks)
    {
       GL11.glPushMatrix();
       GL11.glTranslatef((float)x, (float)y, (float)z);
       GL11.glEnable(GL12.GL_RESCALE_NORMAL);
       GL11.glScalef(0.5F, 0.5F, 0.5F);
       this.bindEntityTexture(entityIn);
       
       GlStateManager.rotate(180.0F-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
       GlStateManager.rotate(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
     //GL11.glRotatef(180.0F - this.renderManager.playerViewY, 1.5F, 1.0F, 1.5F);  Rotates view of potion from throwing player
//       GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);

//       GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
       

 //      this.bindTexture(this.splashPotionTextures);// DON'T NEED
//changed renderItemModel to renderItem  -->, stack TransformType
       //.renderItemModel(this.func_177082_d(entity), ItemCameraTransforms.TransformType.GROUND);
       this.field_177083_e.renderItem(this.getStackToRender(entityIn), ItemCameraTransforms.TransformType.GROUND);
       GlStateManager.disableRescaleNormal();
       GlStateManager.popMatrix();
//       super.doRender(entity, x, y, z, par8, partialTicks);//dont call or it renders twice 
/*       IIcon icon = this.field_94151_a.getIconFromDamage(this.field_94150_f);

        if (icon != null)
        {
            GL11.glPushMatrix();
            GL11.glTranslatef((float)x, (float)y, (float)z);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glScalef(0.5F, 0.5F, 0.5F);
            this.bindEntityTexture(entity);
            Tessellator tessellator = Tessellator.instance;

            if (icon == ItemPotion.func_94589_d("bottle_splash"))
            {
                int i = PotionHelper.func_77915_a(((EntitySplashProjectile)entity).getPotionDamage(), false);
                float f2 = (float)(i >> 16 & 255) / 255.0F;
                float f3 = (float)(i >> 8 & 255) / 255.0F;
                float f4 = (float)(i & 255) / 255.0F;
                //sets the value of the thrown potion animation
//                GL11.glColor3f(f2, f3, f4);
//                GL11.glColor3f(float red, float green, float blue);
                GL11.glColor3f(0.0F, 0.9F, 0.5F);
                GL11.glPushMatrix();
                this.func_77026_a(tessellator, ItemPotion.func_94589_d("overlay"));
                GL11.glPopMatrix();
                GL11.glColor3f(1.0F, 1.0F, 1.0F);
            }

            this.func_77026_a(tessellator, icon);
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glPopMatrix();
        }*/
       
    }

   //changed constructor in 1.8.9 from Entity to EntityPotion
    @Override
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
    }

    //changed constructor in 1.8.9 from Entity to EntityPotion
	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		
		return this.splashPotionTextures;
	}
}

/*rotate projectile -default for GL11 ....playerViewY =1.0F, 1.0F, 1.0F */
 /*   private void func_77026_a(Tessellator par1Tessellator, IIcon par2Icon)
    {
        float f = par2Icon.getMinU();
        float f1 = par2Icon.getMaxU();
        float f2 = par2Icon.getMinV();
        float f3 = par2Icon.getMaxV();
        float f4 = 1.0F;
        float f5 = 0.5F;
        float f6 = 0.25F;
//GL11.glRotatef(180.0F - this.renderManager.playerViewY, 1.5F, 1.0F, 1.5F);  Rotates view of potion from throwing player
        GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);

        GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        par1Tessellator.startDrawingQuads();
        par1Tessellator.setNormal(0.0F, 1.0F, 0.0F);
        par1Tessellator.addVertexWithUV((double)(0.0F - f5), (double)(0.0F - f6), 0.0D, (double)f, (double)f3);
        par1Tessellator.addVertexWithUV((double)(f4 - f5), (double)(0.0F - f6), 0.0D, (double)f1, (double)f3);
        par1Tessellator.addVertexWithUV((double)(f4 - f5), (double)(f4 - f6), 0.0D, (double)f1, (double)f2);
        par1Tessellator.addVertexWithUV((double)(0.0F - f5), (double)(f4 - f6), 0.0D, (double)f, (double)f2);
        par1Tessellator.draw();
    }
    
 //   GL11.glRotatef(par1EntityArrow.prevRotationYaw + (par1EntityArrow.rotationYaw - par1EntityArrow.prevRotationYaw) * par9 - 90.0F, 0.0F, 1.0F, 0.0F);
}*/
