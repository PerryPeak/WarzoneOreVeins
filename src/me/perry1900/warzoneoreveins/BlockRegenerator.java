package me.perry1900.warzoneoreveins;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

public class BlockRegenerator {
    public static BlockRegenerator blockRegen = new BlockRegenerator();

    public void blockRegenerator(HashMap<String, Object> stringBlockList, Location coords) {
        HashMap<Material, Integer> blockList = new HashMap<Material, Integer>();
        for (Entry<String, Object> entry : stringBlockList.entrySet()) {
            String key = entry.getKey();
            Integer value = (int) entry.getValue();
            Material block = Material.getMaterial(key);
            int chance = value;
            blockList.put(block, chance);
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                coords.getBlock().setType(Material.BEDROCK);
            }
        }.runTaskLater(WarzoneOreVeins.instance, 1);
        new BukkitRunnable() {
            @Override
            public void run() {
                Material chosenBlock = randomBlockChooser(blockList);
                coords.getBlock().setType(chosenBlock);
            }
        }.runTaskLater(WarzoneOreVeins.instance, 30 * 20);

    }

    public Material randomBlockChooser(HashMap<Material, Integer> blockList) {
        //double randDouble = /*(int)*/Math.random();
        int randInt = (int) Math.round(Math.random() * 100);
        int lastValue = 0;
//        HashMap<Material, ArrayList<Double>> range = new HashMap<Material, ArrayList<Double>>(); //unused
//        ArrayList<Double> rangeList = new ArrayList<Double>(); // unused
        for (HashMap.Entry<Material, Integer> entry : blockList.entrySet()) {
            Material key = entry.getKey();
            int value = entry.getValue() + lastValue;
//            rangeList.add((double) lastValue + 1 / 100); //unused
//            rangeList.add((double) value / 100); //unused
//            range.put(key, rangeList); //unused
            if (lastValue <= randInt && value >= randInt/*(double) lastValue + 1 / 100 <= randDouble && (double) value / 100 >= randDouble*/) {
                return key;
            }
            lastValue = value;
//            rangeList.clear(); //unused
        }
        return Material.STONE;
    }

    public void blockRegeneratorInsant(HashMap<String, Object> stringBlockList, Location coords) {
        HashMap<Material, Integer> blockList = new HashMap<Material, Integer>();
        for (Entry<String, Object> entry : stringBlockList.entrySet()) {
            String key = entry.getKey();
            Integer value = (int) entry.getValue();
            Material block = Material.getMaterial(key);
            int chance = value;
            blockList.put(block, chance);
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                Material chosenBlock = randomBlockChooser(blockList);
                coords.getBlock().setType(chosenBlock);
            }
        }.runTaskLater(WarzoneOreVeins.instance, 1);
    }
}
