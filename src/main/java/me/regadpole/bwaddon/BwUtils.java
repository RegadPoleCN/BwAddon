package me.regadpole.bwaddon;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BoundingBox;
import org.screamingsandals.bedwars.api.game.ItemSpawner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BwUtils {
    public static final List<Material> unbreakableEqu = Arrays.asList(Material.MACE, Material.SHEARS,
            Material.LEATHER_BOOTS, Material.LEATHER_CHESTPLATE, Material.LEATHER_HELMET, Material.LEATHER_LEGGINGS,
            Material.CHAINMAIL_BOOTS, Material.CHAINMAIL_CHESTPLATE, Material.CHAINMAIL_HELMET, Material.CHAINMAIL_LEGGINGS,
            Material.IRON_BOOTS, Material.IRON_CHESTPLATE, Material.IRON_HELMET, Material.IRON_LEGGINGS,
            Material.DIAMOND_BOOTS, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_HELMET, Material.DIAMOND_LEGGINGS,
            Material.WOODEN_PICKAXE, Material.STONE_PICKAXE, Material.IRON_PICKAXE, Material.DIAMOND_PICKAXE, Material.NETHERITE_PICKAXE,
            Material.WOODEN_AXE, Material.STONE_AXE, Material.IRON_AXE, Material.DIAMOND_AXE, Material.NETHERITE_AXE,
            Material.WOODEN_SWORD, Material.STONE_SWORD, Material.IRON_SWORD, Material.DIAMOND_SWORD);

    public static ItemStack getResourceItem(String item) {
        return BwAddon.api.getItemSpawnerTypeByName(item).getStack();
    }

    public static List<ItemStack> getResourceHome(Player player) {
        final var game = BwAddon.api.getGameOfPlayer(player);
        List<ItemStack> stack = new ArrayList<>();
        game.getItemSpawners().forEach(spawner -> {
            var spawnerName = spawner.getItemSpawnerType().getName();
            if (spawnerName.equals("铁") || spawnerName.equals("金")) {
                stack.add(spawner.getItemSpawnerType().getStack());
            }
        });
        return stack;
    }

    public static List<BoundingBox> getSpawnerBoundingBoxes(Player player) {
        final var game = BwAddon.api.getGameOfPlayer(player);
        List<BoundingBox> boundingBoxes = new ArrayList<>();
        List<ItemSpawner> itemSpawners = new ArrayList<>();
        game.getItemSpawners().forEach(spawner -> {
            var spawnerName = spawner.getItemSpawnerType().getName();
            if (spawnerName.equals("铁") || spawnerName.equals("金")) {
                itemSpawners.add(spawner);
            }
        });
        List<Location> locations = new ArrayList<>();
        itemSpawners.forEach(spawner -> locations.add(spawner.getLocation()));
        locations.forEach(location -> boundingBoxes.add(BoundingBox.of(location, 1.5, 1.5, 1.5)));
        return boundingBoxes;
    }
}
