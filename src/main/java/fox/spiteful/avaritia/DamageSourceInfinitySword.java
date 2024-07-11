package fox.spiteful.avaritia;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.IChatComponent;

public class DamageSourceInfinitySword extends EntityDamageSource {

    private static final Random randy = new Random();

    public DamageSourceInfinitySword(Entity source) {
        super("infinity", source);
    }

    @Override
    public IChatComponent func_151519_b(EntityLivingBase p_151519_1_) {
        ItemStack itemstack = this.damageSourceEntity instanceof EntityLivingBase
                ? ((EntityLivingBase) this.damageSourceEntity).getHeldItem()
                : null;
        String s = "death.attack.infinity";
        int rando = randy.nextInt(5);
        if (rando != 0) s = s + "." + rando;
        return new ChatComponentTranslation(
                s,
                p_151519_1_.func_145748_c_(), this.damageSourceEntity.func_145748_c_());
    }

    @Override
    public boolean isDifficultyScaled() {
        return false;
    }

}
