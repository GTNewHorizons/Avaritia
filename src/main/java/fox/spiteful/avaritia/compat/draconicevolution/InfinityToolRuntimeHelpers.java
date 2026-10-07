package fox.spiteful.avaritia.compat.draconicevolution;

import net.minecraft.item.ItemStack;

import fox.spiteful.avaritia.compat.Compat;

public class InfinityToolRuntimeHelpers {

    private static final float LEGACY_SWORD_DAMAGE = Float.MAX_VALUE;

    private InfinityToolRuntimeHelpers() {}

    public static float getSwordDamage(ItemStack stack) {
        if (!Compat.draconicEvolution) {
            return LEGACY_SWORD_DAMAGE;
        }
        return InfinityToolConfigHelpers.getFloat(stack, InfinityToolConfigKeys.SWORD_ATTACK_DAMAGE, 2000F);
    }

    public static boolean isSwordExecutionEnabled(ItemStack stack) {
        if (!Compat.draconicEvolution) {
            return true;
        }
        return InfinityToolConfigHelpers.getBoolean(stack, InfinityToolConfigKeys.SWORD_EXECUTION_ENABLED, true);
    }

    public static boolean isSwordCreativeBypassEnabled(ItemStack stack) {
        if (!Compat.draconicEvolution) {
            return true;
        }
        return InfinityToolConfigHelpers.getBoolean(stack, InfinityToolConfigKeys.SWORD_CREATIVE_BYPASS_ENABLED, true);
    }

    public static boolean isPickaxeHammerEnabled(ItemStack stack) {
        if (!Compat.draconicEvolution) {
            return true;
        }
        return InfinityToolConfigHelpers.getBoolean(stack, InfinityToolConfigKeys.PICKAXE_HAMMER_ENABLED, true);
    }

    public static int getPickaxeHammerRange(ItemStack stack) {
        if (!Compat.draconicEvolution) {
            return 8;
        }
        return InfinityToolConfigHelpers.getInteger(stack, InfinityToolConfigKeys.PICKAXE_HAMMER_RANGE, 8);
    }

    public static boolean isAxeVeinEnabled(ItemStack stack) {
        if (!Compat.draconicEvolution) {
            return true;
        }
        return InfinityToolConfigHelpers.getBoolean(stack, InfinityToolConfigKeys.AXE_VEIN_ENABLED, true);
    }

    public static int getAxeCleaveRange(ItemStack stack) {
        if (!Compat.draconicEvolution) {
            return 13;
        }
        return InfinityToolConfigHelpers.getInteger(stack, InfinityToolConfigKeys.AXE_CLEAVE_RANGE, 13);
    }

    public static boolean isShovelDestroyerEnabled(ItemStack stack) {
        if (!Compat.draconicEvolution) {
            return true;
        }
        return InfinityToolConfigHelpers.getBoolean(stack, InfinityToolConfigKeys.SHOVEL_DESTROYER_ENABLED, true);
    }

    public static int getShovelDestroyerRange(ItemStack stack) {
        if (!Compat.draconicEvolution) {
            return 8;
        }
        return InfinityToolConfigHelpers.getInteger(stack, InfinityToolConfigKeys.SHOVEL_DESTROYER_RANGE, 8);
    }

    public static boolean isBowRapidFireEnabled(ItemStack stack) {
        if (!Compat.draconicEvolution) {
            return true;
        }
        return InfinityToolConfigHelpers.getBoolean(stack, InfinityToolConfigKeys.BOW_RAPID_FIRE_ENABLED, true);
    }

    public static boolean isBowSwordRainEnabled(ItemStack stack) {
        if (!Compat.draconicEvolution) {
            return true;
        }
        return InfinityToolConfigHelpers.getBoolean(stack, InfinityToolConfigKeys.BOW_SWORD_RAIN_ENABLED, true);
    }
}
