package fox.spiteful.avaritia;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.IChatComponent;

public class DamageSourceInfinitySword extends EntityDamageSource {

    public DamageSourceInfinitySword(Entity source) {
        super("infinity", source);
    }

    @Override
    public IChatComponent func_151519_b(EntityLivingBase entity) { // getDeathMessage
        String s = "death.attack.infinity";
        int rando = entity.worldObj.rand.nextInt(5);
        if (rando != 0) s = s + "." + rando;
        // func_145748_c_ = getFormattedCommandSenderName
        return new ChatComponentTranslation(s, entity.func_145748_c_(), this.damageSourceEntity.func_145748_c_());
    }

    @Override
    public boolean isDifficultyScaled() {
        return false;
    }

}
