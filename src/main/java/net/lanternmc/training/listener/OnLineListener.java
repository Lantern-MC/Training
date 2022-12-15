package net.lanternmc.training.listener;

import net.lanternmc.training.Counter;
import net.lanternmc.training.LocationManager;
import net.lanternmc.training.Training;
import net.lanternmc.training.manager.Default;
import net.lanternmc.training.utils.TitleUtils;
import net.lanternmc.training.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class OnLineListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (e.getPlayer().hasPermission("Training.noclear")) return;
        Training.teleportCheckPoint(e.getPlayer());
        createRunable(e.getPlayer());
    }

    private void createRunable(Player p) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if(Training.getInstance().getConf().get("world") != null) {
                    LocationManager.FirstJoin(p);
                } else {
                    Location checkPoint = new Location(Bukkit.getWorld("Trainworld"),
                            0,
                            500,
                            0,
                            (float) 0,
                            (float) 0).add(0.5, 1, 0.5);
                }
                p.sendMessage(new String[]{
                        "§b§l高级训练场 §7>> §e输入 §6/bridge §e更改练习参数",
                        "§b§l高级训练场 §7>> §e踩在 §a绿宝石块 §e上可以设置传送点",
                        "§b§l高级训练场 §7>> §e踩在 §c红石块 §e上可以回到传送点",
                        "§b§l高级训练场 §7>> §e踩在 §b青金石块 §e上可以回到出生点",
                        "§b§l高级训练场 §7>> §e踩在 §b钻石块 §e上可以开始射击练习",
                        "§b§l高级训练场 §7>> §e§n踩在 §b屎 §e上可以做老八",
                        "§b§l高级训练场 §7>> §6Bilibili @SakuraKooi",
                        "§b§l高级训练场 §7>> §6Bilibili @MineCraft小玄易  " +
                                "(LanternMC 集成训练场 开发者 嘘来自品牌“AiXCoder” 上取来的借鉴源码)"
                });
                p.getInventory().setItem(8, Default.getMenu());
            }
        }.runTaskLater(Training.getInstance(), 10);
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent e) {
        if (e.getPlayer().hasPermission("Training.noclear")) return;
        if (e.getItemDrop().getItemStack().getType() == Material.GOLD_PICKAXE) {
            e.getItemDrop().remove();
        }
    }

    @EventHandler
    public void onPvP(EntityDamageByEntityEvent e) {
        if (e.isCancelled()) return;
        if (e.getEntity() == null) return;
        if (e.getDamager() == null) return;
        if (e.getEntity().getType() == EntityType.PLAYER) if (e.getDamager().getType() == EntityType.PLAYER) {
            int state = onPvPDamage((Player) e.getEntity(), (Player) e.getDamager());
            if (state == -1) {
                e.setCancelled(true);
            } else if (state == 1) {
                e.setCancelled(true);
                Training.getCounter((Player) e.getDamager()).setPvPEnabled(true);
                TitleUtils.sendTitle((Player) e.getDamager(), "", "§c注意: §aPvP已开启", 10, 20, 10);
                ((Player) e.getEntity()).damage(0.00);
                ((Player) e.getEntity()).setNoDamageTicks(60);
                ((Player) e.getDamager()).setNoDamageTicks(60);
            }
        } else if (e.getDamager() instanceof Projectile) {
            Projectile proj = (Projectile) e.getDamager();
            if (proj.getShooter() instanceof Player) {
                int state = onPvPDamage((Player) e.getEntity(), (Player) proj.getShooter());
                if (state == -1) {
                    e.setCancelled(true);
                } else if (state == 1) {
                    e.setCancelled(true);
                    Training.getCounter((Player) proj.getShooter()).setPvPEnabled(true);
                    TitleUtils.sendTitle((Player) proj.getShooter(), "", "§c注意: §aPvP已开启", 10, 20, 10);
                    ((Player) e.getEntity()).damage(0.00);
                    ((Player) e.getEntity()).setNoDamageTicks(60);
                    ((Player) proj.getShooter()).setNoDamageTicks(60);
                }
            }
        }
    }

    private int onPvPDamage(Player player, Player damager) {
        if (!Training.getCounter(player).isPvPEnabled()) return -1; // cancel
        if (!Training.getCounter(damager).isPvPEnabled()) return 1; // enable
        return 0; // accept
    }





    @EventHandler
    public void antiArmorStandManipulate(PlayerArmorStandManipulateEvent e) {
        e.setCancelled(true);
        if (e.getPlayer().getGameMode() == GameMode.CREATIVE && e.getPlayer().isOp())
            if (e.getRightClicked().getCustomName().contains("VillagerSpawnPoint")) {
                e.getRightClicked().remove();
                TitleUtils.sendTitle(e.getPlayer(), "", "§a村民刷新点已移除", 10, 20, 10);
            }
    }

    @EventHandler
    public void disableVillagerShop(PlayerInteractEntityEvent e) {
        if (e.getRightClicked().getType() == EntityType.VILLAGER) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void disableWeather(WeatherChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void logoutBreak(PlayerQuitEvent e) {
        Training.getInstance().getCounter(e.getPlayer()).instantBreakBlock();
        Bukkit.getConsoleSender().sendMessage("§bTraining §7>> §a玩家 " + e.getPlayer().getName() + " 离线, 已清除其放置的方块.");
    }

    @EventHandler
    public void noHunger(FoodLevelChangeEvent e) {
        e.setFoodLevel(20);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity().getType() == EntityType.PLAYER) {
            Counter c = Training.getCounter((Player) e.getEntity());
            if (e.getFinalDamage() > 20) {
                c.reset();
                Training.getInstance().teleportCheckPoint((Player) e.getEntity());
                TitleUtils.sendTitle((Player) e.getEntity(), "",
                        "§4致命伤害 - " + Utils.formatDouble(e.getFinalDamage() / 2) + " ❤", 10, 20, 10);
                e.setDamage(0.0);
            } else if (e.getFinalDamage() > 10) {
                TitleUtils.sendTitle((Player) e.getEntity(), "",
                        "§c严重伤害 - " + Utils.formatDouble(e.getFinalDamage() / 2) + " ❤", 10, 20, 10);
            }
            e.setDamage(0.0);
        }
    }



}
