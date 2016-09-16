package me.dominik.oneversusone.manager;

import com.google.gson.reflect.TypeToken;
import me.dominik.oneversusone.OneVersusOne;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.util.Vector;

import java.lang.reflect.Type;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dominik on 16.09.2016.
 */
public class ArenaManager {

    private Type STRING_LOCATION_MAP = new TypeToken<Map<String, Location>>() { }.getType();

    public ArenaManager(){
        OneVersusOne.getInstance().getMySQL().update("CREATE TABLE IF NOT EXISTS arenas (ID int,TITLE text, AUTHOR text, LOCATIONS text)");
    }

    public void saveArena(String title, String author, Location loc1, Location loc2){
        RegionManager rgm = new RegionManager(loc1,loc2);
        Location maxLoc = rgm.getLocationMax();
        Location minLoc = rgm.getLocationMin();
        Map<String, Location> points = getPoints(maxLoc,minLoc);
        String loc = OneVersusOne.locationGson.toJson(points,STRING_LOCATION_MAP);

        try {
            PreparedStatement sta = OneVersusOne.getInstance().getCon().prepareStatement("INSERT INTO arenas (TITLE, AUTHOR, LOCATIONS) VALUES (?,?,?);");
            sta.setString(1, title);
            sta.setString(2, author);
            sta.setString(3, loc);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public Map<String, Location> getPoints(Location maxLoc, Location minLoc){

        Map<String, Location> placeholder = new HashMap<>();
        int i = 1;

        for(int x = (int) minLoc.getX(); (int) minLoc.getX() <= maxLoc.getX(); x++){
            for(int y = (int) minLoc.getY(); (int) minLoc.getY() <= maxLoc.getY(); y++){
                for(int z = (int) minLoc.getZ(); (int) minLoc.getZ() <= maxLoc.getZ(); z++){
                    Block b = maxLoc.getWorld().getBlockAt(x,y,z);
                    if(b.getType().equals(Material.DIAMOND_BLOCK)){
                        Location specSpawn = new Location(maxLoc.getWorld(),x,y,z);
                        placeholder.put("specspawn", specSpawn);
                    }
                    if(b.getType().equals(Material.CHEST)){
                        Block ob = maxLoc.getWorld().getBlockAt(x,y+1,z);
                        if(ob.getType().equals(Material.SKULL)){
                            Skull skull = (Skull) ob.getState();
                            Location loc = new Location(maxLoc.getWorld(),x,y,z);
                            Vector vector = new Vector(skull.getRotation().getModX(), skull.getRotation().getModY(), skull.getRotation().getModZ());
                            loc.setDirection(vector);
                            placeholder.put("spawn" + i, loc);
                            i++;
                        }
                    }
                    if(b.getType().equals(Material.MOB_SPAWNER)){
                        Location loc = new Location(maxLoc.getWorld(),x,y,z);
                        placeholder.put("punkt1", loc);
                    }
                    if(b.getType().equals(Material.JUKEBOX)){
                        Location loc = new Location(maxLoc.getWorld(),x,y,z);
                        placeholder.put("punkt2", loc);
                    }
                }
            }
        }
        return placeholder;
    }




}
