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

    public void insert(char ch) {
        if (array.length == 0) {
            array = new char[3];
            gapBeg = 1;
            gapEnd = array.length;
            array[0] = ch;
            
        } else {
            if (gapEnd - gapBeg == 0) {
            char[] newArray = new char[array.length*2];
            // copy the begpart
            for (int i = 0; i < gapBeg; i++) {
                newArray[i] = array[i];
            }

            // copy the endpart, this many elements to copy in the second part of the split
            int afterCursorCopyNum = array.length - gapEnd;
            for (int i = 1; i <= afterCursorCopyNum; i++) {
                newArray[newArray.length - i] = array[array.length - i];
            }
                // update gapEnd cursor
                gapEnd = newArray.length - afterCursorCopyNum; // this amount of elements copied, and we move the cursor that many times to the left from the end
                array = newArray;
            }
            // add new ch
            array[gapBeg] = ch;
            gapBeg++;
        }
    }

    public void delete() {
        if(gapBeg > 0){
            gapBeg--;
        }
    }

    public int getCursorPosition() {
        return gapBeg;
    }

    public void moveLeft() {
        if (gapBeg > 0) {
        array[gapEnd - 1] = array[gapBeg - 1];
        gapEnd--;
        gapBeg--;   
        }  
    }

    public void moveRight() {
        if (gapEnd < array.length) {
            array[gapBeg]= array[gapEnd];
            gapBeg++;
            gapEnd++;
        }
    }

    public int getSize() {
        return (array.length - (gapEnd - gapBeg));
    }

    public char getChar(int i) {
        // if index out of bounds
        if (i >= array.length || i < 0) {
            throw new IndexOutOfBoundsException();
        // if the index is at the beginning
        } else if (i >= 0 && i < gapBeg) {
            return array[i];
        } else {
            int gapLength = gapEnd-gapBeg;
            // skip the length of the space
            int index = i + gapLength;
            if(index >= array.length) {
                throw new IndexOutOfBoundsException();
            } else {
                return array[index];
            }
        }
    }

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
