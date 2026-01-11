package fox.spiteful.avaritia.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMatterClusterOpener extends ItemBlock {

    public ItemMatterClusterOpener(Block block) {
        super(block);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);

        tooltip.add(StatCollector.translateToLocal("tooltip.matter-cluster-decompressor.line1"));
        tooltip.add(StatCollector.translateToLocal("tooltip.matter-cluster-decompressor.line2"));
        tooltip.add(StatCollector.translateToLocal("tooltip.matter-cluster-decompressor.line3"));
    }
}
