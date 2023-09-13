package pixelpickles.slaveseconomy.slaveseconomy.commands;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pixelpickles.slaveseconomy.slaveseconomy.SlavesEconomy;
import pixelpickles.slaveseconomy.slaveseconomy.Vault.EconomyImplementer;

import static pixelpickles.slaveseconomy.slaveseconomy.Utils.Util.color;

public class SlaveCommand implements CommandExecutor {

    public static boolean isInt(String s, CommandSender p) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    private SlavesEconomy plugin = SlavesEconomy.plugin;
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!plugin.playerBank.containsKey(((Player) sender).getUniqueId())) {
            plugin.playerBank.put(((Player) sender).getUniqueId(), (double) 0);
        }
        if (args.length >= 3) {
            if (args[0].equals("take")) {
                Player target = Bukkit.getPlayer(args[1]);

                if (!sender.hasPermission("Slaves.Take")) return true;

                if (target == null) {
                    sender.sendMessage(color("&f(&e&l!&f) Вы должны указать игрока который на сервере!"));
                    return true;
                }

                if (isInt(args[2], sender)) {
                    Integer slavesAmount = Integer.parseInt(args[2]);
                    plugin.newEconomyImplementer.withdrawPlayer(target, slavesAmount);
                    sender.sendMessage(color("&f(&e&l!&f) Вы взяли &e%amount% &fраба у игрока &e%player%").replace("%amount%", String.valueOf(slavesAmount)).replace("%player%", target.getName()));
                    return false;
                } else {
                    sender.sendMessage(color("&f(&e&l!&f) Вы не указали целое число!"));
                }
            } else if (args[0].equals("give")) {
                Player target = Bukkit.getPlayer(args[1]);

                if (!sender.hasPermission("Slaves.Give")) return true;

                if (target == null) {
                    sender.sendMessage(color("&f(&e&l!&f) Вы должны указать игрока который на сервере!"));
                    return true;
                }

                if (isInt(args[2], sender)) {
                    Integer slavesAmount = Integer.parseInt(args[2]);
                    plugin.newEconomyImplementer.depositPlayer(target, slavesAmount);
                    target.sendMessage(color("&f(&e&l!&f) Вам выдали &e%amount% &fрабов".replace("%amount%", String.valueOf(slavesAmount))));
                    sender.sendMessage(color("&f(&e&l!&f) Вы выдали &e%amount% &fраба игроку &e%player%").replace("%amount%", String.valueOf(slavesAmount)).replace("%player%", target.getName()));
                } else {
                    sender.sendMessage(color("&f(&e&l!&f) Вы не указали целое число!"));
                }
            } else if (args[0].equals("send")) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    sender.sendMessage(color("&f(&e&l!&f) Вы должны указать игрока который на сервере!"));
                    return true;
                }

                if (isInt(args[2], sender)) {
                    Integer slavesAmount = Integer.parseInt(args[2]);
                    if (slavesAmount.equals(0)) {
                        target.sendMessage(color("&f(&e&l!&f) Вы не можете отдавать нечего. Вы нормальные?"));
                        return true;
                    }

                    if (plugin.newEconomyImplementer.getBalance((Player) sender) >= slavesAmount) {
                        plugin.newEconomyImplementer.withdrawPlayer((Player) sender, slavesAmount);
                        plugin.newEconomyImplementer.depositPlayer(target, slavesAmount);
                        target.sendMessage(color("&f(&e&l!&f) Игрок %player% отдал вам &e%amount% &fраба".replace("%amount%", String.valueOf(slavesAmount)).replace("%player%", sender.getName())));
                        sender.sendMessage(color("&f(&e&l!&f) Вы отдали &e%amount% &fраба игроку &e%player%").replace("%amount%", String.valueOf(slavesAmount)).replace("%player%", target.getName()));
                        return false;
                    } else {
                        sender.sendMessage(color("&f(&e&l!&f) У вас недостаточно средств!"));
                        return true;
                    }
                } else {
                    sender.sendMessage(color("&f(&e&l!&f) Вы не указали целое число!"));
                }
            } else {
                sender.sendMessage(color("&f(&e&l!&f) Аргумент не найден"));
                return false;
            }
        } else if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (!sender.hasPermission("Slaves.PlayerBalance")) return true;

            if (target == null) {
                sender.sendMessage(color("&f(&e&l!&f) Вы должны указать игрока который на сервере!"));
                return true;
            }
            sender.sendMessage(color("&f(&e&l!&f) Баланс игрока %player%:&e "+Integer.valueOf((int) plugin.newEconomyImplementer.getBalance((target)))).replace("%player%", target.getName()));
        } else {
            if (!(sender instanceof Player)) return true;
            sender.sendMessage(color("&f(&e&l!&f) Баланс:&e "+Integer.valueOf((int) plugin.newEconomyImplementer.getBalance((Player) sender))));
            return false;
        }
        return false;
    }
}
