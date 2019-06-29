package com.glistre.glistremod.items.swords;

import java.util.List;
import java.util.Random;

import com.glistre.glistremod.GlistreMod;
import com.glistre.glistremod.effects.potions.splash.EntitySplashProjectile;
import com.glistre.glistremod.entities.EntityIcePotionBall;
import com.glistre.glistremod.init.BlockRegistry;
import com.glistre.glistremod.init.ItemRegistry;
import com.glistre.glistremod.reference.Reference;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
//import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;


public class CuredIceSword extends ItemSword {
    
    private String texturePath = "com.glistre.glistremod:";
    
    public CuredIceSword(Item ToolMaterial, String textureName)
    {
        super(Item.ToolMaterial.DIAMOND);
        this.setUnlocalizedName(textureName);
//        this.setTextureName("MightyIceSword");
    }

@Override
//@SideOnly(Side.CLIENT)
public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List list){
	//enchantment 17 =Smite
		ItemStack enchant01 = new ItemStack(ItemRegistry.mighty_ice_sword);
		enchant01.addEnchantment(Enchantment.getEnchantmentByID(17), 2);
		list.add(enchant01);
	}

@Override
//@SideOnly(Side.CLIENT)
/** Makes your Item Enchanted when it is crafted */
    public void onCreated(ItemStack item, World world, EntityPlayer player) 
    {
		//enchantment Sharpness = 16
        item.addEnchantment(Enchantment.getEnchantmentByID(16), 2);
        // Replace the "." after second "Enchantment" to see options
        // The number is the Enchantment Level
    }

@Override
//adds particle effects to Item
public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World worldIn, EntityPlayer player, EnumHand hand)
{
	float var4 = 1.0F;
	int i = (int)(player.prevPosX + (player.posX - player.prevPosX) * (double)var4);
	int j = (int)(player.prevPosY + (player.posY - player.prevPosY) * (double)var4);
	int k = (int)(player.prevPosZ + (player.posZ - player.prevPosZ) * (double)var4);

	if(true){
		//moveSpeed == 1
	if(player instanceof EntityLivingBase) ((EntityLivingBase)player).addPotionEffect(new PotionEffect(Potion.getPotionById(1), 200, 3));
	//moveSlowdown == 2
	if(player instanceof EntityLivingBase) ((EntityLivingBase)player).removePotionEffect(Potion.getPotionById(2));

	}

	 for (int l = 0; l < 8; ++l)
     {
			worldIn.spawnEntityInWorld(new EntityIcePotionBall(worldIn, player, l));
		    //f * 0.5F== ?//added the float here no idea what it should be
		    float f = 1;
		    worldIn.playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
		//changed 1.10.2
//			worldIn.playSoundAtEntity(player, "glistremod:chill_hit", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 1.2F) + 0.5F);
     
     }

//	player.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
//	return itemStack;
    return new ActionResult(EnumActionResult.SUCCESS, itemStack);       

	}

//1.10.2 change #onItemUse to EnumActionResult
@Override
public EnumActionResult onItemUse(ItemStack itemStack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		 //x, y, and z coordinates. Mess around with them by adding or subtracting to move where the block places.
	
	 	IBlockState state = BlockRegistry.liquid_ice.getDefaultState();
	 	worldIn.setBlockState(pos, state);
	    //f * 0.5F== ?//added the float here no idea what it should be
	    float f = 1;
	 	//"glistremod:fast_freezing_ice"
		 worldIn.playSound((EntityPlayer)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.BLOCK_DISPENSER_LAUNCH, SoundCategory.WEATHER, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 1.2F) + f + 0.5F);
		 // potion 2 = slowness
		 playerIn.removePotionEffect(Potion.getPotionById(2));
		 return EnumActionResult.SUCCESS;
	}

public boolean hitEntity(ItemStack item, EntityLivingBase target, EntityLivingBase player)
	{    
	    target.addPotionEffect(new PotionEffect(Potion.getPotionById(2), 200, 7));
	    return true;


	}
}
