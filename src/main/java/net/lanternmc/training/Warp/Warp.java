package net.lanternmc.training.Warp;

import net.lanternmc.training.Training;
import net.lanternstudio.toolsapi.CommandRegister.AbstractCommand;
import net.lanternstudio.toolsapi.CommandRegister.CommandResult;
import net.lanternstudio.toolsapi.CommandRegister.PermissionWrapper;
import net.lanternstudio.toolsapi.CommandRegister.TabResult;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;

public class Warp extends AbstractCommand {
    public Warp() {
        super("warp");
    }

    @Override
    public @NotNull CommandResult onCommand(@NotNull Player player, @NotNull String[] strings) {
        if (strings.length >= 1) {
            if (Training.getInstance().getConf().getString("warps." + strings[0]) == null) {
                return CommandResult.from(CommandResult.Type.SUCCESS, "§f[§8地标管理§f]§c没有这个地标");
            }
            String[] str = Training.getInstance().getConf().getString("warps." + strings[0]).split(":");
            Location local = new Location(Bukkit.getWorld(str[0]),
                    Double.parseDouble(str[1]),
                    Double.parseDouble(str[2]),
                    Double.parseDouble(str[3]),
                    Float.parseFloat(str[4]),
                    Float.parseFloat(str[5]));
            player.teleport(local);
            return CommandResult.from(CommandResult.Type.SUCCESS, "§f[§8地标管理§f]§e已将你传送到§6" + strings[0]);
        } else {
            return CommandResult.from(CommandResult.Type.SUCCESS, "§f[§8地标管理§f]§c指令错误");
        }
    }

    @Override
    public @NotNull CommandResult onConsoleCommand(@NotNull ConsoleCommandSender consoleCommandSender, @NotNull String[] strings) {
        return CommandResult.SUCCESS;
    }

    @Override
    public @NotNull TabResult onTab(@NotNull Player player, @NotNull String[] strings) {
        ConfigurationSection conf = Training.getInstance().getConf().getConfigurationSection("warps");
        if (conf == null) return TabResult.empty();
        Collection<String> set = conf.getKeys(true);
        return TabResult.of(strings[0], set);
    }

    @Override
    public @NotNull TabResult onConsoleTab(@NotNull ConsoleCommandSender consoleCommandSender, @NotNull String[] strings) {
        return TabResult.empty();
    }

    @Override
    public @NotNull PermissionWrapper getPermission() {
        return PermissionWrapper.of("group.default");
    }

    @Override
    public @NotNull String getSyntax() {
        return "/warp <名字>";
    }

    @Override
    public @NotNull String getDescription() {
        return "传送到指定地标";
    }
}
