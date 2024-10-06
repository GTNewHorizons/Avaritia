package fox.spiteful.avaritia.compat.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fox.spiteful.avaritia.crafting.CompressOreRecipe;
import fox.spiteful.avaritia.crafting.CompressorManager;
import fox.spiteful.avaritia.crafting.CompressorRecipe;
import fox.spiteful.avaritia.gui.GUICompressor;

public class CompressionHandler extends TemplateRecipeHandler {

    @SideOnly(Side.CLIENT)
    private FontRenderer fontRender;

    @SideOnly(Side.CLIENT)
    public CompressionHandler() {
        super();
        fontRender = Minecraft.getMinecraft().fontRenderer;
    }

    public class CachedCompression extends CachedRecipe {

        private PositionedStack ingred;
        private PositionedStack result;
        private int cost;

        public CachedCompression(CompressorRecipe recipe) {
            this(recipe.getOutput(), recipe.getCost(), recipe.getIngredient());
        }

        public CachedCompression(ItemStack output, int price, Object ingredient) {
            this.ingred = new PositionedStack(ingredient, 51, 16);
            this.result = new PositionedStack(output, 111, 16);
            this.cost = price;
        }

        @Override
        public List<PositionedStack> getIngredients() {
            return getCycledIngredients(cycleticks / 48, Arrays.asList(this.ingred));
        }

        @Override
        public PositionedStack getResult() {
            return this.result;
        }

        public void computeVisuals() {
            for (ItemStack item : this.ingred.items) item.stackSize = this.cost;
            this.ingred.item.stackSize = this.cost;
            this.ingred.generatePermutations();
        }

        public int getCost() {
            return cost;
        }

    }

    @Override
    public void loadTransferRects() {
        transferRects.add(new RecipeTransferRect(new Rectangle(74, 23, 24, 18), "extreme_compression"));
    }

    @Override
    public Class<? extends GuiContainer> getGuiClass() {
        return GUICompressor.class;
    }

    @Override
    public String getRecipeName() {
        return StatCollector.translateToLocal("crafting.extreme_compression");
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals("extreme_compression") && getClass() == CompressionHandler.class) {
            for (CompressorRecipe recipe : CompressorManager.getRecipes()) {
                if (safeOre(recipe)) {
                    CachedCompression r = new CachedCompression(recipe);
                    r.computeVisuals();
                    arecipes.add(r);
                }
            }
        } else super.loadCraftingRecipes(outputId, results);
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        for (CompressorRecipe recipe : CompressorManager.getRecipes()) {
            if (safeOre(recipe) && NEIServerUtils.areStacksSameTypeCrafting(recipe.getOutput(), result)) {
                CachedCompression r = new CachedCompression(recipe);
                r.computeVisuals();
                arecipes.add(r);
            }
        }
    }

    @Override
    public void loadUsageRecipes(String inputId, Object... ingredients) {
        if (inputId.equals("extreme_compression") && getClass() == CompressionHandler.class) {
            loadCraftingRecipes("extreme_compression");
        } else {
            super.loadUsageRecipes(inputId, ingredients);
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        for (CompressorRecipe recipe : CompressorManager.getRecipes()) {
            if (safeOre(recipe) && recipe.validInput(ingredient)) {
                CachedCompression r = new CachedCompression(recipe.getOutput(), recipe.getCost(), ingredient);
                r.computeVisuals();
                arecipes.add(r);
            }
        }
    }

    private boolean safeOre(CompressorRecipe recipe) {
        if (!(recipe instanceof CompressOreRecipe)) return true;
        return !((ArrayList<?>) recipe.getIngredient()).isEmpty();
    }

    @Override
    public String getGuiTexture() {
        return "avaritia:textures/gui/compressor.png";
    }

    @Override
    public void drawExtras(int recipe) {
        drawProgressBar(74, 15, 176, 14, 24, 16, 48, 0);
    }

    @Override
    public String getOverlayIdentifier() {
        return "extreme_compression";
    }
}
