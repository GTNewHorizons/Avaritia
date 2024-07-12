package fox.spiteful.avaritia.items.tools;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import fox.spiteful.avaritia.Avaritia;

public class ItemSwordSkulls extends ItemSword {

    public ItemSwordSkulls() {
        super(ToolMaterial.EMERALD);
        setUnlocalizedName("skullfire_sword");
        setTextureName("avaritia:skull_sword");
        setCreativeTab(Avaritia.tab);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.epic;
    }

    @Override
    public void addInformation(ItemStack item, EntityPlayer player, List<String> tooltip, boolean wut) {
        tooltip.add(
                EnumChatFormatting.DARK_GRAY + ""
                        + EnumChatFormatting.ITALIC
                        + StatCollector.translateToLocal("tooltip.skullfire_sword.desc"));
    }
}
