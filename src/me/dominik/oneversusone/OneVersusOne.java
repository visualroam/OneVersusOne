package me.dominik.oneversusone;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.Setter;
import me.dominik.oneversusone.commands.ArenaCommand;
import me.dominik.oneversusone.commands.TestCommand;
import me.dominik.oneversusone.commands.queueCommand;
import me.dominik.oneversusone.listener.block.BlockBreakAndPlaceListener;
import me.dominik.oneversusone.listener.entity.EntityDamageListener;
import me.dominik.oneversusone.listener.inventory.InventoryClickListener;
import me.dominik.oneversusone.listener.other.WeatherChangeListener;
import me.dominik.oneversusone.listener.player.*;
import me.dominik.oneversusone.manager.*;
import me.dominik.oneversusone.utils.LocationTypeAdapter;
import me.dominik.oneversusone.utils.MySQL;
import me.dominik.oneversusone.utils.NPC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by Dominik on 15.09.2016.
 */
public class OneVersusOne extends JavaPlugin {

    @Getter public static final String PREFIX = "§6One§4Versus§6One §f| §e";
    @Getter private static OneVersusOne instance;
    @Getter private List<Player> playersIngame = new ArrayList<>();
    @Getter @Setter private NPC npc;
    @Getter MySQL mySQL;
    @Getter @Setter private Connection con;
    public static Gson locationGson = new GsonBuilder().registerTypeAdapter(Location.class, new LocationTypeAdapter()).create();
    @Getter HashMap<Player, HashMap<Integer, Location>> map = new HashMap<>();
    @Getter ArenaManager manager;
    @Getter PlayerDatabaseManager pdmanager;
    @Getter NPCManager npcManager;
    @Getter GameProfileManager profileManager;
    @Getter HashMap<String, GameProfile> UUID_GAMEPROFIL = new HashMap<>();
    @Getter InventoryManager ivnManager = new InventoryManager();
    @Getter QueueManager queueManager = new QueueManager();
    @Getter @Setter HashMap<String, OneVsOneHandler> list = new HashMap<>();

    @Override
    public void onEnable() {

        initListener();
        initCommands();
        initMySQLDatabase();

        manager = new ArenaManager();
        pdmanager = new PlayerDatabaseManager();
        npcManager = new NPCManager();
        profileManager = new GameProfileManager();

        this.npc = new NPC("_XD0M3_","DerKev","",new Random().nextInt(10000), OneVersusOne.getInstance().getNpcManager().getNPCLocation(), Material.AIR,false);

        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onLoad() {
        OneVersusOne.instance = this;
        super.onLoad();
    }


    public void initCommands(){
        this.getCommand("test").setExecutor(new TestCommand());
        this.getCommand("arena").setExecutor(new ArenaCommand());
        this.getCommand("queue").setExecutor(new queueCommand());
    }


    public void initListener(){
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new EntityDamageListener(), this);
        pm.registerEvents(new JoinAndQuitListener(), this);
        pm.registerEvents(new PlayerInteractListener(), this);
        pm.registerEvents(new BlockBreakAndPlaceListener(), this);
        pm.registerEvents(new MenuInteractEvent(), this);
        pm.registerEvents(new InventoryClickListener(), this);
        pm.registerEvents(new FoodChangeListener(), this);
        pm.registerEvents(new WeatherChangeListener(), this);
        pm.registerEvents(new PlayerDropListener(), this);
        pm.registerEvents(new PlayerDeathListener(), this);
    }

    public void initMySQLDatabase(){
        mySQL = new MySQL("37.228.132.119", "loro", "loro", "aedexx7y5sjn");

    }




}


/*

Schauen was beim Marker nicht geht.
Schaun ob Datenbank geht und beginnen die Warteschlange machen ;)

 */