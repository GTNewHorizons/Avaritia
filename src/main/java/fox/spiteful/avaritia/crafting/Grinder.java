package fox.spiteful.avaritia.crafting;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import cpw.mods.fml.common.registry.GameRegistry;
import fox.spiteful.avaritia.Config;
import fox.spiteful.avaritia.blocks.LudicrousBlocks;
import fox.spiteful.avaritia.items.LudicrousItems;

public class Grinder {

    public static ShapelessOreRecipe catalyst;

    public static void artsAndCrafts() {

        OreDictionary.registerOre("blockCrystalMatrix", new ItemStack(LudicrousBlocks.crystal_matrix, 1, 0));
        OreDictionary.registerOre("blockCosmicNeutronium", new ItemStack(LudicrousBlocks.resource_block, 1, 0));
        OreDictionary.registerOre("blockInfinity", new ItemStack(LudicrousBlocks.resource_block, 1, 1));
        OreDictionary.registerOre("ingotCrystalMatrix", new ItemStack(LudicrousItems.resource, 1, 1));
        OreDictionary.registerOre("ingotCosmicNeutronium", new ItemStack(LudicrousItems.resource, 1, 4));
        OreDictionary.registerOre("ingotInfinity", new ItemStack(LudicrousItems.resource, 1, 6));

        GameRegistry.addShapedRecipe(
                new ItemStack(LudicrousItems.resource, 1, 0),
                "X X",
                " X ",
                "X X",
                'X',
                new ItemStack(Items.diamond));
        GameRegistry.addShapedRecipe(
                new ItemStack(LudicrousItems.resource, 1, 1),
                "DSD",
                "DSD",
                'D',
                new ItemStack(LudicrousItems.resource, 1, 0),
                'S',
                new ItemStack(Items.nether_star));
        GameRegistry.addShapedRecipe(
                new ItemStack(LudicrousBlocks.double_craft, 1),
                "CCC",
                "CCC",
                "CCC",
                'C',
                new ItemStack(Blocks.crafting_table));
        GameRegistry.addShapedRecipe(
                new ItemStack(LudicrousBlocks.triple_craft, 1),
                "CCC",
                "CCC",
                "CCC",
                'C',
                new ItemStack(LudicrousBlocks.double_craft));
        GameRegistry.addShapedRecipe(
                new ItemStack(Blocks.crafting_table, 9),
                "C",
                'C',
                new ItemStack(LudicrousBlocks.double_craft));
        GameRegistry.addShapedRecipe(
                new ItemStack(LudicrousBlocks.double_craft, 9),
                "C",
                'C',
                new ItemStack(LudicrousBlocks.triple_craft));
        GameRegistry.addShapedRecipe(
                new ItemStack(LudicrousBlocks.dire_crafting, 1),
                "CCC",
                "CXC",
                "CCC",
                'C',
                new ItemStack(LudicrousItems.resource, 1, 1),
                'X',
                new ItemStack(LudicrousBlocks.triple_craft));

        if (Config.craftingOnly) return;

        GameRegistry.addShapedRecipe(
                new ItemStack(LudicrousBlocks.crystal_matrix, 1, 0),
                "CCC",
                "CCC",
                "CCC",
                'C',
                new ItemStack(LudicrousItems.resource, 1, 1));
        GameRegistry.addShapedRecipe(
                new ItemStack(LudicrousItems.resource, 9, 1),
                "C",
                'C',
                new ItemStack(LudicrousBlocks.crystal_matrix, 1, 0));
        GameRegistry.addShapedRecipe(
                new ItemStack(LudicrousItems.resource, 1, 3),
                "CCC",
                "CCC",
                "CCC",
                'C',
                new ItemStack(LudicrousItems.resource, 1, 2));
        GameRegistry.addShapedRecipe(
                new ItemStack(LudicrousItems.resource, 1, 4),
                "CCC",
                "CCC",
                "CCC",
                'C',
                new ItemStack(LudicrousItems.resource, 1, 3));
        GameRegistry.addShapedRecipe(
                new ItemStack(LudicrousItems.resource, 9, 3),
                "C",
                'C',
                new ItemStack(LudicrousItems.resource, 1, 4));
        GameRegistry.addShapedRecipe(
                new ItemStack(LudicrousItems.resource, 9, 2),
                "C",
                'C',
                new ItemStack(LudicrousItems.resource, 1, 3));
        GameRegistry.addShapedRecipe(
                new ItemStack(LudicrousBlocks.resource_block, 1, 0),
                "CCC",
                "CCC",
                "CCC",
                'C',
                new ItemStack(LudicrousItems.resource, 1, 4));
        GameRegistry.addShapedRecipe(
                new ItemStack(LudicrousItems.resource, 9, 4),
                "C",
                'C',
                new ItemStack(LudicrousBlocks.resource_block, 1, 0));
        GameRegistry.addShapedRecipe(
                new ItemStack(LudicrousBlocks.resource_block, 1, 1),
                "CCC",
                "CCC",
                "CCC",
                'C',
                new ItemStack(LudicrousItems.resource, 1, 6));
        GameRegistry.addShapedRecipe(
                new ItemStack(LudicrousItems.resource, 9, 6),
                "C",
                'C',
                new ItemStack(LudicrousBlocks.resource_block, 1, 1));
        GameRegistry.addShapedRecipe(
                new ItemStack(LudicrousItems.resource, 1, 9),
                " I ",
                "ISI",
                " I ",
                'I',
                new ItemStack(LudicrousItems.resource, 1, 4),
                'S',
                new ItemStack(Items.stick));

        ExtremeCraftingManager.getInstance().addRecipe(
                new ItemStack(LudicrousBlocks.neutron_collector, 1),
                "IIQQQQQII",
                "I QQQQQ I",
                "I  RRR  I",
                "X RRRRR X",
                "I RRXRR I",
                "X RRRRR X",
                "I  RRR  I",
                "I       I",
                "IIIXIXIII",
                'X',
                new ItemStack(LudicrousItems.resource, 1, 1),
                'I',
                new ItemStack(Blocks.iron_block, 1),
                'Q',
                new ItemStack(Blocks.quartz_block, 1),
                'R',
                new ItemStack(Blocks.redstone_block, 1));

        ExtremeCraftingManager.getInstance().addRecipe(
                new ItemStack(LudicrousItems.resource, 1, 6),
                "NNNNNNNNN",
                "NCXXCXXCN",
                "NXCCXCCXN",
                "NCXXCXXCN",
                "NNNNNNNNN",
                'C',
                new ItemStack(LudicrousItems.resource, 1, 1),
                'N',
                new ItemStack(LudicrousItems.resource, 1, 4),
                'X',
                new ItemStack(LudicrousItems.resource, 1, 5));

        catalyst = ExtremeCraftingManager.getInstance().addShapelessOreRecipe(
                new ItemStack(LudicrousItems.resource, 1, 5),
                new ItemStack(Blocks.emerald_block, 1),
                new ItemStack(LudicrousItems.singularity, 1, 0),
                new ItemStack(LudicrousItems.singularity, 1, 1),
                new ItemStack(LudicrousItems.singularity, 1, 2),
                new ItemStack(LudicrousItems.singularity, 1, 3),
                new ItemStack(LudicrousItems.singularity, 1, 4),
                new ItemStack(LudicrousItems.ultimate_stew),
                new ItemStack(LudicrousItems.cosmic_meatballs),
                new ItemStack(LudicrousItems.endest_pearl));

        ItemStack result = new ItemStack(LudicrousItems.infinity_pickaxe, 1);
        result.addEnchantment(Enchantment.fortune, 10);
        ExtremeCraftingManager.getInstance().addRecipe(
                result,
                " IIIIIII ",
                "IIIICIIII",
                "II  N  II",
                "    N    ",
                "    N    ",
                "    N    ",
                "    N    ",
                "    N    ",
                "    N    ",
                'I',
                new ItemStack(LudicrousItems.resource, 1, 6),
                'C',
                new ItemStack(LudicrousBlocks.crystal_matrix, 1),
                'N',
                new ItemStack(LudicrousItems.resource, 1, 4));

        ExtremeCraftingManager.getInstance().addRecipe(
                new ItemStack(LudicrousItems.infinity_sword),
                "       II",
                "      III",
                "     III ",
                "    III  ",
                " C III   ",
                "  CII    ",
                "  NC     ",
                " N  C    ",
                "X        ",
                'I',
                new ItemStack(LudicrousItems.resource, 1, 6),
                'X',
                new ItemStack(LudicrousItems.resource, 1, 5),
                'C',
                new ItemStack(LudicrousItems.resource, 1, 1),
                'N',
                new ItemStack(LudicrousItems.resource, 1, 4));

        ExtremeCraftingManager.getInstance().addRecipe(
                new ItemStack(LudicrousItems.infinity_shovel),
                "      III",
                "     IIXI",
                "      III",
                "     N I ",
                "    N    ",
                "   N     ",
                "  N      ",
                " N       ",
                "N        ",
                'I',
                new ItemStack(LudicrousItems.resource, 1, 6),
                'X',
                new ItemStack(LudicrousBlocks.resource_block, 1, 1),
                'N',
                new ItemStack(LudicrousItems.resource, 1, 4));

        ExtremeCraftingManager.getInstance().addRecipe(
                new ItemStack(LudicrousItems.infinity_axe),
                " I   ",
                "IIIII",
                "IIII ",
                " IN  ",
                "  N  ",
                "  N  ",
                "  N  ",
                "  N  ",
                "  N  ",
                'I',
                new ItemStack(LudicrousItems.resource, 1, 6),
                'N',
                new ItemStack(LudicrousItems.resource, 1, 4));

        ExtremeCraftingManager.getInstance().addRecipe(
                new ItemStack(LudicrousItems.infinity_bow),
                "   II",
                "  I W",
                " I  W",
                "I   W",
                "X   W",
                "I   W",
                " I  W",
                "  I W",
                "   II",
                'I',
                new ItemStack(LudicrousItems.resource, 1, 6),
                'X',
                new ItemStack(LudicrousBlocks.crystal_matrix, 1),
                'W',
                new ItemStack(Blocks.wool, 1, 0));

        ExtremeCraftingManager.getInstance().addRecipe(
                new ItemStack(LudicrousItems.infinity_armor),
                " NN   NN ",
                "NNN   NNN",
                "NNN   NNN",
                " NIIIIIN ",
                " NIIXIIN ",
                " NIIIIIN ",
                " NIIIIIN ",
                " NIIIIIN ",
                "  NNNNN  ",
                'I',
                new ItemStack(LudicrousItems.resource, 1, 6),
                'X',
                new ItemStack(LudicrousBlocks.crystal_matrix, 1),
                'N',
                new ItemStack(LudicrousItems.resource, 1, 4));

        ExtremeCraftingManager.getInstance().addRecipe(
                new ItemStack(LudicrousItems.infinity_helm),
                "  NNNNN  ",
                " NIIIIIN ",
                " N XIX N ",
                " NIIIIIN ",
                " NIIIIIN ",
                " NI I IN ",
                'I',
                new ItemStack(LudicrousItems.resource, 1, 6),
                'X',
                new ItemStack(LudicrousItems.resource, 1, 5),
                'N',
                new ItemStack(LudicrousItems.resource, 1, 4));

        ExtremeCraftingManager.getInstance().addRecipe(
                new ItemStack(LudicrousItems.infinity_pants),
                "NNNNNNNNN",
                "NIIIXIIIN",
                "NINNXNNIN",
                "NIN   NIN",
                "NCN   NCN",
                "NIN   NIN",
                "NIN   NIN",
                "NIN   NIN",
                "NNN   NNN",
                'I',
                new ItemStack(LudicrousItems.resource, 1, 6),
                'X',
                new ItemStack(LudicrousItems.resource, 1, 5),
                'C',
                new ItemStack(LudicrousBlocks.crystal_matrix, 1),
                'N',
                new ItemStack(LudicrousItems.resource, 1, 4));

        ExtremeCraftingManager.getInstance().addRecipe(
                new ItemStack(LudicrousItems.infinity_shoes),
                " NNN NNN ",
                " NIN NIN ",
                " NIN NIN ",
                "NNIN NINN",
                "NIIN NIIN",
                "NNNN NNNN",
                'I',
                new ItemStack(LudicrousItems.resource, 1, 6),
                'N',
                new ItemStack(LudicrousItems.resource, 1, 4));

        ExtremeCraftingManager.getInstance().addExtremeShapedOreRecipe(
                new ItemStack(LudicrousItems.skull_sword),
                "       IX",
                "      IXI",
                "     IXI ",
                "    IXI  ",
                " B IXI   ",
                "  BXI    ",
                "  WB     ",
                " W  B    ",
                "D        ",
                'I',
                new ItemStack(LudicrousItems.resource, 1, 1),
                'X',
                new ItemStack(Items.blaze_powder),
                'B',
                new ItemStack(Items.bone),
                'D',
                new ItemStack(Items.nether_star),
                'W',
                "logWood");

        CompressorManager
                .addRecipe(new ItemStack(LudicrousItems.singularity, 1, 0), 7296, new ItemStack(Blocks.iron_block));
        CompressorManager
                .addRecipe(new ItemStack(LudicrousItems.singularity, 1, 1), 1215, new ItemStack(Blocks.gold_block, 1));
        CompressorManager
                .addRecipe(new ItemStack(LudicrousItems.singularity, 1, 2), 1215, new ItemStack(Blocks.lapis_block, 1));
        CompressorManager.addRecipe(
                new ItemStack(LudicrousItems.singularity, 1, 3),
                7296,
                new ItemStack(Blocks.redstone_block, 1));
        CompressorManager.addRecipe(
                new ItemStack(LudicrousItems.singularity, 1, 4),
                1215,
                new ItemStack(Blocks.quartz_block, 1));
        if (Config.copper && !OreDictionary.getOres("blockCopper").isEmpty()) {
            CompressorManager.addOreRecipe(new ItemStack(LudicrousItems.singularity, 1, 5), 3648, "blockCopper");
            catalyst.getInput().add(new ItemStack(LudicrousItems.singularity, 1, 5));
        }
        if (Config.tin && !OreDictionary.getOres("blockTin").isEmpty()) {
            CompressorManager.addOreRecipe(new ItemStack(LudicrousItems.singularity, 1, 6), 3648, "blockTin");
            catalyst.getInput().add(new ItemStack(LudicrousItems.singularity, 1, 6));
        }
        if (Config.lead && !OreDictionary.getOres("blockLead").isEmpty()) {
            CompressorManager.addOreRecipe(new ItemStack(LudicrousItems.singularity, 1, 7), 3648, "blockLead");
            catalyst.getInput().add(new ItemStack(LudicrousItems.singularity, 1, 7));
        }
        if (Config.silver && !OreDictionary.getOres("blockSilver").isEmpty()) {
            CompressorManager.addOreRecipe(new ItemStack(LudicrousItems.singularity, 1, 8), 7296, "blockSilver");
            catalyst.getInput().add(new ItemStack(LudicrousItems.singularity, 1, 8));
        }
        if (Config.nickel && !OreDictionary.getOres("blockNickel").isEmpty()) {
            CompressorManager.addOreRecipe(new ItemStack(LudicrousItems.singularity, 1, 9), 3648, "blockNickel");
            catalyst.getInput().add(new ItemStack(LudicrousItems.singularity, 1, 9));
        }
        if (Config.enderium && !OreDictionary.getOres("blockEnderium").isEmpty()) {
            CompressorManager.addOreRecipe(new ItemStack(LudicrousItems.singularity, 1, 10), 608, "blockEnderium");
            catalyst.getInput().add(new ItemStack(LudicrousItems.singularity, 1, 10));
        }
        if (Config.steel && !OreDictionary.getOres("blockSteel").isEmpty()) {
            catalyst.getInput().add(OreDictionary.getOres("blockSteel"));
        }
        if (Config.metallurgy && !OreDictionary.getOres("ingotTartarite").isEmpty()) {
            catalyst.getInput().add(OreDictionary.getOres("ingotTartarite"));
        }
        if (Config.numanuma && !OreDictionary.getOres("blockIronCompressed").isEmpty()) {
            catalyst.getInput().add(OreDictionary.getOres("blockIronCompressed"));
        }
        if (Config.darksteel && !OreDictionary.getOres("blockDarkSteel").isEmpty()) {
            catalyst.getInput().add(OreDictionary.getOres("blockDarkSteel"));
        }

        if (Config.endStone) ExtremeCraftingManager.getInstance().addExtremeShapedOreRecipe(
                new ItemStack(LudicrousItems.endest_pearl),
                "   EEE   ",
                " EEPPPEE ",
                " EPPPPPE ",
                "EPPPNPPPE",
                "EPPNSNPPE",
                "EPPPNPPPE",
                " EPPPPPE ",
                " EEPPPEE ",
                "   EEE   ",
                'E',
                new ItemStack(Blocks.end_stone),
                'P',
                new ItemStack(Items.ender_pearl),
                'S',
                new ItemStack(Items.nether_star),
                'N',
                new ItemStack(LudicrousItems.resource, 1, 4));
        else ExtremeCraftingManager.getInstance().addExtremeShapedOreRecipe(
                new ItemStack(LudicrousItems.endest_pearl),
                "   EEE   ",
                " EEPPPEE ",
                " EPPPPPE ",
                "EPPPNPPPE",
                "EPPNSNPPE",
                "EPPPNPPPE",
                " EPPPPPE ",
                " EEPPPEE ",
                "   EEE   ",
                'P',
                new ItemStack(Items.ender_pearl),
                'S',
                new ItemStack(Items.nether_star),
                'N',
                new ItemStack(LudicrousItems.resource, 1, 4));

        ExtremeCraftingManager.getInstance().addRecipe(
                new ItemStack(LudicrousBlocks.compressor),
                "IIIHHHIII",
                "X N   N X",
                "I N   N I",
                "X N   N X",
                "RNN O NNR",
                "X N   N X",
                "I N   N I",
                "X N   N X",
                "IIIXIXIII",
                'X',
                new ItemStack(LudicrousItems.resource, 1, 1),
                'N',
                new ItemStack(LudicrousItems.resource, 1, 4),
                'I',
                new ItemStack(Blocks.iron_block, 1),
                'H',
                new ItemStack(Blocks.hopper, 1),
                'R',
                new ItemStack(Blocks.redstone_block, 1),
                'O',
                new ItemStack(LudicrousBlocks.resource_block, 1, 0));
    }

}
