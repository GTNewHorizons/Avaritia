package fox.spiteful.avaritia.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fox.spiteful.avaritia.Avaritia;
import fox.spiteful.avaritia.tile.TileEntityCompressor;

public class BlockCompressor extends BlockContainer {

    private IIcon top, sides, front;

    public BlockCompressor() {
        super(Material.iron);
        setStepSound(Block.soundTypeMetal);
        setHardness(20.0F);
        setBlockName("neutronium_compressor");
        setHarvestLevel("pickaxe", 3);
        setCreativeTab(Avaritia.tab);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.top = iconRegister.registerIcon("avaritia:compressor_top");
        this.sides = iconRegister.registerIcon("avaritia:compressor_side");
        this.front = iconRegister.registerIcon("avaritia:compressor_front");
    }

    @Override
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        if (side == 1) return top;
        int facing = 2;
        TileEntityCompressor machine = (TileEntityCompressor) world.getTileEntity(x, y, z);
        if (machine != null) facing = machine.getFacing();
        if (side == facing) return front;
        else return sides;
    }

    @Override
    public IIcon getIcon(int side, int metadata) {
        if (side == 1) return top;
        if (side == 3) return front;
        return sides;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7,
            float par8, float par9) {
        if (world.isRemote) {
            return true;
        } else {
            player.openGui(Avaritia.instance, 3, world, x, y, z);
            return true;
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityCompressor();
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack item) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileEntityCompressor) {
            TileEntityCompressor machine = (TileEntityCompressor) tile;
            int l = MathHelper.floor_double((double) (player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

            if (l == 0) machine.setFacing(2);

            if (l == 1) machine.setFacing(5);

            if (l == 2) machine.setFacing(3);

            if (l == 3) machine.setFacing(4);
        }

    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int wut) {
        TileEntityCompressor compressor = (TileEntityCompressor) world.getTileEntity(x, y, z);

        if (compressor != null) {
            for (int i = 0; i < 2; i++) {
                ItemStack itemstack = compressor.getStackInSlot(i);

                if (itemstack != null) {
                    float f = world.rand.nextFloat() * 0.8F + 0.1F;
                    float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
                    float f2 = world.rand.nextFloat() * 0.8F + 0.1F;

                    while (itemstack.stackSize > 0) {
                        int j1 = world.rand.nextInt(21) + 10;

                        if (j1 > itemstack.stackSize) {
                            j1 = itemstack.stackSize;
                        }

                        itemstack.stackSize -= j1;
                        EntityItem entityitem = new EntityItem(
                                world,
                                (float) x + f,
                                (float) y + f1,
                                (float) z + f2,
                                new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

                        if (itemstack.hasTagCompound()) {
                            entityitem.getEntityItem()
                                    .setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
                        }

                        float f3 = 0.05F;
                        entityitem.motionX = (float) world.rand.nextGaussian() * f3;
                        entityitem.motionY = (float) world.rand.nextGaussian() * f3 + 0.2F;
                        entityitem.motionZ = (float) world.rand.nextGaussian() * f3;
                        world.spawnEntityInWorld(entityitem);
                    }
                }
            }

            world.func_147453_f(x, y, z, block); // updateNeighborsAboutBlockChange
        }

        super.breakBlock(world, x, y, z, block, wut);
    }
}
