/**
 * Code blatantly copied from Vazkii
 *
 * Get the original here:
 * https://github.com/Vazkii/Botania/blob/master/src/main/java/vazkii/botania/api/internal/VanillaPacketDispatcher.java
 */

package fox.spiteful.avaritia.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public final class VanillaPacketDispatcher {

    public static void dispatchTEToNearbyPlayers(TileEntity tile) {
        for (EntityPlayer player : tile.getWorldObj().playerEntities) if (player instanceof EntityPlayerMP mp
                && pointDistancePlane(mp.posX, mp.posZ, tile.xCoord + 0.5, tile.zCoord + 0.5) < 64) {
                    mp.playerNetServerHandler.sendPacket(tile.getDescriptionPacket());
                }
    }

    public static void dispatchTEToNearbyPlayers(World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null) dispatchTEToNearbyPlayers(tile);
    }

    public static float pointDistancePlane(double x1, double y1, double x2, double y2) {
        return (float) Math.hypot(x1 - x2, y1 - y2);
    }
}
