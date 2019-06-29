package com.glistre.glistremod.biome;

import java.util.Random;

import com.glistre.glistremod.entities.blacktobie.EntityBlackTobo;
import com.glistre.glistremod.entities.guardian.EntityTobieSkel;
import com.glistre.glistremod.entities.wolf.EntityGlistreWolf;
import com.glistre.glistremod.init.BiomeRegistry;
import com.glistre.glistremod.init.BlockRegistry;
import com.glistre.glistremod.worldgen.BlockGenMesaGold;
import com.glistre.glistremod.worldgen.CabinChestGen;
import com.glistre.glistremod.worldgen.EmeraldGenerator;
import com.glistre.glistremod.worldgen.FirstBlockGen;
import com.glistre.glistremod.worldgen.TobyKingTowerGen;
import com.glistre.glistremod.worldgen.WorldGenGlistreMushroom;
import com.glistre.glistremod.worldgen.WorldGenSeaweed;
import com.google.common.base.Predicate;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockFlower.EnumFlowerType;
import net.minecraft.block.BlockTallGrass.EnumType;
import net.minecraft.block.BlockYellowFlower;
import net.minecraft.block.state.IBlockState;
//import net.minecraft.block.state.pattern.BlockHelper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
//import net.minecraft.world.biome.BiomeGenForest;
//import net.minecraft.world.biome.BiomeGenPlains;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigMushroom;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenClay;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenTaiga2;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.world.BlockEvent;

public class GlistreBiome extends Biome
{
	//something sunflower field
//	protected BlockPos sunflowerpos;
//	protected BlockPos redflowerpos;
	
//	protected boolean enableSunflowers;
	protected boolean field_150628_aC;
	BiomeDecoratorGlistre glistreBiomeDecorator;
    private WorldGenerator theWorldGenerator;


    public GlistreBiome(BiomeProperties properties)
    {
        super(properties);
 //removed 1.10       this.setBiomeName("Glistering Biome");
        theBiomeDecorator = createBiomeDecorator();
//        this.theWorldGenerator = new WorldGenMinable((IBlockState) BlockRegistry.silver_ore_1.getDefaultState(), 20);
//        this.theWorldGenerator = new WorldGenMinable((IBlockState) BlockRegistry.silver_block_1.getDefaultState(), 8);

        this.topBlock = (IBlockState) Blocks.GRASS.getDefaultState();
//		this.fillerBlock = (IBlockState) Blocks.dirt.getDefaultState();
        this.theBiomeDecorator = new BiomeDecoratorGlistre(); //need this or get cannot cast to DeferredBiomeGenerator
		glistreBiomeDecorator = new BiomeDecoratorGlistre();
		glistreBiomeDecorator = (BiomeDecoratorGlistre)theBiomeDecorator;
		glistreBiomeDecorator.glistreshroomsPerChunk = 2;
		glistreBiomeDecorator.seaweedPerChunk = 16;
		glistreBiomeDecorator.emeraldsPerChunk = 20;
		
//		glistreBiomeDecorator.villagesPerChunk = 2;

//		this.theBiomeDecorator = new BiomeDecorator();//adds the flowers, etc. but removes the customBiomeDecorator values if using theBiomeDecorator!!
//		this.glistreBiomeDecorator.clayPerChunk = 2; 
		

        this.glistreBiomeDecorator.waterlilyPerChunk = 100; 
        this.glistreBiomeDecorator.flowersPerChunk = 1;
        this.glistreBiomeDecorator.redFlowersPerChunk = 3;
        this.glistreBiomeDecorator.sunFlowersPerChunk = 10;
    
        this.glistreBiomeDecorator.reedsPerChunk = 10;
        
        this.glistreBiomeDecorator.darkTreesPerChunk = 10;
        this.glistreBiomeDecorator.grassPerChunk = 10;


//        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableMonsterList.clear(); 
        this.spawnableCreatureList.add(new SpawnListEntry(EntityOcelot.class, 10, 1, 1));      
        this.spawnableCreatureList.add(new SpawnListEntry(EntityHorse.class, 10, 2, 3));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityPig.class, 1, 2, 5));     
        this.spawnableMonsterList.add(new SpawnListEntry(EntitySkeleton.class, 1, 1, 1));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityZombie.class, 1, 1, 1));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityGlistreWolf.class, 20, 1, 3));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityTobieSkel.class, 20, 1, 2));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityBlackTobo.class, 15, 1, 2));
	//	theWorldGenerator = new WorldGenFlowers((BlockFlower) Blocks.red_flower.getDefaultState(), BlockFlower.EnumFlowerType.POPPY);
        this.addDefaultFlowers();
        this.addFlower(Blocks.YELLOW_FLOWER.getDefaultState().withProperty(Blocks.YELLOW_FLOWER.getTypeProperty(), BlockFlower.EnumFlowerType.DANDELION), 8);
        this.addFlower(Blocks.RED_FLOWER.getDefaultState().withProperty(Blocks.RED_FLOWER.getTypeProperty(), BlockFlower.EnumFlowerType.POPPY), 8);

 // adds sunflowers but too crazy and too much tall grass and trees, not enough height       
 //       this.addFlower(Blocks.double_plant.getDefaultState().withProperty(Blocks.double_plant.VARIANT, BlockDoublePlant.EnumPlantType.SUNFLOWER), 6);
      
