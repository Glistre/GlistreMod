package com.glistre.glistremod.effects.potions;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.glistre.glistremod.GlistreMod;
import com.glistre.glistremod.init.ItemRegistry;
import com.glistre.glistremod.reference.Reference;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
//import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

public class VomitusEffect extends Potion {
//substring goes over to the right how ever many you specify, 
//and sets that as the new start. so 1 in this case... AKA if you don't put the slash at the beginning 
//and just make it modid + "textures/folder/icon.png" substring isn't required... or it may even require a colon to specify the domain so modid + ":" + "textures/folder/icon.png"﻿	

//	public static final ResourceLocation iconTexture = new ResourceLocation("glistremod:textures/gui/vomitus_potion_icon.png");

	/**
	 * The icon texture to use in the HUD and inventory GUI.
	 */
	private final ResourceLocation iconTexture;
	
	/** ID value of the potion this effect matches. */
    private int potionID;
    /** The duration of the potion effect */
    private int duration;
    /** The amplifier of the potion effect */
    private int amplifier;

    
    private List<ItemStack> curativeItems;
    
    private int width;
    private int height;
    private int u;
    private int v;

    
    
    //@Deprecated // use the Constructor without potion ID or everything will explode.
 	//public PoisonProtectEffect(ResourceLocation location, boolean isBadEffect, int potionColor){
     public VomitusEffect(boolean isBadEffect, int potionColor, String name){
     	//	public PoisonProtectEffect(int potionID, boolean bad, int potionColor) {
 		super(isBadEffect, potionColor);
 		setPotionName(this, name);
 		iconTexture = new ResourceLocation(Reference.MODID, "textures/potions/" + name + ".png");
 	}
     
 	public VomitusEffect(final boolean isBadEffect, final int liquidR, final int liquidG, final int liquidB, final String name) {
 		this(isBadEffect, new Color(liquidR, liquidG, liquidB).getRGB(), name);
 	}
     
 	 /* Set the registry name of {@code potion} to {@code potionName} and the unlocalised name to the full registry name.
 	 *
 	 * @param potion     The potion
 	 * @param potionName The potion's name
 	 */
 	public static void setPotionName(final Potion potion, final String potionName) {
 		potion.setRegistryName(Reference.MODID, potionName);
 		potion.setPotionName("effect." + potion.getRegistryName().toString());
 	}


	
//this will put Minecraft Icons for potions  use renderInventoryEffect don't use this
/*	@Override
	public Potion setIconIndex(int x, int y) {
		super.setIconIndex(x, y);
		return this;
		}*/

	@SideOnly(Side.CLIENT)
	@Override
	public boolean hasStatusIcon() 
	{
//	    Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("glistremod", "textures/gui/potionicon.png"));
	    return false;
	}
	
	@Override
	public void performEffect(EntityLivingBase entity, int strength) {

		super.performEffect(entity, strength);
		//int timeDelay = 40;
		//9==nausea/confusion
		PotionEffect vomitus = new PotionEffect(Potion.getPotionById(9), 600, 0);
//		PotionEffect vomitus = new PotionEffect(Potion.confusion.id, timeDelay + 2, 1000);
//		vomitus.addCurativeItem(new ItemStack(ItemRegistry.nauseaProtection));
		vomitus.addCurativeItem(new ItemStack(Items.MILK_BUCKET));
		vomitus.getCurativeItems().clear();
    //   if (entity instanceof EntityPlayer)
		entity.addPotionEffect(vomitus);
		vomitus.addCurativeItem(new ItemStack(ItemRegistry.glistre_food_1));
		
		
	}

	@Override
	public boolean isReady(int ticksLeft, int amplifier){ 
		int k = (5 >> amplifier);
		return k < 1 || ticksLeft % k == 0;

	}

/*	@Override
	public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
	    mc.getTextureManager().bindTexture(new ResourceLocation("glistremod", "glistremod:textures/gui/vomitus_potion_icon.png"));
//		mc.getTextureManager().bindTexture(icon);			
	    Gui.drawModalRectWithCustomSizedTexture(x + 5, y + 6, 0, 0, 18, 18, 18, 18);
	}*/
	

	//this next method causes the icon to render
	@Override
	@SideOnly(Side.CLIENT)
	public void renderInventoryEffect(int x, int y, PotionEffect effect, net.minecraft.client.Minecraft mc){
		//ITextureObject texture = Minecraft.getMinecraft().renderEngine.getTexture(icon);

		//if (effect.getPotionID() == 31){//not using this anymore in 1.8.9 no potion IDs
		  if (mc.currentScreen != null)
		{
			 GL11.glEnable(GL11.GL_BLEND);
			 mc.getTextureManager().bindTexture(iconTexture);//always bind texture before drawing
			 mc.currentScreen.drawTexturedModalRect(x + 5, y + 6, u + 9, v + 9, 18, 18);
			 GL11.glDisable(GL11.GL_BLEND);
		}
}

	@Override
	@SideOnly(Side.CLIENT)
	public int getStatusIconIndex(){
		ITextureObject texture = Minecraft.getMinecraft().renderEngine.getTexture(iconTexture);
		Minecraft.getMinecraft().renderEngine.bindTexture(iconTexture);
		
		return super.getStatusIconIndex();
		
	}
	
	public boolean isBadEffect(){
		
		return true;
	}


	public int getPotionID() {
		return this.potionID;
	}
	

}
