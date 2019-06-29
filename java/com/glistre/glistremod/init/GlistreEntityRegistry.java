package com.glistre.glistremod.init;

import com.glistre.glistremod.GlistreMod;
import com.glistre.glistremod.effects.potions.splash.EntitySplashProjectile;
import com.glistre.glistremod.effects.potions.splash.ItemSplashPotion;
import com.glistre.glistremod.effects.potions.splash.RenderSplashPotion;
import com.glistre.glistremod.entities.blacktobie.EntityBlackTobo;
import com.glistre.glistremod.entities.guardian.EntityTobieSkel;

import com.glistre.glistremod.entities.king.EntityTobieKing;
import com.glistre.glistremod.entities.queen.EntityTobieQueen;
//import com.glistre.glistremod.entities.unused.EntityTobie;
import com.glistre.glistremod.entities.wolf.EntityBlackWolf;
import com.glistre.glistremod.entities.wolf.EntityGlistreWolf;
import com.glistre.glistremod.projectiles.blaster.EntityBlasterBolt;
import com.glistre.glistremod.projectiles.blaster.EntityEnderBoltFireball;
import com.glistre.glistremod.projectiles.blaster.Projectile2;
import com.glistre.glistremod.projectiles.tobyworstsword.TobyEntityProjectile;
import com.glistre.glistremod.projectiles.tobyworstsword.TobyEntityThrowable;
import com.glistre.glistremod.projectiles.tobyworstsword.TobyEntitySword;
import com.glistre.glistremod.projectiles.tobyworstsword.TobyRenderProjectile;
import com.glistre.glistremod.reference.Reference;
import com.glistre.glistremod.tabs.TabRegistry;

import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
//import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityList;
import net.minecraft.init.MobEffects;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.util.ResourceLocation;

public class GlistreEntityRegistry {

	public static void GlistreMod(){
		initializeEntity();
		registerEntity();		
		register();
	}
	
	// the ray or blast bolt like an arrow
    public static Item blaster_bolt_1;
    
	public static Item splash_poison_protection;
//	Tobie's Worst Enemy Sword
    public static Item tobie_worst_projectile_1;
    

//    public int blaster_bolt_1ID;
    public static Item ender_bolt_1;
 //   public int ender_bolt_1ID;
    public static Item sceptre_bolt_1;
    
//    public static Item item_spawn_egg_2 = new ItemMonsterPlacer().setUnlocalizedName("black_wolf").setCreativeTab(TabRegistry.tab_builder).setMaxStackSize(12);

//    public int sceptre_bolt_1ID;
	
public static int modEntityID = 0;

	public static void initializeEntity(){
        blaster_bolt_1 = new Projectile2(blaster_bolt_1, "blaster_bolt_1").setUnlocalizedName("blaster_bolt_1").setMaxStackSize(64).setCreativeTab(TabRegistry.tab_potion);

       // Potion id = Potion.REGISTRY.getObject(new ResourceLocation("glistremod", "splash_poison_protection"));
      //  Potion.REGISTRY.register(id, key, value);
  //PotionType.getPotionTypeForName(tag.getString("Potion")).getID(p_185171_0_)
		splash_poison_protection = new ItemSplashPotion(16, "splash_poison_protection", new PotionEffect[]{new PotionEffect(MobEffects.REGENERATION, 1200)}, 888888).setUnlocalizedName("splash_poison_protection");
		//TOBIE'S WORST ENEMY Sword/Item   
        tobie_worst_projectile_1 = new TobyEntitySword(Item.ToolMaterial.IRON).setUnlocalizedName("tobie_worst_projectile_1");
        ender_bolt_1 = new Projectile2(ender_bolt_1, "ender_bolt_1").setUnlocalizedName("ender_bolt_1").setMaxStackSize(64).setCreativeTab(TabRegistry.tab_potion);
        sceptre_bolt_1 = new Projectile2(sceptre_bolt_1, "sceptre_bolt_1").setUnlocalizedName("sceptre_bolt_1").setMaxStackSize(64).setCreativeTab(TabRegistry.tab_potion);
	
}
	
