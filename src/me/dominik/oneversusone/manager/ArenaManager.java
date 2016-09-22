package me.dominik.oneversusone.manager;

import com.google.gson.reflect.TypeToken;
import me.dominik.oneversusone.Arena;
import me.dominik.oneversusone.OneVersusOne;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.util.Vector;

import java.lang.reflect.Type;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dominik on 16.09.2016.
 */
public class ArenaManager {

    private Type STRING_LOCATION_MAP = new TypeToken<Map<String, Location>>() { }.getType();

    public ArenaManager(){
        OneVersusOne.getInstance().getMySQL().update("CREATE TABLE IF NOT arenas (\n" +
                "  `ID` int(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `TITLE` text,\n" +
                "  `AUTHOR` text,\n" +
                "  `LOCATIONS` text,\n" +
                "  PRIMARY KEY (`ID`)\n" +
                ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1");
    }

    public void saveArena(String title, String author, Location loc1, Location loc2){
        System.out.println("1");
        RegionManager rgm = new RegionManager(loc1,loc2);
        System.out.println("2");
        Location maxLoc = rgm.getLocationMax();
        System.out.println("2");
        Location minLoc = rgm.getLocationMin();
        System.out.println("3");
        Map<String, Location> points = getPoints(maxLoc,minLoc);
        System.out.println("4");
        String loc = OneVersusOne.locationGson.toJson(points,STRING_LOCATION_MAP);
        System.out.println("5");
        try {
            PreparedStatement sta = OneVersusOne.getInstance().getCon().prepareStatement("INSERT INTO arenas (`TITLE`, `AUTHOR`, `LOCATIONS`) VALUES (?,?,?);");
            System.out.println("6");
            sta.setString(1, title);
            System.out.println("7");
            sta.setString(2, author);
            System.out.println("8");
            sta.setString(3, loc);
            System.out.println("9");
            sta.executeUpdate();
            System.out.println("10");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public Map<String, Location> getPoints(Location maxLoc, Location minLoc){

        Map<String, Location> placeholder = new HashMap<>();
        int i = 1;

        System.out.println("minZ: " + minLoc.getZ());
        System.out.println("maxZ: " + maxLoc.getZ());

        for(int x = (int) minLoc.getX(); minLoc.getX() <= maxLoc.getX(); x++){
            for(int y = (int) minLoc.getY(); minLoc.getY() <= maxLoc.getY(); y++){
                for(int z = (int) minLoc.getZ(); minLoc.getZ() <= maxLoc.getZ(); z++){
                    System.out.println(x + " " + y + " " + z);
                    Block b = maxLoc.getWorld().getBlockAt(x,y,z);
                    if(b.getType().equals(Material.DIAMOND_BLOCK)){
                        Location specSpawn = new Location(maxLoc.getWorld(),x,y,z);
                        placeholder.put("specspawn", specSpawn);
                    }
                    System.out.println("1.");
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
                    System.out.println("2.");
                    if(b.getType().equals(Material.MOB_SPAWNER)){
                        Location loc = new Location(maxLoc.getWorld(),x,y,z);
                        placeholder.put("punkt1", loc);
                    }
                    System.out.println("3.");
                    if(b.getType().equals(Material.JUKEBOX)){
                        Location loc = new Location(maxLoc.getWorld(),x,y,z);
                        placeholder.put("punkt2", loc);
                    }
                    if(z == (int) maxLoc.getZ()){
                        break;
                    }
                }
                if(y == (int) maxLoc.getY()){
                    break;
                }
            }
            if(x == (int) maxLoc.getX()){
                break;
            }
        }
        return placeholder;
    }


    public int getAmountArenas(){
        ResultSet rs = OneVersusOne.getInstance().getMySQL().query("SELECT COUNT(*) FROM arenas");
        try{
            if(rs.next()){
                return rs.getInt(1);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }



    public Arena getArenaByID(int id){
        ResultSet rs = OneVersusOne.getInstance().getMySQL().query("SELECT * FROM arenas WHERE ID= '" + id + "'");
        try {
            if(rs.next()){
                Map<String, Location> map;
                map = OneVersusOne.locationGson.fromJson(rs.getString("LOCATIONS"), STRING_LOCATION_MAP);
                Location loc1 = map.get("punkt1");
                Location loc2 = map.get("punkt2");
                Location loc3 = map.get("spawn1");
                Location loc4 = map.get("spawn2");
                Location loc5 = map.get("specspawn");
                String name = rs.getString("TITLE");
                String author = rs.getString("AUTHOR");
                return new Arena(loc3,loc4,loc5,loc1,loc2,name,author);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }







}
