package main.SSJ;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class ParticleSystem implements Listener {

	static HashMap<Player, Integer> cooldownTimeK;

	static Main plugin;

	@SuppressWarnings("static-access")
	public ParticleSystem(Main main) {
		this.plugin = main;
	}

	private static Map<CommandSender, BukkitTask> tasks = new HashMap<>();

	public static void ssjPs(CommandSender sender, Player player, String particle1, String particle2, Integer spc,
			Integer spc2) {

		BukkitTask t = tasks.put(sender, (new BukkitRunnable() {

			public void run() {

				Location location = player.getLocation();

				if (!particle2.isEmpty()) {

					if (particle2.contains("redstone")) {

						ParticleEffects.runRedstone2(player, location, spc2);

						ParticleEffects.runp(player, location, particle1, spc);

					} else if (particle1.contains("redstone")) {

						ParticleEffects.runRedstone(player, location, spc);

						ParticleEffects.runp2(player, location, particle2, spc2);

					} else if (particle1.contains("redstone") && particle2.contains("redstone")) {

						ParticleEffects.runRedstone(player, location, spc2);
						ParticleEffects.runRedstone2(player, location, spc2);

					} else {

						ParticleEffects.runp(player, location, particle1, spc);
						ParticleEffects.runp2(player, location, particle2, spc2);
					}

				} else if (particle2.isEmpty()) {

					if (particle1.contains("redstone")) {

						ParticleEffects.runRedstone(player, location, spc);

					} else {

						ParticleEffects.runp(player, location, particle1, spc);
					}

				}
			}
		}).runTaskTimerAsynchronously(plugin, 0L, 0L));
		if (t != null)
			t.cancel();

	}

	public static void kaiokenPs(CommandSender sender, Player player, String particlek1, String particlek2,
			Integer ktime, Integer kpc, Integer kpc2) {

		cooldownTimeK.put(player, ktime);
		BukkitTask t = tasks.put(sender, (new BukkitRunnable() {
			public void run() {
				cooldownTimeK.put(player, Integer.valueOf(((Integer) cooldownTimeK.get(player)).intValue() - 1));

				if (((Integer) cooldownTimeK.get(player)).intValue() == 0 || ktime == 0) {

					Listeners.removeP(player);

					cooldownTimeK.remove(player);
					cancel();
				}

				Location location = player.getLocation();

				if (!particlek2.isEmpty()) {

					if (particlek2.contains("redstone")) {

						ParticleEffects.runRedstone2(player, location, kpc2);
						ParticleEffects.runKaioken(player, location, particlek1, kpc);

					} else if (particlek1.contains("redstone")) {

						ParticleEffects.runKaioken2(player, location, particlek2, kpc2);
						ParticleEffects.runRedstone(player, location, kpc);

					} else if (particlek1.contains("redstone") && particlek2.contains("redstone")) {

						ParticleEffects.runRedstone(player, location, kpc);
						ParticleEffects.runRedstone2(player, location, kpc2);

					} else {

						ParticleEffects.runKaioken(player, location, particlek1, kpc);
						ParticleEffects.runKaioken2(player, location, particlek2, kpc2);
					}

				} else if (particlek2.isEmpty()) {

					if (particlek1.contains("redstone")) {

						ParticleEffects.runRedstone(player, location, kpc);

					} else {

						ParticleEffects.runKaioken(player, location, particlek1, kpc);

					}
				}
			}

		}).runTaskTimer(plugin, 0L, 0L));

		if (t != null)
			t.cancel();

	}

	public static void cancel(Player player) {
		BukkitTask t = tasks.get(player);
		if (t != null)
			t.cancel();
	}

}
