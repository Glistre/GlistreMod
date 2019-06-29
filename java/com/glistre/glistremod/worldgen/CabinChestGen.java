//MANY THANKS TO sky01 FOR THE GREAT COMPLEX STRUCTURE TUTORIAL FOR 1.8.9!


package com.glistre.glistremod.worldgen;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.ArrayUtils;

import com.glistre.glistremod.blocks.GlistreChest;
import com.glistre.glistremod.entities.blacktobie.EntityBlackTobo;
import com.glistre.glistremod.entities.guardian.EntityTobieSkel;
//import com.glistre.glistremod.entities.king.EntityTobieKing;
import com.glistre.glistremod.init.BiomeRegistry;
import com.glistre.glistremod.init.BlockRegistry;
import com.glistre.glistremod.init.GlistreEntityRegistry;
import com.glistre.glistremod.init.ItemRegistry;
import com.glistre.glistremod.lib.Defaults;
import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator; //added 1.8.9

public class CabinChestGen extends WorldGenerator implements IWorldGenerator
{
	@Override
    public void generate(Random random, int chunkX, int chunkZ,  World worldIn, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
       
		// these are important!
				int blockX = chunkX * 16;
				int blockZ = chunkZ * 16;

				switch(worldIn.provider.getDimension())
        {
        
        //case -1 = Nether
            case -1: 
            	generateSurface(random, blockX, blockZ, worldIn, chunkGenerator, chunkProvider);
                break;
        //case 0 = Overworld
            case 0:
            	generateSurface(random, blockX, blockZ, worldIn, chunkGenerator, chunkProvider);
            	break;
        //case 1 = End
            case 1:
            	generateSurface(random, blockX, blockZ, worldIn, chunkGenerator, chunkProvider);
            	break;         	
        //default case e.g. Mystcraft ..others
            default:
            	generateSurface(random, blockX, blockZ, worldIn, chunkGenerator, chunkProvider);
            	break;
        }              
    }
	
	/** BLUEPRINTS **/
	// Format: int[][] { {distanceRight, distanceUp, distanceBack} } 
	private final int[][] logsPos = new int[][] 
		{
			// corners (first layer)
			{0,0,0},{0,1,0},	// front-left
			{4,0,0},{4,1,0},	// front-right
			{4,0,4},{4,1,4},	// back-right
			{0,0,4},{0,1,4},	// back-left
			// upper trim (first part of roof)
			{0,2,0},{1,2,0},{2,2,0},{3,2,0},{4,2,0},	// front
			{0,2,4},{1,2,4},{2,2,4},{3,2,4},{4,2,4},	// back
			{0,2,1},{0,2,2},{0,2,3},	// left
			{4,2,1},{4,2,2},{4,2,3},	// right
			// roof
			{1,3,1},{2,3,1},{3,3,1},
			{1,3,2},{2,3,2},{3,3,2},
			{1,3,3},{2,3,3},{3,3,3}
		};
	private final int[][] planksPos = new int[][]
		{
			// floor
			{0,-1,0},{1,-1,0},{2,-1,0},{3,-1,0},{4,-1,0},
			{0,-1,1},{1,-1,1},{2,-1,1},{3,-1,1},{4,-1,1},
			{0,-1,2},{1,-1,2},{2,-1,2},{3,-1,2},{4,-1,2},
			{0,-1,3},{1,-1,3},{2,-1,3},{3,-1,3},{4,-1,3},
			{0,-1,4},{1,-1,4},{2,-1,4},{3,-1,4},{4,-1,4},
			// walls
			//// front
			{1,0,0},{2,0,0},{3,0,0},	// I want a door here, but I place
			{1,1,0},{2,1,0},{3,1,0},	// planks anyway for consistency
			//// right
			{4,0,1},{4,0,2},{4,0,3},
			{4,1,1},{4,1,2},{4,1,3},
			//// back
			{1,0,4},{2,0,4},{3,0,4},
			{1,1,4},{2,1,4},{3,1,4},
			//// left
			{0,0,1},{0,0,2},{0,0,3},
			{0,1,1},{0,1,2},{0,1,3}
		};
	private final int[] doorBottomPos = new int[] {2,0,0};
	private final int[] doorTopPos = new int[] {2,1,0};
	private final int[] chestPos = new int[] {1,0,3};
	private final int[] workbenchPos = new int[] {2,0,3};
	private final int[] furnacePos = new int[] {3,0,3};
	private final int[] torchPos = new int[] {2,0,2};
	

