package org.apache.maven.supersaiyantransformations;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class SSJCooldowns {

    private final SSJ ssj;

    HashMap<Player, Integer> cooldownTime;

    HashMap<Player, BukkitRunnable> cooldownTask;

    public SSJCooldowns(SSJ ssj) {
        this.ssj = ssj;
    }

    public void ssjCooldown(Player player, Integer stime) {

        cooldownTime.put(player, stime);

        cooldownTask.put(player, new BukkitRunnable() {

            public void run() {

                cooldownTime.put(player, cooldownTime.get(player) - 1);

                if (cooldownTime.get(player) == 0
                        || stime == 0
                        || player.isOp()) {

                    cooldownTime.remove(player);

                    cooldownTask.remove(player);

                    cancel();

                }

            }
        });

        cooldownTask.get(player).runTaskTimer(ssj, 20L, 20L);

        if (player.isOp()) {

            player.sendMessage(String.valueOf(ssj.Prefix) + ChatColor.DARK_GREEN
                    + "You are OP, cooldown doesn't apply to you!");

        } else if (stime == 0) {

            player.sendMessage(String.valueOf(ssj.Prefix) + ChatColor.DARK_GREEN
                    + "Cooldown for this transformation is set to 0.");

        } else if (cooldownTime.get(player) == 0) {

            player.sendMessage(String.valueOf(ssj.Prefix) + ChatColor.DARK_GREEN
                    + "Cooldown is finished!");

        } else {

            player.sendMessage(ssj.Prefix + "You're now on a "
                    + stime
                    + " second cooldown");

        }

    }

    public void kaiokenCooldown(Player player, Integer ktime2) {

        cooldownTime.put(player, ktime2);

        cooldownTask.put(player, new BukkitRunnable() {

            public void run() {

                cooldownTime.put(player, cooldownTime.get(player) - 1);

                if (cooldownTime.get(player) == 0
                        || ktime2 == 0
                        || player.isOp()) {

                    cooldownTime.remove(player);

                    cooldownTask.remove(player);

                    cancel();

                }
            }
        });

        cooldownTask.get(player).runTaskTimer(ssj, 20L, 20L);

        if (player.isOp()) {

            player.sendMessage(String.valueOf(ssj.Prefix) + ChatColor.DARK_GREEN
                    + "You are OP, cooldown doesn't apply to you!");

        } else if (ktime2 == 0) {

            player.sendMessage(String.valueOf(ssj.Prefix) + ChatColor.DARK_GREEN
                    + "Cooldown for this transformation is set to 0.");

        } else if (cooldownTime.get(player) == 0) {

            player.sendMessage(String.valueOf(ssj.Prefix) + ChatColor.DARK_GREEN
                    + "Cooldown is finished!");

        } else {

            player.sendMessage(ssj.Prefix + "You're now on a "
                    + ktime2
                    + " second cooldown");
        }
    }

}
