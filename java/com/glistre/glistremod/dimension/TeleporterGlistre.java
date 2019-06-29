package com.glistre.glistremod.dimension;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.glistre.glistremod.blocks.BlockGlistrePortal;
import com.glistre.glistremod.init.BlockRegistry;
import com.google.common.collect.Lists;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class TeleporterGlistre extends Teleporter
{

    private final WorldServer worldServerInstance; //this is initialized in the constructor or right here because it's final.  in this case in the constructor
    /** A private Random() function in Teleporter */
    private final Random random;
    /** Stores successful portal placement locations for rapid lookup. */
    private final Long2ObjectMap destinationCoordinateCache = new Long2ObjectOpenHashMap();
    /**
     * A list of valid keys for the destinationCoordainteCache. These are based on the X & Z of the players initial
     * location.
     */
    private final List destinationCoordinateKeys = Lists.newArrayList();
    private boolean clearPath;
    private BlockPos newPos;
    
    public TeleporterGlistre(WorldServer worldserver)
    {
    	super(worldserver);
        this.worldServerInstance = worldserver;
        this.random = new Random(worldserver.getSeed());//generates a random/seed for the world  using .getSeed makes it the same random as the minecraft random
        this.clearPath = true;
        this.newPos = null;
    }
    
    public TeleporterGlistre(WorldServer worldServer, BlockPos newPos, boolean clearPath){
        super(worldServer);
        this.worldServerInstance = worldServer;
        this.random = worldServer.rand;
        this.clearPath = clearPath;
        this.newPos = newPos;
    }

/*    public void placeInPortal(Entity entityIn, float rotationYaw)
    {
        if (this.worldServerInstance.provider.getDimensionId() != 1)
        {
            if (!this.placeInExistingPortal(entityIn, rotationYaw))
            {
                this.makePortal(entityIn);
                this.placeInExistingPortal(entityIn, rotationYaw);
                
            }
            
        }
        else
        {
            int i = MathHelper.floor_double(entityIn.posX);//corner?
            int j = MathHelper.floor_double(entityIn.posY) - 1;
            int k = MathHelper.floor_double(entityIn.posZ);
            int l = 1;
            int i1 = 0;

            for (int j1 = -2; j1 <= 2; ++j1)
            {
                for (int k1 = -2; k1 <= 2; ++k1)
                {
                    for (int l1 = -1; l1 < 3; ++l1)
                    {
                        int i2 = i + k1 * l + j1 * i1;
                        int j2 = j + l1;
                        int k2 = k + k1 * i1 - j1 * l;
                        boolean flag = l1 < 0;
                        this.worldServerInstance.setBlockState(new BlockPos(i2, j2, k2), flag ? BlockRegistry.silver_ore_1.getDefaultState() : Blocks.air.getDefaultState());
                    }
                }
            }

            entityIn.setLocationAndAngles((double)i, (double)j, (double)k, entityIn.rotationYaw, 0.0F);
            entityIn.motionX = entityIn.motionY = entityIn.motionZ = 0.0D;
        }
    }*/
   public void placeInPortal(Entity entityIn, float rotationYaw)
    {
    	if (!this.placeInExistingPortal(entityIn, rotationYaw))
        {
    		this.makePortal(entityIn);
            //this.placeInExistingPortal(entityIn, rotationYaw);
        }
        this.placeInExistingPortal(entityIn, rotationYaw);

    }

    //last ad 12-13-16
    public boolean placeInExistingPortal(Entity entityIn, float rotationYaw)
    {
    int i = 128;
    double d0 = -1.0D;
    int j = MathHelper.floor_double(entityIn.posX);
    int k = MathHelper.floor_double(entityIn.posZ);
    boolean flag = true;
    BlockPos blockpos = BlockPos.ORIGIN;
   // long l = ChunkPos.chunkXZ2Int(j, k); 1.10 code
    long l = ChunkPos.asLong(j, k);
    
   // if (this.destinationCoordinateCache.containsKey(l))  1.10 code
    if (this.destinationCoordinateCache.containsKey(l))
    {
    	//getValueByKey(l); 1.10 code
    TeleporterGlistre.PortalPosition teleporter$portalposition = (TeleporterGlistre.PortalPosition)this.destinationCoordinateCache.get(1);
    d0 = 0.0D;
    blockpos = teleporter$portalposition;
    teleporter$portalposition.lastUpdateTime = this.worldServerInstance.getTotalWorldTime();
    flag = false;
    }
    else
    {
    BlockPos blockpos3 = new BlockPos(entityIn);

    for (int i1 = -128; i1 <= 128; ++i1)
    {
    BlockPos blockpos2;

    for (int j1 = -128; j1 <= 128; ++j1)
    {
    for (BlockPos blockpos1 = blockpos3.add(i1, this.worldServerInstance.getActualHeight() - 1 - blockpos3.getY(), j1); blockpos1.getY() >= 0; blockpos1 = blockpos2)
    {
    blockpos2 = blockpos1.down();

    //if (this.worldServerInstance.getBlockState(blockpos1).getBlock() == NxBlocks.Portal && this.worldServerInstance.getBlockState(blockpos1).getValue(NxBlocks.Portal.PortalID)==this.PortalID)
    if (this.worldServerInstance.getBlockState(blockpos1).getBlock() == BlockRegistry.light_portal) 
    		//&& Portals.getPortalID(worldServerInstance,blockpos1,null)==this.PortalID)
    {
    for (blockpos2 = blockpos1.down(); this.worldServerInstance.getBlockState(blockpos2).getBlock() == BlockRegistry.light_portal; blockpos2 = blockpos2.down())
    {
    blockpos1 = blockpos2;
    }

    double d1 = blockpos1.distanceSq(blockpos3);

    if (d0 < 0.0D || d1 < d0)
    {
    d0 = d1;
    blockpos = blockpos1;
    }
    }
    }
    }
    }
    }

    if (d0 >= 0.0D)
    {
    if (flag)
    {
    	//1.10 updating form 1.8.9 changed .add to .put
    this.destinationCoordinateCache.put(l, new TeleporterGlistre.PortalPosition(blockpos, this.worldServerInstance.getTotalWorldTime()));
 //no idea what this next line does but it's part of 1.8.9 code not 1.10
    this.destinationCoordinateKeys.add(Long.valueOf(l));
    
    }

    double d5 = (double)blockpos.getX() + 0.5D;
    double d7 = (double)blockpos.getZ() + 0.5D;
    BlockPos blockposdrop = new BlockPos(blockpos);
    while(this.worldServerInstance.getBlockState(blockposdrop.down()).getBlock()==BlockRegistry.light_portal)blockposdrop=blockposdrop.down();
    //while(!this.worldServerInstance.getBlockState(blockposdrop.down()).isNormalCube())blockposdrop=blockposdrop.down();
    double d6 = (double)blockposdrop.getY() + 0.5D;
    
    /*boolean flag1 = blockpattern$patternhelper.getForwards().rotateY().getAxisDirection() == EnumFacing.AxisDirection.NEGATIVE;
    double d2 = blockpattern$patternhelper.getForwards().getAxis() == EnumFacing.Axis.X ? (double)blockpattern$patternhelper.getFrontTopLeft().getZ() : (double)blockpattern$patternhelper.getFrontTopLeft().getX();
    double d6 = (double)(blockpattern$patternhelper.getFrontTopLeft().getY() + 1) - entityIn.getLastPortalVec().yCoord * (double)blockpattern$patternhelper.getHeight();

    if (flag1)
    {
    ++d2;
    }

    if (blockpattern$patternhelper.getForwards().getAxis() == EnumFacing.Axis.X)
    {
    d7 = d2 + (1.0D - entityIn.getLastPortalVec().xCoord) * (double)blockpattern$patternhelper.getWidth() * (double)blockpattern$patternhelper.getForwards().rotateY().getAxisDirection().getOffset();
    }
    else
    {
    d5 = d2 + (1.0D - entityIn.getLastPortalVec().xCoord) * (double)blockpattern$patternhelper.getWidth() * (double)blockpattern$patternhelper.getForwards().rotateY().getAxisDirection().getOffset();
    }*/

    float f = 0.0F;
    float f1 = 0.0F;
    float f2 = 0.0F;
    float f3 = 0.0F;

    /*if (blockpattern$patternhelper.getForwards().getOpposite() == entityIn.getTeleportDirection())
    {
    f = 1.0F;
    f1 = 1.0F;
    }
    else if (blockpattern$patternhelper.getForwards().getOpposite() == entityIn.getTeleportDirection().getOpposite())
    {
    f = -1.0F;
    f1 = -1.0F;
    }
    else if (blockpattern$patternhelper.getForwards().getOpposite() == entityIn.getTeleportDirection().rotateY())
    {
    f2 = 1.0F;
    f3 = -1.0F;
    }
    else
    {
    f2 = -1.0F;
    f3 = 1.0F;
    }*/

    double d3 = entityIn.motionX;
    double d4 = entityIn.motionZ;
    entityIn.motionX = d3 * (double)f + d4 * (double)f3;
    entityIn.motionZ = d3 * (double)f2 + d4 * (double)f1;
    entityIn.rotationYaw = rotationYaw - (float)(entityIn.getHorizontalFacing().getOpposite().getHorizontalIndex() * 90);


	//to work on server you have to check for entity instance of EntityPlayer and then cast to it
    
    if (entityIn instanceof EntityPlayerMP)
    {
    //	EntityPlayer player = (EntityPlayer)entityIn;//just an example of casting
    //.connection.setPlayerLocation -->1.10 code
    ((EntityPlayerMP)entityIn).setPositionAndRotation(d5, d6, d7, entityIn.rotationYaw, entityIn.rotationPitch);
    }
    else
    {
/*    //1.10 .func_181012_aH()  becomes .getHorizontalFacing()  
 /*  double d3 = entityIn.motionX;
        double d4 = entityIn.motionZ;
        entityIn.motionX = d3 * (double)f + d4 * (double)f3;
        entityIn.motionZ = d3 * (double)f2 + d4 * (double)f1;
        entityIn.rotationYaw = rotationYaw - (float)
              (entityIn.func_181012_aH().getOpposite()
              .getHorizontalIndex() * 90) + (float)(patternhelper.getFinger().getHorizontalIndex() * 90);*/
    	/*//you might need this section to make it work
    	 * if (!this.worldServerInstance.isRemote) { 
   EntityEnderPearl entityenderpearl = new EntityEnderPearl(this.worldServerInstance, (EntityLivingBase) entityIn);
   entityenderpearl.setLocationAndAngles(x, y, z, entityIn.rotationYaw, entityIn.rotationPitch); 
   entityenderpearl.motionY=-1;
   this.worldServerInstance.spawnEntityInWorld(entityenderpearl); 
}*/

    entityIn.setLocationAndAngles(d5, d6, d7, entityIn.rotationYaw, entityIn.rotationPitch);
    
    }

    return true;
    }
    else
    {
    return false;
    }
    }
    //last removed 12-13/16
/*    public boolean placeInExistingPortal(Entity entityIn, float rotationYaw)
    {
        int i = 128;
        double d0 = -1.0D;
        int j = MathHelper.floor_double(entityIn.posX);
        int k = MathHelper.floor_double(entityIn.posZ);
        boolean flag = true;
        BlockPos blockpos = BlockPos.ORIGIN;
        long l = ChunkCoordIntPair.chunkXZ2Int(j, k);

        if (this.destinationCoordinateCache.containsItem(l))
        {
            TeleporterGlistre.PortalPosition portalposition = (TeleporterGlistre.PortalPosition)this.destinationCoordinateCache.getValueByKey(l);
            d0 = 0.0D;
            blockpos = portalposition;
            portalposition.lastUpdateTime = this.worldServerInstance.getTotalWorldTime();
            flag = false;
        }
        else
        {
            BlockPos blockpos3 = new BlockPos(entityIn);

            for (int i1 = -128; i1 <= 128; ++i1)
            {
                BlockPos blockpos2;

                for (int j1 = -128; j1 <= 128; ++j1)
                {
                    for (BlockPos blockpos1 = blockpos3.add(i1, this.worldServerInstance.getActualHeight() - 1 - blockpos3.getY(), j1); blockpos1.getY() >= 0; blockpos1 = blockpos2)
                    {
                        blockpos2 = blockpos1.down();

                        if (this.worldServerInstance.getBlockState(blockpos1).getBlock() == BlockRegistry.light_portal)
                        {
                            while (this.worldServerInstance.getBlockState(blockpos2 = blockpos1.down()).getBlock() == BlockRegistry.light_portal)
                            {
                                blockpos1 = blockpos2;
                            }

                            double d1 = blockpos1.distanceSq(blockpos3);

                            if (d0 < 0.0D || d1 < d0)
                            {
                                d0 = d1;
                                blockpos = blockpos1;
                            }
                        }
                    }
                }
            }
        }

        if (d0 >= 0.0D)
        {
            if (flag)
            {
                this.destinationCoordinateCache.add(l, new TeleporterGlistre.PortalPosition(blockpos, this.worldServerInstance.getTotalWorldTime()));
                this.destinationCoordinateKeys.add(Long.valueOf(l));
            }

            double d5 = (double)blockpos.getX() + 0.5D;
            double d6 = (double)blockpos.getY() + 0.5D;
            double d7 = (double)blockpos.getZ() + 0.5D;
            //extending BlockBreakable instead of BlockPortal causes error on func_18109_f so cast from Block Portal must be a method in BlockPortal class
            BlockPattern.PatternHelper patternhelper = ((BlockPortal) BlockRegistry.light_portal).func_181089_f(this.worldServerInstance, blockpos);
            boolean flag1 = patternhelper.getFinger().rotateY().getAxisDirection() == EnumFacing.AxisDirection.NEGATIVE;
            double d2 = patternhelper.getFinger().getAxis() == EnumFacing.Axis.X ? (double)patternhelper.func_181117_a().getZ() : (double)patternhelper.func_181117_a().getX();
 
            d6 = (double)(patternhelper.func_181117_a().getY() + 1) - entityIn.func_181014_aG().yCoord * (double)patternhelper.func_181119_e();

            if (flag1)
            {
                ++d2;
            }

            if (patternhelper.getFinger().getAxis() == EnumFacing.Axis.X)
            {
                d7 = d2 + (1.0D - entityIn.func_181014_aG().xCoord) * (double)patternhelper.func_181118_d() * (double)patternhelper.getFinger().rotateY().getAxisDirection().getOffset();
            }
            else
            {
                d5 = d2 + (1.0D - entityIn.func_181014_aG().xCoord) * (double)patternhelper.func_181118_d() * (double)patternhelper.getFinger().rotateY().getAxisDirection().getOffset();
            }

            float f = 0.0F;
            float f1 = 0.0F;
            float f2 = 0.0F;
            float f3 = 0.0F;

            if (patternhelper.getFinger().getOpposite() == entityIn.func_181012_aH())
            {
                f = 1.0F;
                f1 = 1.0F;
            }
            else if (patternhelper.getFinger().getOpposite() == entityIn.func_181012_aH().getOpposite())
            {
                f = -1.0F;
                f1 = -1.0F;
            }
            else if (patternhelper.getFinger().getOpposite() == entityIn.func_181012_aH().rotateY())
            {
                f2 = 1.0F;
                f3 = -1.0F;
            }
            else
            {
                f2 = -1.0F;
                f3 = 1.0F;
            }

            double d3 = entityIn.motionX;
            double d4 = entityIn.motionZ;
            entityIn.motionX = d3 * (double)f + d4 * (double)f3;
            entityIn.motionZ = d3 * (double)f2 + d4 * (double)f1;
            entityIn.rotationYaw = rotationYaw - (float)(entityIn.func_181012_aH().getOpposite().getHorizontalIndex() * 90) + (float)(patternhelper.getFinger().getHorizontalIndex() * 90);
       /*     double d4 = (double)((BlockPos)blockpos).getX() + 0.5D;
            double d5 = (double)((BlockPos)blockpos).getY() + 0.5D;
            double d6 = (double)((BlockPos)blockpos).getZ() + 0.5D;
            EnumFacing enumfacing = null;

            if (this.worldServerInstance.getBlockState(((BlockPos)blockpos).west()).getBlock() == BlockRegistry.light_portal)
            {
                enumfacing = EnumFacing.NORTH;
            }

            if (this.worldServerInstance.getBlockState(((BlockPos)blockpos).east()).getBlock() == BlockRegistry.light_portal)
            {
                enumfacing = EnumFacing.SOUTH;
            }

            if (this.worldServerInstance.getBlockState(((BlockPos)blockpos).north()).getBlock() == BlockRegistry.light_portal)
            {
                enumfacing = EnumFacing.EAST;
            }

            if (this.worldServerInstance.getBlockState(((BlockPos)blockpos).south()).getBlock() == BlockRegistry.light_portal)
            {
                enumfacing = EnumFacing.WEST;
            }

            EnumFacing enumfacing1 = EnumFacing.getHorizontal(entityIn.getTeleportDirection());

            if (enumfacing != null)
            {
                EnumFacing enumfacing2 = enumfacing.rotateYCCW();
                BlockPos blockpos2 = ((BlockPos)blockpos).offset(enumfacing);
                boolean flag2 = this.func_180265_a(blockpos2);
                boolean flag3 = this.func_180265_a(blockpos2.offset(enumfacing2));

                if (flag3 && flag2)
                {
                    blockpos = ((BlockPos)blockpos).offset(enumfacing2);
                    enumfacing = enumfacing.getOpposite();
                    enumfacing2 = enumfacing2.getOpposite();
                    BlockPos blockpos3 = ((BlockPos)blockpos).offset(enumfacing);
                    flag2 = this.func_180265_a(blockpos3);
                    flag3 = this.func_180265_a(blockpos3.offset(enumfacing2));
                }

                float f6 = 0.5F;
                float f1 = 0.5F;

                if (!flag3 && flag2)
                {
                    f6 = 1.0F;
                }
                else if (flag3 && !flag2)
                {
                    f6 = 0.0F;
                }
                else if (flag3)
                {
                    f1 = 0.0F;
                }

                d4 = (double)((BlockPos)blockpos).getX() + 0.5D;
                d5 = (double)((BlockPos)blockpos).getY() + 0.5D;
                d6 = (double)((BlockPos)blockpos).getZ() + 0.5D;
                d4 += (double)((float)enumfacing2.getFrontOffsetX() * f6 + (float)enumfacing.getFrontOffsetX() * f1);
                d6 += (double)((float)enumfacing2.getFrontOffsetZ() * f6 + (float)enumfacing.getFrontOffsetZ() * f1);
                float f2 = 0.0F;
                float f3 = 0.0F;
                float f4 = 0.0F;
                float f5 = 0.0F;

                if (enumfacing == enumfacing1)
                {
                    f2 = 1.0F;
                    f3 = 1.0F;
                }
                else if (enumfacing == enumfacing1.getOpposite())
                {
                    f2 = -1.0F;
                    f3 = -1.0F;
                }
                else if (enumfacing == enumfacing1.rotateY())
                {
                    f4 = 1.0F;
                    f5 = -1.0F;
                }
                else
                {
                    f4 = -1.0F;
                    f5 = 1.0F;
                }

                double d2 = entityIn.motionX;
                double d3 = entityIn.motionZ;
                entityIn.motionX = d2 * (double)f2 + d3 * (double)f5;
                entityIn.motionZ = d2 * (double)f4 + d3 * (double)f3;
                entityIn.rotationYaw = rotationYaw - (float)(enumfacing1.getHorizontalIndex() * 90) + (float)(enumfacing.getHorizontalIndex() * 90);
           
            entityIn.setLocationAndAngles(d5, d6, d7, entityIn.rotationYaw, entityIn.rotationPitch);
            return true;
        }
        else
        {
            return false;
        }
    

         //added next return statement 1.8.9
//   	return true;
    
}*/
   //next makePortal method works fine but no portal created and entities weird floating 
/*    @Override
 	public boolean makePortal(Entity entityIn)
 	{
 	int i = MathHelper.floor_double(entityIn.posX);
 	int j = MathHelper.floor_double(entityIn.posY);
 	int k = MathHelper.floor_double(entityIn.posZ);
 	
 	BlockPos pos = new BlockPos(i, j, k - 1);
 	if (this.worldServerInstance.isAirBlock(pos)) {
         if (j > this.worldServerInstance.getActualHeight() - 10)
         {
             j = this.worldServerInstance.getActualHeight() - 10;
         }
// 	j = this.worldServerInstance.getTopSolidOrLiquidBlock(pos);//.getTopSolidOrLiquidBlock((int) i, (int) k);
 	this.worldServerInstance.setBlockState(pos, BlockRegistry.light_portal.getDefaultState());
 	System.out.println("Get Ready");
 	}
 	return true;
 	}*/
    
    
    //next makePortal copied from ...?same as above but slightly more laggy too
/*    @Override
    public boolean makePortal(Entity entity) {
    	
        if (newPos == null){
            int i = 3;
        }


        if (clearPath){
            BlockPos playerSpawnAt = newPos;
            newPos = BlockPos.ORIGIN;

            if (playerSpawnAt.getY() < 5){
                playerSpawnAt = new BlockPos(playerSpawnAt.getX(), 5, playerSpawnAt.getZ());
            }

            BlockPos topRightBlock = playerSpawnAt.add(1,1,1);

            for (int x = 0; x >= -2; --x){
                for(int y = 0; y >= -2; --y){
                    for (int z = 0; z >= -2; --z){
                        BlockPos currentBlock = topRightBlock.add(x,y,z);

                        if (y != -2){
                            worldServerInstance.setBlockToAir(currentBlock);
                            worldServerInstance.notifyNeighborsRespectDebug(currentBlock, Blocks.air);
                        } else {
                            if (worldServerInstance.isAirBlock(currentBlock)
                                    || worldServerInstance.getBlockState(currentBlock).getBlock() instanceof BlockLiquid) {
                                worldServerInstance.setBlockState(currentBlock, BlockRegistry.light_portal.getDefaultState());
                                worldServerInstance.notifyNeighborsRespectDebug(currentBlock, BlockRegistry.light_portal);
                            }
                        }
                    }
                }
            }

        }

        return true;
    }*/
//vanilla portal method drops player five blocks below ground?  Also, no portal either.  
    //And no lag on chunk loading, no floating, but cannot hit entities because of hitting lag. 
    //FA changed BlockGlistre portal to extend BlockBreakable and did not spawn underground.  
    //after saving and loggin back in entity-hit lag is gone
    public boolean makePortal(Entity entity)
    {
        int i = 16;
        double d0 = -1.0D;
        int j = MathHelper.floor_double(entity.posX);
        int k = MathHelper.floor_double(entity.posY);
        int l = MathHelper.floor_double(entity.posZ);
        int i1 = j;
        int j1 = k;
        int k1 = l;
        int l1 = 0;
        int i2 = this.random.nextInt(4);
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for (int j2 = j - i; j2 <= j + i; ++j2)
        {
            double d1 = (double)j2 + 0.5D - entity.posX;

            for (int l2 = l - i; l2 <= l + i; ++l2)
            {
                double d2 = (double)l2 + 0.5D - entity.posZ;
                label142:
//checking for air
                for (int j3 = this.worldServerInstance.getActualHeight() - 1; j3 >= 0; --j3)
                {
                    if (this.worldServerInstance.isAirBlock(blockpos$mutableblockpos.setPos(j2, j3, l2)))
                    {
                        while (j3 > 0 && this.worldServerInstance.isAirBlock(blockpos$mutableblockpos.setPos(j2, j3 - 1, l2)))
                        {
                            --j3;
                        }

                        for (int k3 = i2; k3 < i2 + 4; ++k3)
                        {
                            int l3 = k3 % 2;
                            int i4 = 1 - l3;

                            if (k3 % 4 >= 2)
                            {
                                l3 = -l3;
                                i4 = -i4;
                            }
//checking for a solid block
                            for (int j4 = 0; j4 < 3; ++j4)
                            {
                                for (int k4 = 0; k4 < 4; ++k4)
                                {
                                    for (int l4 = -1; l4 < 4; ++l4)
                                    {
                                        int i5 = j2 + (k4 - 1) * l3 + j4 * i4;
                                        int j5 = j3 + l4;
                                        int k5 = l2 + (k4 - 1) * i4 - j4 * l3;
                                        blockpos$mutableblockpos.setPos(i5, j5, k5);

                                        if (l4 < 0 && !this.worldServerInstance.getBlockState(blockpos$mutableblockpos).getMaterial().isSolid() || l4 >= 0 && !this.worldServerInstance.isAirBlock(blockpos$mutableblockpos))
                                        {
                                            continue label142;
                                        }
                                    }
                                }
                            }

                            double d5 = (double)j3 + 0.5D - entity.posY;
                            double d7 = d1 * d1 + d5 * d5 + d2 * d2;

                            if (d0 < 0.0D || d7 < d0)
                            {
                                d0 = d7;
                                i1 = j2;
                                j1 = j3;
                                k1 = l2;
                                l1 = k3 % 4;
                            }
                        }
                    }
                }
            }
        }

        if (d0 < 0.0D)
        {
            for (int l5 = j - i; l5 <= j + i; ++l5)
            {
                double d3 = (double)l5 + 0.5D - entity.posX;

                for (int j6 = l - i; j6 <= l + i; ++j6)
                {
                    double d4 = (double)j6 + 0.5D - entity.posZ;
                    label562:

                    for (int i7 = this.worldServerInstance.getActualHeight() - 1; i7 >= 0; --i7)
                    {
                        if (this.worldServerInstance.isAirBlock(blockpos$mutableblockpos.setPos(l5, i7, j6)))
                        {
                            while (i7 > 0 && this.worldServerInstance.isAirBlock(blockpos$mutableblockpos.setPos(l5, i7 - 1, j6)))
                            {
                                --i7;
                            }

                            for (int k7 = i2; k7 < i2 + 2; ++k7)
                            {
                                int j8 = k7 % 2;
                                int j9 = 1 - j8;

                                for (int j10 = 0; j10 < 4; ++j10)
                                {
                                    for (int j11 = -1; j11 < 4; ++j11)
                                    {
                                        int j12 = l5 + (j10 - 1) * j8;
                                        int i13 = i7 + j11;
                                        int j13 = j6 + (j10 - 1) * j9;
                                        blockpos$mutableblockpos.setPos(j12, i13, j13);

                                        if (j11 < 0 && !this.worldServerInstance.getBlockState(blockpos$mutableblockpos).getMaterial().isSolid() || j11 >= 0 && !this.worldServerInstance.isAirBlock(blockpos$mutableblockpos))
                                        {
                                            continue label562;
                                        }
                                    }
                                }

                                double d6 = (double)i7 + 0.5D - entity.posY;
                                double d8 = d3 * d3 + d6 * d6 + d4 * d4;

                                if (d0 < 0.0D || d8 < d0)
                                {
                                    d0 = d8;
                                    i1 = l5;
                                    j1 = i7;
                                    k1 = j6;
                                    l1 = k7 % 2;
                                }
                            }
                        }
                    }
                }
            }
        }

        int i6 = i1;
        int k2 = j1;
        int k6 = k1;
        int l6 = l1 % 2;
        int i3 = 1 - l6;

        if (l1 % 4 >= 2)
        {
            l6 = -l6;
            i3 = -i3;
        }

        //actually setting the block of silver ore you can look at ScratchForFun episode 20 for explanation
        if (d0 < 0.0D)
        {
            j1 = MathHelper.clamp_int(j1, 70, this.worldServerInstance.getActualHeight() - 10);
            k2 = j1;

            for (int j7 = -1; j7 <= 1; ++j7)  //one, two , three blocks must be up and down the side y axis
            {
                for (int l7 = 1; l7 < 3; ++l7) //one, two, three, four blocks must be the x-axis ie, horizontal
                {
                    for (int k8 = -1; k8 < 3; ++k8) // one, two, three, four must be 
                    {
                        int k9 = i6 + (l7 - 1) * l6 + j7 * i3;
                        int k10 = k2 + k8 + 1; // added one because portal was being set in the ground not sure why Mojang did that
                        int k11 = k6 + (l7 - 1) * i3 - j7 * l6;
                        boolean flag = k8 < 0;
                        this.worldServerInstance.setBlockState(new BlockPos(k9, k10, k11), flag ? BlockRegistry.silver_ore_1.getDefaultState() : Blocks.AIR.getDefaultState());
                    }
                }
            }
        }

        IBlockState iblockstate = BlockRegistry.light_portal.getDefaultState().withProperty(BlockGlistrePortal.AXIS, l6 != 0 ? EnumFacing.Axis.X : EnumFacing.Axis.Z);

        for (int i8 = 0; i8 < 4; ++i8)
        {
            for (int l8 = 0; l8 < 4; ++l8)
            {
                for (int l9 = -1; l9 < 4; ++l9)
                {
                    int l10 = i6 + (l8 - 1) * l6;
                    int l11 = k2 + l9 + 1; // added one because portal was being set in the ground not sure why Mojang did that
                    int k12 = k6 + (l8 - 1) * i3;
                    boolean flag1 = l8 == 0 || l8 == 3 || l9 == -1 || l9 == 3;
                    this.worldServerInstance.setBlockState(new BlockPos(l10, l11, k12), flag1 ? BlockRegistry.silver_ore_1.getDefaultState() : iblockstate, 2);
                }
            }

            for (int i9 = 0; i9 < 4; ++i9)
            {
                for (int i10 = -1; i10 < 4; ++i10)
                {
                    int i11 = i6 + (i9 - 1) * l6;
                    int i12 = k2 + i10;
                    int l12 = k6 + (i9 - 1) * i3;
                    BlockPos blockpos = new BlockPos(i11, i12, l12);
                    this.worldServerInstance.notifyNeighborsOfStateChange(blockpos, this.worldServerInstance.getBlockState(blockpos).getBlock());
                }
            }
        }

        return true;
    }

    /**
     * called periodically to remove out-of-date portal locations from the cache list. Argument par1 is a
     * WorldServer.getTotalWorldTime() value.
     */
   @Override
    public void removeStalePortalLocations(long worldTime)
    {
        if (worldTime % 100L == 0L)
        {
            Iterator<Long> iterator = this.destinationCoordinateKeys.iterator();
            long i = worldTime - 300L;

            while (iterator.hasNext())
            {
                Long olong = (Long)iterator.next();
                TeleporterGlistre.PortalPosition teleporter$portalposition = (TeleporterGlistre.PortalPosition)this.destinationCoordinateCache.get(olong.longValue());

                if (teleporter$portalposition == null || teleporter$portalposition.lastUpdateTime < i)
                {
                    iterator.remove();
                    this.destinationCoordinateCache.remove(olong.longValue());
                }
            }
        }
    }
    
    public class PortalPosition extends BlockPos
    {
        /** The worldtime at which this PortalPosition was last verified */
        public long lastUpdateTime;

        public PortalPosition(BlockPos pos, long lastUpdate)
        {
            super(pos.getX(), pos.getY(), pos.getZ());
            this.lastUpdateTime = lastUpdate;
        }
    }
}