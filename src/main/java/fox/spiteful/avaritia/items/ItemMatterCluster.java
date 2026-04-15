package fox.spiteful.avaritia.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fox.spiteful.avaritia.items.tools.ToolHelper;
import fox.spiteful.avaritia.render.ICosmicRenderItem;

public class ItemMatterCluster extends Item implements ICosmicRenderItem {

    public static final String MAINTAG = "clusteritems";
    public static final String LISTTAG = "items";
    public static final String ITEMTAG = "item";
    public static final String COUNTTAG = "count";
    public static final String MAINCOUNTTAG = "total";

    /// The max number of items a normal cluster will store. Avaritia tools cannot generate clusters bigger than this,
    /// and this is the maximum that clusters will automatically combine into.
    /// <p/>
    /// It's possible to generate a super-critical (>max capacity) cluster via [#makeCluster(ItemStack)] for situations
    /// where this limit is too low, but care should be taken to avoid allowing the player to automate those clusters
    /// since they can easily be used for near-infinite item storage.
    public static final int MAX_NORMAL_CAPACITY = 64 * 256;
    public static final int MAX_SUPER_CRITICAL_CAPACITY = Integer.MAX_VALUE;

    public IIcon iconFull;
    public IIcon cosmicIcon;
    public IIcon cosmicIconFull;

    public ItemMatterCluster() {
        this.setMaxStackSize(1);
        this.setUnlocalizedName("avaritia_mattercluster");
        this.setTextureName("avaritia:mattercluster");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir) {
        super.registerIcons(ir);

        this.cosmicIcon = ir.registerIcon("avaritia:mattercluster_mask");

        this.iconFull = ir.registerIcon("avaritia:mattercluster_full");
        this.cosmicIconFull = ir.registerIcon("avaritia:mattercluster_full_mask");
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return LudicrousItems.cosmic;
    }

