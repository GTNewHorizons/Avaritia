package fox.spiteful.avaritia.compat.bloodmagic;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import WayofTime.alchemicalWizardry.api.altarRecipeRegistry.AltarRecipeRegistry;
import WayofTime.alchemicalWizardry.api.items.interfaces.IBindable;
import WayofTime.alchemicalWizardry.api.items.interfaces.IBloodOrb;
import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;
import fox.spiteful.avaritia.Avaritia;
import fox.spiteful.avaritia.items.LudicrousItems;

public class ItemOrbArmok extends Item implements IBloodOrb, IBindable {

    public ItemOrbArmok() {
        setMaxStackSize(1);
        this.setUnlocalizedName("orb_armok");
        this.setTextureName("avaritia:orb_armok");
        setCreativeTab(Avaritia.tab);
        AltarRecipeRegistry.registerAltarOrbRecipe(new ItemStack(this), 1, 140);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
        if (!world.isRemote) SoulNetworkHandler.checkAndSetItemOwner(itemstack, player);

        return itemstack;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
        if (!world.isRemote && entity instanceof EntityPlayer) {
            NBTTagCompound itemTag = stack.stackTagCompound;
            if (itemTag == null || itemTag.getString("ownerName").equals("")) return;

            SoulNetworkHandler
                    .addCurrentEssenceToMaximum(itemTag.getString("ownerName"), Integer.MAX_VALUE, getMaxEssence());
        }
    }

    @Override
    public void addInformation(ItemStack item, EntityPlayer player, List<String> tooltip, boolean wut) {
        tooltip.add(StatCollector.translateToLocal("tooltip.armok.desc"));
        tooltip.add(StatCollector.translateToLocal("tooltip.armok.desc2"));
        tooltip.add(StatCollector.translateToLocalFormatted("tooltip.armok.capacity", getMaxEssence()));
        addBindingInformation(item, tooltip);
    }

    @Override
    public int getMaxEssence() {
        return 1000000000;
    }

    @Override
    public int getOrbLevel() {
        return 6;
    }

    @Override
    public boolean isFilledForFree() {
        return true;
    }

    @Override
    public EnumRarity getRarity(ItemStack itemstack) {
        return LudicrousItems.cosmic;
    }

    @Override
    public boolean hasContainerItem() {
        return true;
    }

    @Override
    public boolean doesContainerItemLeaveCraftingGrid(ItemStack itemStack) {
        return false;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        return itemStack;
    }

}
