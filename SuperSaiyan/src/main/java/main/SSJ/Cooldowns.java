package main.SSJ;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;

public class Cooldowns implements Listener{

	static HashMap<Player, Integer> cooldownTime;
	static HashMap<Player, BukkitRunnable> cooldownTask;
	
	static Main plugin;
	
	@SuppressWarnings("static-access")
	public Cooldowns(Main main) {
		this.plugin = main;
	}
	
	public static void ssjCooldown(Player player, Integer stime) {
		
		cooldownTime.put(player, stime);
		cooldownTask.put(player, new BukkitRunnable() {
			public void run() {
				cooldownTime.put(player, Integer.valueOf(
						((Integer) cooldownTime.get(player)).intValue() - 1));
				if (((Integer) cooldownTime.get(player)).intValue() == 0
						|| stime == 0
						|| player.isOp()) {
					cooldownTime.remove(player);
					cooldownTask.remove(player);
					cancel();
				}
			}
		});
		((BukkitRunnable) cooldownTask.get(player)).runTaskTimer(plugin, 20L, 20L);
		if (player.isOp()) {
			player.sendMessage(String.valueOf(plugin.Prefix) + ChatColor.DARK_GREEN
					+ "You are OP, cooldown doesn't apply to you!");
		} else if (stime == 0) {
			player.sendMessage(String.valueOf(plugin.Prefix) + ChatColor.DARK_GREEN
					+ "Cooldown for this transformation is set to 0.");
		} else if (((Integer) cooldownTime.get(player)).intValue() == 0) {
			player.sendMessage(String.valueOf(plugin.Prefix) + ChatColor.DARK_GREEN
					+ "Cooldown is finished!");
		} else {
			player.sendMessage(String.valueOf(plugin.Prefix) + "You're now on a "
					+ stime
					+ " second cooldown");
		}
		
	}
	
	public static void kaiokenCooldown(Player player, Integer ktime2) {
		
		cooldownTime.put(player, ktime2);
		cooldownTask.put(player, new BukkitRunnable() {

			public void run() {

				cooldownTime.put(player, Integer.valueOf(
						((Integer) cooldownTime.get(player)).intValue() - 1));

				if (((Integer)cooldownTime.get(player)).intValue() == 0
						|| ktime2 == 0
						|| player.isOp()) {

					cooldownTime.remove(player);
					cooldownTask.remove(player);
					cancel();

				}
			}
		});

		((BukkitRunnable) cooldownTask.get(player)).runTaskTimer(plugin, 20L, 20L);

		if (player.isOp()) {
			player.sendMessage(String.valueOf(plugin.Prefix) + ChatColor.DARK_GREEN
					+ "You are OP, cooldown doesn't apply to you!");
		} else if (ktime2 == 0) {
			player.sendMessage(String.valueOf(plugin.Prefix) + ChatColor.DARK_GREEN
					+ "Cooldown for this transformation is set to 0.");
		} else if (((Integer) cooldownTime.get(player)).intValue() == 0) {
			player.sendMessage(String.valueOf(plugin.Prefix) + ChatColor.DARK_GREEN
					+ "Cooldown is finished!");
		} else {
			player.sendMessage(String.valueOf(plugin.Prefix) + "You're now on a "
					+ ktime2
					+ " second cooldown");
		}
		
	}

}
