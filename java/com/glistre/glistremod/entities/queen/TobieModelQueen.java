package com.glistre.glistremod.entities.queen;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;

import net.minecraft.client.model.ModelRenderer;


import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.boss.BossStatus;
//import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.util.math.MathHelper;

//@SideOnly(Side.CLIENT)
public class TobieModelQueen extends ModelBiped
{
    
    public static Random rand;
    
  //fields
//    ModelRenderer horns;
    ModelRenderer head;
    ModelRenderer hornRight;
    ModelRenderer hornRight2;
    ModelRenderer hornRight3; 
    ModelRenderer hornRight4;
    ModelRenderer hornRight5;  
    ModelRenderer hornLeft;
    ModelRenderer hornLeft2;
    ModelRenderer hornLeft3;
    ModelRenderer hornLeft4;
    ModelRenderer hornLeft5;   
    ModelRenderer body;
    ModelRenderer rightArm;
    ModelRenderer leftArm;
    ModelRenderer body2;
    ModelRenderer visor;
    

  
  public TobieModelQueen()
  {
    textureWidth = 64;
    textureHeight = 49;
      
      float f6 = 2.0F;
      
      
      
      head = new ModelRenderer(this, 12, 0);
      head.addBox(-5F, -10F, -5F, 10, 10, 10, 0.0F);
      head.setRotationPoint(0F, -2F, 0F);
      head.setTextureSize(64, 49);
      head.mirror = true;
      setRotation(head, 0F, 0F, 0F);

//      this.head.addChild(this.hornRight);
      hornRight = new ModelRenderer(this, 12, 10);
      hornRight.addBox(-1F, -6F, 0F,  1,  5,  2);
      hornRight.setRotationPoint(-2F, -11F, -2F);
      hornRight.setTextureSize(64, 49);
      hornRight.mirror = true;
      setRotation(hornRight, 0F, 0F, 0F);
      hornRight2 = new ModelRenderer(this, 12, 10);
      hornRight2.addBox(-2F, -11F, -1F,  2,  1,  1);
      hornRight2.setRotationPoint(-3F, -6F, -1F);
      hornRight2.setTextureSize(64, 49);
      hornRight2.mirror = true;
      setRotation(hornRight2, 0F, 0F, 0F);
      hornRight3 = new ModelRenderer(this, 12, 12);
      hornRight3.addBox(-1F, -4F, 1F,  1,  2,  1);
      hornRight3.setRotationPoint(-4F, -15F, -3F);
      hornRight3.setTextureSize(64, 49);
      hornRight3.mirror = true;
      setRotation(hornRight3, 0F, 0F, 0F);
      hornRight4 = new ModelRenderer(this, 19, 24);
      hornRight4.addBox(0F, 0F, 0F,  2,  1,  1);
      hornRight4.setRotationPoint(-5F, -20F, -2F);
      hornRight4.setTextureSize(64, 49);
      hornRight4.mirror = true;
      setRotation(hornRight4, 0F, 0F, 0F); 
      hornRight5 = new ModelRenderer(this, 34, 27);
      hornRight5.addBox(0F, -2F, -1F,  1,  3,  1);
      hornRight5.setRotationPoint(-4F, -20F, -1F);
      hornRight5.setTextureSize(64, 49);
      hornRight5.mirror = true;
      setRotation(hornRight5, 0F, 0F, 0F);
//      this.head.addChild(this.hornLeft);
      hornLeft = new ModelRenderer(this, 12, 10);
      hornLeft.addBox(0F, -6F, 0F, 1, 5, 2);
      hornLeft.setRotationPoint(2F, -11F, -2F);
      hornLeft.setTextureSize(64, 49);
      hornLeft.mirror = true;
      setRotation(hornLeft, 0F, 0F, 0F);
      hornLeft2 = new ModelRenderer(this, 12, 10);
      hornLeft2.addBox(0F, 0F, 0F, 2, 1, 1);
      hornLeft2.setRotationPoint(3F, -17F, -2F);
      hornLeft2.setTextureSize(64, 49);
      hornLeft2.mirror = true;
      setRotation(hornLeft2, 0F, 0F, 0F);
      hornLeft3 = new ModelRenderer(this, 12, 12);
      hornLeft3.addBox(0F, -2F, 0F, 1, 2, 1);
      hornLeft3.setRotationPoint(4F, -17F, -2F);
      hornLeft3.setTextureSize(64, 49);
      hornLeft3.mirror = true;
      setRotation(hornLeft3, 0F, 0F, 0F); 
      hornLeft4 = new ModelRenderer(this, 19, 24);
      hornLeft4.addBox(-2F, 0F, 0F, 2, 1, 1);
      hornLeft4.setRotationPoint(5F, -20F, -2F);
      hornLeft4.setTextureSize(64, 49);
      hornLeft4.mirror = true;
      setRotation(hornLeft4, 0F, 0F, 0F); 
      hornLeft5 = new ModelRenderer(this, 34, 27);
      hornLeft5.addBox(0F, 0F, -1F, 1, 2, 1);
      hornLeft5.setRotationPoint(3F, -22F, -1F);
      hornLeft5.setTextureSize(64, 49);
      hornLeft5.mirror = true;
      setRotation(hornLeft5, 0F, 0F, 0F); 

      body = new ModelRenderer(this, 17, 21);
      body.addBox(-5F, 0F, -3F, 10, 8, 5, 0.0F);
      body.setRotationPoint(0F, -1F, 0F);
      body.setTextureSize(64, 49);
      body.mirror = true;
      setRotation(body, 0F, 0F, 0F); 
      rightArm = new ModelRenderer(this, 48, 22);
      rightArm.addBox(-5F, -2F, -2F, 4, 12, 4, 0.0F);
      rightArm.setRotationPoint(-5F, 1F, 0F);
      rightArm.setTextureSize(64, 49);
      rightArm.mirror = true;
      setRotation(rightArm, 0F, 0F, 0F);

      
      leftArm = new ModelRenderer(this, 0, 22);
      leftArm.addBox(1F, -2F, -2F, 4, 12, 4, 0.0F);
      leftArm.setRotationPoint(5F, 1F, 0F);
      leftArm.setTextureSize(64, 49);
      leftArm.mirror = true;
      setRotation(leftArm, 0F, 0F, 0F);
      body2 = new ModelRenderer(this, 19, 35);
      body2.addBox(-4F, 4F, -2F, 8, 21, 3, 0.0F);
      body2.setRotationPoint(0F, -1F, 0F);
      body2.setTextureSize(64, 49);
      body2.mirror = true;
      setRotation(body2, 0F, 0F, 0F);
      visor = new ModelRenderer(this, 42, 6);
      visor.addBox(-5F, -8F, -6F, 10, 3, 1, 0.0F);
      visor.setRotationPoint(0F, -2F, 0F);
      visor.setTextureSize(64, 49);
      visor.mirror = true;
      setRotation(visor, 0F, 0F, 0F);

  }

