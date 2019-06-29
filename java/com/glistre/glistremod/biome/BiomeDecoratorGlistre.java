package com.glistre.glistremod.biome;



import java.util.Random;

import com.glistre.glistremod.chunkprovider.GlistreChunkProvider;
import com.glistre.glistremod.init.BiomeRegistry;
import com.glistre.glistremod.init.BlockRegistry;
import com.glistre.glistremod.mapgen.MapGenGlistreVillage;
import com.glistre.glistremod.worldgen.EmeraldGenerator;
import com.glistre.glistremod.worldgen.FirstBlockGen;
import com.glistre.glistremod.worldgen.WorldGenGlistreMushroom;
import com.glistre.glistremod.worldgen.WorldGenSeaweed;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockFlower.EnumFlowerType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.ChunkProviderSettings;
import net.minecraft.world.gen.feature.WorldGenCanopyTree;
import net.minecraft.world.gen.feature.WorldGenDoublePlant;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenLiquids;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.BiomeEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.DeferredBiomeDecorator;
import net.minecraftforge.event.terraingen.TerrainGen;

public class BiomeDecoratorGlistre extends BiomeDecorator
{
	 /** The world the BiomeDecorator is currently decorating */
    public World currentWorld;
    /** The Biome Decorator's random number generator. */
    public Random randomGenerator;

    /** The X-coordinate of the chunk currently being decorated */
    public int chunkX;
    /** The Z-coordinate of the chunk currently being decorated */
    public int chunkZ;
 //   public ChunkPrimer chunkprimer = new ChunkPrimer();
 //   public ChunkCoordIntPair chunkCoord;


//    public int block3PerChunk;

    public WorldGenGlistreMushroom glistreShroomGen = new WorldGenGlistreMushroom();
    public WorldGenSeaweed seaweedGen = new WorldGenSeaweed(3);
    public WorldGenFlowers redFlowerGen = new WorldGenFlowers((BlockFlower) Blocks.RED_FLOWER, BlockFlower.EnumFlowerType.POPPY);
    public WorldGenDoublePlant sunFlowerGen = new WorldGenDoublePlant();  
//    DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.SUNFLOWER);
//    public EmeraldGenerator emeraldGen = new EmeraldGenerator();
    public WorldGenCanopyTree  darkTreeGen = new WorldGenCanopyTree(false);//boolean is what? 

//	public MapGenGlistreVillage villageGenerator = new MapGenGlistreVillage();
	
    public int glistreshroomsPerChunk;
    public int seaweedPerChunk;
    public int flowersPerChunk;
    public int redFlowersPerChunk;
    public int sunFlowersPerChunk;
    public int darkTreesPerChunk;
    public int emeraldsPerChunk;
    public int waterlilyPerChunk;
    public int reedsPerChunk;
    public int villagesPerChunk;
    
    public BlockPos claypos;
	protected BlockPos field_150628_aC;

    
	public BiomeDecoratorGlistre()
	{
		super();
		
//		this.villageGenerator = new MapGenGlistreVillage();
//		this.chunkCoord = new ChunkCoordIntPair(chunkX, chunkZ);
//		this.villagesPerChunk = 2;
		
		seaweedGen = new WorldGenSeaweed(3);
		this.seaweedPerChunk = 16;

		glistreShroomGen = new WorldGenGlistreMushroom();
		glistreshroomsPerChunk = 2;
		
//		emeraldGen = new EmeraldGenerator();
//	    this.yellowFlowerGen = new WorldGenFlowers(Blocks.yellow_flower, BlockFlower.EnumFlowerType.DANDELION);
	    this.redFlowerGen = new WorldGenFlowers((BlockFlower)Blocks.RED_FLOWER, BlockFlower.EnumFlowerType.POPPY);
	    redFlowersPerChunk = 3;
	    this.sunFlowerGen = new WorldGenDoublePlant();
	    sunFlowersPerChunk = 10;
		emeraldsPerChunk = 20;
		this.flowersPerChunk = 1;
		this.waterlilyPerChunk = 100;
		this.darkTreeGen = new WorldGenCanopyTree(false);
		this.darkTreesPerChunk = 10;
		this.reedsPerChunk = 10;
		this.grassPerChunk = 10;

	}
	
/*	   @Override  //name conflicts with BiomeDecorator #decorate  NOTE: BiomeGenGase is in the constructor 
	   public void decorate(World worldIn, Random rand, BlockPos pos)
	   {
		   
	   }*/

