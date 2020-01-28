package me.perry1900.warzoneoreveins.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Giveoreveinblock implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command is only for players!");
            return true;
        }

        Player p = (Player) sender;
        if (p.hasPermission("WarzoneOreVeins.GiveOreVeinBlock") == false) {
            p.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }

        short OreVeinBlockDurability = 0;
        ItemStack OreVeinBlock = new ItemStack(Material.COAL_ORE, 1, OreVeinBlockDurability);
        OreVeinBlock.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        ItemMeta OreVeinBlockMeta = OreVeinBlock.getItemMeta();
        OreVeinBlockMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        OreVeinBlockMeta.setDisplayName(ChatColor.BLUE + "Ore Vein Block");
        OreVeinBlock.setItemMeta(OreVeinBlockMeta);

        if (p.getInventory().firstEmpty() != -1) {
            p.getInventory().addItem(OreVeinBlock);
            p.sendMessage(ChatColor.GREEN + "You successfully gave yourself an ore vein block");
            return true;
        }

        if (p.getInventory().contains(OreVeinBlock)) {
            p.getInventory().addItem(OreVeinBlock);
            p.sendMessage(ChatColor.GREEN + "You successfully gave yourself an ore vein block");
            return true;
        }

        return false;

    }
}