	public static void registerEntity() {
		// 1.8 removed crash  
        //SPLASH POTION
//	    GameRegistry.registerItem(splash_poison_protection, "splash_poison_protection");

        EntityRegistry.registerModEntity(EntitySplashProjectile.class, "splash_poison_protection", ++modEntityID, GlistreMod.instance, 64, 1, true);
        //1.8 removed crash
        // TOBIE'S WORST ENEMY	
 //  	    GameRegistry.registerItem(tobie_worst_projectile_1, "tobie_worst_projectile_1");  
        EntityRegistry.registerModEntity(TobyEntityProjectile.class, "tobie_worst_projectile_1", ++modEntityID, GlistreMod.instance, 64, 1, true);

        // BLASTERS		

        EntityRegistry.registerModEntity(EntityEnderBoltFireball.class, "ender_bolt_1", ++modEntityID, GlistreMod.instance, 64, 1, true);
// MOBS		
        // 80 is max distance from player, 3 is update frequencies must be 3 for mobs, projectiles must be more precise 1, 
        //last parameter is send velocity information should be true unless entity never moves
	    EntityRegistry.registerModEntity(EntityGlistreWolf.class, "glistre_wolf", ++modEntityID, GlistreMod.instance, 80, 3, true);
	    EntityRegistry.registerEgg (EntityGlistreWolf.class, 0xFFFFFF, 0xFFFF5D);
	    EntityRegistry.registerModEntity(EntityBlackWolf.class, "black_wolf", ++modEntityID, GlistreMod.instance, 80, 3, true);
	    EntityRegistry.registerEgg (EntityBlackWolf.class, 0xFFD700, 0xc5b358);
//	    EntityList.classToStringMapping.put(ItemRegistry.item_spawn_egg_2, "black_wolf");	    
	    EntityRegistry.registerModEntity(EntityBlackTobo.class, "corrupted_tobie", ++modEntityID, GlistreMod.instance, 80, 3, true);	
	    EntityRegistry.registerEgg (EntityBlackTobo.class, 0xc5b358, 0xFFD700);
	    EntityRegistry.registerModEntity(EntityTobieSkel.class, "tobie_skelly_guardian", ++modEntityID, GlistreMod.instance, 80, 3, true);
	    EntityRegistry.registerEgg (EntityTobieSkel.class, 0xCCAC00, 0xFF9900);
	    EntityRegistry.registerModEntity(EntityTobieKing.class, "tobie_king", ++modEntityID, GlistreMod.instance, 80, 3, true);
	    EntityRegistry.registerEgg (EntityTobieKing.class, 0x534600, 0xc5b358);
	    EntityRegistry.registerModEntity(EntityTobieQueen.class, "tobie_queen_elizabeth", ++modEntityID, GlistreMod.instance, 80, 3, true);
	    EntityRegistry.registerEgg (EntityTobieQueen.class, 0xFFD700, 0xCC0000);
	
	}
	
	public static void register(){
   	    GameRegistry.registerItem(blaster_bolt_1, "blaster_bolt_1");  
		GameRegistry.registerItem(splash_poison_protection, "splash_poison_protection");
   	    GameRegistry.registerItem(tobie_worst_projectile_1, "tobie_worst_projectile_1");  
   	    GameRegistry.registerItem(ender_bolt_1, "ender_bolt_1");
   	    GameRegistry.registerItem(sceptre_bolt_1, "sceptre_bolt_1");
	}
	
	public static void registerRenders(){
		registerRender(blaster_bolt_1);
		registerRender(splash_poison_protection);	
		registerRender(tobie_worst_projectile_1);
		registerRender(ender_bolt_1);
		registerRender(sceptre_bolt_1);
		
		
		
//		RenderingRegistry.registerEntityRenderingHandler(TobyEntityProjectile.class, new TobyRenderProjectile(Minecraft.getMinecraft().getRenderManager(), tobie_worst_projectile_1, Minecraft.getMinecraft().getRenderItem()));
//		RenderingRegistry.registerEntityRenderingHandler(EntitySplashProjectile.class, new RenderSplashPotion(Minecraft.getMinecraft().getRenderManager(), splash_poison_protection, Minecraft.getMinecraft().getRenderItem()));

	}
	
	public static void registerRender(Item item){
//		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
//	 	renderItem.getItemModelMesher().register(item, 0, new ModelResourceLocation(Reference.MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	 	ModelLoader.setCustomModelResourceLocation(item, 0 , new ModelResourceLocation(item.getRegistryName(), "inventory"));

	}
	

}
