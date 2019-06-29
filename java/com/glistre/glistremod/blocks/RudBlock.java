package com.glistre.glistremod.blocks;

import javax.annotation.Nullable;

import com.glistre.glistremod.entities.blacktobie.EntityBlackTobo;
import com.glistre.glistremod.entities.king.EntityTobieKing;
import com.glistre.glistremod.entities.queen.EntityTobieQueen;
import com.glistre.glistremod.init.ItemRegistry;
import com.glistre.glistremod.reference.Reference;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class RudBlock extends BlockFluidClassic {
	
	public RudBlock(Fluid fluid, Material material) {
		super(fluid, material);
		setCreativeTab(CreativeTabs.MISC);
	}
	
/*	@Override
	public boolean canCollideCheck(IBlockState state, boolean hitIfLiquid){
		
		//return hitIfLiquid && ((Integer)state.getValue(LEVEL)).intValue() == 1;
		return true;
//		return this.isCollidable();
	}
	
	@Override
      public boolean isCollidable()
      {
      	return true;
      }*/
	
	

    @Override
    public boolean canDisplace(IBlockAccess world, BlockPos pos) {
            if (world.getBlockState(pos).getMaterial().isLiquid()) return false;
            return super.canDisplace(world, pos);
    }
    //.getBlock().getMaterial  changed to just #getMaterial 1.10.2
    @Override
    public boolean displaceIfPossible(World world, BlockPos pos) {
            if (world.getBlockState(pos).getMaterial().isLiquid()) return false;
            return super.displaceIfPossible(world, pos);
    }
    //1.10.2 added EnumHand &ItemStack parameters
   @Override 
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        return true;
    }

/* @Override   
  //duration, level , badeffect or not
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entity){
	
//    ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(ItemRegistry.poisonProtectPotion.id, 300, 0, false));
 //adds damage effect from Rud
        
        if ((!worldIn.isRemote && entity instanceof EntityPlayer ))
    	((EntityPlayer)entity).attackEntityFrom(DamageSource.wither, 100);
    	
    }  */
 
/*   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state)
   {
//       float f = .01F;
 //      return new AxisAlignedBB((double)((float)pos.getX() + f), (double)pos.getY(), (double)((float)pos.getZ() + f), (double)((float)(pos.getX() + 1) - f), (double)((float)(pos.getY() + 1) - f), (double)((float)(pos.getZ() + 1) - f));
       float f = 0.125F;
       return new AxisAlignedBB((double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), (double)(pos.getX() + 1), (double)((float)(pos.getY() + 1) - f), (double)(pos.getZ() + 1));

   }*/
//adds 0.01F to the block bounding box AKA makes box smaller?larger?
 /*  @Override
   @SideOnly(Side.CLIENT)
   public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos)
   {
           float f = 0.125F;
           return new AxisAlignedBB((double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), (double)(pos.getX() + 1), (double)((float)(pos.getY() + 1) - f), (double)(pos.getZ() + 1));
   }*/
   
 /*  @Override 
   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos){
	   this.setBlockBounds(0.0F,  0.0F, 0.0F, 1.0F, 0.1625F, 1.0F);
   }*/
   
/*   @Override//no IBlockState aka stateless matches old onEntityWalking exactly like with slimes
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, Entity entityIn) {
	  
//	   super.onEntityCollidedWithBlock(worldIn, pos, entityIn);
	   //adds damage effect from Rud
       
       if ((!worldIn.isRemote && entityIn instanceof EntityPlayer ))
   	((EntityPlayer)entityIn).attackEntityFrom(DamageSource.wither, 100.0F);
   	
   }	*/  
	
   //use IBlockState needed for 1.8 there are two overloads 
  //duration, level , badeffect?, swirls or not
   @Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
	   
//	   super.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);

//	   	((EntityPlayer)entityIn).attackEntityFrom(DamageSource.generic, 10.0F);

	   if (!worldIn.isRemote &&  entityIn instanceof EntityPlayer ){	

		   ((EntityPlayer)entityIn).attackEntityFrom(DamageSource.drown, .01F);
		   //20 is wither
			((EntityPlayer)entityIn).addPotionEffect(new PotionEffect(Potion.getPotionById(20), 200, 0, false, true));
		   // 9 is nausea/confusion
			((EntityPlayer)entityIn).addPotionEffect(new PotionEffect(Potion.getPotionById(9), 2000, 0, false, true));
			
			
			//adds potion effect to player/target--nausea=confusion
			System.out.print("Called confusion");
			return;
		}	
			else if (entityIn instanceof EntityLiving ) {		
				((EntityLiving)entityIn).addPotionEffect(new PotionEffect(MobEffects.WITHER, 2000, 2, false, true));
				//adds potion effect to mobs/ target --strength=damageboost
//				System.out.println("Called damageboost");
				return;
		}
			else if ((entityIn instanceof EntityBlackTobo || entityIn instanceof EntityTobieKing || entityIn instanceof EntityTobieQueen)){
			//21 == health boost adds hearts above hearts versus 6 instant health/healing
				((EntityBlackTobo) entityIn).addPotionEffect(new PotionEffect(MobEffects.HEALTH_BOOST, 2000, 7, false, true));
				((EntityTobieKing) entityIn).addPotionEffect(new PotionEffect(MobEffects.HEALTH_BOOST, 2000, 7, false, true));
				((EntityTobieQueen) entityIn).addPotionEffect(new PotionEffect(Potion.getPotionById(21), 2000, 7, false, true));
				System.out.println("Called mob healing");
			}
	   return;
	}

 /*   @Override 
    public boolean isOpaqueCube(){

    	return true;
    }
    @Override 
    public boolean isFullCube(){

    	return true;
    }*/
    
}
