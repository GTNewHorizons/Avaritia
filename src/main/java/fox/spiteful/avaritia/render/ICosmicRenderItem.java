package fox.spiteful.avaritia.render;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public interface ICosmicRenderItem {

    public IIcon getMaskTexture(ItemStack stack, EntityPlayer player);

    public float getMaskMultiplier(ItemStack stack, EntityPlayer player);
}