  @Override 
  public void render(Entity entity, float limbSwingTime, float limbSwingAmountAmp, float rotFloat, float rotYaw, float rotPitch, float partTicks)
  {
//    super.render(entity, limbSwing, limbSwingAmount, rotFloat, rotYaw, rotPitch, partTicks);
    
    setRotationAngles(limbSwingTime, limbSwingAmountAmp, rotFloat, rotYaw, rotPitch, partTicks, entity);
    head.render(partTicks);
    hornRight.render(partTicks);
    hornRight2.render(partTicks);
    hornRight3.render(partTicks);
    hornRight4.render(partTicks);
    hornRight5.render(partTicks);
    
    hornLeft.render(partTicks);
    hornLeft2.render(partTicks);
    hornLeft3.render(partTicks);
    hornLeft4.render(partTicks);
    hornLeft5.render(partTicks);

    body.render(partTicks);   
    rightArm.render(partTicks); 
    leftArm.render(partTicks);
    body2.render(partTicks);
    visor.render(partTicks);
    
    
    
//displays mob boss health bar if implemented IBossDisplayData in Entity class
    //removed 1.10.2 not needed?
  /*      if (entity instanceof IBossDisplayData) {

            BossStatus.setBossStatus((IBossDisplayData) entity, true);

        }*/



    }

  
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  /**
   * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
   * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
   * "far" arms and legs can swing at most.
   */
  public void setRotationAngles(float limbSwingTime, float limbSwingAmountAmp, float rotFloat, float headRotX, float headRotY, float partTicks, Entity entity)
  {//partTicks == scale?
      /**
       * Arm Oscillations
       */
      super.setRotationAngles(limbSwingTime, limbSwingAmountAmp, rotFloat, headRotX, headRotY, partTicks, entity);
//      float f6 = MathHelper.sin(this * (float)Math.PI);//f6 
//      float f7 = MathHelper.sin((1.0F - (1.0F - this.onGround) * (1.0F - this.onGround)) * (float)Math.PI);//f7 
      float f6 = 0.0F;//tried this for 1.8 update not onGround exists
      float f7 = 0.0F;//tried this for 1.8 update not onGround exists
      
      this.rightArm.rotateAngleZ = 0.0F;
      this.leftArm.rotateAngleZ = 0.0F;
      this.rightArm.rotateAngleY = -(0.1F - f6 * 0.6F);
      this.leftArm.rotateAngleY = 0.1F - f6 * 0.6F;
      this.rightArm.rotateAngleX = 0.0F;
      this.leftArm.rotateAngleX = 0.0F;
      this.rightArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
      this.leftArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
      this.rightArm.rotateAngleZ += MathHelper.cos(rotFloat * 0.09F) * 0.05F + 0.05F;
      this.leftArm.rotateAngleZ -= MathHelper.cos(rotFloat * 0.09F) * 0.05F + 0.05F;
      this.rightArm.rotateAngleX += MathHelper.sin(rotFloat * 0.067F) * 0.05F;
      this.leftArm.rotateAngleX -= MathHelper.sin(rotFloat * 0.067F) * 0.05F;

      /**
       * FullBody Floating
       */
      float oscillate = MathHelper.cos(rotFloat * 0.09F) * 0.005F;
      
      this.head.rotationPointY += oscillate;
      this.hornRight.rotationPointY += oscillate;
      this.hornRight2.rotationPointY += oscillate;
      this.hornRight3.rotationPointY += oscillate; 
      this.hornRight4.rotationPointY += oscillate;
      this.hornRight5.rotationPointY += oscillate;     
      this.hornLeft.rotationPointY += oscillate;
      this.hornLeft2.rotationPointY += oscillate;
      this.hornLeft3.rotationPointY += oscillate;
      this.hornLeft4.rotationPointY += oscillate; 
      this.hornLeft5.rotationPointY += oscillate;
      this.body.rotationPointY += oscillate;
      this.body2.rotationPointY += oscillate;
      this.rightArm.rotationPointY += oscillate;
      this.leftArm.rotationPointY += oscillate;
      this.visor.rotationPointY += oscillate;
      
  }
}

