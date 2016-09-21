package me.dominik.oneversusone;

import com.sun.tracing.dtrace.ArgsAttributes;
import lombok.AllArgsConstructor;
import org.bukkit.Location;

/**
 * Created by Dominik on 19.09.2016.
 */
@AllArgsConstructor public class Arena {

    public Location player1Spawn;
    public Location player2Spawn;
    public Location specSpawn;
    public Location area1Block;
    public Location area2Block;

    public String name;
    public String author;

}
