package me.santio.paper;

import me.santio.common.MinehutVotes;
import me.santio.common.services.EpochService;
import me.santio.common.services.VoteService;
import me.santio.paper.listeners.JoinListener;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("MissingJavadoc")
public final class MinehutVotesPaper extends JavaPlugin {
    
    private final EpochService epochService = new EpochService();
    public VoteService voteService;
    
    @Override
    public void onEnable() {
        // Initialize the config
        this.saveDefaultConfig();
        
        if (!this.getConfig().contains("server_id")) {
            this.getLogger().warning("Your config.yml is missing a server id, your config.yml will now be reset.");
            this.resetConfig();
        }
        
        // Authenticate the Minehut Vote Client
        voteService = MinehutVotes.authenticate(
            this.getConfig().getString("server_id"),
            this.getConfig().getString("api_key")
        );
        
        // Register the service
        this.getServer().getServicesManager().register(
            VoteService.class,
            voteService,
            this,
            ServicePriority.Normal
        );
        
        // Register the event listener
        this.getServer().getPluginManager().registerEvents(new JoinListener(this), this);
    }
    
    @Override
    public void onDisable() {
        MinehutVotes.close();
    }
    
    private void resetConfig() {
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
    }
    
}
