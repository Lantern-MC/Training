package net.lanternmc.training;

import lombok.Getter;
import lombok.Setter;
import net.lanternmc.training.Warp.Warp;
import net.lanternmc.training.Warp.delWarp;
import net.lanternmc.training.Warp.setWarp;
import net.lanternmc.training.api.BlockSkinProvider;
import net.lanternmc.training.commands.*;
import net.lanternmc.training.listener.*;
import net.lanternmc.training.manager.Default;
import net.lanternmc.training.manager.ScoreBoard;
import net.lanternmc.training.utils.CleanroomChunkGenerator;
import net.lanternmc.training.utils.NoAIUtils;
import net.lanternstudio.toolsapi.CommandRegister.CommandController;
import net.lanternstudio.toolsapi.FileManager.ConfigUtil;
import net.lanternstudio.toolsapi.ScoreBoard.BoardManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.util.HashMap;

public class Training extends JavaPlugin {
    @Getter
    private static Training instance;
    @Getter
    private static HashMap<Player, Counter> counters = new HashMap<>();
    @Getter
    private static HashMap<Block, MaterialData> placedBlocks = new HashMap<>();
    @Setter
    private static BlockSkinProvider blockSkinProvider;

    @Getter
    private ConfigUtil Conf;

    @Override
    public void onEnable() {
        instance = this;
        Conf = new ConfigUtil(this, "Config.yml");
        new BoardManager(this, new ScoreBoard());
        blockSkinProvider = new DefaultBlockSkinProvider();
        Bukkit.getConsoleSender().sendMessage(new String[]{
                "§bTraining §7>> §f----------------------------------------------------------------",
                "§bTraining §7>> §a高级训练场 已加载 §bBy.Ldcr & LanternStudio ",
                "§bTraining §7>> §f----------------------------------------------------------------",
                "§bTraining §7>> §e踩在 §a绿宝石块 §e上可以设置传送点",
                "§bTraining §7>> §e踩在 §c红石块 §e上可以回到传送点",
                "§bTraining §7>> §e踩在 §b青金石块 §e上可以回到出生点",
                "§bTraining §7>> §e踩在 §b钻石块 §e上可以开始射击练习",
                "§bTraining §7>> §e§n踩在 §b屎 §e上可以做老八",
                "§bTraining §7>> §e使用 §a/genvillager §e可在站立位置创建村民刷新点",
                "§bTraining §7>> §c掉入虚空会自动回到 §a传送点 §c并重置地图",
                "§bTraining §7>> §c注意: 创造模式放置的方块不会被重置, 请在生存模式下练习",
                "§bTraining §7>> §f----------------------------------------------------------------"
        });

        regListener();
        regCommands();
        if (getServer().getWorld("Trainworld") == null) {
            createWorld();
        }
        spawnVillager();
//        spawnRange();


        Bukkit.getScheduler().runTaskTimer(this, () -> {
            if (Bukkit.getOnlinePlayers().isEmpty()) return;
            spawnVillager();
        }, 600, 600);

        // 资源包
        // pluginManager.registerEvents(new ResourcePackLoader(), this);
        //
        CommandController controller = new CommandController(this);
        controller.addCommand(new setWarp());
        controller.addCommand(new Warp());
        controller.addCommand(new delWarp());
        controller.addCommand(new Cluthes());


    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("§bTraining §7>> §c正在清除所有已放置方块....");
        for (Counter c : counters.values()) {
            c.instantBreakBlock();
        }
        counters.clear();
        for (Block b : Counter.scheduledBreakBlocks) {
            b.setType(Material.AIR);
        }
        Counter.scheduledBreakBlocks.clear();
        Bukkit.getConsoleSender().sendMessage("§bTraining §7>> §a方块清除完毕.");
    }

    void regListener() {
        Bukkit.getPluginManager().registerEvents(new OnLineListener(), this);
        Bukkit.getPluginManager().registerEvents(new CounterListener(), this);
        Bukkit.getPluginManager().registerEvents(new HighlightListener(), this);
        Bukkit.getPluginManager().registerEvents(new TriggerBlockListener(), this);
        Bukkit.getPluginManager().registerEvents(new ShootManager(), this);
        Bukkit.getPluginManager().registerEvents(new fireball(), this);
        Bukkit.getPluginManager().registerEvents(new ChatManager(), this);
        Bukkit.getPluginManager().registerEvents(new AntiExplosion(), this);
    }

