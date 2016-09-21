package me.dominik.oneversusone;

import me.dominik.oneversusone.utils.Countdown;
import me.dominik.oneversusone.utils.CountdownEvent;
import me.dominik.oneversusone.utils.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by Dominik on 18.09.2016.
 */
public class OneVsOneHandler {

    static Player player1;
    static Player player2;
    static HashMap<String, OneVsOneHandler> map = new HashMap<>();
    public Arena arena;

    public OneVsOneHandler(Player player1, Player player2){
        OneVsOneHandler.player1 = player1;
        OneVsOneHandler.player2 = player2;


    }

    public void selectRandomArena(){
        int seed = (int) (Math.random() * OneVersusOne.getInstance().getManager().getAmountArenas())+1;
        System.out.println("seed = " + seed);
        this.arena = OneVersusOne.getInstance().getManager().getArenaByID(seed);
    }

    public void startOneVersusOne(){
        OneVersusOne.getInstance().getPlayersIngame().add(player1);
        OneVersusOne.getInstance().getPlayersIngame().add(player2);

        selectRandomArena();

        map.put(player1.getUniqueId().toString(), this);
        map.put(player2.getUniqueId().toString(), this);

        player1.teleport(arena.player1Spawn);
        player2.teleport(arena.player2Spawn);

        player1.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 200, 200, true));
        player1.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 200, true));

        player2.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 200, 200, true));
        player2.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 200, true));

        waiting.startCountdown();
    }

    public static Countdown waiting = new Countdown(OneVersusOne.getInstance(), new CountdownEvent() {
        @Override
        public void tick(int i) {
            if(i == 10){
                player1.sendMessage(OneVersusOne.getPREFIX() + " Get Ready!");
                player2.sendMessage(OneVersusOne.getPREFIX() + " Get Ready!");
            }
            if(i == 5){
                player1.sendMessage(OneVersusOne.getPREFIX() + " Noch 5 Sekunden");
                player2.sendMessage(OneVersusOne.getPREFIX() + " Noch 5 Sekunden");
            }
            if(i == 1){
                Title tile = new Title("§4FIGHT!!", "OneVersusOne");
                tile.send(player1);
                tile.send(player2);
            }
        }

        @Override
        public void finish() {
            OneVersusOne.getInstance().setList(map);

            oneversusone.startCountdown();
        }
    },10,0,20L);

    public static Countdown oneversusone = new Countdown(OneVersusOne.getInstance(), new CountdownEvent() {
        @Override
        public void tick(int i) {
            if(i == 600 || i == 540 || i == 480 || i == 420 || i == 360 || i == 300 || i == 240 || i == 180|| i == 120 || i == 60 || i == 30 || i == 10 || i < 10){
                player1.sendMessage(OneVersusOne.getPREFIX() + " Noch " + formatSecondsToString(i));
                player2.sendMessage(OneVersusOne.getPREFIX() + " Noch " + formatSecondsToString(i));
            }
        }

        @Override
        public void finish() {

        }
    },600,0,20L);


    public static String formatSecondsToString(int seconds) {
        int minutes = seconds % 3600 / 60;
        int secondz = seconds % 60;
        return minutes > 0?(secondz > 0?minutes + " Minute" + (minutes > 1?"n":"") + " und " + secondz + " Sekunde" + (secondz == 1?"":"n"):minutes + " Minute" + (minutes > 1?"n":"")):secondz + " Sekunde" + (secondz == 1?"":"n");
    }

    public void finish(Player winner, Player loser){

        winner.sendMessage(OneVersusOne.getPREFIX() + "Sie haben gewonnen!");
        loser.sendMessage(OneVersusOne.getPREFIX() + "Sie haben verloren.");

        GameProfile g1w = OneVersusOne.getInstance().getUUID_GAMEPROFIL().get(winner.getUniqueId().toString());
        GameProfile g2e = OneVersusOne.getInstance().getUUID_GAMEPROFIL().get(loser.getUniqueId().toString());

        eloBerechnung(winner,true,loser,false);

        g1w.setWins(g1w.getWins() + 1);
        g2e.setLose(g2e.getWins() + 1);

        OneVersusOne.getInstance().getPlayersIngame().remove(winner);
        OneVersusOne.getInstance().getPlayersIngame().remove(loser);

        winner.teleport(Bukkit.getWorld("world").getSpawnLocation());
        loser.teleport(Bukkit.getWorld("world").getSpawnLocation());
    }


    public void eloBerechnung(Player player,boolean winOrLosePlayer,Player enemy, boolean winOrLoseEnemy){
        /*
        Ea Erwarteter Elostand
        Ra vorheriger Elostand a
        Rb vorheriger Elostand b

        R'a Endelowert
        k   Faktor abhängig von der Elo
        Sa  1 Sieg 0 Niederlage
         */

        GameProfile g1 = OneVersusOne.getInstance().getUUID_GAMEPROFIL().get(player.getUniqueId().toString());
        GameProfile g2 = OneVersusOne.getInstance().getUUID_GAMEPROFIL().get(enemy.getUniqueId().toString());

        int playerElo = g1.getElo();
        int enemyElo = g2.getElo();

        double Ea1 = 1 / (1 + Math.pow(10, (enemyElo - playerElo) / 400)); //Sollte player A
        double Ea2 = 1 / (1 + Math.pow(10, (playerElo - enemyElo) / 400)); // solte Enemy A
        System.out.println("Ea1 = " + Ea1);
        System.out.println("Ea2 = " + Ea2);

        int k1 = 0;
        int k2 = 0;

        if(g1.getWins() + g1.getLose() < 30){
            k1 = 40;
        } else if(playerElo < 2401){
            k1 = 20;
        } else if(playerElo > 2400){
            k1 = 10;
        }


        if(g2.getWins() + g2.getLose() < 30){
            k2 = 40;
        } else if(enemyElo < 2401){
            k2 = 20;

        } else if(enemyElo > 2400){
            k2 = 10;
        }

        if(winOrLosePlayer){
            int RaA = (int) (playerElo + k1 * (1 - Ea1));
            g1.setElo(RaA);
            player.sendMessage(OneVersusOne.getPREFIX() + "Elo: " + playerElo + "→" + RaA);
        } else {
            int RaA = (int) (playerElo + k1 * (0 - Ea1));
            g1.setElo(RaA);
            player.sendMessage(OneVersusOne.getPREFIX() + "Elo: " + playerElo + "→" + RaA);
        }
        if(winOrLoseEnemy){
            int RaB = (int) (enemyElo + k2 * (1 - Ea2));
            g2.setElo(RaB);
            enemy.sendMessage(OneVersusOne.getPREFIX() + "Elo: " + enemyElo + "→" + RaB);
        } else {
            int RaB = (int) (enemyElo + k2 * (0 - Ea2));
            g2.setElo(RaB);
            enemy.sendMessage(OneVersusOne.getPREFIX() + "Elo: " + enemyElo + "→" + RaB);
        }
    }

}
