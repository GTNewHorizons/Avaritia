package fox.spiteful.avaritia.compat.draconicevolution;

import java.util.Collection;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import it.unimi.dsi.fastutil.ints.IntArrayList;

public class InfinityArmorEffectHandler {

    private InfinityArmorEffectHandler() {}

    public static void handleArmorTick(EntityPlayer player, ItemStack stack, int armorType) {
        if (armorType == 0) {
            handleHelmetTick(player, stack);
            return;
        }
        if (armorType == 1) {
            handleChestTick(player, stack);
            return;
        }
        if (armorType == 2) {
            handleLeggingsTick(player, stack);
        }
    }

    private static void handleHelmetTick(EntityPlayer player, ItemStack stack) {
        boolean nightVisionActive = InfinityArmorConfigHelpers
                .getBoolean(stack, InfinityArmorConfigKeys.ARMOR_NIGHT_VISION_ACTIVE, true);
        boolean nightVisionLock = InfinityArmorConfigHelpers
                .getBoolean(stack, InfinityArmorConfigKeys.ARMOR_NIGHT_VISION_LOCK, true);
        boolean lowLight = player.worldObj
                .getBlockLightValue((int) Math.floor(player.posX), (int) player.posY + 1, (int) Math.floor(player.posZ))
                < 5;

        if (nightVisionActive && (nightVisionLock || lowLight)) {
            player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 419, 0, true));
        } else if (player.isPotionActive(Potion.nightVision.id)) {
            player.removePotionEffect(Potion.nightVision.id);
        }

        if (InfinityArmorConfigHelpers.getBoolean(stack, InfinityArmorConfigKeys.ARMOR_WATER_BREATHING, true)) {
            player.setAir(300);
        }
        if (InfinityArmorConfigHelpers.getBoolean(stack, InfinityArmorConfigKeys.ARMOR_AUTO_FEED, true)) {
            player.getFoodStats().addStats(20, 20F);
        }
    }

    private static void handleChestTick(EntityPlayer player, ItemStack stack) {
        if (!InfinityArmorConfigHelpers.getBoolean(stack, InfinityArmorConfigKeys.ARMOR_REMOVE_NEGATIVE, true)) {
            return;
        }

        Collection<PotionEffect> effects = player.getActivePotionEffects();
        if (effects.isEmpty()) {
            return;
        }

        IntArrayList negativeEffectIds = null;
        for (PotionEffect potion : effects) {
            if (Potion.potionTypes[potion.getPotionID()].isBadEffect) {
                if (negativeEffectIds == null) {
                    negativeEffectIds = new IntArrayList();
                }
                negativeEffectIds.add(potion.getPotionID());
            }
        }

        if (negativeEffectIds == null || negativeEffectIds.isEmpty()) {
            return;
        }

        for (int potionId : negativeEffectIds) {
            player.removePotionEffect(potionId);
        }
    }

    private static void handleLeggingsTick(EntityPlayer player, ItemStack stack) {
        if (InfinityArmorConfigHelpers.getBoolean(stack, InfinityArmorConfigKeys.ARMOR_EXTINGUISH, true)
                && player.isBurning()) {
            player.extinguish();
        }
    }
}
