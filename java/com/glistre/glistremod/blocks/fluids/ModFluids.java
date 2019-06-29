package com.glistre.glistremod.blocks.fluids;

import com.glistre.glistremod.blocks.RudBlock;
import com.glistre.glistremod.reference.Reference;
import com.glistre.glistremod.tabs.TabRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Set;
import java.util.HashSet;

public class ModFluids {
	public static void GlistreMod(){
		registerFluids();
	}	
	
	public static RudFluid rud; 
	
	public static BlockFluidClassic rud_block;
	
	public static Set<IFluidBlock> fluidBlocks = new HashSet<IFluidBlock>();
	
	public static void registerFluids() {
		rud = (RudFluid) createFluid("rud", "glistremod:blocks/fluid_rud", false).setLuminosity(10).setDensity(800).setViscosity(2000);
        rud_block = registerFluidBlock(new RudBlock(rud, new MaterialLiquid(MapColor.ADOBE)));
        
	}
	
	private static RudFluid createFluid(String name, String textureName, boolean hasFlowIcon) {
		ResourceLocation still = new ResourceLocation(textureName + "_still");
		ResourceLocation flowing = hasFlowIcon ? new ResourceLocation(textureName + "_flowing") : still;

		RudFluid fluid = new RudFluid(name, still, flowing);
		
		
		if (!FluidRegistry.registerFluid(fluid)) {
			throw new IllegalStateException(String.format("Unable to register fluid %s", fluid));
		}

		return fluid;
	}
	
	

/*	 private static <T extends Block & IFluidBlock> T registerFluidBlock(T block) {
		 block.setRegistryName(block.getFluid().getName());
		 block.setUnlocalizedName(Reference.MODI_D + ":" + block.getFluid().getUnlocalizedName());
		 block.setCreativeTab(TabRegistry.tab_builder);

		 ModFluids.registerBlock(block);
		 
		 return block;
		 }*/
	
	private static <T extends Block & IFluidBlock> T registerFluidBlock(T block) {
		String fluidName = block.getFluid().getUnlocalizedName();

  		 block.setUnlocalizedName(fluidName);
//		 block.setUnlocalizedName(Reference.MOD_ID + ":" + block.getFluid().getUnlocalizedName());
		 block.setCreativeTab(TabRegistry.tab_builder);

		GameRegistry.registerBlock(block, fluidName);

		fluidBlocks.add(block);

		return block;
		

	}
}