    // spotless:off
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean debug) {
        if (!stack.hasTagCompound() || !stack.getTagCompound().hasKey(MAINTAG)) {
            return;
        }
        NBTTagCompound clustertag = stack.getTagCompound().getCompoundTag(MAINTAG);
        int clusterSize = clustertag.getInteger(MAINCOUNTTAG);
        int maxCapacity = clusterSize > MAX_NORMAL_CAPACITY ? MAX_SUPER_CRITICAL_CAPACITY : MAX_NORMAL_CAPACITY;
        tooltip.add(clusterSize + "/" + maxCapacity + " " + StatCollector.translateToLocal("tooltip.matter_cluster.counter"));
        tooltip.add("");

        if (GuiScreen.isShiftKeyDown()) {
            NBTTagList list = clustertag.getTagList(LISTTAG, 10);
            for (int i = 0; i < list.tagCount(); i++) {
                NBTTagCompound tag = list.getCompoundTagAt(i);
                ItemStack countstack = ItemStack.loadItemStackFromNBT(tag.getCompoundTag(ITEMTAG));
                int count = tag.getInteger(COUNTTAG);
                if (countstack != null) {
                    tooltip.add(countstack.getItem().getRarity(countstack).rarityColor + countstack.getDisplayName() + EnumChatFormatting.GRAY + " x " + count);
                } else {
                    tooltip.add(EnumChatFormatting.RED + "DELETED" + EnumChatFormatting.GRAY + " x " + count);
                }
            }
        } else {
            tooltip.add(EnumChatFormatting.DARK_GRAY + StatCollector.translateToLocal("tooltip.matter_cluster.desc"));
            tooltip.add(EnumChatFormatting.DARK_GRAY + StatCollector.translateToLocal("tooltip.matter_cluster.desc3"));
            tooltip.add(EnumChatFormatting.DARK_GRAY.toString() + EnumChatFormatting.ITALIC + StatCollector.translateToLocal("tooltip.matter_cluster.desc2"));
        }
    }
    //spotless:on

    public static List<ItemStack> makeClusters(List<ItemStack> input) {
        Map<ItemStackWrapper, Integer> items = ToolHelper.collateMatterCluster(input);
        List<ItemStack> clusters = new ArrayList<>();
        List<Entry<ItemStackWrapper, Integer>> itemlist = new ArrayList<>(items.entrySet());

        int currentTotal = 0;
        Map<ItemStackWrapper, Integer> currentItems = new HashMap<>();

        while (!itemlist.isEmpty()) {
            Entry<ItemStackWrapper, Integer> e = itemlist.get(0);
            ItemStackWrapper wrap = e.getKey();
            int wrapcount = e.getValue();

            int count = Math.min(MAX_NORMAL_CAPACITY - currentTotal, wrapcount);

            if (!currentItems.containsKey(e.getKey())) {
                currentItems.put(wrap, count);
            } else {
                currentItems.put(wrap, currentItems.get(wrap) + count);
            }
            currentTotal += count;

            e.setValue(wrapcount - count);
            if (e.getValue() == 0) {
                itemlist.remove(0);
            }

            if (currentTotal == MAX_NORMAL_CAPACITY) {
                ItemStack cluster = makeCluster(currentItems);

                clusters.add(cluster);

                currentTotal = 0;
                currentItems = new HashMap<>();
            }
        }

        if (currentTotal > 0) {
            ItemStack cluster = makeCluster(currentItems);

            clusters.add(cluster);
        }

        return clusters;
    }

    public static ItemStack makeCluster(Map<ItemStackWrapper, Integer> input) {
        ItemStack cluster = new ItemStack(LudicrousItems.matter_cluster);
        int total = 0;
        for (int num : input.values()) {
            total += num;
        }
        setClusterData(cluster, input, total);
        return cluster;
    }

    public static ItemStack makeCluster(ItemStack input) {
        HashMap<ItemStackWrapper, Integer> map = new HashMap<>();

        ItemStack input2 = input.copy();
        input2.stackSize = 1;
        map.put(new ItemStackWrapper(input2), input.stackSize);

        ItemStack cluster = new ItemStack(LudicrousItems.matter_cluster);
        setClusterData(cluster, map, input.stackSize);
        return cluster;
    }

    public static Map<ItemStackWrapper, Integer> getClusterData(ItemStack cluster) {
        if (!cluster.hasTagCompound() || !cluster.getTagCompound().hasKey(MAINTAG)) {
            return new HashMap<>();
        }
        NBTTagCompound tag = cluster.getTagCompound().getCompoundTag(MAINTAG);
        NBTTagList list = tag.getTagList(LISTTAG, 10);
        Map<ItemStackWrapper, Integer> data = new HashMap<>();

        for (int i = 0; i < list.tagCount(); i++) {
            NBTTagCompound entry = list.getCompoundTagAt(i);
            final ItemStack stack = ItemStack.loadItemStackFromNBT(entry.getCompoundTag(ITEMTAG));
            if (stack == null) {
                // item might be null if the cluster contains items that don't exist
                // anymore by removing a mod for example
                continue;
            }
            ItemStackWrapper wrap = new ItemStackWrapper(stack);
            int count = entry.getInteger(COUNTTAG);
            data.put(wrap, count);
        }
        return data;
    }

    public static int getClusterSize(ItemStack cluster) {
        if (!cluster.hasTagCompound() || !cluster.getTagCompound().hasKey(MAINTAG)) {
            return 0;
        }
        return cluster.getTagCompound().getCompoundTag(MAINTAG).getInteger(MAINCOUNTTAG);
    }

    public static boolean isClusterFull(ItemStack cluster) {
        return getClusterSize(cluster) >= MAX_NORMAL_CAPACITY;
    }

    public static void setClusterData(ItemStack stack, Map<ItemStackWrapper, Integer> data, int count) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }

        NBTTagCompound clustertag = new NBTTagCompound();
        NBTTagList list = new NBTTagList();

        for (Entry<ItemStackWrapper, Integer> entry : data.entrySet()) {
            NBTTagCompound itemtag = new NBTTagCompound();
            itemtag.setTag(ITEMTAG, entry.getKey().stack.writeToNBT(new NBTTagCompound()));
            itemtag.setInteger(COUNTTAG, entry.getValue());
            list.appendTag(itemtag);
        }
        clustertag.setTag(LISTTAG, list);
        clustertag.setInteger(MAINCOUNTTAG, count);
        stack.getTagCompound().setTag(MAINTAG, clustertag);
    }

    public static void mergeClusters(ItemStack donor, ItemStack recipient) {
        int donorcount = getClusterSize(donor);
        int recipientcount = getClusterSize(recipient);

        if (donorcount == 0 || donorcount >= MAX_NORMAL_CAPACITY || recipientcount >= MAX_NORMAL_CAPACITY) {
            return;
        }

        Map<ItemStackWrapper, Integer> donordata = getClusterData(donor);
        Map<ItemStackWrapper, Integer> recipientdata = getClusterData(recipient);
        List<Entry<ItemStackWrapper, Integer>> datalist = new ArrayList<>(donordata.entrySet());

        while (recipientcount < MAX_NORMAL_CAPACITY && donorcount > 0) {
            Entry<ItemStackWrapper, Integer> e = datalist.get(0);
            ItemStackWrapper wrap = e.getKey();
            int wrapcount = e.getValue();

            int count = Math.min(MAX_NORMAL_CAPACITY - recipientcount, wrapcount);

            if (!recipientdata.containsKey(wrap)) {
                recipientdata.put(wrap, count);
            } else {
                recipientdata.put(wrap, recipientdata.get(wrap) + count);
            }

            donorcount -= count;
            recipientcount += count;

            if (wrapcount - count > 0) {
                e.setValue(wrapcount - count);
            } else {
                donordata.remove(wrap);
                datalist.remove(0);
            }
        }
        setClusterData(recipient, recipientdata, recipientcount);

        if (donorcount > 0) {
            setClusterData(donor, donordata, donorcount);
        } else {
            donor.setTagCompound(null);
            donor.stackSize = 0;
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        // Do nothing for super critical clusters
        if (getClusterSize(stack) > MAX_NORMAL_CAPACITY) return stack;

        if (!world.isRemote) {
            List<ItemStack> drops = ToolHelper.collateMatterClusterContents(ItemMatterCluster.getClusterData(stack));

            for (ItemStack drop : drops) {
                ToolHelper.dropItem(
                        drop,
                        world,
                        MathHelper.floor_double(player.posX),
                        MathHelper.floor_double(player.posY),
                        MathHelper.floor_double(player.posZ));
            }
        }

        stack.stackSize = 0;
        return stack;
    }

    @Override
    public IIcon getMaskTexture(ItemStack stack, EntityPlayer player) {
        int count = getClusterSize(stack);
        if (count >= MAX_NORMAL_CAPACITY) {
            return cosmicIconFull;
        }
        return cosmicIcon;
    }

    @Override
    public float getMaskMultiplier(ItemStack stack, EntityPlayer player) {
        int count = getClusterSize(stack);
        return Math.min(1f, count / (float) MAX_NORMAL_CAPACITY);
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        int count = getClusterSize(stack);
        if (count >= MAX_NORMAL_CAPACITY) {
            return iconFull;
        }
        return super.getIcon(stack, pass);
    }

    @Override
    public IIcon getIconIndex(ItemStack stack) {
        return this.getIcon(stack, 0);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        int count = getClusterSize(stack);
        if (count == MAX_NORMAL_CAPACITY) {
            return super.getUnlocalizedName(stack) + ".full";
        }
        if (count > MAX_NORMAL_CAPACITY) {
            return super.getUnlocalizedName(stack) + ".veryfull";
        }
        return super.getUnlocalizedName(stack);
    }
}
