package fox.spiteful.avaritia.compat.minetweaker;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import org.apache.logging.log4j.LogManager;

import cpw.mods.fml.common.registry.GameRegistry;
import fox.spiteful.avaritia.Config;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.item.IngredientItem;
import minetweaker.api.oredict.IOreDictEntry;
import minetweaker.api.oredict.IngredientOreDict;

public class Tweak {

    private static final org.apache.logging.log4j.Logger TRANSLATORLOGGER = LogManager
            .getLogger("[SCRIPTS TO CODE TRANSLATOR]");

    public static void info(String log) {
        TRANSLATORLOGGER.info(log);
    }

    public static String convertStack(ItemStack stack) {
        if (stack == null) return "null";
        GameRegistry.UniqueIdentifier itemIdentifier = GameRegistry.findUniqueIdentifierFor(stack.getItem());
        int meta = stack.getItemDamage();
        int size = stack.stackSize;
        NBTTagCompound tagCompound = stack.stackTagCompound;
        if (tagCompound == null || tagCompound.hasNoTags()) {
            return "getModItem(\"" + itemIdentifier.modId
                    + "\", \""
                    + itemIdentifier.name
                    + "\", "
                    + size
                    + ", "
                    + meta
                    + ", missing)";
        } else {
            return "createItemStack(\"" + itemIdentifier.modId
                    + "\", \""
                    + itemIdentifier.name
                    + "\", "
                    + size
                    + ", "
                    + meta
                    + ", "
                    + "\""
                    + tagCompound.toString().replace("\"", "\\\"")
                    + "\""
                    + ", missing)";
        }
    }

    public static String convertStack(IIngredient ingredient) {
        Object internal = ingredient.getInternal();
        if (internal instanceof ItemStack) return convertStack((ItemStack) internal);
        else return "ERRORSTACK";
    }

    public static String convertArrayInLine(Object[] arr) {
        StringBuilder arrayString = new StringBuilder();
        for (int i = 0, arrLength = arr.length; i < arrLength; i++) {
            Object o = arr[i];
            if (o instanceof String) arrayString.append("\"").append((String) o).append("\"");
            else if (o instanceof Character) arrayString.append("'").append((char) o).append("'");
            else if (o instanceof ItemStack) arrayString.append(convertStack((ItemStack) o));
            else if (o instanceof IItemStack || o instanceof IngredientItem)
                arrayString.append(convertStack((IIngredient) o));
            else if (o instanceof IOreDictEntry)
                arrayString.append("\"").append((String) ((IOreDictEntry) o).getInternal()).append("\"");
            else if (o instanceof IngredientOreDict)
                arrayString.append("\"").append((String) ((IngredientOreDict) o).getInternal()).append("\"");
            else if (o == null) arrayString.append("null");
            else arrayString.append(o);
            if (i + 1 < arrLength) arrayString.append(", ");
        }
        return arrayString.toString();
    }

    public static void registrate() {
        MineTweakerAPI.registerClass(ExtremeCrafting.class);
        if (Config.craftingOnly) return;

        MineTweakerAPI.registerClass(Compressor.class);
    }
}
