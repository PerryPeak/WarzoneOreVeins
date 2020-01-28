package me.perry1900.warzoneoreveins;

import org.bukkit.*;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class EventListener implements Listener {
    @EventHandler
    public void BBE(BlockBreakEvent event) throws IOException {
        Block block = event.getBlock();
        Player player = event.getPlayer();
        List<Location> Locations = (List<Location>) WarzoneOreVeins.instance.getCustomConfig().getList("Locations");
        if (Locations != null && Locations.contains(block.getLocation())) {
            if (player.getGameMode() == GameMode.CREATIVE) {
                Locations.remove(block.getLocation());
                WarzoneOreVeins.instance.getCustomConfig().set("Locations", Locations);
                WarzoneOreVeins.instance.getCustomConfig().save(WarzoneOreVeins.instance.getCustomConfigFile());
            } else {
                try {
                    event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), WarzoneOreVeins.instance.getConfig().getItemStack("BlockDrops." + event.getBlock().getType()));
                    event.getBlock().setType(Material.AIR);
                } catch (IllegalArgumentException e) {

                }
                HashMap<String, Object> blockList = new HashMap<String, Object>(WarzoneOreVeins.instance.getConfig().getConfigurationSection("BlockChances").getValues(false));
                BlockRegenerator.blockRegen.blockRegenerator(blockList, block.getLocation());
            }
        }
    }

    @EventHandler
    public void BPE(BlockPlaceEvent event) throws IOException {
        Block block = event.getBlockPlaced();
        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE) {
            ItemStack blockItem = event.getItemInHand();
            if (blockItem!=null && blockItem.getItemMeta().getDisplayName().equals(ChatColor.BLUE + "Ore Vein Block")) {
                org.bukkit.Location coords = block.getLocation();
                if (WarzoneOreVeins.instance.getCustomConfig().getList("Locations") == null) {
                    List<Location> Locations = new ArrayList<>();
                    Locations.add(coords);
                    WarzoneOreVeins.instance.getCustomConfig().set("Locations", Locations);
                } else {
                    List<Location> Locations = (List<Location>) WarzoneOreVeins.instance.getCustomConfig().getList("Locations");
                    Locations.add(coords);
                    WarzoneOreVeins.instance.getCustomConfig().set("Locations", Locations);
                }
                WarzoneOreVeins.instance.getCustomConfig().save(WarzoneOreVeins.instance.getCustomConfigFile());
            }
        }
    }

}
