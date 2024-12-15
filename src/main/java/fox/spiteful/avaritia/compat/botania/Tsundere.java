package fox.spiteful.avaritia.compat.botania;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import cpw.mods.fml.common.registry.GameRegistry;
import fox.spiteful.avaritia.Avaritia;
import fox.spiteful.avaritia.blocks.LudicrousBlocks;
import fox.spiteful.avaritia.compat.Compat;
import fox.spiteful.avaritia.crafting.ExtremeCraftingManager;
import fox.spiteful.avaritia.crafting.ExtremeShapedRecipe;
import fox.spiteful.avaritia.items.LudicrousItems;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.recipe.RecipeRuneAltar;

public class Tsundere {

    public static Item costumes;

    public static void baka() throws Compat.ItemNotFoundException {

        BotaniaAPI.registerSubTile("asgardandelion", SubTileCheaty.class);
        BotaniaAPI.registerSubTileSignature(SubTileCheaty.class, new Signature("asgardandelion"));
        BotaniaAPI.addSubTileToCreativeMenu("asgardandelion");

        ItemStack cheaty = getFlower("asgardandelion");

        SubTileCheaty.lexicon = new LudicrousLexicon("asgardandelion", BotaniaAPI.categoryGenerationFlowers);
        SubTileCheaty.lexicon.addPage(BotaniaAPI.internalHandler.textPage("avaritia.lexicon.asgardandelion.0"));
        if (!Avaritia.isDreamCraftLoaded) {
            SubTileCheaty.lexicon.setIcon(cheaty);

            ExtremeCraftingManager.getInstance().addRecipe(
                    cheaty,
                    new Object[] { "   III   ", "  IIIII  ", "  IIXII  ", "  IIIII  ", "   III   ", " nn N nn ",
                            "nnnnNnnnn", " nn N nn ", "    N    ", 'I', new ItemStack(LudicrousItems.resource, 1, 6),
                            'X', new ItemStack(LudicrousItems.resource, 1, 5), 'N',
                            new ItemStack(LudicrousItems.resource, 1, 4), 'n',
                            new ItemStack(LudicrousItems.resource, 1, 3), });
        }

        BotaniaAPI.registerSubTile("soarleander", SubTileChicken.class);
        BotaniaAPI.registerSubTileSignature(SubTileChicken.class, new Signature("soarleander"));
        BotaniaAPI.addSubTileToCreativeMenu("soarleander");

        ItemStack chicken = getFlower("soarleander");

        ItemStack chickenitem = new ItemStack(Items.chicken);

        RecipeRuneAltar chickenrecipe = BotaniaAPI.registerRuneAltarRecipe(
                chicken,
                8000,
                getFlower("gourmaryllis"),
                chickenitem,
                chickenitem,
                chickenitem,
                chickenitem,
                chickenitem,
                chickenitem,
                chickenitem,
                chickenitem);

        SubTileChicken.lexicon = new LudicrousLexicon("soarleander", BotaniaAPI.categoryGenerationFlowers);
        SubTileChicken.lexicon.setLexiconPages(
                BotaniaAPI.internalHandler.textPage("avaritia.lexicon.soarleander.0"),
                BotaniaAPI.internalHandler.runeRecipePage("avaritia.lexicon.soarleander.1", chickenrecipe));
        SubTileChicken.lexicon.setIcon(chicken);

        LudicrousBlocks.infinitato = GameRegistry.registerBlock(new BlockInfinitato(), "infinitato");
        GameRegistry.registerTileEntity(TileInfinitato.class, "Avaritia_Infinitato");
        costumes = new ItemInfinitatoCostume();
        GameRegistry.registerItem(costumes, "costumes");

        Block potato = Compat.getBlock("Botania", "tinyPotato");

        ExtremeShapedRecipe tatorecipe = ExtremeCraftingManager.getInstance().addRecipe(
                new ItemStack(LudicrousBlocks.infinitato),
                new Object[] { "IIIIIIIII", "IIIIIIIII", "IIISISIII", "IIIIIIIII", "IISIXISII", "IIISSSIII",
                        "IIIIIIIII", "IIIIIIIII", "IIIIIIIII", 'I', new ItemStack(potato), 'X',
                        new ItemStack(LudicrousItems.resource, 1, 5), 'S', new ItemStack(Items.diamond) });

        BlockInfinitato.lexiconEntry = new LudicrousLexicon("infinitato", BotaniaAPI.categoryMisc);
        BlockInfinitato.lexiconEntry
                .setLexiconPages(
                        BotaniaAPI.internalHandler.textPage("avaritia.lexicon.infinitato.0"),
                        new PageLudicrousRecipe("avaritia.lexicon.infinitato.1", tatorecipe))
                .setIcon(new ItemStack(LudicrousBlocks.infinitato));

    }

    private static ItemStack getFlower(String type) throws Compat.ItemNotFoundException {
        Item specialFlower = Compat.getItem("Botania", "specialFlower");
        ItemStack flower = new ItemStack(specialFlower, 1, 0);
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("type", type);
        flower.setTagCompound(tag);
        return flower;
    }

}
