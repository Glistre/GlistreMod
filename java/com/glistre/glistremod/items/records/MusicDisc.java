package com.glistre.glistremod.items.records;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.glistre.glistremod.reference.Reference;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.BlockJukebox;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class MusicDisc extends ItemRecord
{
	
private static final Map records = new HashMap();
public final String recordName;
 public MusicDisc(String recordName, SoundEvent sound)
 {
	 super(recordName, sound);
 this.recordName = recordName;
 this.maxStackSize = 1;
 records.put(recordName, this);
}



 @Override
 public EnumActionResult onItemUse(ItemStack itemStack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ){
 IBlockState state = worldIn.getBlockState(pos);

 if (state.getBlock() == Blocks.JUKEBOX && !((Boolean)state.getValue(BlockJukebox.HAS_RECORD)).booleanValue())
 
 {
// if (worldIn.getBlockState(pos).getBlock() == Blocks.jukebox && worldIn.getBlockMetadata(x, y, z) == 0)
// {
 if (worldIn.isRemote)
 return EnumActionResult.SUCCESS;
 else{
	 ((BlockJukebox)Blocks.JUKEBOX).insertRecord(worldIn, pos, state, itemStack);
	 //	 ((BlockJukebox)Blocks.jukebox).func_149926_b(worldIn, pos, itemStack);
//world.playAuxSFX(1005, x, y, z, this.itemID);//1.10.2 changed again to #playEvent
 worldIn.playEvent((EntityPlayer)null, 1005, pos, Item.getIdFromItem(this));
 --itemStack.stackSize;
 return EnumActionResult.SUCCESS;
 }
 } 
 else
 return EnumActionResult.PASS;
 }


 
/**
 * Return the record item corresponding to the given name.
 */
@SideOnly(Side.CLIENT)
@SubscribeEvent

 public static MusicDisc getRecord(String par0Str)
 {
 return (MusicDisc)records.get(par0Str);
 }

@Override
public ResourceLocation getRecordResource(String name)
{
	 return new ResourceLocation(Reference.MODID +":"+ name);
}


}




 