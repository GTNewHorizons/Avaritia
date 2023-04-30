package fox.spiteful.avaritia.compat.minetweaker;

import java.util.*;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import fox.spiteful.avaritia.crafting.ExtremeCraftingManager;
import fox.spiteful.avaritia.crafting.ExtremeShapedOreRecipe;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.oredict.IOreDictEntry;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.avaritia.ExtremeCrafting")
public class ExtremeCrafting {

    @ZenMethod
    public static void addShapeless(IItemStack output, IIngredient[] ingredients) {

        MineTweakerAPI.apply(new Add(new ShapelessOreRecipe(toStack(output), toObjects(ingredients))));
    }

    @ZenMethod
    public static void addShaped(IItemStack output, IIngredient[][] ingredients) {
        int height = ingredients.length;
        int width = 0;
        for (IIngredient[] row : ingredients) {
            if (width < row.length) width = row.length;
        }

        String letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int nextletter = 0;
        // ExtremeShapedOreRecipe r = (ExtremeShapedOreRecipe)recipe;
        HashMap<Character, Object> itemMap = new HashMap<>();
        HashMap<Object, Character> reverseItemMap = new HashMap<>();

        Object[] input = new Object[width * height];
        String[] shapes = new String[height];
        int x = 0;
        int xx = 0;
        for (IIngredient[] row : ingredients) {
            Object[] objects = toObjects(row);
            StringBuilder shape = new StringBuilder();
            for (Object o : objects) {
                Object o2 = o;
                if (o2 instanceof ItemStack) o2 = new ItemMetaNBTWrapper((ItemStack) o2);
                Character chr = null;
                if (o == null) chr = '-';
                else if (reverseItemMap.containsKey(o2)) {
                    chr = reverseItemMap.get(o2);
                } else {
                    chr = letters.charAt(nextletter);
                    nextletter++;
                    itemMap.put(chr, o);
                    reverseItemMap.put(o2, chr);
                }
                shape.append(chr);
            }
            shapes[xx++] = shape.toString();

            for (IIngredient ingredient : row) {
                input[x++] = toActualObject(ingredient);
            }
        }

        List<Object> ready = new ArrayList<>(Arrays.asList(shapes));

        for (Map.Entry<Character, Object> entry : itemMap.entrySet()) {
            ready.add(entry.getKey());
            ready.add(entry.getValue());
        }

        Tweak.info(
                "ExtremeCraftingManager.getInstance().addExtremeShapedOreRecipe(" + Tweak.convertStack(toStack(output))
                        + ", "
                        + Tweak.convertArrayInLine(ready.toArray())
                        + ");");

        MineTweakerAPI.apply(new Add(new ExtremeShapedOreRecipe(toStack(output), input, width, height)));
    }

    @ZenMethod
    public static void remove(IItemStack target) {
        MineTweakerAPI.apply(new Remove(toStack(target)));
    }

    private static class Add implements IUndoableAction {

        IRecipe recipe;

        public Add(IRecipe add) {
            recipe = add;
        }

        @Override
        public void apply() {

            ExtremeCraftingManager.getInstance().getRecipeList().add(recipe);

        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public void undo() {
            ExtremeCraftingManager.getInstance().getRecipeList().remove(recipe);
        }

        @Override
        public String describe() {
            return "Adding Xtreme Crafting Recipe for " + recipe.getRecipeOutput().getDisplayName();
        }

        @Override
        public String describeUndo() {
            return "Un-adding Xtreme Crafting Recipe for " + recipe.getRecipeOutput().getDisplayName();
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }

    }

    private static class Remove implements IUndoableAction {

        IRecipe recipe = null;
        ItemStack remove;

        public Remove(ItemStack rem) {
            remove = rem;
        }

        @Override
        public void apply() {

            Tweak.info("AvaritiaHelper.removeExtremeCraftingRecipe(" + Tweak.convertStack(remove) + ");");

            for (Object obj : ExtremeCraftingManager.getInstance().getRecipeList()) {
                if (obj instanceof IRecipe) {
                    IRecipe craft = (IRecipe) obj;
                    if (craft.getRecipeOutput().isItemEqual(remove)) {
                        recipe = craft;
                        ExtremeCraftingManager.getInstance().getRecipeList().remove(obj);
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
            ExtremeCraftingManager.getInstance().getRecipeList().add(recipe);
        }

        @Override
        public String describe() {
            return "Removing Xtreme Crafting Recipe for " + remove.getDisplayName();
        }

        @Override
        public String describeUndo() {
            return "Un-removing Xtreme Crafting Recipe for " + remove.getDisplayName();
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }

    }

    private static class AddCatalyst implements IUndoableAction {

        Object ingredient;

        public AddCatalyst(Object add) {
            ingredient = add;
        }

        @Override
        public void apply() {}

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public void undo() {}

        @Override
        public String describe() {
            if (ingredient instanceof ItemStack)
                return "Adding " + ((ItemStack) ingredient).getDisplayName() + " to Infinity Catalyst recipe.";
            else if (ingredient instanceof List)
                return "Adding " + ((ItemStack) (((List) ingredient).get(0))).getDisplayName()
                        + " to Infinity Catalyst recipe.";
            else return "Adding something to Infinity Catalyst recipe.";
        }

        @Override
        public String describeUndo() {
            if (ingredient instanceof ItemStack)
                return "Removing " + ((ItemStack) ingredient).getDisplayName() + " from Infinity Catalyst recipe.";
            else if (ingredient instanceof List)
                return "Removing " + ((ItemStack) (((List) ingredient).get(0))).getDisplayName()
                        + " from Infinity Catalyst recipe.";
            else return "Removing something from Infinity Catalyst recipe.";
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }

    }

    private static class RemoveCatalyst implements IUndoableAction {

        IIngredient ingredient;

        public RemoveCatalyst(IIngredient rem) {
            ingredient = rem;
        }

        @Override
        public void apply() {}

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public void undo() {}

        @Override
        public String describe() {
            return "Adding something to Infinity Catalyst recipe.";
        }

        @Override
        public String describeUndo() {
            return "Removing something from Infinity Catalyst recipe.";
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

    private static Object toObject(IIngredient ingredient) {
        if (ingredient == null) return null;
        else {
            if (ingredient instanceof IOreDictEntry) {
                return toString((IOreDictEntry) ingredient);
            } else if (ingredient instanceof IItemStack) {
                return toStack((IItemStack) ingredient);
            } else return null;
        }
    }

    private static Object[] toObjects(IIngredient[] list) {
        if (list == null) return null;
        Object[] ingredients = new Object[list.length];
        for (int x = 0; x < list.length; x++) {
            ingredients[x] = toObject(list[x]);
        }
        return ingredients;
    }

    private static Object toActualObject(IIngredient ingredient) {
        if (ingredient == null) return null;
        else {
            if (ingredient instanceof IOreDictEntry) {
                return OreDictionary.getOres(toString((IOreDictEntry) ingredient));
            } else if (ingredient instanceof IItemStack) {
                return toStack((IItemStack) ingredient);
            } else return null;
        }
    }

    private static String toString(IOreDictEntry entry) {
        return ((IOreDictEntry) entry).getName();
    }

}
