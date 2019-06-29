package com.glistre.glistremod.render;

import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import java.util.Calendar;
import net.minecraft.block.Block;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.model.ModelLargeChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import com.glistre.glistremod.blocks.GlistreChest;
import com.glistre.glistremod.reference.Reference;
import com.glistre.glistremod.tileentity.TileEntityGlistreChest;

import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;


@SideOnly(Side.CLIENT)
public class GlistreChestRenderer extends TileEntitySpecialRenderer
{

	private static ResourceLocation largeChestTexture = new ResourceLocation(Reference.MODID + ":" + "textures/blocks/glistre_large_chest.png");
	private static ResourceLocation chestTexture = new ResourceLocation(Reference.MODID + ":" + "textures/blocks/glistre_chest.png");

    private ModelChest modelchest = new ModelChest();
    private ModelChest largeModelChest = new ModelLargeChest();
    private boolean isChristmas;

    public GlistreChestRenderer(RenderManager renderManager)
    {
        Calendar calendar = Calendar.getInstance();

        if (calendar.get(2) + 1 == 12 && calendar.get(5) >= 24 && calendar.get(5) <= 26)
        {
            this.isChristmas = true;
        }
    }

    public void renderTileEntityAt(TileEntityGlistreChest te, double posX, double posY, double posZ, float partialTicks, int destroyStage)
    {
       int i;

        if (!te.hasWorldObj())
        {
            i = 0;
        } 
        else
        {
            Block block = te.getBlockType();           
            i = te.getBlockMetadata();

            if (block instanceof GlistreChest && i == 0)
            {
                try
                {
                ((GlistreChest)block).checkForSurroundingChests(te.getWorld(), te.getPos(), te.getWorld().getBlockState(te.getPos()));
              }
                catch (ClassCastException e)
                {
                    FMLLog.severe("Attempted to render a chest at %d,  %d, %d that was not a chest", te.getPos().getX(), te.getPos().getY(), te.getPos().getZ());
                }
                i = te.getBlockMetadata();
            }

            te.checkForAdjacentChests();
        }
        


        if (te.adjacentChestZNeg == null && te.adjacentChestXNeg == null )
        {
            ModelChest modelchest;

            if (te.adjacentChestXPos == null && te.adjacentChestZPos == null)
            {
                modelchest = this.modelchest;
                this.bindTexture(chestTexture);
            }
            else 
            {             
                modelchest = this.largeModelChest;
                this.bindTexture(largeChestTexture);
            }

            GL11.glPushMatrix();
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glTranslatef((float)posX, (float)posY + 1.0F, (float)posZ + 1.0F);
            GL11.glScalef(1.0F, -1.0F, -1.0F);
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
            short short1 = 0;

            if (i == 2)
            {
                short1 = 180;
            }

            if (i == 3)
            {
                short1 = 0;
            }

            if (i == 4)
            {
                short1 = 90;
            }

            if (i == 5)
            {
                short1 = -90;
            }

            if (i == 2 && te.adjacentChestXPos != null)
            {
                GL11.glTranslatef(1.0F, 0.0F, 0.0F);
            }

            if (i == 5 && te.adjacentChestZPos != null)
            {
                GL11.glTranslatef(0.0F, 0.0F, -1.0F);
            }

            GL11.glRotatef((float)short1, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
            float f1 = te.prevLidAngle + (te.lidAngle - te.prevLidAngle) * partialTicks;
            float f2;

            if (te.adjacentChestZNeg != null)
            {
                f2 = te.adjacentChestZNeg.prevLidAngle + (te.adjacentChestZNeg.lidAngle - te.adjacentChestZNeg.prevLidAngle) * partialTicks;

                if (f2 > f1)
                {
                    f1 = f2;
                }
            }

            if (te.adjacentChestXNeg != null)
            {
                f2 = te.adjacentChestXNeg.prevLidAngle + (te.adjacentChestXNeg.lidAngle - te.adjacentChestXNeg.prevLidAngle) * partialTicks;

                if (f2 > f1)
                {
                    f1 = f2;
                }
            }
            
            f1 = 1.0F - f1;
            f1 = 1.0F - f1 * f1 * f1;
            modelchest.chestLid.rotateAngleX = -(f1 * (float)Math.PI / 2.0F);
            
            modelchest.renderAll();
     
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glPopMatrix();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }

    }
	@Override
	public void renderTileEntityAt(TileEntity te, double posX, double posZ, double p_180535_6_,
			float p_180535_8_, int p_180535_9_) {
			this.renderTileEntityAt((TileEntityGlistreChest)te, posX, posZ, p_180535_6_, p_180535_8_, p_180535_9_);
	}

}
