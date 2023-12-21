package me.matthewstevens.autoseed.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

import static me.matthewstevens.autoseed.AutoSeed.enabledPlayers;
import static me.matthewstevens.autoseed.AutoSeed.radius;

public class plantingListener implements Listener{
    private static final Set<Material> PLANTABLE_ITEMS = EnumSet.of(
            Material.WHEAT_SEEDS,
            Material.MELON_SEEDS,
            Material.PUMPKIN_SEEDS,
            Material.BEETROOT_SEEDS,
            Material.POTATO,
            Material.CARROT
            // Add any other plantable items here
    );

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        String playerName = ChatColor.stripColor(player.getDisplayName());
        if (player.isSneaking() && event.getAction() == Action.RIGHT_CLICK_BLOCK && enabledPlayers.contains(playerName)) {
            ItemStack item = event.getItem();

            if (item != null && PLANTABLE_ITEMS.contains(item.getType())) {
                Material blockType = event.getClickedBlock().getType();
                if (blockType == Material.FARMLAND) {
                    List<Location> tilledLand = farmlandFinder(player, radius);
                    int seedsAvailable = countItemsInInventory(player.getInventory(), item.getType());
                    int seedsPlanted = 0;

                    for (Location loc : tilledLand) {
                        if (seedsPlanted >= seedsAvailable) break;
                        Block block = loc.getBlock();
                        if (block.getType() == Material.FARMLAND && block.getRelative(0, 1, 0).getType() == Material.AIR) {
                            Material cropType = getCropTypeForSeed(item.getType());
                            if (cropType != null) {
                                block.getRelative(0, 1, 0).setType(cropType);
                                seedsPlanted++;
                            }
                        }
                    }

                    if (seedsPlanted > 0) {
                        removeItemsFromInventory(player.getInventory(), item.getType(), seedsPlanted);
                        player.sendMessage("Planted " + seedsPlanted + " seeds.");
                    }
                }
            }
        }
    }

    private Material getCropTypeForSeed(Material seedType) {
        switch (seedType) {
            case WHEAT_SEEDS:
                return Material.WHEAT;
            case MELON_SEEDS:
                return Material.MELON_STEM;
            case PUMPKIN_SEEDS:
                return Material.PUMPKIN_STEM;
            case BEETROOT_SEEDS:
                return Material.BEETROOTS;
            case POTATO:
                return Material.POTATOES;
            case CARROT:
                return Material.CARROTS;
            // Add cases for any other seeds you have
            default:
                return null;
        }
    }

    private int countItemsInInventory(org.bukkit.inventory.Inventory inventory, Material itemType) {
        int count = 0;
        for (ItemStack stack : inventory.getContents()) {
            if (stack != null && stack.getType() == itemType) {
                count += stack.getAmount();
            }
        }
        return count;
    }

    private void removeItemsFromInventory(org.bukkit.inventory.Inventory inventory, Material itemType, int amountToRemove) {
        for (ItemStack stack : inventory.getContents()) {
            if (stack != null && stack.getType() == itemType) {
                int amountInStack = stack.getAmount();
                if (amountInStack > amountToRemove) {
                    stack.setAmount(amountInStack - amountToRemove);
                    break;
                } else {
                    inventory.remove(stack);
                    amountToRemove -= amountInStack;
                    if (amountToRemove <= 0) break;
                }
            }
        }
    }

    public List<Location> farmlandFinder(Player player, int radius) {
        List<Location> tilledLandLocations = new ArrayList<>();
        World world = player.getWorld();
        Location center = player.getLocation();

        int startX = center.getBlockX() - radius;
        int startY = center.getBlockY() - radius; // Y value can be adjusted based on how vertical you want the search to be
        int startZ = center.getBlockZ() - radius;

        int endX = center.getBlockX() + radius;
        int endY = center.getBlockY() + radius;
        int endZ = center.getBlockZ() + radius;

        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                for (int z = startZ; z <= endZ; z++) {
                    Block block = world.getBlockAt(x, y, z);
                    if (block.getType() == Material.FARMLAND) {
                        tilledLandLocations.add(block.getLocation());
                    }
                }
            }
        }

        return tilledLandLocations;
    }
}