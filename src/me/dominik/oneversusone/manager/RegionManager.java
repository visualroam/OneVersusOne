package me.dominik.oneversusone.manager;

import org.bukkit.Location;
import org.bukkit.World;

/**
 * Created by Dominik on 16.09.2016.
 */
public class RegionManager {


    private int minX, minY, minZ;
    private int maxX, maxY, maxZ;

    private World world;

    private Location loc1;
    private Location loc2;

    public RegionManager(Location loc1, Location loc2){

        this.minX = Math.min(loc1.getBlockX(),loc2.getBlockX());
        this.minY = Math.min(loc1.getBlockY(),loc2.getBlockY());
        this.minZ = Math.min(loc1.getBlockZ(),loc2.getBlockZ());

        this.maxX = Math.max(loc1.getBlockX(),loc2.getBlockX());
        this.maxY = Math.max(loc1.getBlockY(),loc2.getBlockY());
        this.maxZ = Math.max(loc1.getBlockZ(),loc2.getBlockZ());

        this.world = loc1.getWorld();

    }

    public Location getLocationMin(){
        this.loc1 = new Location(this.world,this.minX,this.minY,this.minZ);
        return loc1;
    }
    public Location getLocationMax(){
        this.loc2 = new Location(this.world,this.maxX,this.maxY,this.maxZ);
        return loc2;
    }

}
