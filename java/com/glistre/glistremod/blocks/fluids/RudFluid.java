package com.glistre.glistremod.blocks.fluids;
 
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
 
public class RudFluid extends Fluid
{
    public static final String name = "rud";
//    public static final RudFluid instance = new RudFluid();
 
    public RudFluid(String name2, ResourceLocation still, ResourceLocation flowing)
    {
        super(name, new ResourceLocation("glistremod:blocks/rud_still"), new ResourceLocation("glistremod:blocks/rud_flowing"));
        density = 800;
        isGaseous = false;
    }
    
    @Override
    public int getColor()
    {
     //   return 0xFFFF0000;
    	return 0xFF993333;
    }
 
}