	////////////////////TODO:  Put this in Loot Event
	/** CHEST CONTENTS **/
/*	private final WeightedRandomChestContent[] chestContents =
		{
			// parameters: (Item, metadata, min, max, weight)
			new WeightedRandomChestContent(Items.mushroom_stew, 0, 2, 4, 20),			
			new WeightedRandomChestContent(Items.fish, 0, 2, 8, 20),
			new WeightedRandomChestContent(Items.iron_axe, 0, 1, 1, 30),
			new WeightedRandomChestContent(Items.gold_nugget, 0, 3, 15, 18),
			new WeightedRandomChestContent(Items.coal, 0, 1, 4, 12),			
			new WeightedRandomChestContent(new ItemStack(Item.getItemFromBlock(Blocks.coal_ore)), 1, 2, 18),	
			new WeightedRandomChestContent(new ItemStack(Blocks.bed, 1), 1, 5, 18),
			new WeightedRandomChestContent(ItemRegistry.glistre_dust, 0, 1, 11, 25),
			new WeightedRandomChestContent(ItemRegistry.glistre_chestplate_1, 0, 1, 1, 18),
			new WeightedRandomChestContent(ItemRegistry.chain_helmet_1, 0, 1, 1, 18),
			new WeightedRandomChestContent(ItemRegistry.poison_protection, 0, 1, 2, 16),
			new WeightedRandomChestContent(ItemRegistry.vomitus, 0, 1, 1, 10),
			new WeightedRandomChestContent(GlistreEntityRegistry.tobie_worst_projectile_1, 0, 2, 4, 18),		
		};*/

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos corner) 
	{
		boolean b = worldIn.getBiomeForCoordsBody(corner).getBiomeName().equals("Glistering Biome");
		if(b == true){
		if (canSpawnHere(worldIn, corner))
		{
			// figure out each IBlockState we will use
			IBlockState spruceLogs = Blocks.LOG.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata());
			IBlockState sprucePlank = Blocks.PLANKS.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata());
			IBlockState doorLower = Blocks.OAK_DOOR.getDefaultState().withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.LOWER);
			IBlockState doorUpper = Blocks.OAK_DOOR.getDefaultState().withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.UPPER);
			IBlockState craftingTable = Blocks.CRAFTING_TABLE.getDefaultState();
			IBlockState furnace = Blocks.FURNACE.getDefaultState();
			/*All I have to do is tell my code to add positive offset for this rotation, 
			 * negative offset for that rotation, and my structure can then face north, east, south, or west randomly. Much more natural and useful.

			One more note: if I rotated anything, I would also have to rotate the door (and furnace). 
			I would do this the same way I set the HALF value of the IBlockState, but by setting the FACING value instead.*/			
			IBlockState chest = BlockRegistry.glistre_chest_gold.getDefaultState().withProperty(GlistreChest.FACING, EnumFacing.NORTH);
			IBlockState torch = Blocks.TORCH.getDefaultState();

			// build the layers using the arrays
			buildLayer(worldIn, corner, logsPos, spruceLogs);
			buildLayer(worldIn, corner, planksPos, sprucePlank);

			// place the other features LAST
			placeBlock(worldIn, corner, doorBottomPos, doorLower);
			placeBlock(worldIn, corner, doorTopPos, doorUpper);
			placeBlock(worldIn, corner, workbenchPos, craftingTable);
			placeBlock(worldIn, corner, furnacePos, furnace);
			placeBlock(worldIn, corner, torchPos, torch);

			// I saved the chest for last
			// here's where we use the WeightedRandomChestContent[]
			placeBlock(worldIn, corner, chestPos, chest); 
			// here we have to do some back-math...
			// we need to know the actual location of the TileEntityChest,
			// not just its offsets from the corner
			BlockPos actualPos = corner.add(chestPos[0], chestPos[1], chestPos[2]);
			// right, so we get the TileEntityChest here
			TileEntityChest chestTE = (TileEntityChest)worldIn.getTileEntity(actualPos);
			if(chestTE != null)
			{
				////////////TODO:  put this section in loot event
/*				// now we add the contents we declared earlier (if the TE is not null)
				WeightedRandomChestContent.generateChestContents(worldIn.rand, Lists.newArrayList(chestContents), chestTE, 6);
				
				//added from original 1.8 code 
				ItemRegistry instance = new ItemRegistry();
	        	ItemStack ancientBook = instance.getAncientBook();
	        	chestTE.setInventorySlotContents(0, ancientBook);
	        	// put a stack  in the second slot of the chest
	        	chestTE.setInventorySlotContents(2, new ItemStack(ItemRegistry.glistre_food_2, 6));
	        	chestTE.setInventorySlotContents(3, new ItemStack(ItemRegistry.glistre_sword, 1));
	        	chestTE.setInventorySlotContents(5, new ItemStack(ItemRegistry.nausea_protection, 2));
	        	chestTE.setInventorySlotContents(6, new ItemStack(ItemRegistry.sceptre_1, 1));*/
	        	
	        	
	        	//display chat chest location section   
		        EntityPlayer entityplayer;
				List<EntityPlayer> playerList = worldIn.getEntitiesWithinAABB(EntityPlayer.class, chestTE.getRenderBoundingBox().expand(255.0D, 95.0D,55.0D));

				Iterator<EntityPlayer> i1 = playerList.iterator();
				
	                  while (i1.hasNext())
	                  {
	                        entityplayer = (EntityPlayer)i1.next();  			

//	 			if(!(world.isRemote && entityplayer.getCurrentEquippedItem() !=null && entityplayer.getCurrentEquippedItem().isItemEqual)))	
	                  if(!(worldIn.isRemote && entityplayer !=null))
	                        entityplayer.addChatComponentMessage(
	                        		new TextComponentString(TextFormatting.DARK_GREEN + "Golden Chest in location" 
	                        + TextFormatting.DARK_RED + " X: " + (int)Math.round(actualPos.getX()) 
	                        + TextFormatting.GOLD + " | Y: " + (int)Math.round(actualPos.getY()) 
	                        + TextFormatting.DARK_AQUA +" | Z: " + (int)Math.round(actualPos.getZ())));
			   }                                    
			}
			}
			generate2(worldIn, rand, corner);
			// debug:
	//		System.out.println("Built a cabin starting at " + corner + "!");
	//		return true;
	}
