package edu.grinnell.csc207.texteditor;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

import java.io.IOException;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.input.KeyStroke;
/**
 * The driver for the TextEditor Application.
 */

 
 // WIDTH 50
 // HEIGHT 50
 // row: cursorPos / 50
 // column: cursorPos % 50

public class TextEditor {

        public static void drawBuffer(GapBuffer buf, Screen screen) throws IOException {
        for (int i =0; i < buf.getSize(); i++) {
            // convert char to textchar
            TextCharacter[] textch = TextCharacter.fromCharacter(buf.array[i]);
            // from index calculate row and col, setchar to the backbuffer
            screen.setCharacter(i % 50, i / 50, textch[0]);
        }

        int cursorPos = buf.getCursorPosition();
        // new termPos
        TerminalPosition curTermPos = new TerminalPosition(cursorPos % 50, cursorPos / 50);
        // set the cursor in the back buffer
        screen.setCursorPosition(curTermPos);

        // everything from back buffer to front buffer
        screen.refresh();
    }

    /**
     * The main entry point for the TextEditor application.
     * @param args command-line arguments.
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: java TextEditor <filename>");
            System.exit(1);
        }

        String path = args[0];
        System.out.format("Loading %s...\n", path);

        GapBuffer buf = new GapBuffer();
        Screen screen = new DefaultTerminalFactory().createScreen();
        screen.startScreen();
        
        boolean isRunning = true;
        while (isRunning) {
            KeyStroke stroke = screen.readInput();
            KeyType key = stroke.getKeyType();

            if (key.equals (KeyType.Character)) {
                buf.insert(stroke.getCharacter());
            } else if (key.equals (KeyType.ArrowLeft)) {
                buf.moveLeft();
            } else if (key.equals (KeyType.ArrowRight)) {
                buf.moveRight();
            } else if (key.equals (KeyType.Backspace)) {
                buf.delete();
            } else if (key.equals (KeyType.Escape)) {
                screen.stopScreen();
                isRunning = false;
                System.exit(1);
            }
            drawBuffer(buf, screen);
        }

    }
}
