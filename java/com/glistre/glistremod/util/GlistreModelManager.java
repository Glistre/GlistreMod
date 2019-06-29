package com.glistre.glistremod.util;

import com.glistre.glistremod.blocks.fluids.ModFluids;
import com.glistre.glistremod.reference.Reference;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GlistreModelManager   {  
	
	public static void GlistreMod(){
		registerAllModels();
	}

public static final GlistreModelManager INSTANCE = new GlistreModelManager();

ModelResourceLocation modelResourceLocation = new ModelResourceLocation(FLUID_MODEL_PATH, "inventory");

private static final String FLUID_MODEL_PATH = (Reference.MODID + ":" +  "rud");

public static void registerAllModels() {
        registerFluidModels();
}

private static void registerFluidModels() {
        for (IFluidBlock fluidBlock :ModFluids.fluidBlocks) {
        	
                registerFluidModel(fluidBlock);
        	
        }
}

private static void registerFluidModel(IFluidBlock fluidBlock) {
        Item item = Item.getItemFromBlock((Block) fluidBlock);
        ModelResourceLocation modelResourceLocation = new ModelResourceLocation(FLUID_MODEL_PATH, "inventory");
        
        ModelBakery.registerItemVariants(item, modelResourceLocation);
       // ModelBakery.addVariantName(item);


//        ModelResourceLocation modelResourceLocation = new ModelResourceLocation(FLUID_MODEL_PATH, fluidBlock.getFluid()..getName());

        ModelLoader.setCustomMeshDefinition(item, stack -> modelResourceLocation);

        ModelLoader.setCustomStateMapper((Block) fluidBlock, new StateMapperBase() {
                @Override
                protected ModelResourceLocation getModelResourceLocation(IBlockState state_) {
                        return modelResourceLocation;
                }
        });
} 

}
