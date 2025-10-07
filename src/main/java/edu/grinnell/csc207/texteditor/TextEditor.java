package edu.grinnell.csc207.texteditor;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
/**
 * The driver for the TextEditor Application.
 */

public class TextEditor {
    public static void drawBuffer(GapBuffer buf, Screen screen) throws IOException {
        int textBoxSize = 30;
        // Get characters from the array, ignoring the gap
        char[] charArray = buf.toString().toCharArray();
        for (int i=0; i < buf.getSize(); i++) {
            // convert chars to textchars
            TextCharacter[] textch = TextCharacter.fromCharacter(charArray[i]);
            // calculate row and col based on index, set char to the backbuffer
            screen.setCharacter(i % textBoxSize, i / textBoxSize, textch[0]);
        }
        int cursorPos = buf.getCursorPosition();
        TerminalPosition curTermPos = new TerminalPosition(cursorPos % textBoxSize, cursorPos / textBoxSize);
        // set the cursor in the backbuffer
        screen.setCursorPosition(curTermPos);

        // transfer everything from backbuffer to frontbuffer
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

        Path textPath = Paths.get(path);
        GapBuffer buf = new GapBuffer();

        // Read file, insert its characters to the buffer
        if ((Files.exists (textPath)) && (Files.isRegularFile(textPath))) {
            String text = Files.readString(textPath);
            for (int i =0; i < text.length(); i++) {
                buf.insert(text.charAt(i));
            }
        } 

        // Initialize the screen
        Screen screen = new DefaultTerminalFactory().createScreen();
        screen.startScreen();
        
        boolean isRunning = true;
        // Process input
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
                //System.out.println("gapBeg: " + buf.gapBeg);
                //System.out.println("gapEnd: " + buf.gapEnd);
            } else if (key.equals (KeyType.Escape)) {
                isRunning = false;
                screen.stopScreen();
                Files.writeString(textPath, buf.toString());
                System.exit(1);
            }

            drawBuffer(buf, screen);
        }
    }
}