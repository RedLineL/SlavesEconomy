package pixelpickles.slaveseconomy.slaveseconomy.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pixelpickles.slaveseconomy.slaveseconomy.SlavesEconomy;

public class JoinEvent implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        SlavesEconomy plugin = SlavesEconomy.plugin;
        if (!plugin.playerBank.containsKey(e.getPlayer().getUniqueId())) {
            plugin.playerBank.put((e.getPlayer()).getUniqueId(), (double) 0);
        }
    }
}
