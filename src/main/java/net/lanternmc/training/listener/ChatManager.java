package net.lanternmc.training.listener;

import me.clip.placeholderapi.PlaceholderAPI;
import net.lanternmc.training.manager.Default;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatManager implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        e.setFormat(PlaceholderAPI.setPlaceholders(e.getPlayer(),Default.getChatFormat())
                + e.getMessage());
    }

}
