package com.glistre.glistremod.entities.wolf;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GlistreRenderWolf extends RenderLiving
{

    public GlistreRenderWolf(RenderManager renderManager, ModelBase modelBase, float floatShadowSize)
    {
        super(renderManager, modelBase, floatShadowSize);

    }


	/**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    @Override
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return new ResourceLocation("glistremod:textures/entities/glistre_wolf.png");
    }
}
