package me.dominik.oneversusone.manager;

import com.google.gson.reflect.TypeToken;
import me.dominik.oneversusone.OneVersusOne;
import me.dominik.oneversusone.utils.LocationTypeAdapter;
import me.dominik.oneversusone.utils.NPC;
import org.bukkit.Location;

import java.lang.reflect.Type;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by Dominik on 17.09.2016.
 */
public class NPCManager {

    public NPCManager(){
        OneVersusOne.getInstance().getMySQL().update("CREATE TABLE IF NOT EXISTS NPC (ID int, LOCATION text)");
    }

    public void saveNPCLocation(Location location){
        try {
            PreparedStatement sta = OneVersusOne.getInstance().getCon().prepareStatement("INSERT INTO NPC (`ID`, `LOCATION`) VALUES (?,?);");
            sta.setInt(1, 1);
            sta.setString(2, OneVersusOne.locationGson.toJson(location));
            sta.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Location getNPCLocation(){
        try {
            ResultSet resultSet = OneVersusOne.getInstance().getMySQL().query("SELECT * FROM NPC WHERE ID='1'");
            if(resultSet.next()){
                return OneVersusOne.locationGson.fromJson(resultSet.getString("LOCATION"), Location.class);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
