package com.glistre.glistremod.projectiles.blaster;

import com.glistre.glistremod.init.GlistreEntityRegistry;
import com.glistre.glistremod.projectiles.tobyworstsword.TobyEntityProjectile;
import com.glistre.glistremod.projectiles.tobyworstsword.TobyRenderProjectile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class RendreGlistreFactory implements IRenderFactory<EntityBlasterBolt>{
	
	public static final RendreGlistreFactory FACTORY = new RendreGlistreFactory();
	 
	
	@Override
	public Render<? super EntityBlasterBolt> createRenderFor(RenderManager manager){
		
		return new RendreBlast(manager);

	};

	
/*	@Override
	public Render createRenderFor(RenderManager manager) {
	return new RendreBlast(manager);

	}*/

}
