package com.glistre.glistremod.init;

import com.glistre.glistremod.blocks.BlockGlistreFire;
import com.glistre.glistremod.blocks.BlockGlistreMushroom;
import com.glistre.glistremod.blocks.BlockGlistrePortal;
import com.glistre.glistremod.blocks.BlockHugeGlistreMushroom;
import com.glistre.glistremod.blocks.BlockSeaweed;
import com.glistre.glistremod.blocks.BlockTobyKingFire;
import com.glistre.glistremod.blocks.BlockTobyKingPortal;
import com.glistre.glistremod.blocks.EnchantedBlock;
import com.glistre.glistremod.blocks.GlistreChest;
import com.glistre.glistremod.blocks.GlistreChestGold;
import com.glistre.glistremod.blocks.LiquidIceBlock;
import com.glistre.glistremod.blocks.RudBlock;
import com.glistre.glistremod.blocks.SilverOreBlock;
import com.glistre.glistremod.blocks.SolidOreBlock;
import com.glistre.glistremod.blocks.VoidBlock;
import com.glistre.glistremod.reference.Reference;
import com.glistre.glistremod.tabs.TabRegistry;
import com.glistre.glistremod.tileentity.TileEntityGlistreChest;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
//import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;



public class BlockRegistry {

	public static void GlistreMod(){
		init();
		register();

	}	
	
//	public static Fluid rud; 

	
	public static Block silver_ore_1, silver_ore_2, silver_ore_3, silver_ore_4, silver_block_1, silver_block_2, silver_block_3, silver_block_4, glistre_block_1, 
		enchanted_block_1, void_block_1, block_seaweed, light_fire, light_portal, light_toby_king_fire, light_toby_king_portal, liquid_ice, glistre_chest, glistre_chest_gold,
		shroom_dots_block, glistre_shroom_stripe_block; 

	//make sure step sound on silverblock is METAL in game
	
