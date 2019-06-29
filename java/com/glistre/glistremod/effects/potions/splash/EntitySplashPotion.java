package com.glistre.glistremod.effects.potions.splash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.glistre.glistremod.init.ItemRegistry;
import com.google.common.collect.HashMultimap;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
//import net.minecraft.util.text.translation.I18n;
import net.minecraft.client.resources.I18n;
import net.minecraft.world.World;

public class EntitySplashPotion extends Item
{
    /**
     * Contains a map from integers to the list of potion effects that potions with that damage value confer (to prevent
     * recalculating it).
     */
    private HashMap effectCache = new HashMap();
    private static final Map SUB_ITEMS_CACHE = new LinkedHashMap();

//    private static final String __OBFID = "CL_00000055";

    public EntitySplashPotion()
    {
        this.setMaxStackSize(1);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
//        this.setCreativeTab(CreativeTabs.tabBrewing);
    }

    
    /**
     * Returns a list of potion effects for the specified itemstack.
     */
    @Override
    public List getEffects(ItemStack itemstack)
    {
        if (itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey("CustomPotionEffects", 9))
        {
            ArrayList arraylist = new ArrayList();
            NBTTagList nbttaglist = itemstack.getTagCompound().getTagList("CustomPotionEffects", 10);

            for (int i = 0; i < nbttaglist.tagCount(); ++i)
            {
                NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
                PotionEffect potioneffect = PotionEffect.readCustomPotionEffectFromNBT(nbttagcompound);

                if (potioneffect != null)
                {
                    arraylist.add(potioneffect);
                }
            }

            return arraylist;
        }
        else
        {
            List list = (List)this.effectCache.get(Integer.valueOf(itemstack.getItemDamage()));

            if (list == null)
            {
            	//changed the List getPotionEffects to #register PotionTypeConversions in 1.10.2
                list = PotionHelper.registerPotionTypeConversion(input, reagentPredicate, output);(itemstack.getItemDamage(), false);
                this.effectCache.put(Integer.valueOf(itemstack.getItemDamage()), list);
            }

            return list;
        }
    }

