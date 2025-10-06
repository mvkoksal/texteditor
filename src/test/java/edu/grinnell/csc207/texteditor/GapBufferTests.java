package edu.grinnell.csc207.texteditor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.IntRange;

public class GapBufferTests {
    @Test
    public void emptyStringTest() {
        GapBuffer buffer = new GapBuffer();
        assertEquals(0, buffer.getSize());
    }

    @Test
    public void addEndTest() {
        GapBuffer buffer = new GapBuffer();
        buffer.insert('H');
        assertEquals(1, buffer.getSize());
        buffer.insert('e');
        buffer.insert('l');
        buffer.insert('l');
        buffer.insert('o');
        assertEquals(5, buffer.getSize());
        assertEquals('l', buffer.getChar(2));
        assertThrows(IndexOutOfBoundsException.class, () -> buffer.getChar(5));
    }

    @Test
    public void addMiddleTest() {
        GapBuffer buffer = new GapBuffer();
        assertEquals(0, buffer.getCursorPosition());
        buffer.insert('H');
        buffer.insert('e');
        buffer.insert('l');
        buffer.moveLeft();
        buffer.moveLeft();
        buffer.moveRight();
        buffer.moveLeft();
        buffer.insert('a');
        buffer.insert('a');
        assertEquals(3, buffer.getCursorPosition());
        assertEquals("Haael", buffer.toString());
    }

    @Test
    public void deleteEndTest() {
        GapBuffer buffer = new GapBuffer();
        buffer.insert('H');
        buffer.insert('e');
        buffer.insert('l');
        buffer.insert('l');
        buffer.insert('o');
        buffer.delete();
        buffer.delete();
        assertEquals(3, buffer.getSize());
        assertEquals("Hel", buffer.toString());
        assertThrows(IndexOutOfBoundsException.class, () -> buffer.getChar(3));
    }

    @Test
    public void deleteMiddleTest() {
        GapBuffer buffer = new GapBuffer();
        assertEquals(0, buffer.getCursorPosition());
        buffer.insert('H');
        buffer.insert('e');
        buffer.insert('l');
        buffer.insert('l');
        buffer.insert('o');
        buffer.moveLeft();
        buffer.moveLeft();
        buffer.delete();
        buffer.delete();
        assertEquals(1, buffer.getCursorPosition());
        assertEquals("Hlo", buffer.toString());
    }

    @Test
    public void moveCursorLeftEdgeCase() {
        GapBuffer buffer = new GapBuffer();
        buffer.moveLeft();
        assertEquals(0, buffer.getCursorPosition());
        buffer.insert('a');
        buffer.insert('a');
        buffer.moveLeft();
        buffer.moveLeft();
        buffer.moveLeft();
        assertEquals(0, buffer.getCursorPosition());
    }

    @Test
    public void moveCursorRightEdgeCase() {
        GapBuffer buffer = new GapBuffer();
        buffer.moveRight();
        assertEquals(0, buffer.getCursorPosition());
        buffer.insert('a');
        buffer.insert('a');
        buffer.moveRight();
        buffer.moveRight();
        assertEquals(2, buffer.getCursorPosition());
    }

    @Test
    public void longBuffer() {
    GapBuffer buffer = new GapBuffer();
    buffer.insert('H');
    buffer.insert('e');
    buffer.insert('l');
    buffer.insert('l');
    buffer.insert('o');
    buffer.insert('H');
    buffer.insert('e');
    buffer.insert('l');
    buffer.insert('l');
    buffer.insert('o');
    buffer.insert('H');
    buffer.insert('e');
    buffer.insert('l');
    buffer.insert('l');
    buffer.insert('o');
    assertEquals(15, buffer.getSize());
    assertEquals(24, buffer.array.length);
    buffer.moveLeft();
    buffer.moveLeft();
    buffer.moveLeft();
    buffer.moveLeft();
    buffer.moveLeft();
    assertEquals(10, buffer.getCursorPosition());
    buffer.delete();
    buffer.delete();
    buffer.delete();
    buffer.delete();
    buffer.delete();
    assertEquals("HelloHello", buffer.toString());
    }

    @Property
    public boolean bufferAddSize(@ForAll @IntRange(min = 0, max = 1000) int sz) {
        GapBuffer buffer = new GapBuffer();
        for (int i = 0; i < sz; i++) {
            buffer.insert('a');
        }
        return ((buffer.getSize() == sz) && (buffer.getCursorPosition() == sz));
    }
}
