package com.glistre.glistremod.dimension;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.List;
import java.util.Random;

import com.glistre.glistremod.blocks.BlockTobyKingPortal;
import com.glistre.glistremod.init.BlockRegistry;
import com.google.common.collect.Lists;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;


public class TeleporterFreon extends Teleporter {
	
    /** Stores successful portal placement locations for rapid lookup. */
    private final Long2ObjectMap destinationCoordinateCache = new Long2ObjectOpenHashMap();
    /**
     * A list of valid keys for the destinationCoordainteCache. These are based on the X & Z of the players initial
     * location.
     */
    private final List destinationCoordinateKeys = Lists.newArrayList();

	
	
    private final WorldServer worldServerInstance;
    private final Random random;
    private boolean clearPath;
    private BlockPos newPos;

    public TeleporterFreon(WorldServer worldServer){
        super(worldServer);
        this.worldServerInstance = worldServer;
        this.random = worldServer.rand;
        this.clearPath = true;
        this.newPos = null;
    }

    public TeleporterFreon(WorldServer worldServer, BlockPos newPos, boolean clearPath){
        super(worldServer);
        this.worldServerInstance = worldServer;
        this.random = worldServer.rand;
        this.clearPath = clearPath;
        this.newPos = newPos;
    }

    public void placeInPortal(Entity entityIn, float rotationYaw)
    {
    	if (!this.placeInExistingPortal(entityIn, rotationYaw))
        {
    		this.makePortal(entityIn);
            //this.placeInExistingPortal(entityIn, rotationYaw);
        }
        this.placeInExistingPortal(entityIn, rotationYaw);

    }
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

                                        if (l4 < 0 && !this.worldServerInstance.getBlockState(blockpos$mutableblockpos).getBlock().getMaterial(null).isSolid() || l4 >= 0 && !this.worldServerInstance.isAirBlock(blockpos$mutableblockpos))
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

        if (d0 < 0.0D)
        {
            j1 = MathHelper.clamp_int(j1, 70, this.worldServerInstance.getActualHeight() - 10);
            k2 = j1;

            for (int j7 = -1; j7 <= 1; ++j7)
            {
                for (int l7 = 1; l7 < 3; ++l7)
                {
                    for (int k8 = -1; k8 < 3; ++k8)
                    {
                        int k9 = i6 + (l7 - 1) * l6 + j7 * i3;
                        int k10 = k2 + k8 + 1;// added one because portal was being set in the ground not sure why Mojang did that
                        int k11 = k6 + (l7 - 1) * i3 - j7 * l6;
                        boolean flag = k8 < 0;
                        this.worldServerInstance.setBlockState(new BlockPos(k9, k10, k11), flag ? BlockRegistry.silver_block_1.getDefaultState() : Blocks.AIR.getDefaultState());
                    }
                }
            }
        }

        IBlockState iblockstate = BlockRegistry.light_toby_king_portal.getDefaultState().withProperty(BlockTobyKingPortal.AXIS, l6 != 0 ? EnumFacing.Axis.X : EnumFacing.Axis.Z);

