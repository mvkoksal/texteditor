package edu.grinnell.csc207.texteditor;

/**
 * A naive implementation of a text buffer using a <code>String</code>.
 */
public class SimpleStringBuffer {
    String string;
    int cursor;

    /**
     * Constructs a SimpleStringBuffer
     */
    public SimpleStringBuffer() {
        this.string = "";
        this.cursor = 0;
    }

    /**
     * Insert a character into the gap buffer at the cursor position
     * @param ch, a character to insert
     */
    public void insert(char ch) {
        String firstHalf = string.substring(0, cursor);
        String secondHalf = string.substring(cursor, string.length());
        string = firstHalf + ch + secondHalf;
        cursor++;
    }

    /**
     * Deletes one character from the left of the cursor
     */
    public void delete() {
        if (cursor != 0) {
        // Remove the last character of the left part
        String firstHalf = string.substring(0, cursor-1);
        String secondHalf = string.substring(cursor, string.length());
        string = firstHalf + secondHalf;
        cursor--;
        }
    }

    /**
     * Returns the cursor position
     * @return cursor position, an integer 
     */
    public int getCursorPosition() {
        return cursor;
    }

     /**
     * Moves the cursor one to the left
     */
    public void moveLeft() {
        if (cursor > 0) {
            cursor--;
        }
    }

    /**
     * Moves the cursor one to the right
     */
    public void moveRight() {
        if ((cursor < string.length()) && (cursor != 0)) {
            cursor++;
        }
    }

    /**
     * Gets the size of the array, ignoring the gap
     * @return the size of the array, an integer
     */
    public int getSize() {
        return string.length();
    }

    /**
     * Gets the char at index i of the array
     * @param i , an integer
     * @return char, the character at index i
     * @throws IndexOutOfBoundsException() if index is out of bounds
     */
    public char getChar(int i) {
        if (i < string.length()) {
            return string.charAt(i);
        } else {
            throw new IndexOutOfBoundsException();
        }
        
    }

    /**
     * Returns the string
     * @return string
     */
    @Override
    public String toString() {
        return string;
    }
}
