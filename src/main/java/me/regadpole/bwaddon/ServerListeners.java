package me.regadpole.bwaddon;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BoundingBox;
import org.screamingsandals.bedwars.api.events.BedwarsGameEndEvent;
import org.screamingsandals.bedwars.api.events.BedwarsGameStartEvent;
import org.screamingsandals.bedwars.api.events.BedwarsPlayerKilledEvent;

import java.util.ArrayList;

public class ServerListeners implements Listener {

    @EventHandler
    public void onGameStart(BedwarsGameStartEvent event) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Configurator.Config.runCommandWhenGameStart);
        Bukkit.getConsoleSender().sendMessage("Run command "+Configurator.Config.runCommandWhenGameStart+" in game "+event.getGame().getName()+" when game start.");
    }

    @EventHandler
    public void onGameEnd(BedwarsGameEndEvent event) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Configurator.Config.runCommandWhenGameEnd);
        Bukkit.getConsoleSender().sendMessage("Run command "+Configurator.Config.runCommandWhenGameEnd+" in game "+event.getGame().getName()+" when game end.");
    }

    @EventHandler
    public void onPlayerKillInGame(BedwarsPlayerKilledEvent event) {
        Player killer = event.getKiller();
        if (killer != null){
            killer.getInventory().addItem(BwUtils.getResourceItem(Configurator.Config.giveResourceWhenPlayerKillInGame));
            Bukkit.getConsoleSender().sendMessage("Give killer "+killer.getName()+"a"+Configurator.Config.giveResourceWhenPlayerKillInGame+" in game "+event.getGame().getName()+".");
        }
    }

    @EventHandler
    public void onPlayerPickRes(EntityPickupItemEvent event) {
        if (!Configurator.Config.resShare) return;
        boolean isRes = false;
        Entity entity = event.getEntity();
        if (!(entity instanceof Player player)) {
            return;
        }
        if (!BwAddon.api.isPlayerPlayingAnyGame(player)) return;

        ItemStack itemStack = event.getItem().getItemStack();
        int pickAmount = itemStack.getAmount();
        double giveAmount = pickAmount*Configurator.Config.giveAnotherPlayerWhenPickRes;
        itemStack.setAmount((int) Math.round(giveAmount));

        for (ItemStack stack : BwUtils.getResourceHome(player)) {
            if (stack.isSimilar(itemStack)) isRes = true;
            if (isRes) break;
        }
        if (!isRes) return;

        var playerLocation = player.getLocation();
        var target = new ArrayList<Player>();

        var boundingBoxes = BwUtils.getSpawnerBoundingBoxes(player);
        for (BoundingBox boundingBox : boundingBoxes) {
            if(boundingBox.contains(playerLocation.getX(), playerLocation.getY(), playerLocation.getZ())) {
                var entities = player.getWorld().getNearbyEntities(boundingBox);
                for (Entity entityNearBy : entities) {
                    if (entityNearBy instanceof Player playerNearBy) {
                        target.add(playerNearBy);
                    }
                }
            }
        }
        target.forEach(targetP -> targetP.getInventory().addItem(itemStack));
    }

    @EventHandler
    public void onInventory(InventoryEvent event) {
        final var inv = event.getInventory();
        if (!(inv.getHolder() instanceof Player player)) return;
        if (!BwAddon.api.isPlayerPlayingAnyGame(player)) return;

        var armorCon = player.getInventory().getArmorContents();
        for (ItemStack content : armorCon) {
            if (!content.getItemMeta().isUnbreakable()) content.getItemMeta().setUnbreakable(true);
        }
        player.getInventory().setArmorContents(armorCon);
        for (Material material : BwUtils.unbreakableEqu) {
            var contents = player.getInventory().all(material);
            contents.forEach((index, item) -> {
                if (!item.getItemMeta().isUnbreakable()) item.getItemMeta().setUnbreakable(true);
                player.getInventory().setItem(index, item);
            });
        }
    }
}
