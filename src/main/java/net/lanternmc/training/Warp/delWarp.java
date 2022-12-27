package net.lanternmc.training.Warp;

import net.lanternmc.training.Training;
import net.lanternstudio.toolsapi.CommandRegister.AbstractCommand;
import net.lanternstudio.toolsapi.CommandRegister.CommandResult;
import net.lanternstudio.toolsapi.CommandRegister.PermissionWrapper;
import net.lanternstudio.toolsapi.CommandRegister.TabResult;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class delWarp extends AbstractCommand {
    public delWarp() {
        super("delwarp");
    }

    @Override
    public @NotNull CommandResult onCommand(@NotNull Player player, @NotNull String[] strings) {
        if (strings.length >= 1) {
            if (Training.getInstance().getConf().getString("warps." + strings[0]) == null) {
                return CommandResult.from(CommandResult.Type.SUCCESS, "§f[§8地标管理§f]§c没有这个地标本来就没有");
            }
            Training.getInstance().getConf().set("warps." + strings[0], null);
            Training.getInstance().getConf().save();
            return CommandResult.from(CommandResult.Type.SUCCESS, "§f[§8地标管理§f]§e删除成功");
        } else {
            return CommandResult.from(CommandResult.Type.SUCCESS, "§f[§8地标管理§f]§e指令不对 删除请 使用/delWarp <名字>");
        }
    }

    @Override
    public @NotNull CommandResult onConsoleCommand(@NotNull ConsoleCommandSender consoleCommandSender, @NotNull String[] strings) {
        return CommandResult.SUCCESS;
    }

    @Override
    public TabResult onTab(@NotNull Player player, @NotNull String[] strings) {
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
