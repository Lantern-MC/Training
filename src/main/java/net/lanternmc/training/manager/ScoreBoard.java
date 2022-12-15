package net.lanternmc.training.manager;

import me.clip.placeholderapi.PlaceholderAPI;
import net.lanternmc.r1_8.Scorebroad.ScoreBoard5.BoardAdapter;
import net.lanternmc.training.Training;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScoreBoard implements BoardAdapter {
    public static List<String> list;

    @Override
    public String getTitle() {
        return ChatColor.translateAlternateColorCodes('&',
                Training.getInstance().getConf().getString("ScoreBoard.title"));
    }

    @Override
    public List<String> getStrings(Player player) {
        SimpleDateFormat format = new SimpleDateFormat ("yy/MM/dd");
        String sb = format.format(new Date());
        list = new ArrayList<>();
        for (String s : Training.getInstance().getConf().getStringList("ScoreBoard.lines")) {
            list.add(ChatColor.translateAlternateColorCodes('&',
                    PlaceholderAPI.setPlaceholders(player, s).replace("{data}", sb)));
        }
        return list;
    }
}
