package fox.spiteful.avaritia;

import java.util.Locale;

import net.minecraft.util.ResourceLocation;

import org.jetbrains.annotations.NotNull;

import com.gtnewhorizon.gtnhlib.util.data.IMod;
import com.gtnewhorizon.gtnhmixins.builders.ITargetMod;
import com.gtnewhorizon.gtnhmixins.builders.TargetModBuilder;

import cpw.mods.fml.common.Loader;

@SuppressWarnings("unused")
public enum Mods implements IMod, ITargetMod {

    AE2FluidCraft(Names.A_E2_FLUID_CRAFT),
    ModularUI2(Names.MODULAR_U_I_2),

    ;

    public static class Names {

        // spotless:off

        public static final String A_E2_FLUID_CRAFT = "ae2fc";
        public static final String MODULAR_U_I_2 = "modularui2";

        // spotless:on
    }

    public final String ID;
    public final String resourceDomain;
    protected boolean checkedMod, modLoaded;
    protected final TargetModBuilder builder;

    Mods(String ID) {
        this.ID = ID;
        this.resourceDomain = ID.toLowerCase(Locale.ENGLISH);
        this.builder = new TargetModBuilder().setModId(getEffectiveModID());
    }

    @Override
    public @NotNull TargetModBuilder getBuilder() {
        return builder;
    }

    @Override
    public String getID() {
        return ID;
    }

    protected String getEffectiveModID() {
        return ID;
    }

    public boolean isModLoaded() {
        if (!checkedMod) {
            this.modLoaded = Loader.isModLoaded(getEffectiveModID());
            checkedMod = true;
        }
        return this.modLoaded;
    }

    @Override
    public String getResourceLocation() {
        return resourceDomain;
    }

    public String getResourcePath(String... path) {
        return this.getResourceLocation(path).toString();
    }

    public ResourceLocation getResourceLocation(String... path) {
        return new ResourceLocation(this.resourceDomain, String.join("/", path));
    }
}
