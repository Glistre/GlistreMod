package com.glistre.glistremod.init;

import com.glistre.glistremod.GlistreMod;
import com.glistre.glistremod.items.records.MusicDisc;
import com.glistre.glistremod.items.records.MusicDisc2;
import com.glistre.glistremod.armor.ChainArmor;
import com.glistre.glistremod.armor.EmeraldArmor;
import com.glistre.glistremod.armor.GlistreArmor;
import com.glistre.glistremod.armor.SilverArmor;
import com.glistre.glistremod.effects.potions.ItemNauseaPotion;
import com.glistre.glistremod.effects.potions.ItemNauseaProtectPotion;
import com.glistre.glistremod.effects.potions.ItemPoisonProtectPotion;
import com.glistre.glistremod.effects.potions.NauseaProtectEffect;
import com.glistre.glistremod.effects.potions.PoisonProtectEffect;
import com.glistre.glistremod.effects.potions.VomitusEffect;
import com.glistre.glistremod.effects.potions.splash.ItemSplashPotion;
import com.glistre.glistremod.entities.wolf.EntityBlackWolf;
import com.glistre.glistremod.items.Sceptre;
import com.glistre.glistremod.items.books.ItemAncientBook;
import com.glistre.glistremod.items.books.ItemRecipeBook;
import com.glistre.glistremod.items.books.ItemSecretBook;
import com.glistre.glistremod.items.bow.BusterBow;
import com.glistre.glistremod.items.burners.GlistreBurner;
import com.glistre.glistremod.items.burners.TobyKingBurner;
import com.glistre.glistremod.items.food.GlistreFood;
import com.glistre.glistremod.items.food.Nori;
import com.glistre.glistremod.items.ingots.SilverIngot;
import com.glistre.glistremod.items.pickaxes.GlistrePickaxe;
import com.glistre.glistremod.items.pickaxes.SilverPickaxe;
import com.glistre.glistremod.items.pickaxes.SparksPickaxe;
import com.glistre.glistremod.items.swords.CuredIceSword;
import com.glistre.glistremod.items.swords.GlistreSword;
import com.glistre.glistremod.items.swords.MightyIceSword;
import com.glistre.glistremod.items.swords.SilverSword;
import com.glistre.glistremod.projectiles.blaster.Blaster;
import com.glistre.glistremod.projectiles.blaster.EnderGun;
import com.glistre.glistremod.projectiles.tobyworstsword.TobyProjectile;
import com.glistre.glistremod.reference.Reference;
import com.glistre.glistremod.tabs.TabRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.EntityEquipmentSlot.Type;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWrittenBook;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
//import net.minecraft.util.WeightedRandomChestContent; removed 1.9
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
//import net.minecraftforge.common.ChestGenHooks; removed 1.9
import net.minecraftforge.common.DungeonHooks;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class ItemRegistry {
	
	public static void GlistreMod(){
		init();
		register();
	}
	
	public static Item silver_ingot_1;
	public static Item glistre_dust;
	public static Item glistre_ingot;
	
//  DECLARE GLISTERING BREAD
    public static Item glistre_food_1;

//  DECLARE GLISTERING PIE
    public static Item glistre_food_2;
    
//	DECLARE SEAWEED SHEETS/ NORI
    public static Item nori;
    public static Item sushi;
    
//  DECLARE THE SWORDS	
	public static Item silver_sword_1; 
	public static Item glistre_sword; 
	public static Item mighty_sword;
	public static Item mighty_ice_sword;
	
//  DECLARE THE PICKAXES 
    public static Item silver_pickaxe_1;
    public static Item glistre_pickaxe;
    public static Item glistre_pickaxe_2;

 // DECLARE THE ARMOR MATERIAL
 	public static ArmorMaterial glistre=	
 			//String name, textureName, durability, reductionAmounts, enchantability, soundOnEquip, toughness
 			EnumHelper.addArmorMaterial("glistre", "glistre", 28, new int[]{3, 7, 6, 4}, 50, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 5.0F);
    
// DECLARE THE ARMOR
 	public static Item silver_helmet_1;
 	public static Item silver_chestplate_1;
 	public static Item silver_leggings_1;
 	public static Item silver_boots_1;
	public static Item glistre_helmet_1;
	public static Item glistre_chestplate_1;
	public static Item glistre_leggings_1;
	public static Item glistre_boots_1;
	public static Item chain_helmet_1;
	public static Item chain_chestplate_1;
	public static Item chain_leggings_1;
	public static Item chain_boots_1;
	public static Item emerald_helmet_1;
	public static Item emerald_chestplate_1;
	public static Item emerald_leggings_1;
	public static Item emerald_boots_1;		
//  DECLARE THE BOW
        public static Item custom_bow_1;
        
//  DECLARE THE RAY GUN
        public static Item sceptre_1;
        public static int sceptreID;

        public static Item blaster_gun_1;
        public static int blaster_gun_1ID;
        
        public static Item tobie_gun_1;
        public static int tobie_gun_1ID;
        
//	DECLARE THE ENDERGUN
        public static Item ender_gun;
        public static int ender_gun1ID;
  
// DECLARE THE RECORDS

        public static ItemRecord wolf_howl;
        public static ItemRecord wolves_howling;
        public static ItemRecord sasquatch;

// DECLARE POTION
        public static int potionID = 0;
    	public static Item poison_protection;
    	public static Item nausea_protection;
    	public static Item vomitus;
    	
    	public static Potion poison_protect_potion;
    	public static Potion nausea_protect_potion;
    	public static Potion vomitus_potion;
    	
// DECLARE THE FLINT & STEEL
    	public static Item glistre_burner;
    	public static Item toby_king_burner;
    	
    	//new egg the 1.8 way
//        public static Item item_spawn_egg_2;

    	

/*        public static Item sceptre_egg;
        public static Item tobie_queen_egg;
        public static Item tobie_king_egg;*/
        

       
// DECLARE THE RECIPE BOOKS
//        public static ItemStack recipe_book;
//        public static ItemStack secret_book;
        public static Item ancient_book;//changed from ItemStack to Item 1.10.2
        public static Item recipe_book;//changed from ItemStack to Item 1.10.2
        public static Item secret_book;//changed from ItemStack to Item 1.10.2
       
	
	public static void init(){
		
// LOAD THE ITEMS
	silver_ingot_1 = new SilverIngot(silver_ingot_1, "silver_ingot_1").setRegistryName("silver_ingot_1").setUnlocalizedName("silver_ingot_1").setMaxStackSize(64);
			//.setCreativeTab(TabRegistry.tabCustom).setTextureName(Reference.MODID + ":" + "SilverIngot_1");

// GLISTRE DUST/ORE
    glistre_dust = new Item().setRegistryName("glistre_dust").setUnlocalizedName("glistre_dust").setMaxStackSize(64);
    		//.setCreativeTab(TabRegistry.tabCustom).setTextureName(Reference.MODID + ":" + "Glistre_Dust");
    glistre_ingot = new Item().setRegistryName("glistre_ingot").setUnlocalizedName("glistre_ingot").setMaxStackSize(64);
    		//.setCreativeTab(TabRegistry.tabCustom).setTextureName(Reference.MODID + ":" + "Glistre_Ingot");
 
// GLISTERING BREAD
    /**healAmount, saturationModifier (F), isWolfsFavoriteMeat */
    glistre_food_1 = new GlistreFood(6, 0.8F, false).setRegistryName("glistre_food_1").setUnlocalizedName("glistre_food_1").setMaxStackSize(64);
    		//.setCreativeTab(TabRegistry.tabFood).setTextureName(Reference.MODID + ":" + "GlistreFood_1");

// GLISTERING PIE
    /** itemID, healAmount, saturationModifier (F), isWolfsFavoriteMeat, Texture Name */
	glistre_food_2 = new GlistreFood(8, 1.0F, true).setRegistryName("glistre_food_2").setUnlocalizedName("glistre_food_2").setMaxStackSize(64);
			//.setCreativeTab(TabRegistry.tabFood).setTextureName(Reference.MODID + ":" + "GlistreFood_2");

// SEAWEED SHEETS/ NORI
    /** healAmount, saturationModifier (F), isWolfsFavoriteMeat*/
    nori = new Nori(1, 0.3F, false).setRegistryName("nori").setUnlocalizedName("nori").setMaxStackSize(64);
    		//.setCreativeTab(TabRegistry.tabFood).setTextureName(Reference.MODID + ":" + "nori");

// SUSHI
    /** healAmount, saturationModifier (F), isWolfsFavoriteMeat*/
    sushi = new Nori(4, 0.8F, false).setRegistryName("sushi").setUnlocalizedName("sushi").setMaxStackSize(64);
    		//.setCreativeTab(TabRegistry.tabFood).setMaxStackSize(64).setTextureName(Reference.MODID + ":" + "sushi");

//  LOAD THE SWORDS
	silver_sword_1 = new SilverSword(silver_sword_1, "silver_sword_1").setRegistryName("silver_sword_1").setUnlocalizedName("silver_sword_1").setMaxStackSize(4);
			//.setCreativeTab(TabRegistry.GlistreTab_1).setTextureName(Reference.MODID + ":" + "SilverSword_1"); 
    	//	MySword(ToolMaterial Item, EnumToolMaterial, "SilverSword_1");
	glistre_sword = new GlistreSword(glistre_sword, "glistre_sword").setRegistryName("glistre_sword_1").setUnlocalizedName("glistre_sword").setMaxStackSize(4);
			//.setCreativeTab(TabRegistry.GlistreTab_1).setTextureName(Reference.MODID + ":" + "glistre_sword");
	mighty_sword = new MightyIceSword(mighty_sword, "mighty_sword").setRegistryName("mighty_sword_1").setUnlocalizedName("mighty_sword").setMaxStackSize(4);
			//.setCreativeTab(TabRegistry.GlistreTab_1).setTextureName(Reference.MODID + ":" + "MightySword");
	mighty_ice_sword = new CuredIceSword(mighty_ice_sword, "mighty_ice_sword").setRegistryName("mighty_ice_sword_1").setUnlocalizedName("mighty_ice_sword").setMaxStackSize(4);
			//.setCreativeTab(TabRegistry.tabCustom).setTextureName(Reference.MODID + ":" + "MightyIceSword");
	
//  LOAD THE PICKAXES
    silver_pickaxe_1 = new SilverPickaxe(silver_pickaxe_1, "silver_pickaxe_1").setRegistryName("silver_pickaxe_1").setUnlocalizedName("silver_pickaxe_1").setMaxStackSize(4);
    		//.setCreativeTab(TabRegistry.tabCustom).setTextureName(Reference.MODID + ":" + "SilverPickaxe_1");
	glistre_pickaxe = new GlistrePickaxe(glistre_pickaxe, "glistre_pickaxe").setRegistryName("glistre_pickaxe").setUnlocalizedName("glistre_pickaxe").setMaxStackSize(4);
			//.setCreativeTab(TabRegistry.tabCustom).setTextureName(Reference.MODID + ":" + "Glistre_Pickaxe");
	glistre_pickaxe_2 = new SparksPickaxe(glistre_pickaxe_2, "glistre_pickaxe_2").setRegistryName("glistre_pickaxe_2").setUnlocalizedName("glistre_pickaxe_2").setMaxStackSize(4);
			//.setCreativeTab(TabRegistry.tabCustom).setTextureName(Reference.MODID + ":" + "Glistre_Pickaxe2");

//	LOAD THE ARMOR
	silver_helmet_1 = new SilverArmor(glistre, 1, EntityEquipmentSlot.HEAD).setRegistryName(new ResourceLocation(Reference.MODID, "silver_helmet_1")).setUnlocalizedName("silver_helmet_1").setMaxStackSize(2);	
	silver_chestplate_1 = new SilverArmor(glistre, 1, EntityEquipmentSlot.CHEST).setRegistryName(new ResourceLocation(Reference.MODID, "silver_chestplate_1")).setUnlocalizedName("silver_chestplate_1").setMaxStackSize(2);			
	silver_leggings_1 = new SilverArmor(glistre, 2, EntityEquipmentSlot.LEGS).setRegistryName(new ResourceLocation(Reference.MODID, "silver_leggings_1")).setUnlocalizedName("silver_leggings_1").setMaxStackSize(2);
  	silver_boots_1 = new SilverArmor(glistre, 1, EntityEquipmentSlot.FEET).setRegistryName(new ResourceLocation(Reference.MODID, "silver_boots_1")).setUnlocalizedName("silver_boots_1").setMaxStackSize(2);

  	glistre_helmet_1 = new GlistreArmor(glistre, 1, EntityEquipmentSlot.HEAD).setRegistryName(new ResourceLocation(Reference.MODID, "glistre_helmet_1")).setUnlocalizedName("glistre_helmet_1").setMaxStackSize(2);
	glistre_chestplate_1 = new GlistreArmor(glistre, 1, EntityEquipmentSlot.CHEST).setRegistryName(new ResourceLocation(Reference.MODID, "glistre_chestplate_1")).setUnlocalizedName("glistre_chestplate_1").setMaxStackSize(2);
	glistre_leggings_1 = new GlistreArmor(glistre, 2, EntityEquipmentSlot.LEGS).setRegistryName(new ResourceLocation(Reference.MODID, "glistre_leggings_1")).setUnlocalizedName("glistre_leggings_1").setMaxStackSize(2);
	glistre_boots_1 = new GlistreArmor(glistre, 1, EntityEquipmentSlot.FEET).setRegistryName(new ResourceLocation(Reference.MODID, "glistre_boots_1")).setUnlocalizedName("glistre_boots_1").setMaxStackSize(2);

//	chain_helmet_1 = new ChainArmor(glistre, 1, EntityEquipmentSlot.HEAD, "chain_helmet_1").setRegistryName(new ResourceLocation(Reference.MODID, "chain_helmet_1")).setUnlocalizedName("chain_helmet_1").setMaxStackSize(2);
	chain_helmet_1 = new ChainArmor(glistre, 1, EntityEquipmentSlot.HEAD).setRegistryName(new ResourceLocation(Reference.MODID, "chain_helmet_1")).setUnlocalizedName("chain_helmet_1").setMaxStackSize(2);

	chain_chestplate_1 = new ChainArmor(glistre, 1, EntityEquipmentSlot.CHEST).setRegistryName(new ResourceLocation(Reference.MODID, "chain_chestplate_1")).setUnlocalizedName("chain_chestplate_1").setMaxStackSize(2);
	chain_leggings_1 = new ChainArmor(glistre, 2, EntityEquipmentSlot.LEGS).setRegistryName(new ResourceLocation(Reference.MODID, "chain_leggings_1")).setUnlocalizedName("chain_leggings_1").setMaxStackSize(2);
	chain_boots_1 = new ChainArmor(glistre, 1, EntityEquipmentSlot.FEET).setRegistryName(new ResourceLocation(Reference.MODID, "chain_boots_1")).setUnlocalizedName("chain_boots_1").setMaxStackSize(2);
//.setRegistryName(new ResourceLocation(Reference.MODID, "chain_helmet_1"))
	
	emerald_helmet_1 = new EmeraldArmor(glistre, 1, EntityEquipmentSlot.HEAD).setRegistryName(new ResourceLocation(Reference.MODID, "emerald_helmet_1")).setUnlocalizedName("emerald_helmet_1").setMaxStackSize(2);
	emerald_chestplate_1 = new EmeraldArmor(glistre, 1, EntityEquipmentSlot.CHEST).setRegistryName(new ResourceLocation(Reference.MODID, "emerald_chestplate_1")).setUnlocalizedName("emerald_chestplate_1").setMaxStackSize(2);
	emerald_leggings_1 = new EmeraldArmor(glistre, 2, EntityEquipmentSlot.LEGS).setRegistryName(new ResourceLocation(Reference.MODID, "silver_leggings_1")).setUnlocalizedName("emerald_leggings_1").setMaxStackSize(2);
	emerald_boots_1 = new EmeraldArmor(glistre, 1, EntityEquipmentSlot.FEET).setRegistryName(new ResourceLocation(Reference.MODID, "silver_boots_1")).setUnlocalizedName("emerald_boots_1").setMaxStackSize(2);
	
//  LOAD THE BOW
    custom_bow_1 = new BusterBow().setRegistryName("custom_bow_1").setUnlocalizedName("custom_bow_1").setMaxStackSize(2);
    		//.setCreativeTab(TabRegistry.GlistreTab_1).setTextureName(Reference.MODID + ":"  + "customBow_1");

//  LOAD THE BLASTERS
    sceptre_1 = new Sceptre().setRegistryName("sceptre_1").setUnlocalizedName("sceptre_1").setMaxStackSize(4);
    		//.setTextureName(Reference.MODID + ":"  + "sceptre_1");
    GameRegistry.registerItem(sceptre_1);        

    blaster_gun_1 = new Blaster(blaster_gun_1ID, "blaster_gun_1").setRegistryName("blaster_gun_1").setUnlocalizedName("blaster_gun_1").setMaxStackSize(2);
    		//.setCreativeTab(TabRegistry.GlistreTab_1).setTextureName(Reference.MODID + ":"  + "blasterGun_1");
   // GameRegistry.registerItem(blaster_gun_1, blaster_gun_1.getUnlocalizedName().substring(5));
    
    //this is the gun for TobieSkellyGuardian works like blaster but needs to render differently
    tobie_gun_1 = new Blaster(tobie_gun_1ID, "tobie_gun_1").setRegistryName("tobie_gun_1").setUnlocalizedName("tobie_gun_1").setMaxStackSize(2);
    		//.setTextureName(Reference.MODID + ":" + "tobieGun_1");
   // GameRegistry.registerItem(tobie_gun_1, tobie_gun_1.getUnlocalizedName().substring(5));
    
    ender_gun = new EnderGun(ender_gun1ID).setRegistryName("ender_gun_1").setUnlocalizedName("ender_gun").setMaxStackSize(2);
    		//.setCreativeTab(TabRegistry.GlistreTab_1).setTextureName(Reference.MODID + ":"  + "endermanGun_1");
   // GameRegistry.registerItem(ender_gun, ender_gun.getUnlocalizedName().substring(5));

//  LOAD RECORD
    //no idea what to put for SoundEvents.whatever in 1.10.2
    wolf_howl = (ItemRecord) new MusicDisc("wolf_howl", SoundEvents.RECORD_FAR).setRegistryName("wolf_howl").setUnlocalizedName("wolf_howl").setCreativeTab(TabRegistry.tab_custom); 
    wolves_howling = (ItemRecord) new MusicDisc("wolves_howling", SoundEvents.RECORD_BLOCKS).setRegistryName("wolves_howling").setUnlocalizedName("wolves_howling").setCreativeTab(TabRegistry.tab_custom);
    sasquatch = (ItemRecord) new MusicDisc2("sasquatch", SoundEvents.RECORD_11).setRegistryName("sasquatch").setUnlocalizedName("sasquatch").setCreativeTab(TabRegistry.tab_custom);

  //LOAD POTION	
//  poison_protection = new ItemPoisonProtectPotion(16, "poison_protection", new PoisonProtectEffect[]{new PoisonProtectEffect (new ResourceLocation("textures/gui/poison_protect_potion_icon.png"), false, 1200)}, 888888).setUnlocalizedName("poison_protection").setCreativeTab(TabRegistry.tab_potion);
    poison_protection = new ItemPoisonProtectPotion(16, "poison_protection", new PoisonProtectEffect[]{new PoisonProtectEffect (false, 1200, "poison_protection")}, 888888).setUnlocalizedName("poison_protection").setCreativeTab(TabRegistry.tab_potion);
//  nausea_protection = new ItemNauseaProtectPotion(16, "nausea_protection", new NauseaProtectEffect[]{new NauseaProtectEffect (new ResourceLocation("textures/gui/nausea_protect_potion_icon.png"), false, 1200)}, 702885).setUnlocalizedName("nausea_protection").setCreativeTab(TabRegistry.tab_potion);
    nausea_protection = new ItemNauseaProtectPotion(16, "nausea_protection", new NauseaProtectEffect[]{new NauseaProtectEffect (false, 1200, "nausea_protection")}, 702885).setUnlocalizedName("nausea_protection").setCreativeTab(TabRegistry.tab_potion);
//  vomitus = new ItemNauseaPotion(16, "vomitus", new VomitusEffect[]{new VomitusEffect (new ResourceLocation("textures/gui/vomitus_protect_potion_icon.png"), true, 1200)}, 666666).setUnlocalizedName("vomitus").setCreativeTab(TabRegistry.tab_potion);
    vomitus = new ItemNauseaPotion(16, "vomitus", new VomitusEffect[]{new VomitusEffect (true, 1200, "vomitus")}, 666666).setUnlocalizedName("vomitus").setCreativeTab(TabRegistry.tab_potion);
   
    ///* ID, badeffect, particleeffectsID, 0 none, 1= black on/or color 999999 Fire Orange 888888 lime green
    poison_protect_potion = new PoisonProtectEffect(false, 888888, "potion.poison_protect").setPotionName("potion.poison_protect");
    //GameRegistry.registerItem(poisonProtectPotion = new ItemCustomPotion(16, "potion.poisonProtect", new PotionEffect(PotionEffect.poisonProtectEffect.id, 300, 1), 9999999);
    //.setIconIndex(2, 2)
 //   nausea_protect_potion = new NauseaProtectEffect(new ResourceLocation("textures/gui/nausea_protect.png"), false, 702885).setPotionName("potion.nausea_protect");
    nausea_protect_potion = new NauseaProtectEffect(false, 702885, "nausea_protect").setPotionName("potion.nausea_protect");
//     vomitus_potion = new VomitusEffect(new ResourceLocation("textures/gui/vomitus.png"), false, 666666).setPotionName("potion.vomitus");
    vomitus_potion = new VomitusEffect(true, 666666, "vomitus").setPotionName("potion.vomitus");

    
 // LOAD FLINT/STEEL
    glistre_burner = new GlistreBurner().setRegistryName("glistre_burner").setUnlocalizedName("glistre_burner").setMaxStackSize(2);
    		//.setTextureName(Reference.MODID + ":" + "glistreburner").setCreativeTab(TabRegistry.tabCustom);
    toby_king_burner = new TobyKingBurner().setRegistryName("toby_king_burner").setUnlocalizedName("toby_king_burner").setMaxStackSize(2);
    		//.setTextureName(Reference.MODID + ":" + "tobyKingBurner").setCreativeTab(TabRegistry.tabCustom);

    //LOAD 1.8 egg
    
//    item_spawn_egg_2 = new ItemMonsterPlacer().setUnlocalizedName("black_wolf").setCreativeTab(TabRegistry.tab_food).setMaxStackSize(12);

//  LOAD SPAWN EGGS
/*    item0_spawn_egg = new GlistreEntityMonsterPlacer("glistre_wolf", 0xFFFFFF, 0xFFFF5D).setUnlocalizedName ("item_spawn_egg").setMaxStackSize(12);
    		//.setTextureName("glistremod:spawn_egg").setCreativeTab(TabRegistry.tabCustom);   		

    item1_spawn_egg = new GlistreEntityMonsterPlacer("corrupted_tobie", 0xc5b358, 0xFFD700).setUnlocalizedName ("item_spawn_egg_1").setMaxStackSize(12);
    		//.setTextureName("glistremod:spawn_egg1");
    /*  itemSpawnEgg2 = new GlistreEntityMonsterPlacer("Tobie Guardian", 0xFFFFFF, 0xFFD700).setUnlocalizedName ("spawn_egg2").setTextureName("glistremod:spawn_egg2");
    
    item3_spawn_egg = new GlistreEntityMonsterPlacer("tobie_skelly_guardian", 0xFFFFFF, 0xc5b358).setUnlocalizedName ("item_spawn_egg_3").setMaxStackSize(12);*/
    		//.setTextureName("glistremod:spawn_egg3");

/*    sceptre_egg = new SceptreMonsterPlacer("tobie_skelly_guardian", 0xFFFFFF, 0xc5b358).setUnlocalizedName ("sceptre_egg").setMaxStackSize(12);
    		//.setTextureName("glistremod:sceptre_1");

    tobie_queen_egg = new QueenElizabethMonsterPlacer("tobie_queen_elizabeth", 0xFF0000, 0xc5b358).setUnlocalizedName ("tobie_queen_egg").setMaxStackSize(2);
    		//.setTextureName("glistremod:tobieQueenSceptre");

    tobie_king_egg = new TobieKingMonsterPlacer("tobie_king", 0xFF0000, 0xc5b358).setUnlocalizedName ("tobie_king_egg").setMaxStackSize(12);
    		//.setTextureName("glistremod:tobieQueenSceptre");*/
    

 	 /**	
 	  * SECRET BOOKS SECTION 
 	  * *********************************************************** */
// LOAD THE BOOKS

	ancient_book = new ItemAncientBook("ancient_book", "ancient_book").setUnlocalizedName("ancient_book").setMaxStackSize(16);
	recipe_book = new ItemRecipeBook().setRegistryName("recipe_book").setUnlocalizedName("recipe_book").setMaxStackSize(16);

	secret_book = new ItemSecretBook().setRegistryName("secret_book").setUnlocalizedName("secret_book").setMaxStackSize(16);

    
// LOAD THE RECIPE BOOK
/*    recipe_book = new ItemStack(Items.WRITTEN_BOOK);
    
    // Create NBT data and add to the book
/*    NBTTagCompound tag = new NBTTagCompound();
    NBTTagList bookPages = new NBTTagList();  
    recipe_book.setTagInfo("pages", bookPages);
    recipe_book.setTagInfo("author", new NBTTagString(TextFormatting.GOLD + "Glistre"));
    recipe_book.setTagInfo("title", new NBTTagString(TextFormatting.GOLD + "Glistre Recipes"));
    recipeBook.setTagInfo("pages", bookPages);


    bookPages.appendTag(new NBTTagString("This book provides vital instructions from the maker" + TextFormatting.GOLD + " Glistre \n \nKeep it in a safe place!"));
    bookPages.appendTag(new NBTTagString("Your adventure begins by finding the Glistering Biome. \n \nOr search for silver ore and make a portal to there!"));     
    bookPages.appendTag(new NBTTagString("Follow the Recipes in this Book!  But beware your greed . . .a corruption awaits ye in this adventure"));
    bookPages.appendTag(new NBTTagString("Glistre Dimension: \n \nBuild a portal with silver ore and light with glistre burner.  \n  \nSilver Ingot and Flint & Steel. . .a glistre burner makes"));   
    bookPages.appendTag(new NBTTagString("Recipes: \n \nCraft glistre dust from a silver ingot and a gold ingot. \n \nGlistre dust can be crafted into glistering bread, glistering pie, and glistre ingots."));
    bookPages.appendTag(new NBTTagString("Silver ingots can be smelted from silver ore. \n \nGlistre ingots can be smelted from glistre dust."));   
    bookPages.appendTag(new NBTTagString("You must have glistre dust to use the Scepter of OP, Blaster Gun, and Ender Gun. \n \nYou must have it in your inventory!"));     
    bookPages.appendTag(new NBTTagString("Silver Ingots make silver sword, armor, pickaxes, \n \nGlistre ingots are used to craft Glistre Sword, armor, pickaxe, etc."));
    bookPages.appendTag(new NBTTagString("Nori is crafted from nine seaweed. . .find it in the Glistering Biome, \n \nSushi can be crafted from nori and raw fish"));   
    bookPages.appendTag(new NBTTagString("Sparks Pickaxe can be crafted \n \nPattern: \n \n x x x \n   x   \n   b    \n \nx = Glistre Ingot \nb = blaze rod"));
    bookPages.appendTag(new NBTTagString("Mesmer's Magic Block can be crafted \n \nPattern: \n \n x x x \n x e x \n x x x  \n \nx = Glistre Ingot \ne = ender pearl"));
//    bookPages.appendTag(new NBTTagString("Spawn Tobie Guardian sceptre can be crafted \n \nPattern: \n \n   x   \n y y y \n   e    \n \nx = Glistre Ingot \ny = Eggs \ne = Emerald"));
    bookPages.appendTag(new NBTTagString("Tobie Sword has extended reach for fighting Tobies"));
    bookPages.appendTag(new NBTTagString("Search blacksmith, pyramid, mineshaft, dungeon, and stronghold chests for special and enchanted items!  "));
    bookPages.appendTag(new NBTTagString("Search for books in chests!"));
    bookPages.appendTag(new NBTTagString("Explore your world and find the Freon Biome or build a new portal to take you there!"));    
    bookPages.appendTag(new NBTTagString("Freon Dimension: \n \nMake a portal with silver blocks and light with ice burner.  \n  \nGlistre Ingot and Flint & Steel. . .an ice burner will make")); 
    bookPages.appendTag(new NBTTagString("Slay the corrupted Tobies!  \n \nGet powerful items! \n \nFind the Ancient Tome!!"));
    bookPages.appendTag(new NBTTagString("Beware. \n \nThe Mighty Sword will carry a heavy price to harness its full power !!"));
    bookPages.appendTag(new NBTTagString("Blocking with the Mighty Sword unleashes powerful freezing effects!!"));
    bookPages.appendTag(new NBTTagString("Find the Tower to kill the Evil King!"));
    bookPages.appendTag(new NBTTagString("Beware the Ice Chest... It has items you may need but hidden danger awaits you!"));
    bookPages.appendTag(new NBTTagString("Edit config file to turn on cheap recipes, turn off ore generation, change biome and dimension IDs. \n \nCheap recipe 1:\n \nGlistre bread  = dirt plus bread.")); 
    bookPages.appendTag(new NBTTagString("Cheap Recipe 2: \n \nSilver Sword is made with silver ore instead of silver ingots."));
    bookPages.appendTag(new NBTTagString("Cheap Recipe 3: \n \nGlistre dust can be crafted into a glistering bread and glistering pie."));  */ 

// LOAD THE SECRET RECIPE BOOK
/*    secret_book = new ItemStack(Items.WRITTEN_BOOK);
    // Create NBT data and add to the book
    NBTTagCompound tag1 = new NBTTagCompound();
    NBTTagList bookPages1 = new NBTTagList();  
    secret_book.setTagInfo("pages", bookPages1);
    secret_book.setTagInfo("author", new NBTTagString(TextFormatting.GOLD + "Glistre"));
    secret_book.setTagInfo("title", new NBTTagString(TextFormatting.GOLD + "Secret Recipes"));
    bookPages1.appendTag(new NBTTagString(TextFormatting.GOLD + "Bone and golden apple makes the Sword of Glistre!"));
    bookPages1.appendTag(new NBTTagString(TextFormatting.GREEN + "A glistre ingot plus golden apple makes the Helmet of Glistre!"));
    bookPages1.appendTag(new NBTTagString(TextFormatting.DARK_RED + "A bone with string shall make a Silver Sword! \n \nA bone with quartz shall make a Silver Pickaxe!"));
    bookPages1.appendTag(new NBTTagString(TextFormatting.DARK_AQUA + "A bone plus a feather shall make Glistre's Pickaxe!"));
    bookPages1.appendTag(new NBTTagString(TextFormatting.DARK_PURPLE + "Combine string with glistre dust to make the Skeleton Buster Bow!"));
    bookPages1.appendTag(new NBTTagString(TextFormatting.BLUE + "Cure the Mighty Sword by cooking in Furnace!"));
    bookPages1.appendTag(new NBTTagString(TextFormatting.LIGHT_PURPLE + "By the way, you might want to harvest that colorful ore on top of the Tower . . a reward awaits!"));*/
    

 // LOAD THE ANCIENT BOOK
/*    ChestGenHooks loot = ChestGenHooks.getInfo("Category"); // create registered ChestGenHooks
    loot.addItem(new WeightedRandomChestContent(ancient_book, 2, 3, 20));    
    ancient_book = new ItemWrittenBook();
    // Create NBT data and add to the book
    NBTTagCompound tag2 = new NBTTagCompound();
    NBTTagList bookPages2 = new NBTTagList();  
    ancient_book.setTagInfo("pages", bookPages2);
    ancient_book.setTagInfo("author", new NBTTagString(TextFormatting.GOLD + "Glistre"));
    ancient_book.setTagInfo("title", new NBTTagString(TextFormatting.GOLD + "Ancient Tome"));
    bookPages2.appendTag(new NBTTagString(TextFormatting.BLUE + "Enter the Freon Dimension and save the Glistering Biome.  \n \nYe must find the tower where thee flows red bludd."));   
    bookPages2.appendTag(new NBTTagString(TextFormatting.DARK_AQUA + "A dark king is corrupting this paradise. \n  \nYe must slay him or all shall be lost."));
    bookPages2.appendTag(new NBTTagString(TextFormatting.DARK_RED + "Beware the red bludd. \n \nIt makes you sick."));
    bookPages2.appendTag(new NBTTagString(TextFormatting.DARK_GREEN + "You will fair better with special items.")); 
    bookPages2.appendTag(new NBTTagString(TextFormatting.GOLD + "Loot the King's chest. . .if you dare!"));
    bookPages2.appendTag(new NBTTagString(TextFormatting.DARK_RED + "ALWAYS WATCH OUT BEHIND YOU!!!!")); */
   
    // Add new loot (Params: Itemstack(theItem), min, max, rarity)
    
//TODO:  add all this stuff for 1.10.2 in Loot event 
	
	/*    ChestGenHooks.addItem("bonusChest", new WeightedRandomChestContent(new ItemStack(ItemRegistry.blaster_gun_1), 1, 1, 20));
    ChestGenHooks.addItem("bonusChest", new WeightedRandomChestContent(new ItemStack(ItemRegistry.glistre_dust), 1, 6, 80));
    ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(secret_book, 1, 1, 50)); 
    ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(ancient_book, 1, 1, 90));   
    ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH).addItem(new WeightedRandomChestContent(secret_book,1,1,90));
    ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH).addItem(new WeightedRandomChestContent(new ItemStack(ItemRegistry.nausea_protection),1,2,5));
    ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH).addItem(new WeightedRandomChestContent(new ItemStack(ItemRegistry.silver_pickaxe_1),1,1,10));    
    ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_DESERT_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(ItemRegistry.poison_protection),1,2,5));   
    ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_DESERT_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(ItemRegistry.nausea_protection),1,2,5));   
    ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_DESERT_CHEST).addItem(new WeightedRandomChestContent(secret_book,0,1,20));
    ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_DESERT_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(ItemRegistry.sasquatch), 1, 1, 10));
    ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_DESERT_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(ItemRegistry.wolf_howl), 1, 1, 10));
    ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_DESERT_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(ItemRegistry.wolves_howling), 1, 1, 10));    
    ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_DESERT_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(ItemRegistry.glistre_ingot), 2, 5, 50));
    ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_DESERT_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(Items.diamond), 1, 7, 30));
    ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_DESERT_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(ItemRegistry.sceptre_1), 1, 2, 30));    
    ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_DESERT_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(ItemRegistry.glistre_food_2), 1, 12, 30));    
    ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(new WeightedRandomChestContent(secret_book,1,1,15));
    ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(ItemRegistry.poison_protection),1,2,15));   
    ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(ItemRegistry.mighty_sword),1,1,15));   
    ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_JUNGLE_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(ItemRegistry.custom_bow_1),1,1,8));
    ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(ItemRegistry.mighty_sword),1,1,10));
    ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(silver_pickaxe_1),1,1,20));
    ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(glistre_pickaxe),1,1,12));
    ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(ItemRegistry.nausea_protection),1,2,5));
    ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(glistre_pickaxe_2),1,1,8));
    ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(glistre_sword),1,1,8));
    ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CORRIDOR).addItem(new WeightedRandomChestContent(secret_book,1,1,20));
    ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_LIBRARY).addItem(new WeightedRandomChestContent(ancient_book,1,1,30));
    ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_LIBRARY).addItem(new WeightedRandomChestContent(secret_book,1,1,20));  */
 
 

	}
/*	public ItemStack getAncientBook(){
		return ancient_book;
	}*/
	
/*	public Item getSecretBook(){
		return secret_book;
	}*/

	public static void register(){
		registerItem(ancient_book);

       // GameRegistry.registerItem(silver_ingot_1);

     //   GameRegistry.registerItem(glistre_dust);

     //   GameRegistry.registerItem(glistre_ingot);
//	PIE        
     //   GameRegistry.registerItem(glistre_food_1);
//	BREAD        
      //  GameRegistry.registerItem(glistre_food_2);
      
    //    GameRegistry.registerItem(nori);

    //    GameRegistry.registerItem(sushi);

     //   GameRegistry.registerItem(silver_sword_1);
    	
    //	GameRegistry.registerItem(glistre_sword);
 	
    	//GameRegistry.registerItem(mighty_sword);
    	//GameRegistry.registerItem(mighty_ice_sword);
    	
      //  GameRegistry.registerItem(silver_pickaxe_1);

    	//GameRegistry.registerItem(glistre_pickaxe);
//Sparks Pickaxe    	
       // GameRegistry.registerItem(glistre_pickaxe_2);

      	//GameRegistry.registerItem(silver_helmet_1);
      	//GameRegistry.registerItem(silver_chestplate_1);    	
      //	GameRegistry.registerItem(silver_leggings_1);    	
    //  	GameRegistry.registerItem(silver_boots_1);
      		
    //	GameRegistry.registerItem(glistre_helmet_1);
    	 		
   	//	GameRegistry.registerItem(glistre_chestplate_1);    				
   	//	GameRegistry.registerItem(glistre_leggings_1);   				
   	//	GameRegistry.registerItem(glistre_boots_1);
       
   //		GameRegistry.registerItem(chain_helmet_1);
   //		GameRegistry.registerItem(chain_chestplate_1);  	
   ////		GameRegistry.registerItem(chain_leggings_1);  	
   	//	GameRegistry.registerItem(chain_boots_1);
   		
   	//	GameRegistry.registerItem(emerald_helmet_1);
   	////	GameRegistry.registerItem(emerald_chestplate_1);  	
   //		GameRegistry.registerItem(emerald_leggings_1);  	
   //		GameRegistry.registerItem(emerald_boots_1);

   	//    GameRegistry.registerItem(custom_bow_1, custom_bow_1.getUnlocalizedName().substring(5));
   	 //   GameRegistry.registerItem(custom_bow_1);

   //	    GameRegistry.registerItem(wolf_howl);
   //	    GameRegistry.registerItem(wolves_howling);
  // 	    GameRegistry.registerItem(sasquatch);
   	    
   	    
  // 	    GameRegistry.registerItem(poison_protection);
  // 	    GameRegistry.registerItem(nausea_protection);
  // 	    GameRegistry.registerItem(vomitus, "vomitus");


 //  	    GameRegistry.registerItem(glistre_burner);
  // 	    GameRegistry.registerItem(toby_king_burner);
   	    
//		GameRegistry.registerItem(ancient_book);
//		GameRegistry.registerItem(recipe_book);
//		GameRegistry.registerItem(secret_book);
   	    
//   	    GameRegistry.registerItem(recipeBook, "recipeBook");

   	    //Register 1.8 egg  colors reverse corrupted toby egg
//  	    GameRegistry.registerItem(item_spawn_egg_2, "item_spawn_egg_2");   	       		
//  	    EntityRegistry.registerEgg(entityClass, primary, secondary);
   	    
//   	    EntityList.entityEggs.put(item_spawn_egg_2, new EntityEgg(0xFFD700, 0xc5b358))
   	    
 /* 	    GameRegistry.registerItem(sceptre_egg, "sceptre_egg");
   	    GameRegistry.registerItem(tobie_queen_egg, "tobie_queen_egg");
   	    GameRegistry.registerItem(tobie_king_egg, "tobie_king_egg");*/

	}
	
	public static void registerItem(Item item){
		item.setCreativeTab(TabRegistry.glistre_tab_1);
		GameRegistry.register(item);
		GlistreMod.log.info("Registered item: " + item.getUnlocalizedName().substring(5));
}

	public static void registerRenders()
	{
		
		registerRender(silver_ingot_1);
		registerRender(glistre_dust);registerRender(glistre_ingot);
		registerRender(glistre_food_1);
		registerRender(glistre_food_2);
		registerRender(nori);registerRender(sushi);
		registerRender(silver_sword_1);registerRender(glistre_sword);registerRender(mighty_sword);registerRender(mighty_ice_sword);
		registerRender(silver_pickaxe_1);registerRender(glistre_pickaxe);registerRender(glistre_pickaxe_2);
		registerRender(silver_helmet_1);registerRender(silver_chestplate_1);registerRender(silver_leggings_1);registerRender(silver_boots_1);
		registerRender(glistre_helmet_1);registerRender(glistre_chestplate_1);registerRender(glistre_leggings_1);registerRender(glistre_boots_1);
		registerRender(chain_helmet_1);registerRender(chain_chestplate_1);registerRender(chain_leggings_1);registerRender(chain_boots_1);
		registerRender(emerald_helmet_1);registerRender(emerald_chestplate_1);registerRender(emerald_leggings_1);registerRender(emerald_boots_1);

		registerRender(custom_bow_1);
		registerRender(sceptre_1);registerRender(blaster_gun_1);registerRender(tobie_gun_1);registerRender(ender_gun);
		registerRender(wolf_howl);registerRender(wolves_howling);registerRender(sasquatch);
		registerRender(poison_protection);registerRender(nausea_protection);registerRender(vomitus);
		registerRender(glistre_burner);registerRender(toby_king_burner);
		
		registerRender(ancient_book);
		registerRender(recipe_book);
		registerRender(secret_book);
//   	    registerRender(item_spawn_egg_2);

//		registerRender(sceptre_egg);registerRender(tobie_queen_egg);registerRender(tobie_king_egg);

		
	}
	@SideOnly(Side.CLIENT)
		public static void registerRender(Item item)
	{
	

  //blockregistry:  	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(Reference.MODID + ":" + block.getUnlocalizedName().substring(5), "inventory"));
	 //	ModelLoader.setCustomModelResourceLocation(item, 0 , new ModelResourceLocation(item.getRegistryName(), "inventory"));
	 	ModelLoader.setCustomModelResourceLocation(item, 0 , new ModelResourceLocation(Reference.MODID + ":" + item.getUnlocalizedName().substring(5),
 "inventory"));

		/*	for (int i = 0; i < NB_VARIANTS; i++) {
			String variant = item.getVariantProperty().getName() + "=" + item.getVariantName(i);
			ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(item.getRegistryName(), variant));*/
	 	


	  	
	} 	
}



