package com.glistre.glistremod.blocks;

import java.util.Random;

import com.glistre.glistremod.dimension.TeleporterFreon;
import com.glistre.glistremod.dimension.TeleporterGlistre;
import com.glistre.glistremod.dimension.TeleporterFreon;
import com.glistre.glistremod.init.BiomeRegistry;
import com.glistre.glistremod.init.BlockRegistry;
import com.glistre.glistremod.lib.Defaults;
import com.glistre.glistremod.tabs.TabRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTobyKingPortal extends BlockPortal
{
	public String blockName;

    public static final PropertyEnum AXIS = PropertyEnum.create("axis", EnumFacing.Axis.class, new EnumFacing.Axis[] {EnumFacing.Axis.X, EnumFacing.Axis.Z});
//    private static final String __OBFID = "CL_00000284";
/*    protected static final AxisAlignedBB X_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.375D, 1.0D, 1.0D, 0.625D);
    protected static final AxisAlignedBB Z_AABB = new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 1.0D, 1.0D);
    protected static final AxisAlignedBB Y_AABB = new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D);*/

 

//Material materialIn, boolean ignoreSimilarityIn
    public BlockTobyKingPortal() {
 //       super();
        this.setDefaultState(this.blockState.getBaseState().withProperty(AXIS, EnumFacing.Axis.X));
        this.setTickRandomly(true);
		this.setUnlocalizedName(blockName);
		this.setCreativeTab(TabRegistry.glistre_tab_1);
	}

	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        super.updateTick(worldIn, pos, state, rand);

 /*       if (worldIn.provider.isSurfaceWorld() && worldIn.getGameRules().getBoolean("doMobSpawning") && rand.nextInt(2000) < worldIn.getDifficulty().getDifficultyId())
        {
            int i = pos.getY();
            BlockPos blockpos1;

            for (blockpos1 = pos; !World.doesBlockHaveSolidTopSurface(worldIn, blockpos1) && blockpos1.getY() > 0; blockpos1 = blockpos1.down())
            {
                ;
            }

            if (i > 0 && !worldIn.getBlockState(blockpos1.up()).getBlock().isNormalCube())
            {
                Entity entity = ItemMonsterPlacer.spawnCreature(worldIn, 57, (double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 1.1D, (double)blockpos1.getZ() + 0.5D);

                if (entity != null)
                {
                    entity.timeUntilPortal = entity.getPortalCooldown();
                }
            }
        }*/
    }

    public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state)
    {
        return null;
    }