//		} else System.out.println("Sorry, can't spawn a cabin at " + corner); //modified
//		return false;														  //modified
		return b;
		
	}
	public boolean generate2(World worldIn, Random rand, BlockPos corner) 
	{
        int x = corner.getX();
        int z = corner.getZ();
        int y = corner.getY();
        
        if(!(worldIn.isRemote) && canSpawnTobie(worldIn, corner))
	{
        //50/50 chance of good/bad Tobie Spawn
//        if(rand.nextFloat() < 0.50F) //should be 50% (2) + 1 == 1 should also be 50% chance
        if((rand.nextInt(2) + 1)== 1 && worldIn.getWorldTime() < 12500) {
        //all coordinates must match #canSpawnTobie and possibly each other
	        EntityBlackTobo badtobie = new EntityBlackTobo(worldIn);
			badtobie.setPosition(x + 2, y + 1, z + 8);
		//	entity.setLocationAndAngles(i, j, k, yaw, pitch)
		 	badtobie.setCurrentItemOrArmor(0, new ItemStack(ItemRegistry.tobie_gun_1));
			badtobie.setLocationAndAngles(x + 2, y + 1, z + 8, 0.0F , 0.0F);
			worldIn.spawnEntityInWorld(badtobie); 
			badtobie.onUpdate();
//        System.out.println("Bad Tobie near cabin location X: " +  + (int)Math.round(badtobie.posX) + " | Y: " + (int)Math.round(badtobie.posY) + " | Z: " + (int)Math.round(badtobie.posZ));
	} else {
		    EntityTobieSkel goodtobie = new EntityTobieSkel(worldIn);	
			goodtobie.setPosition(x + 2, y + 1, z + 8);	
		 	goodtobie.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(ItemRegistry.tobie_gun_1));
			//goodtobie.setCurrentItemOrArmor(0, new ItemStack(ItemRegistry.tobie_gun_1));
			goodtobie.setLocationAndAngles(x + 2, y + 1, z + 8, 0.0F , 0.0F);
			worldIn.spawnEntityInWorld(goodtobie); 
			goodtobie.onUpdate();	
	}
	}
        
	return true;
	}
	

