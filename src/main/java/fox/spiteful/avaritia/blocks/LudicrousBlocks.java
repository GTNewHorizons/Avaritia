package fox.spiteful.avaritia.blocks;

import net.minecraft.block.Block;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import fox.spiteful.avaritia.Config;
import fox.spiteful.avaritia.tile.TileEntityCompressor;
import fox.spiteful.avaritia.tile.TileEntityDireCrafting;
import fox.spiteful.avaritia.tile.TileEntityNeutron;

public class LudicrousBlocks {

    public static Block double_craft;
    public static Block triple_craft;
    public static Block crystal_matrix;
    /**
     * 0 = Neutronium, 1 = Infinity
     */
    public static Block resource_block;
    public static Block dire_crafting;
    public static Block neutron_collector;
    public static Block compressor;

    public static Block infinitato;

    public static Block clusterOpener;

    public static void voxelize() {
        double_craft = GameRegistry.registerBlock(new BlockDoubleCraft(), "Double_Craft");
        triple_craft = GameRegistry.registerBlock(new BlockTripleCraft(), "Triple_Craft");
        dire_crafting = GameRegistry.registerBlock(new BlockDireCrafting(), "Dire_Crafting");
        GameRegistry.registerTileEntity(TileEntityDireCrafting.class, "Avaritia_Dire_Craft");

        if (Config.craftingOnly) return;

        crystal_matrix = GameRegistry.registerBlock(new BlockCrystalMatrix(), "Crystal_Matrix");
        resource_block = GameRegistry.registerBlock(new BlockResource(), ItemBlockResource.class, "Resource_Block");
        neutron_collector = GameRegistry.registerBlock(new BlockNeutronCollector(), "Neutron_Collector");
        GameRegistry.registerTileEntity(TileEntityNeutron.class, "Avaritia_Neutron");
        compressor = GameRegistry.registerBlock(new BlockCompressor(), "Neutronium_Compressor");
        GameRegistry.registerTileEntity(TileEntityCompressor.class, "Avaritia_Compressor");

        if (Loader.isModLoaded("gtnhlib") && Loader.isModLoaded("modularui2")) {
            BlockMatterClusterOpener.register();
        }
    }
}
