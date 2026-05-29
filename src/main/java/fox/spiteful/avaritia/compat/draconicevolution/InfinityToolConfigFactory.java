package fox.spiteful.avaritia.compat.draconicevolution;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

import com.brandon3055.draconicevolution.common.lib.References;
import com.brandon3055.draconicevolution.common.utils.ItemConfigField;

public class InfinityToolConfigFactory {

    private InfinityToolConfigFactory() {}

    public static List<ItemConfigField> createSwordFields(ItemStack stack, int slot) {
        List<ItemConfigField> fields = new ArrayList<>();
        fields.add(
                new ItemConfigField(References.BOOLEAN_ID, slot, InfinityToolConfigKeys.SWORD_EXECUTION_ENABLED)
                        .readFromItem(stack, true));
        fields.add(
                new ItemConfigField(References.BOOLEAN_ID, slot, InfinityToolConfigKeys.SWORD_CREATIVE_BYPASS_ENABLED)
                        .readFromItem(stack, true));
        fields.add(
                new ItemConfigField(References.FLOAT_ID, slot, InfinityToolConfigKeys.SWORD_ATTACK_DAMAGE)
                        .setMinMaxAndIncromente(1F, 10000F, 1F).readFromItem(stack, 1000F));
        return fields;
    }

    public static List<ItemConfigField> createPickaxeFields(ItemStack stack, int slot) {
        List<ItemConfigField> fields = new ArrayList<>();
        fields.add(
                new ItemConfigField(References.BOOLEAN_ID, slot, InfinityToolConfigKeys.PICKAXE_HAMMER_ENABLED)
                        .readFromItem(stack, true));
        fields.add(
                new ItemConfigField(References.FLOAT_ID, slot, InfinityToolConfigKeys.PICKAXE_HAMMER_RANGE)
                        .setMinMaxAndIncromente(1F, 16F, 1F).readFromItem(stack, 8F));
        return fields;
    }

    public static List<ItemConfigField> createAxeFields(ItemStack stack, int slot) {
        List<ItemConfigField> fields = new ArrayList<>();
        fields.add(
                new ItemConfigField(References.BOOLEAN_ID, slot, InfinityToolConfigKeys.AXE_VEIN_ENABLED)
                        .readFromItem(stack, true));
        fields.add(
                new ItemConfigField(References.FLOAT_ID, slot, InfinityToolConfigKeys.AXE_CLEAVE_RANGE)
                        .setMinMaxAndIncromente(1F, 24F, 1F).readFromItem(stack, 13F));
        return fields;
    }

    public static List<ItemConfigField> createShovelFields(ItemStack stack, int slot) {
        List<ItemConfigField> fields = new ArrayList<>();
        fields.add(
                new ItemConfigField(References.BOOLEAN_ID, slot, InfinityToolConfigKeys.SHOVEL_DESTROYER_ENABLED)
                        .readFromItem(stack, true));
        fields.add(
                new ItemConfigField(References.FLOAT_ID, slot, InfinityToolConfigKeys.SHOVEL_DESTROYER_RANGE)
                        .setMinMaxAndIncromente(1F, 16F, 1F).readFromItem(stack, 8F));
        return fields;
    }

    public static List<ItemConfigField> createBowFields(ItemStack stack, int slot) {
        List<ItemConfigField> fields = new ArrayList<>();
        fields.add(
                new ItemConfigField(References.BOOLEAN_ID, slot, InfinityToolConfigKeys.BOW_RAPID_FIRE_ENABLED)
                        .readFromItem(stack, true));
        fields.add(
                new ItemConfigField(References.BOOLEAN_ID, slot, InfinityToolConfigKeys.BOW_SWORD_RAIN_ENABLED)
                        .readFromItem(stack, true));
        return fields;
    }
}