    /**
     * Returns a list of effects for the specified potion damage value.
     */
    public List getEffects(int p_77834_1_)
    {
        List list = (List)this.effectCache.get(Integer.valueOf(p_77834_1_));

        if (list == null)
        {
            list = PotionHelper.getPotionEffects(p_77834_1_, false);
            this.effectCache.put(Integer.valueOf(p_77834_1_), list);
        }

        return list;
    }
/*//commented out 1.10.2 not sure why it's here cannot eat a splash potion  
 *  @Override
    public ItemStack onEaten(ItemStack p_77654_1_, World p_77654_2_, EntityPlayer p_77654_3_)
    {
        if (!p_77654_3_.capabilities.isCreativeMode)
        {
            --p_77654_1_.stackSize;
        }

        if (!p_77654_2_.isRemote)
        {
            List list = this.getEffects(p_77654_1_);

            if (list != null)
            {
                Iterator iterator = list.iterator();

                while (iterator.hasNext())
                {
                    PotionEffect potioneffect = (PotionEffect)iterator.next();
                    p_77654_3_.addPotionEffect(new PotionEffect(potioneffect));
                }
            }
        }

        if (!p_77654_3_.capabilities.isCreativeMode)
        {
            if (p_77654_1_.stackSize <= 0)
            {
                return new ItemStack(Items.GLASS_BOTTLE);
            }

            p_77654_3_.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
        }

        return p_77654_1_;
    }*/

    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack p_77626_1_)
    {
        return 32;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack p_77661_1_)
    {
        return EnumAction.DRINK;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
/*    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
        if (isSplash(itemStack.getItemDamage()))
        {
            if (!player.capabilities.isCreativeMode)
            {
                --itemStack.stackSize;
            }

            world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

            if (!world.isRemote)
            {
                world.spawnEntityInWorld(new EntityPotion(world, player, itemStack));
            }

            return itemStack;
        }
        else
        {
            player.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
            return itemStack;
        }
    }*/
    
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer player, EnumHand hand)
    {
        player.setActiveHand(hand);
		if(player.getActivePotionEffect(ItemRegistry.nausea_protect_potion) != null && player.getActivePotionEffect(ItemRegistry.nausea_protect_potion).getDuration() > 0){
		}
		else if(player.getActivePotionEffect(ItemRegistry.poison_protect_potion) != null && player.getActivePotionEffect(ItemRegistry.poison_protect_potion).getDuration() > 0){
			player.curePotionEffects(new ItemStack (ItemRegistry.vomitus));
		}
		else {		
 //added this to add Poison Protection II... duration, level, bad or not
    		player.addPotionEffect(new PotionEffect(ItemRegistry.vomitus_potion, 300, 0, true, true));
		}
        return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
        return false;
    }



    /**
     * returns whether or not a potion is a throwable splash potion based on damage value
     */
    public static boolean isSplash(int par1)
    {
        return (par1 & 16384) != 0;
    }

   @SideOnly(Side.CLIENT)
    public int getColorFromDamage(int p_77620_1_)
    {
        return PotionHelper.getLiquidColor(p_77620_1_, false);
    }

    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack p_82790_1_, int p_82790_2_)
    {
        return p_82790_2_ > 0 ? 16777215 : this.getColorFromDamage(p_82790_1_.getItemDamage());
    }

    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public boolean isEffectInstant(int p_77833_1_)
    {
        List list = this.getEffects(p_77833_1_);

        if (list != null && !list.isEmpty())
        {
            Iterator iterator = list.iterator();
            PotionEffect potioneffect;

            do
            {
                if (!iterator.hasNext())
                {
                    return false;
                }

                potioneffect = (PotionEffect)iterator.next();
            }
            while (!Potion.potionTypes[potioneffect.getPotionID()].isInstant());

            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public String getItemStackDisplayName(ItemStack itemstack)
    {
        if (itemstack.getItemDamage() == 0)
        {
           // return I18n.translateToLocal(PotionUtils.getPotionFromItem(itemstack).getNamePrefixed("item.emptyPotion.name"));
        	//return TextComponentTranslation.translateToLocal("item.emptyPotion.name").trim();
        	return I18n.format("item.emptyPotion.name").trim();
        }
        else
        {
            String s = "";

            if (isSplash(itemstack.getItemDamage()))
            {
                s = I18n.format("potion.prefix.grenade").trim() + " ";
            }

            List list = Items.POTIONITEM.getEffects(itemstack);
            String s1;

            if (list != null && !list.isEmpty())
            {
                s1 = ((PotionEffect)list.get(0)).getEffectName();
                s1 = s1 + ".postfix";
                return s + I18n.format(s1).trim();
            }
            else
            {
                s1 = PotionHelper.getPotionPrefix(itemstack.getItemDamage());
                return I18n.format(s1).trim() + " " + super.getItemStackDisplayName(itemstack);
            }
        }
    }

    /**
     * allows items to add custom lines of information to the mouseover description
     */
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List p_77624_3_, boolean p_77624_4_)
    {
        if (p_77624_1_.getItemDamage() != 0)
        {
            List list1 = Items.POTIONITEM.getEffects(p_77624_1_);
            HashMultimap hashmultimap = HashMultimap.create();
            Iterator iterator1;

            if (list1 != null && !list1.isEmpty())
            {
                iterator1 = list1.iterator();

                while (iterator1.hasNext())
                {
                    PotionEffect potioneffect = (PotionEffect)iterator1.next();
                    String s1 = I18n.format(potioneffect.getEffectName()).trim();
                    Potion potion = Potion.potionTypes[potioneffect.getPotionID()];
                    Map map = potion.getAttributeModifierMap();

                    if (map != null && map.size() > 0)
                    {
                        Iterator iterator = map.entrySet().iterator();

                        while (iterator.hasNext())
                        {
                            Entry entry = (Entry)iterator.next();
                            AttributeModifier attributemodifier = (AttributeModifier)entry.getValue();
                            AttributeModifier attributemodifier1 = new AttributeModifier(attributemodifier.getName(), potion.getAttributeModifierAmount(potioneffect.getAmplifier(), attributemodifier), attributemodifier.getOperation());
                            hashmultimap.put(((IAttribute)entry.getKey()).getAttributeUnlocalizedName(), attributemodifier1);
                        }
                    }

                    if (potioneffect.getAmplifier() > 0)
                    {
                        s1 = s1 + " " + I18n.format("potion.potency." + potioneffect.getAmplifier()).trim();
                    }

                    if (potioneffect.getDuration() > 20)
                    {
                        s1 = s1 + " (" + Potion.getDurationString(potioneffect) + ")";
                    }

                    if (potion.isBadEffect())
                    {
                        p_77624_3_.add(TextFormatting.RED + s1);
                    }
                    else
                    {
                        p_77624_3_.add(TextFormatting.GRAY + s1);
                    }
                }
            }
            else
            {
                String s = I18n.format("potion.empty").trim();
                p_77624_3_.add(TextFormatting.GRAY + s);
            }

            if (!hashmultimap.isEmpty())
            {
                p_77624_3_.add("");
                p_77624_3_.add(TextFormatting.DARK_PURPLE + I18n.format("potion.effects.whenDrank"));
                iterator1 = hashmultimap.entries().iterator();

                while (iterator1.hasNext())
                {
                    Entry entry1 = (Entry)iterator1.next();
                    AttributeModifier attributemodifier2 = (AttributeModifier)entry1.getValue();
                    double d0 = attributemodifier2.getAmount();
                    double d1;

                    if (attributemodifier2.getOperation() != 1 && attributemodifier2.getOperation() != 2)
                    {
                        d1 = attributemodifier2.getAmount();
                    }
                    else
                    {
                        d1 = attributemodifier2.getAmount() * 100.0D;
                    }

                    if (d0 > 0.0D)
                    {
                        p_77624_3_.add(TextFormatting.BLUE + I18n.format("attribute.modifier.plus." + attributemodifier2.getOperation(), new Object[] {ItemStack.DECIMALFORMAT.format(d1), TextComponentTranslation.translateToLocal("attribute.name." + (String)entry1.getKey())}));
                    }
                    else if (d0 < 0.0D)
                    {
                        d1 *= -1.0D;
                        p_77624_3_.add(TextFormatting.RED + I18n.format("attribute.modifier.take." + attributemodifier2.getOperation(), new Object[] {ItemStack.DECIMALFORMAT.format(d1), TextComponentTranslation.translateToLocal("attribute.name." + (String)entry1.getKey())}));
                    }
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack p_77636_1_)
    {
        List list = this.getEffects(p_77636_1_);
        return list != null && !list.isEmpty();
    }

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_)
    {
        super.getSubItems(p_150895_1_, p_150895_2_, p_150895_3_);
        int j;

        if (SUB_ITEMS_CACHE.isEmpty())
        {
            for (int i = 0; i <= 15; ++i)
            {
                for (j = 0; j <= 1; ++j)
                {
                    int k;

                    if (j == 0)
                    {
                        k = i | 8192;
                    }
                    else
                    {
                        k = i | 16384;
                    }

                    for (int l = 0; l <= 2; ++l)
                    {
                        int i1 = k;

                        if (l != 0)
                        {
                            if (l == 1)
                            {
                                i1 = k | 32;
                            }
                            else if (l == 2)
                            {
                                i1 = k | 64;
                            }
                        }

                        List list1 = PotionHelper.getPotionEffects(i1, false);

                        if (list1 != null && !list1.isEmpty())
                        {
                        	SUB_ITEMS_CACHE.put(list1, Integer.valueOf(i1));
                        }
                    }
                }
            }
        }

        Iterator iterator = SUB_ITEMS_CACHE.values().iterator();

        while (iterator.hasNext())
        {
            j = ((Integer)iterator.next()).intValue();
            p_150895_3_.add(new ItemStack(p_150895_1_, 1, j));
        }
    }

}