package main.SSJ;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class ParticleEffects implements Listener {

	static Main plugin;
	  
	 @SuppressWarnings("static-access")
	public ParticleEffects(Main main)
	  {
		this.plugin = main;
	  }


	public static void runRedstone(Player player, Location loc, Integer kpc) {

		Particle.DustOptions redstone = new Particle.DustOptions(Color.RED, 1);

		World locq = player.getWorld();
		Location locs = player.getLocation();
		
		double x = locs.getX();
		double y = locs.getY() + 1;
		double z = locs.getZ();
		
		locq.spawnParticle(Particle.REDSTONE, x, y, z, kpc, 0.3, 1, 0.3, 0.05, redstone);
	}

	public static void runRedstone2(Player player, Location loc, Integer kpc2) {

		Particle.DustOptions redstone = new Particle.DustOptions(Color.RED, 1);

		World locq = player.getWorld();
		Location locs = player.getLocation();
		
		double x = locs.getX();
		double y = locs.getY() + 1;
		double z = locs.getZ();
		
		locq.spawnParticle(Particle.REDSTONE, x, y, z, kpc2, 0.3, 1, 0.3, 0.05, redstone);
	}

	public static void runKaioken(Player player, Location loc, String particlek1, Integer kpc) {

		World locq = player.getWorld();
		Location locs = player.getLocation();
		
		double x = locs.getX();
		double y = locs.getY() + 1;
		double z = locs.getZ();
		
		locq.spawnParticle(Particle.valueOf(particlek1.toUpperCase()), x, y, z, kpc, 0.3, 1, 0.3, 0.05);
		
		}

	public static void runKaioken2(Player player, Location loc, String particlek1, Integer kpc2) {

		World locq = player.getWorld();
		Location locs = player.getLocation();
		
		double x = locs.getX();
		double y = locs.getY() + 1;
		double z = locs.getZ();
		
		locq.spawnParticle(Particle.valueOf(particlek1.toUpperCase()), x, y, z, kpc2, 0.3, 1, 0.3, 0.05);
		}
	
	
	public static void runp(Player player, Location loc, String particle1, Integer spc) {
			
		World locq = player.getWorld();
		Location locs = player.getLocation();
		
		double x = locs.getX();
		double y = locs.getY() + 1;
		double z = locs.getZ();
		
		locq.spawnParticle(Particle.valueOf(particle1.toUpperCase()), x, y, z, spc, 0.3, 1, 0.3, 0.05);
		
	}
	
	public static void runp2(Player player, Location loc, String particle2, Integer spc2) {

		World locq = player.getWorld();
		Location locs = player.getLocation();
		
		double x = locs.getX();
		double y = locs.getY() + 1;
		double z = locs.getZ();
		
		locq.spawnParticle(Particle.valueOf(particle2.toUpperCase()), x, y, z, spc2, 0.3, 1, 0.3, 0.05);
	}

}
