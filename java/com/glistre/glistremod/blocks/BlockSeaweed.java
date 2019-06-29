package com.glistre.glistremod.blocks;


import java.util.Random;

import com.glistre.glistremod.GlistreMod;
import com.glistre.glistremod.init.BlockRegistry;
import com.glistre.glistremod.init.ItemRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//see BlockReed
public class BlockSeaweed extends Block implements IPlantable {
    private String texturePath = "glistremod:";
    public String blockName;
    private int thisBlockID;
 
	//public static final PropertyEnum VARIANT = PropertyEnum.create("variant", BlockSeaweed.EnumType.class);

    public static final PropertyInteger LEVEL = PropertyInteger.create("level", 0, 15);
    protected static final AxisAlignedBB REED_AABB = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 1.0D, 0.875D);

	public BlockSeaweed() {
        super(Material.WATER);
  //      this.setBlockName(blockName)
        this.setUnlocalizedName(blockName);
 //       this.setHarvestLevel("sword", 1);
        
        float f = 0.375F;
 //       this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 1.0F, 0.5F + f);
        this.setTickRandomly(true);
        this.setSoundType(SoundType.SLIME);
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return REED_AABB;
    }
    
	@Override
	public boolean isVisuallyOpaque() {
		return false;
	}
	
/*	@Override
    public boolean isSolid() {
        return false;
    }*/
	
    public void updateTick(World worldIn, BlockPos blockPos, IBlockState state, Random random) {//copied BlockSeaweed

    	int yCheck = blockPos.getY() + 1;
    	BlockPos pos1 = new BlockPos(blockPos.getX(), yCheck, blockPos.getZ());
    	if (worldIn.getBlockState(pos1).getBlock() == Blocks.WATER) {

//    	if (worldIn.getBlockState(blockPos.up()).getBlock() == Blocks.water) {
            int l;
            for (l = 1; worldIn.getBlockState(blockPos.down(l)).getBlock() == this; ++l);
 
            /*   boolean isAnyWater = state.getBlock() == Blocks.water;
        	boolean isDiffWater = isAnyWater && state.getValue(BlockStaticLiquid.getStaticBlock(materialIn)) == BlockDirt.DirtType.DIRT;*/
           if (l < 3) {
            	
 
   //         	int i1 =  state.getBlock().getMetaFromState(state) == state.getValue(property)
  
        	   //      	   IBlockState var4 = worldIn.getBlockState(p_180671_2_.offsetDown());
 // var4.getBlock().getMaterial() == Material.water && ((Integer)var4.getValue(BlockLiquid.LEVEL)).intValue() == 0;
              int i1 = ((Integer)state.getValue(LEVEL)).intValue();
              // 1.7.10 code        	int i1 = worldIn.getBlockMetadata(x, y, z);        	   
 

 //           if (state.getBlock() == Blocks.water && state.getValue(BlockLiquid.LEVEL) == 0 || state.getBlock() == Blocks.flowing_water && state.getValue(BlockLiquid.LEVEL) == 0)
                if (i1 == 15) {  //this is the "age" of the weed/fully grown is metadata 15

  
  //  blockPos.up() is equivalent to this -->worldIn.setBlockState(blockPos.getX(), blockPos.getY() + 1, blockPos.getZ()).getBlock(), this);

                	worldIn.setBlockState(blockPos.up(), this.getDefaultState());
                    worldIn.setBlockState(blockPos, state.withProperty(LEVEL, Integer.valueOf(0)), 4);

 // 1.7.10 code 	worldIn.setBlockMetadataWithNotify(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 0, 4);
                } else {
                    worldIn.setBlockState(blockPos, state.withProperty(LEVEL, Integer.valueOf(i1 + 1)), 4);
             	
 // 1.7.10 code     worldIn.setBlockMetadataWithNotify(blockPos.getX(), blockPos.getY(), blockPos.getZ(), i1 + 1, 4);
                }
            }
        }
    }
