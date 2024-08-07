package fox.spiteful.avaritia.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import fox.spiteful.avaritia.Avaritia;
import fox.spiteful.avaritia.entity.EntityEndestPearl;
import fox.spiteful.avaritia.render.IHaloRenderItem;

public class ItemEndestPearl extends ItemEnderPearl implements IHaloRenderItem {

    public ItemEndestPearl() {
        this.setUnlocalizedName("avaritia_endest_pearl");
        this.setTextureName("avaritia:endestpearl");
        this.maxStackSize = 16;
        this.setCreativeTab(Avaritia.tab);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!player.capabilities.isCreativeMode) {
            --stack.stackSize;
        }

        world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!world.isRemote) {
            world.spawnEntityInWorld(new EntityEndestPearl(world, player));
        }

        return stack;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.rare;
    }

    @Override
    public boolean drawHalo(ItemStack stack) {
        return true;
    }

    @Override
    public IIcon getHaloTexture(ItemStack stack) {
        return ((ItemResource) LudicrousItems.resource).halo[0];
    }

    @Override
    public int getHaloSize(ItemStack stack) {
        return 4;
    }

    @Override
    public boolean drawPulseEffect(ItemStack stack) {
        return true;
    }

    @Override
    public int getHaloColour(ItemStack stack) {
        return 0xFF000000;
    }
}
