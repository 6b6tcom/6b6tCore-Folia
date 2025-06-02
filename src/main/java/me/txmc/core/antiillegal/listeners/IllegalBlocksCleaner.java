package me.txmc.core.antiillegal.listeners;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

import static java.lang.Math.abs;

/**
 * Listener that cleans illegal blocks in chunks when they are loaded.
 * <p>
 * Rebranded for 6b6t.
 * </p>
 */
public class IllegalBlocksCleaner implements Listener {

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        if (event.isNewChunk()) return;

        Chunk chunk = event.getChunk();
        if (abs(chunk.getX()) >= 1000000 || abs(chunk.getZ()) >= 1000000) return;

        int yUpperLimit = event.getWorld().getMaxHeight();
        int yLowerLimit = event.getWorld().getMinHeight();

        World.Environment worldEnv = event.getWorld().getEnvironment();

        if (worldEnv == World.Environment.NETHER) {
            yUpperLimit = 127;
        }

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = yLowerLimit; y < yUpperLimit; y++) {
                    Block block = chunk.getBlock(x, y, z);
                    Material type = block.getType();

                    // Nether rules
                    if (worldEnv == World.Environment.NETHER && (
                            type == Material.END_PORTAL_FRAME ||
                            type == Material.REINFORCED_DEEPSLATE ||
                            type == Material.BARRIER ||
                            type == Material.LIGHT ||
                            type == Material.END_PORTAL ||
                            (type == Material.BEDROCK && y >= yLowerLimit + 5 && y <= yUpperLimit - 5))) {
                        block.setType(Material.AIR);
                    }

                    // Overworld rules
                    if (worldEnv == World.Environment.NORMAL && (
                            type == Material.END_PORTAL_FRAME ||
                            type == Material.REINFORCED_DEEPSLATE ||
                            type == Material.BARRIER ||
                            type == Material.LIGHT ||
                            type == Material.END_PORTAL ||
                            (type == Material.BEDROCK && y >= yLowerLimit + 5))) {
                        block.setType(Material.AIR);
                    }

                    // End rules
                    if (worldEnv == World.Environment.THE_END && (
                            type == Material.END_PORTAL_FRAME ||
                            type == Material.REINFORCED_DEEPSLATE ||
                            type == Material.BARRIER ||
                            type == Material.LIGHT ||
                            type == Material.END_PORTAL ||
                            type == Material.BEDROCK)) {
                        block.setType(Material.AIR);
                    }
                }
            }
        }
    }
}
