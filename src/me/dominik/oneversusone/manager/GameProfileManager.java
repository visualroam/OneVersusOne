package me.dominik.oneversusone.manager;

import me.dominik.oneversusone.GameProfile;
import me.dominik.oneversusone.OneVersusOne;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.naming.PartialResultException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Dominik on 17.09.2016.
 */
public class GameProfileManager {

    public GameProfileManager(){
        OneVersusOne.getInstance().getMySQL().update("CREATE TABLE IF NOT EXISTS Gameprofile (UUID text, ELO int,WINS int, LOSE int)");
    }

    public boolean profileExists(Player p){
        String uuid = p.getUniqueId().toString();
        ResultSet rs = OneVersusOne.getInstance().getMySQL().query("SELECT * FROM Gameprofile WHERE UUID= '" + uuid + "'");
        try {
            if(rs.next()){
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



    public GameProfile createProfile(Player player){
        if(profileExists(player)){
            return new GameProfile(player.getUniqueId().toString(), getElo(player.getUniqueId().toString()),getWINS(player.getUniqueId().toString()),getLOSE(player.getUniqueId().toString()));
        }
        OneVersusOne.getInstance().getMySQL().update("INSERT INTO Gameprofile(UUID, ELO, WINS, LOSE) VALUES ('"+ player.getUniqueId().toString() + "', '1000', '0', '0');");
        return new GameProfile(player.getUniqueId().toString(),1000,0,0);
    }

    public int getElo(String UUID){
        ResultSet rs = OneVersusOne.getInstance().getMySQL().query("SELECT * FROM Gameprofile WHERE UUID='" + UUID + "'");
        try {
            if(rs.next()){
                return rs.getInt("ELO");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return 1000;
    }

    public int getWINS(String UUID){
        ResultSet rs = OneVersusOne.getInstance().getMySQL().query("SELECT * FROM Gameprofile WHERE UUID='" + UUID + "'");
        try {
            if(rs.next()){
                return rs.getInt("WINS");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public int getLOSE(String UUID){
        ResultSet rs = OneVersusOne.getInstance().getMySQL().query("SELECT * FROM Gameprofile WHERE UUID='" + UUID + "'");
        try {
            if(rs.next()){
                return rs.getInt("LOSE");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public void updateProfile(GameProfile gameProfile){
        OneVersusOne.getInstance().getMySQL().update("UPDATE Gameprofile SET ELO= '" + gameProfile.getElo() + "' WHERE UUID= '" + gameProfile.getUUID() + "';");
        OneVersusOne.getInstance().getMySQL().update("UPDATE Gameprofile SET WINS= '" + gameProfile.getWins() + "' WHERE UUID= '" + gameProfile.getUUID() + "';");
        OneVersusOne.getInstance().getMySQL().update("UPDATE Gameprofile SET LOSE= '" + gameProfile.getLose() + "' WHERE UUID= '" + gameProfile.getUUID() + "';");
    }

}
