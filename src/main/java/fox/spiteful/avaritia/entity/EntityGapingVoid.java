package fox.spiteful.avaritia.entity;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.UUID;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.world.BlockEvent;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.mojang.authlib.GameProfile;

import fox.spiteful.avaritia.Config;

public class EntityGapingVoid extends Entity {

    public static final int maxLifetime = 186;
    public static final double collapse = .95;
    public static final double suckrange = 20.0;

    private static final GameProfile defaultProfile = new GameProfile(
            UUID.fromString("139a261e-4a64-4d98-8321-e484cfe7c6af"),
            "[Endest Pearl]");
    private static WeakReference<EntityPlayer> defaultFakePlayer = new WeakReference<>(null);

    private @Nullable GameProfile throwerInfo;
    private WeakReference<EntityPlayer> thrower;

    @SuppressWarnings("unused")
    public EntityGapingVoid(World world) {
        super(world);
        this.isImmuneToFire = true;
        this.setSize(0.1F, 0.1F);
        this.ignoreFrustumCheck = true;
        this.renderDistanceWeight = 100.0;
    }

    public EntityGapingVoid(World world, EntityLivingBase thrower) {
        this(world);

        if (thrower instanceof EntityPlayer player) {
            this.thrower = new WeakReference<>(player);
            this.throwerInfo = player.getGameProfile();
        }
    }