	public static void init(){
//warning:  very high light levels will cause server to lag generating chunks, .4F not a problems;  resistance is to TNT bedROCK is 6000000.0F, hardness is how easy to destroy dirt is 0.5F, obsidian is 50.0F
		silver_ore_1 = (new SilverOreBlock(Material.ROCK, 3).setUnlocalizedName("silver_ore_1")
				//.setBlockName("silverOre_1").setCreativeTab(TabRegistry.tabBuilder).setBlockTextureName(Reference.MODID + ":" + "silverOre_1")
        		.setLightLevel(0.8F).setResistance(75F).setHardness(2.0F));      
		silver_ore_2 = new SilverOreBlock(Material.ROCK, 3).setUnlocalizedName("silver_ore_2")
				//.setCreativeTab(TabRegistry.tabBuilder).setBlockTextureName(Reference.MODID + ":" + "silverOre_2")
        		.setLightLevel(0.8F).setResistance(75F).setHardness(2.0F);       
		silver_ore_3 = new SilverOreBlock(Material.ROCK, 3).setUnlocalizedName("silver_ore_3").setCreativeTab(TabRegistry.tab_builder)
				//.setBlockTextureName(Reference.MODID + ":" + "silverOre_3")
        		.setLightLevel(0.8F).setResistance(75F).setHardness(2.0F);       
		silver_ore_4 = new SilverOreBlock(Material.ROCK, 3).setUnlocalizedName("silver_ore_4").setCreativeTab(TabRegistry.tab_builder)
				//.setBlockTextureName(Reference.MODID + ":" + "silverOre_4")
        		.setLightLevel(0.8F).setResistance(75F).setHardness(2.0F);       
        
        silver_block_1 = new SolidOreBlock(Material.IRON, 3).setUnlocalizedName("silver_block_1").setCreativeTab(TabRegistry.tab_builder)
        		//.setBlockTextureName(Reference.MODID + ":" + "silverBlock_1")
        		.setLightLevel(0.8F).setResistance(150F).setHardness(4.0F);
        silver_block_2 = new SolidOreBlock(Material.IRON, 3).setUnlocalizedName("silver_block_2").setCreativeTab(TabRegistry.tab_builder)
        		//.setBlockTextureName(Reference.MODID + ":" + "silverBlock_2")
        		.setLightLevel(0.8F).setResistance(150F).setHardness(4.0F);
        silver_block_3 = new SolidOreBlock(Material.IRON, 3).setUnlocalizedName("silver_block_3").setCreativeTab(TabRegistry.tab_builder)
        		//.setBlockTextureName(Reference.MODID + ":" + "silverBlock_3")
        		.setLightLevel(0.8F).setResistance(150F).setHardness(4.0F);
        silver_block_4 = new SolidOreBlock(Material.IRON, 3).setUnlocalizedName("silver_block_4").setCreativeTab(TabRegistry.tab_builder)
        		//.setBlockTextureName(Reference.MODID + ":" + "silverBlock_4")
        		.setLightLevel(0.8F).setResistance(150F).setHardness(4.0F);

        glistre_block_1 = new SolidOreBlock(Material.IRON, 3).setUnlocalizedName("glistre_block_1").setCreativeTab(TabRegistry.tab_builder)
        		//.setBlockTextureName(Reference.MODID + ":" + "glistreBlock_1")
        		.setLightLevel(0.3F).setResistance(175F).setHardness(6.0F); 

//       enchanted_block_1 = new EnchantedBlock("enchanted_block_1", Material.anvil, ItemRegistry.glistre_pickaxe, 2, 4)//try 1.8
        enchanted_block_1 = new EnchantedBlock(Material.ANVIL, 3).setUnlocalizedName("enchanted_block_1").setCreativeTab(TabRegistry.tab_food)
        		.setLightLevel(1.0F).setResistance(200F).setHardness(0.1F);
        
        void_block_1 = new VoidBlock(Material.ROCK, 3).setUnlocalizedName("void_block_1").setCreativeTab(TabRegistry.tab_builder)
        		//.setBlockTextureName(Reference.MODID + ":" + "silverBlock_1")
        		.setLightLevel(0.1F).setResistance(150F).setHardness(4.0F);
 
        block_seaweed = new BlockSeaweed().setUnlocalizedName("block_seaweed").setCreativeTab(TabRegistry.tab_food)
        		//.setBlockTextureName(Reference.MODID + ":" + "blockSeaweed")
        		.setLightLevel(0.7F).setResistance(1100.0F).setHardness(0.2F);
        
        light_fire = new BlockGlistreFire().setUnlocalizedName("light_fire").setLightLevel(0.9F).setCreativeTab(TabRegistry.tab_builder);
        light_toby_king_fire = new BlockTobyKingFire().setUnlocalizedName("light_toby_king_fire").setLightLevel(0.9F).setCreativeTab(TabRegistry.tab_builder);
        
        light_portal = new BlockGlistrePortal().setUnlocalizedName("light_portal").setLightLevel(0.9F).setCreativeTab(TabRegistry.tab_builder);
        light_toby_king_portal = new BlockTobyKingPortal().setUnlocalizedName("light_toby_king_portal").setLightLevel(0.9F).setCreativeTab(TabRegistry.tab_builder);
        liquid_ice = new LiquidIceBlock().setUnlocalizedName("liquid_ice").setCreativeTab(TabRegistry.tab_builder);
        		//.setBlockTextureName(Reference.MODID + ":" + "liquidIce");

    
//        rud_block = new RudBlock(rud, Material.water).setUnlocalizedName("rud_block").setCreativeTab(TabRegistry.tab_builder);
 //       rud.setUnlocalizedName(rud_block.getUnlocalizedName().substring(5));

        glistre_chest = new GlistreChest(BlockChest.Type.BASIC).setUnlocalizedName("glistre_chest").setCreativeTab(TabRegistry.tab_builder);
        		//.setBlockTextureName(Reference.MODID + ":" + "glistre_chest"); 
        glistre_chest_gold = new GlistreChestGold(BlockChest.Type.BASIC).setUnlocalizedName("glistre_chest_gold").setCreativeTab(TabRegistry.tab_builder);
        
        shroom_dots_block = new BlockHugeGlistreMushroom(Material.GOURD, BlockRegistry.shroom_dots_block).setUnlocalizedName("shroom_dots_block").setLightLevel(0.8F).setCreativeTab(TabRegistry.tab_food);
        glistre_shroom_stripe_block = new BlockHugeGlistreMushroom(Material.GOURD, BlockRegistry.glistre_shroom_stripe_block).setUnlocalizedName("glistre_shroom_stripe_block").setLightLevel(0.8F).setCreativeTab(TabRegistry.tab_food);

	}
	
