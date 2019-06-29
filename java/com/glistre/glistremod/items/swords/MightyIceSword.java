package com.glistre.glistremod.items.swords;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.glistre.glistremod.GlistreMod;
import com.glistre.glistremod.entities.EntityIcePotionBall;
import com.glistre.glistremod.entities.king.EntityTobieKing;
import com.glistre.glistremod.init.BlockRegistry;
import com.glistre.glistremod.init.ItemRegistry;
import com.glistre.glistremod.reference.Reference;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;


public class MightyIceSword extends ItemSword {
    
    private String texturePath = "com.glistre.glistremod:";
    
    public MightyIceSword(Item ToolMaterial, String textureName)
    {
        super(Item.ToolMaterial.IRON);
        this.setUnlocalizedName(textureName);
//        this.setTextureName("MightySword");
    }

/*@Override   
@SideOnly(Side.CLIENT)

    public void registerIcons(IIconRegister iconRegister)
    {
        this.itemIcon = iconRegister.registerIcon(Reference.MOD_ID +":" + "MightySword");
    }*/
@Override
//@SideOnly(Side.CLIENT)
public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List list){
		//smite == 17
		ItemStack enchant01 = new ItemStack(ItemRegistry.mighty_sword);
		enchant01.addEnchantment(Enchantment.getEnchantmentByID(5), 6);
		list.add(enchant01);
	}
@Override
// Doing this override means that there is no localization for language
// unless you specifically check for localization here and convert
public String getItemStackDisplayName(ItemStack par1ItemStack)
{
    return (TextFormatting.BLUE + "Mighty Ice Sword...ice and effects!");
}

@Override
//@SideOnly(Side.CLIENT)
/** Makes your Item Enchanted when it is crafted */
    public void onCreated(ItemStack item, World world, EntityPlayer player)
    //16 == sharpness
    {
        item.addEnchantment(Enchantment.getEnchantmentByID(16), 4);
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
	//1==speed; 2 ==slowness
	if(true){
	if(player instanceof EntityLivingBase) ((EntityLivingBase)player).addPotionEffect(new PotionEffect(Potion.getPotionById(1), 200, 3));
	if(player instanceof EntityLivingBase) ((EntityLivingBase)player).removePotionEffect(Potion.getPotionById(2));

	}

	
	 for (int l = 0; l < 8; ++l)
     {
			worldIn.spawnEntityInWorld(new EntityIcePotionBall(worldIn, player, l));
//			worldIn.playSoundAtEntity(player, "glistremod:chill_hit", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 1.2F) + 0.5F);
		    //f * 0.5F== ?//added the float here no idea what it should be
		    float f = 1;
		    worldIn.playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
		//changed 1.10.2  need to fix "chill hit" sound

			
     }

	return new ActionResult(EnumActionResult.SUCCESS, itemStack);
	}


@Override
public boolean hitEntity(ItemStack item, EntityLivingBase target, EntityLivingBase player)
	{    
	//2 == slowness
	    target.addPotionEffect(new PotionEffect(Potion.getPotionById(2), 200, 7));
	    return true;

	}
/*@Override
public void onUpdate(ItemStack itemStack, World world, Entity entity, int itemSlot, boolean isSelected){
	if (entity instanceof EntityPlayer) {
		  EntityPlayer player = (EntityPlayer) entity;
		  if (player.inventory.hasItem(this)){
//		if(!itemStack.hasTagCompound())itemStack.setTagCompound(new NBTTagCompound());
		if( itemStack.stackTagCompound == null)
	        itemStack.setTagCompound(new NBTTagCompound());
		NBTTagCompound nbtTagCompound = itemStack.getTagCompound();		
//		NBTTagCompound NBT= itemStack.getTagCompound();
		nbtTagCompound.setBoolean("pickedUp", true);
		//NBTTagCompound NBT= itemStack.getTagCompound();
//		itemStack.setTagCompound(nbtTagCompound);
//		String s = ("Created by Player " + Minecraft.getMinecraft().thePlayer.getDisplayName());

		return;
	}
	}
}*/


/**
 * Called when a player drops the item into the world,
 * returning false from this will prevent the item from
 * being removed from the players inventory and spawning
 * in the world
 *
 * @param player The player that dropped the item
 * @param item The item stack, before the item is removed.
 */
@Override
public boolean onDroppedByPlayer(ItemStack itemStack, EntityPlayer player)
{
	super.onDroppedByPlayer(itemStack, player);
	NBTTagCompound nbtTagCompound = itemStack.getTagCompound();
//	if(!itemStack.hasTagCompound())itemStack.setTagCompound(new NBTTagCompound());
///////////might need to change getTagCompound 1.8 it was stackTagCompound
	if( itemStack.getTagCompound() == null)
        itemStack.setTagCompound(new NBTTagCompound());
	
//	NBTTagCompound NBT= itemStack.getTagCompound();
	nbtTagCompound.setBoolean("activated", true);
	//NBTTagCompound NBT= itemStack.getTagCompound();
//	itemStack.setTagCompound(nbtTagCompound);
//	String s = ("Created by Player " + Minecraft.getMinecraft().thePlayer.getDisplayName());

    return true;
		//super.onDroppedByPlayer(itemStack, player);
}
public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean b) {
	if(!itemStack.hasTagCompound())
		itemStack.setTagCompound(new NBTTagCompound());

    	 if (itemStack.hasTagCompound())
         {
             if (itemStack.getTagCompound().getBoolean("activated"))
             {
                 list.add(TextFormatting.BLUE + "Activated");
             }
         }
         super.addInformation(itemStack, player, list, b);
}

/*public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5){
	super.onUpdate(stack, world, entity, par4, par5);
	{
		EntityTobieKing entityTk = new EntityTobieKing(world);
	    EntityPlayer  player = (EntityPlayer) entity;
	    ItemStack equipped = player.getCurrentEquippedItem();
	    if((!(world.isRemote)) && equipped == stack){
//	    	  player.getBrightness(3.0f);
//	    	  player.getBrightnessForRender(15728880);
 		List<EntityPlayer> playerList = world.getEntitiesWithinAABB(EntityPlayer.class, entityTk.getBoundingBox().expand(155.0D, 95.0D,155.0D));
   			Iterator<EntityPlayer> i1 = playerList.iterator();
    			EntityPlayer entityplayer;
                      while (i1.hasNext()){
                            entityplayer = (EntityPlayer)i1.next();  			
                            player.addChatComponentMessage(
                		new ChatComponentText(EnumChatFormatting.DARK_GREEN + "Toby King in Tower location" 
                + EnumChatFormatting.DARK_RED + " X: " + (int)Math.round(entity.posX) 
                + EnumChatFormatting.GOLD + " | Y: " + (int)Math.round(entity.posY) 
                + EnumChatFormatting.DARK_AQUA +" | Z: " + (int)Math.round(entity.posZ)));
	
	    }
	}
}
}*/
}
