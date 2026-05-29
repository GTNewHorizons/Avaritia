package fox.spiteful.avaritia.items;

import static net.minecraft.util.EnumChatFormatting.BLUE;
import static net.minecraft.util.EnumChatFormatting.DARK_PURPLE;
import static net.minecraft.util.EnumChatFormatting.ITALIC;
import static net.minecraft.util.EnumChatFormatting.RESET;

import java.util.List;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;

import com.brandon3055.draconicevolution.common.items.armor.ICustomArmor;
import com.brandon3055.draconicevolution.common.utils.IConfigurableItem;
import com.brandon3055.draconicevolution.common.utils.ItemConfigField;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fox.spiteful.avaritia.Avaritia;
import fox.spiteful.avaritia.Config;
import fox.spiteful.avaritia.LudicrousText;
import fox.spiteful.avaritia.compat.Compat;
import fox.spiteful.avaritia.compat.draconicevolution.InfinityArmorAbilityResolver;
import fox.spiteful.avaritia.compat.draconicevolution.InfinityArmorConfigFactory;
import fox.spiteful.avaritia.compat.draconicevolution.InfinityArmorEffectHandler;
import fox.spiteful.avaritia.entity.EntityImmortalItem;
import fox.spiteful.avaritia.render.ICosmicRenderItem;
import fox.spiteful.avaritia.render.ModelArmorInfinity;
import gregtech.api.hazards.Hazard;
import gregtech.api.hazards.IHazardProtector;
import thaumcraft.api.IGoggles;
import thaumcraft.api.IVisDiscountGear;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.nodes.IRevealer;
import vazkii.botania.api.item.IManaProficiencyArmor;
import vazkii.botania.api.item.IPhantomInkable;
import vazkii.botania.api.mana.IManaDiscountArmor;

@Optional.InterfaceList({ @Optional.Interface(iface = "thaumcraft.api.IGoggles", modid = "Thaumcraft"),
        @Optional.Interface(iface = "thaumcraft.api.nodes.IRevealer", modid = "Thaumcraft"),
        @Optional.Interface(iface = "thaumcraft.api.IVisDiscountGear", modid = "Thaumcraft"),
        @Optional.Interface(iface = "vazkii.botania.api.item.IPhantomInkable", modid = "Botania"),
        @Optional.Interface(iface = "vazkii.botania.api.mana.IManaDiscountArmor", modid = "Botania"),
        @Optional.Interface(iface = "vazkii.botania.api.item.IManaProficiencyArmor", modid = "Botania"),
        @Optional.Interface(
                iface = "com.brandon3055.draconicevolution.common.utils.IConfigurableItem",
                modid = "DraconicEvolution"),
        @Optional.Interface(
                iface = "com.brandon3055.draconicevolution.common.items.armor.ICustomArmor",
                modid = "DraconicEvolution"),
        @Optional.Interface(iface = "gregtech.api.hazards.IHazardProtector", modid = "gregtech_nh") })
