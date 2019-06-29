package com.glistre.glistremod.biome;

import java.util.Random;

import com.glistre.glistremod.blocks.fluids.ModFluids;
import com.glistre.glistremod.entities.blacktobie.EntityBlackTobo;
import com.glistre.glistremod.entities.wolf.EntityBlackWolf;
import com.glistre.glistremod.init.BiomeRegistry;
import com.glistre.glistremod.init.BlockRegistry;
import com.glistre.glistremod.worldgen.EmeraldGenerator;
import com.glistre.glistremod.worldgen.TobyKingTowerGen;
import com.glistre.glistremod.worldgen.WorldGenFreonSpikes;
import com.google.common.base.Predicate;

import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
//import net.minecraft.block.state.pattern.BlockHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenIceSpike;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.feature.WorldGenLiquids;
import net.minecraft.world.gen.feature.WorldGenMinable;
//import net.minecraft.world.gen.feature.WorldGenHugeTrees;
//import net.minecraft.world.gen.feature.WorldGenTaiga1;
//import net.minecraft.world.gen.feature.WorldGenTaiga2;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.TerrainGen;

public class FreonBiome extends Biome{

    private WorldGenerator theWorldGenerator;
    private WorldGenFreonSpikes freonSpikesGen;

//    private WorldGenFreonSpikes field_150616_aD = new WorldGenFreonSpikes(); 
	
	public FreonBiome(Biome.BiomeProperties biome){
		super(biome);
      //  this.setBiomeName("Freon Biome");
        this.topBlock = (IBlockState) Blocks.SNOW.getDefaultState();
		this.fillerBlock = (IBlockState) Blocks.PACKED_ICE.getDefaultState();
		this.theBiomeDecorator.treesPerChunk = 16;

        this.theWorldGenerator = new WorldGenLakes(Blocks.WATER);

        this.theWorldGenerator = new WorldGenMinable((IBlockState) BlockRegistry.silver_ore_1.getDefaultState(), 20);
        this.theWorldGenerator = new WorldGenMinable((IBlockState) BlockRegistry.silver_block_1.getDefaultState(), 8);
        this.theWorldGenerator = new WorldGenMinable((IBlockState) BlockRegistry.liquid_ice.getDefaultState(), 8);
        this.freonSpikesGen = new WorldGenFreonSpikes();
		
  //      this.setColor(0x443333);
  //      this.setEnableSnow();
  //      this.setHeight(BiomeGenBase.height_MidHills);
 //       this.setMinMaxHeight(1.7F, 4.1F);
 //       this.setTemperatureRainfall(1.75F, 1.25F);
        //change water color to red--0xFF0000
        //change water color to dark green like grass

//        this.waterColorMultiplier = 0x9CFF00;
 //       this.canSpawnLightningBolt();
        this.spawnableCreatureList.clear();
        this.spawnableMonsterList.clear();
        this.spawnableMonsterList.add(new SpawnListEntry(EntitySkeleton.class, 1, 1, 1));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityZombie.class, 30, 1, 2));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityWitch.class, 20, 1, 2));
        this.spawnableMonsterList.add(new SpawnListEntry(EntitySpider.class, 20, 1, 1));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityEnderman.class, 10, 1, 2));
        this.spawnableCaveCreatureList.add(new SpawnListEntry(EntityCaveSpider.class, 10, 1, 2));
//        this.spawnableCreatureList.add(new SpawnListEntry(EntityBlackTobo.class, 30, 1, 2));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityBlackWolf.class, 10, 1, 2));
	}
	
