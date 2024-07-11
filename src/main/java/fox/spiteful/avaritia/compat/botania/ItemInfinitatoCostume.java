package fox.spiteful.avaritia.compat.botania;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemInfinitatoCostume extends Item {

    public static final String[] types = new String[] { "armstrong", "moon", "egbert", "francis" };

    public IIcon[] icons;

    public ItemInfinitatoCostume() {
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir) {
        icons = new IIcon[types.length];

        for (int x = 0; x < types.length; x++) {
            icons[x] = ir.registerIcon("avaritia:" + "costume_" + types[x]);
        }

    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> list) {}

    @Override
    public IIcon getIconFromDamage(int dam) {
        return this.icons[dam % icons.length];
    }
}
