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

public class ItemRecipeBook extends ItemWrittenBook
{
    public ItemRecipeBook()
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
       itemStack.setTagInfo("title", new NBTTagString(TextFormatting.GOLD + "Glistre Recipes"));
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
//       bookPages.appendTag(new NBTTagString("Spawn Tobie Guardian sceptre can be crafted \n \nPattern: \n \n   x   \n y y y \n   e    \n \nx = Glistre Ingot \ny = Eggs \ne = Emerald"));
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
       bookPages.appendTag(new NBTTagString("Cheap Recipe 3: \n \nGlistre dust can be crafted into a glistering bread and glistering pie.")); 

    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
        return true;
    }
}