    @Override
    protected void entityInit() {
        dataWatcher.addObject(12, 0);
        dataWatcher.setObjectWatched(12);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        // tick, tock
        int age = this.getAge();

        if (age >= maxLifetime) {
            if (!this.worldObj.isRemote) {
                this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 6.0f, true);
            }
            this.setDead();
        } else {
            if (age == 0) {
                this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "Avaritia:gapingVoid", 8.0F, 1.0F);
            }
            this.setAge(age + 1);
        }

        // poot poot
        double particlespeed = 4.5;

        double size = getVoidScale(age) * 0.5 - 0.2;
        for (int i = 0; i < 50; i++) {
            Vec3 pootdir = Vec3.createVectorHelper(0, 0, size);
            pootdir.rotateAroundY(rand.nextFloat() * 180f);
            pootdir.rotateAroundX(rand.nextFloat() * 360f);

            Vec3 pootspeed = pootdir.normalize();
            pootspeed.xCoord *= particlespeed;
            pootspeed.yCoord *= particlespeed;
            pootspeed.zCoord *= particlespeed;

            this.worldObj.spawnParticle(
                    "portal",
                    this.posX + pootdir.xCoord,
                    this.posY + pootdir.yCoord,
                    this.posZ + pootdir.zCoord,
                    pootspeed.xCoord,
                    pootspeed.yCoord,
                    pootspeed.zCoord);
        }

        // *slurping noises*
        AxisAlignedBB suckzone = AxisAlignedBB.getBoundingBox(
                this.posX - suckrange,
                this.posY - suckrange,
                this.posZ - suckrange,
                this.posX + suckrange,
                this.posY + suckrange,
                this.posZ + suckrange);
        List<Entity> sucked = this.worldObj.selectEntitiesWithinAABB(
                Entity.class,
                suckzone,
                ent -> !(ent instanceof EntityPlayer p && p.capabilities.isCreativeMode && p.capabilities.isFlying));

        double radius = getVoidScale(age) * 0.5;

        for (Entity suckee : sucked) {
            if (suckee != this) {
                double dx = this.posX - suckee.posX;
                double dy = this.posY - suckee.posY;
                double dz = this.posZ - suckee.posZ;

                double lensquared = dx * dx + dy * dy + dz * dz;
                double len = Math.sqrt(lensquared);
                double lenn = len / suckrange;

                if (len <= suckrange) {
                    double strength = (1 - lenn) * (1 - lenn);
                    double power = 0.075 * radius;

                    suckee.motionX += (dx / len) * strength * power;
                    suckee.motionY += (dy / len) * strength * power;
                    suckee.motionZ += (dz / len) * strength * power;
                }
            }
        }

        if (this.worldObj.isRemote) return;

        // om nom nom
        double nomrange = radius * 0.95;
        AxisAlignedBB nomzone = AxisAlignedBB.getBoundingBox(
                this.posX - nomrange,
                this.posY - nomrange,
                this.posZ - nomrange,
                this.posX + nomrange,
                this.posY + nomrange,
                this.posZ + nomrange);

        List<EntityLivingBase> nommed = this.worldObj.selectEntitiesWithinAABB(EntityLivingBase.class, nomzone, ent -> {
            if (!(ent instanceof EntityLivingBase)) {
                return false;
            }

            if (ent instanceof EntityPlayer p) {
                return !p.capabilities.isCreativeMode;
            }

            return true;
        });

        for (EntityLivingBase nommee : nommed) {
            double dx = this.posX - nommee.posX;
            double dy = this.posY - nommee.posY;
            double dz = this.posZ - nommee.posZ;

            double lensquared = dx * dx + dy * dy + dz * dz;
            double len = Math.sqrt(lensquared);

            if (len <= nomrange) {
                nommee.attackEntityFrom(DamageSource.outOfWorld, 3.0f);
            }
        }

        // every half second, SMASH STUFF
        if (Config.endestGriefing && age % 10 == 0) {
            int bx = (int) Math.floor(this.posX);
            int by = (int) Math.floor(this.posY);
            int bz = (int) Math.floor(this.posZ);

            int blockrange = (int) Math.round(nomrange);
            int lx, ly, lz;

            for (int y = -blockrange; y <= blockrange; y++) {
                for (int z = -blockrange; z <= blockrange; z++) {
                    for (int x = -blockrange; x <= blockrange; x++) {
                        lx = bx + x;
                        ly = by + y;
                        lz = bz + z;

                        if (ly < 0 || ly > 255) {
                            continue;
                        }

                        double dist = Math.sqrt(x * x + y * y + z * z);
                        if (dist <= nomrange && !this.worldObj.isAirBlock(lx, ly, lz)) {
                            Block b = this.worldObj.getBlock(lx, ly, lz);
                            int meta = this.worldObj.getBlockMetadata(lx, ly, lz);

                            if (!Config.endestTileGriefing && this.worldObj.getTileEntity(lx, ly, lz) != null) {
                                continue;
                            }

                            float resist = b.getExplosionResistance(
                                    this,
                                    this.worldObj,
                                    lx,
                                    ly,
                                    lz,
                                    this.posX,
                                    this.posY,
                                    this.posZ);

                            if (resist <= 10.0 && checkPermissions(this.worldObj, lx, ly, lz, b, meta)) {
                                b.dropBlockAsItemWithChance(worldObj, lx, ly, lz, meta, 0.9f, 0);
                                this.worldObj.setBlockToAir(lx, ly, lz);
                            }
                        }
                    }
                }
            }
        }
    }

    private void setAge(int age) {
        dataWatcher.updateObject(12, age);
    }

    public int getAge() {
        return dataWatcher.getWatchableObjectInt(12);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound tag) {
        this.setAge(tag.getInteger("age"));

        if (tag.hasKey("owner", Constants.NBT.TAG_COMPOUND)) {
            NBTTagCompound ownerTag = tag.getCompoundTag("owner");

            String name = ownerTag.getString("name");
            if (name.isEmpty()) name = null;

            UUID uuid;
            if (ownerTag.hasKey("uuidLower", Constants.NBT.TAG_LONG)
                    && ownerTag.hasKey("uuidUpper", Constants.NBT.TAG_LONG)) {
                uuid = new UUID(tag.getLong("uuidUpper"), tag.getLong("uuidLower"));
            } else {
                uuid = null;
            }

            if (name != null || uuid != null) {
                this.throwerInfo = new GameProfile(uuid, name);
                this.thrower = new WeakReference<>(null);
            }
        }
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound tag) {
        tag.setInteger("age", this.getAge());

        if (this.throwerInfo != null) {
            NBTTagCompound ownerTag = new NBTTagCompound();

            final var ownerName = this.throwerInfo.getName();
            if (ownerName != null) {
                ownerTag.setString("name", ownerName);
            }

            final var ownerUUID = this.throwerInfo.getId();
            if (ownerUUID != null) {
                ownerTag.setLong("uuidUpper", ownerUUID.getMostSignificantBits());
                ownerTag.setLong("uuidLower", ownerUUID.getLeastSignificantBits());
            }

            tag.setTag("owner", ownerTag);
        }
    }

    public static double getVoidScale(double age) {
        double life = age / (double) maxLifetime;

        double curve;
        if (life < collapse) {
            curve = 0.005 + ease(1 - ((collapse - life) / collapse)) * 0.995;
        } else {
            curve = ease(1 - ((life - collapse) / (1 - collapse)));
        }
        return 10.0 * curve;
    }

    private static double ease(double in) {
        double t = in - 1;
        return Math.sqrt(1 - t * t);
    }

    @Override
    public float getShadowSize() {
        return 0.0F;
    }

    @Override
    public boolean func_145774_a(Explosion explosionIn, World worldIn, int x, int y, int z, Block blockIn,
            float unused) {
        // Can the final explosion break this block?
        return Config.endestGriefing && (Config.endestTileGriefing || worldIn.getTileEntity(x, y, z) == null)
                && checkPermissions(worldIn, x, y, z, blockIn, worldIn.getBlockMetadata(x, y, z));
    }

    private boolean checkPermissions(World worldIn, int x, int y, int z, Block block, int meta) {
        final var event = new BlockEvent.BreakEvent(x, y, z, worldIn, block, meta, getOwningPlayer());
        return !MinecraftForge.EVENT_BUS.post(event);
    }

    private @NotNull EntityPlayer getOwningPlayer() {
        // If throwerInfo is null, this pearl intentionally has no player.
        if (this.throwerInfo == null) return getDefaultFakePlayer();

        // If we have the player entity cached, return it.
        final EntityPlayer thrower = this.thrower.get();
        if (thrower != null) return thrower;

        // We have the player info, but no player - try to find the player.
        final var onlinePlayer = findPlayerByProfile(this.throwerInfo);
        if (onlinePlayer != null) {
            this.thrower = new WeakReference<>(onlinePlayer);
            return onlinePlayer;
        }

        // If the player is offline, make a fake player that pretends to be that player (same UUID & name)
        final var playerMimic = FakePlayerFactory.get((WorldServer) this.worldObj, this.throwerInfo);
        playerMimic.setPosition(this.posX, this.posY, this.posZ);
        this.thrower = new WeakReference<>(playerMimic);
        return playerMimic;
    }

    private @NotNull EntityPlayer getDefaultFakePlayer() {
        EntityPlayer fakePlayer = defaultFakePlayer.get();
        if (fakePlayer == null) {
            fakePlayer = FakePlayerFactory.get((WorldServer) this.worldObj, defaultProfile);
            defaultFakePlayer = new WeakReference<>(fakePlayer);
        }

        fakePlayer.setWorld(this.worldObj);
        fakePlayer.setPosition(this.posX, this.posY, this.posZ);
        return fakePlayer;
    }

    private static @Nullable EntityPlayerMP findPlayerByProfile(GameProfile profile) {
        final String throwerName = profile.getName();
        final UUID throwerUUID = profile.getId();

        final var players = MinecraftServer.getServer().getConfigurationManager().playerEntityList;
        if (throwerUUID != null) {
            for (var player : players) {
                if (throwerUUID.equals(player.getGameProfile().getId())) {
                    return player;
                }
            }
        } else {
            for (var player : players) {
                if (throwerName.equals(player.getCommandSenderName())) {
                    return player;
                }
            }
        }

        return null;
    }
}
