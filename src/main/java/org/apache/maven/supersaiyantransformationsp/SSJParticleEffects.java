package org.apache.maven.supersaiyantransformationsp;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class SSJParticleEffects {

    private final SSJ ssj;

    public SSJParticleEffects(SSJ ssj) {

        this.ssj = ssj;

    }

    public void runRedstone(Player player, Integer kpc) {

        Particle.DustOptions redstone = new Particle.DustOptions(Color.RED, 1);

        World locq = player.getWorld();

        Location locs = player.getLocation();

        double x = locs.getX();

        double y = locs.getY() + 1;

        double z = locs.getZ();

        locq.spawnParticle(Particle.REDSTONE, x, y, z, kpc, 0.3, 1, 0.3, 0.05, redstone);

    }

    public void runRedstone2(Player player, Integer kpc2) {

        Particle.DustOptions redstone = new Particle.DustOptions(Color.RED, 1);

        World locq = player.getWorld();

        Location locs = player.getLocation();

        double x = locs.getX();

        double y = locs.getY() + 1;

        double z = locs.getZ();

        locq.spawnParticle(Particle.REDSTONE, x, y, z, kpc2, 0.3, 1, 0.3, 0.05, redstone);

    }

    public void runKaioken(Player player, String particlek1, Integer kpc) {

        World locq = player.getWorld();

        Location locs = player.getLocation();

        double x = locs.getX();

        double y = locs.getY() + 1;

        double z = locs.getZ();

        locq.spawnParticle(Particle.valueOf(particlek1.toUpperCase()), x, y, z, kpc, 0.3, 1, 0.3, 0.05, null);

    }

    public void runKaioken2(Player player, String particlek1, Integer kpc2) {

        World locq = player.getWorld();

        Location locs = player.getLocation();

        double x = locs.getX();

        double y = locs.getY() + 1;

        double z = locs.getZ();

        locq.spawnParticle(Particle.valueOf(particlek1.toUpperCase()), x, y, z, kpc2, 0.3, 1, 0.3, 0.05, null);

    }


    public void runp(Player player, String particle1, Integer spc) {

        World locq = player.getWorld();

        Location locs = player.getLocation();

        double x = locs.getX();

        double y = locs.getY() + 1;

        double z = locs.getZ();

        locq.spawnParticle(Particle.valueOf(particle1.toUpperCase()), x, y, z, spc, 0.3, 1, 0.3, 0.05, null);

    }

    public void runp2(Player player, String particle2, Integer spc2) {

        World locq = player.getWorld();

        Location locs = player.getLocation();

        double x = locs.getX();

        double y = locs.getY() + 1;

        double z = locs.getZ();

        locq.spawnParticle(Particle.valueOf(particle2.toUpperCase()), x, y, z, spc2, 0.3, 1, 0.3, 0.05, null);
    }
}
