package fox.spiteful.avaritia.render;

import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public interface IHaloRenderItem {

    public boolean drawHalo(ItemStack stack);

    public IIcon getHaloTexture(ItemStack stack);

    public int getHaloSize(ItemStack stack);

    public boolean drawPulseEffect(ItemStack stack);

    public int getHaloColour(ItemStack stack);
}
