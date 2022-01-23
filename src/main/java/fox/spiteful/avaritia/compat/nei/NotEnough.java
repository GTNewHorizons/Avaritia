package fox.spiteful.avaritia.compat.nei;

import codechicken.nei.api.API;
import fox.spiteful.avaritia.blocks.LudicrousBlocks;
import net.minecraft.item.ItemStack;

public class NotEnough {

    public static void items(){
        ExtremeShapedRecipeHandler shaped = new ExtremeShapedRecipeHandler();
        ExtremeShapelessRecipeHandler shapeless = new ExtremeShapelessRecipeHandler();
        CompressionHandler compress = new CompressionHandler();
        API.registerRecipeHandler(shaped);
        API.registerRecipeHandler(shapeless);
        API.registerRecipeHandler(compress);
        API.registerUsageHandler(shaped);
        API.registerUsageHandler(shapeless);
        API.registerUsageHandler(compress);

        API.addRecipeCatalyst(new ItemStack(LudicrousBlocks.dire_crafting), "extreme");
        API.addRecipeCatalyst(new ItemStack(LudicrousBlocks.compressor), compress);
    }

    public static void hide(ItemStack stack){
        API.hideItem(stack);
    }
}
