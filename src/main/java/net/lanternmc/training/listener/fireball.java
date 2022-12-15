package net.lanternmc.training.listener;

import net.lanternmc.training.Training;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class fireball implements Listener {
    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if ((e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
                && e.getItem().getType() == (new ItemStack(Material.FIREBALL)).getType()) {
            ItemStack fee = new ItemStack(Material.FIREBALL);
            Fireball fireball = player.launchProjectile(Fireball.class);
            fireball.setYield(1.5F);
            fireball.setBounce(false);
            fireball.setShooter(player);
            e.setCancelled(true);
            fee.setAmount(1);
            if (player.getInventory().getItemInHand().isSimilar(fee))
                player.getInventory().removeItem(fee);
        }
    }




    @EventHandler
    public void onClick(BlockPlaceEvent e) {
        if (e.getBlock().getType().equals(Material.TNT)) {

            e.setCancelled(true);
            e.getBlock().setType(Material.AIR);

            Location location = e.getBlock().getLocation().clone();
            TNTPrimed tnt = location.getWorld().spawn(location.clone().add(0.0D, 1.0D, 0.0D), TNTPrimed.class);
            tnt.setYield(3.0F);
            tnt.setIsIncendiary(false);
            tnt.setFuseTicks(60);
            tnt.setMetadata("TNTLaunch", new FixedMetadataValue(Training.getInstance(), e.getPlayer().getName()));
            tnt.setCustomName(e.getPlayer().getName());
        }
    }

}