    void regCommands() {
        getCommand("bridge").setExecutor(new BridgeCommand());
        getCommand("clearblock").setExecutor(new ClearCommand());
        getCommand("bsaveworld").setExecutor(new SaveWorldCommand());
        getCommand("imstuck").setExecutor(new StuckCommand());
        getCommand("genvillager").setExecutor(new VillagerSpawnPointCommand());
    }

    void createWorld() {
        File file = new File("");
        File worldFolder = new File(file.getAbsolutePath() + System.getProperty("file.separator") + "Trainworld");
        if (!worldFolder.exists()) {
            WorldCreator worldCreator = new WorldCreator("Trainworld");
            worldCreator.generateStructures(false);
            worldCreator.type(WorldType.NORMAL);
            worldCreator.environment(World.Environment.NORMAL);
            worldCreator.generator(new CleanroomChunkGenerator());
            World Trainworld = getServer().createWorld(worldCreator);
            Trainworld.setSpawnFlags(false, false);
            Trainworld.setSpawnLocation(0, 5, 0);
            Bukkit.getConsoleSender().sendMessage("§2由于你没有创建世界我给你创了个 记得用小木斧子把地图挪到指定");
        }
        getServer().createWorld(new WorldCreator("Trainworld"));
    }

    public static void clearEffect(Player player) {
        for (PotionEffect eff : player.getActivePotionEffects()) {
            if (eff.getType() == PotionEffectType.INVISIBILITY && player.isOp()) {
                continue;
            }
            player.removePotionEffect(eff.getType());
        }
    }

    public static void clearInventory(Player p) {
        Inventory inv = p.getInventory();
        for (int i = 0; i < inv.getSize(); i++) {
            ItemStack item = inv.getItem(i);
            if (item != null && item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null && item.getItemMeta().getDisplayName().contains("Key")) {
                continue;
            }
            inv.setItem(i, null);
        }
    }

    public static Counter getCounter(Player p) {
        Counter c = counters.get(p);
        if (c == null) {
            c = new Counter(p);
            counters.put(p, c);
        }
        return c;
    }

    public static void spawnVillager() {
        for (Entity en : Bukkit.getWorld("Trainworld").getEntities())
            if (en.getType() == EntityType.VILLAGER) if ("靶子".equals(en.getCustomName())) {
                en.remove();
            }
        for (ArmorStand stand : Bukkit.getWorld("Trainworld").getEntitiesByClass(ArmorStand.class)) {
            if (stand.getCustomName() == null) {
                continue;
            }
            if (stand.getCustomName().contains("VillagerSpawnPoint")) {
                Villager vi = (Villager) stand.getWorld().spawnEntity(stand.getLocation().add(0, 1, 0),
                        EntityType.VILLAGER);
                vi.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 32766, 254, false, false), true);
                vi.setProfession(Profession.LIBRARIAN);
                vi.setMaxHealth(1);
                vi.setHealth(1);
                vi.setCustomName("靶子");
                vi.setCustomNameVisible(false);
                NoAIUtils.setAI(vi, false);
            }
        }
    }

//    public static void spawnRange() {
//        for (Entity en : Bukkit.getWorld("Trainworld").getEntities())
//            if (en.getType() == EntityType.COW) if ("qwed".equals(en.getCustomName())) {
//                en.remove();
//            }
//        for (ArmorStand stand : Bukkit.getWorld("Trainworld").getEntitiesByClass(ArmorStand.class)) {
//            if (stand.getCustomName() == null) {
//                continue;
//            }
//            if (stand.getCustomName().startsWith("Range")) {
//                PathUtils PU = new PathUtils(stand, true ? 3.0D : 5);
//            }
//        }
//    }

    public static void teleportCheckPoint(Player p) {
        p.setFallDistance(0);
        clearInventory(p);
        p.getInventory().addItem(blockSkinProvider.provide(p));
        p.setFoodLevel(20);
        p.setHealth(20);
        p.setNoDamageTicks(10);
        getCounter(p).teleportCheckPoint();
        p.setGameMode(GameMode.SURVIVAL);
        p.getInventory().setItem(8, Default.getMenu());
    }

    public static void refreshItem(Player p) {
        clearInventory(p);
        p.getInventory().addItem(blockSkinProvider.provide(p));
    }

    public static boolean isPlacedByPlayer(Block b) {
        if (getPlacedBlocks().containsKey(b)) return getPlacedBlocks().get(b).equals(b.getState().getData());
        return false;
    }

}
