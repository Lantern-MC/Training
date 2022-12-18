package net.lanternmc.training.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.lanternmc.training.Training;
import net.lanternmc.training.Counter;
import net.lanternmc.training.utils.SendMessageUtils;

public class BridgeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§b§l搭路练习 §7>> §c此命令用于配置插件参数, 仅玩家可以执行.");
            return true;
        }
        if (args.length != 1) {
            SendMessageUtils.sendMessage(sender,
                    "§b§l搭路练习 §7>> §b§lTraining | By.SakuraKooi",
                    "§b§l搭路练习 §7>> §e/bridge highlight    §a启用/禁用侧搭辅助指示",
                    "§b§l搭路练习 §7>> §e/bridge pvp         §a启用/禁用伤害屏蔽",
                    "§b§l搭路练习 §7>> §e/bridge speed       §a启用/禁用搭路速度统计",
                    "§b§l搭路练习 §7>> §e/bridge stand       §a启用/禁用走搭位置指示",
                    "§b§l搭路练习 §7>> §d所配置的参数仅对您有效, 其他玩家不受影响",
                    "§b§l搭路练习 §7>> §bhttps://github.com/SakuraKoi/Training"
            );
            return true;
        }
        Counter counter = Training.getCounter((Player) sender);
        switch (args[0].toLowerCase()) {
            case "highlight": {
                counter.setHighlightEnabled(!counter.isHighlightEnabled());
                sender.sendMessage("§b§l搭路练习 §7>> §a侧搭辅助指示已" + (counter.isHighlightEnabled() ? "开启" : "关闭"));
                break;
            }
            case "pvp": {

                counter.setPvPEnabled(!counter.isPvPEnabled());
                sender.sendMessage("§b§l搭路练习 §7>> §aPvP已" + (counter.isPvPEnabled() ? "开启" : "关闭"));
                break;
            }
            case "speed": {
                counter.setSpeedCountEnabled(!counter.isSpeedCountEnabled());
                sender.sendMessage("§b§l搭路练习 §7>> §a搭路速度统计已" + (counter.isSpeedCountEnabled() ? "开启" : "关闭"));
                break;
            }
            case "setlobby": {
                if (sender.hasPermission("Training.admin")) {
                    Location loc = ((Player) sender).getLocation();
                    Training.getInstance().getConf().set("world", loc.getWorld().getName());
                    Training.getInstance().getConf().set("x", loc.getX());
                    Training.getInstance().getConf().set("y", loc.getY());
                    Training.getInstance().getConf().set("z", loc.getZ());
                    Training.getInstance().getConf().set("yaw", loc.getYaw());
                    Training.getInstance().getConf().set("pitch", loc.getPitch());
                    Training.getInstance().getConf().save();
                    sender.sendMessage("§b§l搭路练习 §7>> §aOk~");
                }
                break;
            }
            case "stand": {
                counter.setStandBridgeMarkerEnabled(!counter.isStandBridgeMarkerEnabled());
                sender.sendMessage("§b§l搭路练习 §7>> §a走搭位置指示已" + (counter.isStandBridgeMarkerEnabled() ? "开启" : "关闭"));
                break;
            }
            default: {
                sender.sendMessage("§b§l搭路练习 §7>> §a尝试切换的功能 " + args[0] + " 不存在");
                break;
            }
        }
        return true;
    }
}
