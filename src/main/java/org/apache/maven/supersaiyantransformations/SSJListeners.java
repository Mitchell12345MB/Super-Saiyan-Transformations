package org.apache.maven.supersaiyantransformations;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class SSJListeners implements Listener {

    private final SSJ ssj;

    public SSJListeners(SSJ ssj) {

        this.ssj = ssj;

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {

        Player player = event.getPlayer();

        ssj.getPs().cancel(player);

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        ssj.getSSJMethodChecks().removeP(player);

    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {

        Player player = event.getEntity();

        ssj.getSSJMethodChecks().removeP(player);

        ssj.getPs().cancel(player);

    }

    @EventHandler
    public void onPlayerTeleportTeleport(PlayerTeleportEvent event) {

        if (ssj.getConfig().getBoolean("Teleportation_Removal")) {

            Player player = event.getPlayer();

            if ((event.getCause() == PlayerTeleportEvent.TeleportCause.PLUGIN) || (event.getCause() == PlayerTeleportEvent.TeleportCause.COMMAND)) {

                ssj.getSSJMethodChecks().removeP(player);

                ssj.getPs().cancel(player);
            }
        }
    }
}
