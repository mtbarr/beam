package io.github.mtbarr.beam.core.io;

import java.nio.ByteBuffer;

public class ByteArrayReader {

    public static ByteArrayReader read(byte[] bytes) {
        ByteArrayReader reader = new ByteArrayReader(bytes.length);
        reader.byteBuffer.put(bytes);
        reader.byteBuffer.flip();
        return reader;
    }

    final ByteBuffer byteBuffer;

    public ByteArrayReader(int capacity) {
        this.byteBuffer = ByteBuffer.allocate(capacity);
    }

    public int readInt() {
        return byteBuffer.getInt();
    }

    public long readLong() {
        return byteBuffer.getLong();
    }

    public boolean readBoolean() {
        return byteBuffer.get() == 1;
    }

    public byte readByte() {
        return byteBuffer.get();
    }

    public byte[] readBytes(int length) {
        byte[] bytes = new byte[length];
        byteBuffer.get(bytes);
        return bytes;
    }

    public char readChar() {
        return byteBuffer.getChar();
    }

    public double readDouble() {
        return byteBuffer.getDouble();
    }

    public float readFloat() {
        return byteBuffer.getFloat();
    }

    public short readShort() {
        return byteBuffer.getShort();
    }

    public String readString() {
        int length = readInt();
        byte[] bytes = readBytes(length);
        return new String(bytes);
    }
}
