package com.blbilink.blbilogin.modules.effects;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class Particles {
    public void createFallingParticlesAroundPlayer(Player player) {
        Location playerLocation = player.getLocation();
        double radius = 1.5; // radius of the particle circle
        int particleCount = 10; // particles per circle

        for (int i = 0; i < particleCount; i++) {
            // Calculate particle location
            double angle = 2 * Math.PI * i / particleCount;
            double x = playerLocation.getX() + radius * Math.cos(angle);
            double y = playerLocation.getY() + 1;
            double z = playerLocation.getZ() + radius * Math.sin(angle);

            // Set particle location
            Location particleLocation = new Location(playerLocation.getWorld(), x, y, z);


            // Spawn particle effect
            Particle particle = Particle.valueOf("FIREWORKS_SPARK");
            playerLocation.getWorld().spawnParticle(particle, particleLocation, 1, 0, 0, 0, 0.1); // use Firework particle effect
        }

    }
}
