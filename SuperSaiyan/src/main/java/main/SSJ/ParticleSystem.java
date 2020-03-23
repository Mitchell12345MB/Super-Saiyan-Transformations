package main.SSJ;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class ParticleSystem implements Listener{

	static HashMap<Player, Integer> cooldownTimeK;
	
	static Main plugin;
	
	@SuppressWarnings("static-access")
	public ParticleSystem(Main main) {
		this.plugin = main;
	}

	private static Map<CommandSender, BukkitTask> tasks = new HashMap<>();
	
	public static void ssjPs(CommandSender sender, Player player, String particle1, String particle2) {
		
		BukkitTask t = tasks.put(sender, (new BukkitRunnable() {

			public void run() {

				Location location = player.getLocation();

				if (!particle2.isEmpty()) {

					if (particle2.contains("redstone")) {

						ParticleEffects.runRedstoneHelix2(location);

						ParticleEffects.runHelix(location, particle1);

					} else if (particle1.contains("redstone")) {

						ParticleEffects.runRedstoneHelix(location);

						ParticleEffects.runHelix2(location, particle2);

					} else if (particle1.contains("redstone")
							&& particle2.contains("redstone")) {

						ParticleEffects.runRedstoneHelix(location);
						ParticleEffects.runRedstoneHelix2(location);

					} else {

						ParticleEffects.runHelix(location, particle1);
						ParticleEffects.runHelix2(location, particle2);
					}

				} else if (particle2.isEmpty()) {
					
					if (particle1.contains("redstone")) {

						ParticleEffects.runRedstoneHelix(location);

					} else {

						ParticleEffects.runHelix(location, particle1);
						ParticleEffects.updateTimers();
					}

				}
			}
		}).runTaskTimerAsynchronously(plugin, 0L, 0L));
		if (t != null)
			t.cancel();
		
	}
	
public static void kaiokenPs(CommandSender sender, Player player, String particlek1, String particlek2, Integer ktime) {
		
	cooldownTimeK.put(player, ktime);
	BukkitTask t = tasks.put(sender, (new BukkitRunnable() {
		public void run() {
			cooldownTimeK.put(player, Integer.valueOf(
					((Integer) cooldownTimeK.get(player)).intValue() - 1));

			if (((Integer) cooldownTimeK.get(player)).intValue() == 0
					|| ktime == 0) {

				Listeners.removeP(player);

				cooldownTimeK.remove(player);
				cancel();
			}
			
			Location location = player.getLocation();

			if (!particlek2.isEmpty()) {

				if (particlek2.contains("redstone")) {

					ParticleEffects.runRedstoneHelix2(location);
					ParticleEffects.runKaiokenHelix(location, particlek1);

				} else if (particlek1.contains("redstone")) {

					ParticleEffects.runKaiokenHelix2(location, particlek2);
					ParticleEffects.runRedstoneHelix(location);

				} else if (particlek1.contains("redstone")
						&& particlek2.contains("redstone")) {

					ParticleEffects.runRedstoneHelix(location);
					ParticleEffects.runRedstoneHelix2(location);

				} else {

					ParticleEffects.runKaiokenHelix(location, particlek1);
					ParticleEffects.runKaiokenHelix2(location, particlek2);
				}

			} else if (particlek2.isEmpty()) {

				if (particlek1.contains("redstone")) {

					ParticleEffects.runRedstoneHelix(location);

				} else {

					ParticleEffects.runKaiokenHelix(location, particlek1);

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
