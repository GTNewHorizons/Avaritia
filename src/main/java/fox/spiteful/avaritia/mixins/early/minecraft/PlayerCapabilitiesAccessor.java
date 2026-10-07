package fox.spiteful.avaritia.mixins.early.minecraft;

import net.minecraft.entity.player.PlayerCapabilities;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PlayerCapabilities.class)
public interface PlayerCapabilitiesAccessor {

    @Accessor("flySpeed")
    void setPlayerFlySpeed(float flySpeed);

    @Accessor("walkSpeed")
    void setPlayerWalkSpeed(float walkSpeed);
}
