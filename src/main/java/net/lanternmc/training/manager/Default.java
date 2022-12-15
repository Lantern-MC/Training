package net.lanternmc.training.manager;

import net.lanternmc.training.Training;
import net.lanternmc.training.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Default {

    public static ItemStack getMenu() {
        ItemBuilder ib;

        if(Training.getInstance().getConf().get("MenuItem") == null) {
            Training.getInstance().getConf().set("MenuItem.Material", "COMPASS");
            Training.getInstance().getConf().set("MenuItem.Displayname", "§a 搭路引导菜单");
            Training.getInstance().getConf().set("MenuItem.lore", "§f X 用于看教程 X");
            Training.getInstance().getConf().set("MenuItem.share", false);
            Training.getInstance().getConf().save();
        }

        ib = new ItemBuilder(Material.getMaterial(Training.getInstance().getConf().getString("MenuItem.Material")));
        ib.setDisplayName(Training.getInstance().getConf().getString("MenuItem.Displayname"));
        ib.setLore(Training.getInstance().getConf().getString("MenuItem.lore"));
        if (Training.getInstance().getConf().getBoolean("MenuItem.share")){
            ib.addGlow();
        }
        return ib.build();
    }

    public static String getChatFormat() {
        if(Training.getInstance().getConf().get("Chat") == null) {
            Training.getInstance().getConf().set("ChatFormat", "%player_name%: ");
            Training.getInstance().getConf().save();
        }
        return Training.getInstance().getConf().getString("ChatFormat");
    }

}
