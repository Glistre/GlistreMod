package com.glistre.glistremod.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import com.glistre.glistremod.GlistreMod;
import com.glistre.glistremod.dimension.TeleporterFreon;
import com.glistre.glistremod.dimension.TeleporterGlistre;
import com.glistre.glistremod.events.EntityPortalFreonFX;
import com.glistre.glistremod.init.BiomeRegistry;
import com.glistre.glistremod.init.BlockRegistry;
import com.glistre.glistremod.lib.Defaults;
import com.glistre.glistremod.tabs.TabRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
//import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticlePortal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockGlistrePortal extends BlockPortal
{
	public String blockName;


    public static final PropertyEnum<EnumFacing.Axis> AXIS = PropertyEnum.<EnumFacing.Axis>create("axis", EnumFacing.Axis.class, new EnumFacing.Axis[] {EnumFacing.Axis.X, EnumFacing.Axis.Z});
    protected static final AxisAlignedBB X_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.375D, 1.0D, 1.0D, 0.625D);
    protected static final AxisAlignedBB Z_AABB = new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 1.0D, 1.0D);
    protected static final AxisAlignedBB Y_AABB = new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D);

    public BlockGlistrePortal()
    {
        
        this.setDefaultState(this.blockState.getBaseState().withProperty(AXIS, EnumFacing.Axis.X));
        this.setTickRandomly(true);
		this.setUnlocalizedName(blockName);
		this.setCreativeTab(TabRegistry.glistre_tab_1);
    }

   @Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        super.updateTick(worldIn, pos, state, rand);

        if (worldIn.provider.isSurfaceWorld() && worldIn.getGameRules().getBoolean("doMobSpawning") && rand.nextInt(2000) < worldIn.getDifficulty().getDifficultyId())
        {
            int i = pos.getY();
            BlockPos blockpos;

            for (blockpos = pos; !worldIn.getBlockState(blockpos).isFullyOpaque() && blockpos.getY() > 0; blockpos = blockpos.down())
            {
                ;
            }

            if (i > 0 && !worldIn.getBlockState(blockpos.up()).isNormalCube())
            {
                Entity entity = ItemMonsterPlacer.spawnCreature(worldIn, EntityList.getEntityStringFromClass(EntityPigZombie.class), (double)blockpos.getX() + 0.5D, (double)blockpos.getY() + 1.1D, (double)blockpos.getZ() + 0.5D);

                if (entity != null)
                {
                    entity.timeUntilPortal = entity.getPortalCooldown();
                }
            }
        }
    }

	@Nullable
    public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state)
    {
		return NULL_AABB;
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
    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
// HERE IS THE LIGHT PORTAL METHOD: func_150000_e replaces tryToCreatePortal became getPortalSize in 1.7.10 now called trySpawnPortal

    //1.10.2 changed trySpawnPortal to #trySpawnPortal
    public boolean trySpawnPortal(World worldIn, BlockPos blockPos)
    {
        BlockGlistrePortal.Size size = new BlockGlistrePortal.Size(worldIn, blockPos, EnumFacing.Axis.X);
        //this is checking left and right if blockglistreportal is there then it will not make the portal (field is a block position)
        //func is basically the get block method
        if (size.isValid() && size.portalBlockCount == 0)
        {
            size.placePortalBlocks(); //this is going to check blockpos with a double for loop
           //*for (int i = -1; i <=2; i++) checks the x --horizontal is it 4 wide -1 to 2 == four blocks wide  //for int j= -1;, j<=3, j++ ) is it five high z =axis
            return true;
        }
        else 
        {
            BlockGlistrePortal.Size size1 = new BlockGlistrePortal.Size(worldIn, blockPos, EnumFacing.Axis.Z);
            //this is checking front and back if blockglistreportal is there then it will not make the portal (field is a block position)

        	if (size1.isValid() && size1.portalBlockCount == 0)
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
    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block neighborBlock)
    {
        EnumFacing.Axis axis = (EnumFacing.Axis)state.getValue(AXIS);
        BlockGlistrePortal.Size size;

        if (axis == EnumFacing.Axis.X)
        {
            size = new BlockGlistrePortal.Size(worldIn, pos, EnumFacing.Axis.X);

            if (!size.isValid() || size.portalBlockCount < size.width * size.height)
            {
                worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
            }
        }
        else if (axis == EnumFacing.Axis.Z)
        {
            size = new BlockGlistrePortal.Size(worldIn, pos, EnumFacing.Axis.Z);

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
    
    //this is the method that tells which dimension to put the player into
    @Override 
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity player) { 
        if (!player.isRiding() && !player.isBeingRidden() && player.timeUntilPortal == 0) { 
            if (player instanceof EntityPlayerMP) { // && !this.worldObj.isRemote) { 
                //200 tick duration is ten seconds. There are 20 ticks a second 
   //             PotionEffect effect = new PotionEffect(Potion.confusion.id, 200); 
   //            player.addPotionEffect(effect); 
 
                EntityPlayerMP mpPlayer = (EntityPlayerMP) player; 
                mpPlayer.timeUntilPortal = 100; //how long the player has to stand in the portal counts down from num
                int targetDimension = 0; // 0 = Overworld dimension ID //setting to zero so it's not null then changing?
                if (mpPlayer.dimension != Defaults.DIM_ID.GLISTRE) { //if player is not in the dimension then put the player in it next line
                    targetDimension = Defaults.DIM_ID.GLISTRE; 
                }
                
                //#getConfigurationManager() renamed in 1.10.2 to #getPlayerList
                Teleporter tele = new TeleporterGlistre(mpPlayer.mcServer.worldServerForDimension(targetDimension)); 
                mpPlayer.mcServer.getPlayerList().transferPlayerToDimension(mpPlayer, targetDimension, tele); 
            } 
        } 
 }
//temporarily removed 12-13-2016 TODO: add back for server testing once portal is fixed
 /* @SubscribeEvent //added this event because some modders had crash in survival mode running server
	public void onLivingUpdateEvent(LivingUpdateEvent event)
	{
	Entity entityIn = event.entity;
	IBlockState state = entityIn.worldObj.getBlockState(entityIn.getPosition());
	if(state.getBlock()==BlockRegistry.light_portal)
	{

    if (entityIn.ridingEntity == null && entityIn.riddenByEntity == null && entityIn instanceof EntityPlayerMP) {
		EntityPlayerMP player = (EntityPlayerMP) entityIn;
		FMLCommonHandler.instance().getMinecraftServerInstance();
		MinecraftServer server = MinecraftServer.getServer();

		if (player.timeUntilPortal > 0) {
			player.timeUntilPortal = 10;
		}
		else if (entityIn.dimension != Defaults.DIM_ID.GLISTRE) {
			player.timeUntilPortal = 10;
			player.mcServer.getConfigurationManager().transferPlayerToDimension(player, Defaults.DIM_ID.GLISTRE, new TeleporterGlistre(server.worldServerForDimension(Defaults.DIM_ID.GLISTRE)));
			entityIn.getPortalCooldown();
			}
		else {
			player.timeUntilPortal = 10;
			player.setLocationAndAngles((3+8*(int)(Defaults.DIM_ID.GLISTRE/2))/player.worldObj.provider.getMovementFactor(), 71, (3+(9*(Defaults.DIM_ID.GLISTRE%2)))/player.worldObj.provider.getMovementFactor(), entityIn.rotationYaw, entityIn.rotationPitch);
			player.mcServer.getConfigurationManager().transferPlayerToDimension(player, 0, new TeleporterGlistre(server.worldServerForDimension(0)));
		}

    }
  }
}*/
/*   you will end up in Nether
 * 
 *  public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
        if (entityIn.ridingEntity == null && entityIn.riddenByEntity == null)
        {
            entityIn.setPortal(pos);
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
 //           worldIn.playSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, "portal.portal", 0.5F, rand.nextFloat() * 0.4F + 0.8F, false);
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
			if(worldIn.isRemote){     // or !worldObj.isRemote
				//1.10.2 changed Particle to ParticlePortal
	    	ParticlePortal particle = new EntityPortalFreonFX(worldIn, d0, d1, d2, d3, d4, d5); 
			particle.setRBGColorF(1.000f, 0.843f, 0.000f); // to change colors and position  random.nextFloat(); dark blue = 0.6f, 0.2f, 0.8f gold=1.000f, 0.843f, 0.000f
//			Minecraft.getMinecraft().effectRenderer.addEffect(particle); the next line works too because this line is in the proxy
			GlistreMod.proxy.addParticleEffect(particle);  // when that method is called on the server it does nothing, and on the client it instantiates the particles.  Minecraft class is also client side only


			}
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
     //       private static final String __OBFID = "CL_00000285";

            public Size(World worldIn, BlockPos blockpos, EnumFacing.Axis axis)
            {
                this.world = worldIn;
                this.axis = axis;

                if (axis == EnumFacing.Axis.X)
                {
                    this.leftDir = EnumFacing.EAST;
                    this.rightDir = EnumFacing.WEST;
                }
                else
                {
                    this.leftDir = EnumFacing.NORTH;
                    this.rightDir = EnumFacing.SOUTH;
                }
//func_150857_a to #isEmptyBlock
                for (BlockPos blockpos1 = blockpos; blockpos.getY() > blockpos1.getY() - 21 && blockpos.getY() > 0 && this.isEmptyBlock(worldIn.getBlockState(blockpos.down()).getBlock()); blockpos = blockpos.down())
                {
                    ;
                }

                int i = this.getDistanceUntilEdge(blockpos, this.leftDir) - 1;

                if (i >= 0)
                {
                    this.bottomLeft = blockpos.offset(this.leftDir, i);
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
//#func_180120_a became #getDistanceUntilEdge 1.10.2
            protected int getDistanceUntilEdge(BlockPos blockpos, EnumFacing enumfacing)
            {
                int i;

                for (i = 0; i < 22; ++i)
                {
                    BlockPos blockpos1 = blockpos.offset(enumfacing, i);

                    if (!this.isEmptyBlock(this.world.getBlockState(blockpos1).getBlock()) || this.world.getBlockState(blockpos1.down()).getBlock() != BlockRegistry.silver_ore_1)
                    {
                        break;
                    }
                }

                Block block = this.world.getBlockState(blockpos.offset(enumfacing, i)).getBlock();
                return block == BlockRegistry.silver_ore_1 ? i : 0;
            }
            //#func_150858_a became #calculatePortalHeight()
            //@Override  cannot override not a static method so wtf?
            
            //@Override
            protected int calculatePortalHeight()
            {
                //int i;
                //label56 became label24
                label24:

                for (this.height = 0; this.height < 21; ++this.height)
                {
                    for (int i = 0; i < this.width; ++i)
                    {
                        BlockPos blockpos = this.bottomLeft.offset(this.rightDir, i).up(this.height);
                        Block block = this.world.getBlockState(blockpos).getBlock();

                        if (!this.isEmptyBlock(block))
                        {
                            break label24;
                        }

                        if (block == BlockRegistry.light_portal)
                        {
                            ++this.portalBlockCount;
                        }

                        if (i == 0)
                        {
                            block = this.world.getBlockState(blockpos.offset(this.leftDir)).getBlock();

                            if (block != BlockRegistry.silver_ore_1)
                            {
                                break label24;
                            }
                        }
                        else if (i == this.width - 1)
                        {
                            block = this.world.getBlockState(blockpos.offset(this.rightDir)).getBlock();

                            if (block != BlockRegistry.silver_ore_1)
                            {
                                break label24;
                            }
                        }
                    }
                }

                for (int j = 0; j < this.width; ++j)
                {
                    if (this.world.getBlockState(this.bottomLeft.offset(this.rightDir, j).up(this.height)).getBlock() != BlockRegistry.silver_ore_1)
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
            
            //changed func_150857 to isEmptyBlock
            protected boolean isEmptyBlock(Block block)
            {
            	//1.10.2 changed #getMaterial to #getDefaultState
            	
    			return block.getDefaultState() == Material.AIR || block == BlockRegistry.light_fire || block == BlockRegistry.light_portal;
            }
            //func_150860_b() to isValid()
            public boolean isValid()
            {
            	//bottomLeft width height
                return this.bottomLeft != null && this.width >= 2 && this.width <= 21 && this.height >= 3 && this.height <= 21;
            }
//func_150859_c() to #placePortalBlocks
            public void placePortalBlocks()
            {
                for (int i = 0; i < this.width; ++i)
                {
                    BlockPos blockpos = this.bottomLeft.offset(this.rightDir, i);

                    for (int j = 0; j < this.height; ++j)
                    {
                        this.world.setBlockState(blockpos.up(j), BlockRegistry.light_portal.getDefaultState().withProperty(BlockGlistrePortal.AXIS, this.axis), 2);
                    }
                }
            }
        }
}