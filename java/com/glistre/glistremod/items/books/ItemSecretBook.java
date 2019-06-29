package com.glistre.glistremod.items.books;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemWrittenBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSecretBook extends ItemWrittenBook
{
    public ItemSecretBook()
    {
       
    	this.setMaxStackSize(16);
    }
    
    private NBTTagList putTableOfContents(NBTTagList bookTagList)
    {
     NBTTagList bookPages = new NBTTagList();
        bookPages.appendTag(new NBTTagString("Page 1"));
        

       return bookTagList;
    }



    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */


    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
    {
 	  // playerIn.displayGUIBook(itemStackIn);
 	  if (playerIn.worldObj.isRemote)
 	 {
 	 Minecraft.getMinecraft().displayGuiScreen(new GuiScreenBook(playerIn, itemStackIn, false));
 	 }
 	   return itemStackIn;
    }
    
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int unknownInt, boolean unknownBool)
    {
       NBTTagList bookPages = new NBTTagList();

       bookPages = putTableOfContents(bookPages);

       itemStack.setTagInfo("pages", bookPages);
       itemStack.setTagInfo("author", new NBTTagString(TextFormatting.GOLD + "Glistre"));
       itemStack.setTagInfo("title", new NBTTagString(TextFormatting.GOLD + "Secret Recipes"));
       bookPages.appendTag(new NBTTagString(TextFormatting.GOLD + "Bone and golden apple makes the Sword of Glistre!"));
       bookPages.appendTag(new NBTTagString(TextFormatting.GREEN + "A glistre ingot plus golden apple makes the Helmet of Glistre!"));
       bookPages.appendTag(new NBTTagString(TextFormatting.DARK_RED + "A bone with string shall make a Silver Sword! \n \nA bone with quartz shall make a Silver Pickaxe!"));
       bookPages.appendTag(new NBTTagString(TextFormatting.DARK_AQUA + "A bone plus a feather shall make Glistre's Pickaxe!"));
       bookPages.appendTag(new NBTTagString(TextFormatting.DARK_PURPLE + "Combine string with glistre dust to make the Skeleton Buster Bow!"));
       bookPages.appendTag(new NBTTagString(TextFormatting.BLUE + "Cure the Mighty Sword by cooking in Furnace!"));
       bookPages.appendTag(new NBTTagString(TextFormatting.LIGHT_PURPLE + "By the way, you might want to harvest that colorful ore on top of the Tower . . a reward awaits!")); 
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
        return true;
    }
}