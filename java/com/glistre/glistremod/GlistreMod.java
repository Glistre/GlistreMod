package com.glistre.glistremod;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.apache.logging.log4j.Logger;

import com.glistre.glistremod.biome.WorldTypeFreon;
import com.glistre.glistremod.biome.WorldTypeGlistre;
import com.glistre.glistremod.blocks.fluids.ModFluids;
import com.glistre.glistremod.effects.potions.splash.EntitySplashProjectile;
import com.glistre.glistremod.entities.blacktobie.EntityBlackTobo;
import com.glistre.glistremod.entities.guardian.EntityTobieSkel;
import com.glistre.glistremod.entities.king.EntityTobieKing;
import com.glistre.glistremod.entities.queen.EntityTobieQueen;
import com.glistre.glistremod.entities.wolf.EntityBlackWolf;
import com.glistre.glistremod.entities.wolf.EntityGlistreWolf;
import com.glistre.glistremod.events.GlistreEventHandler;
import com.glistre.glistremod.events.GlistreModEventHooks;
import com.glistre.glistremod.events.GlistreModTerrainGenHooks;
import com.glistre.glistremod.init.BiomeRegistry;
import com.glistre.glistremod.init.BlockRegistry;
import com.glistre.glistremod.init.DimensionRegistry;
import com.glistre.glistremod.init.GMTileEntityRegistry;
import com.glistre.glistremod.init.GlistreEntityRegistry;
import com.glistre.glistremod.init.ItemRegistry;
//import com.glistre.glistremod.init.LootTableRegistry;
import com.glistre.glistremod.init.Recipes;
import com.glistre.glistremod.items.bow.BusterBow;
import com.glistre.glistremod.lib.ConfigurationGlistre;
import com.glistre.glistremod.lib.GlistreGuiFactory;
import com.glistre.glistremod.mapgen.GlistreVillagePieces;
import com.glistre.glistremod.mapgen.MapGenGlistreVillage;
import com.glistre.glistremod.mapgen.MapGenGlistreVillage.GlistreStart;
import com.glistre.glistremod.projectiles.blaster.EntityBlasterBolt;
import com.glistre.glistremod.projectiles.blaster.EntityEnderBoltFireball;
import com.glistre.glistremod.projectiles.blaster.RendreGlistreFactory;
import com.glistre.glistremod.projectiles.tobyworstsword.TobyEntityProjectile;
import com.glistre.glistremod.lib.GlistreConfigGui;
import com.glistre.glistremod.proxies.CommonProxy;
import com.glistre.glistremod.reference.Reference;
import com.glistre.glistremod.tabs.TabRegistry;
import com.glistre.glistremod.util.GlistreModelManager;
import com.glistre.glistremod.worldgen.WorldGen;

import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.potion.Potion;
import net.minecraft.stats.Achievement;
import net.minecraft.util.ResourceLocation;
//import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.WorldType;
import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.AchievementPage;
//import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
//import sun.rmi.runtime.Log;


/* 	MOD INFO */

	@Mod(modid = Reference.MODID, name = Reference.MOD_NAME, version = Reference.VERSION, guiFactory = Reference.GUI_FACTORY, canBeDeactivated = true)

	//, dependencies = "required-after:Mystcraft"
	
public class GlistreMod {
		
	
/*	PROXY INFO */
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;

	public static Configuration config;
	
	@Instance(Reference.MODID)
	//@Mod.Instance(value = "modid")
	//@Mod.Instance(Reference.MOD_ID)	//change 1.10.2 to value = "modid"?
	public static GlistreMod instance;
	
	public static Logger log = FMLLog.getLogger();
	// use a named channel to identify packets related to this mod
	public static final String NETWORK_CHANNEL_NAME = "GlistreMod"; // put the name of your mod here  
	//or create field 	public static String NETWORK_CHANNEL_NAME;

	public static FMLEventChannel channel;
	// networking
	public static SimpleNetworkWrapper network;
	public static int modEntityID = 0;


/**	
 * DECLARATION SECTION 
 * *********************************************************** */

// DECLARE TOOL MATERIAL
				/**name, harvestLevel, maxUses, efficiency, damage, enchantability*/
	public static ToolMaterial Silvers=EnumHelper.addToolMaterial("Silvers", 4, 1520, 1.0F, 6, 16);
	public static ToolMaterial Glistres=EnumHelper.addToolMaterial("Glistres", 4, 2020, 1.0F, 7, 16);
	public static ToolMaterial Sparks=EnumHelper.addToolMaterial("Sparks", 4, 3020, 1.0F, 8, 16);
              

//	DECLARE THE NEW ACHIEVEMENTS	
       public static Achievement blockAchievement_1;
       public static Achievement mobKillAchievement_1;

