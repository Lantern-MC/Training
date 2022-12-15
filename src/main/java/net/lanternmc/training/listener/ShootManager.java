package net.lanternmc.training.listener;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class ShootManager implements Listener {

    @EventHandler
    public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent e) {
        Entity entity = e.getRightClicked();
        if (entity != null && entity.getCustomName() != null
                && entity.getCustomName().equals("移动靶")
                && e.getPlayer().isOp()
                && !(entity instanceof Player))
            entity.remove();
    }

    @EventHandler
    public void Shootby(EntityDamageByEntityEvent e) {
        String name = e.getEntity().getCustomName();
        if (name != null && name.equals("移动靶")) {
            e.setCancelled(true);
            if (e.getDamager() instanceof Arrow) {
                Arrow arrow = (Arrow)e.getDamager();
                if (arrow.getShooter() instanceof Player) {
                    Player p = (Player)arrow.getShooter();
                    p.sendMessage("§a命中 +1");
                }
            }

        }

    }

}

