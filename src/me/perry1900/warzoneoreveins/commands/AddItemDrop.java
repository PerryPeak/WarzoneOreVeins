package me.perry1900.warzoneoreveins.commands;

import me.perry1900.warzoneoreveins.WarzoneOreVeins;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddItemDrop implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command is only for players!");
            return true;
        }
        Player p = (Player) sender;
        if (p.getItemInHand() == null || p.getItemInHand().getType() == Material.AIR) {
            p.sendMessage("You must hold the item you want to set as a drop when running the command"); //TODO: allow changing it to air
            return true;
        }
        if (!p.hasPermission("WarzoneOreVeins.additemdrop")) {
            p.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }
        try {
            Material block = Material.getMaterial(args[0]);
            if (block == null) {
                p.sendMessage("Invalid block name");
                return true;
            }
            if (block.isSolid()) {
                WarzoneOreVeins.instance.getConfig().set("BlockDrops." + args[0], p.getItemInHand());
                WarzoneOreVeins.instance.saveConfig();
                p.sendMessage("Successfully set " + args[0] + "'s drop to " + p.getItemInHand().getItemMeta().getDisplayName());
                return true;
            }
            p.sendMessage("You can only use solid blocks");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}
