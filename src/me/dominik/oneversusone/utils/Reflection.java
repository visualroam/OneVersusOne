package me.dominik.oneversusone.utils;

import jdk.nashorn.internal.runtime.ECMAException;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * Created by Dominik on 16.09.2016.
 */
public class Reflection {

    public static void setField(Object instanz, Class<?> clazz, String field, Object value) throws Exception {
        Field f = clazz.getDeclaredField(field);
        f.setAccessible(true);
        f.set(instanz, value);
    }

}