//        this.addFlower(Blocks.pumpkin, 7, 20);       
 //  1.8 pumpkins??     this.addFlower(Blocks.pumpkin.getDefaultState().withProperty(((BlockFlower) Blocks.pumpkin).getTypeProperty(), BlockFlower.EnumFlowerType.DANDELION), 30);

        
 //       this.setColor(0x7F007F); purple
//        this.setColor(0x443333); cowboy.lavender
        this.canRain();
 //       this.setColor(0x003000);//Myrtle == green
//         this.setColor(0xffd700);//gold
 //        this.setColor(0xc5b358);//vegas gold #c5b358
  //      this.setColor(0xF7D365);//brown savanna 
  //      this.setColor(0xf9de4c);//yellow gold
       
 //       this.setEnableSnow();//1.10.2 changed to setSnowEnabled
//        this.field_150628_aC = true; //need for plains biome
//        this.enableSnow = true;
   //     this.enableRain = true;
        this.isHighHumidity();
        
        //changed from .75F, 1.1 F to 1.1, 2.0F when plains biome
  //      this.setMinMaxHeight(.75F, 1.7F);
  //      this.setHeight(Biome.height_LowHills);
        //first parameter is temp .2F and rain looks like snow, second parameter is rainfall 0F none .5F is normal
 //       this.setTemperatureRainfall(0.75F, 1.0F);
        
       
    }
   /*   @Override   
    public int getFoliageColorAtPos(BlockPos pos){
		return color;
	//	return 6937835;  dark burned up color
    }*/
    @Override   
    public int getGrassColorAtPos(BlockPos pos){
//		return color;
		return 0xf9de4c;
	//	return 6937835;  dark burned up color
    }
/*	@Override
	public void addFlower(IBlockState state, int weight)
	{
	 this.flowers.add(new FlowerEntry(state, weight));
	}
/*	@Override
    public boolean darkenSkyDuringRain() 
    {        
    	return false;     
    }*/
   @Override
   //canSpawnLightningBolt
    public boolean canRain() 
    { 
        return this.isSnowyBiome() ? true : this.canRain(); 
    } 
   
/*	@Override
	public BiomeGenBase.TempCategory getTempCategory()
	{
		return TempCategory.COLD;
	}*/
   
/*    @Override
	private void setMinMaxHeight(float f, float g) 
	{
	
	}*/