	public static void register(){


        GameRegistry.registerBlock(silver_ore_1, silver_ore_1.getUnlocalizedName().substring(5));
        GameRegistry.registerBlock(silver_ore_2, silver_ore_2.getUnlocalizedName().substring(5));      
        GameRegistry.registerBlock(silver_ore_3, silver_ore_3.getUnlocalizedName().substring(5));      
        GameRegistry.registerBlock(silver_ore_4, silver_ore_4.getUnlocalizedName().substring(5));
             
        GameRegistry.registerBlock(silver_block_1, silver_block_1.getUnlocalizedName().substring(5));
        GameRegistry.registerBlock(silver_block_2, silver_block_2.getUnlocalizedName().substring(5));
        GameRegistry.registerBlock(silver_block_3, silver_block_3.getUnlocalizedName().substring(5));
        GameRegistry.registerBlock(silver_block_4, silver_block_4.getUnlocalizedName().substring(5));

        GameRegistry.registerBlock(glistre_block_1, glistre_block_1.getUnlocalizedName().substring(5));
        GameRegistry.registerBlock(enchanted_block_1, enchanted_block_1.getUnlocalizedName().substring(5));
        GameRegistry.registerBlock(void_block_1, "void_block_1");
     
        GameRegistry.registerBlock(block_seaweed, block_seaweed.getUnlocalizedName().substring(5));
  
        GameRegistry.registerBlock(light_fire, light_fire.getUnlocalizedName().substring(5));
        GameRegistry.registerBlock(light_toby_king_fire, light_toby_king_fire.getUnlocalizedName().substring(5));
 
        GameRegistry.registerBlock(light_toby_king_portal, "light_toby_king_portal");
        GameRegistry.registerBlock(light_portal, "light_portal");
        GameRegistry.registerBlock(liquid_ice, liquid_ice.getUnlocalizedName().substring(5));
        
//		registerRender(rud);//not sure what to do with Fluid on 1.8 update
//        GameRegistry.registerBlock(rud_block, Reference.MODID + "_" + rud_block.getUnlocalizedName().substring(5));

//		registerRender(rud_block);
        GameRegistry.registerBlock(glistre_chest, "glistre_chest");
        GameRegistry.registerBlock(glistre_chest_gold, "glistre_chest_gold");
 
        GameRegistry.registerBlock(shroom_dots_block, "shroom_dots_block");
        GameRegistry.registerBlock(glistre_shroom_stripe_block, "glistre_shroom_stripe_block");


	}
	
    public static void registerRenders()
	{
		registerRender(silver_ore_1);
		registerRender(silver_ore_2);
		registerRender(silver_ore_3);
		registerRender(silver_ore_4);
		registerRender(silver_block_1);
		registerRender(silver_block_2);
		registerRender(silver_block_3);
		registerRender(silver_block_4);
		registerRender(glistre_block_1);
		registerRender(enchanted_block_1);
		registerRender(void_block_1);		
		registerRender(block_seaweed);
		registerRender(light_fire);
		registerRender(light_toby_king_fire);
		registerRender(light_portal);
		registerRender(light_toby_king_portal);
		registerRender(liquid_ice);
//		registerRender(rud_block);
		registerRender(shroom_dots_block);		
		registerRender(glistre_shroom_stripe_block);
		registerRender(glistre_chest);
		registerRender(glistre_chest_gold);

		
	}

  
	public static void registerRender(Block block)
	{
/*		Item item = Item.getItemFromBlock(block);
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		//oddly next line render pink/purple checkers for some blocks when placed--problem is .registerBuiltInBlocks
//	    Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getModelManager().getBlockModelShapes().registerBuiltInBlocks(block);	

	    Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getModelManager().getBlockModelShapes().reloadModels();
    	renderItem.getItemModelMesher().register(item, 0, new ModelResourceLocation(Reference.MODID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
 
 *///1.9 next two lines
 //   	ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(BlockRegistry.glistre_chest), 0, TileEntityGlistreChest.class);
    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(Reference.MODID + ":" + block.getUnlocalizedName().substring(5), "inventory"));
	
	}
/*	public static void registerItemModel(Item item, String modelLocation) {
		final ModelResourceLocation fullModelLocation = new ModelResourceLocation(modelLocation, "inventory");
		ModelBakery.addVariantName(item, modelLocation); // Ensure the custom model is loaded and prevent the default model from being loaded
		ModelLoader.setCustomMeshDefinition(item, stack -> fullModelLocation);
	}*/
	

}
