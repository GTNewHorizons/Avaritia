package fox.spiteful.avaritia.compat.minetweaker;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import mantle.utils.ItemMetaWrapper;

public class ItemMetaNBTWrapper extends ItemMetaWrapper {

    public final NBTTagCompound nbt;

    public ItemMetaNBTWrapper(Item item, Integer meta, NBTTagCompound nbt) {
        super(item, meta);
        this.nbt = nbt;
    }

    public ItemMetaNBTWrapper(ItemStack stack) {
        super(stack);
        this.nbt = stack.stackTagCompound;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ItemMetaNBTWrapper that = (ItemMetaNBTWrapper) o;

        if (nbt != null ? !nbt.equals(that.nbt) : that.nbt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (nbt != null ? nbt.hashCode() : 0);
        return result;
    }
}
