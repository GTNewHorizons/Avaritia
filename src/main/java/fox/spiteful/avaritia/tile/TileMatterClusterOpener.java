package fox.spiteful.avaritia.tile;

import java.util.Map;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.oredict.OreDictionary;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.cleanroommc.modularui.api.IGuiHolder;
import com.cleanroommc.modularui.drawable.AdaptableUITexture;
import com.cleanroommc.modularui.drawable.GuiTextures;
import com.cleanroommc.modularui.drawable.UITexture;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.utils.Alignment;
import com.cleanroommc.modularui.utils.item.IItemHandlerModifiable;
import com.cleanroommc.modularui.utils.item.InvWrapper;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widgets.SlotGroupWidget;
import com.cleanroommc.modularui.widgets.TextWidget;
import com.cleanroommc.modularui.widgets.layout.Column;
import com.cleanroommc.modularui.widgets.layout.Row;
import com.cleanroommc.modularui.widgets.slot.FluidSlot;
import com.cleanroommc.modularui.widgets.slot.ItemSlot;
import com.cleanroommc.modularui.widgets.slot.ModularSlot;
import com.glodblock.github.common.item.ItemFluidDrop;
import com.gtnewhorizon.gtnhlib.capability.CapabilityProvider;
import com.gtnewhorizon.gtnhlib.capability.item.ItemSink;
import com.gtnewhorizon.gtnhlib.capability.item.ItemSource;
import com.gtnewhorizon.gtnhlib.item.InventoryItemSink;
import com.gtnewhorizon.gtnhlib.item.InventoryItemSource;
import com.gtnewhorizon.gtnhlib.util.ItemUtil;
import com.gtnewhorizon.gtnhlib.util.data.LazyItem;

import fox.spiteful.avaritia.Mods;
import fox.spiteful.avaritia.items.ItemMatterCluster;
import fox.spiteful.avaritia.items.ItemStackWrapper;