        for (int i8 = 0; i8 < 4; ++i8)
        {
            for (int l8 = 0; l8 < 4; ++l8)
            {
                for (int l9 = -1; l9 < 4; ++l9)
                {
                    int l10 = i6 + (l8 - 1) * l6;
                    int l11 = k2 + l9 + 1;// added one because portal was being set in the ground not sure why Mojang did that
                    int k12 = k6 + (l8 - 1) * i3;
                    boolean flag1 = l8 == 0 || l8 == 3 || l9 == -1 || l9 == 3;
                    this.worldServerInstance.setBlockState(new BlockPos(l10, l11, k12), flag1 ? BlockRegistry.silver_block_1.getDefaultState() : iblockstate, 2);
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

    @Override//some slightly floating entities no portal spawns, player spawns underground
/*    public boolean makePortal(Entity entity) {
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
                                worldServerInstance.setBlockState(currentBlock, BlockRegistry.light_toby_king_portal.getDefaultState());
                                worldServerInstance.notifyNeighborsRespectDebug(currentBlock, BlockRegistry.light_toby_king_portal);
                            }
                        }
                    }
                }
            }

        }

        return true;
    }*/
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
    TeleporterFreon.PortalPosition teleporter$portalposition = (TeleporterFreon.PortalPosition)this.destinationCoordinateCache.get(l);
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
    if (this.worldServerInstance.getBlockState(blockpos1).getBlock() == BlockRegistry.light_toby_king_portal) 
    		//&& Portals.getPortalID(worldServerInstance,blockpos1,null)==this.PortalID)
    {
    for (blockpos2 = blockpos1.down(); this.worldServerInstance.getBlockState(blockpos2).getBlock() == BlockRegistry.light_toby_king_portal; blockpos2 = blockpos2.down())
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
    	//changed add to put updating to 1.10 but all of the rest of #placeInExistingPortal is commented out so that's probably wrong
    this.destinationCoordinateCache.put(l, new TeleporterFreon.PortalPosition(blockpos, this.worldServerInstance.getTotalWorldTime()));
 //no idea what this next line does but it's part of 1.8.9 code not 1.10
    this.destinationCoordinateKeys.add(Long.valueOf(l));
    
    }

    double d5 = (double)blockpos.getX() + 0.5D;
    double d7 = (double)blockpos.getZ() + 0.5D;
    BlockPos blockposdrop = new BlockPos(blockpos);
    while(this.worldServerInstance.getBlockState(blockposdrop.down()).getBlock()==BlockRegistry.light_toby_king_portal)blockposdrop=blockposdrop.down();
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
/*    @Override//some slightly floating entities no portal spawns, player spawns underground
      public boolean placeInExistingPortal(Entity entityIn, float rotationYaw) {
      if (newPos == null){
            newPos = entityIn.getPosition();
        }


        newPos = new BlockPos(MathHelper.clamp_int(newPos.getX(), -29999850, 29999850),
                MathHelper.clamp_int(newPos.getY(), 0, worldServerInstance.getHeight()),
                MathHelper.clamp_int(newPos.getZ(), -29999850, 29999850));


        if (!clearPath){
            entityIn.setLocationAndAngles(newPos.getX()+.5d, newPos.getY()+.5d, newPos.getZ()+.5d, entityIn.rotationYaw, entityIn.rotationPitch);
            return true;
        } else {
            BlockPos startingPlayerPos = newPos;
            BlockPos currentSearchPos = startingPlayerPos;

            // can only go as low as 5 height level
            if (currentSearchPos.getY() < 5){
                currentSearchPos = new BlockPos(currentSearchPos.getX(), 5, currentSearchPos.getZ());
            }

            boolean found = false;
            //check above player for an air block
            while (!found && currentSearchPos.getY() < worldServerInstance.getActualHeight()-1){

                if(worldServerInstance.isAirBlock(currentSearchPos)
                        && worldServerInstance.isAirBlock(currentSearchPos.up())
                        && worldServerInstance.isSideSolid(currentSearchPos.down(), EnumFacing.UP)){
                    found = true;
                } else {
                    currentSearchPos = currentSearchPos.up();
                }
            }
            //check under player, for air block
            if (!found){
                for(int i = 5; !found && i < startingPlayerPos.getY(); i++){
                    if(worldServerInstance.isAirBlock(currentSearchPos)
                            && worldServerInstance.isAirBlock(currentSearchPos.up())
                            && worldServerInstance.isSideSolid(currentSearchPos.down(), EnumFacing.UP)){
                        found = true;
                    } else {
                        currentSearchPos = new BlockPos(currentSearchPos.getX(), i, currentSearchPos.getZ());
                    }
                }
            }

            if (found){
                entityIn.setLocationAndAngles(currentSearchPos.getX(), currentSearchPos.getY(), currentSearchPos.getZ(), entityIn.rotationYaw, entityIn.rotationPitch);
            }

            return found;




        }
    }*/
    }