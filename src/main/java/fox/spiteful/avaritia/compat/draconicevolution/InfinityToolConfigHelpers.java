package fox.spiteful.avaritia.compat.draconicevolution;

import net.minecraft.item.ItemStack;

import fox.spiteful.avaritia.compat.Compat;

public class InfinityToolConfigHelpers {

    private InfinityToolConfigHelpers() {}

    public static boolean getBoolean(ItemStack stack, String key, boolean defaultValue) {
        if (!Compat.draconicEvolution || stack == null) {
            return defaultValue;
        }
        return InfinityConfigProfileBridge.getBoolean(stack, key, defaultValue);
    }

    public static int getInteger(ItemStack stack, String key, int defaultValue) {
        if (!Compat.draconicEvolution || stack == null) {
            return defaultValue;
        }
        return Math.round(InfinityConfigProfileBridge.getFloat(stack, key, defaultValue));
    }

    public static float getFloat(ItemStack stack, String key, float defaultValue) {
        if (!Compat.draconicEvolution || stack == null) {
            return defaultValue;
        }
        return InfinityConfigProfileBridge.getFloat(stack, key, defaultValue);
    }
}
