package fox.spiteful.avaritia.mixins;

import org.jetbrains.annotations.NotNull;

import com.gtnewhorizon.gtnhmixins.builders.IMixins;
import com.gtnewhorizon.gtnhmixins.builders.MixinBuilder;

public enum Mixins implements IMixins {

    MINECRAFT(Side.COMMON, "minecraft.EntityLivingBaseAccessor", "minecraft.PlayerCapabilitiesAccessor");

    private final MixinBuilder builder;

    Mixins(Side side, String... mixins) {
        this.builder = new MixinBuilder().addSidedMixins(side, mixins).setPhase(Phase.EARLY);
    }

    @Override
    public @NotNull MixinBuilder getBuilder() {
        return builder;
    }
}
