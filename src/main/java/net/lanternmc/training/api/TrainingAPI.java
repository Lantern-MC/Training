package net.lanternmc.training.api;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import net.lanternmc.training.Training;

public class TrainingAPI {
    public static void setBlockSkinProvider(BlockSkinProvider blockSkinProvider) {
        Training.setBlockSkinProvider(blockSkinProvider);
    }

    public static void clearEffect(Player player) {
        Training.clearEffect(player);
    }

    public static void clearInventory(Player player) {
        Training.clearInventory(player);
    }

    public static void respawnVillager() {
        Training.spawnVillager();
    }

    public static boolean isPlacedByPlayer(Block block) {
        return Training.isPlacedByPlayer(block);
    }

    public static void teleportCheckPoint(Player player) {
        Training.teleportCheckPoint(player);
    }

    public static void refreshItem(Player p) {
        Training.refreshItem(p);
    }

    public static void setPlayerHighlightEnabled(Player player, boolean enabled) {
        Training.getCounter(player).setHighlightEnabled(enabled);
    }

    public static void setPlayerPvPEnabled(Player player, boolean enabled) {
        Training.getCounter(player).setPvPEnabled(enabled);
    }

    public static void setPlayerSpeedDisplayEnabled(Player player, boolean enabled) {
        Training.getCounter(player).setSpeedCountEnabled(enabled);
    }

    public static void setPlayerStandBridgeMarkerEnabled(Player player, boolean enabled) {
        Training.getCounter(player).setStandBridgeMarkerEnabled(enabled);
    }

    public static boolean isPlayerHighlightEnabled(Player player) {
        return Training.getCounter(player).isHighlightEnabled();
    }

    public static boolean isPlayerPvPEnabled(Player player) {
        return Training.getCounter(player).isPvPEnabled();
    }

    public static boolean isPlayerSpeedDisplayEnabled(Player player) {
        return Training.getCounter(player).isSpeedCountEnabled();
    }

    public static boolean isPlayerStandBridgeMarkerEnabled(Player player) {
        return Training.getCounter(player).isStandBridgeMarkerEnabled();
    }
}
