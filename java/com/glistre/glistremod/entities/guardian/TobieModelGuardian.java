package com.glistre.glistremod.entities.guardian;

import java.util.Random;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class TobieModelGuardian extends ModelBiped
{
    
    public static Random rand;
    
  //fields
    ModelRenderer head;
    ModelRenderer body;
    ModelRenderer rightarm;
    ModelRenderer leftarm;
    ModelRenderer body2;
    ModelRenderer visor;
  
  public TobieModelGuardian()
  {
    textureWidth = 64;
    textureHeight = 49;
    
      head = new ModelRenderer(this, 12, 0);
      head.addBox(-5F, -10F, -5F, 10, 10, 10);
      head.setRotationPoint(0F, -2F, 0F);
      head.setTextureSize(64, 49);
      head.mirror = true;
      setRotation(head, 0F, 0F, 0F);
      body = new ModelRenderer(this, 17, 21);
      body.addBox(-5F, 0F, -3F, 10, 8, 5);
      body.setRotationPoint(0F, -1F, 0F);
      body.setTextureSize(64, 49);
      body.mirror = true;
      setRotation(body, 0F, 0F, 0F);
      rightarm = new ModelRenderer(this, 48, 22);
      rightarm.addBox(-5F, -2F, -2F, 4, 12, 4);
      rightarm.setRotationPoint(-5F, 1F, 0F);
      rightarm.setTextureSize(64, 49);
      rightarm.mirror = true;
      setRotation(rightarm, 0F, 0F, 0F);
      leftarm = new ModelRenderer(this, 0, 22);
      leftarm.addBox(1F, -2F, -2F, 4, 12, 4);
      leftarm.setRotationPoint(5F, 1F, 0F);
      leftarm.setTextureSize(64, 49);
      leftarm.mirror = true;
      setRotation(leftarm, 0F, 0F, 0F);
      body2 = new ModelRenderer(this, 19, 35);
      body2.addBox(-4F, 4F, -2F, 8, 11, 3);
      body2.setRotationPoint(0F, -1F, 0F);
      body2.setTextureSize(64, 49);
      body2.mirror = true;
      setRotation(body2, 0F, 0F, 0F);
      visor = new ModelRenderer(this, 42, 6);
      visor.addBox(-5F, -8F, -6F, 10, 3, 1);
      visor.setRotationPoint(0F, -2F, 0F);
      visor.setTextureSize(64, 49);
      visor.mirror = true;
      setRotation(visor, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
//    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    head.render(f5);
    body.render(f5);
    rightarm.render(f5);
    leftarm.render(f5);
    body2.render(f5);
    visor.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
  {
      /**
       * Arm Oscillations
       */
      super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
//      float f6 = MathHelper.sin(this.onGround * (float)Math.PI);
//      float f7 = MathHelper.sin((1.0F - (1.0F - this.onGround) * (1.0F - this.onGround)) * (float)Math.PI);
      float f6 = 0.0F;//tried this for 1.8 update not onGround exists
      float f7 = 0.0F;//tried this for 1.8 update not onGround exists
      this.rightarm.rotateAngleZ = 0.0F;
      this.leftarm.rotateAngleZ = 0.0F;
      this.rightarm.rotateAngleY = -(0.1F - f6 * 0.6F);
      this.leftarm.rotateAngleY = 0.1F - f6 * 0.6F;
      this.rightarm.rotateAngleX = 0.0F;
      this.leftarm.rotateAngleX = 0.0F;
      this.rightarm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
      this.leftarm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
      this.rightarm.rotateAngleZ += MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
      this.leftarm.rotateAngleZ -= MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
      this.rightarm.rotateAngleX += MathHelper.sin(par3 * 0.067F) * 0.05F;
      this.leftarm.rotateAngleX -= MathHelper.sin(par3 * 0.067F) * 0.05F;

      /**
       * FullBody Floating
       */
      float oscillate = MathHelper.cos(par3 * 0.09F) * 0.005F;
      
      this.head.rotationPointY += oscillate;
      this.body.rotationPointY += oscillate;
      this.body2.rotationPointY += oscillate;
      this.rightarm.rotationPointY += oscillate;
      this.leftarm.rotationPointY += oscillate;
      this.visor.rotationPointY += oscillate;
  }

}
