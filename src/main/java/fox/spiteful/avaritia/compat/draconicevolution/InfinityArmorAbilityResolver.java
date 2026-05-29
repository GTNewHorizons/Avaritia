package fox.spiteful.avaritia.compat.draconicevolution;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class InfinityArmorAbilityResolver {

    private static final float LEGACY_SPEED_MULTIPLIER = 0F;
    private static final float LEGACY_JUMP_MULTIPLIER = 0F;
    private static final float LEGACY_FLIGHT_SPEED_MULTIPLIER = 1F;
    private static final float LEGACY_VERTICAL_ACCELERATION = 0F;

    private InfinityArmorAbilityResolver() {}

    public static float getSpeedModifier(ItemStack stack, EntityPlayer player) {
        return InfinityArmorConfigHelpers.getConditionalMultiplier(
                stack,
                player,
                InfinityArmorConfigKeys.ARMOR_SPEED_MULT,
                LEGACY_SPEED_MULTIPLIER);
    }

    public static float getJumpModifier(ItemStack stack, EntityPlayer player) {
        return InfinityArmorConfigHelpers.getConditionalMultiplier(
                stack,
                player,
                InfinityArmorConfigKeys.ARMOR_JUMP_MULT,
                LEGACY_JUMP_MULTIPLIER);
    }

    public static boolean hasHillStep(ItemStack stack, EntityPlayer player) {
        if (!InfinityArmorConfigHelpers.getBoolean(stack, InfinityArmorConfigKeys.ARMOR_HILL_STEP, true)) {
            return false;
        }
        if (!InfinityArmorConfigHelpers.getBoolean(stack, InfinityArmorConfigKeys.ARMOR_SPRINT_ONLY, false)) {
            return true;
        }
        return InfinityArmorConfigHelpers.isSprintConditionActive(stack, player);
    }

    public static boolean[] getFlightState(ItemStack stack) {
        return new boolean[] { true,
                InfinityArmorConfigHelpers.getBoolean(stack, InfinityArmorConfigKeys.ARMOR_FLIGHT_LOCK, false),
                InfinityArmorConfigHelpers
                        .getBoolean(stack, InfinityArmorConfigKeys.ARMOR_INERTIA_CANCELLATION, false) };
    }

    public static float getFlightSpeedModifier(ItemStack stack, EntityPlayer player) {
        return InfinityArmorConfigHelpers.getConditionalFlightMultiplier(
                stack,
                player,
                InfinityArmorConfigKeys.ARMOR_FLIGHT_SPEED_MULT,
                LEGACY_FLIGHT_SPEED_MULTIPLIER);
    }

    public static float getFlightVerticalModifier(ItemStack stack, EntityPlayer player) {
        return InfinityArmorConfigHelpers.getConditionalFlightMultiplier(
                stack,
                player,
                InfinityArmorConfigKeys.ARMOR_VERTICAL_ACCELERATION,
                LEGACY_VERTICAL_ACCELERATION);
    }
}