/*	public static void placeSpawner(World world, int x, int y, int z, String mob) {
		BlockPos pos = new BlockPos(x, y, z);
		EntityBlackTobo badtobie = new EntityBlackTobo(world);
		TileEntityMobSpawner theSpawner = (TileEntityMobSpawner)world.getTileEntity(pos);
		MobSpawnerBaseLogic logic = theSpawner.getSpawnerBaseLogic();
		logic.setEntityName(mob);
	}*/

	// use an int[][] to place a lot of one block at once
	private void buildLayer(World world, BlockPos frontLeftCorner, int[][] blockPositions, IBlockState toPlace)
	{
		// iterate through the entire int[][]
		for(int[] coord : blockPositions)
		{
			placeBlock(world, frontLeftCorner, coord[0], coord[1], coord[2], toPlace);
		}
	}
	
	/** Helper Method **/
	private void placeBlock(World world, BlockPos frontLeftCorner, int[] offsets, IBlockState toPlace)
	{
		placeBlock(world, frontLeftCorner, offsets[0], offsets[1], offsets[2], toPlace);
	}

	/** Places a block using corner position and offsets **/
	private void placeBlock(World world, BlockPos frontLeftCorner, int offsetX, int offsetY, int offsetZ, IBlockState toPlace)
	{	
		// figure out where that block is relative to the corner
		BlockPos placePos = frontLeftCorner.add(offsetX, offsetY, offsetZ);
		world.setBlockState(placePos, toPlace, 2);
	}

	private boolean canSpawnHere(World world, BlockPos posAboveGround)
	{
		//posAboveGround =  new BlockPos(posAboveGround.getX(), posAboveGround.getY(), posAboveGround.getZ());

		// check all the corners to see which ones are replaceable
		boolean corner1Air = canReplace(world, posAboveGround);
		boolean corner2Air = canReplace(world, posAboveGround.add(4, 0, 0));
		boolean corner4Air = canReplace(world, posAboveGround.add(0, 0, 4));
		boolean corner3Air = canReplace(world, posAboveGround.add(4, 0, 4));
		
		// if Y > 20 and all corners pass the test, it's okay to spawn the structure
		return posAboveGround.getY() > 20 && corner1Air && corner2Air && corner3Air && corner4Air;
	}
	
	private boolean canSpawnTobie(World world, BlockPos posAboveGround)
	{
		//posAboveGround =  new BlockPos(posAboveGround.getX(), posAboveGround.getY(), posAboveGround.getZ());

		// check all the corners to see which ones are replaceable
		boolean center1Air = canReplaceTobie(world, posAboveGround.add(2, 1, 8));
	//	boolean corner2Air = canReplace(world, posAboveGround.add(4, 0, 0));
	//	boolean corner4Air = canReplace(world, posAboveGround.add(0, 0, 4));
	//	boolean corner3Air = canReplace(world, posAboveGround.add(4, 0, 4));
		
		// if Y > 20 and all corners pass the test, it's okay to spawn the structure
		return posAboveGround.getY() > 20 && center1Air;
	}

	//next two methods 1.10.2 added block.getDefaultState() to #getMaterial
	private boolean canReplace(World world, BlockPos pos)
	{
		Block block = world.getBlockState(pos).getBlock();
		Material material = block.getMaterial(block.getDefaultState());
		// we think it's replaceable if it's air / liquid / snow, plants, or leaves 
		return material.isReplaceable() || material == Material.PLANTS || material == Material.LEAVES;
	}
	
	private boolean canReplaceTobie(World world, BlockPos pos)
	{
		Block block = world.getBlockState(pos).getBlock();
		Material material = block.getMaterial(block.getDefaultState());
		// we think it's replaceable if it's air / liquid / snow, plants, or leaves 
		return material.isReplaceable() || material == Material.AIR|| material == Material.WATER;
	}

		//was #generateSurface in 1.8

	public void generateSurface(Random random, int blockX, int blockZ, World worldIn, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) 
			{
		/** GLISTRE CABIN GEN **/
		// make a world generator to use (could be in a separate class but I combined WorldGenerator and IWorldGenerator because I'm messy
//		WorldGenerator genGlistreCabins = new WorldGenCookieBushes();
		// get the biome. Could use 64 for Y for any random biome: you can use anything between 0 and 255, mine is custom ID but had an error so set 155
		Biome biome = worldIn.getBiomeForCoordsBody(new BlockPos(blockX, 155, blockZ));
		// check that it's a Glistering biome
		// we could also use: if(biome instanceof BiomeGenWhatever)
		if(biome == BiomeRegistry.biomeGlistre)
		{
			// how many we want to make per chunk
			// let's make it random between MIN and MAX
	/*		int MIN = 1;
			int MAX = 2;
			int numCabins = MIN + random.nextInt(MAX - MIN);
			// now let's generate the cabins
			for(int i = 0; i < numCabins; i++)
			{
				// get a random position in the chunk
				int randX = blockX + random.nextInt(16);
				int randZ = blockZ + random.nextInt(16);
				// the y-value we pass here will be used as minimum spawn height == 24 means could spawn in a cave
				this.generate(worldIn, random, new BlockPos(randX, 24, randZ));
			}
		 }*/
		/** TOBIE GEN **/
/*			else {
			int MINTOBIE = 1;
			int MAXTOBIE = 2;
			int numTobies = MINTOBIE + random.nextInt(MAXTOBIE - MINTOBIE);
			// now let's generate the Tobies
			for(int i = 0; i < numTobies; i++)
			{
				// get a random position in the chunk
				int randX = blockX + random.nextInt(16);
				int randZ = blockZ + random.nextInt(16);
				// the y-value we pass here will be used as minimum spawn height == 24 means could spawn in a cave
				this.generate2(worldIn, random, new BlockPos(randX, 24, randZ));
			}
			
		}*/
		/** END COOKIE BUSH GEN **/

		// 1% of chunks can have a cabin if CABIN_CHANCE set to 1 and rand.nextInt(100) --> hence, 3 of 200 equals 1.5% chance
				final int CABIN_CHANCE = 3;
				if(random.nextInt(200) < CABIN_CHANCE)
				{
					// get a random position in the chunk
					int randX = blockX + random.nextInt(16);
					int randZ = blockZ + random.nextInt(16);
					// use our custom function to get the ground height
					//use for oregen not chest or cabin -->  int groundY = random.nextInt(80); 
					int groundY = getGroundFromAbove(worldIn, randX, randZ);
					(new CabinChestGen()).generate(worldIn, random, new BlockPos(randX, groundY + 1, randZ));
				}
		}
				else{
	   		 // (new GoldenChestGen()).generate(worldIn, random, pos0);
		      //this below tells me if its generating or not
//		              System.out.println("Generating Cabin and Golden Chest in Glistering Biome"); 
					// 1% of chunks can have a tobie if TOBIE_CHANCE set to 1 and rand.nextInt(100) --> hence, 3 of 400 equals .75% chance
					final int TOBIE_CHANCE = 2;
					if(random.nextInt(2000) < TOBIE_CHANCE)
					{
						// get a random position in the chunk
						int randX = blockX + random.nextInt(16);
						int randZ = blockZ + random.nextInt(16);
						// use our custom function to get the ground height
						//use for oregen not chest or cabin -->  int groundY = random.nextInt(80); 
						int groundY = getGroundFromAbove(worldIn, randX, randZ);
						(new CabinChestGen()).generate2(worldIn, random, new BlockPos(randX, groundY + 1, randZ));
					}
			} 
		 	}
	
	/** HELPER METHODS **/

	// find a grass or dirt block to place the bush on
	public static int getGroundFromAbove(World world, int x, int z)
	{
		int y = 255;
		boolean foundGround = false;
		while(!foundGround && y-- >= 0)
		{
			Block blockAt = world.getBlockState(new BlockPos(x,y,z)).getBlock();
			// "ground" for our bush is grass or dirt
			foundGround = blockAt == Blocks.DIRT || blockAt == Blocks.GRASS;
		}

		return y;
	}
	


}