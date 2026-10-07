package fox.spiteful.avaritia.items.tools;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;

import com.brandon3055.draconicevolution.common.utils.IConfigurableItem;
import com.brandon3055.draconicevolution.common.utils.ItemConfigField;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fox.spiteful.avaritia.Avaritia;
import fox.spiteful.avaritia.DamageSourceInfinitySword;
import fox.spiteful.avaritia.achievements.Achievements;
import fox.spiteful.avaritia.compat.Belmont;
import fox.spiteful.avaritia.compat.draconicevolution.InfinityToolConfigFactory;
import fox.spiteful.avaritia.compat.draconicevolution.InfinityToolRuntimeHelpers;
import fox.spiteful.avaritia.entity.EntityImmortalItem;
import fox.spiteful.avaritia.items.LudicrousItems;
import fox.spiteful.avaritia.render.ICosmicRenderItem;

@Optional.Interface(
        iface = "com.brandon3055.draconicevolution.common.utils.IConfigurableItem",
        modid = "DraconicEvolution")
public class ItemSwordInfinity extends ItemSword implements ICosmicRenderItem, IConfigurableItem {

    private static final ToolMaterial opSword = EnumHelper
            .addToolMaterial("INFINITY_SWORD", 32, 9999, 9999F, -3.0F, 200);
    private IIcon cosmicMask;
    private IIcon pommel;

    public ItemSwordInfinity() {
        super(opSword);
        setUnlocalizedName("infinity_sword");
        setTextureName("avaritia:infinity_sword");
        setCreativeTab(Avaritia.tab);
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase victim, EntityLivingBase player) {
        if (player.worldObj.isRemote) return true;
        boolean executionEnabled = InfinityToolRuntimeHelpers.isSwordExecutionEnabled(stack);
        float attackDamage = executionEnabled ? Float.MAX_VALUE : InfinityToolRuntimeHelpers.getSwordDamage(stack);
        if (victim instanceof EntityPlayer pvp) {
            if (LudicrousItems.isInfinite(pvp)) {
                if (Belmont.isVampire(pvp)) victim.attackEntityFrom(
                        new DamageSourceInfinitySword(player).setFireDamage().setDamageBypassesArmor(),
                        4.0F);
                else victim.attackEntityFrom(new DamageSourceInfinitySword(player).setDamageBypassesArmor(), 4.0F);
                return true;
            }
            if (pvp.getHeldItem() != null && pvp.getHeldItem().getItem() == LudicrousItems.infinity_sword
                    && pvp.isUsingItem())
                return true;
        }

        victim.recentlyHit = 60;
        victim.func_110142_aN() // getCombatTracker
                .func_94547_a(new DamageSourceInfinitySword(player), victim.getHealth(), victim.getHealth());
        if (executionEnabled) {
            victim.setHealth(0);
            if (Belmont.isVampire(victim)) victim.onDeath(new EntityDamageSource("infinity", player).setFireDamage());
            else victim.onDeath(new EntityDamageSource("infinity", player));
            return true;
        }
        victim.attackEntityFrom(new DamageSourceInfinitySword(player).setDamageBypassesArmor(), attackDamage);
        return true;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        if (!entity.worldObj.isRemote && entity instanceof EntityPlayer victim) {
            if (victim.capabilities.isCreativeMode && !victim.isDead
                    && victim.getHealth() > 0
                    && InfinityToolRuntimeHelpers.isSwordCreativeBypassEnabled(stack)
                    && !LudicrousItems.isInfinite(victim)) {
                victim.func_110142_aN() // getCombatTracker
                        .func_94547_a(new DamageSourceInfinitySword(player), victim.getHealth(), victim.getHealth());
                victim.setHealth(0);
                victim.onDeath(new EntityDamageSource("infinity", player));
                player.addStat(Achievements.creative_kill, 1);
                return true;
            }
        }
        return false;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return LudicrousItems.cosmic;
    }

    @Override
    public void setDamage(ItemStack stack, int damage) {
        super.setDamage(stack, 0);
    }

    @Override
    public IIcon getMaskTexture(ItemStack stack, EntityPlayer player) {
        return cosmicMask;
    }

    @Override
    public float getMaskMultiplier(ItemStack stack, EntityPlayer player) {
        return 1.0f;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister ir) {
        super.registerIcons(ir);

        this.cosmicMask = ir.registerIcon("avaritia:infinity_sword_mask");
        this.pommel = ir.registerIcon("avaritia:infinity_sword_pommel");
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        if (pass == 1) {
            return this.pommel;
        }

        return super.getIcon(stack, pass);
    }

    @Override
    public boolean requiresMultipleRenderPasses() {
        return true;
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
    public List<ItemConfigField> getFields(ItemStack stack, int slot) {
        return InfinityToolConfigFactory.createSwordFields(stack, slot);
    }

    @Optional.Method(modid = "DraconicEvolution")
    @Override
    public boolean hasProfiles() {
        return true;
    }
}
