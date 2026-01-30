package com.minecraft.nbt;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class NBTManager {

    public NBTTagCompound readNBT(File file) {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(file))) {
            if (readByte(dis) == 10) {
                return readTagCompound(dis);
            }
        } catch (IOException e) {
            System.err.println("Failed to read NBT file: " + e.getMessage());
        }
        return new NBTTagCompound();
    }

    public void writeNBT(File file, NBTTagCompound compound) {
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            
            try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(file))) {
                writeByte(dos, 10);
                writeTagCompound(dos, compound);
            }
        } catch (IOException e) {
            System.err.println("Failed to write NBT file: " + e.getMessage());
        }
    }

    private NBTTagCompound readTagCompound(DataInputStream dis) throws IOException {
        NBTTagCompound compound = new NBTTagCompound();
        String name = readString(dis);
        
        byte type;
        while ((type = readByte(dis)) != 0) {
            readTag(dis, compound, type);
        }
        
        return compound;
    }

    private void readTag(DataInputStream dis, NBTTagCompound compound, byte type) throws IOException {
        String name = readString(dis);
        
        switch (type) {
            case 1:
                compound.setByte(name, readByte(dis));
                break;
            case 2:
                compound.setShort(name, readShort(dis));
                break;
            case 3:
                compound.setInt(name, readInt(dis));
                break;
            case 4:
                compound.setLong(name, readLong(dis));
                break;
            case 5:
                compound.setFloat(name, readFloat(dis));
                break;
            case 6:
                compound.setDouble(name, readDouble(dis));
                break;
            case 8:
                compound.setString(name, readString(dis));
                break;
            case 9:
                compound.setByteArray(name, readByteArray(dis));
                break;
            case 10:
                compound.setCompound(name, readTagCompound(dis));
                break;
            case 11:
                compound.setIntArray(name, readIntArray(dis));
                break;
            case 12:
                compound.setLongArray(name, readLongArray(dis));
                break;
        }
    }

    private void writeTagCompound(DataOutputStream dos, NBTTagCompound compound) throws IOException {
        writeString(dos, "");
        
        for (String key : compound.getKeys()) {
            byte type = compound.getType(key);
            writeByte(dos, type);
            writeString(dos, key);
            
            switch (type) {
                case 1:
                    writeByte(dos, compound.getByte(key));
                    break;
                case 2:
                    writeShort(dos, compound.getShort(key));
                    break;
                case 3:
                    writeInt(dos, compound.getInt(key));
                    break;
                case 4:
                    writeLong(dos, compound.getLong(key));
                    break;
                case 5:
                    writeFloat(dos, compound.getFloat(key));
                    break;
                case 6:
                    writeDouble(dos, compound.getDouble(key));
                    break;
                case 8:
                    writeString(dos, compound.getString(key));
                    break;
                case 9:
                    writeByteArray(dos, compound.getByteArray(key));
                    break;
                case 10:
                    writeTagCompound(dos, compound.getCompound(key));
                    break;
                case 11:
                    writeIntArray(dos, compound.getIntArray(key));
                    break;
                case 12:
                    writeLongArray(dos, compound.getLongArray(key));
                    break;
            }
        }
        
        writeByte(dos, 0);
    }

    private byte readByte(DataInputStream dis) throws IOException {
        return dis.readByte();
    }

    private short readShort(DataInputStream dis) throws IOException {
        return Short.reverseBytes(dis.readShort());
    }

    private int readInt(DataInputStream dis) throws IOException {
        return Integer.reverseBytes(dis.readInt());
    }

    private long readLong(DataInputStream dis) throws IOException {
        return Long.reverseBytes(dis.readLong());
    }

    private float readFloat(DataInputStream dis) throws IOException {
        return Float.intBitsToFloat(Integer.reverseBytes(dis.readInt()));
    }

    private double readDouble(DataInputStream dis) throws IOException {
        return Double.longBitsToDouble(Long.reverseBytes(dis.readLong()));
    }

    private String readString(DataInputStream dis) throws IOException {
        short length = readShort(dis);
        byte[] bytes = new byte[length];
        dis.readFully(bytes);
        return new String(bytes, "UTF-8");
    }

    private byte[] readByteArray(DataInputStream dis) throws IOException {
        int length = readInt(dis);
        byte[] bytes = new byte[length];
        dis.readFully(bytes);
        return bytes;
    }

    private int[] readIntArray(DataInputStream dis) throws IOException {
        int length = readInt(dis);
        int[] array = new int[length];
        for (int i = 0; i < length; i++) {
            array[i] = readInt(dis);
        }
        return array;
    }

    private long[] readLongArray(DataInputStream dis) throws IOException {
        int length = readInt(dis);
        long[] array = new long[length];
        for (int i = 0; i < length; i++) {
            array[i] = readLong(dis);
        }
        return array;
    }

    private void writeByte(DataOutputStream dos, byte value) throws IOException {
        dos.writeByte(value);
    }

    private void writeShort(DataOutputStream dos, short value) throws IOException {
        dos.writeShort(Short.reverseBytes(value));
    }

    private void writeInt(DataOutputStream dos, int value) throws IOException {
        dos.writeInt(Integer.reverseBytes(value));
    }

    private void writeLong(DataOutputStream dos, long value) throws IOException {
        dos.writeLong(Long.reverseBytes(value));
    }

    private void writeFloat(DataOutputStream dos, float value) throws IOException {
        dos.writeInt(Integer.reverseBytes(Float.floatToIntBits(value)));
    }

    private void writeDouble(DataOutputStream dos, double value) throws IOException {
        dos.writeLong(Long.reverseBytes(Double.doubleToLongBits(value)));
    }

    private void writeString(DataOutputStream dos, String value) throws IOException {
        byte[] bytes = value.getBytes("UTF-8");
        writeShort(dos, (short)bytes.length);
        dos.write(bytes);
    }

    private void writeByteArray(DataOutputStream dos, byte[] value) throws IOException {
        writeInt(dos, value.length);
        dos.write(value);
    }

    private void writeIntArray(DataOutputStream dos, int[] value) throws IOException {
        writeInt(dos, value.length);
        for (int v : value) {
            writeInt(dos, v);
        }
    }

    private void writeLongArray(DataOutputStream dos, long[] value) throws IOException {
        writeInt(dos, value.length);
        for (long v : value) {
            writeLong(dos, v);
        }
    }
}
