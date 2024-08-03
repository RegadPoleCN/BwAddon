package me.regadpole.bwaddon;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.Collections;
import java.util.List;

public class BwCommands implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§b§l[BwAddon]§r"+"§c请使用/pb help查看命令使用方法");
            return true;
        }
        if (args[0].equals("reload")) {
            Configurator.loadConfig();
            sender.sendMessage("§b§l[BwAddon]§r" + "§aPlugin reloaded");
        } else {
            if (args.length != 1) return true;
            sender.sendMessage("§b§l[BwAddon]§r" + "§c错误的指令用法，请使用/pb help查看命令使用方法");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return Collections.emptyList();
    }
}
