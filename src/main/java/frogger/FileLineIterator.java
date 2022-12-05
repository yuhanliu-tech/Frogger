package org.cis120.frogger;

import javax.swing.*;
import java.util.Iterator;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.util.NoSuchElementException;

/**
 * This is the FileLineIterator from the Twitterbot Homework
 */

public class FileLineIterator implements Iterator<String> {

    private BufferedReader br;

    public FileLineIterator(BufferedReader reader) {
        // Complete this constructor.

        if (reader == null) {
            throw new IllegalArgumentException("Reader is Null");
        } else {
            br = reader;
        }

    }

    public FileLineIterator(String filePath) {
        this(fileToReader(filePath));
    }

    public static BufferedReader fileToReader(String filePath) {

        try {
            return new BufferedReader(new FileReader(filePath));
        } catch (Exception e) {
            throw new IllegalArgumentException("File Not Found");
        }

    }

    @Override
    public boolean hasNext() {

        boolean value = true;

        try {

            br.mark(1);
            int c = br.read();
            br.reset();

            if (c == -1) {
                value = false;
                br.close();
            }

        } catch (IOException io) {

            try {
                br.close();
            } catch (IOException e) {
                System.out.println("IO Exception");
            }
        }

        return value;
    }

    @Override
    public String next() {

        if (hasNext()) {

            try {
                return br.readLine();
            } catch (IOException e) {
                throw new NoSuchElementException("FLI: No More Data");
            }

        } else {
            throw new NoSuchElementException("FLI: No More Lines");
        }
    }

}
