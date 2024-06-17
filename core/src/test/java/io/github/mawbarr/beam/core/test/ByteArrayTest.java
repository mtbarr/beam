package io.github.mawbarr.beam.core.test;

import io.github.mtbarr.beam.core.io.ByteArrayReader;
import io.github.mtbarr.beam.core.io.ByteArrayWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ByteArrayTest {

    private ByteArrayWriter writer;
    private byte[] bytes;

    @BeforeEach
    void setUp() {
        writer = ByteArrayWriter.newByteArrayWriter();
    }

    @Test
    void testWriteReadString() {
        String name = "John";
        writer.writeString(name);
        bytes = writer.toByteArray();

        ByteArrayReader reader = ByteArrayReader.read(bytes);
        String nameRead = reader.readString();

        Assertions.assertEquals(name, nameRead);
    }

    @Test
    void testWriteReadInt() {
        int age = 30;
        writer.writeInt(age);
        bytes = writer.toByteArray();

        ByteArrayReader reader = ByteArrayReader.read(bytes);
        int ageRead = reader.readInt();

        Assertions.assertEquals(age, ageRead);
    }

    @Test
    void testWriteReadStringForCity() {
        String city = "New York";
        writer.writeString(city);
        bytes = writer.toByteArray();

        ByteArrayReader reader = ByteArrayReader.read(bytes);
        String cityRead = reader.readString();

        Assertions.assertEquals(city, cityRead);
    }

    @Test
    void testWriteReadBoolean() {
        boolean married = true;
        writer.writeBoolean(married);
        bytes = writer.toByteArray();

        ByteArrayReader reader = ByteArrayReader.read(bytes);
        boolean marriedRead = reader.readBoolean();

        Assertions.assertEquals(married, marriedRead, "O estado civil lido deve ser igual ao estado civil escrito");
    }

    @Test
    void testMultipleWritesAndReads() {
        String name = "John";
        int age = 30;
        String city = "New York";
        boolean married = true;

        writer.writeString(name);
        writer.writeInt(age);
        writer.writeString(city);
        writer.writeBoolean(married);

        bytes = writer.toByteArray();

        ByteArrayReader reader = ByteArrayReader.read(bytes);
        String nameRead = reader.readString();
        int ageRead = reader.readInt();
        String cityRead = reader.readString();
        boolean marriedRead = reader.readBoolean();

        Assertions.assertEquals(name, nameRead);
        Assertions.assertEquals(age, ageRead);
        Assertions.assertEquals(city, cityRead);
        Assertions.assertEquals(married, marriedRead);
    }
}
