package me.regadpole.bwaddon;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BoundingBox;
import org.screamingsandals.bedwars.api.game.ItemSpawner;

import java.util.ArrayList;
import java.util.List;

public class BwUtils {
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
