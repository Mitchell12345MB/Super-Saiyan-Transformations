package org.apache.maven.supersaiyantransformationsp;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

public class SSJParticleSystem {

    private final SSJ ssj;

    private final Map<CommandSender, BukkitTask> tasks = new HashMap<>();

    HashMap<Player, Integer> cooldownTimeK;

    public SSJParticleSystem(SSJ ssj) {

        this.ssj = ssj;

    }

    public void ssjPs(CommandSender sender, Player player, String particle1, String particle2, Integer spc,
                      Integer spc2) {

        BukkitTask t = tasks.put(sender, (new BukkitRunnable() {

            public void run() {

                if (!particle2.isEmpty()) {

                    if (particle2.contains("redstone")) {

                        ssj.getPe().runRedstone2(player, spc2);

                        ssj.getPe().runp(player, particle1, spc);

                    } else if (particle1.contains("redstone")) {

                        ssj.getPe().runRedstone(player, spc);

                        ssj.getPe().runp2(player, particle2, spc2);

                    } else if (particle1.contains("redstone") && particle2.contains("redstone")) {

                        ssj.getPe().runRedstone(player, spc2);

                        ssj.getPe().runRedstone2(player, spc2);

                    } else {

                        ssj.getPe().runp(player, particle1, spc);

                        ssj.getPe().runp2(player, particle2, spc2);
                    }

                } else {

                    if (particle1.contains("redstone")) {

                        ssj.getPe().runRedstone(player, spc);

                    } else {

                        ssj.getPe().runp(player, particle1, spc);
                    }

                }

            }
        }).runTaskTimerAsynchronously(ssj, 0L, 0L));

        if (t != null)

            t.cancel();

    }

    public void kaiokenPs(CommandSender sender, Player player, String particlek1, String particlek2,
                          Integer ktime, Integer kpc, Integer kpc2) {

        cooldownTimeK.put(player, ktime);

        BukkitTask t = tasks.put(sender, (new BukkitRunnable() {
            public void run() {

                cooldownTimeK.put(player, cooldownTimeK.get(player) - 1);

                if (cooldownTimeK.get(player) == 0 || ktime == 0) {

                    ssj.getSSJMethodChecks().removeP(player);

                    cooldownTimeK.remove(player);

                    cancel();

                }

                if (!particlek2.isEmpty()) {

                    if (particlek2.contains("redstone")) {

                        ssj.getPe().runRedstone2(player, kpc2);

                        ssj.getPe().runKaioken(player, particlek1, kpc);

                    } else if (particlek1.contains("redstone")) {

                        ssj.getPe().runKaioken2(player, particlek2, kpc2);

                        ssj.getPe().runRedstone(player, kpc);

                    } else if (particlek1.contains("redstone") && particlek2.contains("redstone")) {

                        ssj.getPe().runRedstone(player, kpc);

                        ssj.getPe().runRedstone2(player, kpc2);

                    } else {

                        ssj.getPe().runKaioken(player, particlek1, kpc);

                        ssj.getPe().runKaioken2(player, particlek2, kpc2);

                    }

                } else {

                    if (particlek1.contains("redstone")) {

                        ssj.getPe().runRedstone(player, kpc);

                    } else {

                        ssj.getPe().runKaioken(player, particlek1, kpc);

                    }

                }

            }

        }).runTaskTimer(ssj, 0L, 0L));

        if (t != null)

            t.cancel();

    }

    public void cancel(Player player) {

        BukkitTask t = tasks.get(player);

        if (t != null)

            t.cancel();
    }

}
