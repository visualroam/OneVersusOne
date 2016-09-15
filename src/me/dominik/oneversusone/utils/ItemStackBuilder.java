package me.dominik.oneversusone.utils;

import com.google.common.collect.ImmutableList;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.List;

/**
 * by LPmitKev API
 */
public class ItemStackBuilder {


    private ItemStack is;
    private ItemMeta meta;

    public ItemStackBuilder(final Material mat) {
        this(new ItemStack(mat));
    }

    public ItemStackBuilder(final Material mat, byte data) {
        this(new ItemStack(mat, 1, data));
    }

    public ItemStackBuilder(final ItemStack is) {
        this.is = is;
        this.meta = is.getItemMeta();
    }

    public ItemStackBuilder amount(final int amount) {
        is.setAmount(amount);
        return this;
    }

    public ItemStackBuilder name(final String name) {
        meta.setDisplayName(name);
        return this;
    }

    public ItemStackBuilder lore(final String name) {
        List<String> lore = meta.getLore();
        if (lore == null) {
            lore = new ArrayList<>();
        }
        lore.add(name);
        meta.setLore(lore);
        return this;
    }

    public ItemStackBuilder owner(final String name) {
        if (is.getType() == Material.SKULL_ITEM) {
            ((SkullMeta) meta).setOwner(name);
            return this;
        } else {
            throw new IllegalArgumentException("owner() only applicable for skull items");
        }
    }

    public ItemStackBuilder durability(final int durability) {
        is.setDurability((short) durability);
        return this;
    }

    @SuppressWarnings("deprecation")
    public ItemStackBuilder data(final int data) {
        is.setData(new MaterialData(is.getType(), (byte) data));
        return this;
    }

    public ItemStackBuilder enchantment(final Enchantment enchantment, final int level) {
        meta.addEnchant(enchantment, level, true);
        return this;
    }

    public ItemStackBuilder enchantment(final Enchantment enchantment) {
        meta.addEnchant(enchantment, 1, false);
        return this;
    }

    public ItemStackBuilder type(final Material material) {
        is.setType(material);
        return this;
    }

    public ItemStackBuilder clearLore() {
        meta.setLore(ImmutableList.<String>of());
        return this;
    }

    public ItemStackBuilder clearEnchantments() {
        meta.getEnchants().keySet().forEach(meta::removeEnchant);
        return this;
    }

    public ItemStackBuilder color(Color color) {
        if (is.getType() == Material.LEATHER_BOOTS || is.getType() == Material.LEATHER_CHESTPLATE || is.getType() == Material.LEATHER_HELMET
                || is.getType() == Material.LEATHER_LEGGINGS) {
            ((LeatherArmorMeta) meta).setColor(color);
            return this;
        } else {
            throw new IllegalArgumentException("color() only applicable for leather armor!");
        }
    }

    public ItemStackBuilder author(String name) {
        if (is.getType() == Material.WRITTEN_BOOK) {
            ((BookMeta) meta).setAuthor(name);
            return this;
        } else {
            throw new IllegalArgumentException("owner() only applicable for written books!");
        }
    }

    public ItemStackBuilder page(String content, int page) {
        if (is.getType() == Material.WRITTEN_BOOK) {
            ((BookMeta) meta).setPage(page, content);
            return this;
        } else {
            throw new IllegalArgumentException("owner() only applicable for written books!");
        }
    }

    public ItemStackBuilder page(String content) {
        if (is.getType() == Material.WRITTEN_BOOK) {
            ((BookMeta) meta).addPage(content);
            return this;
        } else {
            throw new IllegalArgumentException("owner() only applicable for written books!");
        }
    }

    public ItemStackBuilder flag(ItemFlag flag) {
        meta.addItemFlags(flag);
        return this;
    }

    public ItemStackBuilder nbt(String name, Object value) { // Integer, String, Boolean, Bayte, Byte[], Flaot, Dobule, Int[], Long Short
        net.minecraft.server.v1_8_R3.ItemStack nis = CraftItemStack.asNMSCopy(is);
        NBTTagCompound tag = nis.hasTag() ? nis.getTag() : new NBTTagCompound();
        if (value instanceof String) {
            tag.setString(name, (String) value);
        } else if (value instanceof Integer) {
            tag.setInt(name, (Integer) value);
        } else if (value instanceof Boolean) {
            tag.setBoolean(name, (Boolean) value);
        } else if (value instanceof Byte) {
            tag.setByte(name, (Byte) value);
        } else if (value instanceof byte[]) {
            tag.setByteArray(name, (byte[]) value);
        } else if (value instanceof Float) {
            tag.setFloat(name, (Float) value);
        } else if (value instanceof Double) {
            tag.setDouble(name, (Double) value);
        } else if (value instanceof int[]) {
            tag.setIntArray(name, (int[]) value);
        } else if (value instanceof Long) {
            tag.setLong(name, (Long) value);
        } else if (value instanceof Short) {
            tag.setShort(name, (Short) value);
        } else {
            throw new IllegalArgumentException("Only Integer, Strings, Booleans, Bytes, ByteArrays, Floats, Doubles, IntegerArrays, Long and Shorts are applicable!");
        }
        nis.setTag(tag);
        is = CraftItemStack.asBukkitCopy(nis);
        return this;
    }

    public ItemStackBuilder glow() {
        this.enchantment(Enchantment.DURABILITY);
        this.flag(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    public ItemStackBuilder unbreakable() {
        meta.spigot().setUnbreakable(true);
        return this;
    }

    public ItemStackBuilder pattern(Pattern pattern) {
        if (is.getType().name().contains("BANNER")) {
            ((BannerMeta) meta).addPattern(pattern);
        } else {
            throw new IllegalArgumentException("pattern() only applicable for banners!");
        }
        return this;
    }

    public ItemStackBuilder baseColor(DyeColor color) {
        if (is.getType().name().contains("BANNER")) {
            ((BannerMeta) meta).setBaseColor(color);
        } else {
            throw new IllegalArgumentException("baseColor() only applicable for banners!");
        }
        return this;
    }



    public ItemStack build() {
        is.setItemMeta(meta);
        return is;
    }


}
