package me.dominik.oneversusone.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Dominik on 16.09.2016.
 */
public class MyInventory {

    Inventory inventory;
    String inventoryName;
    int inventorySize;

    public MyInventory(String inventoryName, int inventorySize){
        this.inventoryName = ChatColor.translateAlternateColorCodes('&', inventoryName);
        this.inventorySize = inventorySize;
        inventory = Bukkit.createInventory(null,this.inventorySize,this.inventoryName);
    }

    public MyInventory(Inventory inv){
        this.inventory = inv;
        this.inventoryName = inv.getName();
        this.inventorySize = inv.getSize();
    }

    public Inventory build(){
        return inventory;
    }

    public MyInventory addItemAtAllSlots(ItemStack is){
        for(int i = 0; i <= inventorySize - 1; i++){
            inventory.setItem(i,is);
        }
        return this;
    }

    public MyInventory addItemAtSlot(int slot, ItemStack is){
        inventory.setItem(slot,is);
        return this;
    }

    public MyInventory MaxStackSize(int size) {
        inventory.setMaxStackSize(size);
        return this;
    }


}
