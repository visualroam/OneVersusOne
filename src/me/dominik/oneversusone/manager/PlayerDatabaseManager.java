package me.dominik.oneversusone.manager;
import me.dominik.oneversusone.OneVersusOne;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;


public class PlayerDatabaseManager {

    public PlayerDatabaseManager(){
        OneVersusOne.getInstance().getMySQL().update("CREATE TABLE IF NOT EXISTS Spieler(NAME text, UUID text);");
    }

    public boolean playerExists(Player p){
        String name = p.getName();
        ResultSet rs = OneVersusOne.getInstance().getMySQL().query("SELECT * FROM Spieler WHERE NAME= '" + name + "'");
        try {
            if(rs.next()){
                return rs.getString("NAME") != null;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean playerExists(String name, String what){
        if(what.equalsIgnoreCase("name")){
            ResultSet rs = OneVersusOne.getInstance().getMySQL().query("SELECT * FROM Spieler WHERE NAME= '" + name + "'");
            try {
                if(rs.next()){
                    return rs.getString("NAME") != null;
                }
                return false;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }
        if(what.equalsIgnoreCase("uuid")){
            ResultSet rs = OneVersusOne.getInstance().getMySQL().query("SELECT * FROM Spieler WHERE UUID= '" + name + "'");
            try {
                if(rs.next()){
                    return rs.getString("UUID") != null;
                }
                return false;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }
        return false;
    }

    public void createPlayer(Player p){
        String name = p.getName();
        String uuid = p.getUniqueId().toString();
        if(!(playerExists(p))){
            OneVersusOne.getInstance().getMySQL().update("INSERT INTO Spieler(NAME, UUID) VALUES ('" + name + "', '"+ uuid + "');");
        }

    }

    public String getPlayerName(String UUID){
        String name = "";
        if(playerExists(UUID, "uuid")){
            ResultSet rs = OneVersusOne.getInstance().getMySQL().query("SELECT * FROM Spieler WHERE UUID= '" + UUID + "'");
            try {
                if((!rs.next()) || rs.getString("NAME") == null);
                name = rs.getString("NAME");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            name = "unknown";
        }

        return name;
    }

    public String getPlayerUUID(String name){
        String uuid = "";
        if(playerExists(name, "name")){
            ResultSet rs = OneVersusOne.getInstance().getMySQL().query("SELECT * FROM Spieler WHERE NAME= '" + name + "'");
            try {
                if((!rs.next()) || rs.getString("UUID") == null);
                uuid = rs.getString("UUID");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            uuid = "unknown";
        }
        return uuid;
    }

    public void sameName(Player p){
        String name = p.getName();
        String uuid = p.getUniqueId().toString();
        if(playerExists(p)){
            if(!name.equals(getPlayerName(uuid))){
                OneVersusOne.getInstance().getMySQL().update("UPDATE Spieler SET NAME= '" + name + "' WHERE UUID= '" + uuid + "';");
                p.sendMessage(OneVersusOne.getPREFIX() + "Dein Neuer Name wurde aktualisiert");
            } else {
                p.sendMessage(" TEST ");
            }
        } else {
            createPlayer(p);
            sameName(p);
        }

    }



}