//    @SideOnly(Side.CLIENT)
    public int getBiomeGrassColor()
    {
    	return 0x443333;
    }
 /*   @SideOnly(Side.CLIENT)
    public int getBiomeFoliageColor()
    {
    	return 0x443333;
    }*/
 //   @SideOnly(Side.CLIENT)
    public int getSkyColorByTemp(float par1)
    {
//    	return 0x443333; //grey sky
    	//gold/grey sky
    	return 0xA0A016;
    }
	
    /**
     * Gets a WorldGen appropriate for this biome.
    */ 
    
 /*      public WorldGenerator getRandomWorldGenForGrass(Random random){
       return random.nextInt(2) == 0 ?  this.theWorldGenerator : new WorldGenTallGrass(EnumType.GRASS);     
   }   */
 /*   public WorldGenAbstractTree genBigTreeChance(Random random)
    {
        return (WorldGenAbstractTree)(this.field_150632_aF == 3 && random.nextInt(3) > 0 ? field_150631_aE : (this.field_150632_aF != 2 && random.nextInt(5) != 0 ? this.worldGeneratorTrees : field_150630_aD));
    }*/

 /*      public WorldGenerator getRandomWorldGenForTrees(Random rand){
       return(WorldGenerator)(rand.nextInt(1) == 0 ? this.worldGeneratorBigTree : (rand.nextInt(6) == 0 ? this.worldGeneratorBigTree : rand.nextInt(20) == 0 ? this.worldGeneratorTrees : this.worldGeneratorTrees));
    }*/
    
 /*   public void decorate(World worldIn, Random p_180624_2_, BlockPos p_180624_3_)
    {
        int i;
        int j;
        int k;
        int l;

        if (this.field_150632_aF == 3)
        {
            for (i = 0; i < 4; ++i)
            {
                for (j = 0; j < 4; ++j)
                {
                    k = i * 4 + 1 + 8 + p_180624_2_.nextInt(3);
                    l = j * 4 + 1 + 8 + p_180624_2_.nextInt(3);
                    BlockPos blockpos1 = worldIn.getHorizon(p_180624_3_.add(k, 0, l));

                    if (p_180624_2_.nextInt(20) == 0)
                    {
                        WorldGenBigMushroom worldgenbigmushroom = new WorldGenBigMushroom();
                        worldgenbigmushroom.generate(worldIn, p_180624_2_, blockpos1);
                    }
                    else
                    {
                        WorldGenAbstractTree worldgenabstracttree = this.genBigTreeChance(p_180624_2_);
                        worldgenabstracttree.func_175904_e();

                        if (worldgenabstracttree.generate(worldIn, p_180624_2_, blockpos1))
                        {
                            worldgenabstracttree.func_180711_a(worldIn, p_180624_2_, blockpos1);
                        }
                    }
                }
            }
        }*/
    
    
    
/*   @Override
    public BiomeGenBase createMutatedBiome(int num)
    {
        BiomeGenPlains biomegenplains = new BiomeGenPlains(this);
        biomegenplains.setBiomeName("Glistering Sunflower Plains");
//        biomegenplains.field_150628_aC = true;
        biomegenplains.setColor(9286496);
        biomegenplains.field_150609_ah = 14273354;
        return biomegenplains;
    }*/
    
   @SubscribeEvent
	public void DecorateBiomeEvent(net.minecraftforge.event.terraingen.DecorateBiomeEvent event)
	{
		BlockPos pos = event.getPos();
		World world = event.getWorld();
		Random random = event.getRand();
		
		int xCoord = pos.getX();
		int zCoord = pos.getZ();

		BlockPos pos1 = new BlockPos(xCoord+2, 0, zCoord+2);
		
		if (random.nextInt(500)==0) (new CabinChestGen()).generate(world, random, pos1);
	}
   
   
    //replaces GlistreBlock with SilverBlock copied from BlockEmeraldClass
   @Override  //name conflicts with BiomeDecorator #decorate
   public void decorate(World worldIn, Random rand, BlockPos pos)
   {
       super.decorate(worldIn, rand, pos);//weird if you don't call then sunflowers show up but not anything form BiomeDecoratorGlistre , if you do call nothing shows

       {

    	  
           //next two for loop inside for loop (double for loop) of code is update to 1.8

    /*	   for (int chunkX = 0; chunkX < 4; ++chunkX) {
   			for (int chunkZ = 0; chunkZ < 4; ++chunkZ) {
           int k = 3 + rand.nextInt(6);
           int l;    
           int m;
           int n;
           
    	   if(worldIn.getChunkFromBlockCoords(pos) != null){
    	       

	        for (k = 0; k < 7; ++k)
	        	{
	            l = chunkX + rand.nextInt(16);
	            m = rand.nextInt(64);
	            n = chunkZ + rand.nextInt(16);
	            this.theWorldGenerator.generate(worldIn, rand, pos);
	        	}
	    	   	}*/
	   			
	            
       net.minecraftforge.common.MinecraftForge.ORE_GEN_BUS.post(new net.minecraftforge.event.terraingen.OreGenEvent.Pre(worldIn, rand, pos));
       {
       WorldGenerator gold = new BlockGenMesaGold();
       if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, rand, gold, pos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.GOLD))
           gold.generate(worldIn, rand, pos);

       for (int i = 0; i < 7; ++i)
       {
           int j1 = rand.nextInt(16);
           int k1 = rand.nextInt(64);
           int l1 = rand.nextInt(16);
           if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, rand, gold, pos.add(j1, k1, l1), net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.GOLD))
           new BlockGenMesaGold().generate(worldIn, rand, pos.add(j1, k1, l1));
       }
       net.minecraftforge.common.MinecraftForge.ORE_GEN_BUS.post(new net.minecraftforge.event.terraingen.OreGenEvent.Post(worldIn, rand, pos));
   		}
       
       net.minecraftforge.common.MinecraftForge.ORE_GEN_BUS.post(new net.minecraftforge.event.terraingen.OreGenEvent.Pre(worldIn, rand, pos));
       {
       WorldGenerator silverore = new FirstBlockGen();
       if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, rand, silverore, pos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.CUSTOM))
           silverore.generate(worldIn, rand, pos);
       for (int i = 0; i < 7; ++i)
       {
           int j1 = rand.nextInt(16);
           int k1 = rand.nextInt(64);
           int l1 = rand.nextInt(16);
           if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, rand, silverore, pos.add(j1, k1, l1), net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.CUSTOM))
           silverore.generate(worldIn, rand, pos.add(j1, k1, l1));
       }
       net.minecraftforge.common.MinecraftForge.ORE_GEN_BUS.post(new net.minecraftforge.event.terraingen.OreGenEvent.Post(worldIn, rand, pos));

       }
       //next section for big shrooms
