package net.lanternmc.training.listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.util.Vector;

public class AntiExplosion implements Listener {

    @EventHandler
    public void onExplosionPrimeEvent(ExplosionPrimeEvent e) {
        e.getEntity().setVelocity(new Vector(0, 2, 0));
        e.setRadius(e.getRadius());
        e.setCancelled(true);
    }

    @EventHandler
    public void onEntityExplodeEvent(EntityExplodeEvent e) {
        e.getEntity().setVelocity(new Vector(0, 2, 0));
        e.setYield(3.0F);
        e.setCancelled(true);
    }

    @EventHandler
    public void onTNTDamage(EntityDamageByEntityEvent e) {
        Entity damager = e.getDamager();
        if (damager.hasMetadata("TNTLaunch")) {
            Entity entity = e.getEntity();
            if (entity instanceof Player) {
                if (damager instanceof TNTPrimed) {
                    e.setDamage(3);
                    entity.setVelocity(entity.getLocation().toVector().clone().subtract(damager.getLocation().toVector()).setY(1));
                    ((Player) entity).setLastDamage(0);
                }
            }
        }
    }
}
