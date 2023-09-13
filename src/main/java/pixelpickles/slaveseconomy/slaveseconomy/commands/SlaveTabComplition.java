package pixelpickles.slaveseconomy.slaveseconomy.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import pixelpickles.slaveseconomy.slaveseconomy.SlavesEconomy;

import java.util.ArrayList;
import java.util.List;

public class SlaveTabComplition implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1){
            List<String> tabComp = new ArrayList<>();
            tabComp.add("take");
            tabComp.add("send");
            tabComp.add("give");
            for(Player p : Bukkit.getServer().getOnlinePlayers())
            {
                tabComp.add(p.getName());
            }
            return tabComp;
        }

        return null;
    }
}
