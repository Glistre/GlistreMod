package com.glistre.glistremod.init;

import com.glistre.glistremod.GlistreMod;
import com.glistre.glistremod.lib.ConfigurationGlistre;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

public class Recipes {
	
	public static void initShapedRecipes(){
		if(!ConfigurationGlistre.secretRecipe){

	    //  SILVER SWORD RECIPE  
   	        GameRegistry.addRecipe(new ItemStack(ItemRegistry.silver_sword_1, 1), new Object[]{" X "," X "," S ", 'S', Items.STICK,'X', ItemRegistry.silver_ingot_1,});

		}else{
			
	    //  SILVER SWORD cheap RECIPE  
   	        GameRegistry.addRecipe(new ItemStack(ItemRegistry.silver_sword_1, 1), new Object[]{" X "," X "," S ", 'S', Items.STICK,'X', BlockRegistry.silver_ore_1,});
		}	
	   	 /**	
	   	  * RECIPES SECTION 
	   	  * *********************************************************** */
	   	    
	//ITEM RECIPES
	   	    
	   	//SECRET RECIPES		      
		//  Glistre Sword secret recipe
	    	ItemStack enchant = new ItemStack(ItemRegistry.glistre_sword);
	    	//fireAspect ==20
	    	enchant.addEnchantment(Enchantment.getEnchantmentByID(20), 7);
	    	GameRegistry.addShapelessRecipe(enchant, Items.BONE, Items.GOLDEN_APPLE);
	    //  Helmet of Glistre secret recipe
	    	ItemStack enchant0 = new ItemStack(ItemRegistry.glistre_helmet_1);
	    	//blastprotection == 3
	    	enchant0.addEnchantment(Enchantment.getEnchantmentByID(3), 9);
	   	   	GameRegistry.addShapelessRecipe(enchant0, ItemRegistry.glistre_ingot, Items.GOLDEN_APPLE);
		//  Silver Sword secret recipe
	    	ItemStack enchant01 = new ItemStack(ItemRegistry.silver_sword_1);
	    	enchant01.addEnchantment(Enchantment.getEnchantmentByID(20), 7);
	    	GameRegistry.addShapelessRecipe(enchant01, Items.BONE, Items.STRING);	   	   	
	    //  Silver Pickaxe secret recipe
	    	ItemStack enchant02 = new ItemStack(ItemRegistry.silver_pickaxe_1);
	    	//fortune == 35
	    	enchant02.addEnchantment(Enchantment.getEnchantmentByID(35), 11);
	    	GameRegistry.addShapelessRecipe(enchant02, Items.BONE, Items.QUARTZ);
	    //	Glistre Pickaxe secret recipe
	    	ItemStack enchant03 = new ItemStack(ItemRegistry.glistre_pickaxe);
	    	//silktouch == 33
	    	enchant03.addEnchantment(Enchantment.getEnchantmentByID(33), 6);
	    	GameRegistry.addShapelessRecipe(enchant03, Items.BONE, Items.FEATHER);
	    //  Skeleton Buster bow secret recipe   	    
	   	    ItemStack enchant04 = new ItemStack(ItemRegistry.custom_bow_1);
	   	    //infinity == 51
	   		enchant04.addEnchantment(Enchantment.getEnchantmentByID(51), 10);
	   		GameRegistry.addShapelessRecipe(enchant04, Items.STRING, ItemRegistry.glistre_dust);
	   		//power == 48
	   		enchant04.addEnchantment(Enchantment.getEnchantmentByID(48), 7);
	   		
	// STANDARD RECIPES

	/*//  SILVER SWORD RECIPE  
		    GameRegistry.addRecipe(new ItemStack(ItemRegistry.SilverSword_1, 1), new Object[]{" X "," X "," S ", 'S', Items.stick,'X', ItemRegistry.SilverIngot_1,});
	*/
	//  GLISTERING PIE RECIPE  
		         ItemStack glistrepie = new ItemStack(ItemRegistry.glistre_food_2);        
		         GameRegistry.addShapelessRecipe(glistrepie, ItemRegistry.glistre_food_1, Items.SUGAR);
	   	        
	//  SEAWEED SHEET/ NORI
	   	         GameRegistry.addRecipe(new ItemStack(ItemRegistry.nori, 3), new Object[]
	   	        		 {
	   	        			"XXX",
	   	        			"XXX",
	   	        			"XXX",
	   	        		'X', BlockRegistry.block_seaweed,
	   	        		 });       
	//   SUSHI
	   	         ItemStack sushi = new ItemStack(ItemRegistry.sushi);        
	   	         GameRegistry.addShapelessRecipe(sushi, ItemRegistry.nori, Items.FISH);
	   	        		    	             

	//  SWORD RECIPE  
	   	        GameRegistry.addRecipe(new ItemStack(ItemRegistry.glistre_sword, 1), new Object[]
	   	     	        {
	   	     	                " X ",
	   	     	                " X ",
	   	     	                " S ",
	   	     	            'S', Items.STICK,
	   	     	            'X', ItemRegistry.glistre_ingot,
	   	     	        });
	   	        
	//  PICKAXES RECIPE  
	   	        GameRegistry.addRecipe(new ItemStack(ItemRegistry.silver_pickaxe_1, 1), new Object[]
	   	     	        {
	   	     	                "XXX",
	   	     	                " X ",
	   	     	                " S ",
	   	     	            'S', Items.STICK,
	   	     	            'X', ItemRegistry.silver_ingot_1,
	   	     	        });
	//Glistre's Pickaxe
	   	     	GameRegistry.addRecipe(new ItemStack(ItemRegistry.glistre_pickaxe, 1), new Object[]
	   	     	        {
	   	     	                "XXX",
	   	     	                " X ",
	   	     	                " S ",
	   	     	            'S', Items.STICK,
	   	     	            'X', ItemRegistry.glistre_ingot,
	   	     	        });
	//Sparks Pickaxe    	     	        
	   	     	GameRegistry.addRecipe(new ItemStack(ItemRegistry.glistre_pickaxe_2, 1), new Object[]
	   	     	        {
	   	     	                "XXX",
	   	     	                " X ",
	   	     	                " S ",
	   	     	            'S', Items.BLAZE_ROD,
	   	     	            'X', ItemRegistry.glistre_ingot,
	   	     	        });
	   	     	
	//  SCEPTRE TOBIE GUARDIAN SPAWNER
	   	     	
	   	   	    GameRegistry.addRecipe(new ItemStack(ItemRegistry.sceptre_1, 6), new Object[] {
	   	   	    		" X ",
	   	   	    		"YYY",
	   	   	    		" Z ",
	   	   	    		'X', ItemRegistry.glistre_ingot,
	   	   	    		'Y', Items.EGG,
	   	   	    		'Z', Items.EMERALD,   		
	   	   	    		});

	// SMELTING RECIPE
	   	     	GameRegistry.addSmelting(BlockRegistry.silver_ore_1, (new ItemStack(ItemRegistry.silver_ingot_1, 1)), 12);
	   	     	GameRegistry.addSmelting(ItemRegistry.mighty_sword, (new ItemStack(ItemRegistry.mighty_ice_sword, 1)), 1000);

	//  GLISTRE BURNER RECIPE
	   	     	ItemStack glistreburner = new ItemStack(ItemRegistry.glistre_burner);
	   	     	GameRegistry.addShapelessRecipe(glistreburner, ItemRegistry.silver_ingot_1, Items.FLINT_AND_STEEL);

	   	     	ItemStack blueburner = new ItemStack(ItemRegistry.toby_king_burner);
	   	     	GameRegistry.addShapelessRecipe(blueburner, ItemRegistry.glistre_ingot, Items.FLINT_AND_STEEL);
	   	 //	BLOCK RECIPES
	   	     	GameRegistry.addRecipe(new ItemStack(BlockRegistry.silver_block_1, 1), new Object[]
	   	                {
	   	                        "XXX",
	   	                        "XXX",
	   	                        "XXX",
	   	                    'X', ItemRegistry.silver_ingot_1,
	   	                });
	   	                
	   	     	GameRegistry.addRecipe(new ItemStack(BlockRegistry.glistre_block_1, 1), new Object[]
	   	                {
	   	                        "XXX",
	   	                        "XXX",
	   	                        "XXX",
	   	                    'X', ItemRegistry.glistre_ingot,
	   	                });
	   	                
	   	     	GameRegistry.addRecipe(new ItemStack(BlockRegistry.enchanted_block_1, 1), new Object[]
	   	                {
	   	                        "XXX",
	   	                        "XSX",
	   	                        "XXX",
	   	                    'X', ItemRegistry.glistre_ingot,
	   	                    'S', Items.ENDER_PEARL,
	   	                });
	   	        
//	   		ENCHANTED SILVER ARMOR RECIPES  
	   	     	GameRegistry.addRecipe(new ItemStack(ItemRegistry.silver_helmet_1, 1), new Object[]
	   	     			{
	   	     					"XXX",
	   	     					"X X",
	   	     				'X', ItemRegistry.silver_ingot_1,
	   	     			});         
	   	     
	   	     	GameRegistry.addRecipe(new ItemStack(ItemRegistry.silver_chestplate_1, 1), new Object[]
	   	     			{
	   	     					"X X",
	   	     					"XXX",
	   	     					"XXX",
	   	     				'X', ItemRegistry.silver_ingot_1,
	   	     			});        
	   	    
	   	       GameRegistry.addRecipe(new ItemStack(ItemRegistry.silver_leggings_1, 1), new Object[]
	   	    		   {
	   	    				   "XXX",
	   	    				   "X X",
	   	    				   "X X",
	   	    				'X', ItemRegistry.silver_ingot_1,
	   	    		   });         
	   	       
	   	       GameRegistry.addRecipe(new ItemStack(ItemRegistry.silver_boots_1, 1), new Object[]
	   	    		   {
	   	    				   "X X",
	   	    				   "X X",
	   	    				'X', ItemRegistry.silver_ingot_1,
	   	    		   });
	  	    
//			GameRegistry.addRecipe(new ItemStack(SilverSword_1, 1), new Object[]{ " X "," X "," S ", 'S', Items.blaze_rod,'X', Items.stick});
	   	        

	   	       //  HELMET OF GLISTRE RECIPE   
	   	           GameRegistry.addRecipe(new ItemStack(ItemRegistry.glistre_helmet_1, 1), new Object[]
	   	           {
	   	                   "XXX",
	   	                   "X X",
	   	               'X', ItemRegistry.glistre_ingot,
	   	           });         

	   	       //  CHESTPLATE OF GLISTRE RECIPE   
	   	           GameRegistry.addRecipe(new ItemStack(ItemRegistry.glistre_chestplate_1, 1), new Object[]
	   	           {
	   	                   "X X",
	   	                   "XXX",
	   	                   "XXX",
	   	               'X', ItemRegistry.glistre_ingot,
	   	           });         

	   	       //  LEGGINGS OF GLISTRE RECIPE 
	   	           GameRegistry.addRecipe(new ItemStack(ItemRegistry.glistre_leggings_1, 1), new Object[]
	   	           {
	   	                   "XXX",
	   	                   "X X",
	   	                   "X X",
	   	               'X', ItemRegistry.glistre_ingot,
	   	           });         

	   	       //  BOOTS OF GLISTRE RECIPE    
	   	           GameRegistry.addRecipe(new ItemStack(ItemRegistry.glistre_boots_1, 1), new Object[]
	   	           {
	   	                   "X X",
	   	                   "X X",
	   	               'X', ItemRegistry.glistre_ingot,
	   	           });
	   	           //LIGHTNINGEMERALD ARMOR
	   	           
		   	       // EMERALD HELMET GLISTRE RECIPE   
	   	           GameRegistry.addRecipe(new ItemStack(ItemRegistry.emerald_helmet_1, 1), new Object[]
	   	           {
	   	                   "XXX",
	   	                   "X X",
	   	               'X', Items.EMERALD,
	   	           });         

	   	       //  EMERALD CHESTPLATE RECIPE   
	   	           GameRegistry.addRecipe(new ItemStack(ItemRegistry.emerald_chestplate_1, 1), new Object[]
	   	           {
	   	                   "X X",
	   	                   "XXX",
	   	                   "XXX",
	   	               'X', Items.EMERALD,
	   	           });         

	   	       //  EMERALD LEGGINGS RECIPE 
	   	           GameRegistry.addRecipe(new ItemStack(ItemRegistry.emerald_leggings_1, 1), new Object[]
	   	           {
	   	                   "XXX",
	   	                   "X X",
	   	                   "X X",
	   	               'X', Items.EMERALD,
	   	           });         

	   	       //  EMERALD BOOTS RECIPE    
	   	           GameRegistry.addRecipe(new ItemStack(ItemRegistry.emerald_boots_1, 1), new Object[]
	   	           {
	   	                   "X X",
	   	                   "X X",
	   	               'X', Items.EMERALD,
	   	           });
	   
		

}
	public static void initShapelessRecipes(){
		if(!ConfigurationGlistre.secretRecipe){
		//  GLISTERING BREAD standard RECIPE
	        ItemStack glistrebread = new ItemStack(ItemRegistry.glistre_food_1);        
	        GameRegistry.addShapelessRecipe(glistrebread, ItemRegistry.glistre_dust, Items.BREAD);
	   	//  GLISTRE DUST standard RECIPE
	        ItemStack GlistreDust = new ItemStack(ItemRegistry.glistre_dust);        
	        GameRegistry.addShapelessRecipe(GlistreDust, ItemRegistry.silver_ingot_1, Items.GOLD_INGOT);
		}else {
		//  Glistering Bread secret recipe
	        ItemStack glistrebread = new ItemStack(ItemRegistry.glistre_food_1);        
	        GameRegistry.addShapelessRecipe(glistrebread, Blocks.DIRT, Items.BREAD);
		//  Glistre dust secret recipe
	        ItemStack GlistreDust = new ItemStack(ItemRegistry.glistre_dust);        
	        GameRegistry.addShapelessRecipe(GlistreDust, Items.IRON_INGOT, Items.DYE);
			
		}
	
	}
	
	public static void initSmeltingRecipes(){

		// SMELTING GLISTRE INGOT RECIPE
	        ItemStack GlistreIngot2 = new ItemStack(ItemRegistry.glistre_ingot);
	        GameRegistry.addSmelting(ItemRegistry.glistre_dust, GlistreIngot2, 12.0F);      

	}
}
