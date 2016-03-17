package com.prefixMatches;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Class allows parse input file.
 *
 * @author Denys Storozhenko
 */
public class Parser {
    private String filename;

    public Parser(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * Returns a string, which consists of words separated by spaces.
     *
     * @return a string, which consists of words separated by spaces.
     * @throws FileNotFoundException
     */
    public String parse() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(getClass().getClassLoader().getResource(filename).getFile()));
        StringBuilder stringBuilder = new StringBuilder();
        while (scanner.hasNext()){
            String tmp = scanner.next();
            if (tmp.matches("^[a-zA-Z]+$")){
                stringBuilder.append(tmp).append(" ");
            }
        }
        return String.valueOf(stringBuilder);
    }
}