/*	private void setMinMaxHeight(float f, float g) 
	{
	
	}*/
	
    public int getSkyColorByTemp(float par1)
    {
// grey sky
    	return 0x443333;  
    }
    
    @Override
	public Biome.TempCategory getTempCategory()
	{
		return TempCategory.COLD;
	}
    public int getBiomeGrassColor()
    {
    	return 0x443333;
    }

    public WorldGenerator getRandomWorldGenForTrees(Random random){
    	return(WorldGenerator)(random.nextInt(1) == 0 ? new WorldGenFreonSpikes() : (random.nextInt(6) == 0 
    			? this.SWAMP_FEATURE : random.nextInt(50) == 0  
   				? this.theWorldGenerator : this.theWorldGenerator));
    }
    @SubscribeEvent
	public void DecorateBiomeEvent(net.minecraftforge.event.terraingen.DecorateBiomeEvent event)
	{
		BlockPos pos = event.getPos();
		World world = event.getWorld();
		Random random = event.getRand();
		
		int xCoord = pos.getX();
		int zCoord = pos.getZ();

		
		
		BlockPos pos1 = new BlockPos(xCoord+2, 0, zCoord+2);
		
		if (random.nextInt(500)==0) (new TobyKingTowerGen()).generate(world, random, pos1);
	}
    
    @Override
    public void decorate(World worldIn, Random rand, BlockPos pos)
    {
        super.decorate(worldIn, rand, pos);
        
        for (int i = 0; i < 1; ++i)
        {
            int j = rand.nextInt(16) + 4;
            int k = rand.nextInt(16) + 4;
            this.freonSpikesGen.generate(worldIn, rand, worldIn.getHeight(pos.add(j, 0, k)));
        }
 /*       net.minecraftforge.common.MinecraftForge.ORE_GEN_BUS.post(new net.minecraftforge.event.terraingen.OreGenEvent.Pre(worldIn, rand, pos));
        WorldGenerator emeralds = new EmeraldGenerator();
        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, rand, emeralds, pos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.EMERALD))
            emeralds.generate(worldIn, rand, pos);
 
        for (int i = 0; i < 7; ++i)
        {
            int j1 = rand.nextInt(16);
            int k1 = rand.nextInt(64);
            int l1 = rand.nextInt(16);
            if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, rand, theWorldGenerator, pos.add(j1, k1, l1), net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.EMERALD))
            this.theWorldGenerator.generate(worldIn, rand, pos.add(j1, k1, l1));
        }
        net.minecraftforge.common.MinecraftForge.ORE_GEN_BUS.post(new net.minecraftforge.event.terraingen.OreGenEvent.Post(worldIn, rand, pos));*/
    }
    
 
    public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal)
    {
        this.topBlock = Blocks.SNOW.getDefaultState();
        this.fillerBlock = Blocks.PACKED_ICE.getDefaultState();
 
        if ((noiseVal < -1.0D || noiseVal > 2.0D) && this.equals(BiomeRegistry.biomeFreon))
        {
            this.topBlock = Blocks.SNOW.getDefaultState();
            this.fillerBlock = Blocks.PACKED_ICE.getDefaultState();
        }
 
        this.generateBiomeTerrain(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
    }

    
    //1.7.10 replaces GlistreBlock with SilverBlock copied from BlockEmeraldClass
    //1.8 replaces SilverOre with SilverBlock
 /*   public void decorate(World worldIn, Random random, BlockPos blockPos)
    {

        //next two bizarre lines of code is update to 1.8
        for (int chunkX = 0; chunkX < 4; ++chunkX) {
			for (int chunkZ = 0; chunkZ < 4; ++chunkZ) {
		int k = 3 + random.nextInt(6);
        int l;
        int i1;
        int j1;
     
        for (l = 0; l < k; ++l)
        {
            i1 = chunkX + random.nextInt(16);
            j1 = random.nextInt(28) + 4;
            int k1 = chunkZ + random.nextInt(16);
//1.8 changed target from glistre_block_1 to silver_ore_1 //Predicate is google.common.base.Predicate
            //1.8.9 should be BlockMatcher
            
            if (worldIn.getBlockState(blockPos).getBlock().isReplaceableOreGen(worldIn, blockPos, BlockHelper.forBlock(BlockRegistry.silver_ore_1)))       		
            {
            	//last argument to setBlockState is a bit flag that tells Minecraft whether or not to update the client side / notify neighboring blocks / other things, and you almost always want it set to either 2 (notify client) or 3 (notify client AND notify neighbors, IIRC).
                worldIn.setBlockState(blockPos, (IBlockState) BlockRegistry.silver_block_1.getDefaultState(), 2);
            }
        }

        if(worldIn.getChunkFromBlockCoords(blockPos) != null){
        for (k = 0; k < 7; ++k)
        {
            l = chunkX + random.nextInt(16);
            i1 = random.nextInt(64);
            j1 = chunkZ + random.nextInt(16);
            this.theWorldGenerator.generate(worldIn, random, blockPos);
        }
        //hopefully try/catch statement wraps around #generate so decorate event does not try to generate blocks before the chunk exists which would make y negative
        //  for example and create the 'Already decorating!' error and crash
        try{
            super.decorate(worldIn, random, blockPos);  
            }catch (Exception err){Throwable cause = err.getCause();
            if (err.getMessage() != null && err.getMessage().equals("Already decorating!!") ||
                    (cause != null && cause.getMessage() != null && cause.getMessage().equals("Already decorating!!") || cause != null  && cause.getMessage().equals("NullPointerException")))
                    //Exception caught executing FutureTask: java.util.concurrent.ExecutionException: java.lang.NullPointerException
                //    java.util.concurrent.ExecutionException: java.lang.NullPointerException))
            {
                ;
            } else {
                err.printStackTrace();
            }
            }
    
      }    
     }
    }
}*/
}

