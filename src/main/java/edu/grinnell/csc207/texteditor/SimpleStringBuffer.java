package edu.grinnell.csc207.texteditor;

/**
 * A naive implementation of a text buffer using a <code>String</code>.
 */
public class SimpleStringBuffer {
    String string;
    int cursor;

    public SimpleStringBuffer() {
        this.string = "";
        this.cursor = 0;
    }

    public void insert(char ch) {
        String firstHalf = string.substring(0, cursor);
        String secondHalf = string.substring(cursor, string.length());
        string = firstHalf + ch + secondHalf;
        cursor++;
    }

    public void delete() {
        if (cursor != 0) {
        // we remove the last char of firstHalf
        String firstHalf = string.substring(0, cursor-1);
        String secondHalf = string.substring(cursor, string.length());
        string = firstHalf + secondHalf;
        cursor--;
        }
    }

    public int getCursorPosition() {
        return cursor;
    }

    public void moveLeft() {
        if (cursor > 0) {
            cursor--;
        }
    }

    public void moveRight() {
        if ((cursor < string.length()) && (cursor != 0)) {
            cursor++;
        }
    }

    public int getSize() {
        return string.length();
    }

    public char getChar(int i) {
        if (i < string.length()) {
            return string.charAt(i);
        } else {
            throw new IndexOutOfBoundsException();
        }
        
    }

    @Override
    public String toString() {
        return string;
    }
}
