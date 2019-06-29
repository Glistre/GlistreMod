package com.glistre.glistremod.events;

import com.glistre.glistremod.mapgen.MapGenGlistreBase;
import com.glistre.glistremod.mapgen.MapGenGlistreVillage;

import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GlistreModTerrainGenHooks {

	@SubscribeEvent
	public void InitMapGen(InitMapGenGlistreEvent event)
	{
		if(event.type.name() == event.type.VILLAGE.name())
		{
			event.newGen = new MapGenGlistreBase();
		}
		System.out.println("Generating Glistre Village!!!");
	}
}
