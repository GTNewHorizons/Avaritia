package fox.spiteful.avaritia;

import java.util.ArrayList;

import net.minecraft.potion.Potion;

public class PotionHelper {

    private static ArrayList<Potion> badPotions;

    public static void healthInspection() {
        badPotions = new ArrayList<Potion>();
        for (Potion potion : Potion.potionTypes) {
            if (potion != null && potion.isBadEffect) badPotions.add(potion);
        }
    }

    public static boolean badPotion(Potion effect) {
        return badPotions.contains(effect);
    }
}
