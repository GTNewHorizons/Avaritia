package fox.spiteful.avaritia.render;

import net.minecraftforge.client.event.TextureStitchEvent;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class LudicrousRenderEvents {

    @SubscribeEvent
    public void letsMakeAQuilt(TextureStitchEvent.Pre event) {
        if (event.map.getTextureType() != 1) {
            return;
        }

        ModelArmorInfinity.overlayIcon = event.map.registerIcon("avaritia:infinity_armor_mask");
        ModelArmorInfinity.invulnOverlayIcon = event.map.registerIcon("avaritia:infinity_armor_mask2");
        ModelArmorInfinity.wingOverlayIcon = event.map.registerIcon("avaritia:infinity_armor_wingmask");
    }

    @SubscribeEvent
    public void weMadeAQuilt(TextureStitchEvent.Post event) {
        if (event.map.getTextureType() != 1) {
            return;
        }

        CosmicRenderShenanigans.bindItemTexture();
        ModelArmorInfinity.itempagewidth = GL11.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_WIDTH);
        ModelArmorInfinity.itempageheight = GL11.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_HEIGHT);

        ModelArmorInfinity.armorModel.rebuildOverlay();
        ModelArmorInfinity.legModel.rebuildOverlay();
    }
}
