package fox.spiteful.avaritia.items.tools;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

import com.brandon3055.draconicevolution.common.utils.IConfigurableItem;
import com.brandon3055.draconicevolution.common.utils.ItemConfigField;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fox.spiteful.avaritia.Avaritia;
import fox.spiteful.avaritia.compat.draconicevolution.InfinityToolConfigFactory;
import fox.spiteful.avaritia.compat.draconicevolution.InfinityToolRuntimeHelpers;
import fox.spiteful.avaritia.entity.EntityHeavenArrow;
import fox.spiteful.avaritia.render.CosmicBowRenderer;
import fox.spiteful.avaritia.render.ICosmicRenderItem;

@Optional.Interface(
        iface = "com.brandon3055.draconicevolution.common.utils.IConfigurableItem",
        modid = "DraconicEvolution")
public class ItemBowInfinity extends Item implements ICosmicRenderItem, IConfigurableItem {

    private static final int RAPID_FIRE_USE_DURATION = 13;
    private static final int STANDARD_BOW_USE_DURATION = 72000;
    private static final int BOW_FRAME_COUNT = 3;

    private IIcon[] iconArray;
    private IIcon[] maskArray;
    private IIcon idleMask;

    public ItemBowInfinity() {
        this.maxStackSize = 1;
        this.setMaxDamage(9999);
        this.setUnlocalizedName("infinity_bow");
        this.setTextureName("avaritia:infinity_bow");
        this.setCreativeTab(Avaritia.tab);
    }

    @Override
    public void setDamage(ItemStack stack, int damage) {
        super.setDamage(stack, 0);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int useCount) {
        if (!InfinityToolRuntimeHelpers.isBowRapidFireEnabled(stack)) {
            this.fire(stack, world, player, useCount);
        }
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
        if (InfinityToolRuntimeHelpers.isBowRapidFireEnabled(stack) && count == 1) {
            this.fire(stack, player.worldObj, player, count);
            player.setItemInUse(stack, RAPID_FIRE_USE_DURATION);
        }
    }

    public void fire(ItemStack stack, World world, EntityPlayer player, int useCount) {
        float maxf = (float) RAPID_FIRE_USE_DURATION;
        int j = getDrawProgress(stack, useCount);

        /*
         * ArrowLooseEvent event = new ArrowLooseEvent(player, stack, j); MinecraftForge.EVENT_BUS.post(event); if
         * (event.isCanceled()) { return; } j = event.charge;
         */

        boolean flag = true;// player.capabilities.isCreativeMode ||
                            // EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;

        if (flag || player.inventory.hasItem(Items.arrow)) {
            float f = j / maxf;
            f = (f * f + f * 2.0F) / 3.0F;

            if (f < 0.1) {
                return;
            }

            if (f > 1.0) {
                f = 1.0F;
            }

            EntityArrow entityarrow = new EntityHeavenArrow(world, player, f * 2.0F);
            if (entityarrow instanceof EntityHeavenArrow heavenArrow) {
                heavenArrow.setSwordRainEnabled(InfinityToolRuntimeHelpers.isBowSwordRainEnabled(stack));
            }
            entityarrow.setDamage(60.0);

            if (f == 1.0F) {
                entityarrow.setIsCritical(true);
            }

            int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);

            if (k > 0) {
                entityarrow.setDamage(entityarrow.getDamage() + k * 1 + 1);
            }

            int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack);

            if (l > 0) {
                entityarrow.setKnockbackStrength(l);
            }

            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack) > 0) {
                entityarrow.setFire(100);
            }

            stack.damageItem(1, player);
            world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

            if (flag) {
                entityarrow.canBePickedUp = 2;
            } else {
                player.inventory.consumeInventoryItem(Items.arrow);
            }

            if (!world.isRemote) {
                world.spawnEntityInWorld(entityarrow);
            }
        }
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return InfinityToolRuntimeHelpers.isBowRapidFireEnabled(stack) ? RAPID_FIRE_USE_DURATION
                : STANDARD_BOW_USE_DURATION;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.bow;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        ArrowNockEvent event = new ArrowNockEvent(player, stack);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled()) {
            return event.result;
        }

        player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
        return stack;
    }

    @Override
    public int getItemEnchantability() {
        return 1;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir) {
        int pullframes = 3;
        this.itemIcon = ir.registerIcon(this.getIconString() + "_standby");
        this.idleMask = ir.registerIcon(this.getIconString() + "_standby_mask");
        this.iconArray = new IIcon[pullframes];
        this.maskArray = new IIcon[pullframes];

        for (int i = 0; i < pullframes; ++i) {
            this.iconArray[i] = ir.registerIcon(this.getIconString() + "_pulling_" + i);
            this.maskArray[i] = ir.registerIcon(this.getIconString() + "_pulling_mask_" + i);
        }
    }

    @Override
    public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
        if (usingItem != null) {
            int pull = getDrawProgress(stack, useRemaining);
            if (pull >= (RAPID_FIRE_USE_DURATION * 2) / 3.0) {
                return this.iconArray[2];
            }

            if (pull > RAPID_FIRE_USE_DURATION / 3.0) {
                return this.iconArray[1];
            }

            if (pull > 0) {
                return this.iconArray[0];
            }
        }
        return getIcon(stack, renderPass);
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        return super.getIcon(stack, pass);
    }

    @Override
    public boolean isFull3D() {
        return true;
    }

    @Override
    public IIcon getMaskTexture(ItemStack stack, EntityPlayer player) {
        int frame = -1;
        if (player != null) {

            int bframe = CosmicBowRenderer.getBowFrame(player);
            frame = bframe != 0 ? bframe : -1;
        }
        // Lumberjack.info(frame);
        if (frame == -1) {
            return this.idleMask;
        }

        return maskArray[frame];
    }

    @Override
    public float getMaskMultiplier(ItemStack stack, EntityPlayer player) {
        return 1.0f;
    }

    @Optional.Method(modid = "DraconicEvolution")
    @Override
    public List<ItemConfigField> getFields(ItemStack stack, int slot) {
        return InfinityToolConfigFactory.createBowFields(stack, slot);
    }

    @Optional.Method(modid = "DraconicEvolution")
    @Override
    public boolean hasProfiles() {
        return true;
    }

    public static int getDrawProgress(ItemStack stack, int useRemaining) {
        int maxUseDuration = InfinityToolRuntimeHelpers.isBowRapidFireEnabled(stack) ? RAPID_FIRE_USE_DURATION
                : STANDARD_BOW_USE_DURATION;
        int elapsedTicks = maxUseDuration - useRemaining;
        return Math.max(0, Math.min(RAPID_FIRE_USE_DURATION, elapsedTicks));
    }

    public static int getDrawFrame(ItemStack stack, int useRemaining) {
        int progress = getDrawProgress(stack, useRemaining);
        if (progress <= 0) {
            return 0;
        }
        double ratio = progress / (double) RAPID_FIRE_USE_DURATION;
        return Math.max(0, Math.min(BOW_FRAME_COUNT - 1, (int) Math.ceil(ratio * BOW_FRAME_COUNT) - 1));
    }
}