       public static final ResourceLocation CUSTOM_CHEST_LOOT = register("custom_chest_loot");

   	private static ResourceLocation register(String name) {
		return new ResourceLocation(Reference.MODID, name);
	}
   	
@EventHandler	
public void preInit(FMLPreInitializationEvent event) {

	config = new Configuration(event.getSuggestedConfigurationFile());

	ConfigurationGlistre.syncConfig();

	
/**	
 * LOAD SECTION 
 * *********************************************************** */ 

	 BlockRegistry.init();
	 BlockRegistry.register();
	 ModFluids.registerFluids();
//	 GlistreModelManager.registerAllModels();//already in proxy.init (ClientProxy) so don't need it here?
//	 ItemRegistry.GlistreMod();
	 ItemRegistry.init();  //Are these not needed since I have public void Init in the client proxy?
	 ItemRegistry.register();
	 LootTableList.register(CUSTOM_CHEST_LOOT);
	 TabRegistry.initializeTab();
	 TabRegistry.registerTab();
	 GMTileEntityRegistry.GlistreMod();
//	 LootTableRegistry.registerLootTables();
	 GlistreEntityRegistry.initializeEntity();
	 GlistreEntityRegistry.register();
	 GlistreEntityRegistry.registerEntity();
	 
	 proxy.preInit();


	 MinecraftForge.EVENT_BUS.register(new GlistreModEventHooks());
	 


	 
	 
// STRUCTURES
	 MapGenStructureIO.registerStructure(MapGenGlistreVillage.GlistreStart.class, "Glistre_Village");
	 GlistreVillagePieces.registerVillagePieces(); //put your custom village in there
		// StructureVillagePieces.registerVillagePieces();
        log.info("PreInitialization Complete!");
}


@EventHandler
	public static void init(FMLInitializationEvent event ) 
	{
	 proxy.registerRenders(); //can be done in any init phase but must be done AFTER items are registered


	proxy.init();

//	 TabRegistry.GlistreMod();
		Recipes.initShapedRecipes();
		Recipes.initShapelessRecipes();
		Recipes.initSmeltingRecipes();


		BiomeRegistry.GlistreMod();
		DimensionRegistry.GlistreMod();
		WorldGen.initWorldGen();

//		GlistreEntityRegistry.GlistreMod();
		//the following is code reflection to make Potion effects work 
		Potion[] potionTypes = null;
		for (Field f : Potion.class.getDeclaredFields()) {
		f.setAccessible(true);
		try {
//		if (f.getName().equals("potionTypes") || f.getName().equals("field_76425_a")) {
			
		if (f.getName().equals("potionTypes")) {
		Field modfield = Field.class.getDeclaredField("modifiers");
		modfield.setAccessible(true);
		modfield.setInt(f, f.getModifiers() & ~Modifier.FINAL);
		potionTypes = (Potion[])f.get(null);
		final Potion[] newPotionTypes = new Potion[256];
		System.arraycopy(potionTypes, 0, newPotionTypes, 0, potionTypes.length);
		f.set(null, newPotionTypes);
		}
		}
		catch (Exception e) {
		System.err.println("Severe error, please report this to the mod author:");
		System.err.println(e);
		}
		}
		
	   	FMLCommonHandler.instance().bus().register(instance);
	   	GlistreEventHandler handler = new GlistreEventHandler();

	
    //  REGISTER ENTITY

  //      	EntityRegistry.addSpawn(EntityGlistreWolf.class, 20, 3, 7, EnumCreatureType.CREATURE, BiomeRegistry.biomeGlistre);
       		EntityRegistry.addSpawn(EntityGlistreWolf.class, 5, 1, 2, EnumCreatureType.CREATURE, Biomes.JUNGLE);
       		EntityRegistry.addSpawn(EntityGlistreWolf.class, 5, 1, 2, EnumCreatureType.CREATURE, Biomes.JUNGLE_EDGE);
       		EntityRegistry.addSpawn(EntityGlistreWolf.class, 5, 1, 3, EnumCreatureType.CREATURE, Biomes.TAIGA);
       		EntityRegistry.addSpawn(EntityGlistreWolf.class, 5, 1, 2, EnumCreatureType.CREATURE, Biomes.FOREST);
       		EntityRegistry.addSpawn(EntityGlistreWolf.class, 5, 1, 2, EnumCreatureType.CREATURE, Biomes.ROOFED_FOREST);
       		EntityRegistry.addSpawn(EntityGlistreWolf.class, 5, 1, 2, EnumCreatureType.CREATURE, Biomes.SAVANNA);
       		EntityRegistry.addSpawn(EntityGlistreWolf.class, 5, 1, 3, EnumCreatureType.CREATURE, Biomes.COLD_TAIGA);
       		EntityRegistry.addSpawn(EntityGlistreWolf.class, 5, 1, 1, EnumCreatureType.CREATURE, Biomes.SWAMPLAND);		

            EntityRegistry.addSpawn(EntityTobieSkel.class, 20, 1, 2, EnumCreatureType.CREATURE, BiomeRegistry.biomeGlistre);
            EntityRegistry.addSpawn(EntityBlackTobo.class, 14, 1, 2, EnumCreatureType.CREATURE, BiomeRegistry.biomeFreon);
       		EntityRegistry.addSpawn(EntityBlackTobo.class, 12, 1, 1, EnumCreatureType.CREATURE, BiomeRegistry.biomeGlistre);
        
       		EntityRegistry.addSpawn(EntityBlackTobo.class, 5, 1, 3, EnumCreatureType.CREATURE, Biomes.FOREST);
       		EntityRegistry.addSpawn(EntityBlackTobo.class, 5, 1, 3, EnumCreatureType.CREATURE, Biomes.COLD_TAIGA);
       		EntityRegistry.addSpawn(EntityBlackTobo.class, 5, 1, 3, EnumCreatureType.CREATURE, Biomes.EXTREME_HILLS);
           
            EntityRegistry.addSpawn(EntityBlackWolf.class, 5, 1, 3, EnumCreatureType.CREATURE, Biomes.BIRCH_FOREST);
       		EntityRegistry.addSpawn(EntityBlackWolf.class, 5, 1, 2, EnumCreatureType.CREATURE, Biomes.FOREST);
  //      	EntityRegistry.addSpawn(EntityBlackTobo.class, 20, 1, 3, EnumCreatureType.CREATURE, BiomeRegistry.biomeFreon);	


         	//1.8update changed by adding cast
        	blockAchievement_1 = (Achievement) new Achievement("achievement.blockAchievement_1", "blockAchievement_1", -1, -3, BlockRegistry.silver_ore_1, (Achievement)null).registerStat();
        	mobKillAchievement_1 = (Achievement) new Achievement("achievement.mobKillAchievement_1", "mobKillAchievement_1", -1, -2, ItemRegistry.ancient_book, blockAchievement_1).setSpecial().registerStat();
            AchievementPage.registerAchievementPage(new AchievementPage("GlistreMod Achievements", new Achievement[]{blockAchievement_1, mobKillAchievement_1}));

  		    FMLCommonHandler.instance().bus().register(handler);//don't really need 1.8.9 you register an instance but needed in 1.8 no idea
        	MinecraftForge.EVENT_BUS.register(handler);
        	MinecraftForge.TERRAIN_GEN_BUS.register(new GlistreModTerrainGenHooks());

     	
   		log.info("Initialization Complete!");	  		
   		
	}
@SubscribeEvent
public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event){
	if(event.getModID().equals(Reference.MODID)){
		ConfigurationGlistre.syncConfig();
		//resync configs this is where restart would or not be required	
		System.out.println("Config changed!");
		log.info("Updating config...");
	}
		
}
@EventHandler
	public static void postInit( FMLPostInitializationEvent event ) 
	{

	proxy.postInit();
	MinecraftForge.EVENT_BUS.register(new GlistreModEventHooks());
   	MinecraftForge.TERRAIN_GEN_BUS.register(new GlistreModTerrainGenHooks());


//	MinecraftForge.EVENT_BUS.register(new GuiModInfo(Minecraft.getMinecraft()));
	WorldType BIOMEFREON = new WorldTypeFreon(8, "biomeFreon");
	WorldType BIOMEGLISTRE = new WorldTypeGlistre(9, "biomeGlistre");
	
	//if(MystAPI.instability != null) {
		//API usage
	//}
	
		log.info("Post Initialization Complete!");
	
	}
			
}	
