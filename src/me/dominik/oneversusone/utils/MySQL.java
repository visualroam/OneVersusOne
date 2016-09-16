package me.dominik.oneversusone.utils;

import me.dominik.oneversusone.OneVersusOne;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Dominik on 16.09.2016.
 */
public class MySQL {


    private String HOST = "";
    private String DATABASE = "";
    private String USER = "";
    private String PASSWORD = "";

    public MySQL(String host, String database, String user, String password){
        this.HOST = host;
        this.DATABASE = database;
        this.USER = user;
        this.PASSWORD = password;



        connect();
    }

    private void connect() {
        try {
            OneVersusOne.getInstance().setCon(DriverManager.getConnection("jdbc:mysql://" + HOST + ":3306/" + DATABASE + "?autoReconnect=true", USER, PASSWORD));
            System.out.println(OneVersusOne.getPREFIX() + " Die Verbindund zur Datenbank wurde erstellt.");
        } catch (SQLException sqlex){
            sqlex.printStackTrace();
        }
    }

    public void close() {
        try {
            if(OneVersusOne.getInstance().getCon() != null){
                OneVersusOne.getInstance().getCon().close();
                System.out.println(OneVersusOne.getPREFIX() + " Die Verbindund zur Datenbank wurde geschlossen.");
            }
        } catch (SQLException sqlex){
            sqlex.printStackTrace();
        }
    }

    public void update(String query){
        try {
            Statement st = OneVersusOne.getInstance().getCon().createStatement();
            st.executeUpdate(query);
            st.close();
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }

    }

    public ResultSet query(String query){
        ResultSet rs = null;
        try {
            Statement st = OneVersusOne.getInstance().getCon().createStatement();
            rs = st.executeQuery(query);
        }catch (SQLException sqlex){
            sqlex.printStackTrace();
        }
        return rs;
    }



}
