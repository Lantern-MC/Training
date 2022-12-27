package net.lanternmc.training.Warp;

import net.lanternmc.training.Training;
import net.lanternstudio.toolsapi.CommandRegister.AbstractCommand;
import net.lanternstudio.toolsapi.CommandRegister.CommandResult;
import net.lanternstudio.toolsapi.CommandRegister.PermissionWrapper;
import net.lanternstudio.toolsapi.CommandRegister.TabResult;
import org.bukkit.Location;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class setWarp extends AbstractCommand {
    public setWarp() {
        super("setwarp");
    }

    @Override
    public @NotNull CommandResult onCommand(@NotNull Player player, @NotNull String[] strings) {
        if (strings.length >= 1) {
            Location l = player.getLocation();
            String loc = l.getWorld().getName() + ":" + l.getX() + ":" + l.getY() + ":" + l.getZ() + ":" + l.getYaw()  + ":" + l.getPitch();
            Training.getInstance().getConf().set("warps." + strings[0], loc);
            Training.getInstance().getConf().save();
            return CommandResult.from(CommandResult.Type.SUCCESS, "§f[§8地标管理§f]§e创建成功");
        } else {
            return CommandResult.from(CommandResult.Type.SUCCESS, "§f[§8地标管理§f]§e指令不对请站在指定位置使用/setwarp <名字>");
        }
    }

    @Override
    public @NotNull CommandResult onConsoleCommand(@NotNull ConsoleCommandSender consoleCommandSender, @NotNull String[] strings) {
        return CommandResult.SUCCESS;
    }

    @Override
    public @NotNull TabResult onTab(@NotNull Player player, @NotNull String[] strings) {
        return TabResult.empty();
    }

    @Override
    public @NotNull TabResult onConsoleTab(@NotNull ConsoleCommandSender consoleCommandSender, @NotNull String[] strings) {
        return TabResult.empty();
    }

    @Override
    public @NotNull PermissionWrapper getPermission() {
        return PermissionWrapper.of("Bridge.admin");
    }

    @Override
    public @NotNull String getSyntax() {
        return "地标管理 但你似乎没有权限";
    }

    @Override
    public @NotNull String getDescription() {
        return "地标管理";
    }
}