	@Override
	public void decorate(World worldIn, Random rand, Biome biomeIn, BlockPos pos) {
//		super.decorate(worldIn, rand, biome, pos);//don't call super class it will be NPE

		
		if (this.currentWorld != null)
	        {
	            throw new RuntimeException("Already decorating!!");
	        }
	        else
	        {
            
	        	
	        if (this.decorating)
	        {
	        	throw new RuntimeException("Already decorating");
	        }
            else
            {
                this.chunkProviderSettings = ChunkProviderSettings.Factory.jsonToFactory(worldIn.getWorldInfo().getGeneratorOptions()).build();
            }
	        
	            this.currentWorld = worldIn;
	            this.randomGenerator = rand;
	            this.chunkPos = pos;
//	            this.chunk_X = pos.getX();
//	            this.chunk_Z = pos.getZ();
	            this.glistreShroomGen = new WorldGenGlistreMushroom();
	            this.genDecorations(biomeIn, worldIn, rand);
	            this.currentWorld = null;
	            this.randomGenerator = null;		
	        }
	}

	@Override
	protected void genDecorations(Biome biomeIn, World worldIn, Random rand) 
	{
//		super.genDecorations(biomeIn);//don't call
		MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(currentWorld, randomGenerator, chunkPos));

        int i;
        int j;
        int k;
        int i1;
        int l;
        //func...  became chunkPos?
        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(currentWorld, randomGenerator, chunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.BIG_SHROOM))
        for (int k2 = 0; k2 < this.glistreshroomsPerChunk; ++k2)
        {
            int l6 = this.randomGenerator.nextInt(16) + 8;
            int k10 = this.randomGenerator.nextInt(16) + 8;
            this.glistreShroomGen.generate(this.currentWorld, this.randomGenerator, this.currentWorld.getHeight(this.chunkPos.add(l6, 0, k10)));
        }
        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(currentWorld, randomGenerator, chunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.CLAY))
        for (i1 = 0; i1 < this.seaweedPerChunk; ++i1)
        {
            int l1 = this.randomGenerator.nextInt(16) + 8;
            int i6 = this.randomGenerator.nextInt(16) + 8;
            this.seaweedGen.generate(this.currentWorld, this.randomGenerator, this.currentWorld.getTopSolidOrLiquidBlock(this.chunkPos.add(l1, 0, i6)));
        }
        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(currentWorld, randomGenerator, chunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.FLOWERS))
        for (i1 = 0; i1 < this.flowersPerChunk; ++i1)
        {
            int l1 = this.randomGenerator.nextInt(16) + 8;
            int i6 = this.randomGenerator.nextInt(16) + 8;
            this.yellowFlowerGen.generate(this.currentWorld, this.randomGenerator, this.currentWorld.getTopSolidOrLiquidBlock(this.chunkPos.add(l1, 0, i6)));
        }
        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(currentWorld, randomGenerator, chunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.FLOWERS))
        for (i1 = 0; i1 < this.redFlowersPerChunk; ++i1)
        {
            int l1 = this.randomGenerator.nextInt(16) + 8;
            int i6 = this.randomGenerator.nextInt(16) + 8;
            this.redFlowerGen.generate(this.currentWorld, this.randomGenerator, this.currentWorld.getTopSolidOrLiquidBlock(this.chunkPos.add(l1, 0, i6)));
        }
        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(currentWorld, randomGenerator, chunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.LILYPAD))
        for (i1 = 0; i1 < this.waterlilyPerChunk; ++i1)
        {
            int l1 = this.randomGenerator.nextInt(16) + 8;
            int i6 = this.randomGenerator.nextInt(16) + 8;
            this.waterlilyGen.generate(this.currentWorld, this.randomGenerator, this.currentWorld.getTopSolidOrLiquidBlock(this.chunkPos.add(l1, 0, i6)));
        }
        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(currentWorld, randomGenerator, chunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.REED))
        for (i1 = 0; i1 < this.reedsPerChunk; ++i1)
        {
            int l1 = this.randomGenerator.nextInt(16) + 8;
            int i6 = this.randomGenerator.nextInt(16) + 8;
            this.reedGen.generate(this.currentWorld, this.randomGenerator, this.currentWorld.getTopSolidOrLiquidBlock(this.chunkPos.add(l1, 0, i6)));
        }
        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(currentWorld, randomGenerator, chunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.DEAD_BUSH))
        
        sunFlowerGen.setPlantType(BlockDoublePlant.EnumPlantType.SUNFLOWER);

        for (i1 = 0; i1 < sunFlowersPerChunk; ++i1)
        {
            int j1 = this.randomGenerator.nextInt(16) + 8;
            int k1 = this.randomGenerator.nextInt(16) + 8;
            int l1 = this.randomGenerator.nextInt(currentWorld.getHeight(chunkPos.add(j1, 0, k1)).getY() + 32);
            sunFlowerGen.generate(currentWorld, randomGenerator, chunkPos.add(j1, l1, k1));
        }
            
        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(currentWorld, randomGenerator, chunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.TREE))
        for (i1 = 0; i1 < darkTreesPerChunk; ++i1)
        {
        int j1 = this.randomGenerator.nextInt(16) + 8;
        int k1 = this.randomGenerator.nextInt(16) + 8;
        int l1 = this.randomGenerator.nextInt(currentWorld.getHeight(chunkPos.add(j1, 0, k1)).getY() + 32);
        darkTreeGen.generate(currentWorld, randomGenerator, chunkPos.add(j1, l1, k1));
           
	//	if (net.minecraftforge.event.terraingen.TerrainGen.getModdedMapGen(villageGenerator, net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.VILLAGE) != null)
/*		for (i1 = 0; i1 < villagesPerChunk; ++i1)
	    {
	    int j2 = this.randomGenerator.nextInt(16) + 8;
	    int k2 = this.randomGenerator.nextInt(16) + 8;
	    int l2 = this.randomGenerator.nextInt(128);	
	    //func_175796_a  ocean monument
	    //func_175795_b	 stronghold
	    // villageGenerator..generate(new GlistreChunkProvider(this.currentWorld, l2, generate), this.currentWorld, chunkX, chunkZ, chunkprimer);
	    villageGenerator.generateStructure(currentWorld, randomGenerator, chunkCoord);
	    }	*/

 /*        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(currentWorld, randomGenerator, chunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.CUSTOM))
        for (i1 = 0; i1 < this.emeraldsPerChunk; ++i1)
        {
            int l1 = this.randomGenerator.nextInt(16) + 8;
            int i6 = this.randomGenerator.nextInt(16) + 8;
            emeraldGen.generate(this.currentWorld, this.randomGenerator, this.currentWorld.getTopSolidOrLiquidBlock(this.chunkPos.add(l1, 0, i6)));
        }*/
        
        
  /*      int q;
        int r;
        int s;
        int t;
        
    	   BlockPos pos = new BlockPos(r, t, s);
        	//1.8.9 changed .func_180710_a to .setPlantType
           DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.SUNFLOWER);
            for (q = 0; q < 2; ++q)

            {
                r = this.randomGenerator.nextInt(16) + 8;
                s = this.randomGenerator.nextInt(16) + 8;
                //1.8.9 changed getHorizon to getHeight
                t = this.randomGenerator.nextInt(this.currentWorld.getHeight(pos.add(r, 0, s)).getY() + 32);
                DOUBLE_PLANT_GENERATOR.generate(this.currentWorld, this.randomGenerator, pos.add(r, t, s));
            }*/
        	//change to Terraingenbus?  oregenbus?
        	MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(currentWorld, randomGenerator, claypos));
        	MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(currentWorld, randomGenerator, field_150628_aC));
        	}
        	
        	}
	

        private int nextInt(int i) {
        	if (i <= 1)
        	return 0;
        	return this.randomGenerator.nextInt(i);

        	}
        

	
}
