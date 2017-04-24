package fox.spiteful.avaritia.crafting;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class ExtremeShapedRecipe implements IRecipe
{
    /** How many horizontal slots this recipe is wide. */
    public final int recipeWidth;
    /** How many vertical slots this recipe uses. */
    public final int recipeHeight;
    /** Is a array of ItemStack that composes the recipe. */
    public final ItemStack[] recipeItems;
    /** Is the ItemStack that you get when craft the recipe. */
    private ItemStack recipeOutput;

    public ExtremeShapedRecipe(int width, int height, ItemStack[] ingredients, ItemStack result)
    {
        this.recipeWidth = width;
        this.recipeHeight = height;
        this.recipeItems = ingredients;
        this.recipeOutput = result;
    }

    public ItemStack getRecipeOutput()
    {
        return this.recipeOutput;
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    public boolean matches(InventoryCrafting matrix, World world)
    {
        for (int i = 0; i <= 9 - this.recipeWidth; ++i)
        {
            for (int j = 0; j <= 9 - this.recipeHeight; ++j)
            {
                if (this.checkMatch(matrix, i, j, true))
                {
                    return true;
                }

                if (this.checkMatch(matrix, i, j, false))
                {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Checks if the region of a crafting inventory is match for the recipe.
     */
    private boolean checkMatch(InventoryCrafting matrix, int x, int y, boolean mirrored)
    {
        for (int k = 0; k < 9; ++k)
        {
            for (int l = 0; l < 9; ++l)
            {
                int i1 = k - x;
                int j1 = l - y;
                ItemStack targetStack = null;

                if (i1 >= 0 && j1 >= 0 && i1 < this.recipeWidth && j1 < this.recipeHeight)
                {
                    if (mirrored)
                    {
                        targetStack = this.recipeItems[this.recipeWidth - i1 - 1 + j1 * this.recipeWidth];
                    }
                    else
                    {
                        targetStack = this.recipeItems[i1 + j1 * this.recipeWidth];
                    }
                }

                ItemStack slotStack = matrix.getStackInRowAndColumn(k, l);

                if (slotStack != null || targetStack != null)
                {
                    if ((slotStack == null && targetStack != null) || (slotStack != null && targetStack == null))
                    {
                        return false;
                    }

                    if (targetStack.getItem() != slotStack.getItem())
                    {
                        return false;
                    }

                    if (targetStack.hasTagCompound() &&  !ItemStack.areItemStackTagsEqual(targetStack, slotStack))
                    {
                        return false;
                    }

                    if (targetStack.getItemDamage() != 32767 && targetStack.getItemDamage() != slotStack.getItemDamage())
                    {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    public ItemStack getCraftingResult(InventoryCrafting p_77572_1_)
    {
        return this.getRecipeOutput().copy();

    }

    /**
     * Returns the size of the recipe area
     */
    public int getRecipeSize()
    {
        return this.recipeWidth * this.recipeHeight;
    }

    @Override
    public ItemStack[] getRemainingItems(InventoryCrafting inv) //getRecipeLeftovers
    {
        return ForgeHooks.defaultRecipeGetRemainingItems(inv);
    }

}