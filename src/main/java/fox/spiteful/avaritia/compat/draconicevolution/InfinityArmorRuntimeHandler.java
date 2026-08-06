package fox.spiteful.avaritia.compat.draconicevolution;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import fox.spiteful.avaritia.Config;
import fox.spiteful.avaritia.compat.Compat;
import fox.spiteful.avaritia.items.LudicrousItems;
import fox.spiteful.avaritia.mixins.early.minecraft.EntityLivingBaseAccessor;

public class InfinityArmorRuntimeHandler {

    private static final float BASE_FLY_SPEED = 0.05F;
    private static final float BASE_WALK_SPEED = 0.15F;
    private static final float BASE_JUMP_BOOST = 0.4F;
    private static final float LEGACY_FLIGHT_SPEED_BONUS = 1.0F;
    private static final float LEGACY_VERTICAL_ACCELERATION = 1.0F;

    private final Set<String> playersWithChest = new HashSet<>();
    private final Set<String> playersWithFoot = new HashSet<>();

    @SubscribeEvent
    public void updatePlayerAbilityStatus(LivingUpdateEvent event) {
        if (!(event.entityLiving instanceof EntityPlayer player)) {
            return;
        }

        String key = playerKey(player);
        updateFlight(player, key);
        updateFootAbilities(player, key);
    }

    @SubscribeEvent
    public void jumpBoost(LivingJumpEvent event) {
        if (!(event.entityLiving instanceof EntityPlayer player)) {
            return;
        }

        ItemStack boots = getInfinityArmor(player, 0);
        if (boots == null) {
            return;
        }

        float jumpModifier = InfinityArmorAbilityResolver.getJumpModifier(boots, player);
        player.motionY += BASE_JUMP_BOOST + resolveJumpBonus(jumpModifier);
    }

    private void updateFlight(EntityPlayer player, String key) {
        ItemStack chest = getInfinityArmor(player, 2);
        if (chest != null) {
            boolean[] flightState = InfinityArmorAbilityResolver.getFlightState(chest);
            player.capabilities.allowFlying = flightState[0];
            if (flightState[1]) {
                player.capabilities.isFlying = true;
            }
            float flightSpeedModifier = InfinityArmorAbilityResolver.getFlightSpeedModifier(chest, player);
            player.capabilities.setFlySpeed(resolveFlySpeed(flightSpeedModifier));
            playersWithChest.add(key);
            return;
        }

        if (playersWithChest.remove(key) && !player.capabilities.isCreativeMode) {
            player.capabilities.allowFlying = false;
            player.capabilities.isFlying = false;
        }
        player.capabilities.setFlySpeed(0.05F);
    }

    private void updateFootAbilities(EntityPlayer player, String key) {
        ItemStack boots = Config.fast ? getInfinityArmor(player, 0) : null;
        if (boots == null) {
            if (playersWithFoot.remove(key) && Config.stepUp) {
                player.stepHeight = 0.5F;
            }
            return;
        }

        boolean flying = player.capabilities.isFlying;
        boolean swimming = player.isInsideOfMaterial(Material.water) || player.isInWater();
        if (player.onGround || flying || swimming) {
            updateStepHeight(player, boots);
            applyHorizontalMovement(player, boots, flying);
            if (flying) {
                applyFlightVerticalMovement(player);
                applyFlightInertia(player);
            }
        }
        playersWithFoot.add(key);
    }

    private void updateStepHeight(EntityPlayer player, ItemStack boots) {
        boolean sneaking = player.isSneaking();
        if (InfinityArmorAbilityResolver.hasHillStep(boots, player)) {
            player.stepHeight = sneaking ? 0.501F : 1.001F;
        } else if (Config.stepUp) {
            player.stepHeight = 0.5F;
        }
    }

    private void applyHorizontalMovement(EntityPlayer player, ItemStack boots, boolean flying) {
        boolean sneaking = player.isSneaking();
        float configuredSpeed = InfinityArmorAbilityResolver.getSpeedModifier(boots, player);
        float speed = resolveHorizontalSpeed(configuredSpeed, flying, sneaking);

        if (player.moveForward > 0F) {
            player.moveFlying(0F, 1F, speed);
        } else if (player.moveForward < 0F) {
            player.moveFlying(0F, 1F, -speed * 0.3F);
        }

        if (player.moveStrafing != 0F) {
            player.moveFlying(1F, 0F, speed * 0.5F * Math.signum(player.moveStrafing));
        }
    }

    private void applyFlightVerticalMovement(EntityPlayer player) {
        ItemStack chest = getInfinityArmor(player, 2);
        if (chest == null) {
            return;
        }

        boolean jumping = ((EntityLivingBaseAccessor) player).getIsJumping();
        float verticalMultiplier = InfinityArmorAbilityResolver.getFlightVerticalModifier(chest, player);
        float accelerationScale = resolveVerticalAccelerationScale(verticalMultiplier);
        if (jumping && player.motionY > 0 && player.motionY < 2) {
            player.motionY *= accelerationScale;
        } else if (player.isSneaking() && player.motionY < 0 && player.motionY > -2) {
            player.motionY *= accelerationScale;
        }
    }

    private void applyFlightInertia(EntityPlayer player) {
        ItemStack chest = getInfinityArmor(player, 2);
        if (chest == null || player.moveForward != 0F || player.moveStrafing != 0F) {
            return;
        }

        boolean inertiaCancellation = InfinityArmorAbilityResolver.getFlightState(chest)[2];
        if (inertiaCancellation) {
            player.motionX *= 0.5D;
            player.motionZ *= 0.5D;
        }
    }

    private ItemStack getInfinityArmor(EntityPlayer player, int armorSlot) {
        ItemStack armor = player.getCurrentArmor(armorSlot);
        if (armor == null) {
            return null;
        }
        return armor.getItem() == LudicrousItems.infinity_armor || armor.getItem() == LudicrousItems.infinity_helm
                || armor.getItem() == LudicrousItems.infinity_pants
                || armor.getItem() == LudicrousItems.infinity_shoes ? armor : null;
    }

    private String playerKey(EntityPlayer player) {
        return player.getGameProfile().getName() + ":" + player.worldObj.isRemote;
    }

    private float resolveJumpBonus(float configuredValue) {
        return Compat.draconicEvolution ? configuredValue : BASE_JUMP_BOOST;
    }

    private float resolveFlySpeed(float configuredValue) {
        if (Compat.draconicEvolution) {
            return BASE_FLY_SPEED * (1.0F + configuredValue);
        }
        return BASE_FLY_SPEED + (BASE_FLY_SPEED * LEGACY_FLIGHT_SPEED_BONUS);
    }

    private float resolveHorizontalSpeed(float configuredValue, boolean flying, boolean sneaking) {
        float speed = Compat.draconicEvolution ? BASE_WALK_SPEED * configuredValue : BASE_WALK_SPEED;
        if (flying) {
            speed *= 1.1F;
        }
        if (sneaking) {
            speed *= 0.1F;
        }
        return speed;
    }

    private float resolveVerticalAccelerationScale(float configuredValue) {
        if (Compat.draconicEvolution) {
            return configuredValue <= 0F ? 1.0F : 1.5F + (configuredValue * 0.1F);
        }
        return 1.5F + (LEGACY_VERTICAL_ACCELERATION * 0.1F);
    }
}
