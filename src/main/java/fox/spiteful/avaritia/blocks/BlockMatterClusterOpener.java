package fox.spiteful.avaritia.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.cleanroommc.modularui.factory.GuiManager;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.factory.TileEntityGuiFactory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fox.spiteful.avaritia.Avaritia;
import fox.spiteful.avaritia.tile.TileMatterClusterOpener;

public class BlockMatterClusterOpener extends BlockContainer {

    @SideOnly(Side.CLIENT)
    private IIcon top, side, front;

    public BlockMatterClusterOpener() {
        super(Material.iron);
        setStepSound(Block.soundTypeMetal);
        setHardness(20.0F);
        setBlockName("cluster_opener");
        setHarvestLevel("pickaxe", 3);
        setCreativeTab(Avaritia.tab);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileMatterClusterOpener();
    }

    @Override
    public boolean onBlockActivated(World worldIn, int x, int y, int z, EntityPlayer player, int side, float subX,
            float subY, float subZ) {
        if (!worldIn.isRemote) {
            PosGuiData data = new PosGuiData(player, x, y, z);
            GuiManager.open(TileEntityGuiFactory.INSTANCE, data, (EntityPlayerMP) player);
        }

        return true;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
        int l = MathHelper.floor_double((double) (player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        world.setBlockMetadataWithNotify(x, y, z, l, 2);
    }

    @Override
    public void onBlockPreDestroy(World worldIn, int x, int y, int z, int meta) {
        super.onBlockPreDestroy(worldIn, x, y, z, meta);

        ((TileMatterClusterOpener) worldIn.getTileEntity(x, y, z)).dropContents();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister reg) {
        top = reg.registerIcon("avaritia:cluster_opener/top");
        side = reg.registerIcon("avaritia:cluster_opener/side");
        front = reg.registerIcon("avaritia:cluster_opener/front");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(IBlockAccess worldIn, int x, int y, int z, int side) {
        // In-world rendering
        ForgeDirection dir = ForgeDirection.getOrientation(side);

        if (dir == ForgeDirection.UP) return top;
        if (dir == ForgeDirection.DOWN) return top;

        ForgeDirection front = switch (worldIn.getBlockMetadata(x, y, z)) {
            case 0 -> ForgeDirection.NORTH;
            case 1 -> ForgeDirection.EAST;
            case 2 -> ForgeDirection.SOUTH;
            case 3 -> ForgeDirection.WEST;
            default -> ForgeDirection.UNKNOWN;
        };

        return dir == front ? this.front : this.side;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta) {
        // In-hand rendering
        ForgeDirection dir = ForgeDirection.getOrientation(side);

        if (dir == ForgeDirection.UP) return top;
        if (dir == ForgeDirection.DOWN) return top;

        return dir == ForgeDirection.SOUTH ? this.front : this.side;
    }
}
