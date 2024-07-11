package fox.spiteful.avaritia.compat.tails;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;

public class InfiniteFoxes {

    private static FakeTailEntity fakeEntity;
    private static Object tailPartInfo;
    private static Object earPartInfo;
    private static Object foxTailRender;
    private static Object foxEarsRender;
    private static Method m_RenderPart_render;

    public static void floof() {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            grabReflections();
            MinecraftForge.EVENT_BUS.register(new InfiniteFoxes());
        }
    }

    @SuppressWarnings("unchecked")
    public static void grabReflections() {
        try {
            @SuppressWarnings("rawtypes")
            Class<? extends Enum> c_PartType = (Class<? extends Enum>) Class
                    .forName("kihira.tails.common.PartsData$PartType");
            Class<?> c_PartInfo = Class.forName("kihira.tails.common.PartInfo");

            // Setup Info
            Constructor<?> constr = c_PartInfo.getConstructor(
                    boolean.class,
                    int.class,
                    int.class,
                    int.class,
                    int[].class,
                    c_PartType,
                    ResourceLocation.class);
            tailPartInfo = constr.newInstance(
                    true,
                    0,
                    2,
                    0,
                    new int[] { -5480951, -6594259, -5197647 },
                    Enum.valueOf(c_PartType, "TAIL"),
                    null);
            earPartInfo = constr.newInstance(
                    true,
                    0,
                    0,
                    0,
                    new int[] { -5480951, 0xFF000000, -5197647 },
                    Enum.valueOf(c_PartType, "EARS"),
                    null);

            // Grab renderparts + render method
            Class<?> c_RenderPart = Class.forName("kihira.tails.client.render.RenderPart");
            Class<?> c_PartRegistry = Class.forName("kihira.tails.client.PartRegistry");
            Method m_getRenderPart = c_PartRegistry.getMethod("getRenderPart", c_PartType, int.class);
            foxTailRender = m_getRenderPart.invoke(null, Enum.valueOf(c_PartType, "TAIL"), 0);
            foxEarsRender = m_getRenderPart.invoke(null, Enum.valueOf(c_PartType, "EARS"), 0);

            m_RenderPart_render = c_RenderPart.getMethod(
                    "render",
                    EntityLivingBase.class,
                    c_PartInfo,
                    double.class,
                    double.class,
                    double.class,
                    float.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void renderInfinitatoFluff(float partialTicks) {
        if (fakeEntity == null) {
            fakeEntity = new FakeTailEntity(Minecraft.getMinecraft().theWorld);
        }

        if (m_RenderPart_render != null) {
            try {
                GL11.glScalef(2, 2, 2);
                GL11.glTranslatef(0, .25f, .5f);
                m_RenderPart_render.invoke(foxTailRender, fakeEntity, tailPartInfo, 0, 0, 0, partialTicks);
                GL11.glTranslatef(0, .25f, -.75f);
                GL11.glScalef(2, 2, 2);
                m_RenderPart_render.invoke(foxEarsRender, fakeEntity, earPartInfo, 0, 0, 0, partialTicks);
            } catch (Exception e) {}
        }
    }

    @SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload e) {
        if (fakeEntity != null) {
            fakeEntity.setDead();
            fakeEntity = null;
        }
    }

    public static class FakeTailEntity extends EntityLiving {

        public FakeTailEntity(World world) {
            super(world);
        }

        @Override
        public void writeToNBT(NBTTagCompound nbt) {}

        @Override
        public void readFromNBT(NBTTagCompound nbt) {}
    }
}