/*       if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, chunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.BIG_SHROOM))
       for (int k2 = 0; k2 < this.glistreshroomsPerChunk; ++k2)
       {
           int l6 = rand.nextInt(16) + 8;
           int k10 = rand.nextInt(16) + 8;
           this.glistreShroomGen.generate(worldIn, rand, worldIn.getHeight(this.chunkPos.add(l6, 0, k10)));
       }*/
       
/*	       int m;
	       int n;
	       int o;
	       int p;
       		for (m = 0; m < 3; ++m)
       		{
       		for (n = 0; n < 5; ++n)
 //      	for (n = 0; n < 7; ++n)
       		{
        	   o = rand.nextInt(3) + 8;
               p = rand.nextInt(3) + 8;
               o = m * 1 + 1 + 3 + rand.nextInt(3);
               p = n * 1 + 1 + 3 + rand.nextInt(3);
               BlockPos blockpos1 = worldIn.getHeight(pos.add(o, 0, p));
//               if (net.minecraftforge.event.terraingen.TerrainGen.(worldIn, rand, blockpos1, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.))

               if (rand.nextInt(3) == 0)
               {
                   WorldGenGlistreMushroom worldgenglistremushroom = new WorldGenGlistreMushroom();
                   worldgenglistremushroom.generate(worldIn, rand, blockpos1);
               }
       		} 
       		}*/


       int q;
       int r;
       int s;
       int t;
 //     if (this.field_150628_aC)
  //    {
       	//1.8.9 changed .func_180710_a to .setPlantType
 /*          DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.SUNFLOWER);
           for (q = 0; q < 2; ++q)

           {
               r = rand.nextInt(16) + 8; //that's a chunk and a half, baby
               s = rand.nextInt(16) + 8;
               //1.8.9 changed getHorizon to getHeight
               t = rand.nextInt(worldIn.getHeight(pos.add(r, 0, s)).getY() + 32);
               DOUBLE_PLANT_GENERATOR.generate(worldIn, rand, pos.add(r, t, s));
           }
       	  MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(worldIn, rand, sunflowerpos));  */  
       	  
 /*          if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, pos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.FLOWERS))
               for (q = 0; q < 8; ++q)
               {
                   int r1 = rand.nextInt(16) + 8;
                   int s1 = rand.nextInt(16) + 8;
                   t = rand.nextInt(worldIn.getHeight(pos.add(r1, 0, s1)).getY() + 32);

                   new WorldGenFlowers((BlockFlower) Blocks.red_flower.getDefaultState(), BlockFlower.EnumFlowerType.POPPY).generate(worldIn, rand, pos.add(r1, t, s1));
                   //(worldIn, rand, worldIn.getTopSolidOrLiquidBlock(pos.add(r1, 0, s1)));
               }
      }

 	  	MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(worldIn, rand, redflowerpos));*/
	  
   			}

   			
       }
       
   
    	   
 /*  public void decorate(World worldIn, Random random, BlockPos blockPos)
    {
 //       super.decorate(worldIn, random, blockPos);// causing Already Decorating crash but no trees and regular flowers without it
        
        //next two bizarre lines of code is update to 1.8
        for (int chunkX = 0; chunkX < 4; ++chunkX) {
			for (int chunkZ = 0; chunkZ < 4; ++chunkZ) {
        int k = 3 + random.nextInt(6);
        int l;    
        int i1;
        int j1;
        
        int m;
        int n;
        int o;
        int p;
        
        int q;
        int r;
        int s;
        int t;
     

        
        //hopefully next if statement wraps around #generate so decorate event does not try to generate blocks before the chunk exists which would make y negative
  //      for example and create the 'Already decorating!' error and crash        
  //     try{
    	   if(worldIn.getChunkFromBlockCoords(blockPos) != null){
       

        for (k = 0; k < 7; ++k)
        	{
            l = chunkX + random.nextInt(16);
            i1 = random.nextInt(64);
            j1 = chunkZ + random.nextInt(16);
            this.theWorldGenerator.generate(worldIn, random, blockPos);
        	}
    	   }
    	    

       //next section add silver ore
       for (l = 0; l < k; ++l)
       {
           i1 = chunkX + random.nextInt(16);
           j1 = random.nextInt(28) + 4;
           int k1 = chunkZ + random.nextInt(16);

//           if (worldIn.getBlockState(blockPos).isReplaceableOreGen(worldIn, i1, j1, k1, BlockRegistry.glistre_block_1))
           if (worldIn.getBlockState(blockPos).getBlock().isReplaceableOreGen(worldIn, blockPos, (BlockHelper.forBlock(BlockRegistry.silver_ore_1))))
           {
               worldIn.setBlockState(blockPos, (IBlockState) BlockRegistry.silver_block_1.getDefaultState(), 2);
           }          
       }
        //next section for big shrooms
        for (m = 0; m < 3; ++m)
        {
            for (n = 0; n < 5; ++n)
            {
                o = m * 1 + 1 + 3 + random.nextInt(3);
                p = n * 1 + 1 + 3 + random.nextInt(3);
                BlockPos blockpos1 = worldIn.getHeight(blockPos.add(o, 0, p));

                if (random.nextInt(3) == 0)
                {
                    WorldGenGlistreMushroom worldgenglistremushroom = new WorldGenGlistreMushroom();
                    worldgenglistremushroom.generate(worldIn, random, blockpos1);
                }
            }
        } 
        if (this.field_150628_aC)
        {
        	//1.8.9 changed .func_180710_a to .setPlantType
            DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.SUNFLOWER);

            for (q = 0; q < 2; ++q)
            {
                r = random.nextInt(16) + 8;
                s = random.nextInt(16) + 8;
                //1.8.9 changed getHorizon to getHeight
                t = random.nextInt(worldIn.getHeight(blockPos.add(r, 0, s)).getY() + 32);
                DOUBLE_PLANT_GENERATOR.generate(worldIn, random, blockPos.add(r, t, s));
            }
        }

    
        }}
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
       
    }*/
    @Override
    public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal)
    {
        this.topBlock = Blocks.GRASS.getDefaultState();
 //       this.fillerBlock = Blocks.dirt.getDefaultState();
 //1.10 changed .isEqualTo to equals
        if ((noiseVal < -1.0D || noiseVal > 2.0D) && this.equals(BiomeRegistry.biomeGlistre))
        {
            this.topBlock = Blocks.GRASS.getDefaultState();
//            this.fillerBlock = Blocks.dirt.getDefaultState();
        }
 
        this.generateBiomeTerrain(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
    }
    
	@Override
	public BiomeDecorator createBiomeDecorator(){
		return new BiomeDecoratorGlistre();
	}
    
}
    

 /*      
catch (Exception err){Throwable cause = err.getCause();
if (err.getMessage() != null && err.getMessage().equals("Already decorating!!") ||
        (cause != null && cause.getMessage() != null && cause.getMessage().equals("Already decorating!!") || cause != null  && cause.getMessage().equals("NullPointerException")))
        //Exception caught executing FutureTask: java.util.concurrent.ExecutionException: java.lang.NullPointerException
    //    java.util.concurrent.ExecutionException: java.lang.NullPointerException))
{
    ;
} else {
    err.printStackTrace();
}
} */  
