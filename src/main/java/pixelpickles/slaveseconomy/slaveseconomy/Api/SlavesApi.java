package pixelpickles.slaveseconomy.slaveseconomy.Api;

import org.bukkit.entity.Player;
import pixelpickles.slaveseconomy.slaveseconomy.SlavesEconomy;

import static pixelpickles.slaveseconomy.slaveseconomy.Utils.Util.color;

public class SlavesApi {
    private SlavesEconomy plugin = SlavesEconomy.plugin;
    public void slavesGive(Player player, Integer amount) {
        plugin.newEconomyImplementer.depositPlayer(player, Double.valueOf(amount));
        player.sendMessage(color("&f(&e&l!&f) Вам выдали &e%amount% &fрабов".replace("%amount%", String.valueOf(amount))));
    }
    public Integer slavesGetBalance(Player player) {
        return (int) plugin.newEconomyImplementer.getBalance(player);
    }
    public void slavesTake(Player player, Integer amount) {
        player.sendMessage(color("&f(&e&l!&f) У вас забрали &e%amount% &fраба".replace("%amount%", String.valueOf(amount))));
        plugin.newEconomyImplementer.withdrawPlayer(player, Double.valueOf(amount));
    }
}
