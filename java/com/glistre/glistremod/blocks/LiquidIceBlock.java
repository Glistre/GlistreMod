package com.glistre.glistremod.blocks;

import java.util.Random;

import com.glistre.glistremod.init.BiomeRegistry;
import com.glistre.glistremod.init.BlockRegistry;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockIce;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class LiquidIceBlock extends BlockIce {
	public String blockName;

    protected static final AxisAlignedBB LIQUID_ICE_AABB = new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, .99F, 1.0F);

	public LiquidIceBlock() {
		super();
		this.setUnlocalizedName(blockName);		
		this.setTickRandomly(true);
	}
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
    	  return LIQUID_ICE_AABB;    	
    }
/*	@Override
	public int tickRate(){
		return 30;
	}*/
@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
	{
        worldIn.scheduleUpdate(pos, this, 20);      //was #scheduleBlockUpdate
        worldIn.setBlockState(pos, BlockRegistry.liquid_ice.getDefaultState());  
    }

    @Override
  public void updateTick( World worldIn, BlockPos pos, IBlockState state, Random rand) {
 //   super.updateTick(worldIn, pos, state, rand);

    Block blk = Blocks.SNOW_LAYER;
    Block blk1 = Blocks.SNOW;

    pos = new BlockPos(pos.getX(), pos.getY(), pos.getZ());//liquid ice
    
    BlockPos pos0 = new BlockPos(pos.getX(), (pos.getY()+1), pos.getZ());//ice
    BlockPos pos1 = new BlockPos((pos.getX()+1), pos.getY(), pos.getZ());//ice
    BlockPos pos2 = new BlockPos(pos.getX()-1, pos.getY(), pos.getZ());//ice
    BlockPos pos3 = new BlockPos(pos.getX(), pos.getY(), (pos.getZ()+1));//ice
    BlockPos pos4 = new BlockPos(pos.getX(), pos.getY(), (pos.getZ()-1));//ice
    BlockPos pos5 = new BlockPos(pos.getX(), pos.getY(), (pos.getZ()+2));//ice
    BlockPos pos6 = new BlockPos(pos.getX(), pos.getY(), (pos.getZ()-2));//ice
    BlockPos pos7 = new BlockPos((pos.getX()+2), pos.getY(), pos.getZ()); //snow_layer
    BlockPos pos8 = new BlockPos(pos.getX(), (pos.getY()+2), pos.getZ());//snow


    state=BlockRegistry.liquid_ice.getDefaultState();
    IBlockState state0=blk.getDefaultState(); 
    IBlockState state1= Blocks.ICE.getDefaultState();
    IBlockState state2=blk1.getDefaultState();

    worldIn.setBlockState(pos, state);
    worldIn.setBlockState(pos0, state1);
    worldIn.setBlockState(pos1, state1);
    worldIn.setBlockState(pos2, state1);
    worldIn.setBlockState(pos3, state1);
    worldIn.setBlockState(pos4, state1);
    worldIn.setBlockState(pos5, state1);
    worldIn.setBlockState(pos6, state1);
    worldIn.setBlockState(pos7, state0);    
    worldIn.setBlockState(pos8, state2);
    
    
//added 1.10.2 worldIn, pos parameters in #getLightOpacity might cause an NPE?
    if (worldIn.getLightFor(EnumSkyBlock.BLOCK, pos) > 11 - this.getDefaultState().getLightOpacity(worldIn, pos))
    {
        if (worldIn.provider.doesWaterVaporize())
        {
            this.turnIntoWater(worldIn, pos);
        }
      }   
    }
    @Override
    protected void turnIntoWater(World worldIn, BlockPos pos)
    {
        if (worldIn.provider.doesWaterVaporize())
        {
            worldIn.setBlockToAir(pos);
        }
        else
        {
            this.dropBlockAsItem(worldIn, pos, worldIn.getBlockState(pos), 0);
            worldIn.setBlockState(pos, Blocks.WATER.getDefaultState());
            worldIn.notifyBlockOfStateChange(pos, Blocks.WATER);
        }
    }
/*    removed with 1.10.2
 * @Override
	public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state)
	{
		return new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.99, 1.0);
	}*/

// when right click placing Liquid Ice block draws in lightning	
    //1.10.2 changed order of parameters in #randomDisplayTick
	@Override	
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World worldIn, BlockPos pos,  Random rand){
    Biome b = BiomeRegistry.biomeFreon;
    if (b == BiomeRegistry.biomeFreon){
    {
    	//1.10 added boolean not sure what it does
    	worldIn.spawnEntityInWorld(new EntityLightningBolt(worldIn, (double)pos.getX() + 1.0D, (double)pos.getY() + 0.99D, (double)pos.getZ() + 1.0D, true));

    }
  }
}
	//not even sure what this does
/*	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {

		super.setBlockBoundsBasedOnState(worldIn, pos);
	}*/
	
	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn){
		//adds potion effect to mobs/ target
		if (entityIn instanceof EntityLiving) {
			((EntityLiving)entityIn).addPotionEffect(new PotionEffect(Potion.getPotionById(2), 200, 7));
		}
		//adds potion effect to player/target
		else if (entityIn instanceof EntityLivingBase){
			((EntityLivingBase)entityIn).addPotionEffect(new PotionEffect(Potion.getPotionById(1), 200, 3));
		}	
	}
}

/*	@Override
public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int blockX, int blockY, int blockZ) {
		return AxisAlignedBB.getBoundingBox(blockX, blockY, blockZ, blockX + 1, (double) blockY + 0.99 , blockZ + 1);
}*/


//adding scheduleBlockUpdate makes the blocks impossible to break unless you break the block originally placed
//   world.scheduleBlockUpdate(x, y, z, this, 30);
/*    for(int r=1; r<10; r++){
 * 
 * 

//if (worldIn.getBlockState(pos) == blk1){
    if(worldIn.getBlockMaterial(i, j+r, k) == Material.air ){
    world.setBlock(i, j+r, k, Block.glass.blockID);
    }
   }*/
/*    public void onNeighborBlockChange(World world, int x, int y, int z, int l)
{
    world.scheduleBlockUpdate(x, y, z, Blocks.air, 60);
}*/
/*    public void onNeighborBlockChange(World world, int x, int y, int z, int l)
{
    world.scheduleBlockUpdate(x, y, z, Blocks.air, 60);
}*/
