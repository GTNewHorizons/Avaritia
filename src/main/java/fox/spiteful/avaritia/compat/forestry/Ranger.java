package fox.spiteful.avaritia.compat.forestry;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.EnumHelper;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import forestry.api.recipes.RecipeManagers;
import fox.spiteful.avaritia.Config;
import fox.spiteful.avaritia.compat.Compat;
import fox.spiteful.avaritia.items.LudicrousItems;

public class Ranger {

    public static Item honey;
    public static Item honeydew;
    public static Item comb;

    public static boolean magic = false;
    public static boolean extra = false;

    public static EnumRarity trash = EnumHelper.addRarity("TRASH", EnumChatFormatting.DARK_GRAY, "Trash");

    public static void stopForestFires() throws Compat.ItemNotFoundException {
        magic = Loader.isModLoaded("MagicBees");
        extra = Loader.isModLoaded("ExtraBees");

        comb = Compat.getItem("Forestry", "beeCombs");
        honey = Compat.getItem("Forestry", "honeyDrop");
        honeydew = Compat.getItem("Forestry", "honeydew");

        if (!Config.bees) return;

        LudicrousItems.combs = new ItemComb();
        GameRegistry.registerItem(LudicrousItems.combs, "Combs");
        LudicrousItems.beesource = new ItemBeesource();
        GameRegistry.registerItem(LudicrousItems.beesource, "Beesource");

        Allele.prepareGenes();
        GreedyBeeSpecies.buzz();
        ExpensiveMutation.mutate();

        final float centrifugeChanceA = 0.16f;
        Map<ItemStack, Float> products = new HashMap<>();
        products.put(new ItemStack(Items.dye, 1, 1), centrifugeChanceA);
        products.put(new ItemStack(Items.dye, 1, 2), centrifugeChanceA);
        products.put(new ItemStack(Items.dye, 1, 4), centrifugeChanceA);
        products.put(new ItemStack(Items.dye, 1, 5), centrifugeChanceA);
        products.put(new ItemStack(Items.dye, 1, 11), centrifugeChanceA);
        products.put(new ItemStack(Items.dye, 1, 14), centrifugeChanceA);
        RecipeManagers.centrifugeManager.addRecipe(20, new ItemStack(LudicrousItems.combs, 1, 1), products);

        final float centrifugeChanceB = 1.0f;
        RecipeManagers.centrifugeManager.addRecipe(
                20,
                new ItemStack(LudicrousItems.combs, 1, 0),
                Collections.singletonMap(new ItemStack(LudicrousItems.beesource, 1, 1), centrifugeChanceB));

    }

}
