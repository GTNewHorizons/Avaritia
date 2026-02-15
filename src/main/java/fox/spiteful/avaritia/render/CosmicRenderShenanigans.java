package fox.spiteful.avaritia.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.world.World;

import com.gtnewhorizon.gtnhlib.client.renderer.postprocessing.shaders.UniversiumShader;
import com.gtnewhorizon.gtnhlib.client.renderer.shader.ShaderProgram;

public class CosmicRenderShenanigans {

    public static boolean inventoryRender = false;
    public static float cosmicOpacity = 1.0f;

    public static void useShader() {
        final UniversiumShader shader = UniversiumShader.getInstance();
        if (inventoryRender) {
            shader.setRenderInInventory();
        }
        if (cosmicOpacity != 1.0f) {
            shader.setCosmicOpacity(cosmicOpacity);
        }
        shader.use();
    }

    public static void releaseShader() {
        ShaderProgram.unbind();
    }

    public static void setLightFromLocation(World world, int x, int y, int z) {
        UniversiumShader.getInstance().setLightFromLocation(world, x, y, z);
    }

    public static void setLightLevel(float level) {
        UniversiumShader.getInstance().setLightLevel(level);
    }

    public static void setLightLevel(float r, float g, float b) {
        UniversiumShader.getInstance().setLightLevel(r, g, b);
    }

    public static void bindItemTexture() {
        Minecraft mc = Minecraft.getMinecraft();
        mc.renderEngine.bindTexture(TextureMap.locationItemsTexture);
    }
}
