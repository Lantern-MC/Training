package net.lanternmc.training;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class LocationManager {

    private static HashMap<Player, Location> CheckPoint = new HashMap<>();

    public static final Location publicLoc = new Location(Bukkit.getWorld(Training.getInstance().getConf().getString("world")),
            Training.getInstance().getConf().getDouble("x"),
            Training.getInstance().getConf().getDouble("y"),
            Training.getInstance().getConf().getDouble("z"),
            (float) Training.getInstance().getConf().getDouble("yaw"),
            (float) Training.getInstance().getConf().getDouble("pitch")).add(0.5, 1, 0.5);

    public static Location getPlayerCheckpoint(Player p) {
        if (!CheckPoint.containsKey(p)) {
            setPlayCheckPoint(p, publicLoc);
        }
        return CheckPoint.get(p);
    }

    public static void setPlayCheckPoint(Player p, Location loc) {
        CheckPoint.put(p, loc);
    }

    public static void FirstJoin(Player p) {
        p.teleport(publicLoc);
    }

    public static void removePlayCheckPoint(Player p) {
        CheckPoint.remove(p);
    }
}
