package me.dominik.oneversusone.listener.other;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

/**
 * Created by Dominik on 19.09.2016.
 */
public class WeatherChangeListener implements Listener {

    public void onChange(WeatherChangeEvent e){

        e.setCancelled(true);

    }

}