/*@Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos blockPos) {
    	Block block = worldIn.getBlockState(blockPos.down()).getBlock();
    	if(block != Blocks.water && block != BlockRegistry.block_seaweed) return false;
        BlockPos pos0 = new BlockPos((blockPos.getX()-1), blockPos.getY(), blockPos.getZ());
        BlockPos pos1 = new BlockPos((blockPos.getX()+1), blockPos.getY(), blockPos.getZ());
        BlockPos pos2 = new BlockPos(blockPos.getX(), blockPos.getY(), (blockPos.getZ()+1));
        BlockPos pos3 = new BlockPos(blockPos.getX(), blockPos.getY(), (blockPos.getZ()-1));
               
    	boolean isWater = ((worldIn.getBlockState(pos0)).getBlock() == Blocks.water || worldIn.getBlockState(pos1) == Blocks.water || worldIn.getBlockState(pos2) == Blocks.water || worldIn.getBlockState(pos3) == Blocks.water);
        Block block1 = worldIn.getBlockState(blockPos.down()).getBlock();
        return (((block1 == Blocks.sand || block1 == Blocks.dirt || block1 == Blocks.gravel || block1 instanceof SilverOreBlock) && isWater) || block1 == Blocks.gravel ||(block1 instanceof BlockSeaweed && isWater));
    }*/
    /**
     * Called when a neighboring block was changed and marks that this state should perform any checks during a neighbor
     * change. Cases may include when redstone power is updated, cactus blocks popping off due to a neighboring solid
     * block, etc.
     */
    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block neighborBlock) {
//    public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
 
//    	super.neighborChanged(state, worldIn, pos, neighborBlock);
    	this.checkForDrop(worldIn, pos, state);
    }

    
   
 /*   @Override
    protected final boolean checkForDrop(World worldIn, BlockPos blockPos, IBlockState state) {
    	
    	
      
        if (worldIn.isAirBlock(blockPos.down()) || worldIn.getBlockState(blockPos.down()).getBlock() == Blocks.water || worldIn.isAirBlock(blockPos.down())) {
        	return true;
        }
        else{
        	this.dropBlockAsItem(worldIn, blockPos, (IBlockState) state.getValue(LEVEL), 0);
            // 1.7.10		worldIn.getBlockMetadata(x, y, z), 0);
            worldIn.setBlockToAir(blockPos);
            worldIn.setBlockState(blockPos, Blocks.air.getDefaultState(), 3);
            return false;
        
        }
    }*/
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{
		if (worldIn.getBlockState(pos.down()).getBlock() == Blocks.CLAY
			    || worldIn.getBlockState(pos.down()).getBlock() == Blocks.DIRT
			    || worldIn.getBlockState(pos.down()).getBlock() == Blocks.SAND
			    || worldIn.getBlockState(pos.down()).getBlock() == Blocks.GRAVEL
			    || worldIn.getBlockState(pos.down()).getBlock() == BlockRegistry.block_seaweed)
		{
			int yCheck = pos.getY() + 1;
			BlockPos pos1 = new BlockPos(pos.getX(), yCheck, pos.getZ());
			boolean outcome = false;
			for (boolean isChecking = true; isChecking == true; )
			{
				if (worldIn.getBlockState(pos1).getBlock() == BlockRegistry.block_seaweed)
				{
					yCheck++;
					pos1 = new BlockPos(pos.getX(), yCheck, pos.getZ());
					isChecking = true;
				}
				else if (worldIn.getBlockState(pos1).getBlock() == Blocks.WATER)
				{
					outcome = true;
					isChecking = false;
				}
				else
				{
					outcome = false;
					isChecking = false;
				}
			}
			return outcome;
		}
		else
		{
			return false;
		}
	}
    @Override
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos) 
    { 
        return true; 
    }
    
	protected final boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state)
	{
		if (this.canBlockStay(worldIn, pos))
		{
			return true;
		}
		else
		{
			worldIn.setBlockState(pos, Blocks.WATER.getDefaultState());
			this.dropBlockAsItem(worldIn, pos, state, 0);
			return false;
		}
	}
	
	 @Override 
	 @SideOnly(Side.CLIENT) 
	 public BlockRenderLayer getBlockLayer() 
	 { 
	  return BlockRenderLayer.CUTOUT; 
	 }
	 
    public boolean canBlockStay(World worldIn, BlockPos pos) {
  //     return worldIn.isSideSolid(pos.offset(facing), facing.getOpposite(), true); 

        return this.canPlaceBlockAt(worldIn, pos);
    }

   @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, World worldIn, BlockPos blockPos) {
        return NULL_AABB;
    }


    public int idDropped(int par1, Random random, int par3){
    	return thisBlockID;
    }
    public int quantityDropped(Random random)
    {
        return 2;
    }
    
    public boolean isOpaqueCube() {
        return false;
    }
    
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(ItemRegistry.glistre_food_2);
    }

	@Override
	public EnumPlantType getPlantType(IBlockAccess worldIn, BlockPos blockPos) {
		return EnumPlantType.Crop;
	}

	@Override
	public IBlockState getPlant(IBlockAccess worldIn, BlockPos blockPos) {
		return this.getDefaultState();
	}

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(BlockLiquid.LEVEL, Integer.valueOf(meta));
    }

    @SideOnly(Side.CLIENT) 
    public Block.EnumOffsetType getOffsetType() 
    { 
        return Block.EnumOffsetType.XYZ; 
    } 
    
    /*@Override
	public int getPlantMetadata(IBlockAccess world, BlockPos pos) {
		return worldIn.getBlockMetadata(0);
	}*/
    
    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        return ((Integer)state.getValue(BlockLiquid.LEVEL)).intValue();
    }
    
    @Override 
    protected BlockStateContainer createBlockState() { return new BlockStateContainer(this, new IProperty[] {BlockLiquid.LEVEL}); } 

	
}