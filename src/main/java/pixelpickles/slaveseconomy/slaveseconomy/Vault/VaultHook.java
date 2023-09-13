package pixelpickles.slaveseconomy.slaveseconomy.Vault;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import pixelpickles.slaveseconomy.slaveseconomy.SlavesEconomy;

import static pixelpickles.slaveseconomy.slaveseconomy.Utils.Util.color;

public class VaultHook {
    private SlavesEconomy plugin = SlavesEconomy.plugin;
    private Economy provider;

    public void hook() {
        provider = plugin.newEconomyImplementer;
        Bukkit.getServicesManager().register(Economy.class, this.provider, this.plugin, ServicePriority.Normal);
        Bukkit.getConsoleSender().sendMessage(color("&aVaultAPI hooked into "+plugin.getName()));
    }

    public void unhook(){
        Bukkit.getServicesManager().unregister(Economy.class, this.provider);
        Bukkit.getConsoleSender().sendMessage(color("&aVaultAPI un inhooked from "+plugin.getName()));
    }
}
