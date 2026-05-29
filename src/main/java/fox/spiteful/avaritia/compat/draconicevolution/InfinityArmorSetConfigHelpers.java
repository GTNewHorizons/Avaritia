package fox.spiteful.avaritia.compat.draconicevolution;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import fox.spiteful.avaritia.items.LudicrousItems;

public class InfinityArmorSetConfigHelpers {

    private InfinityArmorSetConfigHelpers() {}

    public static boolean isInfinitySetEquipped(EntityPlayer player) {
        return LudicrousItems.isInfinite(player);
    }

    public static boolean isDamageImmune(EntityPlayer player) {
        ItemStack chest = player.getEquipmentInSlot(3);
        if (chest == null || chest.getItem() != LudicrousItems.infinity_armor) {
            return false;
        }
        return InfinityArmorConfigHelpers.getBoolean(chest, InfinityArmorConfigKeys.ARMOR_DAMAGE_IMMUNITY, true);
    }

    public static boolean isDeathImmune(EntityPlayer player) {
        ItemStack chest = player.getEquipmentInSlot(3);
        if (chest == null || chest.getItem() != LudicrousItems.infinity_armor) {
            return false;
        }
        return InfinityArmorConfigHelpers.getBoolean(chest, InfinityArmorConfigKeys.ARMOR_DEATH_IMMUNITY, true);
    }
}
