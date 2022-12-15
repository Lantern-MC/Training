package net.lanternmc.training;

import org.bukkit.SandstoneType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Sandstone;
import net.lanternmc.training.api.BlockSkinProvider;

public class DefaultBlockSkinProvider implements BlockSkinProvider {
    @Override
    public ItemStack provide(Player player) {
        return new Sandstone(SandstoneType.SMOOTH).toItemStack(64);
    }
}
