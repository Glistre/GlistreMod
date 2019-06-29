package com.glistre.glistremod.mapgen;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import com.glistre.glistremod.init.BiomeRegistry;

import net.minecraft.init.Biomes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStart;
//import net.minecraft.world.gen.structure.StructureVillagePieces;
import com.glistre.glistremod.mapgen.GlistreVillagePieces;

public class MapGenGlistreVillage extends MapGenStructure
{
    public static List<Biome> glistreVillageSpawnBiomes = Arrays.<Biome>asList(new Biome[] {Biomes.PLAINS, BiomeRegistry.biomeGlistre, BiomeRegistry.biomeFreon});
    /** World terrain type, 0 for normal, 1 for flat map */
    private int terrainType;
    private int field_82665_g;
    private int field_82666_h;

    public MapGenGlistreVillage()
    {
    	 this.field_82665_g = 20;   // Maximum distance in chunks between villages  vanilla 32  
    	 this.field_82666_h = 6;    // Minimum distance in chunks between villages  vanilla 8
    	
    }

    public MapGenGlistreVillage(Map<String, String> p_i2093_1_)
    {
        this();

        for (Entry<String, String> entry : p_i2093_1_.entrySet())
        {
            if (((String)entry.getKey()).equals("size"))
            {
                this.terrainType = MathHelper.parseIntWithDefaultAndMax((String)entry.getValue(), this.terrainType, 0);
            }
            else if (((String)entry.getKey()).equals("distance"))
            {
                this.field_82665_g = MathHelper.parseIntWithDefaultAndMax((String)entry.getValue(), this.field_82665_g, this.field_82666_h + 1);
            }
        }
    }

    @Override
    public String getStructureName()
    {
        return "Glistre_Village";
    }

    protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ)
    {
        int i = chunkX;
        int j = chunkZ;

        if (chunkX < 0)
        {
            chunkX -= this.field_82665_g - 1;
        }

        if (chunkZ < 0)
        {
            chunkZ -= this.field_82665_g - 1;
        }

        int k = chunkX / this.field_82665_g;
        int l = chunkZ / this.field_82665_g;
        Random random = this.worldObj.setRandomSeed(k, l, 10387312);
        k = k * this.field_82665_g;
        l = l * this.field_82665_g;
        k = k + random.nextInt(this.field_82665_g - this.field_82666_h);
        l = l + random.nextInt(this.field_82665_g - this.field_82666_h);

        if (i == k && j == l)
        {
            boolean flag = this.worldObj.getBiomeProvider().areBiomesViable(i * 16 + 8, j * 16 + 8, 0, glistreVillageSpawnBiomes);

            if (flag)
            {
                return true;
            }
        }

        return false;
    }

    protected StructureStart getStructureStart(int chunkX, int chunkZ)
    {
        return new MapGenGlistreVillage.GlistreStart(this.worldObj, this.rand, chunkX, chunkZ, this.terrainType);
    }

    public static class GlistreStart extends StructureStart
        {
            /** well ... thats what it does */
            private boolean hasMoreThanTwoComponents;

            public GlistreStart()
            {
            }

            public GlistreStart(World worldIn, Random rand, int x, int z, int p_i2092_5_)
            {
                super(x, z);
                List<GlistreVillagePieces.PieceWeight> list = GlistreVillagePieces.getStructureVillageWeightedPieceList(rand, p_i2092_5_);
                
                //getWorldChunkManager changed to getBiomeProvider
                GlistreVillagePieces.Start structurevillagepieces$start = new GlistreVillagePieces.Start(worldIn.getBiomeProvider(), 0, rand, (x << 4) + 2, (z << 4) + 2, list, p_i2092_5_);
                this.components.add(structurevillagepieces$start);
                structurevillagepieces$start.buildComponent(structurevillagepieces$start, this.components, rand);
                List<StructureComponent> list1 = structurevillagepieces$start.pendingRoads; // field_74930_jchanged to pendingRoads
                List<StructureComponent> list2 = structurevillagepieces$start.pendingHouses; // field_74932_ichanged to pendingHouses

                while (!list1.isEmpty() || !list2.isEmpty())
                {
                    if (list1.isEmpty())
                    {
                        int i = rand.nextInt(list2.size());
                        StructureComponent structurecomponent = (StructureComponent)list2.remove(i);
                        structurecomponent.buildComponent(structurevillagepieces$start, this.components, rand);
                    }
                    else
                    {
                        int j = rand.nextInt(list1.size());
                        StructureComponent structurecomponent2 = (StructureComponent)list1.remove(j);
                        structurecomponent2.buildComponent(structurevillagepieces$start, this.components, rand);
                    }
                }

                this.updateBoundingBox();
                int k = 0;

                for (StructureComponent structurecomponent1 : this.components)
                {
                    if (!(structurecomponent1 instanceof GlistreVillagePieces.Road))
                    {
                        ++k;
                    }
                }

                this.hasMoreThanTwoComponents = k > 2;
            }

            /**
             * currently only defined for Villages, returns true if Village has more than 2 non-road components
             */
            public boolean isSizeableStructure()
            {
                return this.hasMoreThanTwoComponents;
            }

            public void writeToNBT(NBTTagCompound tagCompound)
            {
                super.writeToNBT(tagCompound);
                tagCompound.setBoolean("Valid", this.hasMoreThanTwoComponents);
            }

            public void readFromNBT(NBTTagCompound tagCompound)
            {
                super.readFromNBT(tagCompound);
                this.hasMoreThanTwoComponents = tagCompound.getBoolean("Valid");
            }
        }
}