package fox.spiteful.avaritia.crafting;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

import fox.spiteful.avaritia.Avaritia;

public class CompressorManager {

    private static final ArrayList<CompressorRecipe> recipes = new ArrayList<>();

    public static void addRecipe(ItemStack output, int amount, ItemStack input) {
        if (Avaritia.isDreamCraftLoaded) return;
        recipes.add(new CompressorRecipe(output, amount, input));
    }

    public static void addOreRecipe(ItemStack output, int amount, String ore) {
        if (Avaritia.isDreamCraftLoaded) return;
        recipes.add(new CompressOreRecipe(output, amount, ore));
    }

    public static ItemStack getOutput(ItemStack input) {
        if (Avaritia.isDreamCraftLoaded) return null;
        for (CompressorRecipe recipe : recipes) {
            if (recipe.validInput(input)) return recipe.getOutput();
        }
        return null;
    }

    public static int getCost(ItemStack input) {
        if (input == null) return 0;
        if (Avaritia.isDreamCraftLoaded) return 0;
        for (CompressorRecipe recipe : recipes) {
            if (recipe.validInput(input)) return recipe.getCost();
        }
        return 0;
    }

    public static int getPrice(ItemStack output) {
        if (output == null) return 0;
        if (Avaritia.isDreamCraftLoaded) return 0;
        for (CompressorRecipe recipe : recipes) {
            if (recipe.getOutput().isItemEqual(output)) return recipe.getCost();
        }
        return 0;
    }

    public static String getName(ItemStack input) {
        if (Avaritia.isDreamCraftLoaded) return null;
        for (CompressorRecipe recipe : recipes) {
            if (recipe.validInput(input)) return recipe.getIngredientName();
        }
        return null;
    }

    public static ArrayList<CompressorRecipe> getRecipes() {
        return recipes;
    }

}