/*    public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos)
    {
        EnumFacing.Axis axis = (EnumFacing.Axis)worldIn.getBlockState(pos).getValue(AXIS);
        float f = 0.125F;
        float f1 = 0.125F;

        if (axis == EnumFacing.Axis.X)
        {
            f = 0.5F;
        }

        if (axis == EnumFacing.Axis.Z)
        {
            f1 = 0.5F;
        }

        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f1, 0.5F + f, 1.0F, 0.5F + f1);
    }*/

    public static int getMetaForAxis(EnumFacing.Axis axis)
    {
        return axis == EnumFacing.Axis.X ? 1 : (axis == EnumFacing.Axis.Z ? 2 : 0);
    }

    public boolean isFullCube()
    {
        return false;
    }
 // HERE IS THE LIGHT PORTAL METHOD: func_150000_e replaces tryToCreatePortal became getPortalSize in 1.7.10 now called trySpawnPortal
    public boolean trySpawnPortal(World worldIn, BlockPos blockPos)
    {
        BlockTobyKingPortal.Size size = new BlockTobyKingPortal.Size(worldIn, blockPos, EnumFacing.Axis.X);

        if (size.isValid() && size.portalBlockCount == 0)
        {
            size.placePortalBlocks();
            return true;
        }
        else 
        {
        	
            BlockTobyKingPortal.Size size1 = new BlockTobyKingPortal.Size(worldIn, blockPos, EnumFacing.Axis.Z);

        	if
        	(size1.isValid() && size1.portalBlockCount == 0)
            {
                size1.placePortalBlocks();
                return true;
            }
            else
            {
                return false;
            }
        }
    }

    /**
     * Called when a neighboring block changes.
     */
    public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
    {
        EnumFacing.Axis axis = (EnumFacing.Axis)state.getValue(AXIS);
        BlockTobyKingPortal.Size size;

        if (axis == EnumFacing.Axis.X)
        {
            size = new BlockTobyKingPortal.Size(worldIn, pos, EnumFacing.Axis.X);

            if (!size.isValid() || size.portalBlockCount < size.width * size.height)
            {
                worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
            }
        }
        else if (axis == EnumFacing.Axis.Z)
        {
            size = new BlockTobyKingPortal.Size(worldIn, pos, EnumFacing.Axis.Z);

            if (!size.isValid() || size.portalBlockCount < size.width * size.height)
            {
                worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side)
    {
        EnumFacing.Axis axis = null;
        IBlockState iblockstate = worldIn.getBlockState(pos);

        if (worldIn.getBlockState(pos).getBlock() == this)
        {
            axis = (EnumFacing.Axis)iblockstate.getValue(AXIS);

            if (axis == null)
            {
                return false;
            }

            if (axis == EnumFacing.Axis.Z && side != EnumFacing.EAST && side != EnumFacing.WEST)
            {
                return false;
            }

            if (axis == EnumFacing.Axis.X && side != EnumFacing.SOUTH && side != EnumFacing.NORTH)
            {
                return false;
            }
        }

        boolean flag = worldIn.getBlockState(pos.west()).getBlock() == this && worldIn.getBlockState(pos.west(2)).getBlock() != this;
        boolean flag1 = worldIn.getBlockState(pos.east()).getBlock() == this && worldIn.getBlockState(pos.east(2)).getBlock() != this;
        boolean flag2 = worldIn.getBlockState(pos.north()).getBlock() == this && worldIn.getBlockState(pos.north(2)).getBlock() != this;
        boolean flag3 = worldIn.getBlockState(pos.south()).getBlock() == this && worldIn.getBlockState(pos.south(2)).getBlock() != this;
        boolean flag4 = flag || flag1 || axis == EnumFacing.Axis.X;
        boolean flag5 = flag2 || flag3 || axis == EnumFacing.Axis.Z;
        return flag4 && side == EnumFacing.WEST ? true : (flag4 && side == EnumFacing.EAST ? true : (flag5 && side == EnumFacing.NORTH ? true : flag5 && side == EnumFacing.SOUTH));
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random random)
    {
        return 0;
    }

    /**
     * Called When an Entity Collided with the Block
     */
/*    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
        if (entityIn.ridingEntity == null && entityIn.riddenByEntity == null)
        {
            entityIn.setInPortal();
        }
    }*/
    /**
     * Deliberately left empty because we don't want anything special happening when something collides 
     * though I might use this instead of onCollideWithPlayer to allow other entities to join in the fun >:D 
     */ 
//    @Override 
//    public void applyEntityCollision(Entity entity) {} 
 
    //this next section works as well as following but the lag is not a teleporter issue its a chunk loading issue
 @Override 
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity player) { 
        
	 if (!(player.isRiding()) && !(player.isBeingRidden()) && player.timeUntilPortal == 0) { 
            if (player instanceof EntityPlayerMP) { // && !this.worldObj.isRemote) { 
                //200 tick duration is ten seconds. There are 20 ticks a second 
   //             PotionEffect effect = new PotionEffect(Potion.confusion.id, 200); 
   //            player.addPotionEffect(effect); 
 
                EntityPlayerMP mpPlayer = (EntityPlayerMP) player; 
                mpPlayer.timeUntilPortal = 100; 
                int targetDimension = -1; // 0 = Overworld dimension ID so if the next code is not executed the portal takes you to Overworld or -1 for Nether
                if (mpPlayer.dimension != Defaults.DIM_ID.FREON) { 
                    targetDimension = Defaults.DIM_ID.FREON; 
                } 
                Teleporter tele = new TeleporterFreon(mpPlayer.mcServer.worldServerForDimension(targetDimension)); 
                mpPlayer.mcServer.getPlayerList().transferPlayerToDimension(mpPlayer, targetDimension, tele); 
            } 
        } 
 } 
/*    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
    if (entityIn.ridingEntity == null && entityIn.riddenByEntity == null && entityIn instanceof EntityPlayerMP) {
		EntityPlayerMP player = (EntityPlayerMP) entityIn;
		FMLCommonHandler.instance().getMinecraftServerInstance();
		MinecraftServer server = MinecraftServer.getServer();

		if (player.timeUntilPortal > 0) { //if the player has to stand in the portal a bit
			player.timeUntilPortal = 10;  // then the player needs to be there 10 tics?
		}
		else if (player.dimension != Defaults.DIM_ID.FREON) {  //if the player is not in the dimension 
			player.timeUntilPortal = 10; //the player needs to be there 10 tics to get transferred
			player.mcServer.getConfigurationManager().transferPlayerToDimension(player, Defaults.DIM_ID.FREON, new TeleporterFreon(server.worldServerForDimension(Defaults.DIM_ID.FREON)));
			entityIn.getPortalCooldown();
			}
		else {
			player.timeUntilPortal = 10;
			player.mcServer.getConfigurationManager().transferPlayerToDimension(player, 0, new TeleporterFreon(server.worldServerForDimension(0)));
		}
	}
}*/

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(AXIS, (meta & 3) == 2 ? EnumFacing.Axis.Z : EnumFacing.Axis.X);
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (rand.nextInt(100) == 0)
        {
            worldIn.playSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5F, rand.nextFloat() * 0.4F + 0.8F, false);
        }

        for (int i = 0; i < 4; ++i)
        {
            double d0 = (double)((float)pos.getX() + rand.nextFloat());
            double d1 = (double)((float)pos.getY() + rand.nextFloat());
            double d2 = (double)((float)pos.getZ() + rand.nextFloat());
            double d3 = ((double)rand.nextFloat() - 0.5D) * 0.5D;
            double d4 = ((double)rand.nextFloat() - 0.5D) * 0.5D;
            double d5 = ((double)rand.nextFloat() - 0.5D) * 0.5D;
            int j = rand.nextInt(2) * 2 - 1;

            if (worldIn.getBlockState(pos.west()).getBlock() != this && worldIn.getBlockState(pos.east()).getBlock() != this)
            {
                d0 = (double)pos.getX() + 0.5D + 0.25D * (double)j;
                d3 = (double)(rand.nextFloat() * 2.0F * (float)j);
            }
            else
            {
                d2 = (double)pos.getZ() + 0.5D + 0.25D * (double)j;
                d5 = (double)(rand.nextFloat() * 2.0F * (float)j);
            }

            worldIn.spawnParticle(EnumParticleTypes.CRIT_MAGIC, d0, d1, d2, d3, d4, d5 );
        }
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        return getMetaForAxis((EnumFacing.Axis)state.getValue(AXIS));
    }

    @SideOnly(Side.CLIENT)
    public Item getItem(World worldIn, BlockPos pos)
    {
        return null;
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {AXIS});
    }

    public static class Size
        {
            private final World world;
            private final EnumFacing.Axis axis;
            private final EnumFacing rightDir;
            private final EnumFacing leftDir;
            private int portalBlockCount = 0;
            private BlockPos bottomLeft;
            private int height;
            private int width;
          //  private static final String __OBFID = "CL_00000285";

            public Size(World worldIn, BlockPos p_i45694_2_, EnumFacing.Axis p_i45694_3_)
            {
                this.world = worldIn;
                this.axis = p_i45694_3_;

                if (p_i45694_3_ == EnumFacing.Axis.X)
                {
                    this.leftDir = EnumFacing.EAST;
                    this.rightDir = EnumFacing.WEST;
                }
                else
                {
                    this.leftDir = EnumFacing.NORTH;
                    this.rightDir = EnumFacing.SOUTH;
                }

                for (BlockPos blockpos1 = p_i45694_2_; p_i45694_2_.getY() > blockpos1.getY() - 21 && p_i45694_2_.getY() > 0 && this.isEmptyBlock(worldIn.getBlockState(p_i45694_2_.down()).getBlock()); p_i45694_2_ = p_i45694_2_.down())
                {
                    ;
                }

                int i = this.getDistanceUntilEdge(p_i45694_2_, this.leftDir) - 1;

                if (i >= 0)
                {
                    this.bottomLeft = p_i45694_2_.offset(this.leftDir, i);
                    this.width = this.getDistanceUntilEdge(this.bottomLeft, this.rightDir);

                    if (this.width < 2 || this.width > 21)
                    {
                        this.bottomLeft = null;
                        this.width = 0;
                    }
                }

                if (this.bottomLeft != null)
                {
                    this.height = this.calculatePortalHeight();
                }
            }
           // @Override
            protected int getDistanceUntilEdge(BlockPos blockpos, EnumFacing p_180120_2_)
            {
                int i;

                for (i = 0; i < 22; ++i)
                {
                    BlockPos blockpos1 = blockpos.offset(p_180120_2_, i);

                    if (!this.isEmptyBlock(this.world.getBlockState(blockpos1).getBlock()) || this.world.getBlockState(blockpos1.down()).getBlock() != BlockRegistry.silver_block_1)
                    {
                        break;
                    }
                }

                Block block = this.world.getBlockState(blockpos.offset(p_180120_2_, i)).getBlock();
                return block == BlockRegistry.silver_block_1 ? i : 0;
            }
          //  @Override
            protected int calculatePortalHeight()
            {
                int i;
                label24:

                for (this.height = 0; this.height < 21; ++this.height)
                {
                    for (i = 0; i < this.width; ++i)
                    {
                        BlockPos blockpos = this.bottomLeft.offset(this.rightDir, i).up(this.height);
                        Block block = this.world.getBlockState(blockpos).getBlock();

                        if (!this.isEmptyBlock(block))
                        {
                            break label24;
                        }

                        if (block == BlockRegistry.light_toby_king_portal)
                        {
                            ++this.portalBlockCount;
                        }

                        if (i == 0)
                        {
                            block = this.world.getBlockState(blockpos.offset(this.leftDir)).getBlock();

                            if (block != BlockRegistry.silver_block_1)
                            {
                                break label24;
                            }
                        }
                        else if (i == this.width - 1)
                        {
                            block = this.world.getBlockState(blockpos.offset(this.rightDir)).getBlock();

                            if (block != BlockRegistry.silver_block_1)
                            {
                                break label24;
                            }
                        }
                    }
                }

                for (i = 0; i < this.width; ++i)
                {
                    if (this.world.getBlockState(this.bottomLeft.offset(this.rightDir, i).up(this.height)).getBlock() != BlockRegistry.silver_block_1)
                    {
                        this.height = 0;
                        break;
                    }
                }

                if (this.height <= 21 && this.height >= 3)
                {
                    return this.height;
                }
                else
                {
                    this.bottomLeft = null;
                    this.width = 0;
                    this.height = 0;
                    return 0;
                }
            }
           // @Override
            protected boolean isEmptyBlock(Block block)
            {
    			return block.getDefaultState() == Material.AIR|| block == BlockRegistry.light_toby_king_fire || block == BlockRegistry.light_toby_king_portal;
            }

            public boolean isValid()
            {
                return this.bottomLeft != null && this.width >= 2 && this.width <= 21 && this.height >= 3 && this.height <= 21;
            }

            public void placePortalBlocks()
            {
                for (int i = 0; i < this.width; ++i)
                {
                    BlockPos blockpos = this.bottomLeft.offset(this.rightDir, i);

                    for (int j = 0; j < this.height; ++j)
                    {
                        this.world.setBlockState(blockpos.up(j), BlockRegistry.light_toby_king_portal.getDefaultState().withProperty(BlockTobyKingPortal.AXIS, this.axis), 2);
                    }
                }
            }
        }
}