package net.lanternmc.training.api;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface BlockSkinProvider {
    ItemStack provide(Player player);
}
