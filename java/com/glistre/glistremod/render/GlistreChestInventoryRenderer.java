package com.glistre.glistremod.render;

import java.util.Map;

import com.glistre.glistremod.init.BlockRegistry;
import com.glistre.glistremod.tileentity.TileEntityGlistreChest;
import com.glistre.glistremod.tileentity.TileEntityGlistreChestGold;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GlistreChestInventoryRenderer extends TileEntityItemStackRenderer {
	//the Tile entity you want to render
    public static TileEntityItemStackRenderer instance = new TileEntityItemStackRenderer();
 //   private final TileEntityChest chestBasic = new TileEntityChest(BlockChest.Type.BASIC);
    private final TileEntityGlistreChest chestGlistre = new TileEntityGlistreChest(BlockChest.Type.BASIC);
    private final TileEntityGlistreChestGold goldenChest = new TileEntityGlistreChestGold(BlockChest.Type.BASIC);
//    private TileEntityGlistreChest te = new TileEntityGlistreChest();
//private Map<Integer, TileEntityGlistreChest> itemRenders = Maps.newHashMap();
	
//	public GlistreChestInventoryRenderer() {

//		itemRenders.put(0, (TileEntityGlistreChest) new TileEntityGlistreChest());
//		itemRenders.put(0, (TileEntityGlistreChestGold) new TileEntityGlistreChestGold());

//	}

    @Override
    public void renderByItem(ItemStack itemStack) {
        Block block = Block.getBlockFromItem(itemStack.getItem());
        
//Your custom block
        if (block == BlockRegistry.glistre_chest) {
            TileEntityRendererDispatcher.instance.renderTileEntityAt(this.chestGlistre, 0.0D, 0.0D, 0.0D, 0.0F);
//			TileEntityRendererDispatcher.instance.renderTileEntityAt(itemRenders.get(0), 0.0D, 0.0D, 0.0D, 0.0F);

        }else if  (block == BlockRegistry.glistre_chest_gold) {
            TileEntityRendererDispatcher.instance.renderTileEntityAt(this.goldenChest, 0.0D, 0.0D, 0.0D, 0.0F);

        }else {

//for minecraft to render its own tile entities such as the chest
            super.renderByItem(itemStack);
        }
    }
}
