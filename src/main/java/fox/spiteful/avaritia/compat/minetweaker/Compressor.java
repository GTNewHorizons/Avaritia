package fox.spiteful.avaritia.compat.minetweaker;

import net.minecraft.item.ItemStack;

import fox.spiteful.avaritia.crafting.CompressOreRecipe;
import fox.spiteful.avaritia.crafting.CompressorManager;
import fox.spiteful.avaritia.crafting.CompressorRecipe;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.oredict.IOreDictEntry;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.avaritia.Compressor")
public class Compressor {

    @ZenMethod
    public static void add(IItemStack output, int amount, IIngredient input, boolean exact) {
        CompressorRecipe recipe = null;
        if (input instanceof IOreDictEntry)
            recipe = new CompressOreRecipe(toStack(output), amount, toString((IOreDictEntry) input), exact);
        else if (input instanceof IItemStack)
            recipe = new CompressorRecipe(toStack(output), amount, toStack((IItemStack) input), exact);
        if (recipe != null) MineTweakerAPI.apply(new Add(recipe));
    }

    @ZenMethod
    public static void add(IItemStack output, int amount, IIngredient input) {
        add(output, amount, input, true);
    }

    @ZenMethod
    public static void remove(IItemStack output) {
        MineTweakerAPI.apply(new Remove(toStack(output)));
    }

    private static class Add implements IUndoableAction {

        CompressorRecipe recipe;

        public Add(CompressorRecipe add) {
            recipe = add;
        }

        @Override
        public void apply() {

            CompressorManager.getRecipes().add(recipe);
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public void undo() {
            CompressorManager.getRecipes().remove(recipe);
        }

        @Override
        public String describe() {
            return "Adding Compressor Recipe for " + recipe.getOutput().getDisplayName();
        }

        @Override
        public String describeUndo() {
            return "Un-adding Compressor Recipe for " + recipe.getOutput().getDisplayName();
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }

    }

    private static class Remove implements IUndoableAction {

        CompressorRecipe recipe = null;
        ItemStack remove;

        public Remove(ItemStack rem) {
            remove = rem;
        }

        @Override
        public void apply() {

            for (Object obj : CompressorManager.getRecipes()) {
                if (obj instanceof CompressorRecipe) {
                    CompressorRecipe craft = (CompressorRecipe) obj;
                    if (craft.getOutput().isItemEqual(remove)) {
                        recipe = craft;
                        CompressorManager.getRecipes().remove(obj);
                        break;
                    }
                }
            }
        }

        @Override
        public boolean canUndo() {
            return recipe != null;
        }

        @Override
        public void undo() {
            CompressorManager.getRecipes().add(recipe);
        }

        @Override
        public String describe() {
            return "Removing Compressor Recipe for " + remove.getDisplayName();
        }

        @Override
        public String describeUndo() {
            return "Un-removing Compressor Recipe for " + remove.getDisplayName();
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }

    }

    private static ItemStack toStack(IItemStack item) {
        if (item == null) return null;
        else {
            Object internal = item.getInternal();
            if (internal == null || !(internal instanceof ItemStack)) {
                MineTweakerAPI.getLogger().logError("Not a valid item stack: " + item);
            }
            return (ItemStack) internal;
        }
    }

    private static String toString(IOreDictEntry entry) {
        return ((IOreDictEntry) entry).getName();
    }
}
