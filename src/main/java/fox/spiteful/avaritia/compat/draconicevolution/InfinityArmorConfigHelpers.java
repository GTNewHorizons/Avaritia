package fox.spiteful.avaritia.compat.draconicevolution;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import fox.spiteful.avaritia.compat.Compat;

public class InfinityArmorConfigHelpers {

    private InfinityArmorConfigHelpers() {}

    public static boolean getBoolean(ItemStack stack, String key, boolean defaultValue) {
        if (!Compat.draconicEvolution || stack == null) {
            return defaultValue;
        }
        return InfinityConfigProfileBridge.getBoolean(stack, key, defaultValue);
    }

    public static float getFloat(ItemStack stack, String key, float defaultValue) {
        if (!Compat.draconicEvolution || stack == null) {
            return defaultValue;
        }
        return InfinityConfigProfileBridge.getFloat(stack, key, defaultValue);
    }

    public static boolean isSprintConditionActive(ItemStack stack, EntityPlayer player) {
        return player.isSprinting() || player.isSneaking();
    }

    public static float getConditionalMultiplier(ItemStack stack, EntityPlayer player, String key, float defaultValue) {
        float configuredValue = getFloat(stack, key, defaultValue);
        if (!getBoolean(stack, InfinityArmorConfigKeys.ARMOR_SPRINT_ONLY, false)) {
            return configuredValue;
        }
        return isSprintConditionActive(stack, player) ? configuredValue : configuredValue / 5F;
    }

    public static float getConditionalFlightMultiplier(ItemStack stack, EntityPlayer player, String key,
            float defaultValue) {
        float configuredValue = getFloat(stack, key, defaultValue);
        if (!getBoolean(stack, InfinityArmorConfigKeys.ARMOR_EFFECTIVE_ON_SPRINT, false)) {
            return configuredValue;
        }
        return isSprintConditionActive(stack, player) ? configuredValue : 0F;
    }
}
