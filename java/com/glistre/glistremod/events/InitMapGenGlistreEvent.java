package com.glistre.glistremod.events;

import net.minecraftforge.fml.common.eventhandler.Event;

import com.glistre.glistremod.mapgen.MapGenGlistreBase;

import net.minecraft.world.gen.MapGenBase;

public class InitMapGenGlistreEvent extends Event
{
    /** Use CUSTOM to filter custom event types
     */
    public static enum EventType { CAVE, MINESHAFT, NETHER_BRIDGE, NETHER_CAVE, RAVINE, SCATTERED_FEATURE, STRONGHOLD, VILLAGE, OCEAN_MONUMENT, CUSTOM }

    public final EventType type;
    public final MapGenGlistreBase originalGen;
    public MapGenGlistreBase newGen;

    InitMapGenGlistreEvent(EventType type, MapGenGlistreBase original)
    {
        this.type = type;
        this.originalGen = original;
        this.newGen = original;
    }
}