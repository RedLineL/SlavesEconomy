package pixelpickles.slaveseconomy.slaveseconomy.Papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import pixelpickles.slaveseconomy.slaveseconomy.SlavesEconomy;

public class SpigotExpansion extends PlaceholderExpansion {
    private SlavesEconomy plugin = SlavesEconomy.plugin;

    @Override
    public String getIdentifier() {
        return "SlavesEconomy";
    }

    @Override
    public String getAuthor() {
        return "RedL1neL";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public  String onPlaceholderRequest(Player p, String params) {
        if (p==null) {
            return "";
        }
        if (params.equals("playerBalance")) {
            return String.valueOf((int) plugin.newEconomyImplementer.getBalance(p));
        }
        return null;
    }
}
