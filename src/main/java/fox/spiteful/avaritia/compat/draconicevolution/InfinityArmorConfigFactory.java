package fox.spiteful.avaritia.compat.draconicevolution;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

import com.brandon3055.draconicevolution.common.lib.References;
import com.brandon3055.draconicevolution.common.utils.ItemConfigField;

public class InfinityArmorConfigFactory {

    private InfinityArmorConfigFactory() {}

    public static List<ItemConfigField> createFields(ItemStack stack, int slot, int armorType) {
        List<ItemConfigField> fields = new ArrayList<>();

        if (armorType == 0) {
            fields.add(
                    new ItemConfigField(References.BOOLEAN_ID, slot, InfinityArmorConfigKeys.ARMOR_NIGHT_VISION_ACTIVE)
                            .readFromItem(stack, true));
            fields.add(
                    new ItemConfigField(References.BOOLEAN_ID, slot, InfinityArmorConfigKeys.ARMOR_NIGHT_VISION_LOCK)
                            .readFromItem(stack, true));
            fields.add(
                    new ItemConfigField(References.BOOLEAN_ID, slot, InfinityArmorConfigKeys.ARMOR_WATER_BREATHING)
                            .readFromItem(stack, true));
            fields.add(
                    new ItemConfigField(References.BOOLEAN_ID, slot, InfinityArmorConfigKeys.ARMOR_AUTO_FEED)
                            .readFromItem(stack, true));
        } else if (armorType == 1) {
            fields.add(
                    new ItemConfigField(References.FLOAT_ID, slot, InfinityArmorConfigKeys.ARMOR_VERTICAL_ACCELERATION)
                            .setMinMaxAndIncromente(0F, 8F, 0.1F).readFromItem(stack, 0F).setModifier("PLUSPERCENT"));
            fields.add(
                    new ItemConfigField(References.FLOAT_ID, slot, InfinityArmorConfigKeys.ARMOR_FLIGHT_SPEED_MULT)
                            .setMinMaxAndIncromente(0F, 6F, 0.1F).readFromItem(stack, 1F).setModifier("PLUSPERCENT"));
            fields.add(
                    new ItemConfigField(References.BOOLEAN_ID, slot, InfinityArmorConfigKeys.ARMOR_EFFECTIVE_ON_SPRINT)
                            .readFromItem(stack, false));
            fields.add(
                    new ItemConfigField(References.BOOLEAN_ID, slot, InfinityArmorConfigKeys.ARMOR_FLIGHT_LOCK)
                            .readFromItem(stack, false));
            fields.add(
                    new ItemConfigField(References.BOOLEAN_ID, slot, InfinityArmorConfigKeys.ARMOR_INERTIA_CANCELLATION)
                            .readFromItem(stack, false));
            fields.add(
                    new ItemConfigField(References.BOOLEAN_ID, slot, InfinityArmorConfigKeys.ARMOR_REMOVE_NEGATIVE)
                            .readFromItem(stack, true));
            fields.add(
                    new ItemConfigField(References.BOOLEAN_ID, slot, InfinityArmorConfigKeys.ARMOR_DAMAGE_IMMUNITY)
                            .readFromItem(stack, true));
            fields.add(
                    new ItemConfigField(References.BOOLEAN_ID, slot, InfinityArmorConfigKeys.ARMOR_DEATH_IMMUNITY)
                            .readFromItem(stack, true));
        } else if (armorType == 2) {
            fields.add(
                    new ItemConfigField(References.FLOAT_ID, slot, InfinityArmorConfigKeys.ARMOR_SPEED_MULT)
                            .setMinMaxAndIncromente(0F, 8F, 0.1F).readFromItem(stack, 0F).setModifier("PLUSPERCENT"));
            fields.add(
                    new ItemConfigField(References.BOOLEAN_ID, slot, InfinityArmorConfigKeys.ARMOR_SPRINT_ONLY)
                            .readFromItem(stack, false));
            fields.add(
                    new ItemConfigField(References.BOOLEAN_ID, slot, InfinityArmorConfigKeys.ARMOR_EXTINGUISH)
                            .readFromItem(stack, true));
        } else if (armorType == 3) {
            fields.add(
                    new ItemConfigField(References.FLOAT_ID, slot, InfinityArmorConfigKeys.ARMOR_JUMP_MULT)
                            .setMinMaxAndIncromente(0F, 15F, 0.1F).readFromItem(stack, 0F).setModifier("PLUSPERCENT"));
            fields.add(
                    new ItemConfigField(References.BOOLEAN_ID, slot, InfinityArmorConfigKeys.ARMOR_SPRINT_ONLY)
                            .readFromItem(stack, false));
            fields.add(
                    new ItemConfigField(References.BOOLEAN_ID, slot, InfinityArmorConfigKeys.ARMOR_HILL_STEP)
                            .readFromItem(stack, true));
        }

        return fields;
    }
}
