package edu.grinnell.csc207.texteditor;

import java.util.Arrays;

/**
 * A gap buffer-based implementation of a text buffer.
 */
public class GapBuffer {
    public char[] array;
    int gapBeg;
    int gapEnd;

    public GapBuffer () {
        this.gapBeg = 0;
        this.gapEnd = 0;
        array = new char[0];
    }

    /**
     * Inserts a character into the buffer
     * @param ch, a character to insert
     */
    public void insert(char ch) {
        // If the array is empty, initialize it and add ch
        if (array.length == 0) {
            array = new char[3];
            gapBeg = 1;
            gapEnd = array.length;
            array[0] = ch;
        // If the array is full, grow the buffer
        } else {
            if (gapEnd - gapBeg == 0) {
            char[] newArray = new char[array.length*2];
            // copy the left part
            for (int i = 0; i < gapBeg; i++) {
                newArray[i] = array[i];
            }
            // variable counting the number of elements in the right section
            int afterCursorCopyNum = array.length - gapEnd;
            // copy the right part
            for (int i = 1; i <= afterCursorCopyNum; i++) {
                newArray[newArray.length - i] = array[array.length - i];
            }
                // move gapEnd cursor
                gapEnd = newArray.length - afterCursorCopyNum; 
                array = newArray;
            }
            // add new ch
            array[gapBeg] = ch;

            // move gapBeg cursor
            gapBeg++;
        }
    }

    /**
     * Deletes one character from the left of the cursor.
     */
    public void delete() {
        if(gapBeg > 0){
            gapBeg--;
        }
    }

    /**
     * Returns the current cursor position
     * @return gapBeg - position of the cursor, an integer
     */
    public int getCursorPosition() {
        return gapBeg;
    }

    /**
     * Moves the cursor one to the left
     */
    public void moveLeft() {
        if (gapBeg > 0) {
        array[gapEnd - 1] = array[gapBeg - 1];
        gapEnd--;
        gapBeg--;   
        }  
    }

    /**
     * Moves the cursor one to the right
     */
    public void moveRight() {
        if (gapEnd < array.length) {
            array[gapBeg]= array[gapEnd];
            gapBeg++;
            gapEnd++;
        }
    }

    /**
     * Gets the size of the array, ignoring the gap
     * @return the size of the array, an integer
     */
    public int getSize() {
        return (array.length - (gapEnd - gapBeg));
    }

    /**
     * Gets the char at index i of the array, ignoring the gap
     * @param i , an integer
     * @return char, the character at index i
     */
    public char getChar(int i) {
        // index out of bounds
        if (i >= array.length || i < 0) {
            throw new IndexOutOfBoundsException();
        // index at the beginning
        } else if (i >= 0 && i < gapBeg) {
            return array[i];
        } else {
            int gapLength = gapEnd-gapBeg;
            // ignore the gap
            int index = i + gapLength;
            // index out of bounds (check again after increasing the index)
            if(index >= array.length) {
                throw new IndexOutOfBoundsException();
            } else {
                return array[index];
            }
        }
    }

    /**
     * Returns a string representation of the array, ignoring the gap
     * @return String, a string representation of the array of characters
     */
    public String toString() {
        if (array.length == 0) {
            return "";
        } else {
            String beforeCursorString = new String(array, 0, gapBeg);
            String afterCursorString = new String(array, gapEnd, array.length - gapEnd);
            return beforeCursorString + afterCursorString;
        }
    }
}
