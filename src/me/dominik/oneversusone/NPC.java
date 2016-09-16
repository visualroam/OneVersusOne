package me.dominik.oneversusone;


import com.mojang.authlib.GameProfile;
import me.dominik.oneversusone.utils.Reflection;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.util.UUID;


/**
 * Created by Dominik on 16.09.2016.
 */
public class NPC extends Reflection {

    PacketPlayOutNamedEntitySpawn create = new PacketPlayOutNamedEntitySpawn();

    int entityid;
    Location location;

    public NPC(Location location){
        this.entityid = (int) (Math.random()*1000) + 1;
        this.location = location;
    }

    public void spawn(){
        DataWatcher watcher = new DataWatcher(null);
        watcher.a(6, (float) 20);

        Packet packet = NPC.spawnNPC(new PacketPlayOutNamedEntitySpawn(),entityid, "0f689279-56fb-48cc-878c-1b79166387ad", location, null, watcher);
        for(Player all : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) all).getHandle().playerConnection.sendPacket(packet);
        }
    }

    public void destroy(){
        PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(new int[]{entityid});
        for(Player online : Bukkit.getOnlinePlayers()) {
            PlayerConnection connection = ((CraftPlayer) online).getHandle().playerConnection;
            connection.sendPacket(destroy);
        }
    }

    @SuppressWarnings("deprecation")
    public static Packet spawnNPC(PacketPlayOutNamedEntitySpawn create,int entityid, String uuid, Location loc, ItemStack inhand, DataWatcher watcher) {
        try {
            setField(create, create.getClass(), "a", entityid);
            setField(create, create.getClass(), "b", UUID.fromString(uuid));
            setField(create, create.getClass(), "c", toFixedPoint(loc.getBlockX()));
            setField(create, create.getClass(), "d", toFixedPoint(loc.getBlockY()));
            setField(create, create.getClass(), "e", toFixedPoint(loc.getBlockZ()));
            setField(create, create.getClass(), "f", toPackedByte(loc.getYaw()));
            setField(create, create.getClass(), "g", toPackedByte(loc.getPitch()));
            setField(create, create.getClass(), "h", inhand == null ? 0 : inhand.getTypeId());
            setField(create, create.getClass(), "i", watcher);

            return create;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static int toFixedPoint(double d) {
        return (int)d * 32;
    }

    private static byte toPackedByte(float f) {
        return (byte) ((int) (f * 256.0F / 360.0F));
    }

    public static void setField(Object instanz, Class<?> clazz, String field, Object value) throws Exception {

        Field f = clazz.getDeclaredField(field);
        f.setAccessible(true);
        f.set(instanz, value);

    }




}
