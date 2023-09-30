package me.santio.paper.listeners;

import me.santio.common.exceptions.MinehutException;
import me.santio.common.models.Vote;
import me.santio.paper.MinehutVotesPaper;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Handles checking if a player has voted for the server when they join.
 */
public class JoinListener implements Listener {
    
    private final MinehutVotesPaper plugin;
    
    /**
     * Creates a new JoinListener instance.
     * @param plugin The plugin instance.
     */
    public JoinListener(MinehutVotesPaper plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        
        Bukkit.broadcastMessage("§aChecking if " + event.getPlayer().getName() + " has voted...");
        plugin.voteService.request(event.getPlayer().getName()).thenAccept(request -> {
            try {
                final Vote vote = plugin.voteService.validateResponse(request);
                Bukkit.broadcastMessage("§aGot the vote object");
            } catch (MinehutException e) {
                Bukkit.broadcastMessage("§cError: " + e.getMessage());
            }
        });
    
    }
    
}
