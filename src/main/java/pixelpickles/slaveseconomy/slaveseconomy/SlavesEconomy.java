package pixelpickles.slaveseconomy.slaveseconomy;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import pixelpickles.slaveseconomy.slaveseconomy.Api.SlavesApi;
import pixelpickles.slaveseconomy.slaveseconomy.Events.JoinEvent;
import pixelpickles.slaveseconomy.slaveseconomy.Papi.SpigotExpansion;
import pixelpickles.slaveseconomy.slaveseconomy.Vault.EconomyImplementer;
import pixelpickles.slaveseconomy.slaveseconomy.Vault.VaultHook;
import pixelpickles.slaveseconomy.slaveseconomy.commands.SlaveCommand;
import pixelpickles.slaveseconomy.slaveseconomy.commands.SlaveTabComplition;

import javax.security.auth.login.Configuration;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class SlavesEconomy extends JavaPlugin {
    public static SlavesEconomy plugin;
    public SlavesApi slavesApi;
    public EconomyImplementer newEconomyImplementer;
    public VaultHook vaultHook;
    public final HashMap<UUID, Double> playerBank = new HashMap<>();
    public FileConfiguration config = getConfig();
    @Override
    public void onEnable() {
        plugin = this;
        plugin.saveConfig();

        this.getCommand("slave").setExecutor(new SlaveCommand());
        getCommand("slave").setTabCompleter(new SlaveTabComplition());


        newEconomyImplementer = new EconomyImplementer();
        vaultHook = new VaultHook();
        slavesApi = new SlavesApi();

        new SpigotExpansion().register();

        vaultHook.hook();
        load();
        getServer().getPluginManager().registerEvents(new JoinEvent(), this);

    }

    @Override
    public void onDisable() {
        vaultHook.unhook();
        save();
    }

    private class MyTask extends BukkitRunnable {
        @Override
        public void run() {
            for (Player e : getServer().getOnlinePlayers()) {
                SlavesEconomy plugin = SlavesEconomy.plugin;
                if (!plugin.playerBank.containsKey(e.getPlayer().getUniqueId())) {
                    plugin.playerBank.put((e.getPlayer()).getUniqueId(), (double) 0);
                }
            }
        }
    }

    public void save(){
        for(Map.Entry<UUID, Double> entry: playerBank.entrySet()) {
            config.set("Db." + entry.getKey(), entry.getValue());
        }
        saveConfig();
    }

    public void load() {
        ConfigurationSection dbSection = config.getConfigurationSection("Db");
        if (dbSection != null) {
            for (String key : dbSection.getKeys(false)) {
                plugin.playerBank.put(UUID.fromString(key), plugin.getConfig().getDouble("Db." + key));
            }
        }
    }
}
