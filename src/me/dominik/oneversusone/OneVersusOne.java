package me.dominik.oneversusone;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Dominik on 15.09.2016.
 */
public class OneVersusOne extends JavaPlugin {

    @Getter public static final String PREFIX = "§6One§4Versus§6One §f| §e";
    @Getter private static OneVersusOne instance;


    @Override
    public void onEnable() {
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
}
