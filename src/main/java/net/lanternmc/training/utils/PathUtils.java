package net.lanternmc.training.utils;

import com.google.common.collect.Lists;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class PathUtils extends EntityCow {
    private Location location;
    static {
        ((Map<String, Class<PathUtils>>)getPrivateField("c", EntityTypes.class, null)).put("PathUtils", PathUtils.class);
        ((Map<Class<PathUtils>, String>)getPrivateField("d", EntityTypes.class, null)).put(PathUtils.class, "AICowEntity");
        ((Map<Class<PathUtils>, Integer>)getPrivateField("f", EntityTypes.class, null)).put(PathUtils.class, Integer.valueOf(54));
        ((Map<String, Integer>)getPrivateField("g", EntityTypes.class, null)).put("AICowEntity", Integer.valueOf(54));
    }

    public PathUtils(Location location, Location toLoc) {
        super(((CraftWorld)location.getWorld()).getHandle());
        setCustomName("Fuck");
        setCustomNameVisible(true);
        setPosition(location.getX(), location.getY(), location.getZ());
        this.location = toLoc;
        this.goalSelector.a(6, new PathfinderGoalPatrol(this, 1.0D, new Location[]{toLoc}));
    }


    private static Object getPrivateField(String fieldName, Class clazz, Object object) {
        Object obj = null;
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            obj = field.get(object);
        } catch (NoSuchFieldException|IllegalAccessException e) {
            e.printStackTrace();
        }
        return obj;
    }

    private static class PathfinderGoalPatrol extends PathfinderGoal {

        private double speed;
        private EntityInsentient entity;
        private List<Location> loc;
        private Navigation navigation;
        private int currentLocationIndex;


        public PathfinderGoalPatrol(EntityInsentient entityInsentient, double v, Location[] locations) {
            this.entity = entity;
            this.navigation = (Navigation) this.entity.getNavigation();
            this.speed = speed;
            this.loc = Lists.newArrayList(locations.clone());
            this.currentLocationIndex = 0;
        }

        @Override
        public boolean a() {
            Location entityLocation = this.entity.getBukkitEntity().getLocation();
            if (entityLocation.distance(this.loc.get(this.currentLocationIndex)) < 1.0D) {
                if (this.currentLocationIndex + 1 >= this.loc.size()) {
                    this.currentLocationIndex = 0;
                    return true;
                }
                this.currentLocationIndex++;
            }
            return true;
        }
    }
}
