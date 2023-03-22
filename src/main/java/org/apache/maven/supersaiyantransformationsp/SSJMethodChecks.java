package org.apache.maven.supersaiyantransformationsp;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class SSJMethodChecks {

    private SSJ ssj;

    public SSJMethodChecks (SSJ ssj) {

        this.ssj = ssj;

    }

    public void removeP(Player player) {

            player.removePotionEffect(PotionEffectType.FAST_DIGGING);

            player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);

            player.removePotionEffect(PotionEffectType.HEAL);

            player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);

            player.removePotionEffect(PotionEffectType.JUMP);

            player.removePotionEffect(PotionEffectType.REGENERATION);

            player.removePotionEffect(PotionEffectType.SATURATION);

            player.removePotionEffect(PotionEffectType.SPEED);

            player.removePotionEffect(PotionEffectType.HEALTH_BOOST);

            player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);

    }

}
