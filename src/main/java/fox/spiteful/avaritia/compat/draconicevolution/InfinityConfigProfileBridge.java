package fox.spiteful.avaritia.compat.draconicevolution;

import net.minecraft.item.ItemStack;

import com.brandon3055.draconicevolution.common.utils.IConfigurableItem;

import cpw.mods.fml.common.Optional;

public class InfinityConfigProfileBridge {

    private InfinityConfigProfileBridge() {}

    @Optional.Method(modid = "DraconicEvolution")
    public static boolean getBoolean(ItemStack stack, String key, boolean defaultValue) {
        return IConfigurableItem.ProfileHelper.getBoolean(stack, key, defaultValue);
    }

    @Optional.Method(modid = "DraconicEvolution")
    public static float getFloat(ItemStack stack, String key, float defaultValue) {
        return IConfigurableItem.ProfileHelper.getFloat(stack, key, defaultValue);
    }
}
