package me.dominik.oneversusone;

import lombok.Getter;
import lombok.Setter;
import me.dominik.oneversusone.commands.TestCommand;
import me.dominik.oneversusone.listener.entity.EntityDamageListener;
import org.bukkit.BanEntry;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dominik on 15.09.2016.
 */
public class OneVersusOne extends JavaPlugin {

    @Getter public static final String PREFIX = "§6One§4Versus§6One §f| §e";
    @Getter private static OneVersusOne instance;
    @Getter private List<Player> playersIngame = new ArrayList<>();
    @Getter @Setter private NPC npc;

    @Override
    public void onEnable() {
        initListener();
        initCommands();
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

    }

    public void initListener(){
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new EntityDamageListener(), this);
    }


}
