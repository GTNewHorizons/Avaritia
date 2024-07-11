package fox.spiteful.avaritia.compat.forestry;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fox.spiteful.avaritia.Avaritia;
import fox.spiteful.avaritia.items.LudicrousItems;
import fox.spiteful.avaritia.render.IHaloRenderItem;

public class ItemBeesource extends Item implements IHaloRenderItem {

    private static final String[] types = new String[] { "infinity_drop", "dust" };

    public IIcon[] icons;

    public IIcon halo;

    public ItemBeesource() {
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setUnlocalizedName("avaritia_beesource");
        this.setCreativeTab(Avaritia.tab);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir) {
        icons = new IIcon[types.length];

        for (int x = 0; x < types.length; x++) {
            icons[x] = ir.registerIcon("avaritia:" + "resource_" + types[x]);
        }

        halo = ir.registerIcon("avaritia:halo");
    }

    @Override
    public IIcon getIconFromDamage(int dam) {
        return this.icons[dam % icons.length];
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        int i = MathHelper.clamp_int(stack.getItemDamage(), 0, types.length);
        return super.getUnlocalizedName() + "." + types[i];
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> list) {
        for (int j = 0; j < types.length; ++j) {
            list.add(new ItemStack(item, 1, j));
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        switch (stack.getItemDamage()) {
            case 0:
                return LudicrousItems.cosmic;
            case 1:
                return Ranger.trash;
            default:
                return EnumRarity.common;
        }
    }

    @Override
    public boolean drawHalo(ItemStack stack) {
        int meta = stack.getItemDamage();
        return (meta == 0);
    }

    @Override
    public IIcon getHaloTexture(ItemStack stack) {
        return halo;
    }

    @Override
    public int getHaloSize(ItemStack stack) {
        return 10;
    }

    @Override
    public boolean drawPulseEffect(ItemStack stack) {
        int meta = stack.getItemDamage();
        return meta == 0;
    }

    @Override
    public int getHaloColour(ItemStack stack) {
        return 0xFF000000;
    }
}
