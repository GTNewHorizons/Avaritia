package fox.spiteful.avaritia.render;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

public class CosmicBowRenderer extends CosmicItemRenderer implements IItemRenderer {

    @Override
    public void renderItem(ItemRenderType type, ItemStack stack, Object... data) {
        if (type == ItemRenderType.EQUIPPED) {
            GL11.glPushMatrix();
            GL11.glTranslatef(0.2F, -0.3F, 0.15F);
            super.renderItem(type, stack, data);
            GL11.glPopMatrix();
        } else {
            super.renderItem(type, stack, data);
        }
    }

    public static int getBowFrame(EntityPlayer player) {
        ItemStack inuse = player.getItemInUse();

        if (inuse != null) {
            int max = inuse.getMaxItemUseDuration();
            double pull = (max - player.getItemInUseCount()) / (double) max;
            return Math.max(0, (int) Math.ceil(pull * 3.0) - 1);
        }
        return 0;
    }

    @Override
    public IIcon getStackIcon(ItemStack stack, int pass, EntityPlayer player) {
        Item item = stack.getItem();
        ItemStack inuse;
        int time;
        if (player != null) {
            inuse = player.getItemInUse();
            time = player.getItemInUseCount();
        } else {
            inuse = stack;
            time = 0;
        }

        return item.getIcon(stack, pass, player, inuse, time);
    }
}
