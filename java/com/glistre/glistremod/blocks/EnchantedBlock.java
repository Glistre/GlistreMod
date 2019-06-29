package com.glistre.glistremod.blocks;

import java.util.ArrayList;
import java.util.Random;

import com.glistre.glistremod.GlistreMod;
import com.glistre.glistremod.init.GlistreEntityRegistry;
import com.glistre.glistremod.init.ItemRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class EnchantedBlock extends Block {

    private String texturePath = "glistremod:";  
//    private int thisBlockID;
    public String blockName;
    
    public EnchantedBlock (Material blockMaterial, int harvestLevel) {
        
        super(Material.ANVIL);
//     dsetBlockName(blockName);
        this.setUnlocalizedName(blockName);
//        texturePath += textureName;
//tool class, harvest level 0, 1,2, 3Diamond, 4 custom
        this.setHarvestLevel("pickaxe", 4);
        this.setSoundType(SoundType.GLASS);
 
    }

/*    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return ItemRegistry.Glistre_Sword;

    }*/
    
   public int quantityDropped(Random random)
    {
        return 1;
    }

   /*   @Override
   public int quantityDropped(IBlockState blockstate, int fortune, Random random) {
       if (this.least_quantity >= this.most_quantity)
           return this.least_quantity;
       return this.least_quantity + random.nextInt(this.most_quantity - this.least_quantity + fortune + 1);
   }*/
    
    @Override
    public ArrayList<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune){ // for maximum flexibility (you can drop multiple different things)
    //   public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {

        ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
        
        if (RANDOM.nextFloat() < 0.5F)
        drops.add(new ItemStack(ItemRegistry.glistre_sword, RANDOM.nextInt(3) + 1));
        if (RANDOM.nextFloat() < 0.1F)
        drops.add(new ItemStack(ItemRegistry.mighty_sword, RANDOM.nextInt(2) + 1));
        if (RANDOM.nextFloat() < 0.1F)
        drops.add(new ItemStack(ItemRegistry.silver_sword_1, RANDOM.nextInt(2) + 1));
        if (RANDOM.nextFloat() < 0.1F)
        drops.add(new ItemStack(ItemRegistry.poison_protection, RANDOM.nextInt(2) + 1)); 
        drops.add(new ItemStack(ItemRegistry.glistre_dust, RANDOM.nextInt(2) + 1));
        if (RANDOM.nextFloat() < 0.5F)
            drops.add(new ItemStack(Items.DIAMOND));
        return drops;
    }

 /*   @Override 
    //PotionEffect(int id, int effectDuration, int effectAmplifier, boolean ambient, boolean showParticles)
//duration, level , badeffect or not
//public void onEntityWalking(World world, int x, int y, int z, Entity entity){
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn){
    if (entityIn instanceof EntityLivingBase) { // If the entity is an instance of EntityLivingBase or any class that inherits from it
	    ((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(ItemRegistry.poison_protect_potion.id, 0, 300, 2, false, true); // Cast to EntityLivingBase and call addPotionEffect
	}
//((EntityLivingBase) entity).addPotionEffect(new PotionEffect(ItemRegistry.poison_protect_potion.id, 300, 0, false));

super.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);
}*/




}

