package fox.spiteful.avaritia.gui;

import fox.spiteful.avaritia.tile.TileEntityDireCrafting;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GUIExtremeCrafting extends GuiContainer {

    private static final ResourceLocation tex = new ResourceLocation("avaritia:textures/gui/dire_crafting_gui.png");

    public GUIExtremeCrafting(InventoryPlayer par1InventoryPlayer, World par2World, BlockPos pos, TileEntityDireCrafting table)
    {
        super(new ContainerExtremeCrafting(par1InventoryPlayer, par2World, pos, table));
        this.ySize = 256;
        this.xSize = 238;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int i, int j)
    {
        //this.fontRendererObj.drawString(StatCollector.translateToLocal("container.extreme_crafting"), 28, 6, 4210752);
        //this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(tex);
        int foo = (this.width - this.xSize) / 2;
        int bar = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(foo, bar, 0, 0, this.ySize, this.ySize);
    }
}