public class ItemArmorInfinity extends ItemArmor implements ICosmicRenderItem, IGoggles, IRevealer, IVisDiscountGear,
        IPhantomInkable, IManaDiscountArmor, IManaProficiencyArmor, IHazardProtector, IConfigurableItem, ICustomArmor {

    public static final ArmorMaterial infinite_armor = EnumHelper
            .addArmorMaterial("infinity", 9999, new int[] { 6, 16, 12, 6 }, 1000);
    public IIcon cosmicMask;
    public final int slot;

    public ItemArmorInfinity(int slot) {
        super(infinite_armor, 0, slot);
        this.slot = slot;
        setCreativeTab(Avaritia.tab);
        setUnlocalizedName("infinity_armor_" + slot);
        setTextureName("avaritia:infinity_armor_" + slot);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        return "avaritia:textures/models/infinity_armor.png";
    }

    @Override
    public void setDamage(ItemStack stack, int damage) {
        super.setDamage(stack, 0);
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        InfinityArmorEffectHandler.handleArmorTick(player, itemStack, armorType);
    }

    @Optional.Method(modid = "DraconicEvolution")
    @Override
    public List<ItemConfigField> getFields(ItemStack stack, int slot) {
        return InfinityArmorConfigFactory.createFields(stack, slot, armorType);
    }

    @Optional.Method(modid = "DraconicEvolution")
    @Override
    public boolean hasProfiles() {
        return true;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return LudicrousItems.cosmic;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemstack, int armorSlot) {
        ModelArmorInfinity model = armorSlot == 2 ? ModelArmorInfinity.legModel : ModelArmorInfinity.armorModel;

        model.update(entityLiving, itemstack, armorSlot);

        return model;
    }

    @Optional.Method(modid = "Thaumcraft")
    @Override
    public boolean showIngamePopups(ItemStack itemStack, EntityLivingBase entityLivingBase) {
        return armorType == 0;
    }

    @Optional.Method(modid = "Thaumcraft")
    @Override
    public boolean showNodes(ItemStack itemStack, EntityLivingBase entityLivingBase) {
        return armorType == 0;
    }

    @Optional.Method(modid = "Thaumcraft")
    @Override
    public int getVisDiscount(ItemStack itemStack, EntityPlayer entityPlayer, Aspect aspect) {
        return 20;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean adv) {
        if (Compat.thaumic) {
            list.add(
                    DARK_PURPLE + StatCollector.translateToLocal("tc.visdiscount")
                            + ": "
                            + this.getVisDiscount(stack, player, null)
                            + "%");
        }
        if (Compat.botan) {
            if (hasPhantomInk(stack))
                list.add(StatCollector.translateToLocal("botaniamisc.hasPhantomInk").replace('&', '§'));
        }
        if (this.slot == 3 && Config.fast) {
            list.add("");
            list.add(BLUE + "+" + ITALIC + LudicrousText.makeSANIC("SANIC") + RESET + BLUE + "% Speed");
        }
        super.addInformation(stack, player, list, adv);
    }

    public boolean hasPhantomInk(ItemStack stack) {
        if (stack.getTagCompound() == null) return false;
        return stack.getTagCompound().getBoolean("phantomInk");
    }

    public void setPhantomInk(ItemStack stack, boolean ink) {
        NBTTagCompound tag = stack.getTagCompound();
        if (tag == null) {
            tag = new NBTTagCompound();
            stack.setTagCompound(tag);
        }
        tag.setBoolean("phantomInk", ink);
    }

    @Optional.Method(modid = "Botania")
    @Override
    public float getDiscount(ItemStack stack, int slot, EntityPlayer player) {
        return 0.25F;
    }

    @Optional.Method(modid = "Botania")
    @Override
    public boolean shouldGiveProficiency(ItemStack itemStack, int i, EntityPlayer player) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister ir) {
        super.registerIcons(ir);

        this.cosmicMask = ir.registerIcon("avaritia:infinity_armor_" + slot + "_mask");
    }

    @Override
    public IIcon getMaskTexture(ItemStack stack, EntityPlayer player) {
        return this.cosmicMask;
    }

    @Override
    public float getMaskMultiplier(ItemStack stack, EntityPlayer player) {
        return 1.0f;
    }

    @Override
    public boolean hasCustomEntity(ItemStack stack) {
        return true;
    }

    @Override
    public Entity createEntity(World world, Entity location, ItemStack itemstack) {
        return new EntityImmortalItem(world, location, itemstack);
    }

    @Override
    public boolean hasEffect(ItemStack par1ItemStack, int pass) {
        return false;
    }

    @Optional.Method(modid = "DraconicEvolution")
    @Override
    public float getProtectionPoints(ItemStack stack) {
        return 0F;
    }

    @Optional.Method(modid = "DraconicEvolution")
    @Override
    public int getRecoveryPoints(ItemStack stack) {
        return 0;
    }

    @Optional.Method(modid = "DraconicEvolution")
    @Override
    public float getSpeedModifier(ItemStack stack, EntityPlayer player) {
        return InfinityArmorAbilityResolver.getSpeedModifier(stack, player);
    }

    @Optional.Method(modid = "DraconicEvolution")
    @Override
    public float getJumpModifier(ItemStack stack, EntityPlayer player) {
        return InfinityArmorAbilityResolver.getJumpModifier(stack, player);
    }

    @Optional.Method(modid = "DraconicEvolution")
    @Override
    public boolean hasHillStep(ItemStack stack, EntityPlayer player) {
        return InfinityArmorAbilityResolver.hasHillStep(stack, player);
    }

    @Optional.Method(modid = "DraconicEvolution")
    @Override
    public float getFireResistance(ItemStack stack) {
        return 1F;
    }

    @Optional.Method(modid = "DraconicEvolution")
    @Override
    public boolean[] hasFlight(ItemStack stack) {
        return armorType == 1 ? InfinityArmorAbilityResolver.getFlightState(stack)
                : new boolean[] { false, false, false };
    }

    @Optional.Method(modid = "DraconicEvolution")
    @Override
    public float getFlightSpeedModifier(ItemStack stack, EntityPlayer player) {
        return armorType == 1 ? InfinityArmorAbilityResolver.getFlightSpeedModifier(stack, player) : 0F;
    }

    @Optional.Method(modid = "DraconicEvolution")
    @Override
    public float getFlightVModifier(ItemStack stack, EntityPlayer player) {
        return armorType == 1 ? InfinityArmorAbilityResolver.getFlightVerticalModifier(stack, player) : 0F;
    }

    @Optional.Method(modid = "DraconicEvolution")
    @Override
    public int getEnergyPerProtectionPoint() {
        return 0;
    }

    @Optional.Method(modid = "DraconicEvolution")
    @Override
    public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {
        return 0;
    }

    @Optional.Method(modid = "DraconicEvolution")
    @Override
    public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {
        return 0;
    }

    @Optional.Method(modid = "DraconicEvolution")
    @Override
    public int getEnergyStored(ItemStack container) {
        return 0;
    }

    @Optional.Method(modid = "DraconicEvolution")
    @Override
    public int getMaxEnergyStored(ItemStack container) {
        return 0;
    }

    /// GT5 Hazmat protection
    @Optional.Method(modid = "gregtech_nh")
    @Override
    public boolean protectsAgainst(ItemStack itemStack, Hazard hazard) {
        return true;
    }
}
