package main.SSJ;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.event.Listener;

public class ParticleEffects implements Listener {

	static Main plugin;
	  
	 @SuppressWarnings("static-access")
	public ParticleEffects(Main main)
	  {
		this.plugin = main;
	  }


	public static void runRedstoneHelix(Location loc) {

		double radius = 5;

		Particle.DustOptions redstone = new Particle.DustOptions(Color.RED, 1);

		for (double y = 5; y >= 0; y -= 0.07) {

			radius = y / 3;

			double x = radius * Math.cos(3 * y);
			double z = radius * Math.sin(3 * y);

			double y2 = 3 - y;

			Location loc2 = new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y2, loc.getZ() + z);
			loc2.getWorld().spawnParticle(Particle.REDSTONE, loc2, 1, redstone);
		}

		for (double y = 5; y >= 0; y -= 0.07) {
			radius = y / 3;
			double x = -(radius * Math.cos(3 * y));
			double z = -(radius * Math.sin(3 * y));

			double y2 = 3 - y;

			Location loc2 = new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y2, loc.getZ() + z);
			loc2.getWorld().spawnParticle(Particle.REDSTONE, loc2, 1, redstone);
		}
	}

	public static void runRedstoneHelix2(Location loc) {

		double radius = 5;

		Particle.DustOptions redstone = new Particle.DustOptions(Color.RED, 1);

		for (double y = 5; y >= 0; y -= 0.07) {

			radius = y / 3;

			double x = radius * Math.cos(3 * y);
			double z = radius * Math.sin(3 * y);

			double y2 = 3 - y;

			Location loc2 = new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y2, loc.getZ() + z);
			loc2.getWorld().spawnParticle(Particle.REDSTONE, loc2, 0, redstone);
		}

		for (double y = 5; y >= 0; y -= 0.07) {

			radius = y / 3;

			double x = -(radius * Math.cos(3 * y));
			double z = -(radius * Math.sin(3 * y));

			double y2 = 3 - y;

			Location loc2 = new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y2, loc.getZ() + z);
			loc2.getWorld().spawnParticle(Particle.REDSTONE, loc2, 0, redstone);
		}

	}

	public static void runKaiokenHelix(Location loc, String particlek1) {

			double radius = 5;

			for (double y = 5; y >= 0; y -= 0.07) {

				radius = y / 3;

				double x = radius * Math.cos(3 * y);
				double z = radius * Math.sin(3 * y);

				double y2 = 3 - y;

				Location loc2 = new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y2, loc.getZ() + z);
				loc2.getWorld().spawnParticle(Particle.valueOf(particlek1.toUpperCase()), loc2, 0);
			}

			for (double y = 5; y >= 0; y -= 0.07) {
				radius = y / 3;
				double x = -(radius * Math.cos(3 * y));
				double z = -(radius * Math.sin(3 * y));

				double y2 = 3 - y;

				Location loc2 = new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y2, loc.getZ() + z);
				loc2.getWorld().spawnParticle(Particle.valueOf(particlek1.toUpperCase()), loc2, 0);
			}
		}

	public static void runKaiokenHelix2(Location loc, String particlek2) {

			double radius = 5;

			for (double y = 5; y >= 0; y -= 0.07) {

				radius = y / 3;

				double x = radius * Math.cos(3 * y);
				double z = radius * Math.sin(3 * y);

				double y2 = 3 - y;

				Location loc2 = new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y2, loc.getZ() + z);
				loc2.getWorld().spawnParticle(Particle.valueOf(particlek2.toUpperCase()), loc2, 0);
			}

			for (double y = 5; y >= 0; y -= 0.07) {
				radius = y / 3;
				double x = -(radius * Math.cos(3 * y));
				double z = -(radius * Math.sin(3 * y));

				double y2 = 3 - y;

				Location loc2 = new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y2, loc.getZ() + z);
				loc2.getWorld().spawnParticle(Particle.valueOf(particlek2.toUpperCase()), loc2, 0);
			}
		}

	private static int stepX = 0;
    private static int stepY = 0;
    private static boolean reverse = false;

    private static int orbs = 4;
    private static int maxStepX = 80;
    private static int maxStepY = 60;
	
	public static void runHelix(Location loc, String particle1) {
			
			//double radius = 5;

			for (int i = 0; i < orbs; i++) {
	            double dx = -(MathL.cos((stepX / (double) maxStepX) * (Math.PI * 2) + (((Math.PI * 2) / orbs) * i))) * ((maxStepY - Math.abs(stepY)) / (double) maxStepY);
	            double dy = (stepY / (double) maxStepY) * 1.5;
	            double dz = -(MathL.sin((stepX / (double) maxStepX) * (Math.PI * 2) + (((Math.PI * 2) / orbs) * i))) * ((maxStepY - Math.abs(stepY)) / (double) maxStepY);
	            
	            Location loc2 = loc.clone().add(dx, dy, dz);
	            loc2.getWorld().spawnParticle(Particle.valueOf(particle1.toUpperCase()), loc2, 0);
	        }

//		for (double y = 5; y >= 0; y -= 0.07) {
//			radius = y / 3;
//			double x = -(radius * Math.cos(3 * y));
//			double z = -(radius * Math.sin(3 * y));
//
//			double y2 = 3 - y;
//
//			Location loc2 = new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y2, loc.getZ() + z);
//			loc2.getWorld().spawnParticle(Particle.valueOf(particle1.toUpperCase()), loc2, 0);
//		}

	}
	
	public static void updateTimers() {
        stepX++;
        if (stepX > maxStepX) {
            stepX = 0;
        }
        if (reverse) {
            stepY++;
            if (stepY > maxStepY)
                reverse = false;
        } else {
            stepY--;
            if (stepY < -maxStepY)
                reverse = true;
        }
    }
	
	public static void runHelix2(Location loc, String particle2) {

			double radius = 5;

			for (double y = 5; y >= 0; y -= 0.07) {

				radius = y / 3;

				double x = radius * Math.cos(3 * y);
				double z = radius * Math.sin(3 * y);

				double y2 = 3 - y;

				Location loc2 = new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y2, loc.getZ() + z);
				loc2.getWorld().spawnParticle(Particle.valueOf(particle2.toUpperCase()), loc2, 0);
			}

			for (double y = 5; y >= 0; y -= 0.07) {
				radius = y / 3;
				double x = -(radius * Math.cos(3 * y));
				double z = -(radius * Math.sin(3 * y));

				double y2 = 3 - y;

				Location loc2 = new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y2, loc.getZ() + z);
				loc2.getWorld().spawnParticle(Particle.valueOf(particle2.toUpperCase()), loc2, 0);
			}

		}

}