public class TileMatterClusterOpener extends TileEntity
        implements ISidedInventory, CapabilityProvider, IGuiHolder<PosGuiData>, IFluidHandler {

    private ItemStack clusterInput, itemOutput;
    private final FluidTank fluidOutput = new FluidTank(Integer.MAX_VALUE);

    private static final LazyItem FLUID_DROPS = new LazyItem(
            Mods.AE2FluidCraft,
            "fluid_drop",
            OreDictionary.WILDCARD_VALUE);

    private static final int[] INPUT_SLOTS = { 0 };
    private static final int[] OUTPUT_SLOTS = { 1 };

    @Override
    public void updateEntity() {
        super.updateEntity();

        if (worldObj.isRemote) return;

        if (clusterInput != null && clusterInput.stackSize <= 0) {
            clusterInput = null;
            markDirty();
        }

        if (itemOutput != null && itemOutput.stackSize <= 0) {
            itemOutput = null;
            markDirty();
        }

        if (fluidOutput.getFluid() != null && fluidOutput.getFluidAmount() <= 0) {
            fluidOutput.setFluid(null);
            markDirty();
        }

        if (clusterInput == null) return;
        if (itemOutput != null || fluidOutput.getFluid() != null) return;

        Map<ItemStackWrapper, Integer> contents = ItemMatterCluster.getClusterData(clusterInput);

        if (contents.isEmpty()) return;

        var iter = contents.entrySet().iterator();

        var e = iter.next();
        iter.remove();

        if (contents.isEmpty()) {
            clusterInput = null;
        } else {
            ItemMatterCluster.setClusterData(clusterInput, contents, contents.values().stream().mapToInt(i -> i).sum());
        }

        itemOutput = ItemUtil.copyAmount(e.getValue(), e.getKey().stack);

        if (FLUID_DROPS.isLoaded() && FLUID_DROPS.matches(itemOutput)) {
            fluidOutput.setFluid(ItemFluidDrop.getFluidStack(itemOutput));
            itemOutput = null;
        }

        markDirty();
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        if (clusterInput != null) {
            compound.setTag("clusterInput", clusterInput.writeToNBT(new NBTTagCompound()));
        }

        if (itemOutput != null) {
            compound.setTag("itemOutput", itemOutput.writeToNBT(new NBTTagCompound()));
        }

        if (fluidOutput.getFluid() != null) {
            compound.setTag("fluidOutput", fluidOutput.getFluid().writeToNBT(new NBTTagCompound()));
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        clusterInput = null;
        itemOutput = null;
        fluidOutput.setFluid(null);

        if (compound.hasKey("clusterInput")) {
            clusterInput = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("clusterInput"));
        }

        if (compound.hasKey("itemOutput")) {
            itemOutput = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("itemOutput"));
        }

        if (compound.hasKey("fluidOutput")) {
            fluidOutput.setFluid(FluidStack.loadFluidStackFromNBT(compound.getCompoundTag("fluidOutput")));
        }
    }

    public void dropContents() {
        if (clusterInput != null) {
            EntityItem item = new EntityItem(worldObj, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, clusterInput);
            worldObj.spawnEntityInWorld(item);
            clusterInput = null;
        }

        if (itemOutput != null) {
            ItemStack cluster = ItemMatterCluster.makeCluster(itemOutput);
            itemOutput = null;

            EntityItem item = new EntityItem(worldObj, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, cluster);
            worldObj.spawnEntityInWorld(item);
        }

        if (FLUID_DROPS.isLoaded() && fluidOutput.getFluidAmount() > 0) {
            ItemStack drops = ItemFluidDrop.newStack(fluidOutput.getFluid());
            fluidOutput.setFluid(null);

            assert drops != null;
            ItemStack cluster = ItemMatterCluster.makeCluster(drops);

            EntityItem item = new EntityItem(worldObj, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, cluster);
            worldObj.spawnEntityInWorld(item);
        }
    }

    @Override
    public <T> @Nullable T getCapability(@NotNull Class<T> capability, @NotNull ForgeDirection side) {
        if (capability == ItemSource.class) {
            return capability.cast(new OutputSlotSource());
        }

        if (capability == ItemSink.class) {
            return capability.cast(new InventoryItemSink(this, side));
        }

        return null;
    }

    private class OutputSlotSource extends InventoryItemSource {

        public OutputSlotSource() {
            super(TileMatterClusterOpener.this, ForgeDirection.UNKNOWN);
        }

        @Override
        protected int[] getSlots() {
            return OUTPUT_SLOTS;
        }
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return INPUT_SLOTS;
    }

    @Override
    public boolean canInsertItem(int slotIndex, ItemStack stack, int side) {
        return slotIndex == 0;
    }

    @Override
    public boolean canExtractItem(int slotIndex, ItemStack stack, int side) {
        return slotIndex == 0;
    }

    @Override
    public int getSizeInventory() {
        return 2;
    }

    @Override
    public ItemStack getStackInSlot(int slotIn) {
        if (slotIn == 0) return clusterInput;
        if (slotIn == 1) return itemOutput;

        return null;
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        if (index != 0) return null;
        if (clusterInput == null) return null;

        int removable = Math.min(count, clusterInput.stackSize);

        ItemStack out = clusterInput.splitStack(removable);

        if (ItemUtil.isStackEmpty(clusterInput)) clusterInput = null;

        markDirty();

        return out;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int index) {
        return decrStackSize(index, Integer.MAX_VALUE);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        if (index == 0) {
            clusterInput = stack;
        }

        if (index == 1) {
            itemOutput = stack;
        }
    }

    @Override
    public String getInventoryName() {
        return getBlockType().getLocalizedName();
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if (index == 0) {
            return stack.getItem() instanceof ItemMatterCluster;
        } else if (index == 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        return 0;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return fluidOutput.getFluid() != null && fluidOutput.getFluid().getFluid() == fluid;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        FluidStack stored = fluidOutput.getFluid();

        if (stored == null || stored.amount <= 0) return null;
        if (stored.getFluid() != resource.getFluid()) return null;

        return drain(from, resource.amount, doDrain);
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        FluidStack stored = fluidOutput.getFluid();

        if (stored == null || stored.amount <= 0) return null;

        int removable = Math.min(stored.amount, maxDrain);

        FluidStack out = new FluidStack(stored.getFluid(), removable);

        if (doDrain) {
            stored.amount -= removable;

            if (stored.amount <= 0) {
                fluidOutput.setFluid(null);
            }

            markDirty();
        }

        return out;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[] { fluidOutput.getInfo() };
    }

    public static final UITexture CLUSTER_SLOT_BACKGROUND = AdaptableUITexture.builder()
            .location("avaritia", "gui/slot/cluster_background.png").imageSize(16, 16).build();

    @Override
    public ModularPanel buildUI(PosGuiData data, PanelSyncManager syncManager, UISettings settings) {
        IItemHandlerModifiable inv = new InvWrapper(this);

        syncManager.registerSlotGroup("input", 1, 150);
        syncManager.registerSlotGroup("output", 1, false);

        // spotless:off
        return new ModularPanel("ClusterOpener")
            .size(178, 152)
            .padding(8)
            .child(new Column()
                .sizeRel(1)
                .expanded()
                .child(new TextWidget<>(getInventoryName()).height(16).alignX(0.5f).align(Alignment.TopCenter))
                .child(new Row().widthRel(1)
                    .alignX(0.5f)
                    .expanded()
                    .child(new ItemSlot()
                        .alignY(0.5f)
                        .leftRel(0.5f, -18, 0.5f)
                        .slot(new ModularSlot(inv, 0).slotGroup("input"))
                        .background(GuiTextures.SLOT_ITEM, CLUSTER_SLOT_BACKGROUND))
                    .child(new ItemSlot()
                        .alignY(0.5f)
                        .leftRel(0.5f, 18, 0.5f)
                        .slot(new ModularSlot(inv, 1).slotGroup("output").accessibility(false, false)))
                    .child(new FluidSlot()
                        .alignY(0.5f)
                        .leftRel(0.5f, 36, 0.5f)
                        .syncHandler(fluidOutput)))
                .child(new Row()
                    .widthRel(1)
                    .height(76)
                    .alignX(0.5f)
                    .child(SlotGroupWidget.playerInventory(false))));
        // spotless:on
    }
}
