package com.minecraft.nbt;

import java.util.HashMap;
import java.util.Map;

public class NBTTagCompound {

    private Map<String, Object> data;
    private Map<String, Byte> types;

    public NBTTagCompound() {
        data = new HashMap<>();
        types = new HashMap<>();
    }

    public void setByte(String key, byte value) {
        data.put(key, value);
        types.put(key, (byte)1);
    }

    public void setShort(String key, short value) {
        data.put(key, value);
        types.put(key, (byte)2);
    }

    public void setInt(String key, int value) {
        data.put(key, value);
        types.put(key, (byte)3);
    }

    public void setLong(String key, long value) {
        data.put(key, value);
        types.put(key, (byte)4);
    }

    public void setFloat(String key, float value) {
        data.put(key, value);
        types.put(key, (byte)5);
    }

    public void setDouble(String key, double value) {
        data.put(key, value);
        types.put(key, (byte)6);
    }

    public void setString(String key, String value) {
        data.put(key, value);
        types.put(key, (byte)8);
    }

    public void setByteArray(String key, byte[] value) {
        data.put(key, value);
        types.put(key, (byte)9);
    }

    public void setCompound(String key, NBTTagCompound value) {
        data.put(key, value);
        types.put(key, (byte)10);
    }

    public void setIntArray(String key, int[] value) {
        data.put(key, value);
        types.put(key, (byte)11);
    }

    public void setLongArray(String key, long[] value) {
        data.put(key, value);
        types.put(key, (byte)12);
    }

    public void setBoolean(String key, boolean value) {
        setByte(key, (byte)(value ? 1 : 0));
    }

    public byte getByte(String key) {
        return (Byte)data.getOrDefault(key, (byte)0);
    }

    public short getShort(String key) {
        return (Short)data.getOrDefault(key, (short)0);
    }

    public int getInt(String key) {
        return (Integer)data.getOrDefault(key, 0);
    }

    public long getLong(String key) {
        return (Long)data.getOrDefault(key, 0L);
    }

    public float getFloat(String key) {
        return (Float)data.getOrDefault(key, 0.0f);
    }

    public double getDouble(String key) {
        return (Double)data.getOrDefault(key, 0.0);
    }

    public String getString(String key) {
        return (String)data.getOrDefault(key, "");
    }

    public byte[] getByteArray(String key) {
        return (byte[])data.getOrDefault(key, new byte[0]);
    }

    public NBTTagCompound getCompound(String key) {
        return (NBTTagCompound)data.getOrDefault(key, new NBTTagCompound());
    }

    public int[] getIntArray(String key) {
        return (int[])data.getOrDefault(key, new int[0]);
    }

    public long[] getLongArray(String key) {
        return (long[])data.getOrDefault(key, new long[0]);
    }

    public boolean getBoolean(String key) {
        return getByte(key) != 0;
    }

    public byte getType(String key) {
        return types.getOrDefault(key, (byte)0);
    }

    public boolean hasKey(String key) {
        return data.containsKey(key);
    }

    public String[] getKeys() {
        return data.keySet().toArray(new String[0]);
    }

    public void remove(String key) {
        data.remove(key);
        types.remove(key);
    }

    public void clear() {
        data.clear();
        types.clear();
    }
}
