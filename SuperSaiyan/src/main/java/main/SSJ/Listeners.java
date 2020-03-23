package main.SSJ;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.potion.PotionEffectType;

public class Listeners implements Listener {

	static Main plugin;

	@SuppressWarnings("static-access")
	public Listeners(Main main) {
		this.plugin = main;
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		ParticleSystem.cancel(player);
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		removeP(player);
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		removeP(player);
		ParticleSystem.cancel(player);
	}

	@EventHandler
	public void onPlayerTeleportTeleport(PlayerTeleportEvent event) {
		if (plugin.getConfig().getBoolean("Teleportation_Removal")) {
			Player player = event.getPlayer();
			if ((event.getCause() == PlayerTeleportEvent.TeleportCause.PLUGIN)
					|| (event.getCause() == PlayerTeleportEvent.TeleportCause.COMMAND)) {
				removeP(player);
				ParticleSystem.cancel(player);
			}
		}
	}

	public static void removeP(Player player) {

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