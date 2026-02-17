package fox.spiteful.avaritia;

import java.util.Locale;

import net.minecraft.util.ResourceLocation;

import org.jetbrains.annotations.NotNull;

import com.gtnewhorizon.gtnhlib.util.data.IMod;
import com.gtnewhorizon.gtnhmixins.builders.ITargetMod;
import com.gtnewhorizon.gtnhmixins.builders.TargetModBuilder;

import cpw.mods.fml.common.Loader;

public enum Mods implements IMod, ITargetMod {

    AE2FluidCraft(Names.A_E2_FLUID_CRAFT),
    GTNHLib(Names.G_T_N_H_LIB),
    ModularUI2(Names.MODULAR_U_I_2);

    public static class Names {

        // spotless:off
        public static final String A_E2_FLUID_CRAFT = "ae2fc";
        public static final String G_T_N_H_LIB = "gtnhlib";
        public static final String MODULAR_U_I_2 = "modularui2";
        // spotless:on
    }

    public final String ID;
    public final String resourceDomain;
    private final TargetModBuilder builder;
    private boolean checkedMod, modLoaded;

    Mods(String ID) {
        this.ID = ID;
        this.resourceDomain = ID.toLowerCase(Locale.ENGLISH);
        this.builder = new TargetModBuilder().setModId(ID);
    }

    @Override
    public @NotNull TargetModBuilder getBuilder() {
        return builder;
    }

    @Override
    public String getID() {
        return ID;
    }

    public boolean isModLoaded() {
        if (!checkedMod) {
            this.modLoaded = Loader.isModLoaded(this.ID);
            this.checkedMod = true;
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
