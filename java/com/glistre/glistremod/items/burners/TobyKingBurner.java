package com.glistre.glistremod.items.burners;

import com.glistre.glistremod.GlistreMod;
import com.glistre.glistremod.blocks.BlockGlistrePortal;
import com.glistre.glistremod.init.BlockRegistry;
import com.glistre.glistremod.tabs.TabRegistry;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class TobyKingBurner extends ItemFlintAndSteel
{


    public TobyKingBurner()
    {
    	
        this.maxStackSize = 1;
        this.setMaxDamage(64);
  //      this.setCreativeTab(TabRegistry.glistre_tab_1);
       }

       /**
        * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
        * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
        */
       public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
       {

           pos = pos.offset(side);

           if (!playerIn.canPlayerEdit(pos, side, stack))
           {
               return EnumActionResult.FAIL;
           }
           else
           {
               if (worldIn.isAirBlock(pos))
//               	if (world.getBlock(x, y - 1, z) != BlockRegistry.MyBlock_1 || !((BlockGlistrePortal) BlockRegistry.lightPortal).getPortalSize(world, x, y, z)){

               {
                   worldIn.playSound(playerIn, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);

//changed 1.10.2   worldIn.playSoundEffect((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, "fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
                   worldIn.setBlockState(pos, BlockRegistry.light_toby_king_fire.getDefaultState());
               }
//               	}
               stack.damageItem(1, playerIn);
               return EnumActionResult.SUCCESS;
           }
       }
   }