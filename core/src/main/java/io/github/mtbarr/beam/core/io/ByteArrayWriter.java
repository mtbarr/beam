package io.github.mtbarr.beam.core.io;

import java.nio.ByteBuffer;

public class ByteArrayWriter {


    public static ByteArrayWriter newByteArrayWriter() {
        return new ByteArrayWriter(ByteArrays.DEFAULT_CAPACITY);
    }

    final ByteBuffer byteBuffer;

    public ByteArrayWriter(int capacity) {
        this.byteBuffer = ByteBuffer.allocate(capacity);
    }

    public void writeInt(int value) {
        byteBuffer.putInt(value);
    }

    public void writeLong(long value) {
        byteBuffer.putLong(value);
    }

    public void writeBoolean(boolean value) {
        byteBuffer.put((byte) (value ? 1 : 0));
    }

    public void writeByte(byte value) {
        byteBuffer.put(value);
    }

    public void writeBytes(byte[] value) {
        byteBuffer.put(value);
    }

    public void writeChar(char value) {
        byteBuffer.putChar(value);
    }

    public void writeDouble(double value) {
        byteBuffer.putDouble(value);
    }

    public void writeFloat(float value) {
        byteBuffer.putFloat(value);
    }

    public void writeShort(short value) {
        byteBuffer.putShort(value);
    }

    public void writeString(String value) {
        byte[] bytes = value.getBytes();
        writeInt(bytes.length);
        writeBytes(bytes);
    }

    public byte[] toByteArray() {
        byteBuffer.flip();
        byte[] bytes = new byte[byteBuffer.remaining()];
        byteBuffer.get(bytes);
        return bytes;
    }
}
