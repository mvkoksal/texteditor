package edu.grinnell.csc207.texteditor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.IntRange;

public class SimpleStringBufferTests {
    @Test
    public void emptyStringTest() {
        SimpleStringBuffer buffer = new SimpleStringBuffer();
        assertEquals(0, buffer.getSize());
    }

    @Test
    public void addEndTest() {
        SimpleStringBuffer buffer = new SimpleStringBuffer();
        buffer.insert('H');
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
        SimpleStringBuffer buffer = new SimpleStringBuffer();
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
        SimpleStringBuffer buffer = new SimpleStringBuffer();
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
        SimpleStringBuffer buffer = new SimpleStringBuffer();
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
        SimpleStringBuffer buffer = new SimpleStringBuffer();
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
        SimpleStringBuffer buffer = new SimpleStringBuffer();
        buffer.moveRight();
        assertEquals(0, buffer.getCursorPosition());
        buffer.insert('a');
        buffer.insert('a');
        buffer.moveRight();
        buffer.moveRight();
        assertEquals(2, buffer.getCursorPosition());
    }

    @Property
    public boolean bufferAddSize(@ForAll @IntRange(min = 0, max = 1000) int sz) {
        SimpleStringBuffer buffer = new SimpleStringBuffer();
        for (int i = 0; i < sz; i++) {
            buffer.insert('a');
        }
        return ((buffer.getSize() == sz) && (buffer.getCursorPosition() == sz));
    }
}
