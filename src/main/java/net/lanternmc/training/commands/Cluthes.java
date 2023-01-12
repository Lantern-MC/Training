package net.lanternmc.training.commands;

import net.lanternmc.training.Training;
import net.lanternmc.training.utils.TitleUtils;
import net.lanternstudio.toolsapi.CommandRegister.AbstractCommand;
import net.lanternstudio.toolsapi.CommandRegister.CommandResult;
import net.lanternstudio.toolsapi.CommandRegister.PermissionWrapper;
import net.lanternstudio.toolsapi.CommandRegister.TabResult;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Cluthes extends AbstractCommand {
    public Cluthes() {
        super("Cluthes");
    }

    @Override
    public CommandResult onCommand(Player p, String[] args) {
        long id;
        if(args.length >= 3) {
            id = Long.parseLong(args[2]);
        } else {
            id = 20L;
        }
        TitleUtils.sendTitle(p,  id/20 +"§c秒开始击退", "", 0,1,0);
        new BukkitRunnable() {

            @Override
            public void run() {
                new BukkitRunnable() {

                    int i = 3;
                    @Override
                    public void run() {
                        if (i == 0) {
                            cancel();
                        }
                        TitleUtils.sendTitle(p, "§c开始击退", "", 0,1,0);
                        p.damage(0);
                        p.setVelocity(new Vector(0, Double.parseDouble(args[0]), Double.parseDouble(args[1])));
                        i--;
                    }
                }.runTaskTimer(Training.getInstance(), 20L, 20L);
            }
        }.runTaskLater(Training.getInstance(), id);
        return CommandResult.from(CommandResult.Type.SUCCESS, "" + args.length + "L：" + "水平:" + args[0] + "  " + "垂直:" + args[1]);
    }

    @Override
    public CommandResult onConsoleCommand(ConsoleCommandSender consoleCommandSender, String[] strings) {
        return CommandResult.SUCCESS;
    }

    @Override
    public TabResult onTab(Player player, String[] strings) {
        return TabResult.EMPTY_RESULT;
    }

    @Override
    public TabResult onConsoleTab(ConsoleCommandSender consoleCommandSender, String[] strings) {
        return TabResult.EMPTY_RESULT;
    }

    @Override
    public PermissionWrapper getPermission() {
        return PermissionWrapper.of("group.default");
    }

    @Override
    public String getSyntax() {
        return "null";
    }

    @Override
    public String getDescription() {
        return "null";
    }
}
