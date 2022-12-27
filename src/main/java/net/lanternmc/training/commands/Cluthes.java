package net.lanternmc.training.commands;

import net.lanternmc.training.Training;
import net.lanternstudio.toolsapi.CommandRegister.AbstractCommand;
import net.lanternstudio.toolsapi.CommandRegister.CommandResult;
import net.lanternstudio.toolsapi.CommandRegister.PermissionWrapper;
import net.lanternstudio.toolsapi.CommandRegister.TabResult;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.io.File;

public class Cluthes extends AbstractCommand {
    public Cluthes() {
        super("Cluthes");
    }

    @Override
    public CommandResult onCommand(Player p, String[] args) {
        if (args[0].equalsIgnoreCase("kb")) {
            int group = (args.length - 1) / 2;
            for (int ii = 1; ii <= group; ii++) {
                p.sendMessage("第" + ii + "组：" + "水平:" + args[ii * 2 - 1] + "  " + "垂直:" + args[ii * 2]);
            }
            new BukkitRunnable() {
                int i = 3;

                @Override
                public void run() {
                    i--;
                    p.sendMessage(i + "");
                    if (i <= 0) {
                        cancel();
                        int group = (args.length - 1) / 2;
                        for (int ii = 1; ii <= group; ii++) {
                            int finalIi = ii;
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    p.damage(0);
                                    p.setVelocity(new Vector(0, Double.parseDouble(args[finalIi * 2 - 1]), Double.parseDouble(args[finalIi * 2])));
                                }
                            }.runTaskLater(Training.getInstance(), 10L * (ii - 1));
                        }

                    }
                }
            }.runTaskTimer(Training.getInstance(), 0L, 20L);

        }
        return CommandResult.from(CommandResult.Type.SUCCESS, "");
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
