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

public class ItemAncientBook extends ItemWrittenBook
{
    public ItemAncientBook(String unlocalizedName, String registryName)
    {
       this.setUnlocalizedName(unlocalizedName);
       this.setRegistryName(registryName);
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
       itemStack.setTagInfo("title", new NBTTagString(TextFormatting.GOLD + "Ancient Tome"));
       bookPages.appendTag(new NBTTagString(TextFormatting.BLUE + "Enter the Freon Dimension and save the Glistering Biome.  \n \nYe must find the tower where thee flows red bludd."));   
       bookPages.appendTag(new NBTTagString(TextFormatting.DARK_AQUA + "A dark king is corrupting this paradise. \n  \nYe must slay him or all shall be lost."));
       bookPages.appendTag(new NBTTagString(TextFormatting.DARK_RED + "Beware the red bludd. \n \nIt makes you sick."));
       bookPages.appendTag(new NBTTagString(TextFormatting.DARK_GREEN + "You will fair better with special items.")); 
       bookPages.appendTag(new NBTTagString(TextFormatting.GOLD + "Loot the King's chest. . .if you dare!"));
       bookPages.appendTag(new NBTTagString(TextFormatting.DARK_RED + "ALWAYS WATCH OUT BEHIND YOU!!!!")); 
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
        return true;
    }
}